package com.iot.dd.service;

import com.iot.dd.dao.entity.resource.CityEntity;
import com.iot.dd.dao.mapper.ResourceMapper;
import com.iot.dd.dao.mapper.StockMapper;
import com.iot.dd.dao.entity.resource.BatteryStockEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/24.
 */
@Service
public class StockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private ResourceMapper resourcemapper;

    //返回多表查询结果集
    public List<BatteryStockEntity> findStockByCity(String cityCode){

      return   stockMapper.findBatteryStockByCity(cityCode);

    }
    public List<BatteryStockEntity> findStockByType(Integer batteryType){

        return   stockMapper.findBatteryStockByType(batteryType);

    }

    //根据城市名找城市编号
    public String findcityId(String cityName){

        return  resourcemapper.selectCityCode(cityName);

    }

    //根据电池型号找其编号
    public Integer findbatteryId(String batteryType){

        return  resourcemapper.selectBatteryTypeId(batteryType);

    }

   //根据城市编号及电池编号删除记录

    public String deletebatteryStock(String cityCode,Integer batteryId) {
        boolean t = stockMapper.deleteStock(cityCode, batteryId);
        if (t) {
            return "删除成功！";

        } else {
            return "删除失败！";

        }
    }
}
