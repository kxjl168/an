$(function () {
    InitQuery_item();
    $("#btnAdd_item").click(function () {
        $('#mform_item')[0].reset();
        $('#mform_item').find("#id").val("");
        $("#myModal_item_title").html("添加");
        $("#myModal_item").modal();
    });

    initLockCompanySelect("lockCompanySelect", "lockCompanyId");

    $("#serverSelect").on("change", function () {
        $("#serverType").val($("#serverSelect").val())
    })

    // modal 新增基本字段事件 关闭事件事件， 清空已有的值 恢复禁用
    $('#myModal_item').on('hide.bs.modal', function (e) {
        $('#mform_item')[0].reset();
        $("#mform_item").data('bootstrapValidator').resetForm();
        $('#lockCompanySelect').removeAttr("disabled");
        $('#serverSelect').removeAttr("disabled");
        $('#lockCompanySelect').selectpicker('val', -1);
        $('#serverSelect').selectpicker('val', -1);
    });

    $("#btnSubmit_item").click(function () {
        $("#mform_item").data('bootstrapValidator').resetForm();
        // var bool2 = bv.isValid();
        if ($('#lockCompanySelect').val() == null || $('#lockCompanySelect').val() == "") {
            error("请选择锁企")
            return
        }
        if ($('#serverSelect').val() == null || $('#serverSelect').val() == "") {
            error("请选择服务类型")
            return
        }
        $("#mform_item").data("bootstrapValidator").validate();
        // flag = true/false
        var flag = $("#mform_item").data("bootstrapValidator").isValid();
        var url = getRPath() + "/manager/pricelockcompany/saveOrUpdate";
        if (flag) {
            // var data = $("#mform_item").serialize();
            var data = {};
            data["id"] = $('#id').val();
            data["lockCompanyId"] = $('#lockCompanySelect').val();
            data["serverType"] = $('#serverSelect').val().split(',')[0];
            data["parentType"] = $('#serverSelect').val().split(',')[1];
            // data["parentType"] = $("#serverSelect").find("option:selected").attr("parentType");
            data["price"] = $('#price').val();
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
    initValidate_item2();
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
            lockCompanySelect: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '不能为空',
                        callback: function (value, validator) {
                            if (value == "" || null == value) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                }
            },
            price: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                }
            },
        }
    });
}

function initValidate_item2() {
    $("#mform_item2").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
        }
    });
}


function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/pricelockcompany/selectList', // 请求后台的URL（*）
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
                enterpriseName: $("#q_companyName").val(),
                enterprisePhone: $("#q_companyPhone").val(),


            };
            return param;
        },
        columns: [{
            field: 'id',
            visible: false
        }, {
            field: 'lockCompanyPriceId',
            visible: false
        },
            {
                field: 'enterpriseName',
                title: '锁企名称',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'enterprisePhone',
                title: '锁企电话',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'serverType',
                title: '服务类型',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return formatServerType(row);
                }
            },
            {
                field: 'price',
                title: '服务价格',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'createTime',
                title: '创建时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (null != value) {
                        return value.split('.')[0];
                    } else {
                        return '-';
                    }
                }
            },
            {
                field: 'updateTime',
                title: '更新时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (null != value) {
                        return value.split('.')[0];
                    } else {
                        return '-';
                    }
                }
            },
            {
                title: '操作',
                field: 'vehicleno',
                align: 'center',
                formatter: modifyAndDeleteButton_item,
                events: PersonnelInformationEvents_item
            }
        ]
    });
}

function formatServerType(row) {
    if (row.parentType == 1) {
        switch (row.serverType) {
            case 0:
                return "安装费"
                break
            case 1:
                return "维修费"
                break
            case 2:
                return "开锁费"
                break
            case 4:
                return "测量费"
                break
            case 5:
                return "猫眼安装费"
                break
            case 6:
                return "换锁"
                break
            case 7:
                return "工程安装"
                break
            case 8:
                return "工程维修"
                break
            case 19:
                return "其他"
                break
        }
    } else if (row.parentType == 2) {
        switch (row.serverType) {
            case 1:
                return "空跑费"
            case 2:
                return "远途费"
            case 3:
                return "改装费"
            case 4:
                return "特殊门"
            case 5:
                return "加急费"
            case 6:
                return "假锁费"
        }
    }
}

function modifyAndDeleteButton_item(value, row, index) {

    setTimeout(function(){
        tippy(".tippy",{
                arrow: true,
                arrowType: 'round', // or 'sharp' (default)
                animation: 'perspective'
            }
        )
    },500);

    return ['<div class="">'
    + '<button id = "update" type = "button"   data-tippy-content="编辑" class = "tippy btn btn-info"><i class="fa fa-edit"></i> </button>&nbsp;'
    // + '<button id = "audit" type = "button" class = "btn btn-warning"><i class="glyphicon glyphicon-check">审核</i> </button>&nbsp;'
    + '<button id = "delete" type = "button"   data-tippy-content="删除" class = "tippy btn btn-danger"><i class="fa fa-trash"></i> </button>&nbsp;'
    + '</div>'].join("");


}



window.PersonnelInformationEvents_item = {
    "click #update": function (e, value, row, index) {
        $.ajax({
            type: "post",
            url: getRPath() + '/manager/pricelockcompany/load',
            data: {
                lockCompanyId: row.id,
                serverType: row.serverType,
                parentType: row.parentType
            },
            async: false,
            dataType: "json",
            success: function (response) {
                $("#id").val(response.lockCompanyPriceId);
                $('#price').val(response.price);
                $('#lockCompanySelect').selectpicker('val', response.id);
                $('#serverSelect').selectpicker('val', response.serverType + "," + response.parentType);
                // $("#serverSelect").attr("parentType",response.parentType);
                $('#lockCompanySelect').attr("disabled", "disabled");
                $('#serverSelect').attr("disabled", "disabled");
                // setArea($("#mform_item"), response);
                $("#myModal_item_title").html("编辑");
                $("#myModal_item").modal();
            }
        });

    },

    "click #delete": function (e, value, row, index) {
        var msg = "您真的确定要删除吗？";
        var url = getRPath() + "/manager/pricelockcompany/delete";
        cconfirm(msg, function () {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "id": row.lockCompanyPriceId
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
        silent: true
    };
    $("#table_list_item").bootstrapTable('refresh', opt);

    //success("test");
}

function checknumber(String) {
    var Letters = "1234567890";
    var i;
    var c;
    for (i = 0; i < String.length; i++) {
        c = String.charAt(i);
        if (Letters.indexOf(c) == -1) {
            return true;
        }
    }
    return false;
}