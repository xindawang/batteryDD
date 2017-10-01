package com.iot.dd.dao.entity.resource;

/**
 * Created by huanglin on 2017/7/22.
 */
public class AutomobileType {
    private Integer id;
    private String name;
    private Integer automobileBrandId;
    private AutomobileBrand autoBrand;
    private String batteryType;
//    private Integer batteryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAutomobileBrandId() {
        return automobileBrandId;
    }

    public void setAutomobileBrandId(Integer automobileBrandId) {
        this.automobileBrandId = automobileBrandId;
    }

    public AutomobileBrand getAutoBrand() {
        return autoBrand;
    }

    public void setAutoBrand(AutomobileBrand autoBrand) {
        this.autoBrand = autoBrand;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    //    public Integer getBatteryId() {
//        return batteryId;
//    }
//
//    public void setBatteryId(Integer batteryId) {
//        this.batteryId = batteryId;
//    }
}
