/**
 * Created by huanglin on 2017/8/1.
 */
var targetUrl = location.href.split("#")[0]
targetUrl = encodeURIComponent(encodeURIComponent(targetUrl))

var strMsg = window.location.search.substring(1)
var userTelephone;

var param = strMsg.split('=')
if (param[0] == 'telephone') userTelephone = param[1]
console.log(userTelephone)
//定义技师的经纬度和用户的经纬度为全局变量。
var userLatitude;
var userLongitude;

var techLatitude;
var techLongitude;
$(function () {
        $.ajax({
            type: "POST",
            url: "/getLocationMsg",
            data: {"targetUrl": targetUrl},
            dataType: "json",
            success: function (msg) {
                wx.config({
                    debug: true,
                    appId: msg.appId + '',
                    timestamp: msg.timestamp + '',
                    nonceStr: msg.nonceStr + '',
                    signature: msg.signature + '',
                    jsApiList: ['openLocation', 'getLocation', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone']
                });
                wx.error(function (res) {
                    alert(JSON.stringify(res));//返回errMsg.
                });

                wx.ready(function () {

                    wx.getLocation({
                        type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                        success: function (res) {
                            userLatitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                            userLongitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                            speed = res.speed; // 速度，以米/每秒计
                            accuracy = res.accuracy; // 位置精度

                            $.ajax({
                                type: "POST",
                                url: "/setCustomerLocation",
                                data: {"latitude": userLatitude, "longitude": userLongitude, "userTelephone": userTelephone},
                                dataType: "json",
                                success: function (data){
                                    console.log(data.toString())
                                }

                            })

                        },
                        error: function(err) {
                                alert(err)
                        }

                    });


                });


            }
        })


    //window.setInterval(getUserLocation, 10000)

    var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [116.397428, 39.90923],//地图中心点
        zoom: 13 //地图显示的缩放级别
    });
    //构造路线导航类
    var driving = new AMap.Driving({
        map: map
        //panel: "panel"
    });
    // 根据起终点名称规划驾车导航路线
    driving.search(
        new AMap.LngLat(116.379028, 39.865042),
        new AMap.LngLat(116.427281, 39.903719));


})
