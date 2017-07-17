package com.wx.hl.domain;

/**
 * Created by huanglin on 2017/7/14.
 */
/**
* @Author: huanglin
* @Description: 微信通用接口凭证
 * @Date:12:38 2017/7/15
* @params:  * @param null
*/
public class AccessToken {
    private String token;
    private int expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
