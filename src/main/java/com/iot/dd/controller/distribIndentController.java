package com.iot.dd.controller;

import com.iot.dd.dao.mapper.ResourceMapper;
import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanglin on 2017/7/24.
 */
@RestController
public class distribIndentController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ResourceMapper resourceMapper;

    @RequestMapping(value="/selectIndentMsg",method= RequestMethod.POST)
    String selectUndoneIndent(HttpServletRequest request) {
        String undoneStatus = resourceMapper.selectStatus(1);
        List<OrderEntity> UndoneIndentMsg = orderService.selectUndoneIndentMsg(undoneStatus);
        List<String> undoneIndentId =new ArrayList<>();
        if (UndoneIndentMsg != null) {
            for (OrderEntity order : UndoneIndentMsg) {
                    undoneIndentId.add(order.getOrderId());
                }
            }

            return JsonTool.objectToJson(undoneIndentId);
        }


    @RequestMapping(value = "/importIndentMsg" ,method=RequestMethod.POST)
    String importUndoneIndentMsg(HttpServletRequest request){
        String orderId=request.getParameter("indentId");
        String undoneIndentState= resourceMapper.selectStatus(1);
        List<OrderEntity> undoneIndentMessage=orderService.importUndoneIndentMsg(undoneIndentState,orderId);

        orderService.InsertOrderId(orderId);

        return JsonTool.objectToJson(undoneIndentMessage);
    }
//
//    @RequestMapping(value="/importMapMessage" ,method=RequestMethod.POST)
//    String
}
