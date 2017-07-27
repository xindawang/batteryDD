package com.iot.dd.service;

import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/26.
 */
@Service
public class IndentManageService {
    @Autowired
    private OrderMapper   ordermapper;


    //根据订单状态查询所有订单
    public List<OrderEntity> findIndentByState(String state){
        return ordermapper.selectIndentMsg(state);

    }

    //根据订单所在城市查询所有订单
    public List<OrderEntity> findIndentByCity(String cityCode){
        return ordermapper.selectIndentByCity(cityCode);

    }

    //根据城市及订单状态查找所有订单
    public List<OrderEntity> findIndentByStateAndCity(String status,String cityCode){
        return ordermapper.selectIndentByStatusAndCity(status,cityCode);
    }

}
