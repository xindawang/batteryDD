/**
 * Created by admin on 2017/9/20.
 */
$(function () {
    //地址规范化
    $("#address").click(function () {
        $("#address1").hide();
        $("#address2").show();
        $("#address3").show();
    });

    $("#finish").click(function () {

        var ss = $("#province option:selected").text() + $("#city option:selected").text() +
            $("#area option:selected").text() + $("#detailAddress").val();

        var province = $("#province option:selected").text();
        if (province != "---请选择---") {
            $("#address").val(ss);
        }
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
        var tLoginName = $("#loginName").val();
        var tName = $("#name").val();
        var password = $("#password").val();
        var cellphone= $("#cellphone").val();
        var telephone=$("#telephone").val();

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
        if(cellphone!=""&&isNaN(cellphone)){
            alert("手机号码应该是0~9之间的字符！！！");
            return;
        }
        if(telephone!=""&&isNaN(telephone)){
            alert("电话号码应该是0~9之间的字符！！！");
            return;
        }
        $.ajax({
                type: "POST",
                url: '/addStaff',
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