package com.example.alipay.controller;

import com.example.alipay.service.OauthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;


/**
 * @Author: shanfeiyang
 * @Date: 2019/3/14 13:14
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/alioAuth")
public class OauthController {


    @Autowired
    private OauthService oauthService;

    @RequestMapping("/selleroAuth")
    public ModelAndView selleroAuth(HttpServletRequest request) {
        Map<String, String> map = oauthService.selleroauth(request);
        ModelAndView mv = new ModelAndView();
        mv.addObject("result", map);
        mv.setViewName("oAuth");
        return mv;
    }

    @RequestMapping("saveInfo")
    public ModelAndView saveInfo(HttpServletRequest request) {
        String storeId = request.getParameter("storeId");
        oauthService.saveInfo(request, storeId);
        return new ModelAndView(new RedirectView(
                "https://openauth.alipaydev.com/oauth2/appToAppAuth.htm?app_id=2016092800613370&redirect_uri=http%3a%2f%2f47.99.244.14%3a9000%2falioAuth%2fselleroAuth&storeId=" + storeId));

    }


}
