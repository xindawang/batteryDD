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

    /*
    * 添加一个用户
    * */

    //添加technician
    @Insert("insert into technician(TECHNICIAN_ID,LOGIN_NAME,PASSWORD,NAME,SEX,EMAIL,CELLPHONE,TELEPHONE,ID_NUMBER,LICENSE_PLATE_NUMBER,ADDRESS,CITY_CODE)" +
            "values(#{technicianId},#{loginName},#{password},#{name},#{sex},#{email},#{cellphone},#{telephone},#{idNumber},#{licensePlateNumber},#{address},#{cityCode})")
    boolean addTechnician(TechnicianEntity user);

    //添加staff
    @Insert("insert into  staff(LOGIN_NAME,PASSWORD,NAME,SEX,CELLPHONE,TELEPHONE,EMAIL,ID_NUMBER,ADDRESS)" +
            "values(#{loginName},#{password},#{name},#{sex},#{cellphone},#{telephone},#{email},#{idNumber},#{address})")
    boolean addStaff(StaffEntity entity);

    //添加管理员
    @Insert("insert into admin(LOGIN_NAME,PASSWORD,NAME,SEX,CELLPHONE,TELEPHONE,EMAIL,ID_NUMBER,ADDRESS)" +
            "values(#{loginName},#{password},#{name},#{sex},#{cellphone},#{telephone},#{email},#{idNumber},#{address})")
    boolean addAdmin(AdminEntity entity);

    //查询
    @Select("select * from technician where LOGIN_NAME=#{loginName} limit 1")
    TechnicianEntity selectTechnician(String loginName);
   //客服信息更新
    @Update("update staff set name=#{name},sex=#{sex} ,cellphone=#{cellphone},telephone=#{telephone},email=#{email},id_number=#{idNumber},address=#{address},role=#{role} where login_name=#{loginName} ")
    boolean modifyStaffInfo(StaffEntity user);



}
