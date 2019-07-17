function test() {


    var url = getRPath() + "/manager/torderinfooperatelog/test";

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
    //$('#q_orderNo').val(GetQueryString("id"));

    var managerType = $("#managerType").val();
    if (managerType == 2) {

        $("#companyIdView").css("display", "none")
    } else {

        $("#companyIdView").css("display", "block");
        $("#lockEnterpriseID").select2().val(null).trigger("change");
        $("#lockEnterpriseID").select2("destroy");
        $("#lockEnterpriseID").html("");
        initManagerLockCompanySelect();

        if(GetQueryString("enterpriseId"))
        {
        var option = new Option(GetQueryString("enterpriseName"), GetQueryString("enterpriseId"), true, true);
        $("#lockEnterpriseID").append(option).trigger('change');
        $("#lockEnterpriseID").trigger({
            type: 'select2:select',
            params: {
                data: {text: GetQueryString("enterpriseName"), id: GetQueryString("enterpriseId")}
            }
        });
        }
    }

    //初始化查询相关
    initDataTimePicker("startTime")
    initDataTimePicker("endTime")


    //初始化查询相关
    initDataTimePicker("logstartTime")
    initDataTimePicker("logendTime")

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


    // $('#q_EnterpriseName').val(GetQueryString("enterpriseName"));
    // $('#q_EnterpriseId').val(GetQueryString("enterpriseId"));

    $('#q_choice').val(GetQueryString("q_choice"));
    $('#logstartTime').val(GetQueryString("logstartTime"));
    $('#startTime').val(GetQueryString("startTime"));
    $('#operateUserId').val(GetQueryString("operateUserId"));

    $('#logendTime').val(GetQueryString("logendTime"));
    $('#endTime').val(GetQueryString("endTime"));
    var qtype = GetQueryString("q_operlogType");
    if (qtype) {
        $('#q_operlogType').val(qtype);
        operTypeChange();
        setTimeout(function () {
            $('#q_operlogSubType').val(GetQueryString("q_operlogSubType"));
            InitQuery_item();
        }, 40);
    }
    else {
        InitQuery_item();
    }


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

        var url = getRPath() + "/manager/torderinfooperatelog/saveOrUpdate";

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

function operTypeChange() {

    var otype = $("#q_operlogType").val();

    $("#q_operlogSubType").html("");
    if (otype == "0") {
        $("#q_operlogSubType").html("" +

            "  <option value=''>请选择</option>" +
            "<option value='1'>新建下发</option>" +
            "<option value='2'>审核加钱</option>" +

            "");
    }
    else if (otype == "1") {
        $("#q_operlogSubType").html("" +

            "  <option value=''>请选择</option>" +
            "<option value='0'>创建工单</option>" +
            "<option value='5'>确认锁企工单</option>" +
            "<option value='1'>派单</option>" +
            "<option value='2'>审核</option>" +
            "<option value='7'>向锁企提交申请</option>" +
            "<option value='3'>回访评分</option>" +
            "<option value='4'>结账</option>" +

            "<option value='6'>确认锁企已打款</option>" +

            "");
    }
    else if (otype == "2") {
        $("#q_operlogSubType").html("" +

            "  <option value=''>请选择</option>" +
            "<option value='1'>接单</option>" +
            "<option value='2'>申请加钱</option>" +
            "<option value='3'>取消工单</option>" +
            "<option value='4'>确认完成</option>" +
            "");
    }

    $("#q_operlogSubType").val("");

}


function InitQuery_item() {

    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/torderinfooperatelog/orderAllList', // 请求后台的URL（*）
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

            var sellerId = '';

            var managerType = $("#managerType").val();
            if (managerType != 2) {

                sellerId = $("#lockEnterpriseID").val()[0];
            }

            var param = {
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sortName: params.sort, // 要排序的字段
                sortOrder: params.order, // 排序规则

                orderNo: $("#q_orderNo").val(),
                type: $("#q_type").val(),
                content: $("#q_content").val(),
                // EnterpriseName: $("#q_EnterpriseName").val(),
                // SellerId: $("#lockEnterpriseID").val()[0],
                SellerId:sellerId,
                choice: $("#q_choice").val(),
                projectName: $("#q_projectName").val(),

                serverTypeGet: $("#q_ServerType").val(),
                custPhone: $("#q_CustPhone").val(),
                customerName: $("#q_customerName").val(),
                startTime: $("#startTime").val(),
                endTime: $("#endTime").val(),
                areaCode: $("#q_provinceCode").val() + $("#q_cityCode").val() + $("#q_districtCode").val(),

                logstartTime: $("#logstartTime").val(),
                logendTime: $("#logendTime").val(),

                operlogType: $('#q_operlogType').val(),
                operlogSubType: $('#q_operlogSubType').val(),
                operateUserId: $("#operateUserId").val(),

                lockerPhone: $("#q_lockPhone").val(),
                lockName: $("#q_lockName").val(),
             
                orderFeeType:$("#q_orderFeeType").val(),
            };
            return param;
        },
        rowAttributes: function rowAttributes(row, index) {
            return {
                'title': "点击行查看详情",

            }
        },
        rowStyle: function (row, index) {

            return "";// rowStyleFormat(row, index);

        },
        columns: [{
            field: 'id',
            visible: false
        },


            {
                field: 'icon',
                title: '标识',
                align: 'center',
                valign: 'middle',
                visible: false,
                formatter: function (value, row, index) {
                    return orderFlagFormat(value, row, index);
                }
            },
            {
                field: 'orderNo',
                title: '订单编号',
                align: 'center',
                valign: 'middle',
            },

            {
                field: 'orderState',
                title: '订单状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {

                    return orderStatusTypeName(value);


                    if (value >= 201) {
                        return "待作业"
                    } else {
                        if (value == 1) {
                            return "<span style='color: red'>加钱待平台审核</span>"
                        } else if (value == 2) {
                            return "<span style='color: red'>退单待平台审核</span>"
                        } else if (value == 4) {
                            return "<span style='color: red'>平台申请待锁企审核</span>"
                        } else if (value == 5) {
                            return "<span style='color: red'>锁企不通过待平台处理</span>"
                        }
                    }
                }
            },
            {
                field: 'companyName',
                title: '合伙人/代理商',
                align: 'center',
                visible: false,
                valign: 'middle',
            },
            {
                field: 'lockName',
                title: '锁匠名 ',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'lockerPhone',
                title: '锁匠电话 ',
                visible: false,
                align: 'center',
                valign: 'middle',
            },

            {
                field: 'createTime',
                title: '工单创建时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {

                    return value.split(".")[0]
                }
            },
            {
                field: 'receiveTime',
                title: '分单时间',
                align: 'center',
                visible: false,
                valign: 'middle',
                formatter: function (value) {
                    if (value)
                        return value.split(".")[0]
                    else
                        return "";

                }
            },
            {
                field: 'custName',
                title: '客户姓名',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'custPhone',
                title: '客户电话',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'areaName',
                title: '客户所在地区',
                align: 'center',
                visible: false,
                valign: 'middle',
            },
            {
                field: 'addressDetail',
                title: '安装地址',
                visible: false,
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'sellerTotalPrice',
                title: '锁企服务费',
                align: 'center',
                visible: false,
                valign: 'middle'
            }, {
                field: 'lockerTotalPrice',
                title: '锁匠服务费',
                align: 'center',
                visible: false,
                valign: 'middle'
            },
            {
                field: 'serverType',
                title: '服务类型',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                    return orderTypeNameNew(value);
                    if (value == 0) {
                        return "安装"
                    }
                    if (value == 1) {
                        return "维修"
                    }
                    if (value == 2) {
                        return "开锁"
                    }
                    if (value == 3) {
                        return "特殊门改造"
                    }
                    if (value == 4) {
                        return "测量"
                    }
                    if (value == 5) {
                        return "猫眼安装"
                    }
                }
            },
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


}


function doSearch_item() {


    var opt = {
        silent: true,
        pageNumber: 1
    };
    $("#table_list_item").bootstrapTable('refresh', opt);

    //success("test");
}

$("#q_choice").on("change", function () {
    $("#q_operlogType").val("")
    $("#q_operlogSubType").val("")
});
$("#q_operlogType").on("change", function () {
    $("#q_choice").val("")
});

$("#btnImportOut").click(function () {

    var sellerId = '';
    var managerType = $("#managerType").val();
    if (managerType != 2) {

        sellerId = $("#lockEnterpriseID").val();
    }

    window.open(getRPath() + '/manager/torderinfooperatelog/orderExcel?' +
        'orderNo=' + $("#q_orderNo").val() +
        '&type=' + $("#q_type").val()+
        // '&content=' + $("#q_content").val()+
        // '&EnterpriseName=' + $("#q_EnterpriseName").val()+
        '&SellerId=' + sellerId+
        '&choice=' + $("#q_choice").val()+
        // '&projectName=' + $("#q_projectName").val()+
        '&serverTypeGet=' + $("#q_ServerType").val()+
        '&orderFeeType=' + $("#q_orderFeeType").val()+
        '&custPhone=' + $("#q_CustPhone").val()+
        '&customerName=' + $("#q_customerName").val()+
        '&startTime=' + $("#startTime").val()+
        '&endTime=' + $("#endTime").val()+
        '&areaCode=' + $("#q_provinceCode").val() + $("#q_cityCode").val() + $("#q_districtCode").val()+
        '&logstartTime=' + $("#logstartTime").val()+
        '&logendTime=' + $("#logendTime").val()+
        '&operlogType=' + $("#q_operlogType").val()+
        '&operlogSubType=' + $("#q_operlogSubType").val()+
        '&lockerPhone=' + $("#q_lockPhone").val()+
        '&lockName=' + $("#q_lockName").val()+
        '&operateUserId=' + $("#operateUserId").val()
        , "_blank");
});