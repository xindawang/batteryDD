package com.iot.dd.dao.entity.Indent;

/**
 * Created with IntelliJ IDEA.
 * User: huanglin
 * Date: 2017/9/20
 * Time: 23:05
 * To change this template use File | Settings | File Templates.
 */
public class IndentMonitorEntity {

    private String orderId;

    private Float customerLongitude;
    private Float customerLatitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public Float getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(Float customerLongitude) {
        this.customerLongitude = customerLongitude;
    }

    public Float getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(Float customerLatitude) {
        this.customerLatitude = customerLatitude;
    }
}
