package com.iot.dd.service;

import com.iot.dd.Dao.IndentStatesDao;
import com.iot.dd.domain.IndentStatesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/26.
 */
@Service
public class indentStatesService {
    @Autowired
    private IndentStatesDao statedao;

    public List<IndentStatesEntity> findState(){
        return statedao.find();

    }


}
