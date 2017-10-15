package edu.ccnt.mymall.service;

import edu.ccnt.mymall.common.ServerResponse;

import java.util.Map;

/**
 * Created by LXY on 2017/10/15.
 */
public interface IOrderService {
    ServerResponse pay(Integer userId, Long orderNo, String path);
    ServerResponse aliCallback(Map<String,String> params);
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);
}
