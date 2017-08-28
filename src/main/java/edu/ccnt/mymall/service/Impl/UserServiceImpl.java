package edu.ccnt.mymall.service.Impl;

import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.dao.UserMapper;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int userCount = userMapper.checkUsername(username);
        if(userCount==0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //密码登录md5

        User user = userMapper.selectLogin(username,password);
        if(user==null){
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword("");
        return ServerResponse.createBySuccess("登录成功",user);
    }
}
