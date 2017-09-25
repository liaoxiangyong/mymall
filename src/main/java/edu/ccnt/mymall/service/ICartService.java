package edu.ccnt.mymall.service;

import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.vo.CartVo;

/**
 * Created by LXY on 2017/9/24.
 */
public interface ICartService {

    ServerResponse addCartProduct(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> updateCartProduct(Integer userId, Integer productId, Integer count);
}
