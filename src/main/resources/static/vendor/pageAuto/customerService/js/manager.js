$(function () {

    $("#lockEnterpriseID").select2().val(null).trigger("change");
    $("#lockEnterpriseID").select2("destroy");
    initManagerLockCompanySelect();
    InitQuery_item();

    $("#btnAdd_item").click(function () {

        $.ajax({
            type: "post",
            url: getRPath() + '/manager/statisticsManager/customerServiceList',
            data: {
                creatTime: $("#q_month").val(),
                nickName: $("#nickName").val(),
                EnterpriseId: $("#lockEnterpriseID").val()[0]
            },
            dataType: "json",
            success: function (data) {
                DataChart(data);
            }
        });

        $("#myModal_item").modal();
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
            }
        }
    });
}

function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/statisticsManager/customerServiceList',
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
                nickName: $("#nickName").val(),
                EnterpriseId: $("#lockEnterpriseID").val()[0]
            };
            return param;
        },
        columns: [
            {
                field: 'id',
                visible: false
            },
            {
                field: 'creatTime',
                title: '月份',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value == "合计"){
                        return "合计";
                    } else {
                        return new Date(value).Format("MM");
                    }
                }
            },
            {
                field: 'receiveCount',
                title: '接单数',
                align: 'center',
                valign: 'middle'

            },
            {
                field: 'doneCount',
                title: '完成订单数',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'receiveTimeOutCount',
                title: '超时接单数',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'despatchTimeOutCount',
                title: '超时派单数',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'inquireCount',
                title: '回访工单数',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'inquireTimeOutCount',
                title: '超时回访工单数',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'enComplainCount',
                title: '锁企投诉数',
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'cuComplainCount',
                title: '客户投诉数',
                align: 'center',
                valign: 'middle'
            },
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

function DataChart(data) {

    var myChart = echarts.init(document.getElementById('MonthTj1'));

    var item = data.rows[data.rows.length-1];

    var receiveRate = Math.floor(1000-(item.receiveTimeOutCount*1000/item.receiveCount))/10;
    var despatchRate = Math.floor(1000-(item.despatchTimeOutCount*1000/item.receiveCount))/10;
    var inquireTimeRate = Math.floor(1000-(item.inquireTimeOutCount*1000/item.inquireCount))/10;
    var complainRate = Math.floor((parseInt(item.enComplainCount)+parseInt(item.cuComplainCount))*1000/item.receiveCount)/10;

    var labelTop = {
        normal : {
            label : {
                show : true,
                position : 'center',
                formatter : '{b}',
                textStyle: {
                    baseline : 'bottom'
                }
            },
            labelLine : {
                show : false
            }
        }
    };
    var labelFromatter = {
        normal : {
            label : {
                formatter : function (params){
                    return (100 - params.value).toFixed(1) + '%'
                },
                textStyle: {
                    baseline : 'top'
                }
            }
        }
    };
    var labelBottom = {
        normal : {
            color: '#ccc',
            label : {
                show : true,
                position : 'center'
            },
            labelLine : {
                show : false
            }
        },
        emphasis: {
            color: 'rgba(0,0,0,0)'
        }
    };
    var radius = [40, 55];
    option = {
        legend: {
            x : 'center',
            y : 'bottom',
            data:[
                '接单及时率 ','派单及时率 ','回访及时率 ','客诉率 '
            ]
        },
        title : {
            text: '服务统计',
            subtext: '',
            x: 'center'
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            width: '20%',
                            height: '30%',
                            itemStyle : {
                                normal : {
                                    label : {
                                        formatter : function (params){
                                            return 'other\n' + params.value + '%\n'
                                        },
                                        textStyle: {
                                            baseline : 'middle'
                                        }
                                    }
                                },
                            }
                        }
                    }
                },
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        series : [
            {
                type : 'pie',
                center : ['12.5%', '50%'],
                radius : radius,
                x: '0%', // for funnel
                itemStyle : labelFromatter,
                data : [
                    {name:'other', value:100-receiveRate, itemStyle : labelBottom},
                    {name:'接单及时率', value:receiveRate,itemStyle : labelTop}
                ]
            },
            {
                type : 'pie',
                center : ['37.5%', '50%'],
                radius : radius,
                x:'25%', // for funnel
                itemStyle : labelFromatter,
                data : [
                    {name:'other', value:100-despatchRate, itemStyle : labelBottom},
                    {name:'派单及时率', value:despatchRate,itemStyle : labelTop}
                ]
            },
            {
                type : 'pie',
                center : ['62.5%', '50%'],
                radius : radius,
                x:'50%', // for funnel
                itemStyle : labelFromatter,
                data : [
                    {name:'other', value:100-inquireTimeRate, itemStyle : labelBottom},
                    {name:'回访及时率', value:inquireTimeRate,itemStyle : labelTop}
                ]
            },
            {
                type : 'pie',
                center : ['87.5%', '50%'],
                radius : radius,
                x:'75%', // for funnel
                itemStyle : labelFromatter,
                data : [
                    {name:'other', value:100-complainRate, itemStyle : labelBottom},
                    {name:'客诉率', value:complainRate,itemStyle : labelTop}
                ]
            }
        ]
    };

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}