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
        alert(cityCode);

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
                    data:'type'
                },
                {
                    title:'城市',
                    data:'cityName'
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
                    data:'type'
                },
                {
                    title:'城市',
                    data:'cityName'
                },
                {
                    title:'电池库存量',
                    data:'inventory'
                },

            ]
        })




    });



});