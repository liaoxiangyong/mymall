package edu.ccnt.mymall.service.Impl;

import edu.ccnt.mymall.common.Const;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.dao.UserMapper;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.IUserService;
import edu.ccnt.mymall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.USER_EXCEPTION;
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
        String md5Password  = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.selectLogin(username,md5Password);
        if(user==null){
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    @Override
    public ServerResponse<String> checkInfo(String type, String str) {
        if(StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage("Email已存在");
                }
            }
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
        }else{
            return ServerResponse.createByErrorMessage("校验错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> register(User user) {
        //需要先校验用户名和邮箱是否已存在
        ServerResponse<String> serverResponse = this.checkInfo(Const.USERNAME,user.getUsername());
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }

        serverResponse = this.checkInfo(Const.EMAIL,user.getEmail());
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));    //md5加密

        int resultCount = userMapper.insert(user);

        if(resultCount ==0 ){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }
}
