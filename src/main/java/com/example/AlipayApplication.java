package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.alipay.dao")
@EnableTransactionManagement(proxyTargetClass = true)
public class AlipayApplication  {
	public static void main(String[] args) {
		SpringApplication.run(AlipayApplication.class, args);
	}
}
