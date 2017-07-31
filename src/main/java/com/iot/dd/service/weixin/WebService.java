package com.iot.dd.service.weixin;

import com.iot.dd.dao.entity.weixin.WeixinOauth2Token;
import net.sf.json.JSONObject;

/**
 * Created by huanglin on 2017/7/27.
 */
public class WebService {
    //通过code获取网页授权access_token
    public static WeixinOauth2Token getOauth2AccessToken(String code){
        String access_token_MsgUrl="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        WeixinOauth2Token wat=null;
        String requestUrl;
        requestUrl=access_token_MsgUrl.replace("APPID", WeixinInitService.APPID)
                .replace("SECRET", WeixinInitService.APPSECRET)
                .replace("CODE",code);
        JSONObject jsonObject= UserInformationService.httpRequest(requestUrl,"GET",null);
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
}
