package com.wx.hl.domain;

import java.awt.*;

/**
 * Created by huanglin on 2017/7/15.
 */
/**
* @Author: huanglin
* @Description:父菜单项 :包含有二级菜单项的一级菜单。这类菜单项包含有二个属性
 * ：name和sub_button，而sub_button以是一个子菜单项数组
* @Date:12:35 2017/7/15
* @params:  * @param null
*/
public class ComplexButton {
    private Buttton[] sub_button;
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Buttton[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Buttton[] sub_button) {
        this.sub_button = sub_button;
    }
}
