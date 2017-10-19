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

        var ss =  $("#detailAddress").val();

        var province = $("#province option:selected").text();
        var city = $("#city option:selected").text();
        var code=$("#city option:selected").val();
        var area= $("#area option:selected").text();

        if (province=="---请选择--"||city=="---请选择--"||area=="---请选择--") {
            alert("请设置地址！！！");
           return;
        }
        ss = province+ city+area+$("#detailAddress").val();
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
        var address=$("#address").val();
        var idNumber=$("#idNumber").val();
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
        if (idNumber == null || idNumber == "") {
            alert("身份证号不能为空！！！");
            return;
        }
        if (address == null || address == "") {
            alert("请设置地址！！！");
            return;
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