package com.wx.hl.domain;

import java.awt.*;

/**
 * Created by huanglin on 2017/7/15.
 * 整个菜单对象的封装
 */
public class Menu {
    private ComplexButton[] button;

    public ComplexButton[] getButton() {
        return button;
    }

    public void setButton(ComplexButton[] button) {
        this.button = button;
    }
}
