package com.example.alipay.controller;

import com.alipay.api.domain.OrderItem;
import com.example.alipay.pojo.Order;
import com.example.alipay.pojo.Order_item;
import com.example.alipay.pojo.Pay_log;
import com.example.alipay.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: shanfeiyang
 * @Date: 2019/3/28 9:30
 * @Version 1.0
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @RequestMapping("/topage")
    public String topage() {
        return "manager";
    }

    @RequestMapping("/getAllOrder")
    @ResponseBody
    public List<Order> getAllOrder() {
        return managerService.getAllOrder();
    }

    @RequestMapping("/getOrderItem")
    @ResponseBody
    public Order_item getOrderItem(String outTradeNo) {
        return managerService.getOrderItem(outTradeNo);
    }

    @RequestMapping("/getPayLog")
    @ResponseBody
    public Pay_log getPayLog(String outTradeNo) {
        return managerService.getPayLog(outTradeNo);
    }
}
