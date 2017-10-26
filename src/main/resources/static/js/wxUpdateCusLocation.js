/**
 * Created by huanglin on 2017/8/21.
 */
document.write('<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>')





function getLocationAjax(orderId) {//通过ajax来更新地理位置
    var ulatitude;
    var ulongitude;
    wx.getLocation({
        type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
        success: function (res) {

            ulatitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
            ulongitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。


            updateLocationA(orderId,ulongitude,ulatitude)

        },
        error: function (err) {
            alert(err)
        }
    });



}

function updateLocationA(orderId,longitude,latitude) {
    $.ajax({
        type: "POST",
        url: "/setCustomerLocation",
        data: {
            "latitude": latitude,
            "longitude": longitude,
            "orderId": orderId
        },
        dataType: "json",
        success: function (data) {
            console.log(data.toString())
        }

    })
}