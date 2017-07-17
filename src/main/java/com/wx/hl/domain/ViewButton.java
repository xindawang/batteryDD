package com.wx.hl.domain;



/**
 * Created by huanglin on 2017/7/16.
 */
public class ViewButton extends Buttton{
    private String type;		//菜单类型
    private String url;			//view路径值

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
