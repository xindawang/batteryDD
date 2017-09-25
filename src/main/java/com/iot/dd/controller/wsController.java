package com.iot.dd.controller;

import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Created by huanglin on 2017/8/15.
 */
@RestController
public class wsController {

//    @MessageMapping("/welcome")
//    @SendTo("/topic/getResponse")
//    public String setCusLocation(IndentAllocationEntity entity){
//        System.out.println("有地址吗");
//        Float cusLongitude=entity.getCustomerLongitude();//经度
//        Float cusLatitude=entity.getCustomerLatitude();//纬度
//
//
//        return "后端数据";
//   }

    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public String setCusLocation(String data){ //data="lat,log"

        System.out.println(data);

        return data;
    }


}

