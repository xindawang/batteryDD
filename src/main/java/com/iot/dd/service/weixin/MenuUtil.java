package com.iot.dd.service.weixin;

import com.iot.dd.domain.*;
import com.iot.dd.domain.weixin.*;


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
public class MenuUtil {
    /**
     * 	创建自定义菜单(每天限制1000次)
     * */
    public static int createMenu(Menu menu){
        String jsonMenu=JSONObject.fromObject(menu).toString();


        AccessToken token= WeixinUtil.getAccessToken();

        int status=0;

        //System.out.println("菜单："+jsonMenu);
        String path=WeixinUtil.menu_create_url.replace("ACCESS_TOKEN",token.getToken());
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


        String userMessage_url="http://dcdd.tunnel.qydev.com/weixinwebautho";
        String location_url="http://dcdd.tunnel.qydev.com/location.html";
        ViewButton vb_11 = new ViewButton();
        vb_11.setName("个人位置");
        vb_11.setType("view");
        vb_11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT&response_type=code&scope=snsapi_base&state=123#wechat_redirect ".replace("APPID",WeixinUtil.APPID).replace("REDIRECT",location_url));
        CommonButton cb_12 = new CommonButton();
        cb_12.setKey("jyzn");
        cb_12.setName("申请服务");
        cb_12.setType("click");

        ComplexButton cx_1 = new ComplexButton();
        cx_1.setName("电池更换");
        cx_1.setSub_button(new Buttton[]{vb_11,cb_12});


        ViewButton vb_21 = new ViewButton();
        vb_21.setName("公司官网");
        vb_21.setType("view");
        vb_21.setUrl("http://www.baidu.com/");

        ViewButton vb_22 = new ViewButton();
        vb_22.setName("个人信息");
        vb_22.setType("view");
        vb_22.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT&response_type=code&scope=snsapi_base&state=123#wechat_redirect ".replace("APPID",WeixinUtil.APPID).replace("REDIRECT",userMessage_url));

        ViewButton vb_23 = new ViewButton();
        vb_23.setName("更换记录");
        vb_23.setType("view");
        vb_23.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3e4a89adbc62b1e9&redirect_uri=http://www.jiaqiankun.site/imessage/wechat/event/wdbg.html&response_type=code&scope=snsapi_base&state=0531_819");

        ComplexButton cx_2 = new ComplexButton();
        cx_2.setName("信息查询");
        cx_2.setSub_button(new Buttton[]{vb_21,vb_22,vb_23});

        CommonButton cb_31 = new CommonButton();
        cb_31.setKey("cjwt");
        cb_31.setName("常见问题");
        cb_31.setType("click");
        CommonButton cb_32 = new CommonButton();
        cb_32.setKey("myddc");
        cb_32.setName("满意度调查");
        cb_32.setType("click");

        CommonButton cb_33 = new CommonButton();
        cb_33.setKey("app");
        cb_33.setName("APP下载");
        cb_33.setType("click");
        ComplexButton cx_3 = new ComplexButton();
        cx_3.setName("更多精彩");
        cx_3.setSub_button(new Buttton[]{cb_31,cb_32,cb_33});

        Menu menu=new Menu();
        menu.setButton(new ComplexButton[]{cx_1,cx_2,cx_3});

        return menu;
    }
}

