package edu.ccnt.mymall.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDetailVo {

    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String createTime;
    private String updateTime;

    private String imageHost;      //图片在网络上的地址前缀
    private Integer parentCategoryId;

}
