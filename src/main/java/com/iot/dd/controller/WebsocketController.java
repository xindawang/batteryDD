package com.iot.dd.controller;


import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;

import com.alibaba.fastjson.JSON;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.mapper.OrderMapper;
import com.iot.dd.service.OrderService;
import com.iot.dd.service.weixin.IndentService;
import com.iot.dd.service.indentAllocationService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;


import java.text.SimpleDateFormat;
import java.util.List;

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
    indentAllocationService allocation;

    @Autowired
    OrderMapper orderMapper;

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
        System.out.println(chatMessage);
        template.convertAndSend("/topic/dis_tech" + allocationEntity.getTechnicianId(), chatMessage);//将消息转发给技师
        template.convertAndSend("/topic/dis_res" + allocationEntity.getOrderId(), "订单派发成功");//通过客服订单派发成功
    }



    @MessageMapping("/newIndent")
    public void newIndent(String msg) {


        System.out.println(msg);
        template.convertAndSend("/topic/cus_newIndent", msg);//
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

        String orderId = jsonObject.getString("orderId");
        String uLongitude = jsonObject.getString("longitude");
        String uLatitude = jsonObject.getString("latitude");
        System.out.println(orderId + uLatitude + uLongitude);
        Float longitude, latitude;
        if ((uLongitude != null) && (uLatitude != null)) {
            longitude = Float.parseFloat(uLongitude);
            latitude = Float.parseFloat(uLatitude);
            result = indentService.updateCustomerLocation(orderId, longitude, latitude);
        }

        String techId = indentService.getTechId(orderId);

        String cityName = indentService.getCityName(orderId);

        String chatMessage = "{\"latitude\":" + uLatitude + ",\"longitude\":" + uLongitude + ",\"cityName\":" + cityName + "}";
        System.out.println(chatMessage);
        //msg to json
        System.out.println("/topic/cus_location"+orderId+techId);
        template.convertAndSend("/topic/cus_location" + orderId, chatMessage);//将客户位置信息转发给技师
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
        template.convertAndSend("/topic/order_accept" + orderId, message);//通知客户，技师已经接单
        System.out.println("accept"+message);
    }

    /*技师通知位置改变，并提供位置信息
    *数据格式["technicianId":技师编号
    *         "latitude":纬度
    *         "longitude":经度]
    */
    @MessageMapping("/tech_position")
    public void technicianPosit(String message) {
        JSONObject jsonObject = JSONObject.fromObject(message);
        String technicianId = jsonObject.getString("technicianId");
        List<IndentAllocationEntity> listAllocation = allocation.findAllocation(technicianId);//查找与技师有关的订单
        for (int i = 0; i < listAllocation.size(); i++) {
            IndentAllocationEntity entity = listAllocation.get(i);
            OrderEntity order = orderMapper.findOrder(entity.getOrderId());//只给尚未完成的订单对应的用户，发送位置消息
            if (order.getStatus() == 3) {
                template.convertAndSend("/topic/tech_location" + order.getOrderId(), message);//将技师位置信息传给客户
            }
        }

        System.out.println("tech_Position"+message);
    }

    /*技师通知，电池安装完成
    * 数据格式 ["orderId":订单编号,
    *          "technicianId":技师编号,
    *         "message":电池安装完成]
    * */
    @MessageMapping("/orderFinish")
    public void technicianFinish(String message) {
        JSONObject jsonObject = JSONObject.fromObject(message);
        String orderId = jsonObject.getString("orderId");
        template.convertAndSend("/topic/order_finish" + orderId, message);//通知客户，技师已经接单
        System.out.println(message);
    }


    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public void test(String message) {
        System.out.println(message);
    }


}

