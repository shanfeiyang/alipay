package com.example.common.result;

import lombok.Getter;


@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10, "商品不存在"),

    ORDER_NOT_EXIST(12, "订单不存在"),

    ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),

    ORDER_STATUS_ERROR(14, "订单状态不正确"),

    ORDER_UPDATE_FAIL(15, "订单更新失败"),

    ORDER_DETAIL_EMPTY(16, "订单详情为空"),

    ORDER_PAY_STATUS_ERROR(17, "订单支付状态不正确"),

    ALIPAY_CONNECT_FAIL(18, "与支付宝服务器通讯失败"),

    ALIPAY_PRECREATE_FAIL(20, "订单预创建失败"),

    ALIPAY_NOTIFY_MONEY_VERIFY_ERROR(21, "支付宝异步通知金额校验不通过"),

    ALIPAY_ORDER_QUERY_FAIL(25, "订单查询失败"),

    ALIPAY_ORDER_REFUND_FAIL(26, "订单退款失败"),

    ALIPAY_ORDER_CLOSE_FAIL(27, "订单关闭失败"),

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
