package edu.ccnt.mymall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by LXY on 2017/10/23.
 */
@Data
public class OrderProductVo {

    private List<OrderItemVo> list;     //orderItem列表

    private BigDecimal totalPrice;      //订单总价
}
