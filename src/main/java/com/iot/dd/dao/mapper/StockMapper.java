package com.iot.dd.dao.mapper;
import com.iot.dd.dao.entity.resource.BatteryStockEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by admin on 2017/7/24.
 */
@Mapper
public interface StockMapper {

    List<BatteryStockEntity> findBatteryStockByCity(String cityCode);

    List<BatteryStockEntity> findBatteryStockByType(Integer batteryType);


    @Delete("delete from battery_stock where battery_id=#{batteryId} and city_code=#{cityCode}")
    boolean deleteStock(String cityCode,Integer battreyId);

}
