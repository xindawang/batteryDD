/**
 * Created by huanglin on 2017/7/20.
 */
$(function () {

    $('.required').before('<span style="color:red">*</span>')
    $('.unrequired').before('<span style="visibility: hidden">*</span>')
    //省列表
    $.ajax({
        type:"POST",
        url:"/importProvince",
        data:null,
        dataType:"json",
        success:function(data){
            $('#province').empty();
            $('#province').append("<option value= 0>---请选择--</option>");
            for (var i=0;i<data.length;i++){

                $("#province").append("<option value=" + data[i].provinceCode + ">" + data[i].provinceName + "</option>")
            }
        }

    });
   $("#province").change(function () {
       $.ajax({
           type: "POST",
           url: "/importCity",
           data: {"provinceCode": $("#province option:selected").val()},
           dataType: 'json',
           success: function (data) {
               $('#city').empty();//清空下拉列表
               $('#city').append("<option value= 0>---请选择--</option>");
               for (var i in data) {
                   var cityCode = data[i].cityCode;
                   var cityName = data[i].cityName;
                   $('#city').append("<option value=" + cityCode + ">" + cityName + "</option>");
               }
           }


       });
   })

    $("#city").change(function () {
        $.ajax({
            type:"POST",
            url: "/importArea",
            data: {"cityCode": $("#city option:selected").val()},
            dataType: "json",
            success:function(data){
                $('#area').empty();//清空下拉列表
                $('#area').append("<option value= 0>---请选择--</option>");
                for(var i in data){
                    var areaCode=data[i].areaCode;
                    var areaName=data[i].areaName;
                    $('#area').append("<option value="+areaCode+">"+areaName+"</option>");
                }
            }
        });

    });

    $.ajax({
        type:"POST",
        url:"/importAutoBrand",
        data:null,
        dataType:"json",
        success:function(data){
            $("#automobileBrand").empty()
            $("#automobileBrand").append("<option value= 0>---请选择--</option>")
            for (var i=0;i<data.length;i++){
                $('#automobileBrand').append("<option value="+data[i].id+">"+data[i].brandName+"</option>");

            }
        }

    });

    $("#automobileBrand").change(function () {
        $.ajax({
            type:"POST",
            url: "/importAutoType",
            data: {"automobileBrand": $("#automobileBrand option:selected").val()},
            dataType: "json",
            success:function(data){
                $('#automobileType').empty();//清空下拉列表
                $('#automobileType').append("<option value= 0>---请选择--</option>");
                for(var i in data){
                    var id=data[i].id;
                    var name=data[i].name;
                    $('#automobileType').append("<option value="+id+">"+name+"</option>");
                }
            }
        });

    });


    $.ajax({
        type:"POST",
        url:"/importBatteryBrand",
        data:null,
        dataType:"json",
        success:function(data){
            $("#batteryBrand").empty()
            $('#batteryBrand').append("<option value= 0>---请选择---</option>")
            for (var i=0;i<data.length;i++){
                var id=data[i].id;
                var brandName=data[i].brandName;
                $('#batteryBrand').append("<option value="+id+">"+brandName+"</option>");

            }
        }

    });

    $("#batteryBrand").change(function () {
        $.ajax({
            type:"POST",
            url: "/importBatteryType",
            data: {"batteryBrand": $("#batteryBrand option:selected").val()},
            dataType: "json",
            success:function(data){
                $('#batteryType').empty();//清空下拉列表
                $('#batteryType').append("<option value= 0>---请选择---</option>");
                for(var i in data){
                    var id=data[i].id;
                    var type=data[i].type;
                    $('#batteryType').append("<option value="+id+">"+type+"</option>");
                }
            }
        });

    });

    $('#import').click(function(){
        var submit=true
        $(".necessary").each(function () {
            if((!$(this).val()) || ($(this).val()==0) ) {
                submit=false
                window.confirm($(this).attr("data-id")+"不能为空")
            }

        })
        if(isNaN(Number($("#cellphone").val()))){
            window.confirm("移动电话存在非法字符，请检查")
            submit=false
        }

        if(parseInt($("#telephone").val())== NaN){
            window.confirm("固定电话存在非法字符，请检查")
            submit=false
        }
        var re =  /^[0-9a-zA-Z]*$/g;  //判断字符串是否为数字和字母组合     //判断正整数 /^[1-9]+[0-9]*]*$/
        if (!re.test($("#orderId").val()))
        {
            window.confirm("订单编号中存在非法字符，请检查")
            submit=false
        }
        if(!( $("#wyes").attr("checked") || $("#wno").attr("checked"))){
            window.confirm("请选择用户是否从微信端申请服务")
            submit=false
        }
        var wechatStatus1=$("#wyes").attr("checked")?1:0
            if(submit) {
            $.ajax({
                type: "POST",
                url: "/importOrder",
                data: $('#form').serialize()+"&wechatStatus1="+wechatStatus1,
                dataType: "json",
                success: function (data) {
                    //清空表清单

                    $(':input', '#form')
                        .not(':button, :submit, :reset, :hidden, :radio')
                        .val('')
                        .removeAttr('checked')
                        .removeAttr('selected');
                    window.confirm(data)

                }
            })
        }

    });
});
