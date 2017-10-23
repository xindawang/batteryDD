package com.iot.dd.controller;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.Tools.TimeTool;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.mapper.ResourceMapper;
import com.iot.dd.service.OrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;

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
        String orderId=request.getParameter("orderId").replaceAll("\\s","");
        if(orderService.selectOrderId(orderId)!=null){
            return JsonTool.objectToJson("订单编号重复，请检测是否填写错误");
        }
        orderEntity.setOrderId(orderId);
        Integer batteryTypeId=Integer.valueOf(request.getParameter("batteryType"));
        String BatteryTypeName = resourceMapper.selectBatteryTypeNameById(batteryTypeId);
        orderEntity.setBatteryType(BatteryTypeName);

        orderEntity.setCustomerName(request.getParameter("customerName").replaceAll("\\s",""));
        orderEntity.setCustomerCellphone(request.getParameter("cellphone").replaceAll("\\s",""));
        orderEntity.setCustomerTelephone(request.getParameter("telephone").replaceAll("\\s",""));



        String address= resourceMapper.selectProvinceName(request.getParameter("province"))+
                resourceMapper.selectCityName(request.getParameter("city"))+
                resourceMapper.selectAreaName(request.getParameter("area"))+
                request.getParameter("detailAddress").replaceAll("\\s","");
        orderEntity.setAddress(address);

        Integer automobileBrandId=Integer.valueOf(request.getParameter("automobileBrand"));
        String autoBrandName= resourceMapper.selectAutoBrandName(automobileBrandId);
        Integer automobileTypeId=Integer.valueOf(request.getParameter("automobileType"));
        String autoTypeName= resourceMapper.selectAutoTypeName(automobileTypeId);
        String automobileType=autoBrandName+autoTypeName;
        orderEntity.setAutomobileType(automobileType);

        orderEntity.setLicensePlateNumber(request.getParameter("licensePlateNumber").replaceAll("\\s",""));//去除空格

        orderEntity.setCityCode(request.getParameter("city"));

        Date createTime=null;

        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        try {
            createTime=TimeTool.stringToSqlDate(request.getParameter("createTime"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orderEntity.setCreateTime(createTime);


        orderEntity.setStatus(1);//状态：已录入
        orderEntity.setRemark(request.getParameter("remark").replaceAll("\\s",""));

        String wechatStatus=request.getParameter("wechatStatus");
        String wechatStatus1=request.getParameter("wechatStatus1");
        Integer wxStatus=Integer.parseInt(wechatStatus);
        orderEntity.setWechatStatus(wxStatus);

        if(orderService.selectOrderId(orderId)==null) {//检测是否为空
            result = orderService.importOrder(orderEntity);

            //同时向订单转发表中插入订单编号
            orderService.InsertOrderId(orderId);
        }



        return JsonTool.objectToJson(result);

    }

}
