package com.xxxx.seckilldemo.vo;

import com.xxxx.seckilldemo.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo extends Goods {
    //秒杀价格
    private BigDecimal seckillPrice;

    //库存数量
    private  Integer stockCount;
    //开始和结束时间
    private Date startDate;
    private Date endDate;

}
