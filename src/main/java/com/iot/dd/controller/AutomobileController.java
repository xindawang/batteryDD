package com.iot.dd.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.resource.*;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import com.iot.dd.dao.mapper.ResourceMapper;
import com.iot.dd.dao.mapper.automobileMapper;
import com.iot.dd.service.AutomobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by admin on 2017/9/25.
 */
@RestController
public class AutomobileController {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private automobileMapper mapper;

    @Autowired
    private AutomobileService autoService;

    //显示
    @RequestMapping(value = "/automobile", method = RequestMethod.GET)
    public String getAutomobile(int pageSize, int page) {
        PageHelper.startPage(page, pageSize);
        List<autoDetailEntity> list = mapper.findAuto();
        for (int i = 0; i < list.size(); i++) {
            int autoId = list.get(i).getId();
            List<AutomobileTypeBatteryEntity> Autolist = resourceMapper.selectAutomobileBattery(autoId);
            String battery = "";
            for (int k = 0; k < Autolist.size(); k++) {
                battery += resourceMapper.selectBatteryTypeNameById(Autolist.get(k).getBatteryTypeId());
                if (k < Autolist.size() - 1) {
                    battery += ",";
                }
            }
            list.get(i).setBatteryType(battery);
        }
        long total = ((Page<autoDetailEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
    }

    //删除
    @RequestMapping(value = "/automobileDelete", method = RequestMethod.POST)
    public String autoDelete(HttpServletRequest request, HttpServletResponse response) {
        String brand = request.getParameter("brand");
        String carType = request.getParameter("carType");
        String result = autoService.delete(brand, carType);
        if (result.equals("ok")) {
            return "删除成功！";
        } else {
            return "删除失败！";
        }
    }

    //添加车型
    @RequestMapping(value = "/carTypeAdd", method = RequestMethod.GET)
    public String AddCarType(HttpServletRequest request, HttpServletResponse response) {
        String brand = request.getParameter("brand");
        String carType = request.getParameter("carType");

        return autoService.addCar(brand, carType);
    }

    //修改车型名称
    @RequestMapping(value = "/carTypeModify", method = RequestMethod.POST)
    public String modifyType(HttpServletRequest request, HttpServletResponse response) {
        String brand = request.getParameter("brand");
        String carType = request.getParameter("carType");
        String oldcarType = request.getParameter("oldcarType");

        return autoService.updateCarType(carType, oldcarType, brand);
    }

    //显示电池
    @RequestMapping(value = "/BatteryShow", method = RequestMethod.POST)
    public String autoBatteryShow(HttpServletRequest request, HttpServletResponse response) {
        String brand = request.getParameter("brand");
        String carType = request.getParameter("carType");
        return autoService.showBattery(brand, carType);
    }

    //添加电池
    @RequestMapping(value = "/addBattery", method = RequestMethod.POST)
    public String addBattery(HttpServletRequest request, HttpServletResponse response) {
        String brand = request.getParameter("brand");
        String carType = request.getParameter("carType");
        String batteryId = request.getParameter("batteryId");
        return autoService.addBattery(brand, carType, batteryId);
    }

    //删除电池
    @RequestMapping(value = "/deleteBattery", method = RequestMethod.POST)
    public String deleteBattery(HttpServletRequest request, HttpServletResponse response) {
        String brand = request.getParameter("brand");
        String carType = request.getParameter("type");
        String[] batteryId = request.getParameterValues("battery");
        return autoService.deleBattery(brand, carType, batteryId);
    }


}
