package com.iot.dd.dao.entity.resource;

/**
 * Created by admin on 2017/9/25.
 */
public class AutomobileTypeBatteryEntity {
    private int id;
    private int automobileTypeId;
    private int batteryTypeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAutomobileTypeId() {
        return automobileTypeId;
    }

    public void setAutomobileTypeId(int automobileTypeId) {
        this.automobileTypeId = automobileTypeId;
    }

    public int getBatteryTypeId() {
        return batteryTypeId;
    }

    public void setBatteryTypeId(int batteryTypeId) {
        this.batteryTypeId = batteryTypeId;
    }
}
