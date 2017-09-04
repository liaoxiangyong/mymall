package edu.ccnt.mymall.controller.backend;


import edu.ccnt.mymall.common.Const;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.ICategoryService;
import edu.ccnt.mymall.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/category/")
public class CategoryManageController {

    @Autowired
    IUserService iUserService;

    @Autowired
    ICategoryService iCategoryService;

    /**
     * 添加类别
     * @param httpSession
     * @param parentId  父类id
     * @param categoryName  类别名
     * @return
     */
    @RequestMapping(value = "category_add.do",method = RequestMethod.POST)
    @ApiOperation("商品品类添加")
    @ResponseBody
    public ServerResponse addCategory(HttpSession httpSession, @RequestParam(value = "parentId",defaultValue = "0") int parentId, String categoryName){
        //1、判断用户是否登录
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        //2、判断用户是否管理员
        if(iUserService.checkUserAdmin(user).isSuccess()){
            return iCategoryService.addCategory(parentId,categoryName);
        }else{
            return ServerResponse.createByErrorMessage("用户没有管理员权限");
        }
    }
}
