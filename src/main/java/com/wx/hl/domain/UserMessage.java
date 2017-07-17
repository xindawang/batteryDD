package com.wx.hl.domain;

/**
 * Created by huanglin on 2017/7/13.
 */

/**
 * Created by huanglin on 2017/7/13.
 */
public class UserMessage {
    private int id;
    private String name;
    private String password;
    private float latitude;//经度
    private float longitude;//纬度

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
