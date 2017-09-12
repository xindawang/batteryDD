package com.iot.dd.service;

import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import com.iot.dd.dao.entity.resource.CityEntity;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import com.iot.dd.dao.mapper.OrderMapper;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.mapper.ResourceMapper;
import com.iot.dd.service.weixin.IndentService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by huanglin on 2017/7/21.
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    //导入订单
    public String importOrder(OrderEntity orderEntity) {
        String result = null;
        if (orderMapper.addOrder(orderEntity)) {
            result = "订单导入成功";
        } else {
            result = "订单导入失败";
        }
        return result;
    }

    //在派发订单时调出所有未派发订单信息(订单编号)
    public List<String> selectUndoneIndentOrderId(Integer status) {

        return orderMapper.selectImportIndentOrderId(status);
    }

    //根据订单号将select未派发的订单信息
    public List<OrderEntity> importUndoneIndentMsg(Integer status, String orderId) {
        return orderMapper.importIndentMsg(status, orderId);
    }

    //根据订单编号查找订单
    public OrderEntity findOrder(String orderId) {
        return orderMapper.findOrder(orderId);
    }

    //在订单派发开始时选中某一未派发订单时将订单编号存入订单派发表中
    public String InsertOrderId(String orderId) {
        if (orderMapper.selectIndentAllocationMsg(orderId) == null) {
            orderMapper.insertAllocationOderId(orderId);
        } else {
            return "订单编号已存在，不需重复导入";
        }
        return "订单编号成功导入订单转发表";
    }


    //通过订单编号查询用户的地理位置
    public Map<String, Float> selectCustomerLocation(String orderId) {

        Map<String, Float> result = new HashMap<>();

        IndentAllocationEntity indentAllocationEntity = orderMapper.selectCustomerLocation(orderId);
        JSONObject location;
        Float cusLongitude = indentAllocationEntity.getCustomerLongitude();
        Float cusLatitude = indentAllocationEntity.getCustomerLatitude();

        location = IndentService.turnLocation(cusLongitude, cusLatitude);


        result.put("cusLongitude", Float.parseFloat(location.getString("locations").split(",")[0]));
        result.put("cusLatitude", Float.parseFloat(location.getString("locations").split(",")[1]));
        return result;
    }

    //查询订单编号是否存在
    public String selectOrderId(String orderId) {
        return orderMapper.selectIdByOrderId(orderId);
    }


    public List<TechnicianEntity> selectTechMsg(String cityCode) {
        List<TechnicianEntity> techMsg = orderMapper.selectTechMsg(cityCode);

        //去除不含地理位置的技师信息
        for (int i = techMsg.size() - 1; i >= 0; i--) {
            if (techMsg.get(i).getLatitude() == null || techMsg.get(i).getLongitude() == null)
                techMsg.remove(techMsg.get(i));


        }
//        //先去除没有地理位置的技师，再进行转码
//        for(int i=techMsg.size()-1;i>=0;i--){
//            //更新坐标系  GPS-高德
//            JSONObject location= IndentService.turnLocation(techMsg.get(i).getLongitude(),techMsg.get(i).getLatitude());
//            if(location !=null){
//                techMsg.get(i).setLongitude(Float.parseFloat(location.getString("locations").split(",")[0]));
//                techMsg.get(i).setLatitude(Float.parseFloat(location.getString("locations").split(",")[1]));
//            }
//        }


        return techMsg;
    }


    //订单转发给技师，改变订单状态，更新订单转发表中技师编号和地址。

    public void allocIndent(String indentId, String techId) {
        orderMapper.updateIndentAllocTech(indentId, techId);
        Integer indentAlloc = 2;//订单已经转发的状态
        orderMapper.updateStatus(indentId, indentAlloc);
    }


    /*
    * 修改订单状态
    * 技师接受委派或拒绝订单委派时调用
    * */
    public boolean updateStatues(String orderId, int statues) {
        return orderMapper.updateStatues(orderId, statues);
    }

    public boolean setFinishTime(String orderId, Date time) {
        return orderMapper.setFinishTime(orderId, time);
    }

    public List<CityEntity> selectCity() {
        Integer status = 1;
        List<CityEntity> cityEntityList = orderMapper.selectCityCodeByStatus(status);
        List<CityEntity> cityMsgList = new ArrayList<>();
        if (cityEntityList != null) {
            for (int i = 0; i < cityEntityList.size(); i++) {
                String cityCode = cityEntityList.get(i).getCityCode();
                CityEntity cityEntity = orderMapper.selectCityMsgByCityCode(cityCode);
                cityMsgList.add(cityEntity);
            }
        }
        return cityMsgList;
    }

    public List<OrderEntity> selectOrderIdByCity(String cityCode) {
        Integer status = 1;
        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities = orderMapper.selectIndentIdByStatusAndCity(status, cityCode);
        return orderEntities;
    }

}
