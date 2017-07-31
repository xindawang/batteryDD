/**
 * Created by huanglin on 2017/7/26.
 */
$(function () {


    $.ajax({
        type: "POST",
        url: "/createMenu",
        data: null,
        dataType:"text",
        success:function(data) {
            document.getElementById("successText").innerHTML="菜单创建成功"

        },
        error:function(XMLResponse){
            alert(XMLResponse.responseText)
        }

    })
});
