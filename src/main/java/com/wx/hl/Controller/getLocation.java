package com.wx.hl.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by huanglin on 2017/7/12.
 */
@Controller
public class getLocation {

    @RequestMapping("/location")
    public String  LocationMessage(Model model )
    {

        model.addAttribute("longitude",WeixinServlet.longitude_1);
        model.addAttribute("latitude",WeixinServlet.latitude_1);
        return "location";
    }
}
