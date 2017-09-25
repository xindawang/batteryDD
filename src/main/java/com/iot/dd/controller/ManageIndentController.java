package com.iot.dd.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.entity.resource.CityEntity;
import com.iot.dd.service.IndentManageService;
import com.iot.dd.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class ManageIndentController {
    @Autowired
    OrderService orderService;

    @Autowired
    IndentManageService indentManageService;

    @RequestMapping(value = "/manageSelectAllCity", method = RequestMethod.POST)
    String manageSelectIndent(HttpServletRequest request) {
        Integer status = Integer.parseInt(request.getParameter("status"));
        String type = request.getParameter("type");

        List<CityEntity> cityEntityList = new ArrayList<>();
        cityEntityList = orderService.selectCityByStatus(status, type);

        return JsonTool.objectToJson(cityEntityList);
    }


    //订单监控
    @RequestMapping(value = "/findIndent", method = RequestMethod.GET)
    public String findIndent(int pageSize, int page, HttpServletRequest request) {

        PageHelper.startPage(page, pageSize);
        String status = request.getParameter("indentStatus");
        int IntStatus = Integer.parseInt(status);
        String cityCode = request.getParameter("cityCode");
        String dateRegion = request.getParameter("dateRegion");
        List<OrderEntity> list = new ArrayList<>();


        list = indentManageService.findIndentMsg(IntStatus, cityCode, dateRegion);

        long total = ((Page<OrderEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        map.put("type", "classify");
        return JsonTool.objectToJson(map);
    }

    @RequestMapping(value = "/findIndentByNumber", method = RequestMethod.GET)
    String findIndentByNumber(int pageSize, int page, HttpServletRequest request) {
        PageHelper.startPage(page, pageSize);
        String Number = request.getParameter("searchNumber");
        List<OrderEntity> list = new ArrayList<>();

        list = indentManageService.findIndentByNumber(Number);

        long total = ((Page<OrderEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        map.put("type", "search");
        return JsonTool.objectToJson(map);
    }
}
