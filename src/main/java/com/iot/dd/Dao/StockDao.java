package com.iot.dd.Dao;
import com.iot.dd.domain.resource.BatteryStockEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/7/24.
 */
@Mapper
public interface StockDao {


    List<BatteryStockEntity> findBatteryStockByCity(String cityCode);

    List<BatteryStockEntity> findBatteryStockByType(String batteryType);

}
