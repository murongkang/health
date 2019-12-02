package com.itheima.service;

import com.itheima.POJO.CheckGroup;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;

/**
 * @author murongkang
 * @date 2019-12-02 10:11
 */

//提供服务接口
public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult pageQuery(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);


}
