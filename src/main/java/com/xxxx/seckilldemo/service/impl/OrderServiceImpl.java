package com.xxxx.seckilldemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxxx.seckilldemo.pojo.Order;
import com.xxxx.seckilldemo.mapper.OrderMapper;
import com.xxxx.seckilldemo.pojo.SeckillGoods;
import com.xxxx.seckilldemo.pojo.SeckillOrder;
import com.xxxx.seckilldemo.pojo.T;
import com.xxxx.seckilldemo.service.IGoodsService;
import com.xxxx.seckilldemo.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.seckilldemo.service.ISeckillGoodsService;
import com.xxxx.seckilldemo.service.ISeckillOrderService;
import com.xxxx.seckilldemo.utils.JsonUtil;
import com.xxxx.seckilldemo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author daizixiang
 * @since 2022-02-13
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 生成秒杀订单
     * @param user
     * @param goodsVo
     * @return
     */
    @Override
    @Transactional  //使用注解
    public Order seckill(T user, GoodsVo goodsVo) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //减库存
        SeckillGoods goods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goodsVo.getId()));
        goods.setStockCount(goods.getStockCount()-1);
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new
                QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
        boolean seckillGoodsResult = seckillGoodsService.update( new UpdateWrapper<SeckillGoods>().setSql("stock_count = stock_count-1").eq("goods_id", goods.getId()).gt("stock_count", 0));
        if (seckillGoods.getStockCount() < 1) {
            //判断是否还有库存
            valueOperations.set("isStockEmpty:" + goods.getId(), "0");
            return null;
        }
        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goodsVo.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(goodsVo.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);

        //生成秒杀订单 写库
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrderService.save(seckillOrder);

        /*将订单放入redis当中  包含用户的id 和商品id*/
        valueOperations.set("order:" + user.getId() + ":" + goods.getId(), JsonUtil.object2JsonStr(seckillOrder));
        return order;
    }
}
