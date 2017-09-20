package com.iot.dd.service;

import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/7/26.
 */
@Service
public class IndentManageService {
    @Autowired
    private OrderMapper   ordermapper;


    //根据订单状态查询所有订单
    public List<OrderEntity> findIndentByState(Integer state){
        return ordermapper.selectIndentMsg(state);

    }

    //根据订单所在城市查询所有订单
    public List<OrderEntity> findIndentByCity(String cityCode){
        return ordermapper.selectIndentByCity(cityCode);

    }

    //根据城市及订单状态查找所有订单
    public List<OrderEntity> findIndentByStateAndCity(Integer status,String cityCode){
        return ordermapper.selectIndentByStatusAndCity(status,cityCode);
    }


    public List<OrderEntity> findIndentMsg(Integer status, String cityCode, String dateRegion) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        c.setTime(new Date());

        switch (dateRegion) {
            case "oneDay":
                c.add(Calendar.DATE, -1);
                break;
            case "oneWeek":
                c.add(Calendar.DATE, -7);
                break;
            case "oneMonth":
                c.add(Calendar.MONTH, -1);
                break;
            case "halfOfYear":
                c.add(Calendar.MONTH, -6);
            case "oneYear":
                c.add(Calendar.YEAR, -1);
            default:
                c.add(Calendar.DATE, 1);
                break;//全部
        }

        Date date = c.getTime();
        String compareDate = format.format(date);
        //时间直接体现在根据不同的时间段需求在查询订单时与数据库的时间进行比较。不需要单独分列情况
        if (dateRegion.equals("all")) {
            if (status == 0 && cityCode.equals("0")) {//全部城市的全部订单
                return ordermapper.selectAllIndent();
            } else if (status == 0 && !cityCode.equals("0")) {//查询某一城市的所有订单
                return ordermapper.selectIndentByCity(cityCode);
            } else if (status != 0 && cityCode.equals("0")) {//查询某一订单状态的所有订单，不区别城市
                return ordermapper.selectIndentMsg(status);
            } else {
                return ordermapper.selectIndentByStatusAndCity(status, cityCode);
            }
        } else {
            if (status == 0 && cityCode.equals("0")) {//全部城市的全部订单
                return ordermapper.selectAllIndent1(compareDate);
            } else if (status == 0 && !cityCode.equals("0")) {//查询某一城市的所有订单
                return ordermapper.selectIndentByCity1(cityCode, compareDate);
            } else if (status != 0 && cityCode.equals("0")) {//查询某一订单状态的所有订单，不区别城市
                return ordermapper.selectIndentMsg1(status, compareDate);
            } else {
                return ordermapper.selectIndentByStatusAndCity1(status, cityCode, compareDate);
            }
        }
    }

    public List<OrderEntity> findIndentByNumber(String number) {
        return ordermapper.selectIndentByNumber(number);
    }
}
