package com.wx.hl.Controller;

import com.wx.hl.domain.AccessToken;
import com.wx.hl.domain.WeixinUserBaseMessage;
import com.wx.hl.service.WeixinMessageUtil;
import com.wx.hl.service.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wx.hl.service.MenuUtil;
/**
 * Created by huanglin on 2017/7/14.
 */
public class Test {
    private static Logger log= LoggerFactory.getLogger(Test.class);
    public static void main(String[] args) {
        String accessToken=WeixinUtil.getAccessToken().getToken();
        WeixinUserBaseMessage user = WeixinMessageUtil.getUserInfo(accessToken, "oicGh1WoX9rSs9wNv9MzfxIXkJ4A");
        System.out.println("OpenID：" + user.getOpenId());
        System.out.println("关注状态：" + user.getSubscribe());
        System.out.println("关注时间：" + user.getSubscribeTime());
        System.out.println("昵称：" + user.getNickname());
        System.out.println("性别：" + user.getSex());
        System.out.println("国家：" + user.getCountry());
        System.out.println("省份：" + user.getProvince());
        System.out.println("城市：" + user.getCity());
        System.out.println("语言：" + user.getLanguage());
        System.out.println("头像：" + user.getHeadImgUrl());

    }
}
