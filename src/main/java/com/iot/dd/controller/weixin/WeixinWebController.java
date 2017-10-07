package com.iot.dd.controller.weixin;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.entity.weixin.WeixinOauth2Token;
import com.iot.dd.dao.entity.weixin.WeixinUserBaseMessage;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import com.iot.dd.service.OrderService;
import com.iot.dd.service.weixin.IndentService;
import com.iot.dd.service.weixin.UserInformationService;
import com.iot.dd.service.weixin.WeixinInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.parser.Entity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Created by huanglin on 2017/7/16.
 *
 * 获取用户的基本信息
 */
@RestController
public class WeixinWebController {

    @Value("${downloadPathPrefix}")
    private String downloadPathPrefix;

    @Autowired
    private IndentService indentService;
    //获取aaccess_token的地址

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/getOpenId",method= RequestMethod.POST)
    public String getUserMessage(HttpServletRequest request) throws ServletException, IOException {

        HttpSession session=request.getSession();
        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        String openId;
        List<OrderEntity> indentList=new ArrayList<>();
        // 用户同意授权
        if(session.getAttribute("openId")==null) {
            if (code != null) {
                // 获取网页授权access_token
                WeixinOauth2Token weixinOauth2Token = WeixinInitService.getOauth2AccessToken(code);
                //String accessToken = weixinOauth2Token.getAccessToken();
                // 用户标识
                if (weixinOauth2Token != null) {
                    openId = weixinOauth2Token.getOpenId();
                    session.setAttribute("openId", openId);
                    indentList = indentService.getOrderIdByOpenId(openId);
                }

            }

        }else{
            openId=session.getAttribute("openId").toString();
            indentList = indentService.getOrderIdByOpenId(openId);
        }


        return JsonTool.objectToJson(indentList);
    }








    @RequestMapping(value="/applyServiceValidate",method=RequestMethod.POST)
    public String serviceValidate(HttpServletRequest request) throws UnsupportedEncodingException {
        String result;
        String cellphone=request.getParameter("cellphone");
        String code=request.getParameter("code");


        if(cellphone==null ||cellphone.length()!=11){
            result=JsonTool.objectToJson("0");//电话号码不能少于11位或者为空
        }else{
            String indentId =indentService.getIndentOrderIdByCellphone(cellphone,code);
            if(indentId==null){
                result= JsonTool.objectToJson("1");//订单不存在,请检查电话号码
            }else{//telephone与订单对应
                result= JsonTool.objectToJson(indentId);
            }

        }

        return result;
    }


    @RequestMapping(value="/getWXJsMsg",method=RequestMethod.POST)
    String getLocationMsg(HttpServletRequest request){
        //String targetUrl=request.getParameter("targetUrl");
        String result;
        Map<String,Object> LocationConfigMsg= null;
        try {
            LocationConfigMsg = indentService.WeixingetLocation(request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        result=JsonTool.objectToJson(LocationConfigMsg);

        return result;

    }


    //将用户位置更新到数据库
    @RequestMapping(value="/setCustomerLocation",method = RequestMethod.POST)
    String setCustomerLocation(HttpServletRequest request){
        String result=null;
        String orderId=request.getParameter("orderId");
        Float longitude,latitude;
        if((request.getParameter("longitude")!=null)  &&(request.getParameter("longitude")!=null)){
            longitude=Float.parseFloat(request.getParameter("longitude"));
            latitude=Float.parseFloat(request.getParameter("latitude"));
            result=indentService.updateCustomerLocation(orderId,longitude,latitude);
        }
        return result;
    }


    @RequestMapping(value="/getIndentMsgByOrderId",method=RequestMethod.POST)
    String getCusMsgByphone(HttpServletRequest request){
        String orderId=request.getParameter("orderId");
        List<OrderEntity> indentMsg=indentService.getIndentMsgByOrderId(orderId);

        return JsonTool.objectToJson(indentMsg);
    }

    @RequestMapping(value="/getStatusByOrderId",method=RequestMethod.POST)
    String getStatusByOrderId(HttpServletRequest request){
        String orderId=request.getParameter("orderId");
        Integer status=indentService.getStatusByOrderId(orderId);

        return JsonTool.objectToJson(status);
    }


    @RequestMapping(value="/getAllLocation",method=RequestMethod.POST)
    String getAllLocation(HttpServletRequest request)
    {
        String result;

        String orderId=request.getParameter("orderId");
        result=JsonTool.objectToJson(indentService.getAllLocation(orderId));
        return result;

    }

    @RequestMapping(value="/evaSubmit",method=RequestMethod.POST)
    String submitEvaluation(HttpServletRequest request){
        String result=null;
        Integer techService=Integer.parseInt(request.getParameter("techService"));
        String  techId=request.getParameter("techId");
        Integer deliverySpeed=Integer.parseInt(request.getParameter("deliverySpeed"));
        Integer productQuality=Integer.parseInt(request.getParameter("productQuality"));
        String  orderId=request.getParameter("orderId");

        result=indentService.setIndentEvaluation(orderId,techId,deliverySpeed,productQuality,techService);
        return JsonTool.objectToJson(result);
    }

    @RequestMapping(value = "/getTechId",method=RequestMethod.POST)
        String getTechId(HttpServletRequest request){
        String orderId=request.getParameter("orderId");
        return JsonTool.objectToJson(indentService.getTechId(orderId));

    }

    @RequestMapping(value="/getTechMsg",method=RequestMethod.POST)
    String getTechMsg(HttpServletRequest request){
        TechnicianEntity message;
        String orderId=request.getParameter("orderId");
        message=indentService.getTechnicainMsg(orderId);
        if(message ==null){
            return JsonTool.objectToJson("未指派技师");
        }
        return JsonTool.objectToJson(message);
    }




}
