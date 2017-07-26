/**
 * Created by admin on 2017/7/25.
 */


$(function () {

    $.ajax({
        type:"POST",
        url: "/sexFind",
        dataType: "json",
        success:function(data){
            $('#sexType').empty();//清空下拉列表
            for(var i in data){
                var id=data[i].id;
                var sex=data[i].sex;
                $('#sexType').append("<option value="+id+">"+sex+"</option>");
            }
        }
    });

});