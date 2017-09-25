package edu.ccnt.mymall.controller.web;

import edu.ccnt.mymall.common.Const;
import edu.ccnt.mymall.common.ResponseCode;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.ICartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by LXY on 2017/9/23.
 */
@RestController
@RequestMapping(value = "/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    /**
     * 在购物车中添加商品
     * @param httpSession
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping(value = "addCartProduct.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("在购物车中添加商品")
    public ServerResponse addCartProduct(HttpSession httpSession,Integer productId,Integer count){
        //验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }

        return iCartService.addCartProduct(user.getId(),productId,count);
    }

    @RequestMapping(value = "updateCartProduct.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("更新购物车中商品数量")
    public ServerResponse updateCartProduct(HttpSession httpSession,Integer productId,Integer count){
        //验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }

        return iCartService.updateCartProduct(user.getId(),productId,count);
    }


    @RequestMapping(value = "deleteCartProducts.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("删除购物车中商品")
    public ServerResponse deleteCartProducts(HttpSession httpSession,String products){
        //验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }
        return iCartService.deleteCartProducts(user.getId(),products);
    }

    @RequestMapping(value = "selectAll.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("全选购物车")
    public ServerResponse selectAll(HttpSession httpSession){
        //验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),Const.Cart.CHECKED,null);
    }

    @RequestMapping(value = "unSelectAll.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("全不选购物车")
    public ServerResponse unSelectAll(HttpSession httpSession){
        //验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),Const.Cart.UN_CHECKED,null);
    }

    @RequestMapping(value = "select.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("选择某项商品")
    public ServerResponse select(HttpSession httpSession,Integer productId){
        //验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),Const.Cart.CHECKED,productId);
    }

    @RequestMapping(value = "unSelect.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("反选某项商品")
    public ServerResponse unSelect(HttpSession httpSession,Integer productId){
        //验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),Const.Cart.UN_CHECKED,productId);
    }
}
