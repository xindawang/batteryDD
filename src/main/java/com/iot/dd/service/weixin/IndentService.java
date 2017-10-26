package com.iot.dd.service.weixin;

import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.entity.weixin.WeixinOauth2Token;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import com.iot.dd.dao.mapper.OrderMapper;
import com.iot.dd.dao.mapper.ResourceMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huanglin on 2017/7/27.
 */
@Service
public class IndentService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ResourceMapper resourceMapper;

    public Map<String,Object> WeixingetLocation( HttpServletRequest request) throws UnsupportedEncodingException {

        Map<String,Object> ret = new HashMap<String,Object>();
        ret= WeixinInitService.getWxConfig(request);

        return ret;
    }

    public  List<String> getIndentOrderIdByCellphone(String cellphone,String code) {
        String openId="";
        if (code != null) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = WeixinInitService.getOauth2AccessToken(code);
            //String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            if (weixinOauth2Token != null) {
                openId = weixinOauth2Token.getOpenId();

            }
        }
        Integer status=4;//查询已导入的订单--除此之外，还有未完成的订单。
        List<String> indentIdList = orderMapper.selectOrderIdByPhone(cellphone,status);

        for(String indentId : indentIdList) {
            if (orderMapper.selectOpenIdByOrderId(indentId) == null || orderMapper.selectOpenIdByOrderId(indentId).length()==0)//不可以用这个电话号码在其它微信号上申请
                if(openId==null || openId.length()==0)
                orderMapper.updateOpenId(openId, indentId);
        }

        return indentIdList;
    }

    public  List<String> getIndentOrderIdByCellphoneTest(String cellphone) {
        String openId="";

        Integer status=4;//查询已导入的订单--除此之外，还有未完成的订单。
        List<String> indentIdList = orderMapper.selectOrderIdByPhone(cellphone,status);

        return indentIdList;
    }

    public List<OrderEntity> getOrderIdByOpenId(String openId){
        return orderMapper.selectOrderIdByOpenId(openId);
    }

    public void setOpenIdOnIndent(String openId){
            String userCellphone=orderMapper.selectCellphoneByOpenId(openId);
            orderMapper.updateOpenIdByCellphone(openId,userCellphone);
    }


    public Integer getStatusByOrderId(String orderId){
        //返回订单状态
        String statusStr=orderMapper.selectStautsByOrderId(orderId);
        return Integer.parseInt(statusStr);

    }

    public List<OrderEntity> getIndentMsgByOrderId(String orderId){
        return orderMapper.selectIndentByOrderId(orderId);
    }

    public IndentAllocationEntity getAllLocation(String orderId){
        IndentAllocationEntity indentAllocationEntity;
        indentAllocationEntity=orderMapper.selectIndentAllocationMsg(orderId);
//        JSONObject location;
//        Float cusLongitude=indentAllocationEntity.getCustomerLongitude();
//        Float cusLatitude=indentAllocationEntity.getCustomerLatitude();
//
//        location=turnLocation(cusLongitude,cusLatitude);
//        if(location !=null){
//            indentAllocationEntity.setCustomerLongitude(Float.parseFloat(location.getString("locations").split(",")[0]));
//            indentAllocationEntity.setCustomerLatitude(Float.parseFloat(location.getString("locations").split(",")[1]));
//        }

//        Float techLongitude=indentAllocationEntity.getTechnicianLongitude();
//        Float techLatitude=indentAllocationEntity.getTechnicianLatitude();
//        location=turnLocation(techLongitude,techLatitude);
//        if(location !=null){
//            indentAllocationEntity.setTechnicianLongitude(Float.parseFloat(location.getString("locations").split(",")[0]));
//            indentAllocationEntity.setTechnicianLatitude(Float.parseFloat(location.getString("locations").split(",")[1]));
//        }

        return  indentAllocationEntity;
    }


    public String updateCustomerLocation(String orderId,Float longitude,Float latitude){


//        Float newLongitude, newLatitude;
//        if(turnLocation(longitude)!=null){
//                newLongitude=Float.parseFloat(location.getString("locations").split(",")[0]);
//                newLatitude=Float.parseFloat(location.getString("locations").split(",")[1]);
            Boolean res=orderMapper.updateUserLocation(orderId,longitude,latitude);
            if(res=true){
            return "用户地理位置更新成功";
        }else{
            return "用户地理位置更新失败";
        }

    }


    public String  getTechId(String orderId){
            return orderMapper.selectTechIdByOrderId(orderId);
    }

    public String getCityName(String orderId){
        return orderMapper.selectCityNameByOrderId(orderId);
    }


    public TechnicianEntity getTechnicainMsg(String orderId){
        String techId=orderMapper.selectTechIdByOrderId(orderId);
        TechnicianEntity techMsg=orderMapper.selectTechMsgById(techId);

        return techMsg;
    }

    public static JSONObject turnLocation(Float longitude,Float latitude) {
        String locationURL = "http://restapi.amap.com/v3/assistant/coordinate/convert?locations=" + longitude + "," + latitude + "&coordsys=gps&output=json&key=" + WeixinInitService.MapKey;
        JSONObject location = WeixinInitService.doGetStr(locationURL);
        return location;
    }


    public String  setIndentEvaluation(String orderId,String techId,Integer deliverySpeed,Integer productQuality,Integer techService){
        Integer status=5;

        Date date = new Date();//获得系统时间.
        Timestamp currentTime=new Timestamp(date.getTime());
        System.out.println(currentTime);
        if(orderMapper.selectOrderIdFromEva(orderId)==null) {
            orderMapper.insertIndentEvaluation(orderId, techId, currentTime, techService, deliverySpeed, productQuality);
            orderMapper.updateStatus(orderId,5);
            return "success";
        }else{
            return "evaluated";
        }

    }



}
