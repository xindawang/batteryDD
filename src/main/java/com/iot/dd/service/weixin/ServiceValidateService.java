package com.iot.dd.service.weixin;

import com.iot.dd.dao.entity.Indent.OrderEntity;
import com.iot.dd.dao.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huanglin on 2017/7/27.
 */
@Service
public class ServiceValidateService {
    @Autowired
    OrderMapper orderMapper;



    public Map<String,Object> WeixingetLocation( HttpServletRequest request) throws UnsupportedEncodingException {

        Map<String,Object> ret = new HashMap<String,Object>();
        ret= WeixinInitService.getWxConfig(request);

        return ret;
    }

    public  List<OrderEntity> getIndentByTelephone(String telephone) {
        List<OrderEntity> Indent = orderMapper.selectIndentByPhone(telephone);
        if (!Indent.isEmpty()) {//取到数据
            String status = Indent.get(0).getStatus();
            if (status.equals("已录入")) {
                return Indent;
            }

        }
        return null;
    }
}
