/**
 * Created by huanglin on 2017/7/20.
 */
$(function () {
    //省列表
    $.ajax({
        type:"POST",
        url:"/importProvince",
        data:null,
        dataType:"json",
        success:function(data){
            document.getElementById("province").options[0]=new Option("---请选择---",0);
            for (var i=0;i<data.length;i++){
                var ii =i+1;
                document.getElementById("province").options[ii]=new Option(data[i].provinceName,data[i].provinceCode);
                // var code = data[i].provinceCode
                // var name = data[i].provinceName
                // $('#province').append("<option value=" + code + ">" + name +"</option>");
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
               $('#city').append("<option value= 0>---请选择--</option>")
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
            document.getElementById("automobileBrand").options[0]=new Option("---请选择---",0);
            for (var i=0;i<data.length;i++){
                var ii =i+1;
                document.getElementById("automobileBrand").options[ii]=new Option(data[i].brandName,data[i].id);
                // var code = data[i].provinceCode
                // var name = data[i].provinceName
                // $('#province').append("<option value=" + code + ">" + name +"</option>");
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
            document.getElementById("batteryBrand").options[0]=new Option("---请选择---",0);
            for (var i=0;i<data.length;i++){
                var ii =i+1;
                document.getElementById("batteryBrand").options[ii]=new Option(data[i].brandName,data[i].id);
                // var code = data[i].provinceCode
                // var name = data[i].provinceName
                // $('#province').append("<option value=" + code + ">" + name +"</option>");
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
                $('#batteryType').append("<option value= 0>---请选择--</option>");
                for(var i in data){
                    var id=data[i].id;
                    var type=data[i].type;
                    $('#batteryType').append("<option value="+id+">"+type+"</option>");
                }
            }
        });

    });

    $('#import').click(function(){
        $.ajax({
            type:"POST",
            url:"/importOrder",
            data:$('#form').serialize(),
            dataType: "json",
            success:function (data) {

            }
        })


    });
});