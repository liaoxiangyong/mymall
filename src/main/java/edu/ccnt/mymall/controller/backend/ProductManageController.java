package edu.ccnt.mymall.controller.backend;

import edu.ccnt.mymall.common.Const;
import edu.ccnt.mymall.common.ResponseCode;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.Product;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.IFileService;
import edu.ccnt.mymall.service.IProductService;
import edu.ccnt.mymall.service.IUserService;
import edu.ccnt.mymall.util.PropertiesUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

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


    /**
     * 图片上传
     * @param httpSession
     * @param file
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "uploadImage",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("图片上传")
    public ServerResponse uploadImage(HttpSession httpSession, @RequestParam(value = "uploadFile",required = false) MultipartFile file,
                                      HttpServletRequest httpServletRequest){
        //1、验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"管理员未登录");
        }
        //2、验证是否管理员
        if(iUserService.checkUserAdmin(user).isSuccess()){
            //3、业务逻辑
            String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");
            String targetName = iFileService.uploadFile(file,path);
            if(StringUtils.isBlank(targetName))
                return ServerResponse.createByErrorMessage("图片上传失败！");
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetName;
            Map result = new HashMap();
            result.put("uri",targetName);
            result.put("url",url);
            return  ServerResponse.createBySuccess(result);
        }else{
            return ServerResponse.createByErrorMessage("用户无权限");
        }
    }

    @RequestMapping(value = "uploadRichTextImage",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("富文本上传")
    public Map uploadRichTextImage(HttpSession httpSession, @RequestParam(value = "uploadRichTextFile",required = false) MultipartFile file,
                                      HttpServletRequest httpServletRequest){
        Map result = new HashMap();
        //1、验证登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            result.put("success",false);
            result.put("msg","请登录管理员");
            return result;
        }
        //2、验证是否管理员
        if(iUserService.checkUserAdmin(user).isSuccess()){
            //3、业务逻辑
            String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");
            String targetName = iFileService.uploadFile(file,path);
            if(StringUtils.isBlank(targetName)) {
                result.put("success",false);
                result.put("msg","上传失败");
                return result;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetName;

            result.put("success",true);
            result.put("msg","上传成功");
            result.put("file_path",url);
            return result;
        }else{
            result.put("success",false);
            result.put("msg","无管理员权限");
            return result;
        }
    }

}
