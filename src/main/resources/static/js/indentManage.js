/**
 * Created by admin on 2017/7/26.
 */

//




$(function () {


    var statusSpanArray = ["allSpan", "importedSpan", "distributedSpan", "receivedSpan", "installedSpan", "evaluatedSpan"]

    var indentStatus  //设置要求查询的订单状态，默认是全部
    var dateRegion
    var cityCode
    var columnArray = [
        {
            title: '序号',
            increment: 1,
            width: '20px'
        },
        {
            title: '订单编号',
            data: 'orderId'
        },
        {
            title: '电池型号',
            data: 'batteryType'
        },
        {
            title: '客户姓名',
            data: 'customerName'
        },
        {
            title: '手机号码',
            data: 'customerCellphone'
        },
        {
            title: '客户电话',
            data: 'customerTelephone'
        },
        {
            title: '地址',
            data: 'address'
        },
        {
            title: '车型',
            data: 'automobileType'
        },
        {
            title: '车牌号',
            data: 'licensePlateNumber'
        },
        {
            title: '下单时间',
            data: 'createTime'
        },
        {
            title: '完成时间',
            data: 'finishTime'
        },
        {
            title: '订单状态',
            data: 'status'
        },
        {
            title: '备注',
            data: 'remark'
        }
    ]


    indentStatusInit()//设置为全部订单（改变样式）
    cityInit()
    dateRegion = getDateRegion();

    updateTable(indentStatus, cityCode, dateRegion)


    // ------------------事件监听---以下--------------
    //城市改变时触发
    $("#cityList").change(function () {

        cityCode = $("#cityList option:selected").val();
        updateTable(indentStatus, cityCode, dateRegion)
        $("#searchNumber").val("")

    });

    //订单状态改变时触发
    $("#all").click(function () {
        indentStatus = 0  //初始化设置全部订单-status=0
        setStatusCss(indentStatus) //设置选中后的样式
        updateTable(indentStatus, cityCode, dateRegion)
        $("#searchNumber").val("")
    })

    $("#imported").click(function () {
        indentStatus = 1  //初始化设置全部订单-status=0
        setStatusCss(indentStatus) //设置选中后的样式
        updateTable(indentStatus, cityCode, dateRegion)
        $("#searchNumber").val("")
    })

    $("#distributed").click(function () {
        indentStatus = 2  //初始化设置全部订单-status=0
        setStatusCss(indentStatus) //设置选中后的样式
        updateTable(indentStatus, cityCode, dateRegion)
        $("#searchNumber").val("")
    })

    $("#received").click(function () {
        indentStatus = 3  //初始化设置全部订单-status=0
        setStatusCss(indentStatus) //设置选中后的样式
        updateTable(indentStatus, cityCode, dateRegion)
        $("#searchNumber").val("")
    })

    $("#installed").click(function () {
        indentStatus = 4  //初始化设置全部订单-status=0
        setStatusCss(indentStatus) //设置选中后的样式
        updateTable(indentStatus, cityCode, dateRegion)
        $("#searchNumber").val("")
    })

    $("#evaluated").click(function () {
        indentStatus = 5  //初始化设置全部订单-status=0
        setStatusCss(indentStatus) //设置选中后的样式
        updateTable(indentStatus, cityCode, dateRegion)
        $("#searchNumber").val("")
    })

    //时间改变
    $("#dateRegionList").change(function () {
        dateRegion = getDateRegion();
        updateTable(indentStatus, cityCode, dateRegion)
        $("#searchNumber").val("")
    })

    $("#searchButton").click(function () {
        var searchNumber = $("#searchNumber").val()
        if (searchNumber == "") {
            confirmemptyNumber()
        } else {
            updateTable1(searchNumber)
        }


    })

    // ------------------事件监听--以上---------------

    function cityInit() {
        var type = "manage"
        updateCity(indentStatus, type)
    }

    function indentStatusInit() {

        indentStatus = 0  //初始化设置全部订单-status=0
        setStatusCss(indentStatus) //设置选中后的样式
    }

    function setStatusCss(status) {
        for (var i in statusArray) {
            if (i == status) {
                $("#" + statusArray[i]).addClass("choosedColor")
                $("#" + statusSpanArray[i]).addClass("choosedBkColor")
            }
            else {
                $("#" + statusArray[i]).removeClass("choosedColor")
                $("#" + statusSpanArray[i]).removeClass("choosedBkColor")
                $("#" + statusArray[i]).addClass("unChoosedColor")
                $("#" + statusSpanArray[i]).addClass("unChoosedBkColor")
            }

        }
    }

    function updateTable(indentStatus, cityCode, dateRegion) {
        //清空table数据
        $("#Table").children('table').empty();
        // $("#Table").simplePagination.pagination('destroy');
        $("#Table").children('div .page-nav').remove();
        $("#Table").children('div .addon').remove();

        $("#Table").table({
            url: '/findIndent?indentStatus=' + indentStatus + '&&cityCode=' + cityCode + '&&dateRegion=' + dateRegion,
            type: "get",
            columns: columnArray
        })
    }

    function updateTable1(searchNumber) {
        //清空table数据
        $("#Table").children('table').empty();
        // $("#Table").simplePagination.pagination('destroy');
        $("#Table").children('div .page-nav').remove();
        $("#Table").children('div .addon').remove();

        $("#Table").table({
            url: '/findIndentByNumber?searchNumber=' + searchNumber,
            type: "get",
            columns: columnArray
        })
    }

    function updateCity(status, type) {
        $.ajax({
            url: "/manageSelectAllCity",
            type: "POST",
            async: false,
            data: {"status": status, "type": type},
            dataType: "json",
            success: function (data) {
                $("#cityList").empty()
                $("#cityList").append("<option value='0'>所有城市</option>")
                for (var i in data) {
                    $("#cityList").append("<option value=" + data[i].cityCode + ">" + data[i].cityName + "</option>")
                }

                cityCode = $("#cityList option:selected").val()
            }
        })
    }

    function getDateRegion() {
        return $("#dateRegionList option:selected").val()
    }


    function confirmemptyNumber() {
        $.mbox({
            area: ["300px", "auto"], //弹框大小
            border: [0, .5, "#666"],
            title: "订单号或者电话号码为空",
            dialog: {
                msg: "请输入相应订单号或者电码号码后再查询",
                btns: 1,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
                type: 3,   //1:对钩   2：问号  3：叹号
                btn: ["确认"],  //自定义按钮
                yes: function () {  //点击左侧按钮：成功

                    //window.close();
                    $("#searchNumber").focus()

                },
                no: function () {

                }

            }
        })
    }

});