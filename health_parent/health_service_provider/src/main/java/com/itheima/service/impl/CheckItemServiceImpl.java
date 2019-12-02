package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.POJO.CheckItem;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/*注意:配置中开启事务注解。我们要解决开启事务后不能发布服务的问题。
1.修改applicationContext-service.xml配置文件，开启事务控制注解支持时指定proxy-target-class属性，值为true。
 其作用是使用cglib代理方式为Service类创建代理对象

 2.修改HelloServiceImpl类，在Service注解中加入interfaceClass属性，值为HelloService.class，作用是指定服务的接口类型


interfaceClass ：指定提供服务实现的接口
*/

/**
 * @author murongkang
 * @date 2019-11-30 17:50
 */
//interfaceClass ：指定提供服务实现的接口
@Service(interfaceClass = CheckItemService.class)//发布服务必须使用Dubbo提供的Service注解,protocol指定使用的协议
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired  //注入dao
    private CheckItemDao checkItemDao;

    /*检查项添加*/
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /*检查项分页查询*/
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();//查询条件参数，需要自己查询，上面两个参数交给分页助手

        //分页查询基于mybatis的提供的分页助手插件
        PageHelper.startPage(currentPage,pageSize);  //底层基于线程绑定，把数据绑定到当前线程。
        Page<CheckItem> page = checkItemDao.findPage(queryString);
        long total = page.getTotal();  //分页助手会自动查询总页数
        List<CheckItem> result = page.getResult();
        return new PageResult(total,result);


    }

    /*检查项删除*/
    @Override
    public void deleteById(Integer id) {
        //判断当前检查项是否已经被关联到检查组
        long count = checkItemDao.findCountByCheckItem(id);
        if (count>0){
            //检查项数量大于0说明已经关联,不允许删除
             new RuntimeException();
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findById(Integer id) {
       return checkItemDao.findById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.upDate(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }


}
