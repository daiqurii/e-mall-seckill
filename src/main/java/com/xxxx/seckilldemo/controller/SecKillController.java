package com.xxxx.seckilldemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.seckilldemo.pojo.Order;
import com.xxxx.seckilldemo.pojo.SeckillOrder;
import com.xxxx.seckilldemo.pojo.T;
import com.xxxx.seckilldemo.service.IGoodsService;
import com.xxxx.seckilldemo.service.IOrderService;
import com.xxxx.seckilldemo.service.ISeckillOrderService;
import com.xxxx.seckilldemo.vo.GoodsVo;
import com.xxxx.seckilldemo.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seckill")
public class SecKillController {

    @Autowired
    private IGoodsService goodsService;
    //查秒杀订单的
    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private IOrderService orderService;

    /**
     * 秒杀
     *  Windows 优化前 qps 686
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    //简化秒杀过程 秒杀到商品之后直接跳到订单的详情页面
    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, T user, Long goodsId){
        if(user == null){
            return "login";
        }
        model.addAttribute("user",user);
        //根据商品id查一下商品的库存够不够  不能相信前端的数据
        GoodsVo goodsVo = goodsService.findGoodsVoById(goodsId);
        if(goodsVo.getStockCount() <1){
            //没有库存了
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        //判断是否重复抢购  查找有没有id对应的订单
        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if(seckillOrder != null){
            //不为空说明已经抢过了
            model.addAttribute("errmsg",RespBeanEnum.REPEAT_ERROR.getMessage());
            return "secKillFail";
        }
        //生成订单
        Order order = orderService.seckill(user, goodsVo);
        model.addAttribute("order",order);
        model.addAttribute("goods",goodsVo);
        return "orderDetail";

    }
}
