/**
 * Created by huanglin on 2017/8/22.
 */
$(function () {
    var strMsg = window.location.search.substring(1)
    var param = strMsg.split("&")[0].split('=')
    if (param[0] === 'code')
        var code = param[1]

    $("#main-btn").click(function () {

        $.ajax({
            type: "POST",
            url: "/applyServiceValidate",
            data: {"cellphone": $("#main-phone").val(),"code":code},
            dataType: "json",
            success: function (data) {
                console.log(Object.prototype.toString.call(data))
                console.log(data)
                if(data.toString()=="0"){
                    confirm("电话号码不能少于11位或者为空")
                }else if(data.toString()=="1") {
                    confirm("订单不存在,请检查电话号码")
                }else
                {
                    window.location="wxDispatchProgress.html?orderId="+data
                }

            }
        })

    })

})