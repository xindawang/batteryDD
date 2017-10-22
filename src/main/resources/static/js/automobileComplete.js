/**
 * Created by admin on 2017/9/27.
 */
$(function () {

    var strMsg = window.location.search.substring(1)
    var param = strMsg.split("&")
    var brand = 0, carType = 1;
    for (var node in param) {

        var nodeDn = param[node].split("=")
        if (nodeDn[0] === 'brand')
            brand = decodeURIComponent(nodeDn[1]);
        if (nodeDn[0] == 'carType')
            carType = decodeURIComponent(nodeDn[1]);
    }
    $("#brand").val(brand);
    $("#type").val(carType);
    $("#oldtype").val(carType);


    //显示汽车使用的电池
    $.ajax({
        type: "POST",
        url: "/BatteryShow",
        data: {
            brand: brand,
            carType: carType,
        },
        dataType: "json",
        success: function (data) {
            $("#show").empty()
            for (var i = 0; i < data.length; i++) {
                var id = data[i].id;
                var TypeName = data[i].type;
                TypeName = TypeName.replace(/\s/g, "");
                var k = i + 1;
                $('#show').append(k + '&nbsp;&nbsp;' + TypeName + '<p/>');
                $("#show1").append('<input type="checkbox" name="battery" style="display: inline-block" value=' + id + '>' + '&nbsp;&nbsp;' + TypeName + '<p/>');
            }
        }
    });


    //汽车品牌导入
    $.ajax({
        type: "POST",
        url: "/importBatteryBrand",
        data: null,
        dataType: "json",
        success: function (data) {
            $("#batteryBrand").empty()
            $('#batteryBrand').append("<option value= 0>---请选择---</option>")
            for (var i = 0; i < data.length; i++) {
                var id = data[i].id;
                var brandName = data[i].brandName;
                $('#batteryBrand').append("<option value=" + id + ">" + brandName + "</option>");

            }
        }
    });

    $("#batteryBrand").change(function () {
        $.ajax({
            type: "POST",
            url: "/importBatteryType",
            data: {"batteryBrand": $("#batteryBrand option:selected").val()},
            dataType: "json",
            success: function (data) {
                $('#batteryType').empty();//清空下拉列表
                $('#batteryType').append("<option value= 0>---请选择---</option>");
                for (var i in data) {
                    var id = data[i].id;
                    var type = data[i].type;
                    $('#batteryType').append("<option value=" + id + ">" + type + "</option>");
                }
            }
        });
    });

    //选择了电池型号;显示添加按钮
    $("#batteryType").change(function () {
        $("#addBtn").show();
    });
    //添加汽车适用的电池
    $("#addBattery").click(function () {
        var type = $("#batteryType option:selected").text();
        $.ajax({
                type: "POST",
                url: '/addBattery',
                data: {
                    brand: $("#brand").val().replace(/\s/g, ""),
                    carType: $("#type").val().replace(/\s/g, ""),
                    batteryId: $("#batteryType option:selected").val(),
                },
                success: function (data) {

                    if (data == 'ok') {
                        alert("电池添加成功！！");
                        $("#addBtn").hide();
                        $("#show").append("* &nbsp;&nbsp;" + type + "<br>");
                    }
                    else if (data == 'error1') {
                        alert("电池已存在！！");
                    }
                    else {
                        alert("添加失败！！");
                    }

                }
            }
        );

    });

    //删除汽车适用电池，显示复选框
    $("#deleteBattery").click(function () {

        $("#show1").text("");
        $.ajax({ //重新加载数据
            type: "POST",
            url: "/BatteryShow",
            data: {
                brand: brand,
                carType: carType,
            },
            dataType: "json",
            success: function (data) {
                $("#show").empty()
                for (var i = 0; i < data.length; i++) {
                    var id = data[i].id;
                    var TypeName = data[i].type;
                    TypeName = TypeName.replace(/\s/g, "");//去空格
                    var k = i + 1;
                    $("#show1").append('<input type="checkbox" name="battery" style="display: inline-block" value=' + id + '>' + '&nbsp;&nbsp;' + TypeName + '<input id=' + id + ' type="text"  style="display: none"  value=' + TypeName + '>' + '<p/>');
                }
            }
        });
        $("#BatteryArea").hide();
        $("#BatteryArea1").show();
    });

    $("#cancel").click(function () {
        $("#show").text("");
        $.ajax({//重新加载数据
            type: "POST",
            url: "/BatteryShow",
            data: {
                brand: brand,
                carType: carType,
            },
            dataType: "json",
            success: function (data) {
                $("#show").empty()
                for (var i = 0; i < data.length; i++) {
                    var id = data[i].id;
                    var TypeName = data[i].type;
                    var k = i + 1;
                    $('#show').append(k + '&nbsp;&nbsp;' + TypeName + '<p/>');
                }
            }
        });
        $("#BatteryArea").show();
        $("#BatteryArea1").hide();
    });

    $("#commit").click(function () {

        var ss = "";
        var battery = document.getElementsByName('battery');
        var n = 0;//计算复选框选中的数目
        for (var i = 0; i < battery.length; i++) {
            if (battery[i].checked) {
                n = n + 1;
                ss += document.getElementById(battery[i].value).value ; //如果选中，将value添加到变量s中
                if(i<battery.length-1){
                    ss+= ','
                }
            }
        }
        if (n <= 0) {
            alert("请选择要删除的电池！")
            return;
        }
        if (window.confirm("确定删除：" + ss + " 电池吗？")) {
            $.ajax({
                    type: "POST",
                    url: '/deleteBattery',
                    data: $('#form').serialize(),
                    success: function (data) {
                        alert(data);
                        $("#BatteryArea").show();
                        $("#BatteryArea1").hide();
                        //显示删除后汽车使用的电池
                        $.ajax({//重新加载数据
                            type: "POST",
                            url: "/BatteryShow",
                            data: {
                                brand: brand,
                                carType: carType,
                            },
                            dataType: "json",
                            success: function (data) {
                                $("#show").empty()
                                for (var i = 0; i < data.length; i++) {
                                    var id = data[i].id;
                                    var TypeName = data[i].type;
                                    TypeName = TypeName.replace(/\s/g, "");
                                    var k = i + 1;
                                    $('#show').append(k + '&nbsp;&nbsp;' + TypeName + '<p/>');
                                    $("#show1").append('<input type="checkbox" name="battery" style="display: inline-block" value=' + id + '>' + '&nbsp;&nbsp;' + TypeName + '<p/>');
                                }
                            }
                        });

                    }
                }
            );
        }
    });


    //修改车型名称
    $("#editType").click(function () {

        var newtype = $("#type").val();
        if (newtype == null || newtype == "") {
            alert("请输入车型名称！！")
            return;
        }
        $.ajax({
                type: "POST",
                url: '/carTypeModify',
                data: {
                    brand: $("#brand").val(),
                    carType: $("#type").val().replace(/\s/g, ""),
                    oldcarType: $("#oldtype").val()
                },
                success: function (data) {
                    alert(data.toString());
                }
            }
        );

    });
});