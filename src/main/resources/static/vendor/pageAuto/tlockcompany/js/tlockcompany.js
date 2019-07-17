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
    initDataTime("agreenStartTime");
    initDataTime("agreenEndTime");

    $("#distpicker").distpicker({
        autoSelect: false
    });
    $("#province").change(function () {
        $(this).attr("title", $(this).val());
    });
    $("#city").change(function () {
        $(this).attr("title", $(this).val());
    });
    $("#district").change(function () {
        $(this).attr("title", $(this).val());
        $("#areaCode").val($("#district option:selected").attr("data-code"));
    });

    $("#btnAdd_item").click(function () {
        $('#mform_item')[0].reset();
        $('#mform_item').find("#id").val("");
        $("#myModal_item_title").html("添加");
        $("#distpicker2").distpicker({
            autoSelect: true
        });
        $('#myTab a:first').tab('show');
        $("#myModal_item").modal();
    });


    // modal 新增基本字段事件 关闭事件事件， 清空已有的值 恢复禁用
    $('#myModal_item').on('hide.bs.modal', function (e) {
        if ($(e.target).attr("type")) //日期选择等弹出框
            return;
        $('#mform_item')[0].reset();

        $("#mform_item").data('bootstrapValidator').resetForm();

    });
    $('#myModal_item2').on('hide.bs.modal', function (e) {
        $("#divauditReason").hide();
        $("#divauditReason").val('');
        $("#mform_item2").data('bootstrapValidator').resetForm();
    });

    $("#divauditReason").hide();

    function updateNormalValidate(ele, fieldnameOrEle, messagetext, isremove) {
        if (isremove)
            $(ele).bootstrapValidator("removeField", fieldnameOrEle);
        else {
            $(ele).bootstrapValidator("addField", fieldnameOrEle, {
                validators: {
                    notEmpty: {
                        message: messagetext
                    },
                }
            });
        }
    };

    $("#mform_item2 #auditFlag").change(function () {
        if ($(this).val() == "2") {
            $("#divauditReason").show();
            $("#mform_item2").data('bootstrapValidator').resetForm();
            $("#mform_item2").data("bootstrapValidator").validate();
            // updateNormalValidate($("#mform_item2"), $("#auditReason"), "审核原因不能为空", false);
        }
        else {
            $("#divauditReason").hide();
            // updateNormalValidate($("#mform_item2"), $("#auditReason"), "审核原因不能为空", true);
        }
    });

    $("#btnSubmit_item_audit").click(function () {
        if ($(this).val() == "2") {
            $("#mform_item2").data('bootstrapValidator').resetForm();
            $("#mform_item2").data("bootstrapValidator").validate();
        }
        // flag = true/false
        var flag = $("#mform_item2").data("bootstrapValidator").isValid();
        var url = getRPath() + "/manager/tlockcompany/saveOrUpdate";
        if (flag) {
            cconfirm("确认审核此锁企吗?", function () {
                var data = $("#mform_item2").serialize();
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
                            $('#myModal_item2').modal("hide");
                            doSearch_item();
                            success("操作成功！");
                        } else {
                            error(response.message);
                        }
                    }
                });
            });
        }
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
            // var data = {};
            // data["id"] = $('#id').val();
            // data["enterpriseName"] = $('#enterpriseName').val();
            // data["enterprisePhone"] = $('#enterprisePhone').val();
            // data["province"] = $('#province').val();
            // data["city"] = $('#city').val();
            // data["district"] = $('#district').val();
            // data["areaCode"] = $('#areaCode').val();
            // data["enterpriseAddressDetail"] = $('#enterpriseAddressDetail').val();
            // data["parentType"] = $("#serverSelect").find("option:selected").attr("parentType");
            // var list
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
        excluded: [":disabled"], // 不可见的也验证
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
            enterpriseName: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
                                return false
                            }
                            return true
                        }
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
                    },
                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            /*agreenStartTime : {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },*/
            buildPrice: {
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
                            }
                            return true
                        }
                    }
                }
            },
            fixPrice: {
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
                            }
                            return true
                        }
                    }
                }
            },
            openLockPrice: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            specialDoorPrice: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            measutePrice: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            catBuildPrice: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            changeLockPrice: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            engineeringInstallationPrice: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            engineeringMaintenancePrice: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            otherFee: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            refitPrice: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            hurryInVainPrice: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            longDistancePrice: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            urgentPrice: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            falseLockPrice: {
                validators: {

                    callback: {
                        message: '只能输入0~9的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value)) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            },
            orderManagerName: {
                validators: {

                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            marketManagerName: {
                validators: {

                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            contackPersonName: {
                validators: {

                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            techPersonName: {
                validators: {

                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            techPersonPhone: {
                validators: {

                    regexp: {
                        regexp: /^1[0-9]{10}$|0\d{2,3}-\d{7,8}$/i,
                        message: '电话为手机号码或者座机电话'
                    }
                }
            },
            customerManagerName: {
                validators: {

                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            customerManagerPhone: {
                validators: {

                    regexp: {
                        regexp: /^1[0-9]{10}$|0\d{2,3}-\d{7,8}$/i,
                        message: '电话为手机号码或者座机电话'
                    }
                }
            },
            taxBear: {
                validators: {

                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            receiptType: {
                validators: {

                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            receiptTitle: {
                validators: {

                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            receiptEnable: {
                validators: {

                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            taxPoint: {
                validators: {

                    callback: {
                        message: '只能输入100以下的数字',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (checknumber(value) || value > 100 || value < 0) {
                                return false;
                            }
                            return true
                        }
                    }
                }
            }
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
            auditReason: {
                validators: {
                    notEmpty: {
                        message: '审核原因不能为空'
                    },
                    callback: {
                        message: '字符长度不能大于200',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 200) {
                                return false
                            }
                            return true
                        }
                    }
                }
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
                field: 'dataState',
                title: '状态',
                // visible: true,
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value == 0)
                        return "<div class='text-danger'>已停用</div>";
                    else if (value == 1)
                        return "<div class='text-success'>正常</div>";
                }
            },
            {
                field: 'createTime',
                title: '入驻平台时间',
                align: 'center',
                valign: 'middle',
                visible: false,
                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {
                title: '操作',
                field: 'vehicleno',
                align: 'center',
                formatter: modifyAndDeleteButton_item,
                events: PersonnelInformationEvents_item
            }
        ],
        detailView: true,
        onExpandRow: function (index, row, $detail) {

            initDetailLog($detail, row);
        }
    }).on("click-row.bs.table", function (event, row, rowele, field) {

        showOrCloseDetail(rowele, row, 1)
    });
}

function modifyAndDeleteButton_item(value, row, index) {
    var html = '<div class="">'

        + '<button id = "update" type = "button"   data-tippy-content="编辑" class = "tippy btn btn-info"><i class="fa fa-edit"></i> </button>&nbsp;'
        + '<button id = "delete" type = "button"   data-tippy-content="删除" class = "tippy btn btn-danger"><i class="fa fa-trash"></i> </button>&nbsp;';

    if (row.dataState == 1)
        html += '<button id = "drop" type = "button"   data-tippy-content="废弃" class = "tippy btn btn-warning"><i class="fa fa-remove"></i> </button>&nbsp;';

    if (row.dataState == 0)
        html += '<button id = "reset" type = "button"   data-tippy-content="恢复" class = "tippy btn btn-success"><i class="fa fa-refresh"></i> </button>&nbsp;';

    html += '</div>';

    setTimeout(function () {
        tippy(".tippy", {
                arrow: true,
                arrowType: 'round', // or 'sharp' (default)
                animation: 'perspective'
            }
        )
    }, 500);

    return [html].join("");
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
                // setArea($("#mform_item"), response);
                $("#mform_item").find("#province").val(response.province);
                $("#mform_item").find("#province").trigger("change");
                $("#mform_item").find("#city").val(response.city);
                $("#mform_item").find("#city").trigger("change");
                $("#mform_item").find("#district").val(response.district);
                $("#mform_item").find("#areaCode").val(response.areaCode);
                $("#mform_item").data('bootstrapValidator').resetForm();
                $("#mform_item").find("#taxPoint").val(parseInt(response.taxPoint).toFixed(0));
                $("#myModal_item_title").html("编辑");

                $('#myTab a:first').tab('show');

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
    },

    "click #drop": function (e, value, row, index) {
        var msg = "您真的确定要废弃吗？";
        var url = getRPath() + "/manager/tlockcompany/drop";
        cconfirm(msg, function () {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "id": row.id
                },
                success: function (response) {
                    if (response.bol) {
                        success("废弃成功！");
                        doSearch_item();
                    } else {
                        error("" + response.message);
                    }
                }
            });
        });

    },
    "click #reset": function (e, value, row, index) {
        var msg = "您真的确定要恢复吗？";
        var url = getRPath() + "/manager/tlockcompany/reset";
        cconfirm(msg, function () {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "id": row.id
                },
                success: function (response) {
                    if (response.bol) {
                        success("恢复成功！");
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


function initDataTime(itemId) {


    $("#" + itemId).datetimepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN',
        weekStart: 1,
        startView: 2,
        minView: 2,
        autoclose: true
        // todayBtn:true,
        // clearBtn: true
    });
}