package com.iot.dd.Dao;

import com.iot.dd.domain.SexEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/7/25.
 */
@Repository
public interface SexDao {

    @Select("select * from sex")
    List<SexEntity> find();

}
