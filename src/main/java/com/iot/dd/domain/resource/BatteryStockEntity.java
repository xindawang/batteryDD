package com.iot.dd.domain.resource;

/**
 * Created by admin on 2017/7/24.
 */
public class BatteryStockEntity {
    private Integer id;
    private  Integer batteryId;
    private  String cityCode;
    private Integer inventory;

    public Integer getBatteryId() {
        return batteryId;
    }

    public void setBatteryId(Integer batteryId) {
        this.batteryId = batteryId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }
}
