package com.iot.dd.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.resource.BatteryStockEntity;
import com.iot.dd.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/24.
 */

@RestController
public class BatteryAmountController {

    @Autowired
    private StockService stock;

     //按城市查询库存
    @RequestMapping(value="/batteryAmountCity",method = RequestMethod.GET )
    public String findStockByCity(int pageSize, int page,HttpServletRequest request){

        String cityCode=request.getParameter("cityCode");
        PageHelper.startPage(page, pageSize);

        List<BatteryStockEntity> list = stock.findStockByCity(cityCode);
        long total = ((Page<BatteryStockEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
    }

    //按电池型号查询库存
    @RequestMapping(value="/batteryAmountType",method = RequestMethod.GET )
    public String findStockByType(int pageSize, int page,HttpServletRequest request){

        String batteryType=request.getParameter("batteryType");
        PageHelper.startPage(page, pageSize);

        List<BatteryStockEntity> list = stock.findStockByType(Integer.parseInt(batteryType));
        long total = ((Page<BatteryStockEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
    }

    //删除库存
    @RequestMapping(value="/batteryAmountDelete",method = RequestMethod.GET )
    public String deletebattery(int pageSize, int page,HttpServletRequest request){
     String cityName=request.getParameter("cityName");
     String batteryType=request.getParameter("batteryType");

        Integer batteryId=stock.findbatteryId(batteryType);
        String cityCode=stock.findcityId(cityName);
        return stock.deletebatteryStock(cityCode,batteryId);
    }

    //编辑库存量
    @RequestMapping(value="/batteryAmountEdit",method = RequestMethod.GET )
    public String Addbatterycount(int pageSize, int page,HttpServletRequest request){


        return "";
    }

    //增加一个城市的电池库
    @RequestMapping(value="/batteryAmountAdd",method = RequestMethod.GET )
    public String Addbattery(int pageSize, int page,HttpServletRequest request){


        return "";
    }




}
