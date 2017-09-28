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
     @RequestMapping(value = "/getBatteryAmountByCity", method = RequestMethod.GET)
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



    //删除库存
    @RequestMapping(value = "/batteryAmountDelete", method = RequestMethod.POST)
    public String deletebattery(HttpServletRequest request) {
     String cityName=request.getParameter("cityName");
     String batteryType=request.getParameter("batteryType");
        String provinceName = request.getParameter("province");
        return stock.deletebatteryStock(cityName, batteryType, provinceName);
    }

    //编辑库存量
    @RequestMapping(value = "/updateBatteryStock", method = RequestMethod.POST)
    public String Addbatterycount(HttpServletRequest request) {
        String province = request.getParameter("province");
        String cityName = request.getParameter("cityName");
        String batteryType = request.getParameter("batteryType");
        String inventory = request.getParameter("stock");
        String result = stock.updateBatteryStock(cityName, batteryType, province, inventory);
        return JsonTool.objectToJson(result);


    }


    @RequestMapping(value = "/getAllBatteryAmount", method = RequestMethod.GET)
    public String getAllBatteryAmount(int pageSize, int page, HttpServletRequest request) {
        PageHelper.startPage(page, pageSize);
        List<BatteryStockEntity> list = stock.selectAllBatteryStock();
        long total = ((Page<BatteryStockEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);

    }

    @RequestMapping(value = "/addBatteryAmount", method = RequestMethod.POST)
    public String AddBatteryStock(HttpServletRequest request) {
        String id = request.getParameter("batteryType");
        Integer batteryId = Integer.parseInt(id);
        String cityCode = request.getParameter("cityCode");
        String batteryStock = request.getParameter("batteryStock");
        Integer batteryInventory = null;
        if (batteryStock != null) {
            batteryInventory = Integer.parseInt(batteryStock);
        }


        return JsonTool.objectToJson(stock.addBatteryStock(batteryId, cityCode, batteryInventory));
    }



}
