package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.POJO.Setmeal;

import java.util.Map;

/**
 * @author murongkang
 * @date 2019-12-04 16:03
 */
public interface SetmealDao {
    void add(Setmeal setmeal);//添加套餐信息

    void setSetmealAndCheckGroup(Map<String, Integer> map); //添加中间表：参数map:套餐的id和检查组的id


    Page<Setmeal> findPage(String queryString);
}
