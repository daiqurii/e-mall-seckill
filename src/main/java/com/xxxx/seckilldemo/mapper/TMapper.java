package com.xxxx.seckilldemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.seckilldemo.pojo.T;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author daizixiang
 * @since 2022-02-11
 */
public interface TMapper extends BaseMapper<T> {


    int insertUser(T user);
}
