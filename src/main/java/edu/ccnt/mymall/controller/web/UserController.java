package edu.ccnt.mymall.controller.web;

import edu.ccnt.mymall.common.Const;
import edu.ccnt.mymall.common.ResponseCode;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

//@Api(value = "UserController", tags = {"用户管理API"}, description = "用户管理API")
@RestController
@RequestMapping("/user/")
@Slf4j
public class UserController {

    @Autowired
    private IUserService iUserService;

    //登录
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户登录",notes = "用户登录")
    public ServerResponse<User> login(String username, String password, HttpSession httpSession){
        ServerResponse serverResponse =  iUserService.login(username,password);
        if(serverResponse.isSuccess()){
            httpSession.setAttribute(Const.CURRENT_USER,serverResponse.getData());
        }
        return serverResponse;
    }

    //退出登录
    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "退出登录",notes = "用户退出登录")
    public ServerResponse<String> logout(HttpSession httpSession){
        httpSession.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    //注册
    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户注册",notes = "用户注册")
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    //校验用户名和邮箱是否已存在
    @RequestMapping(value = "checkInfo.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "校验用户名和邮箱",notes = "检查用户名或邮箱是否已存在")
    public ServerResponse<String> checkInfo(String type,String str){
        return iUserService.checkInfo(type,str);
    }

    //密码忘记，获取问题
    @RequestMapping(value = "getQuestion.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取问题",notes = "忘记密码之后获取问题")
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.getQuestion(username);
    }

    //校验问题答案
    @RequestMapping(value = "checkAnswer.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "校验问题答案",notes = "校验问题答案")
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    //忘记密码之后更改密码
    @RequestMapping(value = "forgetUpdatePassword.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "找回密码-更新密码",notes = "找回密码-更新密码")
    public ServerResponse<String> forgetUpdatePassword(String username,String newPassword,String forgetToken){
        return iUserService.forgetUpdatePassword(username,newPassword,forgetToken);
    }

    //重置密码
    @RequestMapping(value = "resetPassword.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "重置密码",notes = "重置密码")
    public ServerResponse<String> resetPassword(HttpSession httpSession,String oldPassword,String newPassword){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(user,oldPassword,newPassword);
    }

    //更新用户信息
    @RequestMapping(value = "UpdateUserInfo.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新用户信息",notes = "更新用户可变信息")
    public ServerResponse<User> updateUserInfo(HttpSession httpSession,User user){
        User currentUser = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());        //id，username不能更新
        ServerResponse<User> response = iUserService.updateUserInfo(user);
        if(response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            httpSession.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    //获取用户信息
    @RequestMapping(value = "getUserInfo.do",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取用户信息",notes = "获取用户信息，不包含密码")
    public ServerResponse<User> getUserInfo(HttpSession httpSession){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LONGIN.getCode(),"未登录，请先登录");
        }
        return iUserService.getUserInfo(user.getId());
    }
}
