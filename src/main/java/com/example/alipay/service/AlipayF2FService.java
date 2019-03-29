package com.example.alipay.service;

import com.alipay.api.AlipayApiException;
import com.example.alipay.pojo.ResultMessage;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: shanfeiyang
 * @Date: 2019/3/28 9:36
 * @Version 1.0
 */
public interface AlipayF2FService {
    /**
     * 预创建
     *
     * @param price
     * @param quantity
     * @param goodsName
     * @param response
     * @return
     */
    public Map precreate(Double price, Integer quantity, String goodsName, HttpServletResponse response);

    /**
     * 查询订单状态
     *
     * @param out_trade_no
     * @return
     */
    public ResultMessage queryPayStatus(String out_trade_no);

    /**
     * 退款
     *
     * @param out_trade_no
     * @return
     */
    public ResultMessage refund(String out_trade_no);

    /**
     * 关闭交易
     *
     * @param out_trade_no
     * @return
     */
    public ResultMessage close(String out_trade_no);

    /**
     * 退款查询
     *
     * @param out_trade_no
     * @param refundOrderNo
     * @return
     */
    public String refundQuery(String out_trade_no, String refundOrderNo);

    /**
     * 对账单查询
     *
     * @param billDate
     */
    public void queryBill(String billDate);
}
