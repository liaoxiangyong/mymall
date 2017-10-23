package edu.ccnt.mymall.controller.backend;

import edu.ccnt.mymall.common.Const;
import edu.ccnt.mymall.common.ResponseCode;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.IOrderService;
import edu.ccnt.mymall.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by LXY on 2017/10/23.
 */
@RestController
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping(value = "manageGetOrderDetail.do",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("查看订单详情")
    public ServerResponse manageGetOrderDetail(HttpSession httpSession,Long orderNo){
        //1、验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"管理员未登录");
        }
        //2、验证是否管理员
        if(iUserService.checkUserAdmin(user).isSuccess()){
            //3、业务逻辑
            return iOrderService.manageGetOrderDetail(orderNo);
        }else{
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }


    @RequestMapping(value = "manageGetOrderList.do",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取订单列表")
    public ServerResponse manageGetOrderList(HttpSession httpSession,@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        //1、验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"管理员未登录");
        }
        //2、验证是否管理员
        if(iUserService.checkUserAdmin(user).isSuccess()){
            //3、业务逻辑
            return iOrderService.manageGetOrderList(pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }

    @RequestMapping(value = "manageSearchOrder.do",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取订单列表,这里暂时为按订单号精确查找")
    public ServerResponse manageSearchOrder(HttpSession httpSession,Long orderNo,@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        //1、验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"管理员未登录");
        }
        //2、验证是否管理员
        if(iUserService.checkUserAdmin(user).isSuccess()){
            //3、业务逻辑
            return iOrderService.manageSearchOrder(orderNo,pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }

    @RequestMapping(value = "sendGoods.do",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("发货")
    public ServerResponse sendGoods(HttpSession httpSession,Long orderNo){
        //1、验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"管理员未登录");
        }
        //2、验证是否管理员
        if(iUserService.checkUserAdmin(user).isSuccess()){
            //3、业务逻辑
            return iOrderService.sendGoods(orderNo);
        }else{
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }

}
