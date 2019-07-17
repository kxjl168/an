function test() {


    var url = getRPath() + "/manager/tlockcompany/test";

    $.ajax({
        type: "post",
        url: url,
        //data : data,
        async: false,
        dataType: "json",
        success: function (response) {
            success("操作成功！");
        }
    });
}

$(function () {
    InitQuery_item();


    $("#btnAdd_item").click(function () {
        $('#mform_item')[0].reset();
        $('#mform_item').find("#id").val("");
        $("#myModal_item_title").html("添加");
        $("#distpicker2").distpicker({
            autoSelect: true
        });
        $("#myModal_item").modal();
    });


    // modal 新增基本字段事件 关闭事件事件， 清空已有的值 恢复禁用
    $('#myModal_item').on('hide.bs.modal', function (e) {
        if ($(e.target).attr("type")) //日期选择等弹出框
            return;
        $('#mform_item')[0].reset();
        $("#mform_item").data('bootstrapValidator').resetForm();
    });


    $("#btnSubmit_item").click(function () {
        $("#mform_item").data('bootstrapValidator').resetForm();
        // var bool2 = bv.isValid();
        $("#mform_item").data("bootstrapValidator").validate();
        // flag = true/false
        var flag = $("#mform_item").data("bootstrapValidator").isValid();
        var url = getRPath() + "/manager/tlockcompany/saveOrUpdate";
        if (flag) {
            var data = $("#mform_item").serialize();
            /**/
            $.ajax({
                type: "post",
                url: url,
                data: data,
                async: false,
                dataType: "json",
                success: function (response) {
                    // debugger;
                    if (response.bol) {
                        $('#myModal_item').modal("hide");
                        doSearch_item();
                        success("操作成功！");
                    } else {
                        error(response.message);
                    }
                }
            });
        }
    });
    initValidate_item();
});


function initValidate_item() {
    $("#mform_item").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
            enterpriseName: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                },
            },
            enterprisePhone: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    regexp: {
                        regexp: /^1[0-9]{10}$|0\d{2,3}-\d{7,8}$/i,
                        message: '电话为手机号码或者座机电话'
                    }
                }
            },
            district: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '不能为空',
                        callback: function (value, validator) {
                            if (value == "" || $("#mform_item").find("#city").val() == "" || $("#mform_item").find("#province").val() == "") {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                }
            },
            enterpriseAddressDetail: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                },
            },
        }
    });
}


function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/tlockcompany/tlockCompanyList', // 请求后台的URL（*）
        method: 'post', // 请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar', // 工具按钮用哪个容器
        showHeader: true,
        searchAlign: 'left',
        buttonsAlign: 'left',
        searchOnEnterKey: true,
        striped: true, // 是否显示行间隔色
        cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, // 是否显示分页（*）
        sortable: false, // 是否启用排序
        sortName: 'id', // 排序字段
        sortOrder: "desc", // 排序方式
        sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, // 初始化加载第一页，默认第一页
        pageSize: 10, // 每页的记录行数（*）
        pageList: [10, 25], // 可供选择的每页的行数（*）
        search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        // showColumns: true, //是否显示所有的列
        uniqueId: "id", // 每一行的唯一标识，一般为主键列
        // queryParamsType : "limit",
        queryParams: function queryParams(params) { // 设置查询参数
            var param = {
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sortName: params.sort, // 要排序的字段
                sortOrder: params.order, // 排序规则
                auditFlag: 2,
                enterpriseName: $("#q_companyName").val(),
                enterprisePhone: $("#q_companyPhone").val(),
            };
            return param;
        },
        columns: [{
            field: 'id',
            visible: false
        },
            {
                field: 'enterpriseName',
                title: '名称',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'enterprisePhone',
                title: '电话',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'address',
                title: '地区',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return (row.province ? row.province : '') + " " + (row.city ? row.city : '') + " " + (row.district ? row.district : '');
                }
            },
            {
                field: 'enterpriseAddressDetail',
                title: '详细地址',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'createTime',
                title: '入驻平台时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {
                field: 'auditFlag',
                title: '审核状态',
                align: 'center',
                valign: 'middle',
                visible: false,
                formatter: function (value, row, index) {
                    if (value == -1)
                        return "未提交审核";
                    else if (value == 0)
                        return "已提交审核";
                    else if (value == 1)
                        return "<span class='pass'> 审核通过</span>";
                    else if (value == 2)
                        return "<span class='nopass'>审核不通过</span>";
                }
            },
            {
                title: '操作',
                field: 'vehicleno',
                align: 'center',
                visible: true,
                formatter: modifyAndDeleteButton_item,
                events: PersonnelInformationEvents_item
            }
        ]
    });
}

function modifyAndDeleteButton_item(value, row, index) {
    return ['<div class="">'
    + '<button id = "delete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
    + '</div>'].join("");
}


window.PersonnelInformationEvents_item = {
    "click #update": function (e, value, row, index) {
        $.ajax({
            type: "post",
            url: getRPath() + '/manager/tlockcompany/load',
            data: {
                id: row.id
            },
            async: false,
            dataType: "json",
            success: function (response) {
                $("#mform_item").fill(response);
                setArea($("#mform_item"), response);
                $("#myModal_item_title").html("编辑");
                $("#myModal_item").modal();
            }
        });

    },

    "click #delete": function (e, value, row, index) {
        var msg = "您真的确定要删除吗？";
        var url = getRPath() + "/manager/tlockcompany/delete";
        cconfirm(msg, function () {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "id": row.id
                },
                success: function (response) {
                    if (response.bol) {
                        success("删除成功！");
                        doSearch_item();
                    } else {
                        error("" + response.message);
                    }
                }
            });
        });
    }
};

function doSearch_item() {
    var opt = {
        silent: true,
        pageNumber: 1
    };
    $("#table_list_item").bootstrapTable('refresh', opt);

    //success("test");
}
