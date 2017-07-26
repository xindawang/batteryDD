package com.iot.dd.dao.entity.Indent;

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
    private float technicianLongitude;
    private float technicianLatitude;
    private float customerLongitude;
    private float customerLatitude;

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

    public float getTechnicianLongitude() {
        return technicianLongitude;
    }

    public void setTechnicianLongitude(float technicianLongitude) {
        this.technicianLongitude = technicianLongitude;
    }

    public float getTechnicianLatitude() {
        return technicianLatitude;
    }

    public void setTechnicianLatitude(float technicianLatitude) {
        this.technicianLatitude = technicianLatitude;
    }

    public float getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(float customerLongitude) {
        this.customerLongitude = customerLongitude;
    }

    public float getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(float customerLatitude) {
        this.customerLatitude = customerLatitude;
    }
}
