package com.iot.dd.Controller;


import com.iot.dd.Dao.UserDao;
import com.iot.dd.Tools.JsonTool;

import com.iot.dd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.iot.dd.domain.worker.*;

import java.util.HashMap;
import java.util.Map;

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
    private UserDao userDao;


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
    public String registerPost(HttpServletRequest request) {
        String result=null;
        if(request.getParameter("role").equals("admin"))
        {
            AdminEntity adminEntity=new AdminEntity();
            adminEntity.setName(request.getParameter("name"));
            adminEntity.setPassword(request.getParameter("password"));
            result=userService.registerAdmin(adminEntity);
        }
        else{
            StaffEntity staffEntity=new StaffEntity();
            staffEntity.setName(request.getParameter("name"));
            staffEntity.setPassword(request.getParameter("password"));
            result=userService.registerStaff(staffEntity);
        }
        return result;
    }




    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(HttpServletResponse response,
                            HttpServletRequest request,
                            HttpSession session) {
        String result=null;
        String role=request.getParameter("role");
        String name=request.getParameter("name");
        String password=request.getParameter("password");
        if(role=="staff"){//客服
            result=userService.staffLogin(name,password);
        }
        else{
            result=userService.adminLogin(name,password);
        }
        Map<String,Object> re = new HashMap();
        re.put("result",result);
        return JsonTool.objectToJson(userDao.selectAdminUser(name));
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public String loginOut(HttpSession session) {
        //从session中删除user属性，用户退出登录
        session.removeAttribute("user");
        return "index";
    }
}