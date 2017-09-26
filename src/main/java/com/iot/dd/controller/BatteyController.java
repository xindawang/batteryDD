package com.iot.dd.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.resource.BatteryTypeEntity;
import com.iot.dd.service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: huanglin
 * Date: 2017/9/25
 * Time: 11:17
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class BatteyController {

    @Autowired
    private BatteryService batteryService;

    @RequestMapping(value = "/getAllBatteryType", method = RequestMethod.GET)
    public String findstaff(int pageSize, int page) {
        PageHelper.startPage(page, pageSize);
        List<BatteryTypeEntity> list = batteryService.getAllBatteryType();
        long total = ((Page<BatteryTypeEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
    }

    @RequestMapping(value = "/insertBatteryType", method = RequestMethod.POST)
    public String addBatteryType(HttpServletRequest request) {
        String batteryType = request.getParameter("batteryType");
        String batteryBrandName = request.getParameter("batteryBrandName");

        return JsonTool.objectToJson(batteryService.insertBatteryType(batteryType, batteryBrandName));
    }

    @RequestMapping(value = "/updateBatteryType", method = RequestMethod.POST)
    public String updateBatteryType(HttpServletRequest request) {
        String batteryType = request.getParameter("batteryType");
        String id = request.getParameter("batteryId");
        Integer batteryId = Integer.parseInt(id);
        batteryService.updateBatteryType(batteryType, batteryId);
        return JsonTool.objectToJson("修改成功");
    }

    @RequestMapping(value = "/deleteBatteryType", method = RequestMethod.POST)
    public String deleteBatteryType(HttpServletRequest request) {
        String id = request.getParameter("batteryId");
        Integer batteryId = Integer.parseInt(id);
        return JsonTool.objectToJson(batteryService.deleteBatteryType(batteryId));
    }

}
