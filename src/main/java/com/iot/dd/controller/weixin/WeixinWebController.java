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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by huanglin on 2017/7/16.
 *
 * 获取用户的基本信息
 */
@RestController
public class WeixinWebController {

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
        String indentId=null;
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
                    indentId = indentService.getOrderIdByOpenId(openId);
                }

            }

        }else{
            openId=session.getAttribute("openId").toString();
            indentId = indentService.getOrderIdByOpenId(openId);
        }

        // 跳转到index.jsp
        return JsonTool.objectToJson(indentId);
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
        String result;
        String orderId=request.getParameter("orderId");
        Float longitude=Float.parseFloat(request.getParameter("longitude"));
        Float latitude=Float.parseFloat(request.getParameter("latitude"));

        result=indentService.updateCustomerLocation(orderId,longitude,latitude);
        return result;
    }


    @RequestMapping(value="/getIndentMsgByOrderId",method=RequestMethod.POST)
    String getCusMsgByphone(HttpServletRequest request){
        String cellphone=request.getParameter("orderId");
        List<OrderEntity> indentMsg=indentService.getIndentMsgByOrderId(cellphone);

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
        String result="success";
        Integer techService=Integer.parseInt(request.getParameter(""));
        String  techId=request.getParameter("");
        Integer deliverySpeed=Integer.parseInt(request.getParameter(""));
        Integer productQuality=Integer.parseInt(request.getParameter(""));

         return result;
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
        return JsonTool.objectToJson(message);
    }

}
