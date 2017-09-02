package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.worker.LastIdEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2017/8/31.
 */
@Repository
public interface LastIdMapper {

    @Insert("insert into last_id(id_number,id_pre) values(#{idNumber},#{idPre})")
    boolean add(LastIdEntity entity);

    @Update("update  last_id set id_number=#{idNumber}")
    boolean update(String number);

    @Select("select * from last_id ")
    @Results({
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "idPre", column = "id_pre"),
    })
    LastIdEntity find();


}
