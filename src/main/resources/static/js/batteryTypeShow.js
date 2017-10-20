/**
 * Created by admin on 2017/7/28.
 */
var batteryMsg = [
    {
        title: '序号',
        increment: 1,
        width: '40px'
    }, {
        title: '电池ID',
        data: 'id',
        width: '0px',
        display: "none"

    },
    {
        title: '电池品牌',
        data: 'brandName'
    },
    {
        title: '电池型号',
        data: 'type'

    },
    {
        title: '操作',
        //element: "<button onclick='return deleteInfo(this)'>删除</button><button onclick='return editInfo(this)'>编辑</button>"
        element: "<a href='javascript:;' onclick='return editInfo(this)'  class='layui-btn layui-btn-mini'>编辑</a>" +
        "<a href='javascript:;' onclick='return deleteInfo(this)' class='layui-btn layui-btn-danger layui-btn-mini'>删除</a>",
        width: '100px'

    }]

$(function () {


    document.getElementById("caption").textContent = "电池型号信息";
    updateTable();
    //清空table旧数据


});

//删除信息
function updateTable() {
    $("#Table").children('table').empty();
    // $("#Table").simplePagination.pagination('destroy');
    $("#Table").children('div .page-nav').remove();
    $("#Table").children('div .addon').remove();


    $('#Table').table({
        url: '/getAllBatteryType',
        type: "get",
        columns: batteryMsg
    })
}

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

    var personType = $("#type").val();// 获取类型 customer/admin/staff/technician
    var tr = getRowObj(obj);
    var m = tr.rowIndex;
    m = m - 1;
    var batteryBrand = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(2)').text();
    var batteryType = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(3)').text();
    var batteryId = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(1)').text();
    dropBatteryType(batteryBrand, batteryType, batteryId)
};


//编辑

function editInfo(obj) {

    var personType = $("#type").val();// 获取类型 customer/admin/staff/technician
    var tr = getRowObj(obj);
    var m = tr.rowIndex;
    m = m - 1;
    var batteryBrand = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(2)').text();
    var batteryType = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(3)').text();
    var batteryId = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(1)').text();
    editBatteryType(batteryBrand, batteryType, batteryId)

}

function addBatteryType() {
    $.mbox({
        area: ["500px", "auto"], //弹框大小
        border: [0, .5, "#666"],
        title: "添加电池型号与品牌",
        dialog: {
            msg: "<form>电池品牌<input type='text' maxlength='8' id='batteryBrandName' ><br/>" +
            "电池型号<input type='text'maxlength='12' id='type' ></form>",
            btns: 2,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
            type: 2,   //1:对钩   2：问号  3：叹号
            btn: ["确认添加", "取消"],  //自定义按钮
            yes: function () {  //点击左侧按钮：成功
                var type = $("#type").val()
                var batteryBrandName = $("#batteryBrandName").val()
                insertBatteryType(type, batteryBrandName)
                //console.log(type + batteryBrandName)
            },
            no: function () {

            }

        }
    })
}

function editBatteryType(batteryBrand, batteryType, batteryId) {
    $.mbox({
        area: ["500px", "auto"], //弹框大小
        border: [0, .5, "#666"],
        title: "修改电池型号与品牌",
        dialog: {
            msg: "<form>电池品牌<input disabled='disabled' maxlength='8' type='text' id='batteryBrandName' value=" + batteryBrand + "><br/>" +
            "电池型号<input  type='text' maxlength='12' id='type'  value=" + batteryType + "></form>" +
            "<label style='color:red'>提示：请勿修改电池品牌,如品牌变化请删除该电池品牌下的所有电池型号再重新添加</label>",
            btns: 2,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
            type: 2,   //1:对钩   2：问号  3：叹号
            btn: ["确认修改", "取消"],  //自定义按钮
            yes: function () {  //点击左侧按钮：成功
                var type = $("#type").val()
                var batteryBrandName = $("#batteryBrandName").val()
                updateBatteryType(type, batteryId)

            },
            no: function () {

            }

        }
    })
}

function dropBatteryType(batteryBrand, batteryType, batteryId) {
    $.mbox({
        area: ["300px", "auto"], //弹框大小
        border: [0, .5, "#666"],
        title: "删除电池型号与品牌",
        dialog: {
            msg: "<p >确认删除" + batteryBrand + "品牌下的<br/>" + batteryType + "电池型号？</p>" +
            "<p style='color:red'>警告：删除当前电池型号同时将删除该电池型号的所有库存</p>",
            btns: 2,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
            type: 2,   //1:对钩   2：问号  3：叹号
            btn: ["确认删除", "取消"],  //自定义按钮
            yes: function () {  //点击左侧按钮：成功

                deleteBatteryType(batteryId)
            },
            no: function () {

            }

        }
    })
}

function insertBatteryType(type, brandName) {
    $.ajax({
        url: "/insertBatteryType",
        type: "POST",
        data: {"batteryType": type, "batteryBrandName": brandName},
        dataType: "json",
        success: function (data) {

                confirmMsg(data)
                updateTable()
        }
    })
}


function updateBatteryType(type, id) {
    $.ajax({
        url: "/updateBatteryType",
        type: "POST",
        data: {"batteryType": type, "batteryId": id},
        dataType: "json",
        success: function (data) {
            updateTable()
            confirmMsg(data)
        }
    })
}

function deleteBatteryType(id) {
    $.ajax({
        url: "/deleteBatteryType",
        type: "POST",
        data: {"batteryId": id},
        dataType: "json",
        success: function (data) {
            confirmMsg(data)
            updateTable()
        }
    })
}

function confirmMsg(msg) {
    $.mbox({
        area: ["400px", "auto"], //弹框大小
        border: [0, .5, "#666"],
        title: "提示",
        dialog: {
            msg: msg,
            btns: 1,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
            type: 3,   //1:对钩   2：问号  3：叹号
            btn: ["确认"],  //自定义按钮
            yes: function () {  //点击左侧按钮：成功

            },
            no: function () {

            }

        }
    })
}
