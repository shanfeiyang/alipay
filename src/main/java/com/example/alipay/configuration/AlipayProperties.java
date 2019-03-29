package com.example.alipay.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: shanfeiyang
 * @Date: 2019/3/11 14:57
 * @Version 1.0
 */
@Data
@ConfigurationProperties(prefix = "pay.alipay")
public class AlipayProperties {
    //支付宝gatewayUrl
    private String gatewayUrl;
    // 商户应用id
    private String appid;
    // RSA私钥，用于对商户请求报文加签
    private String appPrivateKey;
    // 支付宝RSA公钥，用于验签支付宝应答
    private String alipayPublicKey;
    // 签名类型
    private String signType = "RSA2";
    // 格式
    private String formate = "json";
    //编码
    private String charset = "UTF-8";
    // 同步地址
    private String returnUrl;
    // 异步地址
    private String notifyUrl;
    //商户授权令牌
    private String app_auth_token;
    //商户门店id
    private String storeId;
    //接口名称
    private String method;
    //最大查询次数
    private static int maxQueryRetry = 5;
    //查询间隔（毫秒）
    private static long queryDuration = 5000;
    //最大撤销次数
    private static int maxCancelRetry = 3;
    //撤销间隔（毫秒
    private static long cancelDuration = 3000;


}
