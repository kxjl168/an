$(function () {

    var managerType = $("#managerType").val();

    if (managerType == 2) {

        $("#companyIdView").css("display", "none")
    }

    $("#lockEnterpriseID").select2().val(null).trigger("change");
    $("#lockEnterpriseID").select2("destroy");
    initManagerLockCompanySelect();
    InitQuery_item();

    // modal 新增基本字段事件 关闭事件事件， 清空已有的值 恢复禁用
    $('#myModal_item').on('hide.bs.modal', function (e) {

        if ($(e.target).attr("type")) //日期选择等弹出框
            return;

        $("#mform_item").data('bootstrapValidator').resetForm();

    });


});


function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/lockCompanyStatistics/mouthOrderList', // 请求后台的URL（*）
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
        pageList : [ 10, 25 ], // 可供选择的每页的行数（*）
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

                creatTime: $("#q_month").val(),
                id: $("#lockEnterpriseID").val()[0]
            };
            return param;
        },
        columns: [
            {
                field: 'id',
                visible: false
            },
            {
                field: 'enterpriseName',
                title: '锁企名称',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'totalCount',
                title: '总单量',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'jan',
                title: '1月',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'feb',
                title: '2月',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'mar',
                title: '3月',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'apr',
                title: '4月',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'may',
                title: '5月',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'jun',
                title: '6月',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'jul',
                title: '7月',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'aug',
                title: '8月',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'sept',
                title: '9月',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'oct',
                title: '10月',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'nov',
                title: '11月',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'dec',
                title: '12月',
                align: 'center',
                valign: 'middle'

            }
        ]
    });
}

function doSearch_item() {

    var opt = {
        silent: true,
        pageNumber: 1
    };
    $("#table_list_item").bootstrapTable('refresh', opt);

}

// $("#btnImportOut").click(function () {
//     window.open(getRPath() + '/manager/lockCompanyStatistics/enterpriseExcel?creatTime=' + $("#q_month").val()+'&enterpriseName='+ $("#q_company").val(),"_blank");
// });
