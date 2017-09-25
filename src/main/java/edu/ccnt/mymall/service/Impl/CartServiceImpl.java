package edu.ccnt.mymall.service.Impl;

import com.google.common.base.Splitter;
import edu.ccnt.mymall.common.Const;
import edu.ccnt.mymall.common.ResponseCode;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.dao.CartMapper;
import edu.ccnt.mymall.dao.ProductMapper;
import edu.ccnt.mymall.model.Cart;
import edu.ccnt.mymall.model.Product;
import edu.ccnt.mymall.service.ICartService;
import edu.ccnt.mymall.util.BigDecimalUtil;
import edu.ccnt.mymall.vo.CartProductVo;
import edu.ccnt.mymall.vo.CartVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service(value = "iCartService")
@Slf4j
public class CartServiceImpl implements ICartService{

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    public ServerResponse addCartProduct(Integer userId,Integer productId,Integer count){
        log.info("购物车添加商品");
        //1、判断购物车是否已有改产品，如果没有就添加，有的话数量相加
        Cart cart = cartMapper.findCartByUserIdAndProductId(userId,productId);
        if(cart == null){
            Cart cartItem = new Cart();
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartItem.setQuantity(count);
            cartMapper.insert(cartItem);
        }else{
            count = count + cart.getQuantity();
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }


        return this.list(userId);
    }


    public ServerResponse<CartVo> updateCartProduct(Integer userId,Integer productId,Integer count) {
        log.info("更新购物车商品数量");
        if(productId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGLE_ARGUMENT.getCode(),ResponseCode.ILLEGLE_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.findCartByUserIdAndProductId(userId,productId);
        if(cart != null){
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return this.list(userId);
    }


    public ServerResponse<CartVo> deleteCartProducts(Integer userId,String products){
        log.info("删除购物车中商品");
        List<String> productList = Splitter.on(".").splitToList(products);
        if(CollectionUtils.isEmpty(productList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGLE_ARGUMENT.getCode(),ResponseCode.ILLEGLE_ARGUMENT.getDesc());
        }
        //删除购物车中商品
        cartMapper.deleteCartByProducts(userId,productList);
        return this.list(userId);
    }

    public ServerResponse<CartVo> list (Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }


    public ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer check,Integer productId){
        log.info("购物车选择与不选");
        cartMapper.selectOrUnSelect(userId,check,productId);
        return this.list(userId);
    }

    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        List<Cart> cartList = cartMapper.findCartByUserId(userId);

        BigDecimal cartTotalPrice = new BigDecimal("0");        //购物车总价
        int selectQuantity = 0;
        if(!CollectionUtils.isEmpty(cartList)){
            for(Cart cart : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cart.getId());
                cartProductVo.setUserId(cart.getUserId());
                cartProductVo.setProductId(cart.getProductId());

                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if(product != null){
                    cartProductVo.setProductSubTitle(product.getSubtitle());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductName(product.getName());
                }

                //判断库存，若超出库存数量则用库存当做数量
                int limitQuantity = 0;
                if(product.getStock()>=cart.getQuantity()){
                    limitQuantity = cart.getQuantity();
                    cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                }else{
                    limitQuantity = product.getStock();
                    cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                }
                cartProductVo.setQuantity(limitQuantity);

                //计算购物车该商品的总价
                BigDecimal productTotalPrice = BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity().doubleValue());
                cartProductVo.setProductTotalPrice(productTotalPrice);
                cartProductVo.setProductChecked(cart.getChecked());
                if(cart.getChecked() == Const.Cart.CHECKED){
                    //如果已经勾选,增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                    selectQuantity += cart.getQuantity();
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setIsAllChecked(isAllChecked(userId));
        cartVo.setSelectQuantity(selectQuantity);
        return cartVo;
    }

    private boolean isAllChecked(Integer userId){
        //判断是否全部被选中
        if(userId == null) return  false;
        return cartMapper.isAllChecked(userId) == 0;
    }
}
