/**
 * Created by huanglin on 2017/7/24.
 */
$(function () {

   $.ajax({
       url:"/selectIndentMsg",
       type:"POST",
       data:null,
       dataType:"json",
       success:function (data) {
            document.getElementById("undoneIndent").options[0]=new Option("---请选择---",0);
           for(var i=0;i<data.length;i++){
               var ii=i+1;
               //使用 $("#undoneIndent")会报错
              document.getElementById("undoneIndent").options[ii]=new Option(data[i],data[i]);
               }


       }
   })

    //创建以北京为中心的全国地图
    var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [116.397428, 39.90923],//地图中心
        zoom: 5
    });


    $("#undoneIndent").change(function () {
        //通过orderId获取用户和订单的信息
        $.ajax({
            url:"/importIndentMsg",
            type:"POST",
            data:{"indentId": $("#undoneIndent option:selected").val()},
            dataType:"json",
            success:function (data) {
                document.getElementById("customerName").value=data[0].customerName;
                $("#wechatId").val(data[0].wechatId);
                $("#telephone").val(data[0].customerTelephone);
                $("#cellphone").val(data[0].customerCellphone);
                $("#automobileType").val(data[0].automobileType);
                $("#licensePlateNumber").val(data[0].licensePlateNumber);
                $("#createTime").val(data[0].createTime);
                $("#batteryType").val(data[0].batteryType);
                $("#address").val(data[0].address);


            }
        })

        var cusLongitude;
        var cusLatitude;
        //通过orderIda获取用户的地理位置作为位置中间
        $.ajax({
            url:"/getCustomerLocation",
            type:"POST",
            dataType:"json",
            data:{"orderId": $("#undoneIndent option:selected").val()},
            success:function (data) {
                cusLongitude=data.cusLongitude
                cusLatitude=data.cusLatitude

                //在获取用户位置后更新地图的中心点和放大级别
                map.setZoom(10)
                map.setCenter([cusLongitude,cusLatitude])


                //设置用户的图标
                var icon = new AMap.Icon({
                    image: '../bgimg/user.png',
                    size: new AMap.Size(24,24)
                });
                marker = new AMap.Marker({
                    icon: icon,
                    position: [cusLongitude, cusLatitude],
                    offset: new AMap.Pixel(-12,-12),
                    zIndex: 101,
                    title: '用户位置',
                    map: map
                });
            }
        })






        var Atitle='赵四<span style="font-size:11px;color:#F00;">电话:1316462138</span>'
        var AdetailedMsg="评价：5分<br/>性别：男"

        distributeTechMap(map,114.380,30.63,Atitle,AdetailedMsg);
        var Btitle='李六<span style="font-size:11px;color:#F00;">电话:10086</span>'
        var BdetailedMsg="评价：5分<br/>性别：男"
        distributeTechMap(map,114.270,30.63,Btitle,BdetailedMsg);


    });








    
    



});
