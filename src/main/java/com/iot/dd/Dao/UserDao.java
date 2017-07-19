package com.iot.dd.Dao;

import com.iot.dd.domain.UserMessage;
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
    adminEntity getAdminUser(String name);


    //在注册插入一个User名字和密码
    @Insert("insert into admin (NAME,PASSWORD) values(#{name},#{password})")
    boolean setAdminUser(adminEntity user);

    //更新经纬度
    @Update("update user latitude=#{latitude},longitude=#{longitude} where name=#{name} ")
    boolean setLocation(UserMessage user);

}
