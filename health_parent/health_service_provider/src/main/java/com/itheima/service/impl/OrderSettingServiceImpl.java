package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.itheima.POJO.OrderSetting;
import com.itheima.dao.OrderSettingDao;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author murongkang
 * @date 2019-12-05 16:36
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    //写了3个查询数据库的方法。findCountByOrderDate：这个方法：查询数据库中是否已经存在改日期，如果次数大于0，就执行更新操作
    //editNumberByOrderDate：根据日期更新预约人数  //add：向表中根据字段添加数据
    @Override
    public void add(List<OrderSetting> list) {
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());//检查此数据（日期）是否存在
                if (count > 0) {
                    //已经存在，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                } else {         //不存在，执行添加操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    //根据日期查询预约设置数据
    @Override
    public List<Map> getOrderSettingByMonth(String date) { //date格式：2019-12
        String dateBegin = date + "-1";   //拼接开始日期,2019-12-1
        String dateEnd = date + "-31";   //2019-12-31
        Map<String, String> map = new HashMap<>();
        map.put("dateBegin", dateBegin); //把开始结束日期存到map中
        map.put("dateEnd", dateEnd);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);//调用dao查询数据库，返回值是list集合,
        List<Map> data = new ArrayList<>();   //创建list集合用于存储orderSettingMap里面的每条数据
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {     //    每个list里面的数据orderSetting:{ date: 1, number: 120, reservations: 1}
                Map orderSettingMap = new HashMap();

                orderSettingMap.put("date", orderSetting.getOrderDate().getDate()); //获取日(几号)
                orderSettingMap.put("number", orderSetting.getNumber()); //可预约人数
                orderSettingMap.put("reservations", orderSetting.getReservations()); //已预约人数
                data.add(orderSettingMap);
            }
        }
        return data;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        //根据日期查询是否已经进行了预约设置
        long count = orderSettingDao.findCountByOrderDate(orderDate);
        if(count > 0){
            //当前日期已经进行了预约设置，需要执行更新操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //当前日期没有就那些预约设置，需要执行插入操作
            orderSettingDao.add(orderSetting);
        }
    }
}

