package com.iot.dd.Dao;


import com.iot.dd.domain.resource.AreaEntity;
import com.iot.dd.domain.resource.CityEntity;
import com.iot.dd.domain.resource.ProvinceEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huanglin on 2017/7/20.
 */
@Repository
//这个注解表示是一个mybatis操作数据库的一个类
public interface ResourceDao {

    @Select("select ID,PROVINCE_CODE as provinceCode,PROVINCE_NAME as provinceName from province")
    List<ProvinceEntity> selectProvince();

    @Select("select ID,CITY_CODE as cityCode,CITY_NAME as cityName from city where PROVINCE_CODE=#{provinceCode}")
    List<CityEntity> selectCity(String provinceCode);

    @Select("select ID,AREA_CODE as areaCode,AREA_NAME as areaName from area where CITY_CODE=#{cityCode}")
    List<AreaEntity> selectArea(String cityCode);

}
