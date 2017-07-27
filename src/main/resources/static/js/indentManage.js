/**
 * Created by admin on 2017/7/26.
 */

//




$(function () {


   //城市改变时触发
    $("#city").change(function () {

       var cityCode=$("#city option:selected").val();
        if(cityCode=='undefined'){
            cityCode='0';
        }

       var indentState=$("#indentState option:selected").text();
        if(indentState=='---请选择--'){
            indentState='0';
        }

        //清空table数据
        $("#Table").children('table').empty();
        // $("#Table").simplePagination.pagination('destroy');
        $("#Table").children('div .page-nav').remove();
        $("#Table").children('div .addon').remove();

        $("#Table").table({
            url: '/findIndent?indentState='+indentState+'&&cityCode='+cityCode,
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
                    title:'电池型号',
                    data:'batteryType'
                },
                {
                    title:'客户姓名',
                    data:'customerName'
                },
                {
                    title:'手机号码',
                    data:'customerCellphone'
                },
                {
                    title:'客户电话',
                    data:'customerTelephone'
                },
                {
                    title:'客户电话',
                    data:'customerTelephone'
                },
                {
                    title:'微信号',
                    data:'wechatId'
                },
                {
                    title:'地址',
                    data:'address'
                },
                {
                    title:'车型',
                    data:'automobileType'
                },
                {
                    title:'车牌号',
                    data:'licensePlateNumber'
                },
                {
                    title:'城市编号',
                    data:'cityCode'
                },
                {
                    title:'下单时间',
                    data:'createTime'
                },
                {
                    title:'完成时间',
                    data:'finishTime'
                },
                {
                    title:'订单状态',
                    data:'status'
                },
                {
                    title:'备注',
                    data:'remark'
                },




            ]
        })







    });

    //订单状态改变时触发
    $("#indentState").change(function () {

        var cityCode=$("#city option:selected").val();
        var indentState=$("#indentState option:selected").text();
        if(indentState=='---请选择--'){
            indentState="0";
        }

        //清空table数据
        $("#Table").children('table').empty();
        // $("#Table").simplePagination.pagination('destroy');
        $("#Table").children('div .page-nav').remove();
        $("#Table").children('div .addon').remove();

        $("#Table").table({
            url: '/findIndent?indentState='+indentState+'&&cityCode='+cityCode,
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
                    title:'电池型号',
                    data:'batteryType'
                },
                {
                    title:'客户姓名',
                    data:'customerName'
                },
                {
                    title:'手机号码',
                    data:'customerCellphone'
                },
                {
                    title:'客户电话',
                    data:'customerTelephone'
                },
                {
                    title:'客户电话',
                    data:'customerTelephone'
                },
                {
                    title:'微信号',
                    data:'wechatId'
                },
                {
                    title:'地址',
                    data:'address'
                },
                {
                    title:'车型',
                    data:'automobileType'
                },
                {
                    title:'车牌号',
                    data:'licensePlateNumber'
                },
                {
                    title:'城市编号',
                    data:'cityCode'
                },
                {
                    title:'下单时间',
                    data:'createTime'
                },
                {
                    title:'完成时间',
                    data:'finishTime'
                },
                {
                    title:'订单状态',
                    data:'status'
                },
                {
                    title:'备注',
                    data:'remark'
                },
            ]
        })
    });




});