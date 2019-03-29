package com.example.alipay.controller;


import com.alipay.api.AlipayApiException;
import com.example.alipay.dao.OrderMapper;
import com.example.alipay.pojo.Order;
import com.example.alipay.pojo.ResultMessage;
import com.example.alipay.service.impl.AlipayF2FF2FServiceimpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @Author: shanfeiyang
 * @Date: 2019/3/11 14:52
 * @Version 1.0
 */
@Controller
@RequestMapping("/alipay")
public class AlipayF2FController {
    private static Logger logger = LoggerFactory.getLogger(AlipayF2FController.class);
    @Autowired
    private AlipayF2FF2FServiceimpl alipayF2FService;

    @Resource
    private OrderMapper orderMapper;


    @RequestMapping("/tofailepage")
    public String tofailepage(String out_trade_no) {
        //跳转支付失败页面前 再查询一遍订单支付状态，确保无误
        if (!StringUtils.isEmpty(out_trade_no)) {
            ResultMessage resultMessage = alipayF2FService.queryPayStatus(out_trade_no);
            if (resultMessage.getSuccess()) {
                return "paysuccess";
            } else if (!resultMessage.getSuccess()) {
                Order order = orderMapper.selectByPrimaryKey(out_trade_no);
                if (resultMessage.getMessage().equals("等待支付")) {
                    //调用关闭交易接口
                    alipayF2FService.close(out_trade_no);
                }
                logger.info("交易超时，需重新下单");
                //如果未在规定时间内完成支付，修改数据库订单状态
                order.setOrderStatus("5");
                order.setCloseTime(new Date());
                orderMapper.updateByPrimaryKey(order);
            }
        }
        return "payfaile";
    }

    @RequestMapping("/tosuceesspage")
    public String tosuceesspage() {
        return "paysuccess";
    }

    /**
     * 预创建，生成二维码
     *
     * @param
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/F2FPay")
    public ModelAndView precreate(Double price, Integer quantity, String goodsName, HttpServletResponse response) {
        //orderNo 内部订单号 用于查询订单商品数量 价格等
        ModelAndView mv = new ModelAndView();
        Map map = alipayF2FService.precreate(price, quantity, goodsName, response);
        mv.addObject("message", map.get("message"));
        mv.addObject("codeurl", map.get("codeurl"));
        mv.addObject("out_trade_no", map.get("out_trade_no"));
        mv.setViewName("alipay");
        return mv;
    }


    /**
     * 订单查询(最主要用于查询订单的支付状态)
     *
     * @param out_trade_no 商户订单号
     * @return
     */
    @GetMapping("/queryPayStatus")
    @ResponseBody
    public ResultMessage query(String out_trade_no) {
        return alipayF2FService.queryPayStatus(out_trade_no);
    }


    /**
     * 退款
     *
     * @param out_trade_no 商户订单号
     * @return
     */
    @GetMapping("/refund")
    @ResponseBody
    public ModelAndView refund(String out_trade_no)  {
        ResultMessage resultMessage = alipayF2FService.refund(out_trade_no);
        ModelAndView mv = new ModelAndView();
        mv.addObject("resultMessage", resultMessage);
        mv.setViewName("refundPage");
        return mv;
    }

    /**
     * 关闭交易
     *
     * @param out_trade_no
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("/close")
    @ResponseBody
    public ResultMessage close(String out_trade_no)  {
        return alipayF2FService.close(out_trade_no);
    }

    /**
     * 退款查询
     *
     * @param out_trade_no  商户订单号
     * @param refundOrderNo 请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部订单号
     * @return
     * @throws AlipayApiException
     */
    @GetMapping("/refundQuery")
    @ResponseBody
    public String refundQuery(String out_trade_no, String refundOrderNo)  {
        return alipayF2FService.refundQuery(out_trade_no, refundOrderNo);
    }

    /**
     * billDate : 账单时间：日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM。
     * 查询对账单下载地址: https://docs.open.alipay.com/api_15/alipay.data.dataservice.bill.downloadurl.query/
     *
     * @param billDate
     */
    @GetMapping("queryBill")
    @ResponseBody
    public void queryBill(String billDate) {
        alipayF2FService.queryBill(billDate);
    }
}
