package com.iot.dd.dao.entity.resource;

/**
 * Created by admin on 2017/9/26.
 */
public class autoDetailEntity {
    private int id;
    private String automobileBrand;
    private String automobileType;
    private String batteryType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutomobileBrand() {
        return automobileBrand;
    }

    public void setAutomobileBrand(String automobileBrand) {
        this.automobileBrand = automobileBrand;
    }

    public String getAutomobileType() {
        return automobileType;
    }

    public void setAutomobileType(String automobileType) {
        this.automobileType = automobileType;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }
}
