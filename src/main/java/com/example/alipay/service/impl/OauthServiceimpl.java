package com.example.alipay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayOpenAuthTokenAppModel;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.example.alipay.dao.SellerMapper;
import com.example.alipay.pojo.Seller;
import com.example.alipay.service.OauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: shanfeiyang
 * @Date: 2019/3/28 9:57
 * @Version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OauthServiceimpl implements OauthService {
    private static Logger logger = LoggerFactory.getLogger(OauthServiceimpl.class);

    @Autowired
    private AlipayClient alipayClient;

    @Resource
    private SellerMapper sellerMapper;

    @Override
    public Map<String, String> selleroauth(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        //获取appID
        String app_id = request.getParameter("app_id");
        //获取授权码app_auth_code
        String app_auth_code = request.getParameter("app_auth_code");
        //获取商家标识
        String storeId = request.getParameter("storeId");
        //调用获取授权令牌接口
        AlipayOpenAuthTokenAppRequest tokenAppRequest = new AlipayOpenAuthTokenAppRequest();
        AlipayOpenAuthTokenAppModel model = new AlipayOpenAuthTokenAppModel();
        //1.authorization_code表示换取app_auth_token。
        //2.refresh_token表示刷新app_auth_token
        model.setGrantType("authorization_code");
        //设置授权码
        model.setCode(app_auth_code);
        tokenAppRequest.setBizModel(model);
        try {
            AlipayOpenAuthTokenAppResponse response = alipayClient.execute(tokenAppRequest);
            String tokenbody = response.getBody();
            logger.info(tokenbody);
            //获取授权令牌
            String appAuthToken = response.getAppAuthToken();
            String userId = response.getUserId();
            //将授权信息存入数据库  code+token+商家信息（userid+自定义参数state等）
            /*商家信息是让商家输入还是我们事先录入？代商家签约时会收集商家信息，
            所以可以事先录入好签约商家的信息，然后只需更新各个商家的授权令牌即可*/
            Seller seller = sellerMapper.selectByStoreId(storeId);
            seller.setAppAuthToken(appAuthToken);
            seller.setStatus("1");
            seller.setSellerId(userId);
            String name = seller.getName();
            sellerMapper.updateByPrimaryKey(seller);
            map.put("appAuthToken", appAuthToken);
            map.put("tokenbody", tokenbody);
            map.put("name", name);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void saveInfo(HttpServletRequest request, String storeId) {
//        String sellerId = request.getParameter("sellerId");

        String name = request.getParameter("name");
        String linkmanName = request.getParameter("linkmanName");
        String linkmanMobile = request.getParameter("linkmanMobile");
        String linkmanEmail = request.getParameter("linkmanEmail");
        String licenseNumber = request.getParameter("licenseNumber");
        String address = request.getParameter("address");
        Seller seller = new Seller();
        seller.setStoreId(storeId);
        seller.setName(name);
        seller.setLinkmanName(linkmanName);
        seller.setLinkmanMobile(linkmanMobile);
        seller.setLinkmanEmail(linkmanEmail);
        seller.setLicenseNumber(licenseNumber);
        seller.setAddress(address);
        seller.setCreateTime(new Date());
        seller.setStatus("0");
        sellerMapper.insert(seller);
    }
}
