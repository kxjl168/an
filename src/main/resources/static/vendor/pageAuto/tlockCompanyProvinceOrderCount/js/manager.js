$(function () {

    var managerType = $("#managerType").val();

    if (managerType == 2) {

        $("#companyIdView").css("display", "none")
    }

    $("#lockEnterpriseID").select2().val(null).trigger("change");
    $("#lockEnterpriseID").select2("destroy");
    initManagerLockCompanySelect();
    InitQuery_item();
});


function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/lockCompanyStatistics/provinceOrderList', // 请求后台的URL（*）
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
                field: 'bJ',
                title: '北京市',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'tJ',
                title: '天津',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'sH',
                title: '上海',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'cQ',
                title: '重庆',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'hB',
                title: '河北',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'sX',
                title: '山西',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'lN',
                title: '辽宁',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'jL',
                title: '吉林',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'hLJ',
                title: '黑龙江',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'jS',
                title: '江苏',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'zJ',
                title: '浙江',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'aH',
                title: '安徽',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'fJ',
                title: '福建',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'jX',
                title: '江西',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'sD',
                title: '山东',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'hN',
                title: '河南',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'hUB',
                title: '湖北',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'hUN',
                title: '湖南',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'gD',
                title: '广东',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'hAIN',
                title: '海南',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'sC',
                title: '四川',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'gZ',
                title: '贵州',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'yN',
                title: '云南',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'sANX',
                title: '陕西',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'gS',
                title: '甘肃',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'qH',
                title: '青海',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'nMG',
                title: '内蒙古',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'gX',
                title: '广西',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'xZ',
                title: '西藏',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'nX',
                title: '宁夏',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'xJ',
                title: '新疆',
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
