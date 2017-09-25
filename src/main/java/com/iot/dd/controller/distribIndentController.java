package com.iot.dd.controller;

import com.iot.dd.dao.entity.resource.CityEntity;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import com.iot.dd.dao.mapper.ResourceMapper;
import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.service.OrderService;
import net.sf.json.JSONObject;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
/**
 * Created by huanglin on 2017/7/24.
 */
//@ServerEndpoint注解是一个类层次的注解，它的功能主要将目前的类定义成一个websocket服务器端，
//注解的值将被用于监听用户的终端访问URL地址，客户端可以通过这个URL来连接到webSocket服务器
@ServerEndpoint("/websocket")
@RestController
public class distribIndentController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ResourceMapper resourceMapper;

    @RequestMapping(value = "/selectIndentMsg", method = RequestMethod.POST)
    String selectUndoneIndent(HttpServletRequest request) {
        Integer undoneStatus =1;
        List<String> undoneIndentId = orderService.selectUndoneIndentOrderId(undoneStatus);

        return JsonTool.objectToJson(undoneIndentId);
    }


    @RequestMapping(value="/getHaveIndentOfCity",method=RequestMethod.POST)
    String getHaveIndentOfCity(HttpServletRequest request){
        Integer status = Integer.parseInt(request.getParameter("status"));
        String type = request.getParameter("type");
        List<CityEntity> cityEntityList = orderService.selectCityByStatus(status, type);
        return JsonTool.objectToJson(cityEntityList);
    }

    @RequestMapping(value="/getIndentIdByCityCode",method=RequestMethod.POST)
    String getIndentIdByCityCOde(HttpServletRequest request){
        String cityCode=request.getParameter("cityCode");
        List<OrderEntity> orderEntities=orderService.selectOrderIdByCity(cityCode);
        return JsonTool.objectToJson(orderEntities);
    }

//在前端选择订单编号中时，向前端传递用户和订单的相关信息
    @RequestMapping(value = "/importIndentMsg" ,method=RequestMethod.POST)
    String importUndoneIndentMsg(HttpServletRequest request){
        String orderId=request.getParameter("indentId");
        Integer undoneIndentState=1;//已录入
        List<OrderEntity> undoneIndentMessage=orderService.importUndoneIndentMsg(undoneIndentState,orderId);


        return JsonTool.objectToJson(undoneIndentMessage);
    }

    @RequestMapping(value="/getCustomerLocation" ,method=RequestMethod.POST)
    public String getCustomerLocation(HttpServletRequest request){
        String orderId=request.getParameter("orderId");
        Map<String ,Float> cusLocation=new HashMap<>();
        cusLocation=orderService.selectCustomerLocation(orderId);
        return JsonTool.objectToJson(cusLocation);
    }

    @RequestMapping(value="/importTechMsgFromCity",method=RequestMethod.POST)
    public String importTechMsgFormCity(HttpServletRequest request){
        String cityCode=request.getParameter("cityCode");
        List<TechnicianEntity> techMsg=orderService.selectTechMsg(cityCode);

        return JsonTool.objectToJson(techMsg);

    }

    @RequestMapping(value="/allocationIndent",method=RequestMethod.POST)
    public String  allocationIndent(HttpServletRequest request){

        String indentId=request.getParameter("indentId");
        String techId=request.getParameter("techId");
        orderService.allocIndent(indentId,techId);

        return JsonTool.objectToJson("success");

    }

//    @RequestMapping(value="/getHaveUnIndentOfCity",method = RequestMethod.POST)
//    public String getHaveUnIndentOfCity(HttpServletRequest request){
//
//    }










}
