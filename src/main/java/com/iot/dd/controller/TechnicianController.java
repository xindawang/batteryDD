package com.iot.dd.controller;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.Indent.IndentEvaluationEntity;
import com.iot.dd.dao.entity.worker.CheckTokenEntity;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import com.iot.dd.dao.mapper.CheckTokenMapper;
import com.iot.dd.service.UserManageService;
import com.iot.dd.service.indentAllocationService;
import com.iot.dd.service.indentEvaluationService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/9/8.
 */
@RestController
public class TechnicianController {
    @Autowired
    private UserManageService usermanageservice;

    @Autowired
    private indentAllocationService indentAlloccation;

    @Autowired
    private indentEvaluationService evaluation;

    @Autowired
    private CheckTokenMapper checkMapper;

    /*
    * android
    * */

    //验证是否在多台设备登录
    @RequestMapping(value = "/onOneCellphone", method = RequestMethod.GET)
    public String checkTechnician(HttpServletRequest request, HttpServletResponse response) {
        String loginName = request.getParameter("loginName");
        String token=request.getParameter("token");
        CheckTokenEntity entity=new CheckTokenEntity();
        entity=checkMapper.findOne(loginName);
        Map<String,String>map=new HashMap<>();
        if(entity.getToken().equals(token)){
            map.put("mask","OK");
        }
        else {
            map.put("mask","reLogin");
        }
        return JsonTool.objectToJson(map);
    }
    @RequestMapping(value = "/findOneTechnician", method = RequestMethod.GET)
    public String findeOneTechnician(HttpServletRequest request, HttpServletResponse response) {
        String loginName = request.getParameter("loginName");
        TechnicianEntity user = new TechnicianEntity();
        user = usermanageservice.findTechnicianOne(loginName);
        return JsonTool.objectToJson(user);
    }


    //更新技师经纬度位置信息（indent ，）indent_allocation
    @RequestMapping(value = "/technicianAddress", method = RequestMethod.GET)
    public String updatePosition(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String technicianName = request.getParameter("loginName");
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        double latitude = Double.parseDouble(request.getParameter("latitude"));

        //根据登陆名找id
        TechnicianEntity technician = usermanageservice.findTechnicianOne(technicianName);
        String technicianId = technician.getTechnicianId();

        //technician表的经纬度
        usermanageservice.updateTechnicianLocation(technicianId, longitude, latitude);
        //indent_allocation
        indentAlloccation.SetTechnicianPosition(technicianId, longitude, latitude);
        return "Ok";
    }

    @RequestMapping(value = "/technicianUpdateName", method = RequestMethod.GET)
    public String updateName(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String technicianId = request.getParameter("technicianId");
        String name = request.getParameter("name");
        boolean t = usermanageservice.updateName(technicianId, name);
        if (t) {
            return "OK";
        } else {
            return "ERROR";
        }
    }

    @RequestMapping(value = "/technicianUpdateGender", method = RequestMethod.GET)
    public String updateGender(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String technicianId = request.getParameter("technicianId");
        String gender = request.getParameter("gender");
        boolean t = usermanageservice.updateGender(technicianId, gender);
        if (t) {
            return "OK";
        } else {
            return "ERROR";
        }
    }

    @RequestMapping(value = "/technicianUpdateCellphone", method = RequestMethod.GET)
    public String updateCellphone(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String technicianId = request.getParameter("technicianId");
        String cellphone = request.getParameter("cellphone");
        boolean t = usermanageservice.updateCellphone(technicianId, cellphone);
        if (t) {
            return "OK";
        } else {
            return "ERROR";
        }
    }

    @RequestMapping(value = "/technicianUpdateTelephone", method = RequestMethod.GET)
    public String updateTelepone(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String technicianId = request.getParameter("technicianId");
        String telephone = request.getParameter("telephone");
        boolean t = usermanageservice.updateTelephone(technicianId, telephone);
        if (t) {
            return "OK";
        } else {
            return "ERROR";
        }
    }

    @RequestMapping(value = "/technicianUpdateEmail", method = RequestMethod.GET)
    public String updateEmail(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String technicianId = request.getParameter("technicianId");
        String email = request.getParameter("email");
        boolean t = usermanageservice.updateEmial(technicianId, email);
        if (t) {
            return "OK";
        } else {
            return "ERROR";
        }
    }


    @RequestMapping(value = "/technicianUpdateIdNumber", method = RequestMethod.GET)
    public String updateIdNumber(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String technicianId = request.getParameter("technicianId");
        String idNumber = request.getParameter("idNumber");
        boolean t = usermanageservice.updateIdNumber(technicianId, idNumber);
        if (t) {
            return "OK";
        } else {
            return "ERROR";
        }
    }

    @RequestMapping(value = "/technicianUpdateCarNumber", method = RequestMethod.GET)
    public String updateCarNumber(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String technicianId = request.getParameter("technicianId");
        String carNumber = request.getParameter("carNumber");
        boolean t = usermanageservice.updateCarNumber(technicianId, carNumber);
        if (t) {
            return "OK";
        } else {
            return "ERROR";
        }
    }

    @RequestMapping(value = "/technicianUpdatePosition", method = RequestMethod.GET)
    public String updateAddress(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String technicianId = request.getParameter("technicianId");
        String cityCode = request.getParameter("cityCode");
        String address = request.getParameter("address");


        boolean t = usermanageservice.updateAddress(technicianId, cityCode, address);
        if (t) {
            return  "OK";
        } else {
            return "ERROR";
        }
    }


    //查找订单的评价
    @RequestMapping(value = "/findOneEvaluation", method = RequestMethod.GET)
    public String findOne(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String orderId = request.getParameter("orderId");
        IndentEvaluationEntity entity = new IndentEvaluationEntity();
        entity = evaluation.findOne(orderId);
        return JsonTool.objectToJson(entity);

    }

}
