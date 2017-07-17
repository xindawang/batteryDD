package com.wx.hl.domain;

/**
 * Created by huanglin on 2017/7/15.
 */
/**
* @Author: huanglin
* @Description:子菜单项 :没有子菜单的菜单项，有可能是二级菜单项，
 * 也有可能是不含二级菜单的一级菜单。
* @Date:12:33 2017/7/15
* @params:  * @param null
*/
public class CommonButton extends Buttton{
    private String type;
    private String key;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
