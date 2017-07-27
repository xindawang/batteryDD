/**
 * Created by admin on 2017/7/26.
 */

$(function () {
    $.ajax({
        type:"POST",
        url:"/indentState",
        dataType:"json",
        success: function (data) {
            $('#indentState').empty();//清空下拉列表
            $('#indentState').append("<option value= 0>---请选择--</option>");
            for (var i in data) {
                var id = data[i].id;
                var state = data[i].state;
                $('#indentState').append("<option value=" +id+ ">" + state + "</option>");
            }
        }

    });

});