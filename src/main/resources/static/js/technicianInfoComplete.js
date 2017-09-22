/**
 * Created by admin on 2017/7/24.
 */

$(function () {
    //显示初始信息
    var userName=GetQueryString('loginName');
    $.ajax({
        type:"post",
        url:"/findOneTechnician",
        data:{
            loginName:userName,
        },
        dataType: "json",
        success:function(data) {
            $("#technicianId").val(data.technicianId);
            $("#loginName").val(data.loginName);
            $("#password").val(data.password);
            $("#name").val(data.name);
            $("#sex").val(data.sex);
            $("#cellphone").val(data.cellphone);
            $("#telephone").val(data.telephone);
            $("#email").val(data.email);
            $("#idNumber").val(data.idNumber);
            $("#address").val(data.address);
            $("#licensePlateNumber").val(data.licensePlateNumber);
            $("#organizationId").val(data.organizationId);

        }
    });


//提交完善后的信息
    $("#submit").click(function () {
        var tID = $("#technicianId").val();
        var tLoginName = $("#loginName").val();
        var tName = $("#name").val();
        var password = $("#password").val();
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
        $.ajax({
                type:"POST",
                url: '/technicianModify',
                data:$("#form").serialize(),
                //dataType:text,
                success:function (data) {
                    alert(data.toString());
                }
            }
        );
    })


    //地址规范化
    $("#address").click(function () {
        $("#address1").hide();
        $("#address2").show();
        $("#address3").show()
    });

    $("#finish").click(function () {

        var ss = $("#province option:selected").text()+ $("#city option:selected").text()+
            $("#area option:selected").text()+ $("#detailAddress").val();
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
