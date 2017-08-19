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

        var userMarkers=[] //保存marker  地图点标记

        var indentId=$("#undoneIndent option:selected").val()
        //通过orderId获取用户和订单的信息
        $.ajax({
            url:"/importIndentMsg",
            type:"POST",
            data:{"indentId": indentId},
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

        //显示indent按钮
        var string=$("#undoneIndent option:selected").text();
        if(string=='---请选择---'){
            $("#indentDetail").hide();
        }
        else {
            $("#indentDetail").show();
        }
        var cusLongitude;
        var cusLatitude;
        //通过orderIda获取用户的地理位置作为位置中间
        $.ajax({
            url:"/getCustomerLocation",
            type:"POST",
            dataType:"json",
            data:{"orderId": indentId},
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
                userMarkers.push(marker)
            }
        })

        //更新可选择的技师信息-通过cityCode
        var techCellphone
        var techName
        var techLongitude
        var techLatitude
        var techSex
        var techId
        $.ajax({
            url:"/importTechMsgFromCity",
            type:"POST",
            data:{"indentId":indentId},
            dataType:"json",
            success:function (data) {
                $("#technician").empty()
                $("#technician").append("<option value=0>---请选择---</option>")
                for(var i in data){
                    techName=data[i].name
                    techLongitude=data[i].longitude
                    techLatitude=data[i].latitude
                    techId=data[i].technicianId
                    techSex=data[i].sex
                    techCellphone=data[i].cellphone

                    $("#technician").append('<option value='+techId+'>'+techName+'-'+techId+'</option>')

                    var title=setTechTitle(techName,techId)
                    var msg=setTechMsg(techCellphone,techSex)

                    distributeTechMap(map,techLongitude,techLatitude,title,msg);

                }
            }
        })
        $("#technician").change(function () {
            var selectedTechMsg=$("#technician option:selected").text()
            var selectedTechId=$("#technician option:selected").val()
            console.log(selectedTechId)
            if($("#technician").val()!=0){
                $.mbox({
                    area: [ "450px", "auto" ], //弹框大小
                    border: [ 0, .5, "#666" ],
                    title:"订单派发确认",
                    dialog: {
                        msg: "订单编号："+indentId+'<br/>'+'派发给'+'<br/>'+selectedTechMsg,
                        btns: 2,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
                        type: 2,   //1:对钩   2：问号  3：叹号
                        btn: [ "确认", "取消"],  //自定义按钮
                        yes: function() {  //点击左侧按钮：成功
                            $.ajax({
                                url:"/allocationIndent",
                                type:"POST",
                                data:{"techId":selectedTechId,"indentId":indentId},
                                dataType:"json",
                                success:function () {

                                }
                            })

                            map.remove(userMarkers)
                            map.remove(techMarkers)
                            map.setZoom(5)
                            map.setCenter([116.397428, 39.90923])
                            console.log("test moveMarker")
                        },
                        no: function() { //点击右侧按钮：失败
                            return false;
                        }
                    }
                })
            }
        })





    });

   //点击‘订单信息’，则显示信息
    $("#DetailShow").click(function () {
        $("#indentDetailShow").show();
    });
    $("#cancel").click(function () {
        $("#indentDetailShow").hide();
    });

    //将获取的技师信息进行拼接用于派单地图显示
    function setTechTitle(name,techId) {
        return name+'<b style="color:#F00;">编号:'+techId+'<b>'
    }

    function setTechMsg(cellphone,sex) {
        return '电话号码：'+cellphone+'<br/>'+'性别：'+sex
    }





    
    



});
