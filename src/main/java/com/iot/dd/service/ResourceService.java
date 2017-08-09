package com.iot.dd.service;

import com.iot.dd.dao.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2017/8/2.
 */
@Service
public class ResourceService {
    @Autowired
    private ResourceMapper resourcemapper;




    //更具城市名获取cityCode
    public String  findCityCODE(String cityName){
        return resourcemapper.selectCityCode(cityName);
    }


}


