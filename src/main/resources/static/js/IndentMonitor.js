$(function () {



    //创建地图
    var map = new AMap.Map('container', {
        zoom: 4,
        resizeEnable: true
    });

    AMapUI.loadUI(['geo/DistrictCluster'], function (DistrictCluster) {

        //启动页面
        initPage(DistrictCluster);
    });
    //geocoder()

    $("#refresh").click(function () {
        document.location.reload()
    })


    function initPage(DistrictCluster) {

        var distCluster = new DistrictCluster({
            map: map, //所属的地图实例
            //返回数据项中的经纬度位置
            getPosition: function (item) {
                return item.position;
            }
        });

        //随机创建一批点，仅作示意
        var data = createDatas();
        //设置数据
        distCluster.setData(data);
    }


    //随机生产点
    function createDatas() {
        var locationData = [];
        $.ajax({
            url: "/getIndentMonitorMsg",
            type: "POST",
            data: {"type": "all"},
            async: false,
            dataType: "json",
            success: function (data) {

                if (data != null) {
                    for (var i in data) {
                        locationData.push({
                            position: [
                                data[i].customerLongitude,
                                data[i].customerLatitude
                            ]

                        });

                    }
                }
            }
        })


        return locationData;
    }

    function geocoder() {
        var geocoder = new AMap.Geocoder({
            city: "010", //城市，默认：“全国”
            radius: 1000 //范围，默认：500
        });
        //地理编码,返回地理编码结果
        geocoder.getLocation("420100", function (status, result) {
            if (status === 'complete' && result.info === 'OK') {
                geocoder_CallBack(result);
            }
        });
    }

    function addMarker(i, d) {
        var marker = new AMap.Marker({
            map: map,
            position: [d.location.getLng(), d.location.getLat()]
        });
        var infoWindow = new AMap.InfoWindow({
            content: d.formattedAddress,
            offset: {x: 0, y: -30}
        });
        marker.on("mouseover", function (e) {
            infoWindow.open(map, marker.getPosition());
        });
    }

    //地理编码返回结果展示
    function geocoder_CallBack(data) {
        var resultStr = "";
        //地理编码结果数组
        var geocode = data.geocodes;
        for (var i = 0; i < geocode.length; i++) {
            //拼接输出html
            //resultStr += "<span style=\"font-size: 12px;padding:0px 0 4px 2px; border-bottom:1px solid #C1FFC1;\">" + "<b>地址</b>：" + geocode[i].formattedAddress + "" + "&nbsp;&nbsp;<b>的地理编码结果是:</b><b>&nbsp;&nbsp;&nbsp;&nbsp;坐标</b>：" + geocode[i].location.getLng() + ", " + geocode[i].location.getLat() + "" + "<b>&nbsp;&nbsp;&nbsp;&nbsp;匹配级别</b>：" + geocode[i].level + "</span>";
            //addMarker(i, geocode[i]);
            console.log(geocode[i].location.getLng())
            console.log(geocode[i].location.getLat())
        }
        //map.setFitView();
        //document.getElementById("result").innerHTML = resultStr;
    }
})