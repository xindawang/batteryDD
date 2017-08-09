package com.iot.dd.service;

import com.iot.dd.dao.mapper.OrderMapper;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huanglin on 2017/7/21.
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

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

    //在派发订单时调出所有未派发订单信息
    public List<OrderEntity> selectUndoneIndentMsg(String status){

        return orderMapper.selectIndentMsg(status);
    }
//根据订单号将select未派发的订单信息
    public List<OrderEntity> importUndoneIndentMsg(String status,String orderId){
        return orderMapper.importIndentMsg(status,orderId);
    }

    //在订单派发开始时选中某一未派发订单时将订单编号存入订单派发表中
    public String InsertOrderId(String orderId) {
        if (orderMapper.selectIndentAllocationMsg(orderId) == null) {
            orderMapper.insertAllocationOderId(orderId);
        } else {
            return "订单编号已导入";
        }
        return "订单编号成功导入订单转发表";
    }


    public String updateCustomerLocation(String telephone,Float longitude,Float latitude){
        String orderId=orderMapper.selectOrderIdbyPhone(telephone);
        Boolean res=orderMapper.updateUserAddress(orderId,longitude,latitude);
        if(res==true){
            return "用户地理位置更新成功";
        }else{
            return "用户地理位置更新失败";
        }

    }

}
