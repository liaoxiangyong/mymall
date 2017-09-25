package edu.ccnt.mymall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by LXY on 2017/9/24.
 */
@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;
    private Boolean isAllChecked;       //商品是否全选
    private BigDecimal cartTotalPrice;      //购物车总价
    private Integer selectQuantity;     //选择的商品数量
}
