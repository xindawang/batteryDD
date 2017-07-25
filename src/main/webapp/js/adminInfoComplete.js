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
        $.ajax({
                type: "POST",
                url: '/adminModify',
                data: $("#form").serialize(),
                //dataType:text,
                success: function (data) {
                    document.getElementById("showMsg").innerHTML = data.toString();
                }
            }
        );
    })


    //地址规范化
    $("#address").click(function () {
        $("#address1").hide();
        $("#address2").show();
    });

    $("#finish").click(function () {

        var ss = $("#province option:selected").text()+ $("#city option:selected").text()+
            $("#area option:selected").text()+ $("#detailAddress").val();
        $("#address").val(ss);
        $("#address2").hide();
        $("#address1").show();
    });
    $("#cancel").click(function () {
        $("#address2").hide();
        $("#address1").show();
    });


});


//取地址中的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}

