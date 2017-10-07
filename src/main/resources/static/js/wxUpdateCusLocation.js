/**
 * Created by huanglin on 2017/8/21.
 */
document.write('<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>')
function UpdateCusLocation(orderId) {


    var targetUrl = location.href.split("#")[0]
    targetUrl = encodeURIComponent(encodeURIComponent(targetUrl))


    $.ajax({
        type: "POST",
        url: "/getWXJsMsg",
        data: {"targetUrl": targetUrl},
        dataType: "json",
        success: function (msg) {
            wx.config({
                debug: false,
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
                    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                    success: function (res) {
                        var ulatitude;
                        var ulongitude;

                        ulatitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                        ulongitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。

                        $.ajax({
                            type: "POST",
                            url: "/setCustomerLocation",
                            data: {
                                "latitude": ulatitude,
                                "longitude": ulongitude,
                                "orderId": orderId
                            },
                            dataType: "json",
                            success: function (data) {
                                console.log(data.toString())
                            }

                        })

                    },
                    error: function (err) {
                        alert(err)
                    }
                });
            });

        }
    })

    function wxUpdateConnect(indentId,longitude,latitude) {
        var socket = new SockJS('/endpointDCDD');
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function () {
            console.log('开始连接')

//2、接收数据
            stompClient.subscribe('/topic/dis_tech'+techId, function (ex) {
                console.log(JSON.parse(ex.body).orderId)
            });

            stompClient.subscribe('/topic/dis_res'+indentId, function (ex) {//订单派发收到回复

                window.confirm(ex.body)
                map.remove(userMarkers)
                map.remove(techMarkers)
                selectCityList();//重新导入存在未派发订单的城市，和订单以及技师

                $("#indentDetail").hide()//隐藏订单详情按钮
                $("#indentDetailShow").hide();//隐藏订单详情内容
            });

            var msg=JSON.stringify({'technicianId':techId,'orderId':indentId})
            stompClient.send("/app/distributeIndent",{},msg)






        })
    }

}