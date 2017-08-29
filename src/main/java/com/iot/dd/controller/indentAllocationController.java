package com.iot.dd.controller;

import com.iot.dd.Tools.Base64decode;
import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.Indent.ConfirmEntity;
import com.iot.dd.dao.entity.Indent.IndentAllocationEntity;
import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import com.iot.dd.dao.mapper.OrderMapper;
import com.iot.dd.service.ConfirmService;
import com.iot.dd.service.ResourceService;
import com.iot.dd.service.UserManageService;
import com.iot.dd.service.indentAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by admin on 2017/7/31.
 */
@RestController
public class indentAllocationController {

    @Autowired
    private indentAllocationService service;

    @Autowired
    private UserManageService user;

    @Autowired
    indentAllocationService allocation;
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ResourceService resourceService;

    @Autowired
    ConfirmService confirmService;

    //网页前端的请求
    /*
    * 设置订单编号select选择框
    * */
    @RequestMapping(value = "/OrderId", method = RequestMethod.GET)
    public String findOrderId(HttpServletRequest request) {

        return JsonTool.listToJson(service.findOrder());
    }

    //设置技师名称select选择框
    @RequestMapping(value = "/technicianId", method = RequestMethod.GET)
    public String findtechnician(HttpServletRequest request) {

        return JsonTool.listToJson(service.findtechnicianId());
    }

    //根据订单编号查找转发记录(多表链接查询)
    @RequestMapping(value = "/findByOrderId", method = RequestMethod.GET)
    public String findByOrderId(HttpServletRequest request) {
        String orderId = request.getParameter("orderId");

        return JsonTool.javaBeanToJson(service.find(orderId));
    }

    //技师编号查找转发记录
    @RequestMapping(value = "/findByTchnicianId", method = RequestMethod.GET)
    public String findBytchnicianId(HttpServletRequest request) {
        String technicianId = request.getParameter("technicianId");
        return JsonTool.listToJson(service.findlist(technicianId));
    }

   /*
   *  android 端请求
    */


    //技师查看已经接下的订单，返回订单编号，电池型号，
    @RequestMapping(value = "/indentAllocation", method = RequestMethod.GET)
    public String find(HttpServletRequest request, HttpServletResponse response) {

        String loginName = request.getParameter("loginName");
        //查找technicianId
        TechnicianEntity technician = user.findTechnicianOne(loginName);
        //根据技师编号查早，接下的订单
        List<IndentAllocationEntity> listAllocation = allocation.findAllocation(technician.getTechnicianId());

        List<Map<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < listAllocation.size(); i++) {
            IndentAllocationEntity entity = listAllocation.get(i);
            OrderEntity order = orderMapper.findOrder(entity.getOrderId());
            String cityName = resourceService.findCityName(order.getCityCode());
            if (order.getStatus() == 3) {//筛选该技师已接下的订单
                Map<String, String> map = new HashMap<String, String>();
                map.put("mask", "accept");
                map.put("orderId", order.getOrderId());
                map.put("batteryType", order.getBatteryType());
                map.put("cityName", cityName);
                map.put("customerCellphone", order.getCustomerCellphone());
                map.put("customerTelephone", order.getCustomerTelephone());
                map.put("licensePlateNumber", order.getLicensePlateNumber());
                map.put("customerLatitude", entity.getCustomerLatitude() + "");
                map.put("customerLongitude", entity.getCustomerLongitude() + "");
                map.put("technicianId", entity.getTechnicianId());
                map.put("address", order.getAddress());
                mapList.add(map);
            }
            if (order.getStatus() == 2) {
                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("mask", "wait");
                map1.put("orderId", order.getOrderId());
                map1.put("batteryType", order.getBatteryType());
                map1.put("cityName", cityName);
                map1.put("customerCellphone", order.getCustomerCellphone());
                map1.put("customerTelephone", order.getCustomerTelephone());
                map1.put("licensePlateNumber", order.getLicensePlateNumber());
                map1.put("customerLatitude", entity.getCustomerLatitude() + "");
                map1.put("customerLongitude", entity.getCustomerLongitude() + "");
                map1.put("technicianId", entity.getTechnicianId());
                map1.put("address", order.getAddress());
                mapList.add(map1);
            }
        }
        String result = JsonTool.listToJson(mapList).toString();
        return result;
    }

    //技师查看客服指派的订单(未接)
    @RequestMapping(value = "/Allocation", method = RequestMethod.GET)
    public String findAllocation(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String loginName = request.getParameter("loginName");
        //查找technicianId
        TechnicianEntity technician = user.findTechnicianOne(loginName);
        //根据技师编号查早，接下的订单
        List<IndentAllocationEntity> listAllocation = allocation.findAllocation(technician.getTechnicianId());
        List<Map<String, String>> mapList = new ArrayList<>();
        OrderEntity order;
        for (int i = 0; i < listAllocation.size(); i++) {
            IndentAllocationEntity entity = listAllocation.get(i);
            order = orderMapper.findOrder(entity.getOrderId());
            if (order.getStatus() == 2) {//筛选客服给技师的派单，2表示已派单
                //用城市名替换cityCode
                String cityName = resourceService.findCityName(order.getCityCode());
                Map<String, String> map1 = new HashMap<String, String>();
                map1.put("orderId", order.getOrderId());
                map1.put("batteryType", order.getBatteryType());
                map1.put("cityName", cityName);
                map1.put("customerCellphone", order.getCustomerCellphone());
                map1.put("customerTelephone", order.getCustomerTelephone());
                map1.put("licensePlateNumber", order.getLicensePlateNumber());
                map1.put("customerLatitude", entity.getCustomerLatitude() + "");
                map1.put("customerLongitude", entity.getCustomerLongitude() + "");
                map1.put("technicianId", entity.getTechnicianId());
                map1.put("address", order.getAddress());
                mapList.add(map1);
            }
        }
        //将orderList已json格式字符串返回
        return JsonTool.listToJson(mapList).toString();
    }

    //查看技师已完成的订单(历史记录)
    @RequestMapping(value = "/completeRecord", method = RequestMethod.GET)
    public String findRecordIndent(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String loginName = request.getParameter("loginName");
        //查找technicianId
        TechnicianEntity technician = user.findTechnicianOne(loginName);
        //根据技师编号查早，接下的订单
        List<IndentAllocationEntity> listAllocation = allocation.findAllocation(technician.getTechnicianId());

        List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
        OrderEntity order;
        for (int i = 0; i < listAllocation.size(); i++) {
            IndentAllocationEntity entity = listAllocation.get(i);
            order = orderMapper.findOrder(entity.getOrderId());
            String cityName = resourceService.findCityName(order.getCityCode());
            if (order.getStatus() == 4) {//订单已完成
                //用城市名替换cityCode
                Map<String, String> map = new HashMap<String, String>();
                map.put("mask", "0");
                map.put("orderId", order.getOrderId());
                map.put("batteryType", order.getBatteryType());
                map.put("cityName", cityName);
                map.put("licenseNumber", order.getLicensePlateNumber());
                map.put("finishTime", order.getFinishTime() + "");
                map.put("starNum", "0");
                maplist.add(map);
            }
            if (order.getStatus() == 5) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("mask", "1");
                map.put("orderId", order.getOrderId());
                map.put("batteryType", order.getBatteryType());
                map.put("cityName", cityName);
                map.put("licenseNumber", order.getLicensePlateNumber());
                map.put("finishTime", order.getFinishTime() + "");
                map.put("starNum", "4");
                maplist.add(map);
            }
        }
        //将orderList已json格式字符串返回
        return JsonTool.listToJson(maplist).toString();
    }


    //更具订单编号查找技师和客户位置（）indent_allocation）
    @RequestMapping(value = "/findPosition", method = RequestMethod.GET)
    public String findPosition(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String orderId = request.getParameter("orderId");
        IndentAllocationEntity entity = service.findPosition(orderId);
        return JsonTool.javaBeanToJson(entity);
    }

    /**
     * 技师接单事务处理
     * 修改indent中statues字段,从‘已派单’改为‘已接单’
     */
    @RequestMapping(value = "/AcceptAllocation", method = RequestMethod.GET)
    public String AcceptAllocation(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String orderId = request.getParameter("orderId");
        int statues = 3;//已接单
        //修改indent中的statuew字段
        service.setAcceptTime(orderId, new Date());
        boolean t = orderMapper.updateStatues(orderId, statues);
        if (t == true)
            return "OK";
        else
            return "error";
    }

    /**
     * 技师拒绝接单事务处理
     * 修改indent中statues字段,从‘已派单’改为‘已导入’
     * 同时删除indentAllocation表中的派单记录
     */
    @RequestMapping(value = "/RefuseAllocation", method = RequestMethod.GET)
    public String refuseAllocation(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String orderId = request.getParameter("orderId");
        int statues = 1;//已录入
        //修改indent中的statuew字段
        boolean t = orderMapper.updateStatues(orderId, statues);
        //删除相关信息中的记录根据orderId
        String technicinaId = null;
        boolean t2 = service.deleteByOrderId(orderId, technicinaId);
        return "OK";
    }

    /*
    * 订单完成
    * */
    @RequestMapping(value = "/androidComplete", method = RequestMethod.POST)
    public String CompleteIndent(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String orderId = request.getParameter("orderId");
        String technicianId = request.getParameter("technicianId");
        String batteryImg = request.getParameter("batteryImg");
        String carImg = request.getParameter("carImg");
        String qualityImg = request.getParameter("qualityImg");

        String path = "E:\\1111\\batteryDD\\src\\main\\resources\\static\\confirmImg\\";

        //绝对路径
        String path1 = path + orderId + "_battery.jpg";
        String path2 = path + orderId + "_carNum.jpg";
        String path3 = path + orderId + "_quality.jpg";
        //相对路径
        String path4 = "/confirmImg" + orderId + "_battery.jpg";
        String path5 = "/confirmImg" + orderId + "_carNum.jpg";
        String path6 = "/confirmImg" + orderId + "_quality.jpg";

        //STRING 格式的图片解码保存
        Base64decode decode = new Base64decode();
        Boolean t1 = decode.string2Image(batteryImg, path1);
        Boolean t2 = decode.string2Image(carImg, path2);
        Boolean t3 = decode.string2Image(qualityImg, path3);
        if (t1 == false || t2 == false || t3 == false) {//解码失败
            return "error";
        }

        //将图片相对路径保存
        ConfirmEntity entity = new ConfirmEntity();
        entity.setOrderId(orderId);
        entity.setTechnicianId(technicianId);
        entity.setBatteryImg(path4);
        entity.setLicensePlateNumberImg(path5);
        entity.setQualityAssuranceImg(path6);
        entity.setTime(new Date());

        confirmService.addOneRecord(entity);


        int statues = 4;//已录入
        //修改indent中的statuew字段
        boolean t = orderMapper.updateStatues(orderId, statues);
        return "OK";
    }
}
