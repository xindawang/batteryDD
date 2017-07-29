/**
 * Created by admin on 2017/7/28.
 */

$(function () {
    //清空table旧数据
    $("#Table").children('table').empty();
    // $("#Table").simplePagination.pagination('destroy');
    $("#Table").children('div .page-nav').remove();
    $("#Table").children('div .addon').remove();

    document.getElementById("type").value = "customer";
    document.getElementById("caption").textContent = "客户信息";

    $('#Table').table({
        url: '/customer',
        type: "get",
        columns: [
            {
                title: '序号',
                increment: 1,
                width: '50px'
            },
            {
                title: '用户名',
                data: 'loginName'
            },
            {
                title: '性别',
                data: 'sex'
            },
            {
                title: '姓名',
                data: 'name'
            },
            {
                title: '手机号码',
                data: 'cellphone'
            },
            {
                title: '电话号码',
                data: 'telephone'
            },
            {
                title: '邮箱',
                data: 'email'
            },
            {
                title: '地址',
                data: 'address'
            },
            {
                title: '邮编',
                data: 'postcode'
            },
            {
                title: '操作',
                element: "<button onclick='return deleteInfo(this)'>删除</button><button onclick='return editInfo(this)'>编辑</button>"
            },
        ]
    })


});


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
    var personType=$("#type").val();// 获取类型 customer/admin/staff/technician
    alert(personType);
    var tr=getRowObj(obj);
    var m=tr.rowIndex;
    m=m-1;
    var loginName=$("#tb>tbody").find('tr:eq('+m+') td:eq(1)').text();
    $.ajax({
        type:"post",
        url:"/delete",
        data:{
            personType:personType,
            loginName:loginName,
        },
        success:function(data) {
            alert(data);
            tr.parentNode.removeChild(tr);//移除该行
        }
    });
};


//编辑

function editInfo(obj) {

    var personType = $("#type").val();// 获取类型 customer/admin/staff/technician
    var tr = getRowObj(obj);
    var m = tr.rowIndex;
    m = m - 1;
    var loginName = $("#tb>tbody").find('tr:eq(' + m + ') td:eq(1)').text();
    if (personType == 'admin') {
        window.location.href = "adminInfoComplete.html?loginName=" + loginName;
    }
    if (personType == 'staff') {
        window.location.href = "staffInfoComplete.html?loginName=" + loginName;

    }
    if (personType == 'technician') {
        window.location.href = "technicianInfoComplete.html?loginName=" + loginName;

    }
    if (personType == 'customer') {
        window.location.href = "customerInfoComplete.html?loginName=" + loginName;

    }

}