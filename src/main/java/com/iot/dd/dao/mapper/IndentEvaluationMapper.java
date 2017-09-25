package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.Indent.IndentEvaluationEntity;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2017/9/18.
 */
@Repository
public interface IndentEvaluationMapper {
    @Select("select * from indent_evaluation where order_id=#{orderId}")
    @Results({
            @Result(column = "order_id",property = "orderId"),
            @Result(column = "technician_id",property = "technicianId"),
            @Result(column = "technician_service",property = "technicianService"),
            @Result(column = "delivery_speed",property = "technicianSpeed"),
            @Result(column = "product_quality",property = "technicianQuality")
    })
    IndentEvaluationEntity findOne(String orderId);


}
