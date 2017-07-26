package com.iot.dd.service;

import com.iot.dd.Dao.StockDao;
import com.iot.dd.domain.resource.BatteryStockEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/24.
 */
@Service
public class StockService {


    private StockDao stockDao;

    //返回多表查询结果集
    public List<BatteryStockEntity> findStockByCity(String cityCode){

      return   stockDao.findBatteryStockByCity(cityCode);

    }
    public List<BatteryStockEntity> findStockByType(String batteryType){

        return   stockDao.findBatteryStockByType(batteryType);

    }



}
