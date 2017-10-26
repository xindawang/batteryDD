/**
 * Created by huanglin on 2017/8/1.
 */



var orderId

var strMsg = window.location.search.substring(1)
var param = strMsg.split("&")
for (var node in param) {

    var nodeDn = param[node].split("=")
    if (nodeDn[0] === 'orderId')
        orderId = nodeDn[1]

}
//定义技师的经纬度和用户的经纬度为全局变量。
var userLatitude
var userLongitude

var techLatitude
var techLongitude
var stompClient
var map
var Maps = []
$(function () {

    configVerify(orderId)//通过微信js调用接口的验证
    websocketMapInit(orderId) //websocket初始化
     map = new AMap.Map("container", {
        resizeEnable: true,
        center: [116.397428, 39.90923],//地图中心点
        zoom: 13 //地图显示的缩放级别
    });



    getAllLocation(orderId)  //先从后台获取一次地理位置进行更新
    getTechMsg(orderId)


    window.setInterval(function () {

             getGPSLocation(orderId)

        },
        10000)

    window.setInterval(function () {
        setDriving(userLongitude, userLatitude, techLongitude, techLatitude)
    },30*1000)



})


    function setDriving(uLongitude, uLatitude, tLongitude, tLatitude) {

        //
        //map.remove(Maps)
        map.clearMap()
        //构造路线导航类
        var driving = new AMap.Driving({
            map: map

        });
        // 根据起终点名称规划驾车导航路线
        if(tLongitude ==null || tLatitude==null){
            map.setCenter([uLongitude,uLatitude]);
        }else{
            driving.search(
                [tLongitude, tLatitude],
                [uLongitude, uLatitude]
            );
        }


        Maps.push(driving)


    }






    function getAllLocation(orderId) {
        $.ajax({
            url: "/getAllLocation",
            type: "POST",
            data: {"orderId": orderId},
            dataType: "json",
            async: true,
            success: function (data) {
                techLongitude = data.technicianLongitude
                techLatitude = data.technicianLatitude
                userLongitude = data.customerLongitude
                userLatitude = data.customerLatitude
                //window.confirm(techLatitude+techLongitude+userLatitude+userLongitude)
                setDriving(userLongitude, userLatitude, techLongitude, techLatitude)
            }

        })
    }

    function getTechMsg(orderId) {
        $.ajax({
            url: "/getTechMsg",
            type: "POST",
            data: {"orderId": orderId},
            dataType: "json",
            success: function (data) {
                $("#name").text("姓名:" + data.name)
                $("#sex").text("性别:" + data.sex)
                $("#techId").text("编号:" + data.technicianId)
                $("#lincePlaNum").text("车牌号:" + data.licensePlateNumber)
                $("#phoneNumber").text("电话:" + data.cellphone)
                $("#cellToTech").attr("href", "tel:" + data.cellphone)

            }
        })
    }

//    ------------websocket----------------------
    function websocketMapInit(indentId) {
        var socket = new SockJS('/endpointDCDD');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function () {
           // window.confirm('开始连接')

            stompClient.subscribe('/topic/tech_location' + indentId, function (ex) {//订单派发收到回复

                techLongitude=JSON.parse(ex.body).longitude
                techLatitude=JSON.parse(ex.body).latitude
                //confirm("tech:"+techLongitude+techLatitude+" user:"+userLongitude+userLatitude)
                setDriving(userLongitude, userLatitude, techLongitude, techLatitude)


            });
        })


    }







