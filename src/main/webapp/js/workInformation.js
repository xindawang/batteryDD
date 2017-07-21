/**
 * Created by admin on 2017/7/20.
 */
$(function () {
    $("#submit").click(function () {
        $.ajax({
                type:"POST",
                url: '/modifyInformation',
                data:$("#form").serialize(),
                //dataType:text,
                success:function (data) {
                        document.getElementById("showMsg").innerHTML=data.toString();
                }
            }
        );
    })
});

//查询用户信息
$(function () {
    $.ajax({
        type: "POST",
        url: '/queryInformation',
        dataType : 'json',
        data:$("#form").serialize(),
        success:function (data) {

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
        error:function () {
            window.alert("信息初始化失败！！");
        }
    });
    }


)