package com.iot.dd.Controller;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.domain.resource.AreaEntity;
import com.iot.dd.domain.resource.CityEntity;
import com.iot.dd.domain.resource.ProvinceEntity;
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



}


