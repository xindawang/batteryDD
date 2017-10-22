package com.iot.dd.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.customer.CustomerEntity;
import com.iot.dd.dao.entity.worker.AdminEntity;
import com.iot.dd.dao.entity.worker.LastIdEntity;
import com.iot.dd.dao.entity.worker.StaffEntity;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import com.iot.dd.dao.mapper.indentAllocationMapper;
import com.iot.dd.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/21.
 */
@RestController
public class UserInfoManageController {
    @Autowired
    private UserManageService usermanageservice;

    @Autowired
    private UserService userService;

    @Autowired
    private LastIdService lastIdService;

    @Autowired
    private ResourceService re;

    //后台信息管理，查询一类用户信息
    @RequestMapping(value = "customer", method = RequestMethod.GET)
    public String findcustomer(int pageSize, int page) {
        PageHelper.startPage(page, pageSize);
        List<CustomerEntity> list = usermanageservice.findCustomerall();
        long total = ((Page<CustomerEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String findadmin(int pageSize, int page) {
        PageHelper.startPage(page, pageSize);
        List<AdminEntity> list = usermanageservice.findAdminall();
        long total = ((Page<AdminEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
    }

    @RequestMapping(value = "/staff", method = RequestMethod.GET)
    public String findstaff(int pageSize, int page) {
        PageHelper.startPage(page, pageSize);
        List<StaffEntity> list = usermanageservice.findStaffall();
        long total = ((Page<StaffEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
    }

    @RequestMapping(value = "/technician", method = RequestMethod.GET)
    public String findtechnician(int pageSize, int page) {
        PageHelper.startPage(page, pageSize);
        List<TechnicianEntity> list = usermanageservice.findTechnicianall();
        long total = ((Page<TechnicianEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
    }


    //删除信息
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteinformation(HttpServletRequest request) {
        String type = request.getParameter("personType");
        String loginName = request.getParameter("loginName");
        System.out.println(type);
        System.out.println(loginName);
        if (type.equals("customer")) {
            int n = usermanageservice.deleteOneCustomer(loginName);
            if (n >= 1) {
                return "删除成功！！";
            } else {
                return "删除失败！！";
            }
        } else if (type.equals("admin")) {
            int n = usermanageservice.deleteOneAdmin(loginName);
            if (n >= 1) {
                return "删除成功！！";
            } else {
                return "删除失败！！";
            }
        } else if (type.equals("staff")) {
            int n = usermanageservice.deleteOneStaff(loginName);
            if (n >= 1) {
                return "删除成功！！";
            } else {
                return "删除失败！！";
            }
        } else {//technician

            int n = usermanageservice.deleteOneTechnician(loginName);
            if (n >= 1) {
                return "删除成功！！";
            } else {
                return "删除失败！！";
            }

        }
    }


    //查询一个用户信息
    @RequestMapping(value = "/findOneStaff", method = RequestMethod.POST)
    public String findOnestaff(HttpServletRequest request) {

        String loginName = request.getParameter("loginName");

        StaffEntity user = new StaffEntity();

        user = usermanageservice.findStaffOne(loginName);

        return JsonTool.objectToJson(user);

    }

    @RequestMapping(value = "/findOneAdmin", method = RequestMethod.POST)
    public String findOneadmin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String loginName = request.getParameter("loginName");

        AdminEntity user = usermanageservice.findAdminOne(loginName);

        return JsonTool.objectToJson(user);


    }

    @RequestMapping(value = "/findOneCustomer", method = RequestMethod.POST)
    public String findOnecustomer(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String loginName = request.getParameter("loginName");
        CustomerEntity user = new CustomerEntity();
        user = usermanageservice.findCustomerOne(loginName);
        return JsonTool.objectToJson(user);

    }

    @RequestMapping(value = "/findOneTechnician", method = RequestMethod.POST)
    public String findOnetechnician(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String loginName = request.getParameter("loginName");
        TechnicianEntity user = new TechnicianEntity();
        user = usermanageservice.findTechnicianOne(loginName);
        return JsonTool.objectToJson(user);

    }

  // 去除字串中所有的空格 replaceAll("\\s","")

    //管理员更新信息
    @RequestMapping(value = "/staffModify", method = RequestMethod.POST)
    public String staffmodify(HttpServletRequest request) {

        StaffEntity user = new StaffEntity();
        String loginName = request.getParameter("loginName").replaceAll("\\s","");
        user.setLoginName(loginName);
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name").replaceAll("\\s",""));
        user.setSex(request.getParameter("sex"));
        user.setCellphone(request.getParameter("cellphone").replaceAll("\\s",""));
        user.setTelephone(request.getParameter("telephone").replaceAll("\\s",""));
        user.setEmail(request.getParameter("email").replaceAll("\\s",""));
        user.setIdNumber(request.getParameter("idNumber").replaceAll("\\s",""));
        user.setAddress(request.getParameter("address"));
        user.setRole(request.getParameter("role"));


//
//        if (request.getParameter("organizationId") == "" || request.getParameter("organizationId") == null) {
//            user.setOrganizationId(0);
//        } else {
//            user.setOrganizationId(Integer.parseInt(request.getParameter("organizationId")));
//        }

        String idNumber = request.getParameter("idNumber").replaceAll("\\s","");
        StaffEntity staff = usermanageservice.findStaffOne(loginName);
        if(!staff.getIdNumber().equals(idNumber)){
            if (idNumber != null && !idNumber.equals("")) {
                StaffEntity staff1 = usermanageservice.checkStaffidNumber(request.getParameter("idNumber"));
                if (staff1 != null) {
                    return "该身份证号已被使用！！";
                }
            }
        }
        return usermanageservice.updateOneStaff(user);


    }

    @RequestMapping(value = "/adminModify", method = RequestMethod.POST)
    public String adminmodify(HttpServletRequest request) {

        AdminEntity user = new AdminEntity();

        user.setLoginName(request.getParameter("loginName").replaceAll("\\s",""));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name").replaceAll("\\s",""));
        user.setCellphone(request.getParameter("cellphone").replaceAll("\\s",""));
        user.setSex(request.getParameter("sex"));
        user.setTelephone(request.getParameter("telephone").replaceAll("\\s",""));
        user.setEmail(request.getParameter("email").replaceAll("\\s",""));
        user.setIdNumber(request.getParameter("idNumber").replaceAll("\\s",""));
        user.setAddress(request.getParameter("address"));
        user.setRole(request.getParameter("role"));

        //身份证号名唯一
        String idNumber = request.getParameter("idNumber").replaceAll("\\s","");

        AdminEntity admin = usermanageservice.findAdminOne(request.getParameter("loginName"));
        if (!admin.getIdNumber().equals(idNumber)) {
            if (idNumber != null && !idNumber.equals("")) {
                AdminEntity admin1 = usermanageservice.checkAdminIdNumber(idNumber);
                if (admin1 != null) {
                    return "该身份证号已被使用！！";
                }
            }
        }
        return usermanageservice.updateOneAdmin(user);


    }

    @RequestMapping(value = "/customerModify", method = RequestMethod.POST)
    public String customermodify(HttpServletRequest request) {

        CustomerEntity user = new CustomerEntity();

        user.setLoginName(request.getParameter("loginName").replaceAll("\\s",""));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name").replaceAll("\\s",""));
        user.setSex(request.getParameter("sex"));
        user.setCellphone(request.getParameter("cellphone").replaceAll("\\s",""));
        user.setTelephone(request.getParameter("telephone").replaceAll("\\s",""));
        user.setEmail(request.getParameter("email").replaceAll("\\s",""));
        user.setAddress(request.getParameter("address"));
        user.setPostcode(request.getParameter("postcode"));
        return usermanageservice.updateOneCustomer(user);


    }

    @RequestMapping(value = "/technicianModify", method = RequestMethod.POST)
    public String technicianmodify(HttpServletRequest request) {

        TechnicianEntity user = new TechnicianEntity();
        String technicianId = request.getParameter("technicianId").replaceAll("\\s","");
        user.setTechnicianId(request.getParameter("technicianId").replaceAll("\\s",""));
        user.setLoginName(request.getParameter("loginName").replaceAll("\\s",""));

        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name").replaceAll("\\s",""));
        user.setSex(request.getParameter("sex"));
        user.setCellphone(request.getParameter("cellphone").replaceAll("\\s",""));
        user.setTelephone(request.getParameter("telephone").replaceAll("\\s",""));
        user.setEmail(request.getParameter("email").replaceAll("\\s",""));
        user.setIdNumber(request.getParameter("idNumber").replaceAll("\\s",""));
        user.setAddress(request.getParameter("address"));
        user.setLicensePlateNumber(request.getParameter("licensePlateNumber").replaceAll("\\s",""));
        user.setCityCode(request.getParameter("cityCode"));


        String idNumber = request.getParameter("idNumber").replaceAll("\\s","");
        TechnicianEntity tech = usermanageservice.findTechnicianOne(request.getParameter("loginName").replaceAll("\\s",""));
        if (!tech.getIdNumber().equals(idNumber)) {
            if (idNumber != null && !idNumber.equals("")) {
                TechnicianEntity tech1 = usermanageservice.checkTechnicianIdNumber(idNumber);
                if (tech1 != null) {
                    return "该身份证号已被使用！！";
                }
            }
        }


        return usermanageservice.updateOneTechnician(user);


    }


    //管理员添加用户
    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    public String addAdmin(HttpServletRequest request, HttpServletResponse response) {

        AdminEntity user = new AdminEntity();

        user.setLoginName(request.getParameter("loginName").replaceAll("\\s",""));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name").replaceAll("\\s",""));
        user.setCellphone(request.getParameter("cellphone").replaceAll("\\s",""));
        user.setTelephone(request.getParameter("telephone").replaceAll("\\s",""));
        user.setEmail(request.getParameter("email").replaceAll("\\s",""));
        user.setIdNumber(request.getParameter("idNumber").replaceAll("\\s",""));
        user.setAddress(request.getParameter("address"));
        user.setRole("admin");

        //用户名唯一
        String loginName = request.getParameter("loginName").replaceAll("\\s","");
        AdminEntity admin = usermanageservice.findAdminOne(loginName);
        if (admin != null) {
            return "该用户名已被使用！！";
        }
        //身份证号名唯一
        String idNumber = request.getParameter("idNumber").replaceAll("\\s","");
        if (idNumber != null && !idNumber.equals("")) {
            AdminEntity admin1 = usermanageservice.checkAdminIdNumber(idNumber);
            if (admin1 != null) {
                return "该身份证号已被使用！！";
            }
        }

        userService.addAdmin(user);
        return "ok";

    }


    @RequestMapping(value = "/addStaff", method = RequestMethod.POST)
    public String addStaff(HttpServletRequest request, HttpServletResponse response) {
        StaffEntity user = new StaffEntity();

        String loginName = request.getParameter("loginName").replaceAll("\\s","");
        user.setLoginName(loginName);
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name").replaceAll("\\s",""));
        user.setSex(request.getParameter("sex"));
        user.setCellphone(request.getParameter("cellphone").replaceAll("\\s",""));
        user.setTelephone(request.getParameter("telephone").replaceAll("\\s",""));
        user.setEmail(request.getParameter("email"));
        user.setIdNumber(request.getParameter("idNumber").replaceAll("\\s",""));
        user.setAddress(request.getParameter("address"));
        user.setRole("客服");

        StaffEntity staff = usermanageservice.findStaffOne(loginName);
        if (staff != null) {
            return "该用户名已被使用！！";
        }

        String idNumber = request.getParameter("idNumber").replaceAll("\\s","");
        if (idNumber != null && !idNumber.equals("")) {
            StaffEntity staff1 = usermanageservice.checkStaffidNumber(idNumber);
            if (staff1 != null) {
                return "该身份证号已被使用！！";
            }
        }

        userService.addStaff(user);


        return "ok";
    }


    @RequestMapping(value = "/addTechnician", method = RequestMethod.POST)
    public String addTechnician(HttpServletRequest request, HttpServletResponse response) {

        String technicianId = request.getParameter("technicianId").replaceAll("\\s","");
        String loginName=request.getParameter("loginName").replaceAll("\\s","");
        TechnicianEntity user = new TechnicianEntity();
        user.setTechnicianId(technicianId);
        user.setLoginName(request.getParameter("loginName").replaceAll("\\s",""));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name").replaceAll("\\s",""));
        user.setSex(request.getParameter("sex"));
        user.setCellphone(request.getParameter("cellphone").replaceAll("\\s",""));
        user.setTelephone(request.getParameter("telephone").replaceAll("\\s",""));
        user.setEmail(request.getParameter("email"));
        user.setIdNumber(request.getParameter("idNumber").replaceAll("\\s",""));
        user.setAddress(request.getParameter("address"));
        user.setLicensePlateNumber(request.getParameter("licensePlateNumber"));
        user.setIswork("0");
        String cityCode = request.getParameter("cityCode");
        user.setCityCode(cityCode);

        //用户名唯一

        TechnicianEntity tech = usermanageservice.findTechnicianOne(loginName);
        if (tech != null) {
            return "该用户名已被使用！！";
        }

        String idNumber = request.getParameter("idNumber").replaceAll("\\s","");
        if (idNumber != null && !idNumber.equals("")) {
            TechnicianEntity tech1 = usermanageservice.checkTechnicianIdNumber(idNumber);
            if (tech1 != null) {
                return "该身份证号已被使用！！";
            }
        }

        //用户编号唯一
        //身份账号唯一
        TechnicianEntity tech2 = usermanageservice.checkTechnicianId(technicianId);
        if (tech2 != null) {
            return "该编号已被使用！！";
        }


        Boolean t = userService.addTechnician(user);
        LastIdEntity entity = lastIdService.find();//技师可用编号
        int id = Integer.parseInt(entity.getIdNumber()) + 1;
        lastIdService.update(id + "");//设置下一个可用编号

        return "ok";
    }

}