package com.example.alipay.service;

import com.alipay.api.domain.OrderItem;
import com.example.alipay.pojo.Order;
import com.example.alipay.pojo.Order_item;
import com.example.alipay.pojo.Pay_log;

import java.util.List;

/**
 * @Author: shanfeiyang
 * @Date: 2019/3/28 11:15
 * @Version 1.0
 */
public interface ManagerService {

    public List<Order> getAllOrder();

    Order_item getOrderItem(String outTradeNo);

    Pay_log getPayLog(String outTradeNo);
}
