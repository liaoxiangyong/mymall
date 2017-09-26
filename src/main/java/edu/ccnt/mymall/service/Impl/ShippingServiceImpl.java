package edu.ccnt.mymall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.dao.ShippingMapper;
import edu.ccnt.mymall.model.Shipping;
import edu.ccnt.mymall.service.IShippingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXY on 2017/9/26.
 */
@Service(value = "iShippingService")
@Slf4j
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    public ServerResponse addShipping(Integer userId, Shipping shipping){
        log.info("添加收货地址");
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if(rowCount > 0){
            Map result = new HashMap();
            result.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("添加收货地址成功",result);
        }
        return ServerResponse.createByErrorMessage("添加收货地址失败");
    }

    public ServerResponse updateShipping(Integer userId,Shipping shipping){
        log.info("更新收货地址");
        shipping.setUserId(userId);     //防止越权
        int rowCount = shippingMapper.updateByShipping(shipping);
        if(rowCount>0) {
            return ServerResponse.createBySuccess("更新收货地址成功");
        }
        return ServerResponse.createByErrorMessage("更新收货地址失败");
    }

    public ServerResponse deleteShipping(Integer userId,Integer shippingId){
        log.info("删除收货地址");
        int rowCount = shippingMapper.deleteByUserIdAndShippingId(userId,shippingId);
        if(rowCount>0) {
            return ServerResponse.createBySuccess("删除收货地址成功");
        }
        return ServerResponse.createByErrorMessage("删除收货地址失败");
    }

    public ServerResponse selectShipping(Integer userId,Integer shippingId){
        log.info("查看收货地址详情");
        Shipping shipping = shippingMapper.selectByUserIdAndShippingId(userId,shippingId);
        if(shipping != null)
            return ServerResponse.createBySuccess("查看收货地址成功",shipping);
        return ServerResponse.createByErrorMessage("收货地址不存在");

    }


    public ServerResponse<PageInfo> listShippings(Integer userId,Integer pageNum,Integer pageSize){
        log.info("查看所有收货地址");
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> list = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);

    }
}
