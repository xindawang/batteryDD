/**
 * Created by huanglin on 2017/7/27.
 */
var targetUrl = location.href.split("#")[0]
//targetUrl = encodeURIComponent(encodeURIComponent(targetUrl))




$(function () {
    var strMsg = window.location.search.substring(1)
    var param = strMsg.split("&")[0].split('=')
    if (param[0] === 'code')
        var code = param[1]
        $.ajax({
            url: "/getOpenId",
            type: "POST",
            dataType: "json",//
            data: {"code": code},
            success: function (data) {
                if (data.length>0) {
                    var orderIdList=''
                    for(var i in data){
                        if(data[i].status <4){
                            orderIdList+=(data[i].orderId+',')

                        }
                    }
                    var dispatchProgressUrl = basicUrl+"/templates/wxDispatchProgress.html?orderId=" + orderIdList
                    window.location="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect "
                        .replace("APPID", APPID).replace("REDIRECT",dispatchProgressUrl)

                    return
                }

                window.location="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect "
                    .replace("APPID", APPID).replace("REDIRECT",wxGetServiceByPhone)

            }
        })





});

