package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.POJO.CheckGroup;

import com.itheima.POJO.CheckItem;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.CheckGroupService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author murongkang
 * @date 2019-12-02 9:50
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

   @Reference  //查找服务
    private CheckGroupService checkGroupService;

   /*新增检查组*/
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){  //RequestBody解析前台提交过来的json数据，封装为指定对象
        try{
            checkGroupService.add(checkGroup,checkitemIds); //服务调用成功
        }catch (Exception e){
            e.printStackTrace();     //服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL); //提示失败信息
        }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS); //提示成功信息;
    }

    /*查询分页*/
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){  //RequestBody解析前台提交过来的json数据，封装为指定对象

         return checkGroupService.pageQuery(queryPageBean);
    }

    //编辑弹框检查组回显数据
    @RequestMapping("/findById")
    public Result findById(Integer id){  //RequestBody解析前台提交过来的json数据，封装为指定对象
        try{
                  CheckGroup checkGroup=checkGroupService.findById(id); //服务调用成功
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup); //提示成功信息;
        }catch (Exception e){
            e.printStackTrace();     //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL); //提示失败信息
        }

    }

    //编辑弹框检查项复选框回显
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){  //RequestBody解析前台提交过来的json数据，封装为指定对象
        try{
            List<Integer> checkitemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id); //服务调用成功
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkitemIds); //提示成功信息;
        }catch (Exception e){
            e.printStackTrace();     //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL); //提示失败信息
        }

    }

    @RequestMapping("/edit")
    public Result edit( @RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try{
            checkGroupService.edit(checkGroup,checkitemIds); //服务调用成功
        }catch (Exception e){
            e.printStackTrace();     //服务调用失败
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL); //提示编辑失败
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS); //提示编辑成功
    }
    /*检查组点击删除方法*/
    @RequestMapping("/deleteId")
    public Result delete( Integer id){
        try{
            checkGroupService.deleteById(id); //服务调用成功
        }catch (Exception e){
            e.printStackTrace();     //服务调用失败
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL); //提示删除失败
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS); //提示删除成功
    }
}
