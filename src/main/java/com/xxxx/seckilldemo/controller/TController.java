package com.xxxx.seckilldemo.controller;


import com.xxxx.seckilldemo.pojo.T;
import com.xxxx.seckilldemo.rabbitmq.MQSender;
import com.xxxx.seckilldemo.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author daizixiang
 * @since 2022-02-11
 */
@Controller
@RequestMapping("/user")
public class TController {

    @Autowired
    private MQSender mqSender;
    /**
     * 系统测试
     * @param user
     * @return
     */
    @RequestMapping("/info")
    //返回的是json的时候要再加一个@ResponseBody
    @ResponseBody
    public RespBean info(T user){
        return RespBean.success(user);
    }


    /**
     * 测试发送RabbitMQ消息
     */
    @RequestMapping("/mq")
    @ResponseBody
    public void mq() {
        mqSender.send("Hello");
    }

}
