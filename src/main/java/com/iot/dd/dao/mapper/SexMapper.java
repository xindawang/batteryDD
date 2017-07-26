package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.resource.SexEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/7/25.
 */
@Repository
public interface SexMapper {

    @Select("select * from sex")
    List<SexEntity> find();

}
