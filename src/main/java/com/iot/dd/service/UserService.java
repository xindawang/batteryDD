package com.iot.dd.service;

import com.iot.dd.Dao.UserDao;
import com.iot.dd.domain.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huanglin on 2017/7/13.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;


    public String register(UserMessage user){
        if(userDao.getOneUser(user.getName())== null){
            userDao.setOneUser(user);
            return "注册成功";
        }else{
            return "该用户已被使用";
        }

    }
    //用户登陆
    public String login(UserMessage user){
        UserMessage dUser=userDao.getOneUser(user.getName());
        if(dUser==null){
            return "该用户不存在";
        }
        else if(! dUser.getPassword().equals(user.getPassword())){
            return "密码错误";
        }
        else{
            user.setId(dUser.getId());
            return "登陆成功";
        }

    }
}
