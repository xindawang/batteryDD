package com.iot.dd.service;

import com.iot.dd.Dao.OrderDao;
import com.iot.dd.domain.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huanglin on 2017/7/21.
 */
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    //导入订单
    public String importOrder(OrderEntity orderEntity){
        String result=null;
        if(orderDao.addOrder(orderEntity)){
            result="订单导入成功";
        }else{
            result="订单导入失败";
        }
        return result;
    }

    //在派发订单时调出所有未派发订单信息
    public List<OrderEntity> selectUndoneIndentMsg(String status){

        return orderDao.selectIndentMsg(status);
    }

    public List<OrderEntity> importUndoneIndentMsg(String status,String orderId){
        return orderDao.importIndentMsg(status,orderId);
    }
}
