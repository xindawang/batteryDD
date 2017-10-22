/**
 * Created by admin on 2017/7/24.
 */




$(function () {

    //显示初始信息
    var userName = decodeURI(GetQueryString('loginName'));
    $.ajax({
        type: "post",
        url: "/findOneStaff",
        data: {
            loginName: userName,
        },
        dataType: "json",
        success: function (data) {

            $("#loginName").val(data.loginName);
            $("#password").val(data.password);
            $("#name").val(data.name);
            $("#sex").val(data.sex);
            $("#cellphone").val(data.cellphone);
            $("#telephone").val(data.telephone);
            $("#email").val(data.email);
            $("#idNumber").val(data.idNumber);
            $("#address").val(data.address);
            $("#role").val(data.role);
            // $("#organizationId").val(data.organizationId);

        }
    });


//提交完善后的信息
    $("#submit").click(function () {
        var tLoginName = $("#loginName").val();
        var tName = $("#name").val();
        var password = $("#password").val();
        var cellphone = $("#cellphone").val();
        var telephone = $("#telephone").val();
        var idNumber=$("#idNumber").val();
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
        if(idNumber!=null && idNumber!=""){
            if(idNumber.length<18){
                alert("身份证号长度为18位！")
                return;
            }
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
                return;
            }
            if (!isNum(telephone)) {
                alert("电话号码应该是0~9之间的字符！！！");
                return;
            }
        }
        $.ajax({
                type: "POST",
                url: '/staffModify',
                data: $("#form").serialize(),
                //dataType:text,
                success: function (data) {
                    alert(data);
                }
            }
        );
    });


    //地址规范化
    $("#address").click(function () {
        $("#address1").hide();
        $("#address2").show();
        $("#address3").show();
    });

    $("#finish").click(function () {

        var province = $("#province option:selected").text();
        var cityName = $("#city option:selected").text();
        var areaName = $("#area option:selected").text();

        var ss = "";
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
        $("#address3").hide();
        $("#address2").hide();
        $("#address1").show();
    });
    $("#cancel").click(function () {
        $("#address3").hide();
        $("#address2").hide();
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


});


//取地址中的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return r[2];
    return null;
}

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