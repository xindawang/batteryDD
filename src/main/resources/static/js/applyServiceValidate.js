/**
 * Created by huanglin on 2017/7/27.
 */
var targetUrl = location.href.split("#")[0]
targetUrl = encodeURIComponent(encodeURIComponent(targetUrl))

$(function () {
    $("#main-btn").click(function () {

        $.ajax({
            type: "POST",
            url: "/applyServiceValidate",
            data: {"cellphone": $("#main-phone").val()},
            dataType: "json",
            success: function (data) {
                console.log(Object.prototype.toString.call(data))
                console.log(data)
                if(data.toString() !="订单存在，请等待技师给您派单"){
                    confirm(data.toString())
                }else {
                        window.location="dispatchProgress.html"
                    //window.location="wxIndentMap.html?telephone="+$("#main-phone").val();
                }

            }
        })

    })

});
