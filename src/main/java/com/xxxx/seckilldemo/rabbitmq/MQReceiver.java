package com.xxxx.seckilldemo.rabbitmq;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xxxx.seckilldemo.pojo.SeckillMessage;
import com.xxxx.seckilldemo.pojo.SeckillOrder;
import com.xxxx.seckilldemo.pojo.T;
import com.xxxx.seckilldemo.service.IGoodsService;
import com.xxxx.seckilldemo.service.IOrderService;
import com.xxxx.seckilldemo.utils.JsonUtil;
import com.xxxx.seckilldemo.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
/**
 * @author zhoubin
 * @since 1.0.0
 */
@Service
@Slf4j
public class MQReceiver {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderService orderService;
    @RabbitListener(queues = "seckillQueue")
    public void receive(String msg) {
        log.info("QUEUE接受消息：" + msg);
        SeckillMessage message = JsonUtil.jsonStr2Object(msg,
                SeckillMessage.class);
        Long goodsId = message.getGoodsId();
        T user = message.getUser();
        GoodsVo goods = goodsService.findGoodsVoById(goodsId);
//判断库存
        if (goods.getStockCount() < 1) {
            return;
        }
//判断是否重复抢购
// SeckillOrder seckillOrder = seckillOrderService.getOne(new
        String seckillOrderJson = (String)redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (!StringUtils.isEmpty(seckillOrderJson)) {
            return;
        }
        orderService.seckill(user, goods);
    }
}

