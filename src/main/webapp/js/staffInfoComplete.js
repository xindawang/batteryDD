/**
 * Created by admin on 2017/7/24.
 */




$(function () {

    //显示初始信息
    var userName=GetQueryString('loginName');
    $.ajax({
        type:"post",
        url:"/findOneStaff",
        data:{
            loginName:userName,
        },
        dataType: "json",
        success:function(data) {

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
            $("#organizationId").val(data.organizationId);

        }
    });


//提交完善后的信息
    $("#submit").click(function () {
        $.ajax({
                type:"POST",
                url: '/staffModify',
                data:$("#form").serialize(),
                //dataType:text,
                success:function (data) {
                    document.getElementById("showMsg").innerHTML=data.toString();
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


    //性别规范化
    $("#sex").click(function () {
        $("#sex1").hide();
        $("#sex2").show();
    });
    $("#sexType").mouseleave (function () {
        var sex= $("#sexType option:selected").text()
        $("#sex").val(sex);
        $("#sex1").show();
        $("#sex2").hide();
    });




});



//取地址中的参数
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}