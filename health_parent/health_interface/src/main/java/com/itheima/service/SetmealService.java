package com.itheima.service;

import com.itheima.POJO.Setmeal;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;

/**
 * @author murongkang
 * @date 2019-12-04 15:39
 */
public interface SetmealService {

    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);
}
