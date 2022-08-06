package com.xxxx.seckilldemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.seckilldemo.exception.GlobalException;
import com.xxxx.seckilldemo.mapper.TMapper;
import com.xxxx.seckilldemo.pojo.T;
import com.xxxx.seckilldemo.service.ITService;
import com.xxxx.seckilldemo.utils.CookieUtil;
import com.xxxx.seckilldemo.utils.MD5UTil;
import com.xxxx.seckilldemo.utils.UUIDUtil;
import com.xxxx.seckilldemo.vo.LoginVo;
import com.xxxx.seckilldemo.vo.RespBean;
import com.xxxx.seckilldemo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author daizixiang
 * @since 2022-02-11
 */
@Service
public class TServiceImpl extends ServiceImpl<TMapper, T> implements ITService {
    /**
     * 登录逻辑
     * @param loginVo  里面是登录的手机号和MD5一次加密后的密码
     * @return
     */
    @Autowired
    private TMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //判断手机号码密码不能为空
        /*if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            //虽然前端已经做了校验 后端还是要做校验的
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        //手机号码校验  使用ValidatorUtil工具类实现
        if (!ValidatorUtil.isMobile(mobile)) {
            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        }*/
        T user = userMapper.selectById(mobile);
        if(null == user){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //判断密码是否正确
        if(!MD5UTil.fromPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //登录成功
        //生成cookie
        String ticket = UUIDUtil.uuid();
        //直接将用户信息存入到redis中
        redisTemplate.opsForValue().set("user"+ticket,user);
        //使用SPringSession的方式 将Session存入到redis中 以二进制形式 jdk
        //request.getSession().setAttribute(ticket,user);
        CookieUtil.setCookie(request,response,"userTicket",ticket);
        return RespBean.success(ticket);
    }

    @Override
    public T getUserByCookie(String ticket ,HttpServletRequest request,HttpServletResponse response) {
        if(StringUtils.isEmpty(ticket)){
            return null;
        }
        T user = (T) redisTemplate.opsForValue().get("user" + ticket);
        if(null != user){
            CookieUtil.setCookie(request,response,"userTicket",ticket);
        }
        return user;
    }
}
