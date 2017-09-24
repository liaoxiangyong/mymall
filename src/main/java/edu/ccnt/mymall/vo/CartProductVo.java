package edu.ccnt.mymall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by LXY on 2017/9/24.
 */
@Data
public class CartProductVo {

    private Integer id;
    private Integer userId;
    private Integer quantity;
    private Integer productId;
    private String productSubTitle;
    private String productName;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productStock;
    private Integer productChecked;//此商品是否勾选

    private String limitQuantity;//限制数量的一个返回结果
}
