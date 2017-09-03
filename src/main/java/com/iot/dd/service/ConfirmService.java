package com.iot.dd.service;

import com.iot.dd.dao.entity.Indent.ConfirmEntity;
import com.iot.dd.dao.mapper.confirmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2017/8/28.
 */

@Service
public class ConfirmService {
    @Autowired

    private confirmMapper mapper;

    public boolean addOneRecord(ConfirmEntity entity){
        return mapper.addOne(entity);
    }

}
