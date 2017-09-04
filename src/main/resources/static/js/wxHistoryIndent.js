/**
 * Created by huanglin on 2017/8/26.
 */


$(function () {

    var strMsg = window.location.search.substring(1)
    var param = strMsg.split("&")
    var code
    var showStyle
    for(var node in param){

        var nodeDn=param[node].split("=")
        if(nodeDn[0]==='code')
            code=nodeDn[1]
        if(nodeDn[0]==='showStyle')
            showStyle=nodeDn[1]

    }

    var batteryType
    var finishTime
    var orderId
    var createTime
    var status
    var techName
    var techId
    var techLicense
    var techCellphone


    //-----------------事件监控-----------------
    //test(showStyle);
    // $('#allIndent').addClass("Acolor")
    // $("#allSpan").show()
    $("#allIndent").click(function () {

        var wxHistoryIndent=basicUrl+"/templates/wxHistoryIndent.html?showStyle="+"all"
        window.location.replace(wxHistoryIndent)

    })

    $("#workingIndent").click(function () {

        var wxHistoryIndent=basicUrl+"/templates/wxHistoryIndent.html?showStyle="+"working"
        window.location.href=wxHistoryIndent
    })
    $("#doneIndent").click(function () {

        var wxHistoryIndent=basicUrl+"/templates/wxHistoryIndent.html?showStyle="+"done"
        window.location.href=wxHistoryIndent

    })

    // -----------------事件监控-------------------------

    $.ajax({
        url: "/getOpenId",
        type: "POST",
        dataType: "json",
        async: false,
        data: {"code": code},
        success: function (data) {
            var indentNumber=0

            if (data != null) {
                for (var i in data) {

                    batteryType=data[i].batteryType
                    finishTime=data[i].finishTime
                     orderId=data[i].orderId
                     createTime=data[i].createTime
                     status=data[i].status


                    //window.confirm(orderId)
                    if(showStyle==null || showStyle=="all") {

                        $('#allIndent').addClass("Acolor")
                        $("#workingIndent").removeClass("Acolor")
                        $("#doneIndent").removeClass("Acolor")
                        $("#allSpan").show()
                        $("#workingSpan").hide()
                        $("#doneSpan").hide()

                        indentNumber=data.length

                        $.ajax({
                            url: "/getTechMsg",
                            type: "POST",
                            async:false,
                            data: {"orderId": orderId},
                            dataType: "json",
                            success: function (data) {
                                if(data=="未指派技师"){
                                    techName="等待指派"
                                    techId = "未知"
                                    techLicense = "未知"
                                    techCellphone = "未知"
                                }else
                                {
                                    techName = data.name
                                    techId = data.technicianId
                                    techLicense = data.licensePlateNumber
                                    techCellphone = data.cellphone
                                }
                                // window.confirm(techName+techId)
                                setIndentMsg(orderId, batteryType, createTime, finishTime, status, techId, techName, techCellphone, techLicense)
                            }
                        })
                    }else if(showStyle=="working"){

                        $("#workingIndent").addClass("Acolor")
                        $("#allIndent").removeClass("Acolor")
                        $("#doneIndent").removeClass("Acolor")
                        $("#allSpan").hide()
                        $("#workingSpan").show()
                        $("#doneSpan").hide()

                        if(status<4){
                            indentNumber++

                            $.ajax({
                                url: "/getTechMsg",
                                type: "POST",
                                async:false,
                                data: {"orderId": orderId},
                                dataType: "json",
                                success: function (data) {
                                    if(data=="未指派技师"){
                                        techName="等待指派"
                                        techId = "未知"
                                        techLicense = "未知"
                                        techCellphone = "未知"
                                    }else
                                    {
                                        techName = data.name
                                        techId = data.technicianId
                                        techLicense = data.licensePlateNumber
                                        techCellphone = data.cellphone
                                    }
                                    setIndentMsg(orderId, batteryType, createTime, finishTime, status, techId, techName, techCellphone, techLicense)
                                }
                            })
                        }
                    }else if(showStyle=="done"){

                        $("#doneIndent").addClass("Acolor")
                        $("#allIndent").removeClass("Acolor")
                        $("#workingIndent").removeClass("Acolor")
                        $("#allSpan").hide()
                        $("#workingSpan").hide()
                        $("#doneSpan").show()

                        if(status>=4){
                            indentNumber++
                            $.ajax({
                                url: "/getTechMsg",
                                type: "POST",
                                async:false,
                                data: {"orderId": orderId},
                                dataType: "json",
                                success: function (data) {
                                    if(data=="未指派技师"){
                                        techName="等待指派"
                                        techId = "未知"
                                        techLicense = "未知"
                                        techCellphone = "未知"
                                    }else
                                    {
                                        techName = data.name
                                        techId = data.technicianId
                                        techLicense = data.licensePlateNumber
                                        techCellphone = data.cellphone
                                    }
                                    // window.confirm(techName+techId)
                                    setIndentMsg(orderId, batteryType, createTime, finishTime, status, techId, techName, techCellphone, techLicense)
                                }
                            })
                        }
                    }
                }

                if(indentNumber==0){
                    showNoIndent()
                }
            }else{
                showNoIndent()
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
    }else if(status==1){
        status="待派发"
    }else{
        status="待安装"
    }


    $("#base").append(

        "<div class='IndentMsg'>"+
        "<div class='indentStatus'>"+status+"</div>"+

        "<div class='indentDetail'>"+
        "<span class='detailTitle'>订单信息</span><br/>"+
        "<span class='detailSpan'>编号:"+orderId+"</span>"+
        "<span class='detailSpan'>型号:"+batteryType+"</span><br/>"+
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

function showNoIndent() {$("#base").append(
    "<div style=' position: fixed;text-align:center;left: 50%;top: 50%;width:128px;height:150px;margin-left:-64px;margin-top:-75px;'>"+
    "<img style='width: 100% ;height:auto' src='../images/weixin/noIndent.png'><br/>"+
    "<span style=' color:#4bb1cf;  font-size: 18px'>订单空空如也</span>"+
    "</div>"
)
}

function evaTurn(orderId,techId) {
    var cusEvationUrl=basicUrl+"/templates/wxCusEvaluation.html?orderId="+orderId+"&techId="+techId
    window.location.href=cusEvationUrl
}

function test(value) {
    window.confirm(value)
}