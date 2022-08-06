package com.xxxx.seckilldemo.service;

import com.xxxx.seckilldemo.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.seckilldemo.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author daizixiang
 * @since 2022-02-13
 */
public interface IGoodsService extends IService<Goods> {
    /**
     * 获取商品列表
     * @return
     */
    List<GoodsVo> findGoodsVo();

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    GoodsVo findGoodsVoById(Long goodsId);
}
