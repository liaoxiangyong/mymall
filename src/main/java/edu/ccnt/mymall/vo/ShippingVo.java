package edu.ccnt.mymall.vo;

import lombok.Data;

/**
 * Created by LXY on 2017/10/22.
 */
@Data
public class ShippingVo {

    private String receiverName;        //收货人姓名

    private String receiverPhone;   //收货人移动电话

    private String receiverMobile; //收货人电话

    private String receiverProvince; //收货地址省份

    private String receiverCity; //收货地址城市

    private String receiverDistrict; //收货地址区县

    private String receiverAddress; //收货地址

    private String receiverZip; //收货地址邮编
}
