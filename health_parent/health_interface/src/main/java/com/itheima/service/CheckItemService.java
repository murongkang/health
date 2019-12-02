package com.itheima.service;

import com.itheima.POJO.CheckItem;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author murongkang
 * @date 2019-11-30 16:53
 */

//服务接口
public interface CheckItemService {


    public void add(CheckItem checkItem); //添加检查项方法

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteById(@Param("id") Integer id);

    CheckItem findById(@Param("id") Integer id);

    void edit(CheckItem checkItem);


    List<CheckItem> findAll();

}
