/**
 * Created by admin on 2017/7/31.
 */


$(function () {


//获取转发记录订单编号
    $.ajax({
        url:"/orderId",
        type:"POST",
        data:null,
        dataType:"json",
        success:function (data) {
            document.getElementById("orderId").options[0]=new Option("---请选择---",0);
            for(var i=0;i<data.length;i++){
                var ii=i+1;
                //使用 $("#undoneIndent")会报错
                document.getElementById("orderId").options[ii]=new Option(data[i],data[i]);
            }
        }
    })
    //获取转发记录订单编号

    $.ajax({
        url:"/technicianId",
        type:"POST",
        data:null,
        dataType:"json",
        success:function (data) {
            document.getElementById("technicianId").options[0]=new Option("---请选择---",0);
            for(var i=0;i<data.length;i++){
                var ii=i+1;
                //使用 $("#undoneIndent")会报错
                document.getElementById("technicianId").options[ii]=new Option(data[i],data[i]);
            }
        }
    })




    //选择订单编号

    $("#orderId").change(function () {

        //清空table数据
        $("#Table").children('table').empty();
        // $("#Table").simplePagination.pagination('destroy');
        $("#Table").children('div .page-nav').remove();
        $("#Table").children('div .addon').remove();

        var orderId=$("#orderId option:selected").val();

        $("#Table").table({
            url: '/findByOrderId?orderId='+orderId,
            type: "get",
            columns: [
                {
                    title: '序号',
                    increment: 1,
                    width: '50px'
                },
                {
                    title:'订单编号',
                    data:'orderId'
                },
                {
                    title:'技师名称',
                    data:'technician.name'
                },
                {
                    title:'接单时间',
                    data:'acceptTime'
                },

            ]
        })
    });



    $("#technicianId").change(function () {

        //清空table数据
        $("#Table").children('table').empty();
        // $("#Table").simplePagination.pagination('destroy');
        $("#Table").children('div .page-nav').remove();
        $("#Table").children('div .addon').remove();

        var orderId=$("#technicianId option:selected").val();

        $("#Table").table({
            url: '/findByTchnicianId?orderId='+orderId,
            type: "get",
            columns: [
                {
                    title: '序号',
                    increment: 1,
                    width: '50px'
                },
                {
                    title:'订单编号',
                    data:'orderId'
                },
                {
                    title:'技师名称',
                    data:'technician.name'
                },
                {
                    title:'接单时间',
                    data:'acceptTime'
                },

            ]
        })
    });



});