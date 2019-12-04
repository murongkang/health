package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.POJO.Setmeal;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * @author murongkang
 * @date 2019-12-04 11:29
 */
//体检套餐管理
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    //使用JedisPool操作Jedis服务
    @Autowired
    private JedisPool jedisPool;

    @Reference //查找服务
    private SetmealService setmealService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) { //指定请求参数 图片文件imgFile
//        System.out.println(imgFile);
        String originalFilename = imgFile.getOriginalFilename(); //这里是获取的原始文件名，也就是你上传的文件的名字如a.jpg
        int index = originalFilename.lastIndexOf(".");//获取文件名最后一个.所在的位置
        String extension = originalFilename.substring(index - 1);//截取后结果如：jpg  而index-1:是获取到后缀名： .jpg
        String fileName = UUID.randomUUID().toString() + extension;//用UUId产生一个随机的文件名 如：xgsfhabhifbia  并且要加上文件后缀如(.jpg),需要获取图片后缀
        //使用七牛云工具类,将文件上传到七牛云服务器
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);  //fileName:上传文件的文件名，不能重复，重复会覆盖，所以用uuid产生随机名
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);//图片上传到七牛云的同时，将图片名称保存到redis的一个集合中
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName); //上传成功,并且把文件名返回，如果文件不能预览，就是这里没返回

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL); //上传失败
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.add(setmeal, checkgroupIds); //服务调用成功
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS); //提示成功信息;
        } catch (Exception e) {
            e.printStackTrace();     //服务调用失败
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL); //提示失败信息
        }
    }

    /*检查项分页查询*/
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){  //RequestBody解析前台提交过来的json数据，封装为指定对象
        return setmealService.findPage(queryPageBean);
    }
}
