package edu.ccnt.mymall.service;

import com.github.pagehelper.PageInfo;
import edu.ccnt.mymall.common.ServerResponse;

import java.util.Map;

/**
 * Created by LXY on 2017/10/15.
 */
public interface IOrderService {
    ServerResponse pay(Integer userId, Long orderNo, String path);

    ServerResponse aliCallback(Map<String,String> params);

    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);

    ServerResponse createOrder(Integer userId,Integer shippingId);

    ServerResponse cancelOrder(Integer userId,Long orderNo);

    ServerResponse getOrderItems(Integer userId);

    ServerResponse getOrderDetail(Integer userId,Long orderNo);

    ServerResponse<PageInfo> getOrderList(Integer userId, Integer pageNum, Integer pageSize);

    ServerResponse manageGetOrderDetail(Long orderNo);

    ServerResponse<PageInfo> manageGetOrderList(Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> manageSearchOrder(Long orderNo,Integer pageNum,Integer pageSize);

    ServerResponse sendGoods(Long orderNo);
}
