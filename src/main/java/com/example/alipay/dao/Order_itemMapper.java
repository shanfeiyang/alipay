package com.example.alipay.dao;

import com.example.alipay.pojo.Order_item;
import com.example.alipay.pojo.Order_itemExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Order_itemMapper {
    int countByExample(Order_itemExample example);

    int deleteByExample(Order_itemExample example);

    int deleteByPrimaryKey(Long itemId);

    int insert(Order_item record);

    int insertSelective(Order_item record);

    List<Order_item> selectByExample(Order_itemExample example);

    Order_item selectByPrimaryKey(Long itemId);

    Order_item selectByOutTradeNo(String outTradeNo);

    int updateByExampleSelective(@Param("record") Order_item record, @Param("example") Order_itemExample example);

    int updateByExample(@Param("record") Order_item record, @Param("example") Order_itemExample example);

    int updateByPrimaryKeySelective(Order_item record);

    int updateByPrimaryKey(Order_item record);
}