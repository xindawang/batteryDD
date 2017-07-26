package com.iot.dd.dao.entity.resource;

/**
 * Created by huanglin on 2017/7/20.
 */
public class AreaEntity {
    private Integer id;//序号
    private String areaCode;//县/区编号
    private String areaName;//名称
    private String cityCode;//城市编号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
