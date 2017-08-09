package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by admin on 2017/7/31.
 */
@Mapper
public interface indentAllocationMapper {

   //查找所有的订单号
    List<IndentAllocationEntity> findorderId();

    //查找技师编号
    List<IndentAllocationEntity> findtechnicianId();


   //按订单编号查询订单转发记录
    IndentAllocationEntity find(String indentId);
    //按技师编号查询订单转发记录
    List<IndentAllocationEntity> findlist(String technicianId);

}
