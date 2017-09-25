package com.iot.dd.service;

import com.iot.dd.dao.entity.Indent.IndentEvaluationEntity;
import com.iot.dd.dao.mapper.IndentEvaluationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2017/9/18.
 */
@Service
public class indentEvaluationService {
    @Autowired
    private IndentEvaluationMapper evaluation;

    public IndentEvaluationEntity findOne(String orderId) {
        return evaluation.findOne(orderId);
    }


}
