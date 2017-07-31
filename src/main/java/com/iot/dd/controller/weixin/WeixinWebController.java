package com.iot.dd.controller.weixin;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.entity.weixin.WeixinOauth2Token;
import com.iot.dd.dao.entity.weixin.WeixinUserBaseMessage;
import com.iot.dd.service.weixin.ServiceValidateService;
import com.iot.dd.service.weixin.UserInformationService;
import com.iot.dd.service.weixin.WebService;
import com.iot.dd.service.weixin.WeixinInitService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
        String telephone=request.getParameter("telephone");

        if(telephone==null ||telephone.length()!=11){
            result=JsonTool.objectToJson("电话号码不能少于11位或者为空");
        }else{
            List<OrderEntity> Indent =validateService.getIndentByTelephone(telephone);
            if(Indent==null){
                result= JsonTool.objectToJson("订单不存在,请检查电话号码");
            }else{//telephone与订单对应
                Map<String,Object> LocationConfigMsg=validateService.WeixingetLocation(request);
                result=JsonTool.objectToJson(LocationConfigMsg);
            }

        }


        return result;
    }

}
