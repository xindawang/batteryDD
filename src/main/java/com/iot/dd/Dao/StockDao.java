package com.iot.dd.Dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/7/24.
 */
@Repository
public interface StockDao {

    @Select("select b.type,c.city_name,bs.inventory from battery_stock bs,city c,battery b where bs.city_code=#{cityCode} and c.city_code=bs.city_code and b.id=bs.battery_id")
    List<Object> findBatteryStock(String cityCode);


    @Select("select b.type,c.city_name,bs.inventory from battery_stock bs,city c,battery b where bs.city_code=#{cityC} and c.city_code=bs.city_code and b.id=bs.battery_id")
    List<Object> findBatteryStockB(String cityCode);

}
