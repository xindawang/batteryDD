package com.iot.dd.Dao;


import com.iot.dd.domain.resource.*;
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

    @Select("select PROVINCE_NAME from province where PROVINCE_CODE=#{provinceCode} ")
    String  selectProvinceName(String provinceCode);

    @Select("select CITY_NAME from city where CITY_CODE=#{cityCode} ")
    String  selectCityName(String cityCode);

    @Select("select AREA_NAME from area where AREA_CODE=#{areaCode} ")
    String  selectAreaName(String areaCode);

    @Select("select CITY_CODE  from city where CITY_NAME=#{cityName}")
    String selectCityCode(String cityName);

    @Select("select STATE from indent_state where ID=#{id}")
    String selectId(Integer id);

    @Select("select ID,BRAND_NAME as brandName from automobile_brand")
    List<AutomobileBrand> selectAutoBrand();

    @Select("select ID,NAME from automobile_type where AUTOMOBILE_BRAND_ID=#{brandId}")
    List<AutomobileType> selectAutoType(Integer brandId);

    @Select("select BRAND_NAME from automobile_brand where ID=#{brandId}")
    String selectAutoBrandName(Integer brandId);

    @Select("select NAME from automobile_type where ID=#{id}")
    String selectAutoTypeName(Integer id);


    @Select("select ID,BRAND_NAME as brandName from battery_brand")
    List<BatteryBrandEntity> selectBatteryBrand();

    @Select("select ID,TYPE from battery where BATTERY_BRAND_ID=#{brandId}")
    List<BatteryEntity>selectBatteryType(Integer brandId);

    @Select("select BRAND_NAME from battery_brand where ID=#{id}")
    String selectBatteryBrandName(Integer id);

    @Select("select TYPE from battery where ID=#{id}")
    String selectBatteryTypeName(Integer id);

}
