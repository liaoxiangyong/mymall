package edu.ccnt.mymall.service.Impl;

import com.sun.org.apache.bcel.internal.generic.NEW;
import edu.ccnt.mymall.common.Const;
import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.common.TokenCache;
import edu.ccnt.mymall.dao.UserMapper;
import edu.ccnt.mymall.model.User;
import edu.ccnt.mymall.service.IUserService;
import edu.ccnt.mymall.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service("iUserService")
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        log.info("用户登录："+username);
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
        log.info("校验"+type+":"+str);
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
        log.info("用户注册:"+user);
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

    @Override
    public ServerResponse<String> getQuestion(String username) {
        log.info("获取用户问题："+username);
        //1、先检查username是否存在
        ServerResponse<String > serverResponse = this.checkInfo(Const.USERNAME,username);
        if(serverResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //2、存在则返回问题
        String question = userMapper.findQuestion(username);
        if(StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题为空");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        log.info("校验"+username+"问题答案"+question+":"+answer);
        //1、先检查用户名是否存在
        ServerResponse<String > serverResponse = this.checkInfo(Const.USERNAME,username);
        if(serverResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //2、返回验证结果
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount>0){
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("找回问题的答案是错误的");
    }

    @Override
    public ServerResponse<String> forgetUpdatePassword(String username, String newPassword, String forgetToken) {
        log.info("忘记密码之更新密码");
        if(StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误，需要传递token");
        }

        //验证用户名是否存在
        ServerResponse<String > serverResponse = this.checkInfo(Const.USERNAME,username);
        if(serverResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);

        if(StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token无效或过期");
        }

        if(StringUtils.equals(forgetToken,token)){
            String password = MD5Util.MD5EncodeUtf8(newPassword);
            int resultCount = userMapper.updatePassword(username,password);
            if(resultCount>0){
                return ServerResponse.createBySuccess("密码修改成功");
            }
        }else{
            return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
        }

        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<User> updateUserInfo(User user) {
        log.info("更新用户信息"+user);
        //需要校验email，email不能为其他用户已经使用的email
        String email = user.getEmail();
        int resultCount = userMapper.checkEmainOtherUser(user.getId(),user.getEmail());
        if(resultCount>0){
            return ServerResponse.createByErrorMessage("该email已被使用");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setQuestion(user.getQuestion());
        newUser.setAnswer(user.getAnswer());
        int updateCount = userMapper.updateByPrimaryKeySelective(newUser);
        if(updateCount>0){
            return ServerResponse.createBySuccess("信息更新成功",newUser);
        }
        return ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse<String> resetPassword(User user, String oldPassword, String newPassword) {
        log.info("重置密码"+user);
        //校验密码是否正确
        int resultCount = userMapper.checkPassword(user.getUsername(),MD5Util.MD5EncodeUtf8(oldPassword));
        if(resultCount>0){
            user.setPassword(MD5Util.MD5EncodeUtf8(newPassword));
            resultCount = userMapper.updateByPrimaryKeySelective(user);
            if(resultCount>0)
                return ServerResponse.createBySuccessMessage("密码更新成功");
        }else{
            return ServerResponse.createByErrorMessage("原始密码输入错误");
        }
        return ServerResponse.createByErrorMessage("密码重置失败");
    }

    @Override
    public ServerResponse<User> getUserInfo(int userId) {
        log.info("获取用户信息"+userId);
        User user = userMapper.selectByPrimaryKey(userId);
        if(user ==null){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        user.setPassword(StringUtils.EMPTY);

        return ServerResponse.createBySuccess("查找成功",user);
    }

    @Override
    public ServerResponse<String> checkUserAdmin(User user){
        if(user.getRole()==Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("没有权限");
    }
}
