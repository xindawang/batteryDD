package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.resource.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/9/26.
 */
@Repository
public interface automobileMapper {

    @Select("select auto.ID, auto.NAME,auto.AUTOMOBILE_BRAND_ID,brand.BRAND_NAME" +
            "        from automobile_type auto join automobile_brand brand on auto.AUTOMOBILE_BRAND_ID =brand.ID order by brand.ID")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "automobileBrand", column = "brand_name"),
            @Result(property = "automobileType", column = "name"),
    })
    List<autoDetailEntity> findAuto();

    @Select("select * from automobile_brand where brand_name=#{brandName}")
    @Results({
            @Result(property = "brandName", column = "brand_name"),
    })
    AutomobileBrand findAutomobileOne(String brandName);

    @Select("select * from automobile_brand where brand_name=#{brandName}")
    @Results({
            @Result(property = "brandName", column = "brand_name"),
    })
    List<AutomobileBrand> findAutomobileBrandList(String brandName);

    @Select("select * from automobile_type where automobile_brand_id=#{brandId}")
    @Results({
            @Result(property = "automobileBrandId", column = "automobile_brand_id"),
    })
    List<AutomobileType> findAutomobileTypeList(int brandId);

    @Select("select * from automobile_type where name=#{typeName} and automobile_brand_id=#{brandId}")
    AutomobileType findAutomobileType(@Param("typeName") String typeName, @Param("brandId") int brandId);

    @Select("select * from automobile_battery where automobile_type_id=#{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "automobileTypeId", column = "automobile_type_id"),
            @Result(property = "batteryTypeId", column = "battery_type_id"),
    })
    List<AutomobileTypeBatteryEntity> findAutomobileBattery(int autoTypeId);

    @Select("select * from battery where id=#{id}")
    @Results({
            @Result(property = "warrantyPeriod", column = "warrenty_period"),
            @Result(property = "batteryBrandId", column = "battery_brand_id"),
    })
    BatteryEntity findBatteryOne(int id);

    @Select("select * from automobile_battery where automobile_type_id=#{autoTypeId} and battery_type_id=#{batteryId}")
    List<AutomobileTypeBatteryEntity> getAutomobileBattery(@Param("autoTypeId") int autoTypeId, @Param("batteryId") String batteryId);

    @Insert("insert into automobile_battery(automobile_type_id,battery_type_id) values(#{automobileTypeId},#{batteryTypeId})")
    Boolean addAutomobileBatteryItem(AutomobileTypeBatteryEntity entity);

    @Delete("delete from automobile_battery where automobile_type_id=#{automobileTypeId} and battery_type_id=#{batteryTypeId}")
    Boolean deleteAutomobileBatteryItem(@Param("automobileTypeId") int automobileTypeId, @Param("batteryTypeId") String batteryTypeId);

    @Delete("delete from automobile_brand where id=#{brandId}")
    Boolean deleteAutomobileBrand(int brandId);

    @Delete("delete from automobile_type where id=#{typeId}")
    Boolean deleteAutomobileType(int typeId);

    @Update("update automobile_type set name=#{name} where id=#{id}")
    boolean updateCarType(AutomobileType entity);

    //添加车型
    @Insert("insert into automobile_brand(brand_name) values(#{brandName})")
    Boolean AddAutomobileBrand(@Param("brandName") String brandName);

    @Insert("insert into automobile_type(name,automobile_brand_id) values(#{name},#{brandId})")
    Boolean AddAutomobileType(@Param("name") String name,@Param("brandId") String brandId);


}
