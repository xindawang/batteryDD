package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
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


    //android

    //按技师编号查找记录
    @Select("select * from indent_allocation where technician_id=#{technician}")
    @Results({
            @Result(column = "order_id", property="orderId"),
            @Result(column = "technician_id",property="technicianId"),
            @Result(column = "accept_time", property="acceptTime"),
            @Result(column = "technician_longitude",property="technicianLongitude"),
            @Result(column = "technician_latitude",property="technicianLatitude"),
            @Result(column = "customer_longitude",property="customerLongitude"),
            @Result(column = "customer_latitude",property="customerLatitude"),
    })
    List<IndentAllocationEntity> findindentAllocation(String technician);

    //按订单编号查找记录
    @Select("select * from indent_allocation where order_id=#{orderId}")
    @Results({
            @Result(column = "order_id", property="orderId"),
            @Result(column = "technician_id",property="technicianId"),
            @Result(column = "accept_time", property="acceptTime"),
            @Result(column = "technician_longitude",property="technicianLongitude"),
            @Result(column = "technician_latitude",property="technicianLatitude"),
            @Result(column = "customer_longitude",property="customerLongitude"),
            @Result(column = "customer_latitude",property="customerLatitude"),
    })
    IndentAllocationEntity findPosition(String orderId);

    //更新技师位置信息
    @Update("update indent_allocation set technician_longitude=#{longitude},technician_latitude=#{latitude} where technician_id=#{technicianId}")
    boolean setPosition(@Param("technicianId") String technicianId,@Param("longitude") double longitude,@Param("latitude") double latitude);

    //设置技师接受订单委派的时间
    @Update("update indent_allocation set accept_time=#{acceptTime} where order_id=#{orderId}")
    boolean setAcceptTime(@Param("orderId") String orderId, @Param("acceptTime") Date acceptTime);

    //技师拒绝接单
    @Delete("delete from indent_allocation where order_id=#{orderId}")
    boolean deleteAllocation(String orderId);

}
