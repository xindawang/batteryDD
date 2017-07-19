package com.iot.dd.Controller;


import com.iot.dd.domain.UserMessage;
import com.iot.dd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.iot.dd.domain.worker.*;
import com.iot.dd.service.*;
/**
 * Created by huanglin on 2017/7/13.
 */
@RestController
//控制类，控制页面跳转，数据传输
public class LoginController {
    //自动注入userService，用来处理业务
    @Autowired
    private UserService userService;



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
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerGet() {
        return "register";
    }

    @RequestMapping(value = "/qwe", method = RequestMethod.GET)
    public String qwe(HttpServletRequest request) {
        if(request.getParameter("role").equals("admin"))
        {
            adminEntity adminEntity=new adminEntity();
            adminEntity.setName(request.getParameter("name"));
            adminEntity.setPassword(request.getParameter("password"));
            String result=userService.registerAdmin(adminEntity);
        }
        else{
            StaffEntity staffEntity=new StaffEntity();
            staffEntity.setName(request.getParameter("name"));
            staffEntity.setPassword(request.getParameter("password"));
        }
        return "def";
    }

    //Model类的作用？？
    //注册用户，使用POST，传输数据
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPost(Model model,
                               //这里和模板中的th:object="${user}"对应起来,也就是就当用户注册信息并点击post后，
                               // 调用该方法，并且将填写的user数据传给后端
                               @ModelAttribute(value = "user") UserMessage user,
                               HttpServletResponse response) {
        //使用userService处理业务
        //String result = userService.register(user);
        //将结果放入model中，在模板中可以取到model中的值
        //model.addAttribute("result", result);//往前台视图传参数。
        return ("/index");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(Model model,
                            @ModelAttribute(value = "user") UserMessage user,
                            HttpServletResponse response,
                            HttpServletRequest request,
                            HttpSession session) {
        System.out.println("test");
        String path = request.getServletContext().getRealPath("/");
        String result = userService.login(user);
        if (result.equals("登陆成功")) {
            //添加到session中，session是一次会话，在本次会话中都可以取到session中的值
            //若是session中有用户存在则会覆盖原来的user，当session中的user存在时判定用户存在
            session.setAttribute("user",user);
        }
        model.addAttribute("result", result);//与seesion的差别
        return "index";
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public String loginOut(HttpSession session) {
        //从session中删除user属性，用户退出登录
        session.removeAttribute("user");
        return "index";
    }
}