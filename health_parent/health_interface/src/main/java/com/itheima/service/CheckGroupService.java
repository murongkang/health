package com.itheima.service;

import com.itheima.POJO.CheckGroup;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author murongkang
 * @date 2019-12-02 10:11
 */

//提供服务接口
public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult pageQuery(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);


    List<Integer> findCheckItemIdsByCheckGroupId(@Param("id") Integer id);//根据检查组id，查询检查项id

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteById(Integer id);

    List<CheckGroup> findAll();
}
