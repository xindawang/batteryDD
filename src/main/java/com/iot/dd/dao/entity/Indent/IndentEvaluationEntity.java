package com.iot.dd.dao.entity.Indent;

/**
 * Created by admin on 2017/9/18.
 */
public class IndentEvaluationEntity {
    int id;
    String orderId;
    String technicianId;
    String time;
    String technicianService;
    String technicianSpeed;
    String technicianQuality;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTechnicianService() {
        return technicianService;
    }

    public void setTechnicianService(String technicianService) {
        this.technicianService = technicianService;
    }

    public String getTechnicianSpeed() {
        return technicianSpeed;
    }

    public void setTechnicianSpeed(String technicianSpeed) {
        this.technicianSpeed = technicianSpeed;
    }

    public String getTechnicianQuality() {
        return technicianQuality;
    }

    public void setTechnicianQuality(String technicianQuality) {
        this.technicianQuality = technicianQuality;
    }
}
