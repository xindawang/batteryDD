/**
 * Created by huanglin on 2017/8/26.
 */
var strMsg = window.location.search.substring(1)
var param = strMsg.split("&")[0].split('=')
if (param[0] === 'code')
    var code = param[1]
// window.confirm(code)
$(function () {
    var batteryType
    var finishTime
    var orderId
    var createTime
    var status
    var techName
    var techId
    var techLicense
    var techCellphone
    $('#allIndent').addClass("Acolor")
    $("#allSpan").show()
    $("#allIndent").click(function () {
        $(this).addClass("Acolor")
        $("#workingIndent").removeClass("Acolor")
        $("#doneIndent").removeClass("Acolor")
        $("#allSpan").show()
        $("#workingSpan").hide()
        $("#doneSpan").hide()
    })

    $("#workingIndent").click(function () {
        $(this).addClass("Acolor")
        $("#allIndent").removeClass("Acolor")
        $("#doneIndent").removeClass("Acolor")
        $("#allSpan").hide()
        $("#workingSpan").show()
        $("#doneSpan").hide()
    })
    $("#doneIndent").click(function () {
        $(this).addClass("Acolor")
        $("#allIndent").removeClass("Acolor")
        $("#workingIndent").removeClass("Acolor")
        $("#allSpan").hide()
        $("#workingSpan").hide()
        $("#doneSpan").show()
    })

    $.ajax({
        url: "/getOpenId",
        type: "POST",
        dataType: "json",
        async: false,
        data: {"code": code},
        success: function (data) {
            if (data != null) {
                for (var i in data) {
                    batteryType=data[i].batteryType
                    finishTime=data[i].finishTime
                     orderId=data[i].orderId
                     createTime=data[i].createTime
                     status=data[i].status

                    $.ajax({
                        url:"/getTechMsg",
                        type:"POST",
                        data:{"orderId":orderId},
                        dataType:"json",
                        success:function(data){
                            techName=data.name
                            techId=data.technicianId
                            techLicense=data.licensePlateNumber
                            techCellphone=data.cellphone
                            // window.confirm(techName+techId)
                            setIndentMsg(orderId,batteryType,createTime,finishTime,status,techId,techName,techCellphone,techLicense)
                        }
                    })
                }
            }


        }

    })


})

function setIndentMsg(orderId,batteryType,createTime,finishTime,status,techId,techName,techCellphone,techLicense) {

    var evaButtonString
    if(status== 4)
    {
        evaButtonString="<div class='evaButtonDiv'><button onclick=\"evaTurn('"+orderId+"','"+techId+"')\" class='evaButton'>评价</button></div>"
    }else{
        evaButtonString=""
    }

    if(status==5){
        status="已评价"
    }else if(status==4){
        status="已安装"
    }else if(status==3){
        status="待安装"
    }else{
        status="待派发"
    }


    $("#base").append(

        "<div class='IndentMsg'>"+
        "<div class='indentStatus'>"+status+"</div>"+

        "<div class='indentDetail'>"+
        "<span class='detailTitle'>订单信息</span><br/>"+
        "<span class='detailSpan'>订单编号:"+orderId+"</span>"+
        "<span class='detailSpan'>电池型号:"+batteryType+"</span><br/>"+
        "<span class='detailSpan'>下单时间:"+createTime+"</span>"+
        "<span class='detailSpan'>安装时间:"+finishTime+"</span>"+
        "</div>"+
        "<div class='techDetail'>"+
        "<span class='detailTitle'>技师信息</span><br/>"+
        "<span class='detailSpan'>技师姓名:"+techName+"</span>"+
        "<span class='detailSpan'>技师编号:"+techId+"</span><br/>"+
        "<span class='detailSpan'>技师电话:"+techCellphone+"</span>"+
        "<span  class='detailSpan'>车牌号:"+techLicense+"</span>"+
        "</div>"+evaButtonString+
        "</div>"

    )


}

function evaTurn(orderId,techId) {
    window.location="wxCusEvaluation.html?orderId="+orderId+"&techId="+techId

}