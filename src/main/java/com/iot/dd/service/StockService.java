package com.iot.dd.service;

import com.iot.dd.dao.entity.resource.BatteryStockEntity;
import com.iot.dd.dao.mapper.ResourceMapper;
import com.iot.dd.dao.mapper.StockMapper;
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





   //根据城市编号及电池编号删除记录

    public String deletebatteryStock(String cityName, String batteryType, String provincenName) {
        String cityCode = resourcemapper.selectCityCode1(cityName, provincenName);
        Integer batteryId = resourcemapper.selectBatteryTypeId(batteryType);

        boolean t = stockMapper.deleteStock(cityCode, batteryId);
        if (t) {
            return "删除成功！";

        } else {
            return "删除失败！";

        }
    }

    public String updateBatteryStock(String cityName, String batteryType, String provincenName, String stock) {
        String cityCode = resourcemapper.selectCityCode1(cityName, provincenName);
        Integer batteryId = resourcemapper.selectBatteryTypeId(batteryType);
        Integer inventory = Integer.parseInt(stock);
        boolean t = stockMapper.updateStock(inventory, batteryId, cityCode);
        if (t) {
            return "库存更新成功";
        }
        return "库存更新失败";
    }

    public List<BatteryStockEntity> selectAllBatteryStock() {
        return stockMapper.selectAllBatteryStock();
    }

    public String addBatteryStock(Integer batteryId, String cityCode, Integer stock) {
        if (stockMapper.InsertBatteryStock(batteryId, cityCode, stock)) {
            return "库存添加成功";
        } else {
            return "库存添加失败，请重新添加";
        }
    }
}
