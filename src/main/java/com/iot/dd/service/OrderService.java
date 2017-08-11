package com.iot.dd.service;

import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import com.iot.dd.dao.mapper.OrderMapper;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huanglin on 2017/7/21.
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    //导入订单
    public String importOrder(OrderEntity orderEntity){
        String result=null;
        if(orderMapper.addOrder(orderEntity)){
            result="订单导入成功";
        }else{
            result="订单导入失败";
        }
        return result;
    }

    //在派发订单时调出所有未派发订单信息(订单编号)
    public List<String> selectUndoneIndentOrderId(Integer status){

        return orderMapper.selectImportIndentOrderId(status);
    }
//根据订单号将select未派发的订单信息
    public List<OrderEntity> importUndoneIndentMsg(Integer status,String orderId){
        return orderMapper.importIndentMsg(status,orderId);
    }

    //在订单派发开始时选中某一未派发订单时将订单编号存入订单派发表中
    public String InsertOrderId(String orderId) {
        if (orderMapper.selectIndentAllocationMsg(orderId) == null) {
            orderMapper.insertAllocationOderId(orderId);
        } else {
            return "订单编号已存在，不需重复导入";
        }
        return "订单编号成功导入订单转发表";
    }


    public String updateCustomerLocation(String telephone,Float longitude,Float latitude){
        String status=resourceMapper.selectStatus(1);
        String orderId=orderMapper.selectOrderIdbyPhone(telephone,status);
        Boolean res=orderMapper.updateUserLocation(orderId,longitude,latitude);
        if(res==true){
            return "用户地理位置更新成功";
        }else{
            return "用户地理位置更新失败";
        }

    }

//通过订单编号查询用户的地理位置
    public Map<String,Float> selectCustomerLocation(String orderId){
        IndentAllocationEntity indentAllocationEntity=orderMapper.selectCustomerLocation(orderId);
        Map<String,Float> result=new HashMap<>();
        result.put("cusLongitude",indentAllocationEntity.getCustomerLongitude());
        result.put("cusLatitude",indentAllocationEntity.getCustomerLatitude());
        return  result;
    }

    //查询订单编号是否存在
    public String selectOrderId(String orderId){
         return orderMapper.selectIdByOrderId(orderId);
    }



}
