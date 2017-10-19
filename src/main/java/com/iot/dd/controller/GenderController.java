package com.iot.dd.controller;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.dao.entity.resource.SexEntity;
import com.iot.dd.service.SexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by admin on 2017/7/25.
 */
@RestController
public class GenderController {
    @Autowired
    private SexService  sexservice;

    @RequestMapping(value="/sexFind",method = RequestMethod.POST)
    public String  findSex(){

        List<SexEntity>list=sexservice.find();
        return JsonTool.objectToJson(list);
    }

}
