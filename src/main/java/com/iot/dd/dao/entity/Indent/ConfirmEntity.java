package com.iot.dd.dao.entity.Indent;

import java.util.Date;

/**
 * Created by admin on 2017/8/28.
 */
public class ConfirmEntity {

    String orderId;
    String technicianId;
    String licensePlateNumberImg;
    String batteryImg;
    String qualityAssuranceImg;
    Date time;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getLicensePlateNumberImg() {
        return licensePlateNumberImg;
    }

    public void setLicensePlateNumberImg(String licensePlateNumberImg) {
        this.licensePlateNumberImg = licensePlateNumberImg;
    }

    public String getBatteryImg() {
        return batteryImg;
    }

    public void setBatteryImg(String batteryImg) {
        this.batteryImg = batteryImg;
    }

    public String getQualityAssuranceImg() {
        return qualityAssuranceImg;
    }

    public void setQualityAssuranceImg(String qualityAssuranceImg) {
        this.qualityAssuranceImg = qualityAssuranceImg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
