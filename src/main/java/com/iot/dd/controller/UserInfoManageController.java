package com.iot.dd.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.customer.CustomerEntity;
import com.iot.dd.dao.entity.worker.AdminEntity;
import com.iot.dd.dao.entity.worker.StaffEntity;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import com.iot.dd.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/21.
 */
@RestController
public class UserInfoManageController {
    @Autowired
    private UserManageService   usermanageservice;

  //查询一类用户信息
    @RequestMapping(value="customer",method = RequestMethod.GET )
    public String findcustomer(int pageSize, int page){
        PageHelper.startPage(page, pageSize);
        List<CustomerEntity> list = usermanageservice.findCustomerall();
        long total = ((Page<CustomerEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
    }
    @RequestMapping(value="/admin",method = RequestMethod.GET )
    public String findadmin(int pageSize, int page){
        PageHelper.startPage(page, pageSize);
        List<AdminEntity> list = usermanageservice.findAdminall();
        long total = ((Page<AdminEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
   }

    @RequestMapping(value="/staff",method = RequestMethod.GET )
    public String findstaff(int pageSize, int page){
        PageHelper.startPage(page, pageSize);
        List<StaffEntity> list = usermanageservice.findStaffall();
        long total = ((Page<StaffEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
    }

    @RequestMapping(value="/technician",method = RequestMethod.GET )
    public String findtechnician(int pageSize, int page){
        PageHelper.startPage(page, pageSize);
        List<TechnicianEntity> list = usermanageservice.findTechnicianall();
        long total = ((Page<TechnicianEntity>) list).getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", total);
        return JsonTool.objectToJson(map);
    }




    //删除信息
    @RequestMapping(value="/delete",method = RequestMethod.POST )
    public String deleteinformation(HttpServletRequest request){
        String type=request.getParameter("personType");
        String loginName=request.getParameter("loginName");
        System.out.println(type);
        System.out.println(loginName);
        if(type.equals("customer"))
        {
            int n=usermanageservice.deleteOneCustomer(loginName);
            if(n>=1) {
                return "删除成功！！";
            }
            else{
                return "删除失败！！";
            }
        }
        else if(type.equals("admin")){
            int n=usermanageservice.deleteOneAdmin(loginName);
            if(n>=1) {
                return "删除成功！！";
            }
            else{
                return "删除失败！！";
            }
        }
        else if(type.equals("staff")){
            int n=usermanageservice.deleteOneStaff(loginName);
            if(n>=1) {
                return "删除成功！！";
            }
            else{
                return "删除失败！！";
            }
        }
        else{//technician

            int n= usermanageservice.deleteOneTechnician(loginName);
            if(n>=1) {
                return "删除成功！！";
            }
            else{
                return "删除失败！！";
            }

        }
    }


    //查询一个用户信息
    @RequestMapping(value="/findOneStaff",method = RequestMethod.POST )
    public String findOnestaff(HttpServletRequest request){

        String loginName=request.getParameter("loginName");

        StaffEntity user=new StaffEntity();

        user =usermanageservice.findStaffOne(loginName);

        return JsonTool.objectToJson(user);

    }

    @RequestMapping(value="/findOneAdmin",method = RequestMethod.POST )
    public String findOneadmin(HttpServletRequest request){
        String loginName=request.getParameter("loginName");

        AdminEntity user=usermanageservice.findAdminOne(loginName);

        return JsonTool.objectToJson(user);


    }
    @RequestMapping(value="/findOneCustomer",method = RequestMethod.POST )
    public String findOnecustomer(HttpServletRequest request){

        String loginName=request.getParameter("loginName");
          CustomerEntity user=new CustomerEntity();
        user=usermanageservice.findCustomerOne(loginName);
        return JsonTool.objectToJson(user);

    }

    @RequestMapping(value="/findOneTechnician",method = RequestMethod.POST )
    public String findOnetechnician(HttpServletRequest request){
        String loginName=request.getParameter("loginName");
        TechnicianEntity user=new TechnicianEntity();
        user=usermanageservice.findTechnicianOne(loginName);
        return JsonTool.objectToJson(user);

    }




    //更新信息

    @RequestMapping(value="/staffModify",method = RequestMethod.POST )
    public String staffmodify(HttpServletRequest request){

        StaffEntity user=new StaffEntity();

         user.setLoginName(request.getParameter("loginName"));
         user.setPassword(request.getParameter("password"));
         user.setName(request.getParameter("name"));
         user.setSex(request.getParameter("sex"));
         user.setCellphone(request.getParameter("cellphone"));
         user.setTelephone(request.getParameter("telephone"));
         user.setEmail(request.getParameter("email"));
         user.setIdNumber(request.getParameter("idNumber"));
         user.setAddress(request.getParameter("address"));
         user.setRole(request.getParameter("role"));

        if(request.getParameter("organizationId")==""||request.getParameter("organizationId")==null) {
            user.setOrganizationId(0);
        }
        else {
            user.setOrganizationId(Integer.parseInt(request.getParameter("organizationId")));
        }


        return usermanageservice.updateOneStaff(user);


    }

    @RequestMapping(value="/adminModify",method = RequestMethod.POST )
    public String adminmodify(HttpServletRequest request){

        AdminEntity user=new AdminEntity();

        user.setLoginName(request.getParameter("loginName"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setCellphone(request.getParameter("cellphone"));
        user.setTelephone(request.getParameter("telephone"));
        user.setEmail(request.getParameter("email"));
        user.setIdNumber(request.getParameter("idNumber"));
        user.setAddress(request.getParameter("address"));
        user.setRole(request.getParameter("role"));


        return usermanageservice.updateOneAdmin(user);


    }

    @RequestMapping(value="/customerModify",method = RequestMethod.POST )
    public String customermodify(HttpServletRequest request){

       CustomerEntity user=new CustomerEntity();

        user.setLoginName(request.getParameter("loginName"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setSex(request.getParameter("sex"));
        user.setCellphone(request.getParameter("cellphone"));
        user.setTelephone(request.getParameter("telephone"));
        user.setEmail(request.getParameter("email"));
        user.setAddress(request.getParameter("address"));
        user.setPostcode(request.getParameter("postcode"));


        return usermanageservice.updateOneCustomer(user);


    }

    @RequestMapping(value="/technicianModify",method = RequestMethod.POST )
    public String technicianmodify(HttpServletRequest request){

        TechnicianEntity user=new TechnicianEntity();
        user.setTechnicianId(request.getParameter("technicianId"));
        user.setLoginName(request.getParameter("loginName"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setSex(request.getParameter("sex"));
        user.setCellphone(request.getParameter("cellphone"));
        user.setTelephone(request.getParameter("telephone"));
        user.setEmail(request.getParameter("email"));
        user.setIdNumber(request.getParameter("idNumber"));
        user.setAddress(request.getParameter("address"));
        user.setLicensePlateNumber(request.getParameter("licensePlateNumber"));

        if(request.getParameter("organizationId")==""||request.getParameter("organizationId")==null) {
            user.setOrganizationId(0);
        }
        else {
            user.setOrganizationId(Integer.parseInt(request.getParameter("organizationId")));
        }


        return usermanageservice.updateOneTechnician(user);


    }



}