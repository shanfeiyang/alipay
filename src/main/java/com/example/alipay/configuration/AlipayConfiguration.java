package com.example.alipay.configuration;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: shan feiyang
 * @Date: 2019/3/11 15:14
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties(AlipayProperties.class)
public class AlipayConfiguration {
    @Autowired
    AlipayProperties alipayProperties;

    @Bean
    public AlipayTradeService alipayTradeService() {
        return new AlipayTradeServiceImpl.ClientBuilder()
                .setGatewayUrl(alipayProperties.getGatewayUrl())
                .setAppid(alipayProperties.getAppid())
                .setPrivateKey(alipayProperties.getAppPrivateKey())
                .setAlipayPublicKey(alipayProperties.getAlipayPublicKey())
                .setSignType(alipayProperties.getSignType())
                .build();
    }
    @Bean
    public AlipayClient alipayClient(){
        return new DefaultAlipayClient(alipayProperties.getGatewayUrl(),
                alipayProperties.getAppid(),
                alipayProperties.getAppPrivateKey(),
                alipayProperties.getFormate(),
                alipayProperties.getCharset(),
                alipayProperties.getAlipayPublicKey(),
                alipayProperties.getSignType());
    }

}
