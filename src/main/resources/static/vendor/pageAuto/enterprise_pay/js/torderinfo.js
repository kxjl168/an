function test() {


    var url = getRPath() + "/manager/torderinfo_pay/test";

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

var managerType = $("#managerType").val()


$(function () {
    $('#q_createTime').val(GetQueryString("month"));
    
    var fs= GetQueryString("finishedState");
    if(fs!=null)
    $('#q_finishedState').val(fs);
    InitQuery_item();
    InitQuery_audit();
    
    initManagerCompanySelectPay();

    //初始化查询相关
    initDataTimePicker("startTime")
    initDataTimePicker("endTime")

    //初始化地市区查询下拉框
    var provinceSelectId = "q_province"
    var citySelectId = "q_city"
    var districtSelectId = "q_district"
    var provinceCodeId = "q_provinceCode"
    var cityCodeId = "q_cityCode"
    var districtCodeId = "q_districtCode"

    initProvinceSelect(provinceSelectId, citySelectId, districtSelectId,
        provinceCodeId, cityCodeId, districtCodeId)
    initCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId)
    initDistrictSelect(districtSelectId, districtCodeId)

    $("#exportEnterpriseBillExcelBtn").click(function () {
     /*   if($('#q_EnterpriseName').val()==null || $('#q_EnterpriseName').val()==""){
            error("请输入锁企名")
            return
        }*/
        
        if($('#q_lockEnterpriseID').val()==null || $('#q_lockEnterpriseID').val()==""){
          //  error("请输入锁企名")
         //   return
        }
        
        var userid=($("#q_lockEnterpriseID").val()=="-1")?"":$("#q_lockEnterpriseID").val();
        if(userid==null)
        	userid="";
        
      /*  var phone="";
        if(typeof(userid)!="undefined"&&userid!=null&&userid!="")
        	phone=$("#q_lockEnterpriseID").select2('data')[0].phone;
        */
        
        window.open(getRPath() + '/manager/torderinfo_pay/enterpriseBillExcelExport?' +
            "serverType=" + $("#q_ServerType").val() +
            "&orderNo =" + $("#q_orderNo").val() +
            "&custPhone =" + $("#q_CustPhone").val() +
            "&customerName=" + $("#q_customerName").val() +
            "&startTime=" + $("#startTime").val() +
            "&endTime=" + $("#endTime").val() +
            "&areaCode=" + $("#q_provinceCode").val() + $("#q_cityCode").val() + $("#q_districtCode").val() +
            "&createTime=" + $('#q_createTime').val() +
            "&finishedState=" + $('#q_finishedState').val() +
            "&sellerId=" +userid
            //"&enterpriseName=" + $('#q_lockEnterpriseID').select2('data')[0].text
            , "_blank");
    });

    /**
     * 导入模态框消失
     */
    $('#importFileModal').on('hide.bs.modal', function (e) {
        $('#importModalForm')[0].reset();
        $("#importModalForm").data('bootstrapValidator').resetForm();
    });
    $("#exportOrderBillSettleAccount").click(function () {
        // initFileInput();
        $("#importFileModal").modal();
    })

    /**
     * 提交审核导入Excel文件
     */
    $("#submitFileImport").click(function () {
        var fileCheck = $(".text-danger").length > 0
        if (fileCheck) {
            error("请选择正确的文件")
            return
        }
        $("#importModalForm").data('bootstrapValidator').resetForm();
        $("#importModalForm").data("bootstrapValidator").validate();
        var flag = $("#importModalForm").data("bootstrapValidator").isValid();

        if (flag) {
            var url = getRPath() + "/manager/torderinfo_pay/importSettleOrderBillByExcel";
            var data = new FormData($("#importModalForm")[0])
            var callback = function (res) {
                if (res.errCode == 1) {
                    success("导入成功！");
                } else {
                    error(res.errMsg);
                }
                $('#importFileModal').modal("hide");
                doSearch_item();
            }
            sendMultiRequest(url, data, callback)
        }
    });

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

        var url = getRPath() + "/manager/torderinfo_pay/checkout";

        if (flag) {
            var data = $("#mform_item").serialize();

            /**/

            cconfirm("确认该工单锁企已结账?", function () {

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
                            success("结账成功！");
                        } else {
                            error(response.message);
                        }
                    }
                });
            })

        }
    });

    initValidate_item();

    initImportFormValidate();
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


function refreshAudit(orderId) {


    var opt = {
        silent: true,

        query: {
            //请求参数
            orderInfoId: orderId,
            orderinfoId: orderId,
            //proposType:3, //申请加钱
            //auditStates:3 //审核通过
        }
    };
    $("#table_list_audit").bootstrapTable('refresh', opt);

    //success("test");
}


function InitQuery_audit() {



    // 初始化Table
    $('#table_list_audit').bootstrapTable({
        url: getRPath() + '/manager/torderinfo_pay/torderinfoMoneyList', // 请求后台的URL（*）
        method: 'post', // 请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar', // 工具按钮用哪个容器
        showHeader: true,
        searchAlign: 'left',
        buttonsAlign: 'left',

        searchOnEnterKey: true,
        striped: true, // 是否显示行间隔色
        cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false, // 是否显示分页（*）
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


                orderinfoId: -1,// $("#allocateOrderModal").find("#orderInfoId").val(),


            };
            return param;
        },
        columns: [{
            field: 'id',
            visible: false
        },
            {
                field: 'operateTime',
                title: '时间',
                visible: false,
                align: 'center',
                valign: 'middle',

                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }

            },
            {
                field: 'reasonDes',
                title: '说明',
                align: 'center',
                valign: 'middle',


            },

            {
                field: 'changeValueSeller',
                title: '金额变动',
                align: 'center',
                valign: 'middle',


            },


        ],

    });


    return;


    // 初始化Table
    $('#table_list_audit').bootstrapTable({
        url: getRPath() + '/manager/torderinfoaudit/torderinfoaudit', // 请求后台的URL（*）
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

                proposType: 3, //申请加钱
                auditStates: 3, //审核通过

                orderInfoId: "-1",
                customerName: $("#q_customerName").val(),
                startTime: $("#startTime").val(),
                endTime: $("#endTime").val(),
                areaCode: $("#q_provinceCode").val() + $("#q_cityCode").val() + $("#q_districtCode").val()


            };
            return param;
        },
        columns: [{
            field: 'id',
            visible: false
        },
            {
                field: 'proposMoney',
                title: '申请加钱金额',
                align: 'center',
                valign: 'middle',


            },
            {
                field: 'proposTime',
                title: '申请时间',
                align: 'center',
                valign: 'middle',

                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }

            },
            {
                field: 'proposReason',
                title: '申请原因',
                align: 'center',
                valign: 'middle',


            },
            {
                field: 'auditStates',
                title: '审核状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value == 0)
                        return "未审核";
                    else if (value == 1)
                        return "源匠审核通过";
                    else if (value == 2)
                        return "源匠审核不通过";
                    else if (value == 3)
                        return "锁企审核通过";
                    else if (value == 4)
                        return "锁企审核不通过";
                    else if (value == 5)
                        return "源匠正在向锁企申请";
                }

            },

            {
                field: 'auditorName',
                title: '审核人',
                align: 'center',
                valign: 'middle',


            }, {
                field: 'sellerTotalPrice',
                title: '源匠总费用',
                align: 'center',
                valign: 'middle',
                visible: false,

            },


        ]
    });
}


function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/torderinfo_pay/orderinfoList', // 请求后台的URL（*）
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


                serverType: $("#q_ServerType").val(),
                projectName: $("#q_projectName").val(),
                orderNo: $("#q_orderNo").val(),
                custPhone: $("#q_CustPhone").val(),
                customerName: $("#q_customerName").val(),
                startTime: $("#startTime").val(),
                endTime: $("#endTime").val(),
                areaCode: $("#q_provinceCode").val() + $("#q_cityCode").val() + $("#q_districtCode").val(),

                createTime: $('#q_createTime').val(),
                finishedState: $('#q_finishedState').val(),
                sellerId: $('#q_lockEnterpriseID').val()
            };
            return param;
        },
        rowAttributes:function rowAttributes(row, index) {
        	return {
        	'title':"点击行查看详情",
        	
        	}
        	},
        columns: [{
            field: 'id',
            visible: false
        },
            {
                field: 'orderNo',
                title: '订单编号',
                align: 'center',
                valign: 'middle',


            },
            {
                field: 'createTime',
                title: '工单创建时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                	if(value)
                    return value.split(".")[0];
                	else
                		return "";
                }
            },
            {
                field: 'doneTime',
                title: '工单完成时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                	if(value)
                    return value.split(".")[0];
                	else
                		return "";
                }
            },
            {
                field: 'enterpriseName',
                title: '锁企',
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

                    return orderTypeNameNew(value);

                    if (value == 0)
                        return "安装";
                    else if (value == 1)
                        return "维修";
                    else if (value == 2)
                        return "开锁";
                    else if (value == 3)
                        return "特殊门改造";
                    else if (value == 4)
                        return "测量";
                    else if (value == 5)
                        return "猫眼安装";
                    else if (value == 6)
                        return "其他";
                }
            },
            {
                field: 'orderState',
                title: '工单状态 ',
                align: 'center',
                valign: 'middle',

                formatter: function (value, row, index) {
                    return orderStatusTypeName(value);
                }
            },
            {
                field: 'finishedState',
                title: '是否结账 ',
                align: 'center',
                valign: 'middle',

                formatter: function (value, row, index) {
                    if (value == 1)
                        return "已结账";
                    else
                        return "待结账";
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


            initDetailLog($detail, row.orderNo);
            //var subTable = $detail.html('<table class="nohide"></table>').find('table');
            //showDetailLog($(subTable),row.orderNo);
        }
    }).on("click-row.bs.table", function (event, row, rowele, field) {
        //  var subTable = $(rowele).html('<table class="nohide"></table>').find('table');


        showOrCloseDetail(rowele, row, 1)
    });


    //根据用户类型决定列展示
    var managerType = $("#managerType").val()
    if (managerType == 2) {
        $('#table_list_item').bootstrapTable("hideColumn", "vehicleno")
    }
}

function settleBillBtn(value, row, index) {
    setTimeout(function () {
        tippy(".tippy", {
                arrow: true,
                arrowType: 'round', // or 'sharp' (default)
                animation: 'perspective',
            }
        )
    }, 500);
    return ['<div class="">'
    + '<button id = "update" type = "button"  data-tippy-content="结账" class = "tippy btn btn-info"><i class="fa fa-cny"></i></button>&nbsp;'
    + '</div>'].join("");
}

window.PersonnelInformationEvents_item = {
    "click #update": function (e, value, row, index) {
        $.ajax({
            type: "post",
            url: getRPath() + '/manager/torderinfo_pay/load',
            data: {
                id: row.id
            },
            async: false,
            dataType: "json",
            success: function (response) {

                $("#mform_item").fill(response);

                var typeName = orderTypeNameNew(response.serverType);
                ;
                $("#serverTypeName").val(typeName);


                refreshAudit(row.id);

                $("#myModal_item_title").html("");

                $("#myModal_item").modal();


            }
        });

    },

    "click #delete": function (e, value, row, index) {
        var msg = "您真的确定要删除吗？";
        var url = getRPath() + "/manager/torderinfo_pay/delete";
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

