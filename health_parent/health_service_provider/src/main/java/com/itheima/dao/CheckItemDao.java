package com.itheima.dao;


import com.github.pagehelper.Page;
import com.itheima.POJO.CheckItem;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;

import java.util.List;

/**
 * @author murongkang
 * @date 2019-11-30 18:06
 */
//基于动态代理产生实现类

public interface CheckItemDao {
    void add(CheckItem checkItem);

    Page<CheckItem> findPage(String queryPageBean);


    void deleteById(Integer id);
    long findCountByCheckItem(Integer id);

    void upDate(CheckItem checkItem);


    CheckItem findById(Integer id); //回显查询

    List<CheckItem> findAll();
}
