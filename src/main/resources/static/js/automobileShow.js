/**
 * Created by admin on 2017/9/25.
 */
$(function () {
    //清空table旧数据
    $("#Table").children('table').empty();
    // $("#Table").simplePagination.pagination('destroy');
    $("#Table").children('div .page-nav').remove();
    $("#Table").children('div .addon').remove();

    document.getElementById("caption").textContent = "汽车相关信息";
    $('#Table').table({
        url: '/automobile',
        type: "get",
        columns: [
            {
                title: '序号',
                increment: 1,
                width: '60px'
            },
            {
                title: '汽车品牌',
                data: 'automobileBrand',
                width: '100px'
            },
            {
                title: '汽车型号',
                data: 'automobileType',
                width: '100px'
            },
            {
                title: '适用电池',
                data: 'batteryType',
            },
            {
                title: '操作',
                element: "<a href='javascript:;' onclick='return editInfo(this)'  class='layui-btn layui-btn-mini'>编辑</a>" +
                "<a href='javascript:;' onclick='return deleteInfo(this)' class='layui-btn layui-btn-danger layui-btn-mini'>删除</a>",
                width: '50x'
            }
        ]
    })


    $("#addCar").click(function () {
        AddMsg();
    });

});

//显示弹框-添加汽车
function AddMsg() {
    $.mbox({
        area: ["300px", "auto"], //弹框大小
        border: [0, .5, "#666"],
        title: "添加汽车",
        dialog: {
            msg: "<div>汽车品牌：" + '<input type="text" maxlength="10" id="carBrand"  style="width:140px" name="carBrand">' + '<br/>' + "汽车型号：" + '<input type="text" id="carType" maxlength="10"  style="width: 140px" name="carType">' + '</div>',
            btns: 2,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
            type: 1,   //1:对钩   2：问号  3：叹号
            btn: ["确定","取消"],  //自定义按钮
            yes: function () {  //点击左侧按钮：成功
                var cBrand = $("#carBrand").val().replace(/\s/g, "");
                var cType = $("#carType").val().replace(/\s/g, "");
                submit(cBrand, cType);
            },
            no:function () {

            }


        }
    })
}

function submit(cBrand, cType) {
    if (cBrand == '') {
        alert("品牌名称不能为空，添加失败");
        return;
    }
    if (cType == '') {
        alert("车型名称不能为空，添加失败");
        return;
    }
    $.ajax({
        type: "get",
        url: "/carTypeAdd",
        data: {
            brand: cBrand,
            carType: cType,
        },
        success: function (data) {
            alert(data);
        }
    });
}


//删除信息

//获取表格行对象
function getRowObj(obj) {
    while (obj.tagName.toLowerCase() != "tr") {
        obj = obj.parentNode;
        if (obj.tagName.toLowerCase() == "table") {
            return null;
        }
    }
    return obj;
}


//删除一个记录后，再返回剩下的所有记录
function deleteInfo(obj) {
   //var personType = $("#type").val();// 获取类型 customer/admin/staff/technician
    var tr = getRowObj(obj);
    var m = tr.rowIndex;
    m = m - 1;
    var brand = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(1)').text();
    var CarType = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(2)').text();
    if (window.confirm("确定删除:"+brand+CarType+" 这个车型吗？")) {
        $.ajax({
            type: "post",
            url: "/automobileDelete",
            data: {
                brand: brand,
                carType: CarType,
            },
            success: function (data) {
                alert(data);
                tr.parentNode.removeChild(tr);//移除该行
            }
        });
    }
};


//编辑

function editInfo(obj) {

    var personType = $("#type").val();// 获取类型 customer/admin/staff/technician
    var tr = getRowObj(obj);
    var m = tr.rowIndex;
    m = m - 1;
    var brand = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(1)').text();
    var type = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(2)').text();
    window.location.href = "automobileComplete.html?brand=" + encodeURIComponent(brand) + "&carType=" + encodeURIComponent(type);
}