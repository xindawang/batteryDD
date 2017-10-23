package com.iot.dd.service;

import com.iot.dd.dao.entity.resource.BatteryTypeEntity;
import com.iot.dd.dao.mapper.ResourceMapper;
import com.iot.dd.dao.mapper.StockMapper;
import com.iot.dd.dao.mapper.automobileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: huanglin
 * Date: 2017/9/25
 * Time: 12:22
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BatteryService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private automobileMapper autoMapper;

    public List<BatteryTypeEntity> getAllBatteryType() {
        return resourceMapper.selectAllBatteryType();
    }

    public String insertBatteryType(String batteryType, String brandName) {
        String result = "电池型号添加成功";
        batteryType = batteryType.trim();
        brandName = brandName.trim();
        if (resourceMapper.selectBatteryBrandNameByName(brandName) == null) {
            resourceMapper.insertBatteryBrand(brandName);
        }
        if (resourceMapper.selectBatteryTypeNameByType(batteryType) == null) {
            Integer brandId = resourceMapper.selectBatteryBrandIdByName(brandName);
            resourceMapper.insertBattery(batteryType, brandId);
        } else {
            result = "电池型号已存在";
        }
        return result;
    }

    public String updateBatteryType(String batteryType, Integer batteryId) {
        //
        String result="电池型号修改成功";
        if (resourceMapper.selectBatteryTypeNameByType(batteryType) == null){
            resourceMapper.updateBattery(batteryType, batteryId);
        }else{
            result="该电池型号已经存在";
        }
        return result;

    }

    public String deleteBatteryType(Integer batteryId) {
        Integer brandId=resourceMapper.selectBatteryBrandIdById(batteryId);
        if (resourceMapper.selectBatteryTypeNameById(batteryId) == null) {
            return "汽车型号不存在，请刷新试试。";
        } else {
            stockMapper.deleteAllStockByBatteryId(batteryId);//删除电池型号，同时删除该型号下的库存
            autoMapper.deleteAutoBatteryByBatteryId(batteryId+"");//删除适用该型号电池的汽车记录（Automobile_Battery 表，注意不是删除汽车车型）
            resourceMapper.deleteBattery(batteryId);

            if(resourceMapper.selectBatteryBrandIdByBrandId(brandId) ==null){
                 resourceMapper.deleteBrand(brandId);
            }
            return "汽车型号成功删除";
        }
    }
}
