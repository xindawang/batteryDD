package com.iot.dd.service.weixin;

import com.iot.dd.dao.entity.weixin.TextMessage;
import com.thoughtworks.xstream.XStream;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by huanglin on 2017/7/12.
 */

//对事件消息的处理。。
public class NewsService {

    //消息类型常量
    public static final String MESSAGE_TEXT="text";
    public static final String MESSAGE_IMAGE="image";
    public static final String MESSAGE_VOICE="voice";
    public static final String MESSAGE_LINK="link";
    public static final String MESSAGE_LOCATION="location";
    public static final String MESSAGE_EVENT="event";//事件
    public static final String MESSAGE_SUBSCRIBE="subscribe";//订阅
    public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";//取消订阅
    public static final String MESSAGE_CLICK="CLICK";
    public static final String MESSAGE_VIEW="VIEW";
    public static final String MESSAGE_LOCATION1="LOCATION";

    //因为事件消息传过来的消息格式是xml格式。
    //xml转成集合。
    public static Map<String,String> xmlToMap(HttpServletRequest request)
            throws IOException,DocumentException {

        Map<String, String> map=new HashMap<String,String>();

        SAXReader reader=new SAXReader();

        //从request获取输入流
        InputStream ins=request.getInputStream();
        Document doc=reader.read(ins);

        Element root=doc.getRootElement();

        List<Element> list=root.elements();

        for(Element e: list)
            map.put(e.getName(),e.getText());
        ins.close();

        return map;

    }



    //厉害，， 对象类型转成xml的方法。  xstream.jar
    public static String textMessageToXml(TextMessage textMessage){
        XStream xStream=new XStream();
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);

    }


    //对消息事件进行回复，对格式进行设置
    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage text=new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());//long --String
        text.setContent(content);

        return textMessageToXml(text);
    }
    //主菜单回复的content
    public static String menuText(){
        StringBuffer sb=new StringBuffer();
        sb.append("欢迎您关注电池滴滴微信公众号，请按照菜单提示操作：\n");
        sb.append("1、如果您已在电商平台下单，请点击\"申请服务\"进行电池更换申请。\n");
        sb.append("注意：申请服务时请填写您在电商平台下单时填写的电话号码 \n");
        sb.append("2、您也可以点击\"我的订单\"查看您的订单");
        sb.append("3、技师可以通过\"APP下载\"下载App");
        return sb.toString();
    }




}
