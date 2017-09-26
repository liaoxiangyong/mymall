package edu.ccnt.mymall.service;

import com.github.pagehelper.PageInfo;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.Shipping;

/**
 * Created by LXY on 2017/9/26.
 */
public interface IShippingService {

    ServerResponse addShipping(Integer userId, Shipping shipping);

    ServerResponse updateShipping(Integer userId,Shipping shipping);

    ServerResponse deleteShipping(Integer userId,Integer shippingId);

    ServerResponse selectShipping(Integer userId,Integer shippingId);

    ServerResponse<PageInfo> listShippings(Integer userId, Integer pageNum, Integer pageSize);
}
