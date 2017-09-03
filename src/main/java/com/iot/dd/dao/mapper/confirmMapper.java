package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.Indent.ConfirmEntity;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2017/8/28.
 */
@Repository
public interface confirmMapper {

    @Insert("insert into receiving_confirmation(order_id,technician_id," +
            "license_plate_number_img,quality_assurance_img,battery_img,time)" +
            "values(#{orderId},#{technicianId},#{licensePlateNumberImg}," +
            "#{qualityAssuranceImg},#{batteryImg},#{time})")
    Boolean addOne(ConfirmEntity entity);

}
