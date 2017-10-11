/**
 * Created by admin on 2017/7/24.
 */
var batteryAmountArray = [
    {
        title: '序号',
        increment: 1,
        width: '50px'
    }, {
        title: '省份',
        data: 'provinceName'
    }, {
        title: '城市',
        data: 'cityName'
    }, {
        title: '电池品牌',
        data: "batteryBrand"
    },
    {
        title: '电池型号',
        data: 'batteryType'
    },
    {
        title: '电池库存量',
        data: 'inventory'
    },
    {
        title: '操作',
        //element: "<button onclick='return deleteInfo(this)'>删除</button><button onclick='return editInfo(this)'>编辑</button>"
        element: "<a href='javascript:;' onclick='return editInfo(this)'  class='layui-btn layui-btn-mini'>编辑</a>" +
        "<a href='javascript:;' onclick='return deleteInfo(this)' class='layui-btn layui-btn-danger layui-btn-mini'>删除</a>",
        width: '100px'

    }]

$(function () {

    getAllProvince()
    updateStockTable("/getAllBatteryAmount")
    //--------------------------事件监听 以下-----------------------------------
    //按城市查看
    $("#city").change(function () {
        var cityCode = $("#city").val()
        var provinceCode=$("#province").val()
        if(provinceCode ==0 && cityCode==0)
            cityCode=-1 //表明查询全部省份的全部
        updateStockTable("/getBatteryAmountByCity?cityCode=" + cityCode)

    });


    $("#province").change(function () {
        var provinceCode=$("#province").val()
        if(provinceCode == 0){
            updateStockTable("/getAllBatteryAmount")
        }
            getAllCityByProvince()


    })


    // $("#addAmount").click(function () {
    //     // console.log("test")
    //     // alert("hello")
    //     //window.location.href="batteryAmountAdd.html"
    //
    // })

    //增加库存

});
//------------------------事件监听 以上-----------------------------------
 //按型号查看


function updateStockTable(url) {
    //清空table数据
    $("#Table").children('table').empty();
    // $("#Table").simplePagination.pagination('destroy');
    $("#Table").children('div .page-nav').remove();
    $("#Table").children('div .addon').remove();
    $("#Table").table({
        url: url,
        type: "get",
        columns: batteryAmountArray
    })
}

//删除信息

//获取表格行对象
function getRowObj(obj)
{
    while(obj.tagName.toLowerCase()!= "tr")
    {    obj = obj.parentNode;
        if(obj.tagName.toLowerCase() == "table") {
            return null;
        }
    }
    return obj;
}


//删除一个记录后，再返回剩下的所有记录
function deleteInfo(obj){
    var tr = getRowObj(obj)
    var m = tr.rowIndex - 1
    var batteryType = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(4)').text()
    var cityName = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(2)').text()
    var province = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(1)').text()
    deleteStockConfirm(batteryType, cityName, province, tr)
};

function deleteBatteryStock(batteryType, cityName, province, tr) {
    $.ajax({
        type: "post",
        url: "/batteryAmountDelete",
        data: {
            "batteryType": batteryType,
            "cityName": cityName,
            "province": province
        },
        success: function (data) {
            window.confirm(data);
            tr.parentNode.removeChild(tr);//移除该行
        }
    });
}
//编辑

function deleteStockConfirm(batteryType, cityName, province, tr) {
    $.mbox({
        area: ["300px", "auto"], //弹框大小
        border: [0, .5, "#666"],
        title: "删除库存",
        dialog: {
            msg: "<p style='color:red'>确认删除" + province + cityName + "电池型号为：" + batteryType + "的库存？</p>",

            btns: 2,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
            type: 2,   //1:对钩   2：问号  3：叹号
            btn: ["确认删除", "取消"],  //自定义按钮
            yes: function () {  //点击左侧按钮：成功

                deleteBatteryStock(batteryType, cityName, province, tr)

            },
            no: function () {

            }

        }
    })
}
function editInfo(obj) {

    var tr = getRowObj(obj);
    var m = tr.rowIndex - 1;

    var batteryType = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(4)').text()
    var cityName = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(2)').text()
    var province = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(1)').text()
    var stock = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(5)').text()
    editBatteryStock(batteryType, cityName, province, stock)
}

function editBatteryStock(batteryType, cityName, province, stock) {
    $.mbox({
        area: ["500px", "auto"], //弹框大小
        border: [0, .5, "#666"],
        title: "修改库存",
        dialog: {
            msg: "<form class='layui-form'>当前省份<input class='layui-form-text' disabled='disabled' type='text'  value=" + province + "><br/>" +
            "当前城市<input class='layui-form-text' disabled='disabled' type='text'  value=" + cityName + "><br/>" +
            "电池型号<input class='layui-form-text' disabled='disabled' type='text'   value=" + batteryType + "><br/>" +
            "库存数量<input  type='text' id='stock' value=" + stock + "></form>",

            btns: 2,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
            type: 2,   //1:对钩   2：问号  3：叹号
            btn: ["确认修改", "取消"],  //自定义按钮
            yes: function () {  //点击左侧按钮：成功

                var stock = $("#stock").val()
                updateBatteryStock(province, cityName, batteryType, stock)

            },
            no: function () {

            }

        }
    })
}


//省列表
function getAllProvince() {


    $.ajax({
        type: "POST",
        url: "/importProvince",
        data: null,
        dataType: "json",
        success: function (data) {
            $('#province').empty();
            $('#province').append("<option value= 0>全部省份</option>");
            for (var i = 0; i < data.length; i++) {

                $("#province").append("<option value=" + data[i].provinceCode + ">" + data[i].provinceName + "</option>")
            }
        }

    });
}


function updateBatteryStock(province, cityName, batteryType, stock) {
    $.ajax({
        url: "/updateBatteryStock",
        type: "POST",
        data: {"province": province, "cityName": cityName, "batteryType": batteryType, "stock": stock},
        dataType: "json",
        success: function (data) {

            window.confirm(data)
            updateStockTable("/getAllBatteryAmount")
        }
    })
}


function getAllCityByProvince() {
    $.ajax({
        type: "POST",
        url: "/importCity",
        data: {"provinceCode": $("#province option:selected").val()},
        dataType: 'json',
        success: function (data) {
            $('#city').empty();//清空下拉列表
            $('#city').append("<option value= 0>全部城市</option>");
            for (var i in data) {
                var cityCode = data[i].cityCode;
                var cityName = data[i].cityName;
                $('#city').append("<option value=" + cityCode + ">" + cityName + "</option>");
            }
        }


    });
}

function getAllBatteryBrand() {

    $.ajax({
        type: "POST",
        url: "/importBatteryBrand",
        data: null,
        dataType: "json",
        success: function (data) {
            $("#batteryBrand").empty()
            $('#batteryBrand').append("<option value= 0>全部品牌</option>")
            for (var i = 0; i < data.length; i++) {
                var id = data[i].id;
                var brandName = data[i].brandName;
                $('#batteryBrand').append("<option value=" + id + ">" + brandName + "</option>");

            }
        }

    });
}


function getAllBatteryTypeByBrand() {
    $.ajax({
        type: "POST",
        url: "/importBatteryType",
        data: {"batteryBrand": $("#batteryBrand option:selected").val()},
        dataType: "json",
        success: function (data) {
            $('#batteryType').empty();//清空下拉列表
            $('#batteryType').append("<option value= 0>---请选择---</option>");
            for (var i in data) {
                var id = data[i].id;
                var type = data[i].type;
                $('#batteryType').append("<option value=" + id + ">" + type + "</option>");
            }
        }
    });

}

function getAllBatteryStock() {

}