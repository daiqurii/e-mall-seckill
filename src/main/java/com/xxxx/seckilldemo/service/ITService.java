package com.xxxx.seckilldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckilldemo.pojo.T;
import com.xxxx.seckilldemo.vo.LoginVo;
import com.xxxx.seckilldemo.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author daizixiang
 * @since 2022-02-11
 */
public interface ITService extends IService<T> {
    /**
     * 登录
     * @param loginVo
     * @param request
     * @param response
     * @return
     */
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据cookie获取用户
     * @param userTicket
     * @return
     */
    T getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response);
}
