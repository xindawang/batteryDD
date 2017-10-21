package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.worker.CheckTokenEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CheckTokenMapper {

    @Insert("insert into check_token(login_name,token,time)" +
            " values(#{loginName},#{token},#{time})")
    boolean addOneRecord(CheckTokenEntity entity);

    @Select("select * from check_token where login_name=#{loginName}")
    @Results({
            @Result(property = "loginName", column = "login_name")
    })
    CheckTokenEntity findOne(@Param("loginName") String loginName);

    @Update("update check_token set token=#{token} where login_name=#{loginName}")
    boolean updateToken(@Param("loginName") String loginName,@Param("token") String token);

    @Update("update check_token set time=#{time} where login_name=#{loginName}")
    boolean updateTime(@Param("loginName") String loginName,@Param("time") Date time);


    @Update("update check_token set token=#{token},time=#{time} where login_name=#{loginName}")
    boolean update(CheckTokenEntity entity);

    @Delete("delete from ckeck_token where login_name=#{loginName}")
    boolean deleteRecord(String longinName);
}