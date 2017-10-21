package com.iot.dd.service;

import com.iot.dd.dao.entity.worker.CheckTokenEntity;
import com.iot.dd.dao.mapper.CheckTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CheckTokenService {

    @Autowired
    private CheckTokenMapper tokenMapper;

    //添加记录
    public boolean addOneRecord(CheckTokenEntity entity) {
        return tokenMapper.addOneRecord(entity);
    }

    //按登陆名查找记录
    public CheckTokenEntity findOneRecord(String loninName) {
        return tokenMapper.findOne(loninName);
    }

    //设置token
    public boolean updateToken(String loginName,String token) {
        return tokenMapper.updateToken(loginName,token);
    }

    //设置时间
    public boolean updateTime(String loginName,Date time) {

        return tokenMapper.updateTime(loginName,time);
    }

    //设置时间和token
    //设置时间
    public boolean updateRecord(String loginName,String token, Date time) {

        CheckTokenEntity entity=new CheckTokenEntity();
        entity.setLoginName(loginName);
        entity.setToken(token);
        entity.setTime(time);
        return tokenMapper.update(entity);
    }

    //删除
    public boolean delete(String loginName){
        return tokenMapper.deleteRecord(loginName);
    }
}
