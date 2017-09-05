package edu.ccnt.mymall.controller.backend;

import edu.ccnt.mymall.common.Const;
import edu.ccnt.mymall.common.ResponseCode;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.Product;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.IProductService;
import edu.ccnt.mymall.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    /**
     * 添加或者更新商品信息
     * @param httpSession
     * @param product
     * @return
     */
    @RequestMapping(value = "productSaveOrInsert.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加或者更新商品信息")
    public ServerResponse productSaveOrInsert(HttpSession httpSession, Product product){
        //1、验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"管理员未登录");
        }
        //2、验证是否管理员
        if(iUserService.checkUserAdmin(user).isSuccess()){
            //3、业务逻辑
            return iProductService.productSaveOrInsert(product);
        }else{
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }

    /**
     * 更新商品状态
     * @param httpSession
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping(value = "updateProductStatus.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("更新商品状态")
    public ServerResponse<String> updateProductStatus(HttpSession httpSession,int productId,int status){
        //1、验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"管理员未登录");
        }
        //2、验证是否管理员
        if(iUserService.checkUserAdmin(user).isSuccess()){
            //3、业务逻辑
            return  iProductService.updateProductStatus(productId,status);
        }else{
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }

}