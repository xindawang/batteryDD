package com.iot.dd.service;

import com.iot.dd.dao.entity.worker.LastIdEntity;
import com.iot.dd.dao.mapper.LastIdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2017/8/31.
 */
@Service
public class LastIdService {
    @Autowired
    LastIdMapper lastIdMapper;

    public boolean add(LastIdEntity entity) {
        return lastIdMapper.add(entity);
    }

    public boolean update(String num) {
        return lastIdMapper.update(num);
    }

    public LastIdEntity find(){
        return lastIdMapper.find();
    }

}
