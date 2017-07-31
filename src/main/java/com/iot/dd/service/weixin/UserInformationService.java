package com.iot.dd.service.weixin;

import com.iot.dd.dao.entity.weixin.WeixinUserBaseMessage;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by huanglin on 2017/7/16.
 */
public class UserInformationService {

    private static Logger log = LoggerFactory.getLogger(WeixinUserBaseMessage.class);

    //通过url获取微信后台返回的jsonObject信息。
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection http = (HttpsURLConnection) url.openConnection();
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setUseCaches(false);
            http.setRequestMethod(requestMethod);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.connect();
            if(null !=outputStr) {
                OutputStream os = http.getOutputStream();
                os.write(outputStr.getBytes("UTF-8"));
                os.close();
            }

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] bt = new byte[size];
            is.read(bt);
            String message = new String(bt, "UTF-8");
            jsonObject = JSONObject.fromObject(message);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static WeixinUserBaseMessage getUserInfo(String accessToken, String openId) {
        WeixinUserBaseMessage weixinUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID",openId);
        // 获取用户信息
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                weixinUserInfo = new WeixinUserBaseMessage();
                // 用户的标识
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
                // 用户关注时间
                weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.setProvince(jsonObject.getString("ProvinceEntity"));
                // 用户所在城市
                weixinUserInfo.setCity(jsonObject.getString("CityEntity"));
                // 用户的语言，简体中文为zh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == weixinUserInfo.getSubscribe()) {
                    log.error("用户{}已取消关注", weixinUserInfo.getOpenId());
                } else {
                    Integer  errorCode = jsonObject.getInt("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                }
            }
        }
        return weixinUserInfo;
    }

}
