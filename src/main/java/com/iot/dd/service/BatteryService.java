package com.iot.dd.service;

import com.iot.dd.dao.entity.resource.BatteryTypeEntity;
import com.iot.dd.dao.mapper.ResourceMapper;
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

    public void updateBatteryType(String batteryType, Integer batteryId) {
        resourceMapper.updateBattery(batteryType, batteryId);
    }

    public String deleteBatteryType(Integer batteryId) {
        if (resourceMapper.selectBatteryTypeNameById(batteryId) == null) {
            return "汽车型号不存在，请刷新试试。";
        } else {
            resourceMapper.deleteBattery(batteryId);
            return "汽车型号成功删除";
        }
    }
}
