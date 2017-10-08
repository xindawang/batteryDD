/**
 * Created by huanglin on 2017/7/19.
 */
$(function () {
    $("#login").click(function () {

        //获取用户类型
        var loginName = $("#username").val();
        var role = "";
        //获取radio
        var radio = document.getElementById("form").role;
        for (var i = 0; i < radio.length; i++) {
            if (radio[i].checked) {//被选择
                role = radio[i].value
            }
        }


        $.ajax({
                type: "POST",
                url: '/login',
                data: $("#form").serialize(),
                // dataType:"json",
                success: function (data) {
                    // console.log(data.result);
                    if (data.toString() == '登陆成功') {
                        if (role == 'staff') {
                            window.location = "index.html?loginName=" + loginName + "&&role=" + role;
                        }
                        if (role == 'admin') {
                            window.location = "indexAdmin.html?loginName=" + loginName + "&&role=" + role;
                        }

                    } else {
                        alert(data)
                    }
                }
            }
        );
    })

    $("#register").click(function () {

        var logName = $("#username").val();
        var passWord = $("#password").val();
        var val = $('input:radio[name="staff"]:checked').val();
        var val1 = $('input:radio[name="admin"]:checked').val();
        if (logName == "") {
            alert("请输入用户名！！！");
            return;
        }
        if (passWord == "") {
            alert("请输入密码！！！");
            return;
        }
        if (val == null || val1 == null) {
            alert("请选择用户类型！！！");
            return;
        }
        $.ajax({
            type: "POST",//请求方式
            url: "/register",//地址，就是action请求路径
            async: true, //设置请求是同步还是异步//同步请求将锁住浏览器，用户的其他操作必须等待请求后才可以执行
            data: $("#form").serialize(),//参数序列化
            //dataType: "json",//数据类型text xml json  script  jsonp
            success: function (msg) {
                if (msg.toString() == '注册成功')
                    window.location = "login.html";
                else
                    document.getElementById("errorMsg").innerHTML = msg.toString();
            }
        });
    })


});
