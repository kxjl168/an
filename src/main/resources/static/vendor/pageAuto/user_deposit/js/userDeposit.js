$(function () {

	
	  var uid=GetQueryString("uid");
	    var uname=GetQueryString("uname");
	    if(uid!=null)
	    	{
	    var option = new Option(uname, uid, true, true);
	    $("#q_userId").append(option).trigger('change');
	    $("#q_userId").trigger({
	        type: 'select2:select',
	        params: {
	            data: {text:uname,id:uid}
	        }
	    });
	    
	    $("#depositState").val("");
	    
	   
	    	}
	
    initDataTimePicker("startTime")
    initDataTimePicker("endTime")

    //初始化表格
    InitQuery_item();

    //改价查询
    InitQuery_orderMoney();

    //初始化验证
    initValidate_item();

    //工单导入验证
    initImportFormValidate();

  
    
    
    
    initLockerQuerySelect("q_userId");
    
    
    $("#exportLockerBillExcelBtn").click(function () {
        if (null == $("#q_userId").val() || "" == $("#q_userId").val()) {
          //  error("请选择提现锁匠");
          //  return
        }
        
        var userid=($("#q_userId").val()=="-1")?"":$("#q_userId").val();
        if(userid==null)
        	userid="";
        
        var phone="";
        if(typeof(userid)!="undefined"&&userid!=null&&userid!="")
        	phone=$("#q_userId").select2('data')[0].phone;
        
        window.open(getRPath() + '/manager/userDeposit/userDepositExcel?' +
            'userId=' + userid +
            "&userName=" + $("#q_userId option:selected").html() +
            "&lockerPhone=" +phone +
            "&depositStatusQuery=" + $("#depositState").val() +
            "&startTime=" + $("#startTime").val() +
            "&endTime=" + $("#endTime").val(), "_blank");
    });

    // modal 新增基本字段事件 关闭事件事件， 清空已有的值 恢复禁用
    $('#noPassReasonModal').on('hide.bs.modal', function (e) {
        $('#notPassReasonForm')[0].reset();
        $("#notPassReasonForm").data('bootstrapValidator').resetForm();
        $("#depositId").val("")
    });

    //提交事件
    $("#noPassReasonSubmit").click(function () {
        $("#notPassReasonForm").data('bootstrapValidator').resetForm();
        $("#notPassReasonForm").data("bootstrapValidator").validate();
        var flag = $("#notPassReasonForm").data("bootstrapValidator").isValid();
        if (flag) {
            var url = getRPath() + "/manager/userDeposit/fail";
            var parameter = $("#notPassReasonForm").serializeArray()
            var callback = function (data) {
                if (data.errCode = 1) {
                    $('#noPassReasonModal').modal("hide");
                    doSearch_item();
                    success("操作成功！");
                } else {
                    error(data.errMsg);
                }
            }
            sendRequest(url, parameter, callback)
        }
    });
    /**
     * 导入模态框消失
     */
    $('#importFileModal').on('hide.bs.modal', function (e) {
        $('#importModalForm')[0].reset();
        $("#importModalForm").data('bootstrapValidator').resetForm();
    });
    $("#exportOrderBillAudit").click(function () {
        // initFileInput();
        $('#status').val(1)
        $("#importDepostiTitle").html("批量导入审核");
        $("#importFileModal").modal();
    })
    $("#exportOrderBillSettleAccount").click(function () {
        // initFileInput();
        $('#status').val(3)
           $("#importDepostiTitle").html("批量导入结账");
        $("#importFileModal").modal();
    })

    /**
     * 提交审核导入Excel文件
     */
    $("#submitFileImport").click(function () {
        /*var fileCheck = $(".text-danger").length > 0
        if (fileCheck) {
            error("请选择正确的文件")
            return
        }*/
        $("#importModalForm").data('bootstrapValidator').resetForm();
        $("#importModalForm").data("bootstrapValidator").validate();
        var flag = $("#importModalForm").data("bootstrapValidator").isValid();

        if (flag) {
            var url = getRPath() + "/manager/userDeposit/importAuditOrderByExcel";
            var data = new FormData($("#importModalForm")[0])
            var callback = function (res) {
                if (res.errCode == 1) {
                    success("导入成功！");
                } else {
                    error(res.errMsg,5000);
                }
                $('#importFileModal').modal("hide");
                doSearch_item();
            }
            sendMultiRequest(url, data, callback)
        }
    });

});

/**
 * 工单导入验证
 */
function initImportFormValidate() {
    $("#importModalForm").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
            excelFile: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            }
        }
    });
}

/**
 * bootstrap validator初始化
 */
function initValidate_item() {
    $("#notPassReasonForm").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
            failCause: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            }
        }
    });

}

/**
 * bootstrap table初始化
 * @constructor
 */
function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/userDeposit/depositList', // 请求后台的URL（*）
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

                userId: ($("#q_userId").val()=="-1")?"":$("#q_userId").val(),
                lockerPhone: $("#q_phone").val(),
                depositStatusQuery: $("#depositState").val(),
                startTime: $("#startTime").val(),
                endTime: $("#endTime").val()
            };
            return param;
        },
        columns: [
            {
                field: 'id',
                visible: false
            },
            {
                field: 'orderNo',
                title: '订单编号',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'depositMoney',
                title: '费用',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {

                    //  var utypeName= getUserTypeName(row);


                    var dtxt = "<span class='usertippy tipurl' data-tippy-content='" + '单击行查看详情' + "' >" + value + "</span>";


                    setTimeout(function () {
                        tippy(".usertippy", {
                                arrow: true,
                                arrowType: 'round', // or 'sharp' (default)
                                animation: 'perspective',
                            }
                        )
                    }, 500);

                    return dtxt;

                }
            },

            {
                field: 'userId',
                title: '锁匠ID',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'userName',
                title: '锁匠称呼',
                align: 'center',
                valign: 'middle'
            },

            /* {
                 field: 'bankName',
                 title: '提现银行',
                 align: 'center',
                 valign: 'middle'
             },
             {
                 field: 'depositNumber',
                 title: '银行卡号码',
                 align: 'center',
                 valign: 'middle'
             },
             {
                 field: 'depositReceiver',
                 title: '收款人姓名',
                 align: 'center',
                 valign: 'middle'
             },*/
            /*
            {
                field: 'orderInfoId',
                title: '提现工单号',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'depositMoney',
                title: '提现金额',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'depositReceiver',
                title: '收款人姓名',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'depositReceiverPhone',
                title: '收款人电话',
                align: 'center',
                valign: 'middle'
            },*/
            {
                field: 'depositStatus',
                title: '提现状态',
                align: 'center',
                valign: 'middle',

                formatter: function (value, row, index) {
                    if (value == 0) {
                        return "<span class='text-info'>待审核</span>"
                    } else if (value == 1) {
                        return "<span class='text-warning'>待打款</span>"
                    } else if (value == 2) {
                        return "审核不通过"
                    } else if (value == 3) {
                        return "<span class='text-success'>打款成功</span>"
                    } else {
                        return "打款失败"
                    }
                }
            },
            {
                field: 'startdoneTime',
                title: '完成时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {

                    return value.split(".")[0]
                }
            },
            {
                field: 'auditOpinion',
                title: '审核意见',
                visible: false,
                align: 'center',
                valign: 'middle',

            },
            {
                field: 'failCause',
                title: '打款失败原因',
                visible: false,
                align: 'center',
                valign: 'middle',

            },
            {
                title: '操作',
                field: 'xx',
                align: 'center',
                formatter: modifyAndDeleteButton_item,
                events: PersonnelInformationEvents_item
            }
        ],
        detailView: true,
        onExpandRow: function (index, row, $detail) {


            initDetailLog($detail, row.orderNo, row.id);
            //var subTable = $detail.html('<table class="nohide"></table>').find('table');
            //showDetailLog($(subTable),row.orderNo);
        }
    }).on("click-row.bs.table", function (event, row, rowele, field) {
        //  var subTable = $(rowele).html('<table class="nohide"></table>').find('table');


        showOrCloseDetail(rowele, row, 1)
    });

}

/**
 * 点击通过按钮
 * @param id
 */
function pass(id, type) {
    var msg = "确定审核通过此提现?";
    if (type == 1) {
        var msg = "确定已打款完成?";
    }
    cconfirm(msg, function () {
        $.ajax({
            type: "post",
            url: getRPath() + '/manager/userDeposit/pass',
            data: {
                id: id
            },
            async: false,
            dataType: "json",
            success: function (response) {
                if (response.errCode == 1) {
                    success(response.errMsg);
                    doSearch_item();
                } else {
                    error(response.errMsg);
                }
            }
        });
    })


}

function fail(id) {
    $("#depositId").val(id)
    $("#noPassReasonModal").modal("show")
}

function doSearch_item() {
    var opt = {
        silent: true,
        pageNumber: 1
    };
    $("#table_list_item").bootstrapTable('refresh', opt);
}

