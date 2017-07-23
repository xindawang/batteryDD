package com.iot.dd.Dao;

import com.iot.dd.domain.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * Created by huanglin on 2017/7/21.
 */
@Repository
public interface  OrderDao {
    @Insert("insert into indent (BATTERY_TYPE,CUSTOMER_NAME,CUSTOMER_CELLPHONE" +
            ",CUSTOMER_TELEPHONE,WECHAT_ID,ADDRESS,AUTOMOBILE_TYPE,LICENSE_PLATE_NUMBER,CITY_CODE,CREATE_TIME" +
            ",STATUS,REMARK) values(#{batteryType},#{customerName},#{customerCellphone}" +
            ",#{customerTelephone},#{wechatId},#{address},#{automobileType},#{licensePlateNumber}" +
            ",#{cityCode},#{createTime},#{status},#{remark})")
    boolean addOrder(OrderEntity orderEntity);
}
