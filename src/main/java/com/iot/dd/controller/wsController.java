package com.iot.dd.controller;

import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huanglin on 2017/8/15.
 */
@RestController
public class wsController {
    //)@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
    @MessageMapping("/sendMsgToB")
    @SendTo("/topic/MsgFromA")
    public String setCusLocation(IndentAllocationEntity entity){
        System.out.println("有地址吗");
        Float cusLongitude=entity.getCustomerLongitude();//经度
        Float cusLatitude=entity.getCustomerLatitude();//纬度


        return "后端数据";
    }
}
