/**
 * Created by huanglin on 2017/8/16.
 */

var orderId
var techId

var targetUrl = location.href.split("#")[0]
var strMsg = window.location.search.substring(1)
var param = strMsg.split("&")
for(var i in param){
    var j=param[i].split("=")
    if(j[0]==="orderId")
        orderId=j[1]
    if(j[0]==="techId")
        techId=j[1]
}


$(function(){

    starChange();
    // $("#evaSubmit").click(checkClick())
    $("#evaSubmit").click(function () {
        var techService
        var deliverySpeed
        var productQuality

        productQuality=$(".remark1").attr("data-id")
        techService=$(".remark2").attr("data-id")
        deliverySpeed=$(".remark3").attr("data-id")


        if(!(techService==null || deliverySpeed==null || productQuality==null)){


            $.ajax({
                url:"/evaSubmit",
                type:"POST",
                dataType:"json",
                data:{"productQuality":productQuality,
                    "techService":techService,
                    "deliverySpeed":deliverySpeed,
                    "techId":techId,
                    "orderId":orderId
                },
                success:function (data) {
                    if (data == "success") {
                    window.location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect "
                        .replace("APPID", APPID).replace("REDIRECT", wxHistoryIndent)
                    }else{
                        window.confirm("您已评价过此订单了")
                    }

                }
            })
        }

    })
});
function starChange(){
    for(var j=1;j<4;j++) {
        for (var i = 1; i <= 5; i++) {

            //为每个星星 绑定点击事件
            $(".list"+j).find(".star" + i).bind("click", function () {
                var index = $(this).attr("class").substr(4, 1);
                var listN=$(this).parent().attr("class").substr(4,1)
                changeEvaluation(index,listN)
                //点击之后 解绑本身及之前星星的 所有事件
                for (var i = 1; i <= index; i++) {
                    //$(".list"+listN).find(".star" + i).unbind();

                }

            });
        }
    }
}


function changeEvaluation(index,listN) {
    var ind=index
    while(++ind <= 5){
        $(".list"+listN).find(".star" + ind).removeClass("c1");

    }
    for (var i = index; i > 0; i--) {
        $(".list"+listN).find(".star" + i).addClass("c1");//改变颜色
    }

    //改变评价样式 及 内容
    switch (index) {
        case "1":
            $(".remark"+listN).addClass("c2").text("非常差");
            $(".remark"+listN).attr("data-id",1);
            break;
        case "2":
            $(".remark"+listN).addClass("c2").text("差");
            $(".remark"+listN).attr("data-id",2);
            break;
        case "3":
            $(".remark"+listN).addClass("c2").text("一般");
            $(".remark"+listN).attr("data-id",3);
            break;
        case "4":
            $(".remark"+listN).addClass("c2").text("好");
            $(".remark"+listN).attr("data-id",4);
            break;
        case "5":
            $(".remark"+listN).addClass("c2").text("非常好");
            $(".remark"+listN).attr("data-id",5);
            break;
    }
}


function checkClick() {
    var techService
    var deliverySpeed
    var productQuality

    productQuality=$(".remark1").attr("data-id")
    techService=$(".remark2").attr("data-id")
    deliverySpeed=$(".remark3").attr("data-id")
    console.log("tech:"+techService)
    console.log("deli:"+deliverySpeed)
    console.log("pro:"+productQuality)
}

