/**
 * Created by huanglin on 2017/8/22.
 */
var stompClient
$(function () {

    var strMsg = window.location.search.substring(1)
    var param = strMsg.split("&")
    var code
    for(var node in param){

        var nodeDn=param[node].split("=")
        if(nodeDn[0]==='code')
            code=nodeDn[1]

    }

    var socket = new SockJS('/endpointDCDD');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
            //window.confirm("开始连接")
    })


    $("#main-btn").click(function () {

        $.ajax({
            type: "POST",
            url: "/applyServiceValidate",
            data: {"cellphone": $("#main-phone").val(),"code":code},
            dataType: "json",
            success: function (data) {
                //console.log(Object.prototype.toString.call(data))
                //console.log(data)
                if(data.toString()=="0"){
                    confirm("电话号码不能少于11位或者为空")
                }else if(data.toString()=="1") {
                    dealNoIndent()
                }else
                {
                    //申请成功后，提示客服及时进行订单派送
                        //window.confirm('开始连接')
                        for(var i in  data){//向后台发现消息，告诉客服用户申请了服务

                            var msg = JSON.stringify({'orderId': data[i]})
                            stompClient.send("/app/newIndent", {}, msg)
                        }


                    var dispatchProgressUrl=basicUrl+"/templates/wxDispatchProgress.html?orderId="+data

                    window.location.href=dispatchProgressUrl
                }

            }
        })

    })


    function dealNoIndent() {
        $.mbox({
            area: ["300px", "auto"], //弹框大小
            border: [0, .5, "#666"],
            title: "当前不存在正在派发的订单",
            dialog: {
                msg: "请检查您的电话号码是否正确或者稍后申请",
                btns: 1,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
                type: 3,   //1:对钩   2：问号  3：叹号
                btn: ["稍后申请"],  //自定义按钮
                yes: function () {  //点击左侧按钮：成功
                    window.opener=null;
                    window.open('','_self');
                    window.close();

                },
                no: function () {

                }

            }
        })
    }



})