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


    $("#undoneIndent").change(function () {
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

    });

    var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [114.418, 30.509],
        zoom: 10
    });

    var icon = new AMap.Icon({
        image: 'https://vdata.amap.com/icons/b18/1/2.png',
        size: new AMap.Size(24, 24)
    });
    marker = new AMap.Marker({
        icon: icon,
        position: [114.418, 30.509],
        offset: new AMap.Pixel(-12,-12),
        zIndex: 101,
        title: '用户地理位置',
        content:'<p>华中科技大学</p>',
        map: map
    });
    var Atitle='赵四<span style="font-size:11px;color:#F00;">电话:1316462138</span>'
    var AdetailedMsg="评价：5分<br/>性别：男"

    distributeTechMap(map,114.380,30.63,Atitle,AdetailedMsg);
    var Btitle='李六<span style="font-size:11px;color:#F00;">电话:10086</span>'
    var BdetailedMsg="评价：5分<br/>性别：男"
    distributeTechMap(map,114.270,30.63,Btitle,BdetailedMsg);
});
