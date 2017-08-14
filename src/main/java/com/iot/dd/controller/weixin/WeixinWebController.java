package com.iot.dd.controller.weixin;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.entity.weixin.WeixinOauth2Token;
import com.iot.dd.dao.entity.weixin.WeixinUserBaseMessage;
import com.iot.dd.service.OrderService;
import com.iot.dd.service.weixin.ServiceValidateService;
import com.iot.dd.service.weixin.UserInformationService;
import com.iot.dd.service.weixin.WebService;
import com.iot.dd.service.weixin.WeixinInitService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Created by huanglin on 2017/7/16.
 *
 * 获取用户的基本信息
 */
@RestController
public class WeixinWebController {

    @Autowired
    ServiceValidateService validateService;
    //获取aaccess_token的地址

    @Autowired
    OrderService orderService;
    public  String requestUrl="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";



    @RequestMapping(value = "/WeixinUserMessage",method= RequestMethod.GET)

    public void getUserMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");

        String state = request.getParameter("state");

        // 用户同意授权
        if(code !=null) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = WebService.getOauth2AccessToken(code);
            String accessToken = weixinOauth2Token.getAccessToken();
           // 用户标识
            String openId = weixinOauth2Token.getOpenId();
           // 获取用户信息
            WeixinUserBaseMessage UserInfo = UserInformationService.getUserInfo(accessToken, openId);


            String nickName=UserInfo.getNickname();
            Integer sex=UserInfo.getSex();
            String city= UserInfo.getCity();
            String country=UserInfo.getCountry();
            String province=UserInfo.getProvince();
            String language=UserInfo.getLanguage();
            String headImgUrl=UserInfo.getHeadImgUrl();

            // 设置要传递的参数

        }
        // 跳转到index.jsp
    }








    @RequestMapping(value="/applyServiceValidate",method=RequestMethod.POST)
    public String serviceValidate(HttpServletRequest request) throws UnsupportedEncodingException {
        String result=null;
        String cellphone=request.getParameter("cellphone");

        if(cellphone==null ||cellphone.length()!=11){
            result=JsonTool.objectToJson("电话号码不能少于11位或者为空");
        }else{
            List<OrderEntity> Indent =validateService.getIndentByCellphone(cellphone);
            if(Indent==null){
                result= JsonTool.objectToJson("订单不存在,请检查电话号码");
            }else{//telephone与订单对应
                result= JsonTool.objectToJson("订单存在，请等待技师给您派单");
            }

        }

        return result;
    }


    @RequestMapping(value="/getLocationMsg",method=RequestMethod.POST)
    String getLocationMsg(HttpServletRequest request){
        //String targetUrl=request.getParameter("targetUrl");
        String result=null;
        Map<String,Object> LocationConfigMsg= null;
        try {
            LocationConfigMsg = validateService.WeixingetLocation(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        result=JsonTool.objectToJson(LocationConfigMsg);

        return result;

    }


    //将用户位置更新到数据库
    @RequestMapping(value="/setCustomerLocation",method = RequestMethod.POST)

    String setCustomerLoaction(HttpServletRequest request){
        String result=null;
        String telephone=request.getParameter("userTelephone");
        Float longitude=Float.parseFloat(request.getParameter("longitude"));
        Float latitude=Float.parseFloat(request.getParameter("latitude"));

        result=orderService.updateCustomerLocation(telephone,longitude,latitude);
        return result;
    }













}
