package com.iot.dd.controller;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import com.iot.dd.service.indentAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 2017/7/31.
 */
@RestController
public class indentAllocationController {

    @Autowired
    private indentAllocationService service;

    //设置订单编号select选择框
    @RequestMapping(value="/OrderId",method = RequestMethod.GET)
    public String findOrderId(HttpServletRequest request){

        return JsonTool.listToJson(service.findOrder());
    }

    //设置技师名称select选择框
    @RequestMapping(value="/technicianId",method = RequestMethod.GET)
    public String findtechnician(HttpServletRequest request){

        return JsonTool.listToJson(service.findtechnicianId());
    }

    //根据订单编号查找转发记录
    @RequestMapping(value="/findByOrderId",method = RequestMethod.GET)
    public String findByOrderId(HttpServletRequest request){
       String orderId=request.getParameter("orderId");

       return JsonTool.javaBeanToJson(service.find(orderId));
    }

    //技师编号查找转发记录
    @RequestMapping(value="/findByTchnicianId",method = RequestMethod.GET)
    public String findBytchnicianId(HttpServletRequest request){
        String technicianId=request.getParameter("technicianId");
        return JsonTool.listToJson(service.findlist(technicianId));
    }

}
