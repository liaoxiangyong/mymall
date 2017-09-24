package edu.ccnt.mymall.service;

import edu.ccnt.mymall.common.ServerResponse;

/**
 * Created by LXY on 2017/9/24.
 */
public interface ICartService {

    ServerResponse addCartProduct(Integer userId, Integer productId, Integer count);
}
