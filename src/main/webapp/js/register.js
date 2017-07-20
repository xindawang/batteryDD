/**
 * Created by huanglin on 2017/7/19.
 */
$(function () {
    document.getElementById("name").innerHTML="newName";
    $("#submit").click(function () {
        $.ajax({
            type: "POST",//请求方式
            url: "/register",//地址，就是action请求路径
            async:true, //设置请求是同步还是异步//同步请求将锁住浏览器，用户的其他操作必须等待请求后才可以执行
            data:$("#form").serialize(),//参数序列化
            //dataType: "json",//数据类型text xml json  script  jsonp
            success: function (msg) {
                if(msg.toString()=='注册成功')
                    window.location="login.html";
                else
                    document.getElementById("errorMsg").innerHTML=msg.toString();
            }
        });
    })
});