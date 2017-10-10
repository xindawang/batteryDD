/**
 * Created by huanglin on 2017/7/24.
 */

var userMarkers = [] //保存marker  地图点标记
var disIndentId
var map
$(function () {

     map = new AMap.Map("container", { //创建地图
        resizeEnable: true
        // center: [116.397428, 39.90923], //地图中心
        // zoom: 5
    });

    websocketInit()//建立websocket连接

    selectCityList()//同步

    //-------建立所有控件的监控----------------

    $("#city").change(function () {
        var cityCode = $("#city option:selected").val()
        var cityName = $("#city option:selected").text()
        selectIndentMsgByCityCode(cityCode)

        map.setCity(cityName);
        map.remove(userMarkers)
        importTechMsgFromCity(cityCode)
    })


    $("#undoneIndent").change(function () {

        disIndentId = $("#undoneIndent option:selected").val()
        disIndentText=$("#undoneIndent option:selected").text()

        if(disIndentId ==="0"){//当订单是---请选择时---
            $("#indentDetail").hide()
        }else if(disIndentText.lastIndexOf("非微信申请")>0) {
            $("#indentDetail").show()
            setCusMsg(disIndentId)

        }else{
            $("#indentDetail").show()
            setCusMsg(disIndentId)
            setCusLocation(disIndentId)
        }
    })


    $("#technician").change(function () {
        var selectedTechMsg = $("#technician option:selected").text()
        var selectedTechId = $("#technician option:selected").val()
        confirmTech(selectedTechMsg, selectedTechId, disIndentId)

    })

    //点击‘订单信息’，则显示信息
    $("#DetailShow").click(function () {
        $("#indentDetailShow").show();
    });
    $("#cancel").click(function () {
        $("#indentDetailShow").hide();
    });


    //-------建立所有控件的监控----------------






})
//改变技师时的操作--也就是要派发的操作
function confirmTech(techMsg,techId,indentId) {

    if (techId != 0  && indentId !=0) {
        $.mbox({
            area: ["450px", "auto"], //弹框大小
            border: [0, .5, "#666"],
            title: "订单派发确认",
            dialog: {
                msg: "订单编号：" + indentId + '<br/>' + '派发给' + '<br/>' + techMsg,
                btns: 2,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
                type: 2,   //1:对钩   2：问号  3：叹号
                btn: ["确认", "取消"],  //自定义按钮
                yes: function () {  //点击左侧按钮：成功

                    //更新未派发订单下拉框，并且清空技师下拉框
                    //confirmIndent(techId, indentId)
                    connect(techId,indentId)

                },
                no: function () { //点击右侧按钮：失败
                    return false;
                }
            }
        })
    }
}

//确认将订单派发给技师的操作
function confirmIndent(techId, indentId) {
    $.ajax({
        url: "/allocationIndent",
        type: "POST",
        data: {"techId": techId, "indentId": indentId},
        dataType: "json",
        success: function (data) {
            window.confirm(data)
            map.remove(userMarkers)
            map.remove(techMarkers)
            selectCityList();//重新导入存在未派发订单的城市，和订单以及技师

            $("#indentDetail").hide()//隐藏订单详情按钮
            $("#indentDetailShow").hide();//隐藏订单详情内容
        }
    })


    //
    //map.setZoom(5)
    //map.setCenter([116.397428, 39.90923])//依然更新地图到最初位置和放大程度

}
//通过cityCode查询未派发订单
function selectIndentMsgByCityCode(cityCode) {

    $.ajax({
        url: "/getIndentIdByCityCode",
        type: "POST",
        data: {"cityCode": cityCode},
        dataType: "json",
        success: function (data) {
            $("#undoneIndent").empty()
            //document.getElementById("undoneIndent").options[0] = new Option("---请选择---", 0);
            $("#undoneIndent").append("<option value='0'>---请选择---</option>")
            for (var i = 0; i < data.length; i++) {
                if(data[i].wechatStatus == 0) {//不从微信申请服务
                    $("#undoneIndent").append("<option value=" + data[i].orderId+">" + data[i].orderId+"(非微信申请)" + "</option>")
                }else{
                    $("#undoneIndent").append("<option value=" + data[i].orderId+">" + data[i].orderId+ "</option>")
                }

                //使用 $("#undoneIndent")会报错
                //document.getElementById("undoneIndent").options[ii] = new Option(data[i].orderId, data[i].orderId);

            }


        }
    })
}

function websocketInit(){
    var socket = new SockJS('/endpointDCDD');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        console.log('开始连接')
    })


}

function connect(techId,indentId) {

//2、接收数据
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

    // if(stompClient !=null) {
    //     stompClient.disconnect()
    //     if (stompClient != null) {
    //         console.log("连接成功3")
    //     }
    // }

}


//在网页加载或者派发完一个订单时对有订单的城市进行更新，并且更新位置
function selectCityList() {
    $("#city").empty()
    $.ajax({
        url: "/getHaveIndentOfCity",
        type: "POST",
        data: {"status": "1", "type": "distribute"},
        asyns: false,
        dataType: "json",
        success: function (data) {

            for (var i = 0; i < data.length; i++) {
                //使用 $("#undoneIndent")会报错
                //document.getElementById("city").options[i] = new Option(data[i].cityName, data[i].cityCode);
                $("#city").append("<option value=" + data[i].cityCode + ">" + data[i].cityName + "</option>")
            }
            var cityName = $("#city option:selected").text()
            if (!cityName) {
                cityName = '北京市';
            }
            // map.setZoom(20)
            map.setCity(cityName);

            var cityCode = $("#city option:selected").val()
            selectIndentMsgByCityCode(cityCode)
            importTechMsgFromCity(cityCode)

        }
    })
}

    //当选择一个订单时，显示订单位置和设置订单信息
function setCusMsg(indentId) {
    if (indentId != 0) {//点击订单编号
        //通过orderId获取用户和订单的信息
        $.ajax({
            url: "/importIndentMsg",
            type: "POST",
            data: {"indentId": indentId},
            dataType: "json",
            success: function (data) {
                $("#customerName").val(data[0].customerName);
                $("#telephone").val(data[0].customerTelephone);
                $("#cellphone").val(data[0].customerCellphone);
                $("#automobileType").val(data[0].automobileType);
                $("#licensePlateNumber").val(data[0].licensePlateNumber);
                $("#createTime").val(data[0].createTime);
                $("#batteryType").val(data[0].batteryType);
                $("#address").val(data[0].address);

            }
        })

        $("#indentDetail").show();//订单详情按钮显示
    }
}
function setCusLocation(indentId) {

    var cusLongitude;//用户地理位置-经度
    var cusLatitude;//纬度

    map.remove(userMarkers)//重新选择订单后也清除地图


        //通过orderIda获取用户的地理位置作为位置中间
        $.ajax({
            url: "/getCustomerLocation",
            type: "POST",
            dataType: "json",
            data: {"orderId": indentId},
            success: function (data) {
                cusLongitude = data.cusLongitude
                cusLatitude = data.cusLatitude


                //map.remove(techMarkers)
                //在获取用户位置后更新地图的中心点和放大级别
                map.setZoom(13)
                map.setCenter([cusLongitude, cusLatitude])

                //设置用户的图标
                var icon = new AMap.Icon({
                    image: '../bgimg/user.png',
                    size: new AMap.Size(32, 32)
                });
                marker = new AMap.Marker({
                    icon: icon,
                    position: [cusLongitude, cusLatitude],
                    offset: new AMap.Pixel(-12, -12),
                    zIndex: 101,
                    title: '用户位置',
                    map: map
                });
                userMarkers.push(marker)
            }
        })

}

//更新城市时，更新当前城市的技师信息
function importTechMsgFromCity(cityCode) {


    //更新可选择的技师信息-通过cityCode
    var techCellphone
    var techName
    var techLongitude
    var techLatitude
    var techSex
    var techId
    var iswork
    var workStatus
    map.remove(techMarkers)//清除之前的技师在地图的图标

    $.ajax({
        url: "/importTechMsgFromCity",
        type: "POST",
        data: {"cityCode": cityCode},
        dataType: "json",
        success: function (data) {
            $("#technician").empty()
            $("#technician").append("<option value=0>---请选择---</option>")
            if (data != null) {
                for (var i in data) {
                    techName = data[i].name
                    techLongitude = data[i].longitude
                    techLatitude = data[i].latitude
                    techId = data[i].technicianId
                    techSex = data[i].sex
                    techCellphone = data[i].cellphone
                    iswork=data[i].iswork
                    if(iswork == '1'){//技师正在工作状态
                        workStatus="(工作中)"
                    }
                    else{//技师不在工作人状态
                        workStatus="(休息中)"
                    }
                    $("#technician").append('<option value=' + techId + '>' + techName + '-' + techId + workStatus+'</option>')


                    var title = setTechTitle(techName, techId)
                    var msg = setTechMsg(techCellphone, techSex,workStatus)

                    distributeTechMap(map, techLongitude, techLatitude, title, msg);

                }
            }
        }
    })

}

//将获取的技师信息进行拼接用于派单地图显示
function setTechTitle(name, techId) {
    return name + '<b style="color:#F00;">编号:' + techId + '<b>'
}

function setTechMsg(cellphone, sex,status) {
    return '电话号码：' + cellphone + '<br/>' + '性别：' + sex+"  状态："+status
}


