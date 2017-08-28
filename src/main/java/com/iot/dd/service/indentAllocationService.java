package com.iot.dd.service;

import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import com.iot.dd.dao.mapper.indentAllocationMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/7/31.
 */
@Service
public class indentAllocationService {

    @Autowired
    private indentAllocationMapper indentAllocatMapper;

    public List<IndentAllocationEntity> findOrder(){

        return indentAllocatMapper.findorderId();
    }

    public List<IndentAllocationEntity> findtechnicianId(){

        return indentAllocatMapper.findorderId();
    }

    //根据订单编号查找
    public IndentAllocationEntity find(String indentId){
       return indentAllocatMapper.find(indentId);
    }
    //根据技师编号多表查询
    public List<IndentAllocationEntity> findlist(String technician){
        return indentAllocatMapper.findlist(technician);
    }


    //android端
    /*
    * 根据技师编号单表查询
    * 目的是找与技师相关的记录
    * 这些记录分为两类：技师已经接受的订单/技师等待接受的订单（需要根据indent表中的Statues字段区分）
    * */
    public List<IndentAllocationEntity> findAllocation(String technician){
        return indentAllocatMapper.findindentAllocation(technician);
    }


    /*
    * 查找该记录中的技师和客户双方的实时位置
    * 在技师派单时，用于地图显示
    * */
    public IndentAllocationEntity findPosition(String orderId)
    {
        return indentAllocatMapper.findPosition(orderId);
    }
    /*
    * 实时更新indentAllocation表中技师的经纬度信息
    * 此方法在UserInforManageController中被调用
    * */
    public void SetTechnicianPosition(String technicianId,double longitude,double latitude){
        indentAllocatMapper.setPosition(technicianId,longitude,latitude);
    }

    /*
    * 技师接受订单委派，设置接单时间
    * */

    public  void setAcceptTime(String orderId,Date acceptTime){
        indentAllocatMapper.setAcceptTime(orderId,acceptTime);
    }

    /*
    * 技师拒绝接受委派订单任务是删除记录
    * */
    public boolean deleteByOrderId(String orderId,String technicianId){
        return indentAllocatMapper.deleteAllocation(orderId,technicianId);

    }


}
