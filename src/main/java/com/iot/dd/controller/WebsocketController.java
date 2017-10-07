package com.iot.dd.controller;

import com.alibaba.fastjson.JSONArray;
import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import com.iot.dd.dao.entity.websocket.BaseMessage;
// import net.sf.json.JSON;
import com.alibaba.fastjson.JSON;
import com.iot.dd.service.OrderService;
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



    @MessageMapping("/all")
    public void all( String msg) {
        BaseMessage baseMessage=JSON.parseObject(msg,BaseMessage.class);


        String chatMessage = "有消息吧";
        template.convertAndSend("/topic/notice"+msg, msg);
    }



    @MessageMapping("/distributeIndent")
    public void distributeIndent( String msg) {
        //JSONArray jsonObject=JSON.parseArray(msg);
        IndentAllocationEntity allocationEntity=JSON.parseObject(msg,IndentAllocationEntity.class);
        String indentId=allocationEntity.getOrderId();
        String techId=allocationEntity.getTechnicianId();

        orderService.allocIndent(indentId,techId);
        String chatMessage = "{\"orderId\":\""+indentId+"\"}";
        template.convertAndSend("/topic/dis_tech"+allocationEntity.getTechnicianId(),chatMessage);//将消息转发给技师

        template.convertAndSend("/topic/dis_res"+allocationEntity.getOrderId(),"订单派发成功");//通过客服订单派发成功
    }





}

