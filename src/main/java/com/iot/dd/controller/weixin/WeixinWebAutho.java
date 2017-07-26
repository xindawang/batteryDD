package com.iot.dd.controller.weixin;

import com.iot.dd.dao.entity.weixin.WeixinOauth2Token;
import com.iot.dd.dao.entity.weixin.WeixinUserBaseMessage;
import com.iot.dd.service.weixin.WeixinMessageUtil;
import com.iot.dd.service.weixin.WeixinUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
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
@Controller
public class WeixinWebAutho {

    //获取aaccess_token的地址
    public  String requestUrl="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    @RequestMapping("/weixinwebautho")
    public String doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");



        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");

        String state = request.getParameter("state");

        // 用户同意授权
        if(code !=null) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = getOauth2AccessToken(code);
//            String accessToken = weixinOauth2Token.getAccessToken();
            String accessToken=WeixinUtil.getAccessToken().getToken();
           // 用户标识
            String openId = weixinOauth2Token.getOpenId();
           // 获取用户信息
            WeixinUserBaseMessage UserInfo = WeixinMessageUtil.getUserInfo(accessToken, openId);

            model.addAttribute("openId",UserInfo.getOpenId());
            model.addAttribute("nickname",UserInfo.getNickname());
            model.addAttribute("sex",UserInfo.getSex());
            model.addAttribute("CityEntity",UserInfo.getCity());
            model.addAttribute("country",UserInfo.getCountry());
            model.addAttribute("ProvinceEntity",UserInfo.getProvince());
            model.addAttribute("language",UserInfo.getLanguage());
            model.addAttribute("headimgurl",UserInfo.getHeadImgUrl());
            model.addAttribute("subscribe", UserInfo.getSubscribe());
            model.addAttribute("subscribe_time",UserInfo.getSubscribeTime());

            // 设置要传递的参数

        }
        // 跳转到index.jsp
        return "WeixinUserMessage";
    }

    public static WeixinOauth2Token getOauth2AccessToken(String code){
        String access_token_MsgUrl="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        WeixinOauth2Token wat=null;
        String requestUrl;
        requestUrl=access_token_MsgUrl.replace("APPID", WeixinUtil.APPID)
                .replace("SECRET",WeixinUtil.APPSECRET)
                .replace("CODE",code);
        JSONObject jsonObject= WeixinMessageUtil.httpRequest(requestUrl,"GET",null);
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                //log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return wat;


    }


    @RequestMapping("/location")
    public String WeixinWebLocation(Model model,HttpServletRequest request){

        Map<String,Object>  ret = new HashMap<String,Object> ();
        ret=WeixinUtil.getWxConfig(request);
        model.addAttribute("appId",ret.get("appId"));
        model.addAttribute("timestamp",ret.get("timestamp"));
        model.addAttribute("nonceStr",ret.get("nonceStr"));
        model.addAttribute("signature",ret.get("signature"));


        return "location";
    }

}
