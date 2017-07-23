package com.iot.dd.service;

import com.iot.dd.Dao.OrderDao;
import com.iot.dd.domain.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
