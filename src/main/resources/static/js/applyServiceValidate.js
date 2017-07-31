/**
 * Created by huanglin on 2017/7/27.
 */
var targetUrl = location.href.split("#")[0]
targetUrl = encodeURIComponent(encodeURIComponent(targetUrl))

$(function () {
    $("#main-btn").click(function () {

        $.ajax({
            type: "POST",
            url: "/applyServiceValidate",
            data: {"telephone": $("#main-phone").val(), "targetUrl": targetUrl},
            dataType: "json",
            success: function (data) {
                console.log(Object.prototype.toString.call(data))
                if(Object.prototype.toString.call(data)=='[object String]'){
                    confirm(data.toString())
                }else {
                    wx.config({
                        debug: true,
                        appId: data.appId + '',
                        timestamp: data.timestamp + '',
                        nonceStr: data.nonceStr + '',
                        signature: data.signature + '',
                        jsApiList: ['openLocation', 'getLocation', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone']
                    });
                    wx.error(function (res) {
                        alert(JSON.stringify(res));//返回errMsg.
                    });

                    wx.ready(function () {

                        var latitude;
                        var longitude;
                        var speed;
                        var accuracy;

                        wx.getLocation({
                            type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                            success: function (res) {
                                latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                                longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                                speed = res.speed; // 速度，以米/每秒计
                                accuracy = res.accuracy; // 位置精度

                                wx.openLocation({
                                    latitude: latitude, // 纬度，浮点数，范围为90 ~ -90
                                    longitude: longitude, // 经度，浮点数，范围为180 ~ -180。
                                    name: '华科', // 位置名
                                    address: '', // 地址详情说明
                                    scale: 26, // 地图缩放级别,整形值,范围从1~28。默认为最大
                                    infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
                                });
                            }

                        });

                    });

                }

            }
        })

    })

});
