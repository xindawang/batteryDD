/**
 * Created by huanglin on 2017/7/24.
 */
$(function () {
    var aa =document.getElementById("undoneIndent")
   $.ajax({
       url:"/selectIndentMsg",
       type:"POST",
       data:null,
       dataType:"json",
       success:function (data) {
           // document.getElementById("undoneIndent").options[0]=new Option("---请选择---",0);
           for(var i=0;i<data.length;i++){
               var ii=i+1;
               //使用 $("#undoneIndent")会报错
              //document.getElementById("undoneIndent").options[ii]=new Option(data[i],data[i]);
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

    distributeMap("container",116.481181,39.989792);

});
