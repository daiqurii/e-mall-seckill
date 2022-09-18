package com.xxxx.seckilldemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author Daiquiri
 * @version 1.0
 * @date 2022/9/18 22:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillMessage {
    private T user;
    private Long goodsId;
}
