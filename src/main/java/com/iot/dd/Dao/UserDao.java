package com.iot.dd.Dao;

import com.iot.dd.domain.worker.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by SunYi on 2016/2/1/0001.
 */
@Repository
//这个注解代表这是一个mybatis的操作数据库的类
public interface UserDao {

    // 根据admin(系统管理员表)获得一个User类
    @Select("select * from admin where NAME=#{name} limit 1")
    AdminEntity selectAdminUser(String name);


    //在注册插入一个系统管理员名字和密码
    @Insert("insert into admin (NAME,PASSWORD) values(#{name},#{password})")
    boolean addAdminUser(AdminEntity user);

    //在注册插入一个客服名字和密码
    @Insert("insert into staff (NAME,PASSWORD) values(#{name},#{password})")
    boolean addStaffUser(StaffEntity user);

    // 根据satff(客服表)获得一个User类
    @Select("select * from staff where NAME=#{name} limit 1")
    StaffEntity selectStaffUser(String name);




}
