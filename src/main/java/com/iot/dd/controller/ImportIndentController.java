package com.iot.dd.controller;

import com.iot.dd.dao.mapper.ResourceMapper;
import com.iot.dd.Tools.TimeTool;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.ParseException;

/**
 * Created by huanglin on 2017/7/21.
 */
@RestController
public class ImportIndentController {

    @Autowired
    private OrderService orderService;



    @Autowired
    private ResourceMapper resourceMapper;

    @RequestMapping(value="/importOrder", method= RequestMethod.POST)
    private String importOrder(HttpServletRequest request){

        String result=null;

        OrderEntity orderEntity=new OrderEntity();
        orderEntity.setOrderId(request.getParameter("orderId"));
        Integer batteryTypeId=Integer.valueOf(request.getParameter("batteryType"));
        String BatteryTypeName= resourceMapper.selectBatteryTypeName(batteryTypeId);
        orderEntity.setBatteryType(BatteryTypeName);

        orderEntity.setCustomerName(request.getParameter("customerName"));
        orderEntity.setCustomerCellphone(request.getParameter("cellphone"));
        orderEntity.setCustomerTelephone(request.getParameter("telephone"));

        orderEntity.setWechatId(request.getParameter("wechatId"));

        String address= resourceMapper.selectProvinceName(request.getParameter("province"))+
                resourceMapper.selectCityName(request.getParameter("city"))+
                resourceMapper.selectAreaName(request.getParameter("area"))+
                request.getParameter("detailAddress");
        orderEntity.setAddress(address);

        Integer automobileBrandId=Integer.valueOf(request.getParameter("automobileBrand"));
        String autoBrandName= resourceMapper.selectAutoBrandName(automobileBrandId);
        Integer automobileTypeId=Integer.valueOf(request.getParameter("automobileType"));
        String autoTypeName= resourceMapper.selectAutoTypeName(automobileTypeId);
        String automobileType=autoBrandName+autoTypeName;
        orderEntity.setAutomobileType(automobileType);
        orderEntity.setLicensePlateNumber(request.getParameter("licensePlateNumber"));
        orderEntity.setCityCode(request.getParameter("city"));

        Date createTime=null;
        try {
            createTime=TimeTool.stringToSqlDate(request.getParameter("createTime"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orderEntity.setCreateTime(createTime);


        orderEntity.setStatus(1);//状态：已录入
        orderEntity.setRemark(request.getParameter("remark"));

        if(orderService.selectOrderId(request.getParameter("orderId"))==null) {
            result = orderService.importOrder(orderEntity);

            //同时向订单转发表中插入订单编号
            orderService.InsertOrderId(request.getParameter("orderId"));
        }

        return result;

    }

}
