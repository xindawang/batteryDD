package com.iot.dd.dao.entity.Indent;

import com.iot.dd.dao.entity.worker.TechnicianEntity;

import java.sql.Date;

/**
 * Created by huanglin on 2017/7/21.
 */
//订单转发实体类
public class IndentAllocationEntity {
    private Integer id;
    private String orderId;
    private String technicianId;
    private Date acceptTime;
    private Float technicianLongitude;
    private Float technicianLatitude;
    private Float customerLongitude;
    private Float customerLatitude;
    private TechnicianEntity technician;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String  getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String  getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String  technicianId) {
        this.technicianId = technicianId;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Float getTechnicianLongitude() {
        return technicianLongitude;
    }

    public void setTechnicianLongitude(Float technicianLongitude) {
        this.technicianLongitude = technicianLongitude;
    }

    public Float getTechnicianLatitude() {
        return technicianLatitude;
    }

    public void setTechnicianLatitude(Float technicianLatitude) {
        this.technicianLatitude = technicianLatitude;
    }

    public Float getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(Float customerLongitude) {
        this.customerLongitude = customerLongitude;
    }

    public Float getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(Float customerLatitude) {
        this.customerLatitude = customerLatitude;
    }

    public TechnicianEntity getTechnician() {
        return technician;
    }

    public void setTechnician(TechnicianEntity technician) {
        this.technician = technician;
    }
}
