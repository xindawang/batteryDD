/**
 * Created by huanglin on 2017/7/19.
 */
$(function () {
    $("#submit").click(function () {
        $.ajax({
            type: "POST",
            url: '/login',
            data:$("form").serialize(),
            //dataType:text,
            success:function (data) {
                if(data.toString()=='登陆成功'){
                    window.location="index.html";
                }else{
                    document.getElementById("errorMsg").innerHTML=data.toString();
                }


            }
            }
        );
    })
});
