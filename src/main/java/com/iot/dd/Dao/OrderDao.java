package com.iot.dd.Dao;

import com.iot.dd.domain.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huanglin on 2017/7/21.
 */
@Repository
public interface  OrderDao {
    @Insert("insert into indent (ORDER_ID,BATTERY_TYPE,CUSTOMER_NAME,CUSTOMER_CELLPHONE" +
            ",CUSTOMER_TELEPHONE,WECHAT_ID,ADDRESS,AUTOMOBILE_TYPE,LICENSE_PLATE_NUMBER,CITY_CODE,CREATE_TIME" +
            ",STATUS,REMARK) values(#{orderId},#{batteryType},#{customerName},#{customerCellphone}" +
            ",#{customerTelephone},#{wechatId},#{address},#{automobileType},#{licensePlateNumber}" +
            ",#{cityCode},#{createTime},#{status},#{remark})")
    boolean addOrder(OrderEntity orderEntity);

    //根据订单状态查询所有订单信息
    @Select("select * from indent where STATUS=#{status}")
            @Results({
                    @Result(property = "orderId", column = "order_id"),
                    @Result(property="batteryType",column="battery_type"),
                    @Result(property ="customerName",column="customer_name"),
                    @Result(property = "customerCellphone",column="customer_cellphone"),
                    @Result(property = "customerTelephone",column="customer_telephone"),
                    @Result(property = "wechatId",column="wechat_id"),
                    @Result(property = "automobileType",column="automobile_type"),
                    @Result(property = "licensePlateNumber",column="license_plate_number"),
                    @Result(property = "createTime",column="create_time"),
            })
    List<OrderEntity> selectIndentMsg(String status);


    //根据订单号查询所有未派发订单信息
    @Select("select * from indent where STATUS=#{0} AND ORDER_ID=#{1}")
    @Results({
            @Result(property = "orderId", column = "order_id"),
            @Result(property="batteryType",column="battery_type"),
            @Result(property ="customerName",column="customer_name"),
            @Result(property = "customerCellphone",column="customer_cellphone"),
            @Result(property = "customerTelephone",column="customer_telephone"),
            @Result(property = "wechatId",column="wechat_id"),
            @Result(property = "automobileType",column="automobile_type"),
            @Result(property = "licensePlateNumber",column="license_plate_number"),
            @Result(property = "createTime",column="create_time"),
    })
    List<OrderEntity> importIndentMsg(String status,String orderId);

}
