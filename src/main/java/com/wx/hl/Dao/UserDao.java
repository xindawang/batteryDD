package com.wx.hl.Dao;

import com.wx.hl.domain.UserMessage;
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
    // 根据username获得一个User类
    @Select("select * from user where name=#{name} limit 1")
    UserMessage getOneUser(String name);


    //插入一个User
    @Insert("insert into user (name,password) values(#{name},#{password})")
    boolean setOneUser(UserMessage user);

    //更新经纬度
    @Update("update user latitude=#{latitude},longitude=#{longitude} where name=#{name} ")
    boolean setLocation(UserMessage user);

}
