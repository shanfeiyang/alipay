package com.example.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.alipay.configuration.AlipayProperties;
import com.example.alipay.dao.OrderMapper;
import com.example.alipay.dao.Pay_logMapper;
import com.example.alipay.pojo.Order;
import com.example.alipay.pojo.Pay_log;
import com.example.common.exception.PayException;
import com.example.common.result.ResultEnum;
import com.example.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 支付宝通用接口.
 * <p>
 * detailed description
 *
 * @Author: shanfeiyang
 * @Date: 2019/3/11 14:52
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/alipaycallbck")
public class AlipayCallbackController {
    private static Logger logger = LoggerFactory.getLogger(AlipayCallbackController.class);


    @Autowired
    private AlipayProperties aliPayProperties;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private Pay_logMapper pay_logMapper;


    /**
     * 支付异步通知
     * <p>
     * 接收到异步通知并验签通过后，一定要检查通知内容，包括通知中的app_id、out_trade_no、total_amount是否与请求中的一致，并根据trade_status进行后续业务处理。
     * <p>
     * https://docs.open.alipay.com/194/103296
     */
    @RequestMapping("/notify")
    @Transactional(readOnly = false)
    public String notify(HttpServletRequest request) {
        // 一定要验签，防止黑客篡改参数
        Map<String, String> map = convertRequestParamsToMap(request);
        StringBuilder notifyBuild = new StringBuilder("/****************************** alipay notify ******************************/\n");
        map.forEach((key, value) -> notifyBuild.append(key + "=" + value + "\n"));
        logger.info("支付宝回调参数:" + notifyBuild.toString());
        //验证签名
        boolean flag = this.rsaCheckV1(request);

        if (flag) {
            logger.info("支付宝回调签名验证成功");
            /**
             * TODO 需要严格按照如下描述校验通知数据的正确性
             *
             * 商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
             * 并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
             * 同时需要校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
             *
             * 上述有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
             * 在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
             * 在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
             * TRADE_FINISHED  即时到账普通版。普通版不支持支付完成后的退款操作，即用户充值完成后，该交易就算是完成了，这笔交易就不能再做任何操作了。
             * TRADE_SUCCESS   即时到账高级版。这个版本在用户充值完成后，卖家可以执行退款操作进行退款，即该交易还没有彻底完成，卖家还可以修改这笔交易。
             */
            // TRADE_FINISHED(表示交易已经成功结束，并不能再对该交易做后续操作);
            // TRADE_SUCCESS(表示交易已经成功结束，可以对该交易做后续操作，如：分润、退款等);
            //对支付结果中的参数进行校验
            try {
                this.check(map);
            } catch (PayException e) {
                e.printStackTrace();
                return "fail";
            }
            String tradeStatus = map.get("trade_status");// 交易状态
            logger.info(tradeStatus);
            //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知TRADE_SUCCESS。
            if (tradeStatus.equals("TRADE_SUCCESS")) {
                String out_trade_no = map.get("out_trade_no");//商户订单号
                String trade_no = map.get("trade_no");//支付宝交易号
                String buyer_id = map.get("buyer_id");//顾客账号
                String notify_time = map.get("notify_time");//通知时间，即支付完成时间
                String receipt_amount = map.get("receipt_amount");//商户实收金额
                //更新订单信息
                updateOrder(out_trade_no, buyer_id, notify_time, receipt_amount);
                //更新支付日志
                updatePaylog(out_trade_no, trade_no, notify_time);
                logger.info("pay success");
            } else if (tradeStatus.equals("WAIT_BUYER_PAY")) {
                logger.info("wait buyer pay");
            } else if (tradeStatus.equals("TRADE_FINISHED")) {
                logger.info("trade finished");
                //默认退款时间三个月内没有退款，交易状态转为TRADE_FINISHED
                //此状态可以向支付宝申请取消
            }
            return "success";
        } else {
            logger.error("支付宝回调签名验证失败");
            return "fail";
        }
    }

    private void updatePaylog(String out_trade_no, String trade_no, String notify_time) {
        Pay_log pay_log = pay_logMapper.selectByPrimaryKey(out_trade_no);
        pay_log.setOutTradeNo(out_trade_no);
        pay_log.setTradeNo(trade_no);
        pay_log.setTradeStatus("1");
        pay_log.setPayTime(DateUtils.stringToDate(notify_time, DateUtils.ymdhms_format));
        pay_logMapper.updateByPrimaryKey(pay_log);
    }

    private void updateOrder(String out_trade_no, String buyer_id, String notify_time, String receipt_amount) {
        Order order = orderMapper.selectByPrimaryKey(out_trade_no);
        order.setOutTradeNo(out_trade_no);
        order.setActRecMoney(new BigDecimal(receipt_amount));
        order.setUserId(buyer_id);
        order.setFinishTime(DateUtils.stringToDate(notify_time, DateUtils.ymdhms_format));
        order.setOrderStatus("2");
        orderMapper.updateByPrimaryKey(order);
    }

    private void check(Map<String, String> params) throws PayException {
        String outTradeNo = params.get("out_trade_no");

        // 1、验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        Order order = orderMapper.selectByPrimaryKey(outTradeNo);
        if (order == null) {
            log.error("【支付宝支付】异步通知, 订单不存在, out_trade_no={}", outTradeNo);
            throw new PayException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），

        BigDecimal total_amount = new BigDecimal(params.get("total_amount")).multiply(new BigDecimal(100));
        BigDecimal payment = order.getPayment().multiply(new BigDecimal(100));
        if (total_amount.compareTo(payment) != 0) {
            log.error("【支付宝支付】异步通知, 订单金额不一致, orderId={}, 微信通知金额={}, 系统金额={}",
                    outTradeNo, total_amount, payment);
            throw new PayException(ResultEnum.ALIPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        // 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
        // 第三步可根据实际情况省略
    }

    // 将request中的参数转换成Map
    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();

        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }

    /**
     * 校验签名
     *
     * @param request
     * @return
     */
    public boolean rsaCheckV1(HttpServletRequest request) {
        // https://docs.open.alipay.com/54/106370
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        try {
            boolean verifyResult = AlipaySignature.rsaCheckV1(params,
                    aliPayProperties.getAlipayPublicKey(),
                    aliPayProperties.getCharset(),
                    aliPayProperties.getSignType());

            return verifyResult;
        } catch (AlipayApiException e) {
            logger.debug("verify sigin error, exception is:{}", e);
            return false;
        }
    }


}
