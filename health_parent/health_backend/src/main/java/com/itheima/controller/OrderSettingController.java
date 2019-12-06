package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.POJO.OrderSetting;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author murongkang
 * @date 2019-12-05 15:14
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    //文件上传，实现预约设置数据批量导入
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")  MultipartFile excelFile){


        try {
            List<String[]> list = POIUtils.readExcel(excelFile); //使用POI解析表格数据
            if(list != null && list.size() > 0){
            List<OrderSetting> orderSettingList = new ArrayList<>();
            for (String[] strings : list) {
               String orderDate=strings[0];
               String number=strings[1];
                OrderSetting orderSetting = new OrderSetting(new Date(orderDate), Integer.parseInt(number));//转换类型
                orderSettingList.add(orderSetting);//把两列数据添加到集合中
            }
            //通过dubbo调用服务实现数据批量导入到数据库
            orderSettingService.add(orderSettingList);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            //文件解析失败
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

    }



    //根据日期的月份查询预约设置的数据，也就是当月的所以数据信息给展示出来
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){ //date格式为：yyyy-MM
        try{
            List<Map> list=orderSettingService.getOrderSettingByMonth(date); //这里封装成Map,是基于页面所要的值。
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list); //把数据返回

        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }



    //根据指定日期修改可预约人数
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try
        {orderSettingService.editNumberByDate(orderSetting);
//预约设置成功
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }
        catch (Exception e){ e.printStackTrace();
//预约设置失败
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
