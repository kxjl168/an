



$(function () {

    initManagerLockSmithSelect();
/*    if ($("#lockerSelect").length > 0) {
        initAllocateSelect()
    }*/

    $("#datepicker_client2").datepicker({
        format: 'yyyy年',
        language: 'zh-CN',
        minViewMode: 2,
        autoclose: true
    });

    var cur = new Date();
    $("#datepicker_client2").datepicker('setDate', cur);

    $.ajax({
        type: "post",
        url: getRPath()+'/manager/admin/companyCount',
        dataType: "json",
        success: function (data) {
            $("#num1").html(data.num1);
            $("#num2").html(data.num2);
            $("#num3").html(data.num3);
            $("#num4").html(data.num4);
        }
    });

    showFirstMonthDataChart();
    showSecondMonthDataChart();


});

function jieopenurl(){
    window.location = getRPath()+"/manager/torderinfo_todo/manager";
}
function huiopenurl(){
    window.location = getRPath()+"/manager/torderinfo_huif/manager";
}
function payopenurl(){
    window.location = getRPath()+"/manager/torderinfo_pay/manager";
}
function overopenurl(){
    window.location = getRPath()+"/manager/orderCompleted/manager";
}
//月份统计1
function showFirstMonthDataChart() {
    $("#lockerId").val($("#lockerSelect").select2("val"));
    var lockerId=$("#lockerId").val();
    $.ajax({
        type: "post",
        url: getRPath()+'/manager/torderinfo_huif/huiFlock',
        data: {lockerId: lockerId},
        dataType: "json",
        success: function (data) {
            genMonthDataChart(data);
        }
    });
}

function genMonthDataChart(data) {

    var myChart = echarts.init(document.getElementById('MonthTj1'));
    var num1 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var num2 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var num3 = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];
    var name = $(".select2-selection__choice").attr('title');
    if (name===undefined){
        name=""
    }
    $.each(data, function (key, value) {
        if (key === "orders") {
            for (var i = 0; i < 12; i++) {
                if (value[i] !== undefined) {
                    var month = value[i].month;
                    num1.splice(month - 1, 1, value[i].orderNum);
                }
            }

            console.log(value)
        } else if (key === "ordersPass") {
            for (var i = 0; i < 12; i++) {
                if (value[i] !== undefined) {
                    var month = value[i].month;
                    num2.splice(month - 1, 1, value[i].orderNum);
                }
            }
        }
    });

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: "  锁匠"+name+"工单统计",
            x: 'left',
            y: 'top',
            textAlign: 'left'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ["接收单数", "已回访单数"]
        },
        toolbox: {
            show: true,
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: num3
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],

        series: [
            {
                name: "接收单数",
                type: 'bar',
                data: num1

            },
            {
                name: "已完成单数",
                type: 'bar',
                data: num2
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}




//月份统计2
function showSecondMonthDataChart() {
    var seld = $("#datepicker_client2").datepicker('getDate');
    var selectDate = seld.getFullYear();
    $.ajax({
        type: "post",
        url: getRPath()+'/manager/torderinfo_huif/finishLock',
        data: {createTime: selectDate},
        dataType: "json",
        success: function (data) {
            twoMonthDataChart(data);
        }
    });
}


function twoMonthDataChart(data) {

    var myChart = echarts.init(document.getElementById('MonthTj2'));
    var num1 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var num2 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var num3 = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];

    $.each(data, function (key, value) {
        if (key === "orders") {
            for (var i = 0; i < 12; i++) {
                if (value[i] !== undefined) {
                    var month = value[i].month;
                    num1.splice(month - 1, 1, value[i].orderNum);
                }

            }
        } else if (key === "ordersPass") {
            for (var i = 0; i < 12; i++) {
                if (value[i] !== undefined) {
                    var month = value[i].month;
                    num2.splice(month - 1, 1, value[i].orderNum);
                }
            }
        }
    });
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: "  平台发单统计",
            x: 'left',
            y: 'top',
            textAlign: 'left'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ["下发单数","已回访单数"]
        },
        toolbox: {
            show: true,
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: num3
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],

        series: [
            {
                name: "下发单数",
                type: 'bar',
                data: num1
            },
            {
                name: "已作业单数",
                type: 'bar',
                data: num2
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}







