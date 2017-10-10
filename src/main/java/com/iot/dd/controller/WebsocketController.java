package com.iot.dd.controller;


import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;

import com.alibaba.fastjson.JSON;
import com.iot.dd.service.OrderService;
import com.iot.dd.service.weixin.IndentService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;


import java.text.SimpleDateFormat;

/**
 * Created by huanglin on 2017/8/15.
 */
@RestController
public class WebsocketController {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private OrderService orderService;

    @Autowired
    private IndentService indentService;


    @MessageMapping("/all")
    public void all(String msg) {
        //BaseMessage baseMessage=JSON.parseObject(msg,BaseMessage.class);


        String chatMessage = "有消息吧";
        template.convertAndSend("/topic/notice" + msg, msg);
    }


    @MessageMapping("/distributeIndent")
    public void distributeIndent(String msg) {

        IndentAllocationEntity allocationEntity = JSON.parseObject(msg, IndentAllocationEntity.class);
        String indentId = allocationEntity.getOrderId();
        String techId = allocationEntity.getTechnicianId();

        orderService.allocIndent(indentId, techId);
        String chatMessage = "{\"orderId\":\"" + indentId + "\"}";
        template.convertAndSend("/topic/dis_tech" + allocationEntity.getTechnicianId(), chatMessage);//将消息转发给技师
        template.convertAndSend("/topic/dis_res" + allocationEntity.getOrderId(), "订单派发成功");//通过客服订单派发成功
    }


    /*将具体的订单对应的客户的地理位置信息传给技师
    * 路经："/topic/dis_tech" + 技师编号+ 订单编号
    * 期望的数据格式：["latitude":纬度
    *                 "longitude":经度]
    * */
    @MessageMapping("/customer_position")
    public void sendPosition(String msg) {
        String result;

        JSONObject jsonObject = JSONObject.fromObject(msg);

        String orderId=jsonObject.getString("orderId");
        String uLongitude=jsonObject.getString("longitude");
        String uLatitude=jsonObject.getString("latitude");
        System.out.println(orderId+uLatitude+uLongitude);
        Float longitude,latitude;
        if((uLongitude!=null)  &&(uLatitude  != null)){
            longitude=Float.parseFloat(uLongitude);
            latitude=Float.parseFloat(uLatitude);
            result=indentService.updateCustomerLocation(orderId,longitude,latitude);
        }

        String techId=indentService.getTechId(orderId);

        String cityName=indentService.getCityName(orderId);

        String chatMessage="{\"latitude\":"+uLatitude+",\"longitude\":"+uLongitude+",\"cityName\":"+cityName+"}";

        //msg to json
        template.convertAndSend("/topic/cus_location" +techId+orderId, chatMessage);//将客户位置信息转发给技师
    }



    /*
    * android
    * */

    /*
   * 技师接单
   * 数据格式 ["orderId":订单编号,
   *          "technicianId":技师编号,
   *         "message":技师已接单]
   *
   * */
    @MessageMapping("/tech_accept")
    public void technicianAceept(String message) {
        JSONObject jsonObject = JSONObject.fromObject(message);
        String orderId = jsonObject.getString("orderId");
        String technicianId=jsonObject.getString("technicianId");
        template.convertAndSend("/topic/dis_custom" +technicianId+ orderId, message);//通知客户，技师已经接单
        System.out.println(jsonObject.getString("message"));
    }

    /*技师通知位置改变，并提供位置信息
    *数据格式["technicianId":技师编号
    *         "latitude":纬度
    *         "longitude":经度]
    */
    @MessageMapping("/tech_Position")
    public void technicianPosit(String message) {
        //解析message 获取technicianId   查询数据库 获得客户id  通知客户技师位置改变
        JSONObject jsonObject = JSONObject.fromObject(message);
        String technicianId = jsonObject.getString("technicianId");
        System.out.println(technicianId + "地理位置");
        System.out.println(message);
        //考虑在此处将技师地理位置保存数据库（更新）
        template.convertAndSend("/topic/dis_custom" + technicianId, message);//将技师位置信息传给客户
    }


    /*技师通知客户，正在前往安装电池
    * 数据格式 ["orderId":订单编号,
    *          "technicianId":技师编号,
    *         "message":正在前往安装]
    * */
    @MessageMapping("/tech_conduct")
    public void technicianGoWork(String message) {

        JSONObject jsonObject = JSONObject.fromObject(message);
        String orderId = jsonObject.getString("orderId");
        System.out.println(jsonObject.getString("message"));
        template.convertAndSend("/topic/order_start" + orderId, message);//通知客户，技师已经接单
    }


    /*技师通知，电池安装完成
    * 数据格式 ["orderId":订单编号,
    *          "technicianId":技师编号,
    *         "message":电池安装完成]
    * */
    @MessageMapping("/tech_finish")
    public void technicianFinish(String message) {
        JSONObject jsonObject = JSONObject.fromObject(message);
        String orderId = jsonObject.getString("orderId");
        System.out.println(jsonObject.getString("message"));
        template.convertAndSend("/topic/order_finish" + orderId, message);//通知客户，技师已经接单
    }

    /*
    * 技师下线通知/技师上线通知
    *数据格式 [
    *          "technicianId":技师编号,
    *         "message":开工or收工]
    * */

    @MessageMapping("/tech_Work")
    public void technicianWork(String message) {
        JSONObject jsonObject = JSONObject.fromObject(message);
        String technicianId=jsonObject.getString("technicianId");
        System.out.println(jsonObject.getString("message"));
        template.convertAndSend("/topic/tech_work" + technicianId, message);//通知客户，技师已经接单
    }

}

