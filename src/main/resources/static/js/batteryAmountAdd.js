$(function () {
    $("#add").click(function () {
        var cityCode = $("#city").val()
        var batterType = $("#batteryType").val()
        var stock = $("#batteryStock").val()
        var add = true
        if (cityCode == null || cityCode == 0) {
            window.confirm("请选择城市")
            add = false
        }

        if (batterType==null || batterType == 0) {
            window.confirm("请选择电池型号")
            add = false
        }

        if (stock == "") {
            window.confirm("库存不能为空")
            add = false
        }else if ( ! isNaN(Number(stock))) {
            if(parseInt(stock) != stock){
                window.confirm("请输入整数")
                add = false
            }
        }
        else{
            window.confirm("请输入正确数值")
            add = false
        }




        if (add) {
            addAmount(cityCode, batterType, stock)
        }
    })
})


function addAmount(cityCode, batteryType, stock) {
    $.ajax({
        url: '/addBatteryAmount',
        type: "POST",
        data: {"cityCode": cityCode, "batteryType": batteryType, "batteryStock": stock},
        dataType: "json",
        success: function (data) {
            window.confirm(data)
            window.location = "batteryAmount.html"
        }


    })
}