package edu.ccnt.mymall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by LXY on 2017/10/22.
 */
@Data
public class OrderItemVo {
    private Long orderNo;       //订单号

    private Integer productId;      //商品id

    private String productName; //商品名
    private String productImage; //商品主图片

    private BigDecimal currentUnitPrice;    //下单时价格

    private Integer quantity;   //购买数量

    private BigDecimal totalPrice;  //总价

    private String createTime;  //创建时间
}
