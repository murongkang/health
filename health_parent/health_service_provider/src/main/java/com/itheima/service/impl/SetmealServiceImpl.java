package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.POJO.CheckItem;
import com.itheima.POJO.Setmeal;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author murongkang
 * @date 2019-12-04 15:47
 */

//体检套餐服务
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private SetmealDao setmealDao;
    //添加套餐信息和关联检查组
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);//添加套餐信息后，套餐表中会自动多出一个主键id,我们需要获取这个id来关联检查组的id
        Integer setmealId = setmeal.getId(); //获取套餐表中自动增加的id
        this.setSetmealAndCheckGroup(setmealId,checkgroupIds);
        //将图片名称保存到redis集合中
        String filename = setmeal.getImg(); //获取文件名
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,filename);

    }

    /*套餐管理分页查询*/
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();//查询条件参数，需要自己查询，上面两个参数交给分页助手

        //分页查询基于mybatis的提供的分页助手插件
        PageHelper.startPage(currentPage,pageSize);  //底层基于线程绑定，把数据绑定到当前线程。
        Page<Setmeal> page = setmealDao.findPage(queryString);
        long total = page.getTotal();  //分页助手会自动查询总页数
        List<Setmeal> result = page.getResult();
        return new PageResult(total,result);
    }

    //抽取方法:关联关系
    public void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkgroupIds){
        if (checkgroupIds!=null&&checkgroupIds.length>0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmeal_id",setmealId);
                map.put("checkgroup_Id",checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }
}
