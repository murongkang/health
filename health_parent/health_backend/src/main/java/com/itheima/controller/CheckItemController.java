package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.POJO.CheckItem;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;

import com.itheima.service.CheckItemService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author murongkang
 * @date 2019-11-30 16:29
 */

//检查项管理
@RestController  //这个注解包括Controller，同时相当于加了@RequestBody,让方法返回java对象，框架自动把它转为json,通过输出流往回写
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference //查找服务
    private CheckItemService CheckItemService;


    //Result？？？为什么返回值是它？什么作用？.因为Result里面封装了错误信息属性
    @RequestMapping("/add")
     public Result add(@RequestBody CheckItem checkItem){  //RequestBody解析前台提交过来的json数据，封装为指定对象
        try{
            CheckItemService.add(checkItem); //服务调用成功
        }catch (Exception e){
                e.printStackTrace();     //服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL); //提示新增检查项失败信息
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS); //提示成功信息;
    }

    /*检查项分页查询*/
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){  //RequestBody解析前台提交过来的json数据，封装为指定对象
        return CheckItemService.findPage(queryPageBean);
    }

    /*检查项点击删除方法*/
    @RequestMapping("/deleteId")
    public Result delete( Integer id){
        try{
            CheckItemService.deleteById(id); //服务调用成功
        }catch (Exception e){
            e.printStackTrace();     //服务调用失败
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL); //提示删除失败
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS); //提示删除成功
    }

    /*检查项编辑回显查询方法*/
    @RequestMapping("/findById")
    public Result findById( Integer id){
        try{
          CheckItem checkItem=CheckItemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem); //提示查询显示成功  .容易出现Bug，我忘记返回对象了
        }catch (Exception e){
            e.printStackTrace();     //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL); //提示查询显示失败
        }

    }

    /*检查项编辑提交修改方法*/
    @RequestMapping("/edit")
    public Result edit( @RequestBody CheckItem checkItem){
        try{
            CheckItemService.edit(checkItem); //服务调用成功
        }catch (Exception e){
            e.printStackTrace();     //服务调用失败
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL); //提示编辑失败
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS); //提示编辑成功
    }

    //检查组查询所有
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<CheckItem> checkItemList = CheckItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemList); //提示查询显示成功
        }catch (Exception e){
            e.printStackTrace();     //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL); //提示查询显示失败
        }
    }

 }
