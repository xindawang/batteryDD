package com.iot.dd.service;

import com.iot.dd.dao.entity.Indent.ConfirmEntity;
import com.iot.dd.dao.mapper.confirmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/8/28.
 */

@Service
public class ConfirmService {
    @Autowired

    private confirmMapper mapper;

    public boolean addOneRecord(ConfirmEntity entity) {
        return mapper.addOne(entity);
    }

    //三张照片对应一条记录的三个字段
    //第一张照片插入记录、2,3张照片执行更新
    public Boolean addOneBatteryIMG(ConfirmEntity entity) {

        return mapper.addOneBatteryIMG(entity);
    }

    public Boolean addOneCarNumIMG(ConfirmEntity entity) {
        return mapper.addOneCarNumIMG(entity);
    }

    public Boolean addOneQualityIMG(ConfirmEntity entity) {
        return mapper.addOneQualityIMG(entity);
    }

    //设置图片路径
    public Boolean updateBatteryPath(String batteryPath) {
        return mapper.updateBatteryPath(batteryPath);
    }

    public Boolean updateCarNumPath(String carPath) {
        return mapper.updateCarNumPath(carPath);
    }

    public Boolean updateQualityPath(String qualityPath) {
        return mapper.updateQualityPath(qualityPath);
    }

    //查找记录

    public List<ConfirmEntity> find(String orderId, String technicianId) {
        return mapper.find(orderId, technicianId);
    }

}
