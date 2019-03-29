package com.example.alipay.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: shanfeiyang
 * @Date: 2019/3/28 9:57
 * @Version 1.0
 */
public interface OauthService {
    Map<String, String> selleroauth(HttpServletRequest request);

    void saveInfo(HttpServletRequest request, String storeId);
}
