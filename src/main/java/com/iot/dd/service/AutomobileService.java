package com.iot.dd.service;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.resource.AutomobileBrand;
import com.iot.dd.dao.entity.resource.AutomobileType;
import com.iot.dd.dao.entity.resource.AutomobileTypeBatteryEntity;
import com.iot.dd.dao.entity.resource.BatteryEntity;
import com.iot.dd.dao.mapper.automobileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/9/27.
 */
@Service
public class AutomobileService {

    @Autowired
    private automobileMapper autoMapper;

    public String addCar(String brandName, String typeName) {
        List<AutomobileBrand> brandList = autoMapper.findAutomobileBrandList(brandName);
        boolean t = false;
        if (brandList.size() > 0) { //存在该品牌，则在该品牌下添加车型
            int brandId = brandList.get(0).getId();
            AutomobileType type = autoMapper.findAutomobileType(typeName,brandId);//判断该型号汽车是否存在
            if(type!=null){
                return "该型号汽车已存在！";
            }
            t = autoMapper.AddAutomobileType(typeName, brandId);
        } else { //先添加品牌，再添加车型
            autoMapper.AddAutomobileBrand(brandName);
            AutomobileBrand entity = autoMapper.findAutomobileOne(brandName);
            int brandId = entity.getId();
            t = autoMapper.AddAutomobileType(typeName, brandId);
        }
        if (t) {
            return "添加成功！！";
        } else {
            return "添加失败！！";
        }

    }

    public String delete(String brandName, String typeName) {

        AutomobileBrand auto = autoMapper.findAutomobileOne(brandName);
        AutomobileType type = autoMapper.findAutomobileType(typeName, auto.getId());//通过品牌和车型名称查找车型（车型名称可能不唯一）
        autoMapper.deleteAutomobileType(type.getId());
        List<AutomobileType> list = autoMapper.findAutomobileTypeList(auto.getId());
        if (list.size() < 1) {
            autoMapper.deleteAutomobileBrand(auto.getId());
        }
        return "ok";
    }

    public String updateCarType(String newName, String oldName, String brandName) {

        AutomobileBrand entity = autoMapper.findAutomobileOne(brandName);//查找品牌
        AutomobileType typeEntity = autoMapper.findAutomobileType(oldName, entity.getId());//通过旧型号名和品牌id确定记录
        typeEntity.setName(newName);
        boolean t = autoMapper.updateCarType(typeEntity);
        if (t) {

            return "修改成功！！";
        } else {
            return "修改失败！！";
        }

    }


    public String showBattery(String brand, String carType) {
        AutomobileBrand automobileBrand = autoMapper.findAutomobileOne(brand);
        AutomobileType entity = autoMapper.findAutomobileType(carType, automobileBrand.getId());
        List<AutomobileTypeBatteryEntity> autobattery = autoMapper.findAutomobileBattery(entity.getId());

        List<BatteryEntity> list = new ArrayList<>();
        for (int i = 0; i < autobattery.size(); i++) {
            BatteryEntity entity1 = autoMapper.findBatteryOne(autobattery.get(i).getBatteryTypeId());
            list.add(entity1);
        }
        return JsonTool.listToJson(list);
    }


    public String addBattery(String brand, String carType, String batteryId) {
        AutomobileBrand automobileBrand = autoMapper.findAutomobileOne(brand);
        AutomobileType entity = autoMapper.findAutomobileType(carType, automobileBrand.getId());
        List<AutomobileTypeBatteryEntity> list = autoMapper.getAutomobileBattery(entity.getId(), batteryId);
        if (list.size() > 0) {
            return "error1";
        } else {
            AutomobileTypeBatteryEntity entity1 = new AutomobileTypeBatteryEntity();
            entity1.setAutomobileTypeId(entity.getId());
            entity1.setBatteryTypeId(Integer.parseInt(batteryId));
            boolean t = autoMapper.addAutomobileBatteryItem(entity1);
            if (t) {
                return "ok";
            } else {
                return "error2";
            }
        }

    }

    public String deleBattery(String brand, String carType, String[] batteryId) {
        AutomobileBrand automobileBrand = autoMapper.findAutomobileOne(brand);
        AutomobileType entity = autoMapper.findAutomobileType(carType, automobileBrand.getId());
        int count = 0;  //删除该汽车适用的电池记录
        if (batteryId != null) {
            for (int i = 0; i < batteryId.length; i++) {
                boolean t = autoMapper.deleteAutomobileBatteryItem(entity.getId(), batteryId[i]);
                if (t) {
                    count++;
                }
            }
        }
        return "成功删除" + count + "条记录！";
    }

}
