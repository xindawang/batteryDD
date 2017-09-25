package com.iot.dd.service;

import com.iot.dd.dao.entity.Indent.IndentMonitorEntity;
import com.iot.dd.dao.mapper.OrderMapper;
import com.iot.dd.service.weixin.WeixinInitService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: huanglin
 * Date: 2017/9/20
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
@Service
public class IndentMonitorService {
    @Autowired
    private OrderMapper orderMapper;

    public List<IndentMonitorEntity> getAllIndentLocation(String type) {
        return orderMapper.selectAllMonitorMsg();
    }


    public static String getLocationByAddress(String address) {
        Float longitude;
        Float latitude;

        String locationURL = "http://restapi.amap.com/v3/geocode/geo?address=" + address + "&output=JSON&key=" + WeixinInitService.MapKey;
        JSONObject result = WeixinInitService.doGetStr(locationURL);

        JSONArray geocodes = result.getJSONArray("geocodes");
        JSONObject geocodesJSONObject = geocodes.getJSONObject(0);
        String location = geocodesJSONObject.getString("location");


        return location;
    }
}
