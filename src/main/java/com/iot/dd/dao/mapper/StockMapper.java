package com.iot.dd.dao.mapper;
import com.iot.dd.dao.entity.resource.BatteryStockEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by admin on 2017/7/24.
 */
@Mapper
public interface StockMapper {

    List<BatteryStockEntity> findBatteryStockByCity(String cityCode);

    List<BatteryStockEntity> findBatteryStockByType(Integer batteryType);

}
