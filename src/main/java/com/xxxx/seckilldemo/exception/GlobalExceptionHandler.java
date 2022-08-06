package com.xxxx.seckilldemo.exception;

import com.xxxx.seckilldemo.vo.RespBean;
import com.xxxx.seckilldemo.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e){
        if(e instanceof GlobalException){
            GlobalException e1 = (GlobalException) e;
            return RespBean.error(e1.getRespBeanEnum());
        }else  if(e instanceof BindException){
            BindException e1 = (BindException) e;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常:"+e1.getAllErrors().get(0).getDefaultMessage());
            return respBean;

        }
        log.debug(e.getMessage());
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
