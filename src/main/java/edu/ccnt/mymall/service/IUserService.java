package edu.ccnt.mymall.service;

import edu.ccnt.mymall.common.ServerResponse;
import edu.ccnt.mymall.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public interface IUserService {

    ServerResponse<User> login(String username,String password);

    ServerResponse<String> checkInfo(String type,String str);

    ServerResponse<String> register(User user);

    ServerResponse<String> getQuestion(String username);

    ServerResponse<String> checkAnswer(String username,String password,String answer);
}
