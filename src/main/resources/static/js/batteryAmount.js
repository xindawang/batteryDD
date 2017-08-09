/**
 * Created by admin on 2017/7/24.
 */


$(function () {

    //按城市查看
    $("#city").change(function () {

        //清空table数据
        $("#Table").children('table').empty();
        // $("#Table").simplePagination.pagination('destroy');
        $("#Table").children('div .page-nav').remove();
        $("#Table").children('div .addon').remove();
        var cityCode=$("#city option:selected").val();
        $("#Table").table({
            url: '/batteryAmountCity?cityCode='+cityCode,
            type: "get",
            columns: [
                {
                    title: '序号',
                    increment: 1,
                    width: '50px'
                },
                {
                    title:'电池型号',
                    data:'battery.type'
                },
                {
                    title:'城市',
                    data:'city.cityName'
                },
                {
                    title:'电池库存量',
                    data:'inventory'
                },

            ]
        })



    });

 //按型号查看

    $("#batteryType").change(function () {

        //清空table数据
        $("#Table").children('table').empty();
        // $("#Table").simplePagination.pagination('destroy');
        $("#Table").children('div .page-nav').remove();
        $("#Table").children('div .addon').remove();


        var batteryType=$("#batteryType option:selected").val();

        $("#Table").table({
            url: '/batteryAmountType?batteryType='+batteryType,
            type: "get",
            columns: [
                {
                    title: '序号',
                    increment: 1,
                    width: '50px'
                },
                {
                    title:'电池型号',
                    data:'battery.type'
                },
                {
                    title:'城市',
                    data:'city.cityName'
                },
                {
                    title:'电池库存量',
                    data:'inventory'
                },
                {
                    title:'编辑',
                    element:"<a href='javascript:;' onclick='return editInfo(this)'  class='layui-btn layui-btn-mini'>编辑</a><a href='javascript:;' onclick='return deleteInfo(this)' class='layui-btn layui-btn-danger layui-btn-mini'>删除</a>"
                }
            ]
        })
    });

    //增加库存

});



//删除信息

//获取表格行对象
function getRowObj(obj)
{
    while(obj.tagName.toLowerCase()!= "tr")
    {    obj = obj.parentNode;
        if(obj.tagName.toLowerCase() == "table") {
            return null;
        }
    }
    return obj;
}


//删除一个记录后，再返回剩下的所有记录
function deleteInfo(obj){
    var tr=getRowObj(obj);
    var m=tr.rowIndex;
    m=m-1;
    var batteryType=$("#tb>tbody").find('tr:eq('+m+') td:eq(1)').text();
    var cityName=$("#tb>tbody").find('tr:eq('+m+') td:eq(2)').text();

    $.ajax({
        type:"post",
        url:"/batteryAmountDelete",
        data:{
            batteryType:batteryType,
            cityName:cityName,
        },
        success:function(data) {
            alert(data);
            tr.parentNode.removeChild(tr);//移除该行
        }
    });
};


//编辑

function editInfo(obj) {

    var tr = getRowObj(obj);
    var m = tr.rowIndex;
    m = m - 1;
    var batteryType = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(1)').text();
    var cityName = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(2)').text();
    var batteryCount = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(3)').text();


}