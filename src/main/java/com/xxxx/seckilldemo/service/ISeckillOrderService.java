package com.xxxx.seckilldemo.service;

import com.xxxx.seckilldemo.pojo.SeckillOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckilldemo.pojo.T;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author daizixiang
 * @since 2022-02-13
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {
    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    Long getResult(T user, Long goodsId);
}
