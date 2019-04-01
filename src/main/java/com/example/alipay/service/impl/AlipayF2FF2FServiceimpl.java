package com.example.alipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;

import com.alipay.demo.trade.service.AlipayTradeService;
import com.example.alipay.configuration.AlipayProperties;
import com.example.alipay.controller.AlipayF2FController;
import com.example.alipay.dao.OrderMapper;
import com.example.alipay.dao.Order_itemMapper;
import com.example.alipay.dao.Pay_logMapper;
import com.example.alipay.dao.SellerMapper;
import com.example.alipay.pojo.*;
import com.example.alipay.service.AlipayF2FService;
import com.example.common.exception.PayException;
import com.example.common.result.ResultEnum;
import com.example.common.util.IdWorker;
import com.example.common.util.QRCodeUtil;
import com.google.zxing.WriterException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @Author: shanfeiyang
 * @Date: 2019/3/18 11:49
 * @Version 1.0
 */
@Service
@Transactional(readOnly = true)
public class AlipayF2FF2FServiceimpl implements AlipayF2FService {
    private static Logger logger = LoggerFactory.getLogger(AlipayF2FController.class);

    @Autowired
    private AlipayTradeService alipayTradeService;

    @Autowired
    private AlipayProperties aliPayProperties;

    @Autowired
    private AlipayClient alipayClient;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private Order_itemMapper order_itemMapper;

    @Resource
    private Pay_logMapper pay_logMapper;

    @Resource
    private SellerMapper sellerMapper;


    /**
     * 订单预创建，生成二维码
     *
     * @param
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false)
    public Map precreate(Double price, Integer quantity, String goodsName, HttpServletResponse response) {
        //out_trade_no 商户外部订单号
        IdWorker idWorker = new IdWorker();
        String out_trade_no = String.valueOf(idWorker.nextId());
        //seller_id 卖家支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
        String sellerId = getSellerId();
        String seller_id = "";
        //total_amount 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
        String totalprice = String.format("%.2f", price * quantity);
        String total_amount = totalprice;
        //subject 订单标题  显示在支付页面
        String subject = "九阳豆浆-支付";
        //store_id 商户门店编号
        String store_id = "test_store_id";
        //设置商品详情
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        GoodsDetail goods = new GoodsDetail();
        goods.setGoodsName(goodsName);
        goods.setPrice(Double.toString(price));
        goods.setQuantity(new Long(Integer.toString(quantity)));
        goodsDetailList.add(goods);
        //qr_code_timeout_express 二维码超时时间 必须大于1m  默认超时时间是两个小时
        String qr_code_timeout_express = "2m";
        // timeout_express 订单最晚付款时间 用户扫码后，支付宝创建订单后开始计时
        String timeoutExpress = "5m";
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(out_trade_no);//订单号
        model.setSellerId(seller_id);//收款支付宝id
        model.setTotalAmount(total_amount);//总金额
        model.setSubject(subject);//标题
        model.setTimeoutExpress(timeoutExpress);//最晚付款时间
        model.setQrCodeTimeoutExpress(qr_code_timeout_express);//二维码有效时间
        model.setStoreId(store_id);//商户门店编号
        model.setGoodsDetail(goodsDetailList);//商品详细描述
        request.setNotifyUrl(aliPayProperties.getNotifyUrl());//回调地址
        request.setBizModel(model);
        Map map = new HashMap();
        try {
            AlipayTradePrecreateResponse res = alipayClient.execute(request, null, getAppAuthToken());
            String code = res.getCode();
            String msg = res.getMsg();
            if (code.equals("10000") && msg.equals("Success")) {
                logger.info("支付宝预下单成功");
                //保存订单信息
                saveOrder(sellerId, total_amount, res);
                //保存订单详情
                saveOrderItem(price, quantity, goodsName, sellerId, total_amount, res);
                //保存支付日志
                savePayLog(out_trade_no, total_amount);
                String content = res.getQrCode();//二维码信息
                String logoImgpath = "F://qr/logo.jpg";
                String codeurl = String.format("F://qr/qr-%s.png", res.getOutTradeNo());
                String act_codeurl = String.format("/images/qr-%s.png", res.getOutTradeNo());
                try {
                    QRCodeUtil.encode(content, logoImgpath, codeurl, true);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                //生成的二维码图上传到图片服务器，并返回图片的url地址
                map.put("message", "下单成功，请在2分钟内完成支付");
                map.put("codeurl", act_codeurl);
                map.put("out_trade_no", out_trade_no);
            } else {
                logger.info("与支付宝服务器通讯失败:", msg);
                throw new PayException(ResultEnum.ALIPAY_CONNECT_FAIL);
            }
        } catch (AlipayApiException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new PayException(ResultEnum.ALIPAY_PRECREATE_FAIL);
        }
        return map;
    }

    private String getSellerId() {
        String storeId = aliPayProperties.getStoreId();
        Seller seller = sellerMapper.selectByStoreId(storeId);
        String sellerId = seller.getSellerId();
        return sellerId;
    }

    private void savePayLog(String out_trade_no, String total_amount) {
        Pay_log pay_log = new Pay_log();
        pay_log.setOutTradeNo(out_trade_no);//订单号
        pay_log.setCreateTime(new Date());//创建时间
        pay_log.setPayType("0");//支付类型：支付宝
        pay_log.setTotalFee(new BigDecimal(total_amount));//支付金额
        pay_log.setTradeStatus("0");//支付状态
        pay_logMapper.insert(pay_log);
    }

    private void saveOrderItem(Double price, Integer quantity, String goodsName, String sellerId, String total_amount, AlipayTradePrecreateResponse res) {
        Order_item order_item = new Order_item();//订单详情
        order_item.setOutTradeNo(res.getOutTradeNo());//订单号
        order_item.setGoodsName(goodsName);//商品名称
        order_item.setNum(quantity);//商品数量
        order_item.setTotalFee(new BigDecimal(total_amount));//总金额
        //order_item.setGoodsId();//商品id
        order_item.setPrice(new BigDecimal(price));//单价
        order_item.setSellerId(sellerId);//商家id
        order_itemMapper.insert(order_item);
    }

    private void saveOrder(String sellerId, String total_amount, AlipayTradePrecreateResponse res) {
        Order order = new Order();//订单
        order.setOutTradeNo(res.getOutTradeNo());//订单号
        order.setPayment(new BigDecimal(total_amount));//支付金额
        order.setCreateTime(new Date());//订单创建时间
        order.setOrderStatus("1");//未付款
        order.setGoodsStatus("1");//未出货
        order.setSellerId(sellerId);
        orderMapper.insert(order);
    }

    /**
     * 查询订单支付状态
     *
     * @param out_trade_no
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public ResultMessage queryPayStatus(String out_trade_no) {
        ResultMessage resultMessage = new ResultMessage();
        if (!StringUtils.isEmpty(out_trade_no)) {
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo(out_trade_no);
            request.setBizModel(model);
            try {
                AlipayTradeQueryResponse response = alipayClient.execute(request, null, getAppAuthToken());
                String status = response.getTradeStatus();
                String code = response.getCode();
                String msg = response.getMsg();
                if (code.equals("10000") && msg.equals("Success")) {
                    logger.info("订单状态查询成功");
                    //支付成功
                    if (status.equals("TRADE_SUCCESS") || status.equals("TRADE_FINISHED")) {
                        logger.info("订单支付成功: )");
                        resultMessage.setSuccess(true);
                        resultMessage.setMessage("支付成功");
                    }
                    //等待支付
                    else if (status.equals("WAIT_BUYER_PAY")) {
                        logger.info("等待支付");
                        resultMessage.setSuccess(false);
                        resultMessage.setMessage("等待支付");
                    } else if (status.equals("TRADE_CLOSED")) {
                        logger.info("未付款交易超时关闭,或支付完成后全额退款");
                        resultMessage.setSuccess(false);
                        resultMessage.setMessage("未付款交易超时关闭,或支付完成后全额退款");
                    }
                } else if (code.equals("40004")) {
                    logger.info("未扫码");
                    resultMessage.setSuccess(false);
                    resultMessage.setMessage("未扫码");
                } else {
                    logger.info("与支付宝服务器通讯失败:", msg);
                    throw new PayException(ResultEnum.ALIPAY_CONNECT_FAIL);
                }
            } catch (AlipayApiException e) {
                logger.error(e.getErrMsg());
                e.printStackTrace();
                throw new PayException(ResultEnum.ALIPAY_ORDER_QUERY_FAIL);
            }
        } else {
            resultMessage.setSuccess(false);
            resultMessage.setMessage("订单号为空,创建订单失败");
        }
        return resultMessage;
    }


    /**
     * 退款
     *
     * @param out_trade_no 商户订单号
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultMessage refund(String out_trade_no) {
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        String totalAmount = orderMapper.getTotalAmount(out_trade_no);
        model.setOutTradeNo(out_trade_no);// 商户订单号
        model.setRefundAmount(totalAmount); // 退款金额
        model.setRefundReason("无理由退货");// 退款原因
        alipayRequest.setBizModel(model);
        ResultMessage resultMessage = null;
        try {
            AlipayTradeRefundResponse alipayResponse = alipayClient.execute(alipayRequest, null, getAppAuthToken());
            String fundChange = alipayResponse.getFundChange();
            String code = alipayResponse.getCode();
            String msg = alipayResponse.getMsg();
            resultMessage = new ResultMessage();
            // 根据fund_change得知本次退款是否发生资金变化
            if (code.equals("10000") && msg.equals("Success")) {
                if (fundChange.equals("Y")) {
                    // A.状态为Y，则修改订单状态为已退款，提示该订单退款成功
                    logger.info("退款成功");
                    Order order = orderMapper.selectByPrimaryKey(out_trade_no);
                    order.setCloseTime(new Date());
                    order.setOrderStatus("4");
                    orderMapper.updateByPrimaryKey(order);
                    resultMessage.setSuccess(true);
                    resultMessage.setMessage("退款成功");
                } else if (fundChange.equals("N")) {
                    // B.状态为N，则退款失败，如果重复提交已退款的订单也会返回N
                    logger.info("退款失败,请勿提交已退款的订单");
                    resultMessage.setSuccess(false);
                    resultMessage.setMessage("退款失败，请勿提交已退款的订单");
                }
            } else {
                logger.info("与支付宝服务器通讯失败:", msg);
                throw new PayException(ResultEnum.ALIPAY_CONNECT_FAIL);
            }
        } catch (AlipayApiException e) {
            logger.error(e.getErrMsg());
            e.printStackTrace();
            throw new PayException(ResultEnum.ALIPAY_ORDER_REFUND_FAIL);
        }
        return resultMessage;
    }

    /**
     * 关闭交易
     *
     * @param out_trade_no
     * @return
     * @throws AlipayApiException
     */
    @Override
    @Transactional(readOnly = true)
    public ResultMessage close(String out_trade_no) {
        ResultMessage resultMessage = new ResultMessage();
        try {
            Order order = orderMapper.selectByPrimaryKey(out_trade_no);
            String orderStatus = order.getOrderStatus();
            if (orderStatus.equals("1")) {
                AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
                AlipayTradeCloseModel model = new AlipayTradeCloseModel();
                model.setOutTradeNo(out_trade_no);
                alipayRequest.setBizModel(model);
                AlipayTradeCloseResponse response = alipayClient.execute(alipayRequest, null, getAppAuthToken());
                String code = response.getCode();
                String msg = response.getMsg();
                if (code.equals("10000") && msg.equals("Success")) {
                    logger.info("订单取消成功");
                    resultMessage.setSuccess(true);
                } else {
                    logger.info("与支付宝服务器通讯失败:", msg);
                    throw new PayException(ResultEnum.ALIPAY_CONNECT_FAIL);
                }
            } else {
                logger.info("订单已支付，不能取消");
                resultMessage.setSuccess(false);
                resultMessage.setMessage("订单已支付，不能取消");
            }
        } catch (AlipayApiException e) {
            logger.error(e.getErrMsg());
            e.printStackTrace();
            throw new PayException(ResultEnum.ALIPAY_ORDER_CLOSE_FAIL);
        }

        return resultMessage;
    }

    /**
     * 退款查询
     *
     * @param out_trade_no  商户订单号
     * @param refundOrderNo 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部订单号
     * @return
     * @throws AlipayApiException
     */
    @Override
    public String refundQuery(String out_trade_no, String refundOrderNo) {
        AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutTradeNo(out_trade_no);
        model.setOutRequestNo(refundOrderNo);
        alipayRequest.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse alipayResponse = null;
        try {
            alipayResponse = alipayClient.execute(alipayRequest, null, getAppAuthToken());
            String refundReason = alipayResponse.getRefundReason();
            logger.info(alipayResponse.getBody());
        } catch (AlipayApiException e) {
            logger.error(e.getErrMsg());
            e.printStackTrace();
        }
        return alipayResponse.getBody();
    }

    /**
     * billDate : 账单时间：日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
     * 查询对账单下载地址: https://docs.open.alipay.com/api_15/alipay.data.dataservice.bill.downloadurl.query/
     *
     * @param billDate
     */
    @Override
    public void queryBill(String billDate) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String billDate1 = simpleDateFormat.format(date);
        System.out.println(billDate1);
        // 1. 查询对账单下载地址
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();

        model.setBillType("trade");
        model.setBillDate("2019-03-18");
        request.setBizModel(model);
        try {
            AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request, null, getAppAuthToken());
            if (response.isSuccess()) {
                String billDownloadUrl = response.getBillDownloadUrl();
                System.out.println(billDownloadUrl);

                // 2. 下载对账单
                List<String> orderList = this.downloadBill(billDownloadUrl);
                System.out.println(orderList);
                // 3. 先比较支付宝的交易合计/退款合计笔数/实收金额是否和自己数据库中的数据一致，如果不一致证明有异常，再具体找出那些订单有异常
                // 查找支付宝支付成功而自己支付失败的记录和支付宝支付失败而自己认为支付成功的异常订单记录到数据库

            } else {
                // 失败
                String code = response.getCode();
                String msg = response.getMsg();
                String subCode = response.getSubCode();
                String subMsg = response.getSubMsg();
            }
        } catch (AlipayApiException e) {
            logger.error(e.getErrMsg());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载下来的是一个【账号_日期.csv.zip】文件（zip压缩文件名，里面有多个.csv文件）
     * 账号_日期_业务明细 ： 支付宝业务明细查询
     * 账号_日期_业务明细(汇总)：支付宝业务汇总查询
     * <p>
     * 注意：如果数据量比较大，该方法可能需要更长的执行时间
     *
     * @param billDownLoadUrl
     * @return
     * @throws IOException
     */
    private List<String> downloadBill(String billDownLoadUrl) throws IOException {
        String ordersStr = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .setSocketTimeout(60000)
                .build();
        HttpGet httpRequest = new HttpGet(billDownLoadUrl);
        httpRequest.setConfig(config);
        CloseableHttpResponse response = null;
        byte[] data = null;
        try {
            response = httpClient.execute(httpRequest);
            HttpEntity entity = response.getEntity();
            data = EntityUtils.toByteArray(entity);
        } finally {
            response.close();
            httpClient.close();
        }
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(data), Charset.forName("GBK"));
        ZipEntry zipEntry = null;
        try {
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    String name = zipEntry.getName();
                    // 只要明细不要汇总
                    if (name.contains("汇总")) {
                        continue;
                    }
                    byte[] byteBuff = new byte[4096];
                    int bytesRead = 0;
                    while ((bytesRead = zipInputStream.read(byteBuff)) != -1) {
                        byteArrayOutputStream.write(byteBuff, 0, bytesRead);
                    }
                    ordersStr = byteArrayOutputStream.toString("GBK");
                } finally {
                    byteArrayOutputStream.close();
                    zipInputStream.closeEntry();
                }
            }
        } finally {
            zipInputStream.close();
        }

        if (ordersStr.equals("")) {
            return null;
        }
        String[] bills = ordersStr.split("\r\n");
        List<String> billList = Arrays.asList(bills);
        billList = billList.parallelStream().map(item -> item.replace("\t", "")).collect(Collectors.toList());

        return billList;
    }

    private String getAppAuthToken() {
        Seller seller = sellerMapper.selectByStoreId(aliPayProperties.getStoreId());
        return seller.getAppAuthToken();
    }

}
