package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huanglin on 2017/7/21.
 */
@Repository
public interface OrderMapper {
    @Insert("insert into indent (ORDER_ID,BATTERY_TYPE,CUSTOMER_NAME,CUSTOMER_CELLPHONE" +
            ",CUSTOMER_TELEPHONE,WECHAT_ID,ADDRESS,AUTOMOBILE_TYPE,LICENSE_PLATE_NUMBER,CITY_CODE,CREATE_TIME" +
            ",STATUS,REMARK) values(#{orderId},#{batteryType},#{customerName},#{customerCellphone}" +
            ",#{customerTelephone},#{wechatId},#{address},#{automobileType},#{licensePlateNumber}" +
            ",#{cityCode},#{createTime},#{status},#{remark})")
    boolean addOrder(OrderEntity orderEntity);

    //根据订单状态查询所有订单信息
    @Select("select * from indent where status=#{status}")
            @Results({
                    @Result(property = "orderId", column = "order_id"),
                    @Result(property="batteryType",column="battery_type"),
                    @Result(property ="customerName",column="customer_name"),
                    @Result(property = "cityCode",column="city_code"),
                    @Result(property = "customerCellphone",column="customer_cellphone"),
                    @Result(property = "customerTelephone",column="customer_telephone"),
                    @Result(property = "wechatId",column="wechat_id"),
                    @Result(property = "automobileType",column="automobile_type"),
                    @Result(property = "licensePlateNumber",column="license_plate_number"),
                    @Result(property = "createTime",column="create_time"),
                    @Result(property = "finishTime",column="finish_time"),
            })
    List<OrderEntity> selectIndentMsg(String status);


    //根据订单号查询所有未派发订单信息
    @Select("select * from indent where STATUS=#{status} AND ORDER_ID=#{orderId}")
    @Results({
            @Result(property = "orderId", column = "order_id"),
            @Result(property="batteryType",column="battery_type"),
            @Result(property ="customerName",column="customer_name"),
            @Result(property = "cityCode",column="city_code"),
            @Result(property = "customerCellphone",column="customer_cellphone"),
            @Result(property = "customerTelephone",column="customer_telephone"),
            @Result(property = "wechatId",column="wechat_id"),
            @Result(property = "automobileType",column="automobile_type"),
            @Result(property = "licensePlateNumber",column="license_plate_number"),
            @Result(property = "createTime",column="create_time"),
            @Result(property = "finishTime",column="finish_time"),
    })
    List<OrderEntity> importIndentMsg(@Param("status") String status,@Param("orderId") String orderId);


    //根据订单所在城市查询所有订单
    @Select("select * from indent where CITY_CODE=#{cityCode} ")
    @Results({
            @Result(property = "orderId", column = "order_id"),
            @Result(property="batteryType",column="battery_type"),
            @Result(property ="customerName",column="customer_name"),
            @Result(property = "cityCode",column="city_code"),
            @Result(property = "customerCellphone",column="customer_cellphone"),
            @Result(property = "customerTelephone",column="customer_telephone"),
            @Result(property = "wechatId",column="wechat_id"),
            @Result(property = "automobileType",column="automobile_type"),
            @Result(property = "licensePlateNumber",column="license_plate_number"),
            @Result(property = "createTime",column="create_time"),
            @Result(property = "finishTime",column="finish_time"),
    })
    List<OrderEntity> selectIndentByCity(String cityCode);

    //根据订单所在城市和订单状态查询所有订单
    @Select("select * from indent where STATUS=#{status} and CITY_CODE=#{cityCode} ")
    @Results({
            @Result(property = "orderId", column = "order_id"),
            @Result(property="batteryType",column="battery_type"),
            @Result(property ="customerName",column="customer_name"),
            @Result(property = "cityCode",column="city_code"),
            @Result(property = "customerCellphone",column="customer_cellphone"),
            @Result(property = "customerTelephone",column="customer_telephone"),
            @Result(property = "wechatId",column="wechat_id"),
            @Result(property = "automobileType",column="automobile_type"),
            @Result(property = "licensePlateNumber",column="license_plate_number"),
            @Result(property = "createTime",column="create_time"),
            @Result(property = "finishTime",column="finish_time"),
    })
    List<OrderEntity> selectIndentByStatusAndCity(@Param("status") String status,@Param("cityCode") String cityCode);

    //根据客户从微信发送过来的电话查询订单是否存在
    @Select("select * from indent where customer_cellphone=#{telephone} OR customer_telephone=#{telephone}  ")
    @Results({
            @Result(property = "orderId", column = "order_id"),
            @Result(property="batteryType",column="battery_type"),
            @Result(property ="customerName",column="customer_name"),
            @Result(property = "cityCode",column="city_code"),
            @Result(property = "customerCellphone",column="customer_cellphone"),
            @Result(property = "customerTelephone",column="customer_telephone"),
            @Result(property = "wechatId",column="wechat_id"),
            @Result(property = "automobileType",column="automobile_type"),
            @Result(property = "licensePlateNumber",column="license_plate_number"),
            @Result(property = "createTime",column="create_time"),
    })
    List<OrderEntity> selectIndentByPhone(String telephone);

    @Select("select order_id from indent where customer_cellphone=#{telephone} OR customer_telephone=#{telephone}")
    String selectOrderIdbyPhone(String telephone);

    @Select("select ORDER_ID as orderId,TECHNICIAN_ID as technicianId,ACCEPT_TIME as acceptTime,TECHNICIAN_LONGITUDE as technicianLongitude,TECHNICIAN_LATITUDE as technicianLatitude,CUSTOMER_LONGITUDE as customerLongitude,CUSTOMER_LATITUDE as customerLatitude from indent_allocation where ORDER_ID=#{orderId}")
    IndentAllocationEntity selectIndentAllocationMsg(String orderId);

    @Insert("insert into  indent_allocation (ORDER_ID) values (#{orderId})")
    boolean insertAllocationOderId(String orderId);




    @Update("update indent_allocation set customer_longitude=#{customerLongitude},customer_latitude=#{customerLatitude} where order_id=#{orderId}")
    boolean updateUserAddress(@Param("orderId") String orderId,@Param("customerLongitude")Float customerLongitude,@Param("customerLatitude")Float customerLatitude);

}
