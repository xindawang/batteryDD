/**
 * Created by admin on 2017/7/28.
 */

$(function () {
    //清空table旧数据
    $("#Table").children('table').empty();
    // $("#Table").simplePagination.pagination('destroy');
    $("#Table").children('div .page-nav').remove();
    $("#Table").children('div .addon').remove();


    document.getElementById("type").value = "admin";
    document.getElementById("caption").textContent = "管理员信息";

    $('#Table').table({
        url: '/admin',
        type: "get",
        columns: [
            {
                title: '序号',
                increment: 1,
                width: '30px'
            },
            {
                title: '用户名',
                data: 'loginName',
                width: '50px'
            },
            {
                title: '姓名',
                data: 'name',
                width: '30px'
            },
            {
                title: '性别',
                data: 'sex',
                width: '30px'
            },
            {
                title: '手机号码',
                data: 'cellphone',
                width: '80px'
            },
            {
                title: '电话号码',
                data: 'telephone',
                width: '80px'
            },
            {
                title: '邮箱',
                data: 'email',
                width: '90px'
            },
            {
                title: '身份证号',
                data: 'idNumber',
                width: '100px'
            },
            {
                title: '地址',
                data: 'address'
            },
            {
                title: '操作',
                //element: "<button onclick='return deleteInfo(this)' >删除</button><button onclick='return editInfo(this)' >编辑</button>"
                element: "<a href='javascript:;' onclick='return editInfo(this)'  class='layui-btn layui-btn-mini'>编辑</a>" +
                "<a href='javascript:;' onclick='return deleteInfo(this)' class='layui-btn layui-btn-danger layui-btn-mini'>删除</a>",
                width: '100px'

            }
        ]
    })

});


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
    var personType = $("#type").val();// 获取类型 customer/admin/staff/technician
    var tr = getRowObj(obj);
    var m = tr.rowIndex;
    m = m - 1;
    var loginName = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(1)').text();
    if (window.confirm("确定删除用户名为：" + loginName + " 的用户吗？")) {
        $.ajax({
            type: "post",
            url: "/delete",
            data: {
                personType: personType,
                loginName: loginName,
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
    var loginName = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(1)').text()+"";

    window.location.href = "adminInfoComplete.html?loginName=" + encodeURI(loginName);

}