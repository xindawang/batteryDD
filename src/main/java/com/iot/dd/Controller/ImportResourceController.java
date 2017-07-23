package com.iot.dd.Controller;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.domain.resource.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

import com.iot.dd.Dao.ResourceDao;
/**
 * Created by huanglin on 2017/7/20.
 */
@RestController
public class ImportResourceController {

    @Autowired
    private ResourceDao resourceDao;
    @RequestMapping(value="/importProvince", method=RequestMethod.POST)
    String getProvince(HttpServletRequest request){
        List<ProvinceEntity> provinceList=resourceDao.selectProvince();

        return JsonTool.objectToJson(provinceList);
    }


    @RequestMapping(value="/importCity",method=RequestMethod.POST)
    String getCity(HttpServletRequest request){
        String provinceCode =request.getParameter("provinceCode");
        List<CityEntity> cityList=resourceDao.selectCity(provinceCode);
        return JsonTool.objectToJson(cityList);
    }


    @RequestMapping(value="/importArea",method=RequestMethod.POST)
    String getArea(HttpServletRequest request){
        String cityCode =request.getParameter("cityCode");
        List<AreaEntity> areaList=resourceDao.selectArea(cityCode);
        return JsonTool.objectToJson(areaList);
    }


    @RequestMapping(value="/importAutoBrand", method=RequestMethod.POST)
    String getAutoBrand(HttpServletRequest request){
        List<AutomobileBrand> AutoBrandList=resourceDao.selectAutoBrand();

        return JsonTool.objectToJson(AutoBrandList);
    }

    @RequestMapping(value="/importAutoType",method=RequestMethod.POST)
    String getAutoType(HttpServletRequest request){
        Integer brandId =Integer.valueOf(request.getParameter("automobileBrand"));
        List<AutomobileType> AutoTypeList=resourceDao.selectAutoType(brandId);
        return JsonTool.objectToJson(AutoTypeList);
    }

    @RequestMapping(value="/importBatteryBrand", method=RequestMethod.POST)
    String getBatteryBrand(HttpServletRequest request){
        List<BatteryBrandEntity> batteryBrandList=resourceDao.selectBatteryBrand();

        return JsonTool.objectToJson(batteryBrandList);
    }


    @RequestMapping(value="/importBatteryType", method=RequestMethod.POST)
    String getBatteryType(HttpServletRequest request){
        Integer batteryBrandId=Integer.valueOf(request.getParameter("batteryBrand"));
        List<BatteryEntity> batteryList=resourceDao.selectBatteryType(batteryBrandId);

        return JsonTool.objectToJson(batteryList);
    }
}


