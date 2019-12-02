package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.POJO.CheckGroup;
import com.itheima.POJO.CheckItem;

import java.util.Map;

/**
 * @author murongkang
 * @date 2019-12-02 10:26
 */
public interface CheckGroupDao {  //记住创建接口的实现xml

    void add( CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map<String,Integer> map);


    Page<CheckGroup> pageQuery(String queryString);


    CheckGroup findById(Integer id);


}
