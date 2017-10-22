/**
 * Created by admin on 2017/9/20.
 */

$(function () {

    //编号
    $.ajax({
            type: "GET",
            url: '/findLastId',
            data: null,
            success: function (data) {
                var d = data;
                $("#technicianId").val(d);
            },
            error: function (data) {
                alert("早不到可用的id");
            }
        }
    );
    //地址规范化
    $("#address").click(function () {
        $("#address1").hide();
        $("#address2").show();
        $("#address3").show();
    });

    $("#finish").click(function () {

        var ss = "";
        var province = $("#province option:selected").text();
        var code = $("#city option:selected").val();
        var cityName = $("#city option:selected").text();
        var areaName = $("#area option:selected").text();

        if (province != "---请选择--") {
            ss += province;
        }
        else {
            alert("请选择省份！");
            return;
        }
        if (cityName != "---请选择--") {
            ss += cityName;
        }
        else {
            alert("请选择城市 ！");
            return;
        }
        if (areaName != "---请选择--") {
            ss += areaName;
        }
        ss += $("#detailAddress").val();
        $("#address").val(ss);
        $("#cityCode").val(code);

        $("#address2").hide();
        $("#address3").hide();
        $("#address1").show();
    });

    $("#cancel").click(function () {
        $("#address2").hide();
        $("#address3").hide();
        $("#address1").show();
    });


    //性别规范化
    $("#sex").click(function () {
        $("#sex1").hide();
        $("#sex2").show();
    });
    $("#sexType").mouseleave(function () {
        var sex = $("#sexType option:selected").text()
        $("#sex").val(sex);
        $("#sex1").show();
        $("#sex2").hide();
    });


    //提交完善后的信息
    $("#submit").click(function () {
        var tID = $("#technicianId").val();
        var tLoginName = $("#loginName").val();
        var tName = $("#name").val();
        var password = $("#password").val();
        var address = $("#address").val();
        var idNumber = $("#idNumber").val();

        var cellphone = $("#cellphone").val();
        var telephone = $("#telephone").val();

        if (tID == null || tID == "") {
            alert("编号不能为空！！！");
            return;
        }
        if (tLoginName == null || tLoginName == "") {
            alert("登陆名不能为空！！！");
            return;
        }
        if (tName == null || tName == "") {
            alert("姓名不能为空！！！");
            return;
        }
        if (password == null || password == "") {
            alert("密码不能为空！！！");
            return;
        }
        if (password.length < 6) {
            alert("请输入6位~16位（含）字符组成的密码！！！");
            return;
        }
        if (!numNndChar(password)) {
            alert("请输入由数字或字母组成的密码！！！");
            return;
        }
        if (idNumber == null || idNumber == "") {
            alert("身份证号不能为空！！！");
            return;
        }
        if (idNumber.length < 18) {
            alert("身份证号长度为18位！");
            return;

        }
        if (address == null || address == "") {
            alert("请设置地址！！！");
            return;
        }
        if (cellphone == null || cellphone == "") {
            alert("请输入手机号");
            return;
        }
        if (cellphone.length < 11) {
            alert("手机号码应该是11个数字字符！！！");
            return;
        }
        if (!isNum(cellphone)) {
            alert("手机号码应该是0~9之间的字符！！！");
            return;
        }

        if (telephone != null && telephone != "") {
            if (telephone.length < 11) {
                alert("电话号码应该是11个数字字符！！！");
            }
            if (!isNum(telephone)) {
                alert("电话号码应该是0~9之间的字符！！！");
                return;
            }
        }
        $.ajax({
                type: "POST",
                url: '/addTechnician',
                data: $("#form").serialize(),
                //dataType:text,
                success: function (data) {
                    if (data == "ok") {
                        $("#submit").hide();
                        alert("添加成功！！！")
                    }
                    else {
                        alert(data)
                    }

                }
            }
        );
    })

});

function numNndChar(str) {
    for (var i = 0; i < str.length; i++) {
        if (str.charAt(i) < '0' || str.charAt(i) > '9') {
            if (str.charAt(i) < 'a' || str.charAt(i) > 'z') {
                if (str.charAt(i) < 'A' || str.charAt(i) > 'Z') {
                    return false;
                }
            }
        }
    }
    return true;
}

function isNum(str) {
    for (var i = 0; i < str.length; i++) {
        if (str.charAt(i) < '0' || str.charAt(i) > '9') {
            return false;
        }
    }
    return true;
}