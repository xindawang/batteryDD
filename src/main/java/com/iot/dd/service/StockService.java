package com.iot.dd.service;

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

    //返回多表查询结果集
    public List<BatteryStockEntity> findStockByCity(String cityCode){

      return   stockMapper.findBatteryStockByCity(cityCode);

    }
    public List<BatteryStockEntity> findStockByType(String batteryType){

        return   stockMapper.findBatteryStockByType(batteryType);

    }



}
