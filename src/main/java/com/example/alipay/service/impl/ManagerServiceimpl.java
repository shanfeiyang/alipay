package com.example.alipay.service.impl;


import com.example.alipay.configuration.AlipayProperties;
import com.example.alipay.dao.OrderMapper;
import com.example.alipay.dao.Order_itemMapper;
import com.example.alipay.dao.Pay_logMapper;
import com.example.alipay.dao.SellerMapper;
import com.example.alipay.pojo.*;
import com.example.alipay.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: shanfeiyang
 * @Date: 2019/3/28 11:15
 * @Version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ManagerServiceimpl implements ManagerService {

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private AlipayProperties alipayProperties;

    @Resource
    private SellerMapper sellerMapper;

    @Resource
    private Order_itemMapper order_itemMapper;

    @Resource
    private Pay_logMapper pay_logMapper;

    @Override
    public List<Order> getAllOrder() {
        String storeId = alipayProperties.getStoreId();
        Seller seller = sellerMapper.selectByStoreId(storeId);
        String sellerId = seller.getSellerId();
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria().andSellerIdEqualTo(sellerId);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        return orders;
    }

    @Override
    public Order_item getOrderItem(String outTradeNo) {
        return order_itemMapper.selectByOutTradeNo(outTradeNo);
    }

    @Override
    public Pay_log getPayLog(String outTradeNo) {
        return pay_logMapper.selectByPrimaryKey(outTradeNo);
    }
}
