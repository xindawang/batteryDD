package com.iot.dd.service;

import com.iot.dd.Dao.UserDao;
import com.iot.dd.domain.worker.StaffEntity;
import com.iot.dd.domain.worker.AdminEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

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

    //管理员信息完善
    public String adminInformation(AdminEntity dUser){
        boolean b= userDao.modifyAdminInfo(dUser);
        if(b){
            return "信息设置成功！";
        }
        else {
            return "信息设置失败！";
        }
    }
    //客服信息完善
    public String staffInformation(StaffEntity dUser ){
        boolean b= userDao.modifyStaffInfo(dUser);
        if(b){
            return "信息设置成功！";
        }
        else {
            return "信息设置失败！";
        }
    }

    //查admin返回一个记录
    public  AdminEntity findAdminByLogin_name(String login_name){
        AdminEntity aUser=new AdminEntity();

        aUser=userDao.selectAdminUser(login_name);
        return aUser;
    }

    //查staff返回一个记录
    public  StaffEntity findStaffByLogin_name(String login_name){
        StaffEntity sUser=new StaffEntity();
        sUser=userDao.selectStaffUser(login_name);
        return sUser;
    }
}
