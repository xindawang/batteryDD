/**
 * Created by huanglin on 2017/8/14.
 */

var indentId        //订单编号
var cusAddress      //用户收货地址
var automobileType  //用户车型
var licensePlateNumber //车牌
var batteryType
var cusCellphone;
var techId



var strMsg = window.location.search.substring(1)
var param = strMsg.split("&")
for(var node in param){

    var nodeDn=param[node].split("=")
    if(nodeDn[0]==='orderId')
        indentId=nodeDn[1]

}

var checkStatusID

$(function() {
    //首先上传一次用户位置方便派单
    UpdateCusLocation(indentId)
    //正在派单状态
    setMsg();

    checkStatusID=window.setInterval(setMsg,10000)
    


})

//根据订单状态设置界面
function setMsg() {

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


//显示弹框-订单信息
function indentMsg(indentId,automobileType,licensePlateNumber,batteryType) {
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

            }

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
function IndentdistributingMsg() {
    $("#progressTitle").text("正在派单")
    $("#progressDetail").html('<p> 您的订单正在派发中，请耐心等待。<br/><a style="color: red" href="javascript:void(0)" onclick="indentMsg(indentId,automobileType,licensePlateNumber,batteryType)">订单详情</a> </p>')
}
//已接单的信息
function indentAcceptedMsg() {
    $.ajax({
        url:"/getTechId",
        type:"POST",
        data:{"orderId":indentId},
        dataType:"json",
        success:function(data){
            techId=data;
        }
    })

    $("#progressTitle").text("技师已出发")
    $("#progressDetail").html('<p>技师已经在的路上,请保持电话畅通</p><br/>' +
        '<button onclick="turnIndentMap()">查看技师位置</button>')
}

function indentFinishedMsg() {

    $("#progressTitle").text("订单派发完成")
    $("#progressDetail").html('<p> 技师已经给您的爱车安装了新电池<br/><a style="color: red" href="javascript:void(0)" onclick="turnEvaluation()">点击评价</a> </p>')
}

function turnIndentMap() {
    var indentMapUrl=basicUrl+"/templates/wxIndentMap.html?orderId="+indentId
    window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect "
        .replace("APPID", APPID).replace("REDIRECT",indentMapUrl)
}

function turnEvaluation() {
    var evaLuationUrl=basicUrl+"/templates/wxCusEvaluation.html?orderId="+indentId+"&techId="+techId
    window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect "
        .replace("APPID", APPID).replace("REDIRECT",evaLuationUrl)
}


function getIndentMsg() {

    $.ajax({
        url:"/getIndentMsgByOrderId",
        type:"POST",
        data:{"orderId":indentId},
        dataType:"json",
        success:function (date) {
            indentId=date[0].orderId
            cusAddress=date[0].address
            automobileType=date[0].automobileType
            licensePlateNumber=date[0].licensePlateNumber
            batteryType=date[0].batteryType


            IndentdistributingMsg()

        }
    })
}