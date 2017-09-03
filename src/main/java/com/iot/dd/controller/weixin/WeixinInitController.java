package com.iot.dd.controller.weixin;

import com.iot.dd.service.weixin.CheckService;
import com.iot.dd.service.weixin.MenuService;
import com.iot.dd.service.weixin.NewsService;
import org.dom4j.DocumentException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by huanglin on 2017/7/11.
 */
@RestController
public class WeixinInitController extends HttpServlet {

    public static float latitude_1;
    public static float longitude_1;


    //进行微信服务器与后台服务器的校验
    @RequestMapping(value = "/", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        //防止数据为空时报空指针错误
        if(signature !=null && timestamp!=null && nonce!=null && echostr !=null) {
            PrintWriter out = resp.getWriter();
            //验证成功，则返回echostr
            if (CheckService.checkSignature(signature, timestamp, nonce)) {
                System.out.println("check successfully");
                out.print(echostr);
            }
        }

    }

    // 对事件消息的回复或者其他操作
    //post方式
    @RequestMapping(value = "/", method = RequestMethod.POST)
    protected void doPost(Model model, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        Map<String, String> map = null;
        try {
            map = NewsService.xmlToMap(req);

            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            String message = null;
            if (NewsService.MESSAGE_TEXT.equals(msgType)) {
                if ("1".equals(content)) {
                    content = "本微信号会获取您的位置，请注意";
                    message = NewsService.initText(toUserName, fromUserName, content);
                } else {
                    content = "回复无效，请回复1";
                    message = NewsService.initText(toUserName, fromUserName, content);
                }
            } else if (NewsService.MESSAGE_LOCATION.equals(msgType)) {

                float latitude = Float.valueOf(map.get("Location_X"));//纬度
                float longitude = Float.parseFloat(map.get("Location_Y"));//经度
                latitude_1 = latitude;
                longitude_1 = longitude;

            } else if (NewsService.MESSAGE_EVENT.equals(msgType)) {
                if (map.get("Event").equals("subscribe"))
                    message = NewsService.initText(toUserName, fromUserName, NewsService.menuText());
            }
            if(message !=null){
                //System.out.println(message);
                out.print(message);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    //在修改菜单代码后要进行访问createMenu页面对菜单进行修改
    @RequestMapping(value = "/createMenu",method = RequestMethod.POST)
    public String  CreateMenu() {
        String result="";
        int status = MenuService.createMenu(MenuService.getMenu());
		if(status==0)
        {
            result="菜单创建成功！";
        }else
        {
            result="菜单创建失败！";

        }
        System.out.println(result);
		return result;

    }

}
