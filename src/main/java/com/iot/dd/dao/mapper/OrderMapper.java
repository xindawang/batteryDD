package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
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
    List<OrderEntity> selectIndentMsg(Integer status);


    //查询订单状态是已录入，且用户经纬度已经上传的订单编号，用于订单派发
    @Select("select indent.ORDER_ID from indent join indent_allocation" +
            " on indent.ORDER_ID = indent_allocation.ORDER_ID" +
            " where indent.STATUS=#{status} AND" +
            " indent_allocation.CUSTOMER_LATITUDE IS NOT NULL")
    List<String> selectImportIndentOrderId(Integer status);

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
    List<OrderEntity> importIndentMsg(@Param("status") Integer status,@Param("orderId") String orderId);


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
    List<OrderEntity> selectIndentByStatusAndCity(@Param("status") Integer status,@Param("cityCode") String cityCode);

    //根据客户从微信发送过来的电话查询订单是否存在
    @Select("select * from indent where customer_cellphone=#{cellphone} OR customer_telephone=#{cellphone}  ")
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
    List<OrderEntity> selectIndentByPhone(String cellphone);

    //通过电话号码查询订单状态是已录入的订单编号
    @Select("select order_id from indent where status=#{status} and (customer_cellphone=#{telephone} or customer_telephone=#{telephone})")
    String selectOrderIdbyPhone(@Param("telephone") String telephone,@Param("status")String status);

    @Select("select ORDER_ID as orderId,TECHNICIAN_ID as technicianId,ACCEPT_TIME as acceptTime,TECHNICIAN_LONGITUDE as technicianLongitude,TECHNICIAN_LATITUDE as technicianLatitude,CUSTOMER_LONGITUDE as customerLongitude,CUSTOMER_LATITUDE as customerLatitude from indent_allocation where ORDER_ID=#{orderId}")
    IndentAllocationEntity selectIndentAllocationMsg(String orderId);

    @Insert("insert into  indent_allocation (ORDER_ID) values (#{orderId})")
    boolean insertAllocationOderId(String orderId);







    @Update("update indent_allocation set customer_longitude=#{customerLongitude},customer_latitude=#{customerLatitude} where order_id=#{orderId}")
    boolean updateUserLocation(@Param("orderId") String orderId,@Param("customerLongitude")Float customerLongitude,@Param("customerLatitude")Float customerLatitude);

    @Select("select customer_longitude as customerLongitude, customer_latitude as customerLatitude from indent_allocation where order_id=#{orderId}")
    IndentAllocationEntity selectCustomerLocation(String orderId);


    //查询订单编号是否已经存在
    @Select("select ORDER_ID from indent where ORDER_ID=#{orderId}")
    String selectIdByOrderId(String orderId);

    //查询订单所在城市所有技师的信息。
    @Select("select * from technician where CITY_CODE=#{cityCode}")
    @Results({
            @Result(property = "technicianId",column="technician_id"),
            @Result(property = "loginName",column="login_name"),
            @Result(property = "idNumber",column="id_number"),
            @Result(property = "longitude",column="technician_longitude"),
            @Result(property = "latitude",column="technician_latitude"),
            @Result(property = "licensePlateNumber",column="license_plate_number"),
            @Result(property = "cityCode",column = "city_code"),
            @Result(property = "organizationId",column="organization_id")
    })
    List<TechnicianEntity> selectTechMsg(String cityCode);

    @Select("select city_code from indent where order_id=#{orderId}")
    String selectCityCodeByOrderId(String orderId);


    @Update("update indent set status=#{status} where order_id=#{orderId}")
    Boolean updateStatus(@Param("orderId")String orderId,@Param("status")Integer status);

    @Update("update indent_allocation set technician_id=#{techId} where order_id=#{orderId}")
    Boolean updateIndentAllocTech(@Param("orderId")String orderId,@Param("techId")String techId);



}
