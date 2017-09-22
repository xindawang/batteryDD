package com.iot.dd.controller;

import com.iot.dd.dao.entity.worker.LastIdEntity;
import com.iot.dd.service.LastIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2017/9/20.
 */
@RestController
public class technicianLastIdController {
    @Autowired
    private LastIdService lastIdService;


    @RequestMapping(value="/findLastId",method = RequestMethod.GET)
    public String findLastId(HttpServletResponse response, HttpServletRequest request){
        LastIdEntity entity=lastIdService.find();//技师可用编号
//        int id=Integer.parseInt(entity.getIdNumber())+1;
//        lastIdService.update(id+"");//设置下一个可用编号
        String ss=entity.getIdPre()+entity.getIdNumber();
        return ss;
    }
}
