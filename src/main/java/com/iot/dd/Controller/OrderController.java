package com.iot.dd.Controller;

import com.iot.dd.Dao.OrderDao;
import com.iot.dd.Dao.ResourceDao;
import com.iot.dd.Tools.TimeTool;
import com.iot.dd.domain.OrderEntity;
import com.iot.dd.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.ParseException;

import static com.iot.dd.Tools.TimeTool.stringToSqlDate;

/**
 * Created by huanglin on 2017/7/21.
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ResourceDao resourceDao;

    @RequestMapping(value="/importOrder", method= RequestMethod.POST)
    private String importOrder(HttpServletRequest request){

        String result=null;

        OrderEntity orderEntity=new OrderEntity();
        Integer batteryTypeId=Integer.valueOf(request.getParameter("batteryType"));
        String BatteryTypeName=resourceDao.selectBatteryTypeName(batteryTypeId);
        orderEntity.setBatteryType(BatteryTypeName);

        orderEntity.setCustomerName(request.getParameter("customerName"));
        orderEntity.setCustomerCellphone(request.getParameter("cellphone"));
        orderEntity.setCustomerTelephone(request.getParameter("telephone"));

        orderEntity.setWechatId(request.getParameter("wechatId"));

        String address=resourceDao.selectProvinceName(request.getParameter("province"))+
                resourceDao.selectCityName(request.getParameter("city"))+
                resourceDao.selectAreaName(request.getParameter("area"))+
                request.getParameter("detailAddress");
        orderEntity.setAddress(address);

        Integer automobileBrandId=Integer.valueOf(request.getParameter("automobileBrand"));
        String autoBrandName=resourceDao.selectAutoBrandName(automobileBrandId);
        Integer automobileTypeId=Integer.valueOf(request.getParameter("automobileType"));
        String autoTypeName=resourceDao.selectAutoTypeName(automobileTypeId);
        String automobileType=autoBrandName+autoTypeName;
        orderEntity.setAutomobileType(automobileType);
        orderEntity.setLicensePlateNumber(request.getParameter("licensePlateNumber"));
        orderEntity.setCityCode(request.getParameter("city"));

        Date createTime=null;
        try {
            createTime=TimeTool.stringToSqlDate(request.getParameter("createTime"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        orderEntity.setCreateTime(createTime);

        String status=resourceDao.selectId(1);//状态：已录入
        orderEntity.setStatus(status);
        orderEntity.setRemark(request.getParameter("remark"));

        result=orderService.importOrder(orderEntity);

        return result;

    }

}