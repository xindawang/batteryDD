/**
 * Created by admin on 2017/7/26.
 */

//




$(function () {


   //城市改变时触发
    $("#city").change(function () {

       var cityCode=$("#city option:selected").val();
       var cityName=$("#city option:selected").text();
       var indentState=$("#indentState option:selected").val();

       alert(cityCode);
       alert(cityName);
       alert(indentState);

    });

    //订单状态改变时触发
    $("#indentState").change(function () {

        var cityCode=$("#city option:selected").val();
        var cityName=$("#city option:selected").text();
        var indentState=$("#indentState option:selected").val();

        alert(cityCode);
        alert(cityName);
        alert(indentState);
    });




});