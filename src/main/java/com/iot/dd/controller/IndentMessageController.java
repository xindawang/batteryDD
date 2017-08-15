package com.iot.dd.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import com.iot.dd.dao.mapper.OrderMapper;
import com.iot.dd.service.IndentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/23.
 */
@RestController
public class IndentMessageController {
    @Autowired
    private IndentManageService indentService;


    //订单监控
   @RequestMapping(value="/findIndent",method = RequestMethod.GET)
    public  String findIndent(int pageSize, int page,HttpServletRequest request){

       PageHelper.startPage(page, pageSize);
       String state=request.getParameter("indentState");
       int IntState=Integer.parseInt(state);
       String cityCode=request.getParameter("cityCode");
       List<OrderEntity> list;

        //0表示不安按条件查询
       if(cityCode.equals("undefined")){
           cityCode="0";
       }
       if(cityCode.equals("0")&&state.equals("0")){
           return "";
       }
       if(!cityCode.equals("0")&&state.equals("0")){//按城市查询

           list=indentService.findIndentByCity(cityCode);
           long total = ((Page<OrderEntity>) list).getTotal();
           Map<String, Object> map = new HashMap<>();
           map.put("list", list);
           map.put("total", total);
           return JsonTool.objectToJson(map);

       }
       if(cityCode.equals("0")&&!state.equals("0")){//按状态查询


           list=indentService.findIndentByState(IntState);
           long total = ((Page<OrderEntity>) list).getTotal();
           Map<String, Object> map = new HashMap<>();
           map.put("list", list);
           map.put("total", total);
           return JsonTool.objectToJson(map);
       }
      else{
           list=indentService.findIndentByStateAndCity(IntState,cityCode);
           long total = ((Page<OrderEntity>) list).getTotal();
           Map<String, Object> map = new HashMap<>();
           map.put("list", list);
           map.put("total", total);
           return JsonTool.objectToJson(map);
       }
   }



}
