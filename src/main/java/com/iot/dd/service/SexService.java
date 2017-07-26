package com.iot.dd.service;

import com.iot.dd.dao.mapper.SexMapper;
import com.iot.dd.dao.entity.resource.SexEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/25.
 */
@Service
public class SexService {
    @Autowired
    private SexMapper sexdao;

    public List<SexEntity> find(){

        return sexdao.find();

    }

}
