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
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取商品详情
     * @param httpSession
     * @param productId
     * @return
     */
    @RequestMapping(value = "getDetail.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取商品信息")
    public ServerResponse getDetail(HttpSession httpSession,int productId){
        //1、验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"管理员未登录");
        }
        //2、验证是否管理员
        if(iUserService.checkUserAdmin(user).isSuccess()){
            //3、业务逻辑
            return  iProductService.getDetail(productId);
        }else{
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }

    /**
     * 获取商品列表
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "getProductList.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取商品列表")
    public ServerResponse getProductList(HttpSession httpSession, @RequestParam(value ="pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //1、验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"管理员未登录");
        }
        //2、验证是否管理员
        if(iUserService.checkUserAdmin(user).isSuccess()){
            //3、业务逻辑
            return  iProductService.manageGetProductList(pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }

    /**
     * 商品搜索
     * @param httpSession
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "searchProductList.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("搜索商品")
    public ServerResponse searchProductList(HttpSession httpSession,String productName,int productId,
                                            @RequestParam(value ="pageNum",defaultValue = "1") int pageNum,
                                            @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //1、验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"管理员未登录");
        }
        //2、验证是否管理员
        if(iUserService.checkUserAdmin(user).isSuccess()){
            //3、业务逻辑
            return  iProductService.searchProductList(productName,productId,pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }

}
