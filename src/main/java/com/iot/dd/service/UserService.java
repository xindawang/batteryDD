package com.iot.dd.service;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.worker.CheckTokenEntity;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import com.iot.dd.dao.mapper.CheckTokenMapper;
import com.iot.dd.dao.mapper.ResourceMapper;
import com.iot.dd.dao.mapper.UserMapper;
import com.iot.dd.dao.entity.worker.StaffEntity;
import com.iot.dd.dao.entity.worker.AdminEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by huanglin on 2017/7/13.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CheckTokenMapper ckeckMapper;


    //管理员注册
    public String registerAdmin(AdminEntity user) {
        if (userMapper.selectAdminUser(user.getName()) == null) {
            userMapper.addAdminUser(user);
            return "注册成功";
        } else {
            return "该用户已被使用";
        }

    }

    //客服注册
    public String registerStaff(StaffEntity user) {
        if (userMapper.selectStaffUser(user.getName()) == null) {
            userMapper.addStaffUser(user);
            return "注册成功";
        } else {
            return "该用户已被使用";
        }

    }

    //技师注册
    public String registerTechnician(TechnicianEntity user) {
        if (userMapper.selectTechnician(user.getLoginName()) == null) {
            userMapper.addTechnician(user);
            return "OK";
        } else {
            return "ERROR";
        }

    }


    //客服登陆
    public String staffLogin(String name, String password) {
        StaffEntity dUser = userMapper.selectStaffUser(name);
        if (dUser == null) {
            return "该用户不存在";
        } else if (!dUser.getPassword().equals(password)) {
            return "密码错误";
        } else {
            return "登陆成功";
        }

    }

    //管理员登陆
    public String adminLogin(String name, String password) {
        AdminEntity dUser = userMapper.selectAdminUser(name);
        if (dUser == null) {
            return "该用户不存在";
        } else if (!dUser.getPassword().equals(password)) {
            return "密码错误";
        } else {
            return "登陆成功";
        }

    }

    //技师登陆
    public String technicianLogin(String name, String password) {
        TechnicianEntity dUser = userMapper.selectTechnician(name);
        Map<String, String> map = new HashMap<>();
        if (dUser == null) {
            map.put("mask", "该用户不存在！！！");
            return JsonTool.objectToJson(map);
        } else if (!dUser.getPassword().equals(password)) {
            map.put("mask", "密码错误！！！");
            return JsonTool.objectToJson(map);
        } else {
            String token = new Random().nextInt(99999) + "";//随机生成五位数作为token,保证同一时间只有一台设备能使用app请求数据
            map.put("mask", "OK");
            map.put("technicianId", dUser.getTechnicianId());
            map.put("token", token);
            //token保存数据库
            CheckTokenEntity entity = new CheckTokenEntity();
            entity = ckeckMapper.findOne(name);
            if (entity != null) {
                entity.setToken(token);
                entity.setTime(new Date());
                ckeckMapper.update(entity);
            } else {
                CheckTokenEntity entity1 = new CheckTokenEntity();
                entity1.setLoginName(name);
                entity1.setToken(token);
                entity1.setTime(new Date());
                ckeckMapper.addOneRecord(entity1);
            }
            return JsonTool.objectToJson(map);
        }

    }


    //管理员信息完善
    public String adminInformation(AdminEntity dUser) {
        boolean b = userMapper.modifyAdminInfo(dUser);
        if (b) {
            return "信息设置成功！";
        } else {
            return "信息设置失败！";
        }
    }

    //客服信息完善
    public String staffInformation(StaffEntity dUser) {
        boolean b = userMapper.modifyStaffInfo(dUser);
        if (b) {
            return "信息设置成功！";
        } else {
            return "信息设置失败！";
        }
    }

    //查admin返回一个记录
    public AdminEntity findAdminByLogin_name(String login_name) {
        AdminEntity aUser = new AdminEntity();

        aUser = userMapper.selectAdminUser(login_name);
        return aUser;
    }

    //查staff返回一个记录
    public StaffEntity findStaffByLogin_name(String login_name) {
        StaffEntity sUser = new StaffEntity();
        sUser = userMapper.selectStaffUser(login_name);
        return sUser;
    }

    //添加
    public Boolean addTechnician(TechnicianEntity entity) {
        return userMapper.addTechnician(entity);
    }

    public Boolean addStaff(StaffEntity entity) {
        return userMapper.addStaff(entity);
    }

    public Boolean addAdmin(AdminEntity entity) {
        return userMapper.addAdmin(entity);
    }

}
