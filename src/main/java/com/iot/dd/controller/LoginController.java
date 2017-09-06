package com.iot.dd.controller;


import com.iot.dd.Tools.JsonTool;
import com.iot.dd.service.LastIdService;
import com.iot.dd.service.ResourceService;
import com.iot.dd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.iot.dd.dao.entity.worker.*;

import java.io.UnsupportedEncodingException;

/**
 * Created by huanglin on 2017/7/13.
 */
@RestController
//控制类，控制页面跳转，数据传输
public class LoginController {
    //自动注入userService，用来处理业务
    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService re;
    @Autowired
    private LastIdService lastIdService;


    //跳转链接，跳转到主页
    @RequestMapping("")
    public String index(HttpServletResponse response) {
        //重定向到 /index
        return response.encodeRedirectURL("/index");
    }

    //真正主页，用户在访问 XXXXX/index就会跳转该方法，
    // 这个XXXXX是你的域名，自己电脑上的话一般都是127.0.0.1:8080或者是localhost：8080
    // 8080是端口号，端口号根据tomcat设置而改变，默认值是8080
    @RequestMapping("/index")
    public String home(Model model) {
        //对应到templates文件夹下面的index
        return "index";
    }

    //对于不同的method是对于不同的请求方式对应不同的方法
    //也就是说，如果你的请求方式是get,也就只会调用get下的方法。

    //进入注册页面，使用Get请求，REST风格的URL能更有雅的处理问题
    //更新register网页时（get）会调用当前方式，也就是更新网页的作用。


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String qwe(HttpServletRequest request) {
        String result = null;
        if (request.getParameter("role").equals("admin")) {
            AdminEntity adminEntity = new AdminEntity();
            adminEntity.setLoginName(request.getParameter("username"));
            adminEntity.setPassword(request.getParameter("password"));
            adminEntity.setRole(request.getParameter("role"));
            result = userService.registerAdmin(adminEntity);
        } else {
            StaffEntity staffEntity = new StaffEntity();
            staffEntity.setLoginName(request.getParameter("username"));
            staffEntity.setPassword(request.getParameter("password"));
            staffEntity.setRole(request.getParameter("role"));
            result = userService.registerStaff(staffEntity);
        }
        return result;
    }


    //登陆
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(HttpServletResponse response,
                            HttpServletRequest request,
                            HttpSession session) {
        String result = "请选择用户类型！";
        String role = request.getParameter("role");
        String loginName = request.getParameter("username");
        String password = request.getParameter("password");
        if (role == null) {
            return result;
        }
        if (role.equals("staff")) {//客服
            session = request.getSession();
            session.setAttribute("loginName", loginName);
            session.setAttribute("role", role);
            result = userService.staffLogin(loginName, password);
        } else if (role.equals("admin")) {
            session.setAttribute("loginName", loginName);
            session.setAttribute("role", role);
            result = userService.adminLogin(loginName, password);
        }
        return result;
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public String loginOut(HttpSession session) {
        //从session中删除user属性，用户退出登录
        session.removeAttribute("user");
        return "index";
    }

    //技师登陆
    @RequestMapping(value="/technicianLogin", method = RequestMethod.GET)
    public String technicianLogin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        return userService.technicianLogin(loginName, password);
    }


    //技师注册
    @RequestMapping(value = "/technicianSignUp", method = RequestMethod.GET)
    public String technicianSignUp(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        String cityName = request.getParameter("cityName");

        LastIdEntity entity=lastIdService.find();//技师可用编号
        int id=Integer.parseInt(entity.getIdNumber())+1;
        lastIdService.update(id+"");//设置下一个可用编号
        //获取城市的编码
        String cityCode = re.findCityCODE(cityName);
        TechnicianEntity user = new TechnicianEntity();
        user.setLoginName(request.getParameter("loginName"));
        user.setPassword(request.getParameter("password"));
        user.setTechnicianId(entity.getIdPre()+entity.getIdNumber());
        user.setName(request.getParameter("name"));
        user.setTelephone(request.getParameter("cellphone"));
        user.setCityCode(cityCode);
        user.setAddress(request.getParameter("address"));
        user.setIdNumber(request.getParameter("idNumber"));
        user.setLicensePlateNumber(request.getParameter("licensePlateNumber"));
        return userService.registerTechnician(user);
    }


//     //管理员和客服信息完善
//    @RequestMapping(value = "/modifyInformation", method = RequestMethod.POST)
//    public String modifyinfromation(HttpServletRequest request) {
//        //工作人员信息完善
//        String loginName=request.getParameter("loginName");
//        String name=request.getParameter("name");
//        String password=request.getParameter("password");
//        String cellphone=request.getParameter("cellphone");
//        String telephone=request.getParameter("telephone");
//        String address=request.getParameter("address");
//        String idNumber=request.getParameter("idNumber");
//        String email=request.getParameter("email");
//        String role=request.getParameter("role");
//      if(role.equals("admin")){
//
//          AdminEntity dUser=new AdminEntity();
//          dUser.setLoginName(loginName);
//          dUser.setName(name);
//          dUser.setPassword(password);
//          dUser.setCellphone(cellphone);
//          dUser.setTelephone(telephone);
//          dUser.setIdNumber(idNumber);
//          dUser.setEmail(email);
//          dUser.setAddress(address);
//          dUser.setRole(role);
//
//        return  userService.adminInformation(dUser);
//      }
//      else{
//
//          StaffEntity sUser=new StaffEntity();
//            sUser.setLoginName(loginName);
//            sUser.setName(name);
//            sUser.setPassword(password);
//            sUser.setCellphone(cellphone);
//            sUser.setTelephone(telephone);
//            sUser.setIdNumber(idNumber);
//            sUser.setEmail(email);
//            sUser.setAddress(address);
//            sUser.setRole(role);
//
//            return userService.staffInformation(sUser);
//
//      }
//    }
//
//    //查询管理员和客服信息
//    @RequestMapping(value ="/queryInformation", method = RequestMethod.POST)
//    public String query_Admin_and_Staff(HttpServletRequest request,HttpSession session){
//        String role=(String) session.getAttribute("role");
//        String loginName=(String) session.getAttribute("loginName");
//      if(role.equals("admin")){
//            AdminEntity dUser=new AdminEntity();
//          dUser=userService.findAdminByLogin_name(loginName);
//          dUser.setRole(role);
//            return JsonTool.objectToJson(dUser);
//        }
//        else{
//            StaffEntity sUser=new StaffEntity();
//            sUser=userService.findStaffByLogin_name(loginName);
//            sUser.setRole(role);
//          return JsonTool.objectToJson(sUser);
//
//        }
//
//    }


}