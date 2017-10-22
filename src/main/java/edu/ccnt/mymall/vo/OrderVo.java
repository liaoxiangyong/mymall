package edu.ccnt.mymall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by LXY on 2017/10/22.
 */
@Data
public class OrderVo {

    private Long orderNo;       //订单号
    private BigDecimal payment; //订单金额
    private Integer paymentType;      //订单支付方式
    private String paymentTypeDesc; //订单支付方式描述
    private Integer postage;    //邮费
    private Integer status; //订单状态
    private String statusDesc;  //订单状态描述
    private String paymentTime;     //支付 时间
    private String sendTime;    //发货时间
    private String endTime;     //订单完成时间
    private String closeTime;   //订单关闭时间
    private String createTime;  //订单创建时间

    //订单的明细
    private List<OrderItemVo> orderItemVoList;

    private Integer shippingId;     //收货地址id
    private String receiverName;        //收货人姓名

    private ShippingVo shippingVo;      //收货地址vo
}
