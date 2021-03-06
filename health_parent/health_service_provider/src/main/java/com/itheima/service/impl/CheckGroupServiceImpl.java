package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.POJO.CheckGroup;

import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author murongkang
 * @date 2019-12-02 10:10
 */
@Service(interfaceClass = CheckGroupService.class) //指定接口名,发布服务必须使用Dubbo提供的Service注解
@Transactional //事务
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired //注入dao
    private CheckGroupDao checkGroupDao;


    /*新增检查组,同时让检查组关联检查项*/
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);//新增检查组

        //设置检查组和检查项的多对多关联关系，在操作中间表
        Integer checkGroupId = checkGroup.getId(); //获取检查组的id.
        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }

    //检查组分页
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();//查询条件参数，需要自己查询，上面两个参数交给分页助手

        //分页查询基于mybatis的提供的分页助手插件
        PageHelper.startPage(currentPage,pageSize);  //底层基于线程绑定，把数据绑定到当前线程。
        Page<CheckGroup> page = checkGroupDao.pageQuery(queryString);
        long total = page.getTotal();  //分页助手会自动查询总页数
        List<CheckGroup> result = page.getResult();
        return new PageResult(total,result);
    }

    @Override
    public CheckGroup findById(Integer id) {
       return   checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    //编辑修改检查组和检查项信息
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //修改检查组基本信息，操作检查组表
        checkGroupDao.edit(checkGroup);
        //清理当前检查组关联的检查项，操作中间关系表,(因为在修改复选框时，会发生变化，和原本绑定的值不同，所以要清理掉重新绑定）
        checkGroupDao.deleteAssoication(checkGroup.getId());
        //重新建立检查组和检查项的关联关系
        //设置检查组和检查项的多对多关联关系，在操作中间表
        Integer checkGroupId = checkGroup.getId(); //获取检查组的id.
        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }

    //检查组删除
    @Override
    public void deleteById(Integer id) {
//        checkGroupDao.findCountByCheckGroup(id);
        checkGroupDao.deleteById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return  checkGroupDao.findAll();

    }

    //抽取方法
    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds){
        if (checkitemIds!=null&&checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkGroup_Id",checkGroupId);
                map.put("checkitem_Id",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }


}
