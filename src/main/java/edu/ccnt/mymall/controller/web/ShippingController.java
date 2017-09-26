package edu.ccnt.mymall.controller.web;

import edu.ccnt.mymall.common.Const;
import edu.ccnt.mymall.common.ResponseCode;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.Shipping;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.IShippingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by LXY on 2017/9/26.
 */
@RestController
@RequestMapping(value = "/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    @RequestMapping(value = "addShipping",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加收货地址")
    public ServerResponse addShipping(HttpSession session, Shipping shipping){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }
        return iShippingService.addShipping(user.getId(),shipping);
    }

    @RequestMapping(value = "updateShipping",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("更新收货地址")
    public ServerResponse updateShipping(HttpSession session, Shipping shipping){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }
        return iShippingService.updateShipping(user.getId(),shipping);
    }


    @RequestMapping(value = "deleteShipping",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("删除收货地址")
    public ServerResponse deleteShipping(HttpSession session, Integer shippingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }
        return iShippingService.deleteShipping(user.getId(),shippingId);
    }

    @RequestMapping(value = "selectShipping",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("查看收货地址详情")
    public ServerResponse selectShipping(HttpSession session, Integer shippingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }
        return iShippingService.selectShipping(user.getId(),shippingId);
    }

    @RequestMapping(value = "listShippings",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("列出所有收货地址")
    public ServerResponse listShippings(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }
        return iShippingService.listShippings(user.getId(),pageNum,pageSize);
    }
}
