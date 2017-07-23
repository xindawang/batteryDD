package com.iot.dd.domain.resource;

/**
 * Created by huanglin on 2017/7/22.
 */
public class BatteryEntity {
    private Integer id;
    private String type;
    private String warrantyPeriod;
    private Integer batteryBrandId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public Integer getBatteryBrandId() {
        return batteryBrandId;
    }

    public void setBatteryBrandId(Integer batteryBrandId) {
        this.batteryBrandId = batteryBrandId;
    }
}
