package com.itheima.dao;

import com.itheima.POJO.OrderSetting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author murongkang
 * @date 2019-12-05 16:39
 */
public interface OrderSettingDao {


    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);


    List<OrderSetting> getOrderSettingByMonth(Map<String, String> date);
}
