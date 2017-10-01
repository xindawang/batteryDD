package com.iot.dd.dao.mapper;
import com.iot.dd.dao.entity.resource.BatteryStockEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/7/24.
 */
@Repository
public interface StockMapper {

    @Select("select battery_brand.brand_name as batteryBrand, battery.type as batteryType ,province.province_name as provinceName,city.city_name as cityName," +
            "battery_stock.inventory from battery_stock join battery on battery_stock.battery_id=battery.id join battery_brand on battery.battery_brand_id=battery_brand.id " +
            "join city on battery_stock.city_code=city.city_code join province on city.province_code=province.province_code where battery_stock.city_code =#{cityCode} order by battery_stock.city_code ,battery.battery_brand_id")
    List<BatteryStockEntity> findBatteryStockByCity(String cityCode);




    @Delete("delete from battery_stock where battery_id=#{batteryId} and city_code=#{cityCode}")
    boolean deleteStock(@Param("cityCode") String cityCode, @Param("batteryId") Integer batteryId);

    @Delete("delete from battery_stock where battery_id=#{batteryId} ")
    boolean deleteAllStockByBatteryId( Integer batteryId);

    @Select("select battery_brand.brand_name as batteryBrand, battery.type as batteryType ,province.province_name as provinceName,city.city_name as cityName," +
            "battery_stock.inventory from battery_stock join battery on battery_stock.battery_id=battery.id join battery_brand on battery.battery_brand_id=battery_brand.id " +
            "join city on battery_stock.city_code=city.city_code join province on city.province_code=province.province_code order by battery_stock.city_code ,battery.battery_brand_id")
    List<BatteryStockEntity> selectAllBatteryStock();


    @Insert("insert into battery_stock values(null,#{batteryId},#{cityCode},#{inventory})")
    Boolean InsertBatteryStock(@Param("batteryId") Integer batteryId, @Param("cityCode") String cityCode, @Param("inventory") Integer inventory);


    @Update("update battery_stock set inventory=#{stock}  where battery_id=#{batteryId} and city_code=#{cityCode}")
    boolean updateStock(@Param("stock") Integer stock, @Param("batteryId") Integer batteryId, @Param("cityCode") String cityCode);

}
