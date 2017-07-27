package com.iot.dd.service;

import com.iot.dd.dao.mapper.IndentStatesMapper;
import com.iot.dd.dao.entity.Indent.IndentStatesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/26.
 */
@Service
public class indentStatesService {
    @Autowired
    private IndentStatesMapper statedao;

    public List<IndentStatesEntity> findState(){
        return statedao.find();

    }


}
