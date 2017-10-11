package com.iot.dd.dao.entity.Indent;

import java.sql.Date;

/**
 * Created by huanglin on 2017/7/21.
 */
//订单实体类
public class IndentShowEntity {

    private String orderId;
    private String batteryType;//电池型号
    private String customerName;
    private String customerCellphone;
    private String address;
    private String automobileType;//车型
    private String licensePlateNumber;
    private String technicianName;
    private String technicianPhone;
    private Date createTime;
    private Date finishTime;
    private String state;
    private String remark;



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCellphone() {
        return customerCellphone;
    }

    public void setCustomerCellphone(String customerCellphone) {
        this.customerCellphone = customerCellphone;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAutomobileType() {
        return automobileType;
    }

    public void setAutomobileType(String automobileType) {
        this.automobileType = automobileType;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getTechnicianPhone() {
        return technicianPhone;
    }

    public void setTechnicianPhone(String technicianPhone) {
        this.technicianPhone = technicianPhone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
