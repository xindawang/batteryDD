package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.worker.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by SunYi on 2016/2/1/0001.
 */
@Repository
//这个注解代表这是一个mybatis的操作数据库的类
public interface UserMapper {

    // 根据admin(系统管理员表)获得一个User类
    @Select("select * from admin where login_name=#{loginName} limit 1")
    @Results({
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "loginName", column = "login_name")
    })
    AdminEntity selectAdminUser(@Param("loginName")String name);



    //在注册插入一个系统管理员名字和密码
    @Insert("insert into admin (LOGIN_NAME,PASSWORD) values(#{loginName},#{password})")
    boolean addAdminUser(AdminEntity user);

    //管理员信息更新
    @Update("update admin set name=#{name} ,cellphone=#{cellphone},telephone=#{telephone},email=#{email},id_number=#{idNumber},address=#{address},role=#{role} where login_name=#{loginName}")
      boolean modifyAdminInfo(AdminEntity user);


    //在注册插入一个客服名字和密码
    @Insert("insert into staff (LOGIN_NAME,PASSWORD) values(#{loginName},#{password})")
    boolean addStaffUser(StaffEntity user);

    // 根据satff(客服表)获得一个User类
    @Select("select * from staff where LOGIN_NAME=#{loginName} limit 1")
    @Results({
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "loginName", column = "login_name")
    })
    StaffEntity selectStaffUser(String name);


     //技师注册
    @Insert("insert into technician (LOGIN_NAME,PASSWORD,TECHNICIAN_ID,NAME,CELLPHONE," +
            "ID_NUMBER,ADDRESS,CITY_CODE,LICENSE_PLATE_NUMBER) values(#{loginName},#{password}," +
            "#{technicianId},#{name},#{cellphone},#{idNumber},#{address},#{cityCode}," +
            "#{licensePlateNumber})")
    boolean addTechnician(TechnicianEntity user);


    //查询
    @Select("select * from technician where LOGIN_NAME=#{loginName} limit 1")
    TechnicianEntity selectTechnician(String loginName);



    //更新经纬度

   //客服信息更新
    @Update("update staff set name=#{name},sex=#{sex} ,cellphone=#{cellphone},telephone=#{telephone},email=#{email},id_number=#{idNumber},address=#{address},role=#{role} where login_name=#{loginName} ")
    boolean modifyStaffInfo(StaffEntity user);



}
