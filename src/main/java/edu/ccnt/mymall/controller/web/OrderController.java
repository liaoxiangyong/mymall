package edu.ccnt.mymall.controller.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import edu.ccnt.mymall.common.Const;
import edu.ccnt.mymall.common.ResponseCode;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by LXY on 2017/10/15.
 */
@Slf4j
@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping(value = "par.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("订单支付")
    public ServerResponse pay(HttpSession httpSession, Long orderNo, HttpServletRequest httpServletRequest){
        //1、验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }
        String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");
        return iOrderService.pay(user.getId(),orderNo,path);
    }


    @RequestMapping(value = "alipayCallBack.do")
    @ResponseBody
    @ApiOperation("支付宝回调")
    public Object alipayCallBack(HttpServletRequest httpServletRequest){
        Map<String,String> params = Maps.newHashMap();
        Map requestParameters = httpServletRequest.getParameterMap();

        for(Iterator iterator = requestParameters.keySet().iterator();iterator.hasNext();){
            String name = (String) iterator.next();
            String[] values = (String[]) requestParameters.get(name);
            String valueStr = "";
            for(int i=0;i<values.length;i++){
                valueStr = (i == values.length -1)?valueStr + values[i]:valueStr + values[i]+",";
            }
            params.put(name,valueStr);
        }
        log.info("支付宝回调,sign:{},trade_status:{},参数:{}",params.get("sign"),params.get("trade_status"),params.toString());

        params.remove("sign_type");

        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if(!alipayRSACheckedV2){
                return ServerResponse.createByErrorMessage("非法请求,验证不通过");
            }
        } catch (AlipayApiException e) {
            log.error("支付宝验证回调异常",e);
        }

        //todo验证各种数据

        ServerResponse serverResponse = iOrderService.aliCallback(params);
        if(serverResponse.isSuccess()){
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    @RequestMapping(value = "query_order_pay_status.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("查询订单状态")
    public ServerResponse<Boolean> queryOrderPayStatus(HttpSession session, Long orderNo){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),ResponseCode.NEED_LONGIN.getDesc());
        }
        ServerResponse serverResponse = iOrderService.queryOrderPayStatus(user.getId(),orderNo);
        if(serverResponse.isSuccess()){
            return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createBySuccess(false);
    }
}
