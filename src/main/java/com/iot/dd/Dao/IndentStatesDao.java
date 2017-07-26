package com.iot.dd.Dao;

import com.iot.dd.domain.IndentStatesEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/7/26.
 */
@Repository
public interface IndentStatesDao {

    @Select("select * from indent_state")
    List<IndentStatesEntity>find();

}
