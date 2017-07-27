package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.Indent.IndentStatesEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/7/26.
 */
@Repository
public interface IndentStatesMapper {

    @Select("select * from indent_state")
    List<IndentStatesEntity>find();

}
