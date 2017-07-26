package com.iot.dd.Controller;

import com.iot.dd.Tools.JsonTool;
import com.iot.dd.domain.IndentStatesEntity;
import com.iot.dd.service.indentStatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by admin on 2017/7/26.
 */
@RestController
public class indentStatesController {
    @Autowired
    private indentStatesService statesService;



    @RequestMapping(value="/indentState",method = RequestMethod.POST)
    public String findStates() {

        List<IndentStatesEntity> list = statesService.findState();

        return JsonTool.listToJson(list);

    }


}
