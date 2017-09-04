package edu.ccnt.mymall.controller.backend;

import edu.ccnt.mymall.common.Const;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/user/")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession httpSession){
        ServerResponse serverResponse = iUserService.login(username,password);
        if(serverResponse.isSuccess()){
            User user = (User) serverResponse.getData();
            int role = user.getRole();
            if(role==Const.Role.ROLE_ADMIN){
                httpSession.setAttribute(Const.CURRENT_USER,user);
                return serverResponse;
            }else{
                return ServerResponse.createByErrorMessage("该用户不是管理员，无法登录");
            }
        }
        return serverResponse;
    }
}
