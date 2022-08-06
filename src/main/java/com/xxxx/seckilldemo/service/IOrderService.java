package com.xxxx.seckilldemo.service;

import com.xxxx.seckilldemo.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckilldemo.pojo.T;
import com.xxxx.seckilldemo.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author daizixiang
 * @since 2022-02-13
 */
public interface IOrderService extends IService<Order> {

    Order seckill(T user, GoodsVo goodsVo);
}
