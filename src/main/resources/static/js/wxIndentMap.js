/**
 * Created by huanglin on 2017/8/1.
 */


var strMsg = window.location.search.substring(1)
var orderId;

var param = strMsg.split('=')
if (param[0] == 'orderId') orderId = param[1]

//定义技师的经纬度和用户的经纬度为全局变量。
var userLatitude
var userLongitude

var techLatitude
var techLongitude




var Maps=[]
$(function () {
    //connect();
    var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [116.397428, 39.90923],//地图中心点
        zoom: 13 //地图显示的缩放级别
    });
    UpdateCusLocation(orderId)
    getLocation()
    getTechMsg()


    window.setInterval(function(){
        UpdateCusLocation(orderId);
        getLocation()


    },
        10000)


function setDriving(uLongitude,uLatitude,tLongitude,tLatitude) {

    //
    //map.remove(Maps)
    map.clearMap()
    //构造路线导航类
    var driving = new AMap.Driving({
        map: map

    });
    // 根据起终点名称规划驾车导航路线
    driving.search(
        [tLongitude, tLatitude],
        [uLongitude, uLatitude]
    );
    Maps.push(driving)


}



//    ------------websocket----------------------
    function connect() {
        var socket = new SockJS('/endpointDCDD');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function () {
            console.log('开始连接')

            stompClient.subscribe('/topic/sendLocation', function (ex) {
                console.log(ex)
                console.log('ok?')
            });
        })
    }

    function sendCusLocation(longitude,latiutde) {
        stompClient.send("/setCustomerLocation",{},JSON.stringify({'customerLongitude':longitude,'customerLatitude':latiutde}))
    }


    function getLocation() {
        $.ajax({
            url:"/getAllLocation",
            type:"POST",
            data:{"orderId":orderId},
            dataType:"json",
            async:true,
            success:function (data) {
                techLongitude=data.technicianLongitude
                techLatitude=data.technicianLatitude
                userLongitude=data.customerLongitude
                userLatitude=data.customerLatitude
                //window.confirm(techLatitude+techLongitude+userLatitude+userLongitude)
                setDriving(userLongitude,userLatitude,techLongitude,techLatitude)
            }

        })
    }

    function getTechMsg() {
        $.ajax({
            url:"/getTechMsg",
            type:"POST",
            data:{"orderId":orderId},
            dataType:"json",
            success:function (data) {
                $("#name").text("姓名:"+data.name)
                $("#sex").text("性别:"+data.sex)
                $("#techId").text("编号:"+data.technicianId)
                $("#lincePlaNum").text("车牌号:"+data.licensePlateNumber)
                $("#phoneNumber").text("电话:"+data.cellphone)
                $("#cellToTech").attr("href","tel:"+data.cellphone)

            }
        })
    }


})
