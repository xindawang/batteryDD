/**
 * Created by huanglin on 2017/8/14.
 */

//var indentId        //订单编号
var cusAddress      //用户收货地址

var indentIdArray

var strMsg = window.location.search.substring(1)
var param = strMsg.split("&")
for(var node in param){

    var nodeDn=param[node].split("=")
    if(nodeDn[0]==='orderId')
        indentIdArray=nodeDn[1]

}

var checkStatusID
var getLocationID

var stompClient
$(function() {
    selectOrderId(indentIdArray);
    configVerify(indentIdArray)
    var orderId=$("#orderList option:selected").val()//代码运行时，开始对第一个订单进行处理，（查询订单进度）
    DealNewIndent(orderId)


    $("#orderList").change(function () {
        var orderId=$("#orderList option:selected").val()
        DealNewIndent(orderId)
    })

})

function DealNewIndent(indentId){

    setMsg(indentId);//订单状态查询初始化

    //确认是否以使用js接口--并且上传一次地理位置。

    websocketStatusInit(indentId) //websocket初始化

    window.clearInterval(checkStatusID)//清除上一次的计时。
    window.clearInterval(getLocationID)

    checkStatusID=window.setInterval(function(){

        setMsg(indentId)//为防止websocket通信失败，每1分钟查询一次订单状态，同时服务器压力也不会过大


    },1000*60)

    getLocationID=window.setInterval(function () {
            //window.confirm("每过10秒针上传一次地理位置")
            getGPSLocation(indentId)//10秒更新一次地理位置
        },
        10000)


}
//根据订单状态设置界面
function setMsg(indentId) {

    if(checkStatus(indentId)===1 || checkStatus(indentId)===2)
    {
        bsStep(1)
        getIndentMsg()
        $("#accepted").removeAttr('onclick')
        $("#finished").removeAttr('onclick')
    }
    else if(checkStatus(indentId)===3)
    {
        bsStep(2)
        indentAcceptedMsg()
    }else if(checkStatus(indentId)===4)
    {
        bsStep(3)
        indentFinishedMsg()

    }
}


function selectOrderId(data) {
    $("#orderList").empty()
    var orderArray = data.split(",")
    for (var i in orderArray) {
        if(orderArray[i] !=null && orderArray[i]!="")
            $("#orderList").append("<option value=" + orderArray[i] + ">" + orderArray[i] + "</option>")

    }
}

//显示弹框-订单信息
function indentMsg() {
    var indentId=$("#orderList option:selected").val()
    $.ajax({
        url: "/getIndentMsgByOrderId",
        type: "POST",
        data: {"orderId": indentId},
        dataType: "json",
        success: function (date) {
            var orderId = date[0].orderId
            var automobileType = date[0].automobileType
            var licensePlateNumber = date[0].licensePlateNumber
            var batteryType = date[0].batteryType

            $.mbox({
                area: ["300px", "auto"], //弹框大小
                border: [0, .5, "#666"],
                title: "订单详细信息",
                dialog: {
                    msg: "订单编号："+indentId+'<br/>'+"汽车型号："+automobileType+'<br/>'+"车牌号："+licensePlateNumber+'<br/>'+"电池型号："+batteryType,
                    btns: 1,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
                    type: 1,   //1:对钩   2：问号  3：叹号
                    btn: ["确定"],  //自定义按钮
                    yes: function () {  //点击左侧按钮：成功

                    },
                    no: function () {

                    }

                }
            })
        }
    })

}

//调整进度条显示  i 为number 可定位到第几步 如bsStep(2)/bsStep(3)
function bsStep(i) {
    $('.step').each(function() {
        var a, $this = $(this);
        if(i > $this.find('li').length) {
            console.log('进度条数超过' + $this.find('li').length );
            a=$this.find('li').length;
        } else if(i == undefined && $this.data('step') == undefined) {
            a = 1
        } else if(i == undefined && $this.data('step') != undefined) {
            a = $(this).data('step');
        } else {
            a = i
        }
        $(this).find('li').removeClass('active');
        $(this).find('li:lt(' + a + ')').addClass('active');
    })
}

function checkStatus(orderId){
    var result
    $.ajax({
        url:"/getStatusByOrderId",
        async: false,
        data:{"orderId":orderId},
        type:"POST",
        dataType:"json",
        success:function (data) {
            result=data
        }
    })
    return result

}

//正在派单时的订单信息

//已接单的信息
function indentAcceptedMsg() {
    $("#progressTitle").text("技师已出发")
    $("#progressDetail").html('<p>技师已经在的路上,请保持电话畅通</p><br/>' +
        '<button  onclick="turnIndentMap()">查看技师位置</button>')



}

function indentFinishedMsg() {

    $("#progressTitle").text("订单派发完成")
    $("#progressDetail").html('<p> 技师已经给您的爱车安装了新电池<br/><a style="color: red" href="javascript:void(0)" onclick="turnEvaluation()">点击评价</a> </p>')
}

function turnIndentMap() {
    var indentId=$("#orderList option:selected").val()
    window.location.href=basicUrl+"/templates/wxIndentMap.html?orderId="+indentId
}

function turnEvaluation() {
    var indentId=$("#orderList option:selected").val()
    $.ajax({
        url:"/getTechId",
        type:"POST",
        data:{"orderId":indentId},
        async:false,
        dataType:"json",
        success:function(data){

            window.location.href=basicUrl+"/templates/wxCusEvaluation.html?orderId="+indentId+"&techId="+data
        }
    })

}


function getIndentMsg() {



            $("#progressTitle").text("正在派单")
            $("#progressDetail").html('<p> 您的订单正在派发中，请耐心等待。<br/><a style="color: red" href="javascript:void(0)" onclick="indentMsg()">订单详情</a> </p>')
}




//------------------------websocket----------------------------

function websocketStatusInit(indentId) {
    //先把之前 的websocket关掉，再进行新新订单的websocket操作
    if (stompClient != null) {
        stompClient.disconnect();
    }
    var socket = new SockJS('/endpointDCDD');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        //window.confirm('开始连接')

        stompClient.subscribe('/topic/order_accept' + indentId, function (ex) {//技师已接单

            var status=JSON.parse(ex.body).status
            confirm(status)
            if(status =="accept"){
                bsStep(2)
                indentAcceptedMsg()
            }

        });

        stompClient.subscribe('/topic/order_finish' + indentId, function (ex) {//订单完成

            var status=JSON.parse(ex.body).status
            confirm(status)
            if(status =="finish"){
                bsStep(3)
                indentFinishedMsg()
            }


        });

    })

}