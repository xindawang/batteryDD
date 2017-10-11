package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.Indent.ConfirmEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/8/28.
 */
@Repository
public interface confirmMapper {

    //插入一条完整的记录
    @Insert("insert into receiving_confirmation(order_id,technician_id," +
            "license_plate_number_img,quality_assurance_img,battery_img,time)" +
            "values(#{orderId},#{technicianId},#{licensePlateNumberImg}," +
            "#{qualityAssuranceImg},#{batteryImg},#{time})")
    Boolean addOne(ConfirmEntity entity);


    //没有记录先插入
    @Insert("insert into receiving_confirmation(order_id,technician_id,battery_img,time)" +
            "values(#{orderId},#{technicianId},#{batteryImg},#{time})")
    Boolean addOneBatteryIMG(ConfirmEntity entity);

    @Insert("insert into receiving_confirmation(order_id,technician_id,license_plate_number_img,time)" +
            "values(#{orderId},#{technicianId},#{licensePlateNumberImg},#{time})")
    Boolean addOneCarNumIMG(ConfirmEntity entity);

    @Insert("insert into receiving_confirmation(order_id,technician_id,quality_assurance_img,time)" +
            "values(#{orderId},#{technicianId},#{qualityAssuranceImg},#{time})")
    Boolean addOneQualityIMG(ConfirmEntity entity);

    //设置图片路径
    @Update("update receiving_confirmation set battery_img=#{batteryPath} where order_id=#{orderId}")
    Boolean updateBatteryPath(@Param("batteryPath") String batteryPath,@Param("orderId") String orderId);

    @Update("update receiving_confirmation set license_plate_number_img=#{carPath} where order_id=#{orderId}")
    Boolean updateCarNumPath(@Param("carPath") String carPath ,@Param("orderId") String orderId);

    @Update("update receiving_confirmation set quality_assurance_img=#{qualityPath} where order_id=#{orderId}")
    Boolean updateQualityPath(@Param("qualityPath") String qualityPath,@Param("orderId") String orderId);

    @Update("update receiving_confirmation set battery_img=#{batteryImg},license_plate_number_img=#{licensePlateNumberImg},quality_assurance_img=#{qualityAssuranceImg} where order_id=#{orderId}")
    Boolean updateQualityAllPath(ConfirmEntity entity);


    //用于判断记录是否存在
    @Select("select * from receiving_confirmation where order_id=#{orderId} and technician_id=#{technicianId}")
   List<ConfirmEntity> find(@Param("orderId") String orderId, @Param("technicianId") String technicianId);

}
