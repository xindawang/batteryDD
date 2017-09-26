package com.iot.dd.dao.entity.resource;

/**
 * Created with IntelliJ IDEA.
 * User: huanglin
 * Date: 2017/9/25
 * Time: 12:16
 * To change this template use File | Settings | File Templates.
 */
public class BatteryTypeEntity {
    private String type;
    private String id;
    private String brandName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
