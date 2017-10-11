package com.iot.dd.service.weixin;

import com.iot.dd.dao.entity.weixin.*;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;
/**
 * Created by huanglin on 2017/7/16.
 */
public class MenuService {
    /**
     * 	创建自定义菜单(每天限制1000次)
     * */
    public static int createMenu(Menu menu){
        String jsonMenu=JSONObject.fromObject(menu).toString();


        AccessToken token= WeixinInitService.getAccessToken();

        int status=0;

        //System.out.println("菜单："+jsonMenu);
        String path= WeixinInitService.menu_create_url.replace("ACCESS_TOKEN",token.getToken());
        try {
            URL url=new URL(path);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(jsonMenu.getBytes("UTF-8"));
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] bt = new byte[size];
            is.read(bt);
            String message=new String(bt,"UTF-8");
            JSONObject jsonMsg = JSONObject.fromObject(message);
            status = Integer.parseInt(jsonMsg.getString("errcode"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }



    /**
     * 		封装菜单数据
     * */
    public static Menu getMenu(){


        String baseUrl = "http://www.batterydd.cn";
        String location_url=baseUrl+"/templates/wxIndentMap.html";
        String serviceInit_url=baseUrl+"/templates/applyServiceValidate.html";
        String cusEvaluation_url=baseUrl+"/templates/wxCusEvaluation.html";
        String historyIndent_url=baseUrl+"/templates/wxHistoryIndent.html";
        String appDownload_url=baseUrl+"/templates/wxAPPDownload.html";
        ComplexButton cx_1 = new ComplexButton();
        cx_1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect ".replace("APPID", WeixinInitService.APPID).replace("REDIRECT",serviceInit_url));
        cx_1.setName("申请服务");
        cx_1.setType("view");


        ViewButton vb_21 = new ViewButton();
        vb_21.setName("信息查询");
        vb_21.setType("view");
        vb_21.setUrl("http://www.baidu.com/");

        ViewButton vb_22 = new ViewButton();
        vb_22.setName("APP下载");
        vb_22.setType("view");
        vb_22.setUrl(appDownload_url);
//
//        ViewButton vb_23 = new ViewButton();
//        vb_23.setName("电池保养");
//        vb_23.setType("view");
//        vb_23.setUrl(cusEvaluation_url);

        ComplexButton cx_2 = new ComplexButton();
        cx_2.setName("信息查询");
        //cx_2.setType("view");
        //cx_2.setUrl("http://www.baidu.com/");
        Buttton[] subButton2={vb_21,vb_22};
        cx_2.setSub_button(subButton2);

        ComplexButton cx_3 = new ComplexButton();
        cx_3.setName("我的订单");
        cx_3.setType("view");
        cx_3.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect ".replace("APPID", WeixinInitService.APPID).replace("REDIRECT",historyIndent_url));


        Menu menu=new Menu();
        menu.setButton(new ComplexButton[]{cx_1,cx_2,cx_3});

        return menu;
    }
}

