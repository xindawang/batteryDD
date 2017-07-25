package com.iot.dd.service;

import com.iot.dd.Dao.SexDao;
import com.iot.dd.domain.SexEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/25.
 */
@Service
public class SexService {
    @Autowired
    private SexDao sexdao;

    public List<SexEntity> find(){

        return sexdao.find();

    }

}
