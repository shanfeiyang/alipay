package com.example.alipay.dao;

import com.example.alipay.pojo.Pay_log;
import com.example.alipay.pojo.Pay_logExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Pay_logMapper {
    int countByExample(Pay_logExample example);

    int deleteByExample(Pay_logExample example);

    int deleteByPrimaryKey(String outTradeNo);

    int insert(Pay_log record);

    int insertSelective(Pay_log record);

    List<Pay_log> selectByExample(Pay_logExample example);

    Pay_log selectByPrimaryKey(String outTradeNo);

    int updateByExampleSelective(@Param("record") Pay_log record, @Param("example") Pay_logExample example);

    int updateByExample(@Param("record") Pay_log record, @Param("example") Pay_logExample example);

    int updateByPrimaryKeySelective(Pay_log record);

    int updateByPrimaryKey(Pay_log record);
}