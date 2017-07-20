package com.iot.dd.service;

import com.iot.dd.Dao.UserDao;
import com.iot.dd.domain.UserMessage;
import com.iot.dd.domain.worker.StaffEntity;
import com.iot.dd.domain.worker.AdminEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huanglin on 2017/7/13.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

//管理员注册
    public String registerAdmin(AdminEntity user){
        if(userDao.selectAdminUser(user.getName())== null){
            userDao.addAdminUser(user);
            return "注册成功";
        }else{
            return "该用户已被使用";
        }

    }

    //客服注册
    public String registerStaff(StaffEntity user){
        if(userDao.selectStaffUser(user.getName())== null){
            userDao.addStaffUser(user);
            return "注册成功";
        }else{
            return "该用户已被使用";
        }

    }
    //客服登陆
    public String staffLogin(String name,String password){
        StaffEntity dUser=userDao.selectStaffUser(name);
        if(dUser==null){
            return "该用户不存在";
        }
        else if(! dUser.getPassword().equals(password)){
            return "密码错误";
        }
        else{
            return "登陆成功";
        }

    }
    //管理员登陆
    public String adminLogin(String name,String password){
        AdminEntity dUser=userDao.selectAdminUser(name);
        if(dUser==null){
            return "该用户不存在";
        }
        else if(! dUser.getPassword().equals(password)){
            return "密码错误";
        }
        else{
            return "登陆成功";
        }

    }
}
