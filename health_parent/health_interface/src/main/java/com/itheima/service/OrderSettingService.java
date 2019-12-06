package com.itheima.service;

import com.itheima.POJO.OrderSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author murongkang
 * @date 2019-12-05 16:26
 */
public interface OrderSettingService {


    void add(List<OrderSetting> list);

    List<Map> getOrderSettingByMonth(String date);//格式：2019-12

    void editNumberByDate(OrderSetting orderSetting);
}
