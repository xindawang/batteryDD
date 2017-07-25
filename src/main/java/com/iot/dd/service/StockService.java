package com.iot.dd.service;

import com.iot.dd.Dao.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/24.
 */
@Service
public class StockService {

    @Autowired
    private StockDao stockservice;

    //返回多表查询结果集
    public List<Object> findStock(String cityCode){

      return   stockservice.findBatteryStock(cityCode);

    }



}
