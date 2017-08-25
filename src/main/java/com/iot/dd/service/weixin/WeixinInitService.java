package com.iot.dd.service.weixin;

import com.iot.dd.dao.entity.weixin.WeixinOauth2Token;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.*;

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.net.ConnectException;

import com.iot.dd.dao.entity.weixin.AccessToken;


/**
 * Created by huanglin on 2017/7/14.
 */


//微信验证类
    //APPID和APPSECRET是微信公众号分配的属性，唯一，

public class WeixinInitService extends TimerTask {

    private static Logger log = LoggerFactory.getLogger(WeixinInitService.class);
    public static final String APPID = "wx653f3498190e7c80";
    // public static  final String APPSECRET="819ecd32b3a55c86539f9ae9e455d431";

    public static final String APPSECRET = "365c06031424c5e9d5e93e40910a8975";
    //获取access的接口地址（GET）
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static final String JSAPI_TICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public static final String MapKey="3a840190111cc8b744af70595a5e7a2e";

    //菜单创建（POST）限100（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";


    public static  String access_token = "";
    public static  String jsapi_ticket = "";
    //重写TimerTask的run方法
    @Override
    public void run(){
        System.out.println("1小时一次");
        access_token=getAccessToken().getToken();

        JSONObject jsonObject = doGetStr(JSAPI_TICKET_URL.replace("ACCESS_TOKEN",access_token));
        jsapi_ticket=jsonObject.getString("ticket");
    }

    //get请求
    //url :为接口地址参数
    public static JSONObject doGetStr(String url) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "utf-8");
                jsonObject = JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //post请求
    public static JSONObject doPostStr(String url, String outStr) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        httpPost.setEntity(new StringEntity(outStr, "utf-8"));
        try {
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            jsonObject = JSONObject.fromObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    //通过access_token_url获取access_token 和expires_in
    public static AccessToken getAccessToken() {
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        JSONObject jsonObject = doGetStr(url);

        if (jsonObject != null) {
            token.setToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return token;

    }




    public static Map<String, Object> getWxConfig(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, Object> ret = new HashMap<String, Object>();

        String appId = WeixinInitService.APPID; // 必填，公众号的唯一标识
        String secret = WeixinInitService.APPSECRET;

        String requestUrl=request.getParameter("targetUrl");//获取在前端编码过的url。
        requestUrl=java.net.URLDecoder.decode(requestUrl,"GBK");
        requestUrl=java.net.URLDecoder.decode(requestUrl,"UTF-8");
//        String access_token = "";
//        String jsapi_ticket = "";
        String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳
        String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret=" +secret;

        String signature = "";
        // 注意这里参数名必须全部小写，且必须有序
        String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr+ "&timestamp=" + timestamp + "&url=" + requestUrl;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(sign.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ret.put("appId", appId);
        ret.put("timestamp", timestamp);
        ret.put("nonceStr", nonceStr);
        ret.put("signature", signature);
        return ret;
    }


    /**
     * 方法名：byteToHex</br>
     * 详述：字符串加密辅助方法 </br>
     * 开发人员：souvc  </br>
     * 创建时间：2016-1-5  </br>
     * @param hash
     * @return 说明返回值含义
     * @throws //说明发生此异常的条件
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;

    }


    //通过code获取网页授权access_token--获取用户信息
    public static WeixinOauth2Token getOauth2AccessToken(String code){
        String access_token_MsgUrl="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        WeixinOauth2Token wat=null;
        String requestUrl;
        requestUrl=access_token_MsgUrl.replace("APPID", WeixinInitService.APPID)
                .replace("SECRET", WeixinInitService.APPSECRET)
                .replace("CODE",code);
        //JSONObject jsonObject= UserInformationService.httpRequest(requestUrl,"GET",null);
        JSONObject jsonObject=doGetStr(requestUrl);
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



