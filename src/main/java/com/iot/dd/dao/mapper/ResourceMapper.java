package com.iot.dd.dao.mapper;


import com.iot.dd.dao.entity.resource.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huanglin on 2017/7/20.
 */
@Repository
//这个注解表示是一个mybatis操作数据库的一个类
public interface ResourceMapper {

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


    //选择订单状态
    @Select("select STATE from indent_state where ID=#{id}")
    String selectStatus(Integer id);

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
    List<BatteryEntity> selectBatteryTypeById(Integer brandId);

    @Select("select BRAND_NAME from battery_brand where Brand_name=#{brandName}")
    String selectBatteryBrandNameByName(String brandName);

    @Select("select TYPE from battery where type=#{type}")
    String selectBatteryTypeNameByType(String type);

    @Select("select TYPE from battery where ID=#{id}")
    String selectBatteryTypeNameById(Integer id);

    @Select("select id from battery where type=#{type}")
    Integer selectBatteryTypeId(String type);

    @Select("select id from battery_brand where brand_name=#{brandName}")
    Integer selectBatteryBrandIdByName(String brandName);

    @Select("select type ,battery.id ,brand_name brandName from battery join battery_brand on battery.battery_brand_id =battery_brand.id order by battery_brand_id")
    List<BatteryTypeEntity> selectAllBatteryType();

    @Update("update battery set type=#{type} where id=#{batteryId}")
    void updateBattery(@Param("type") String type, @Param("batteryId") Integer batteryId);

    @Delete("delete from battery where id=#{batteryId}")
    void deleteBattery(Integer batteryId);

    @Update("update battery_brand set brand_name=#{brandName}")
    void updateBatteryBrand(String brandName);

    @Insert("insert into battery_brand values(null,#{brandName})")
    void insertBatteryBrand(String brandName);

    @Insert("insert into battery (type,battery_brand_id)values(#{type},#{batteryBrandId})")
    void insertBattery(@Param("type") String type, @Param("batteryBrandId") Integer batteryBrandId);


}
