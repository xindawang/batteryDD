/**
 * Created by admin on 2017/7/24.
 */


$(function () {
    //显示初始信息
    var userName = GetQueryString('loginName');
    $.ajax({
        type: "post",
        url: "/findOneAdmin",
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

        },
        error: function (data) {
            alert(data)
        }

    });


//提交完善后的信息
    $("#submit").click(function () {
        var tLoginName = $("#loginName").val();
        var tName = $("#name").val();
        var password = $("#password").val();
        var cellphone = $("#cellphone").val();
        var telephone = $("#telephone").val();

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
        if (cellphone != "" && isNaN(cellphone)) {
            alert("手机号码应该是0~9之间的字符！！！");
            return;
        }
        if (telephone != "" && isNaN(telephone)) {
            alert("电话号码应该是0~9之间的字符！！！");
            return;
        }
        $.ajax({
                type: "POST",
                url: '/adminModify',
                data: $("#form").serialize(),
                //dataType:text,
                success: function (data) {
                    alert(data.toString());
                }
            }
        );
    })


    //地址规范化
    $("#address").click(function () {
        $("#address1").hide();
        $("#address2").show();
        $("#address3").show();
    });

    $("#finish").click(function () {

        var ss = $("#province option:selected").text() + $("#city option:selected").text() +
            $("#area option:selected").text() + $("#detailAddress").val();

        var provinceCode = $("#province option:selected").val();
        var citycode = $("#city option:selected").val();
        var areacode = $("#area option:selected").val();
        if (provinceCode == '0') {
            alert("请选择省份！");
            return;
        }
        if (citycode == "0") {
            alert("请选择城市 ！");
            return;
        }
        if (areacode == "0") {
            alert("请选择市区/县 ！");
            return;
        }

        $("#address").val(ss);
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


});


//取地址中的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

