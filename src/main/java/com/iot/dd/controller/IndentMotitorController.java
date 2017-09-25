package com.iot.dd.controller;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.Indent.IndentMonitorEntity;
import com.iot.dd.service.IndentMonitorService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: huanglin
 * Date: 2017/9/20
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class IndentMotitorController {

    @Autowired
    private IndentMonitorService indentMonitorService;

    @RequestMapping(value = "/getIndentMonitorMsg", method = RequestMethod.POST)
    String getIndentLocation(HttpServletRequest request) {
        List<IndentMonitorEntity> result = new ArrayList<>();
        String type = request.getParameter("type");
        result = indentMonitorService.getAllIndentLocation(type);

        for (IndentMonitorEntity indentMonitor : result) {
            if (indentMonitor.getCustomerLatitude() == null || indentMonitor.getCustomerLongitude() == null) {
                String location = IndentMonitorService.getLocationByAddress(indentMonitor.getAddress());
                Float longitude = Float.parseFloat(location.split(",")[0]);
                Float latitude = Float.parseFloat(location.split(",")[1]);
                indentMonitor.setCustomerLongitude(longitude);
                indentMonitor.setCustomerLatitude(latitude);
            }
        }

        return JsonTool.objectToJson(result);

    }

}
