



$(function () {


    $("#startTime").datepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN',
        minViewMode: 0,
        todayBtn: 'linked',
        autoclose: true,
        clearBtn: true

    });
    $("#endTime").datepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN',
        minViewMode: 0,
        autoclose: true,
        todayBtn: 'linked',
        clearBtn: true
    });

    $("#datepicker_client1").datepicker({
        format: 'yyyy年',
        language: 'zh-CN',
        minViewMode: 2,
        autoclose: true
    });
    $("#datepicker_client2").datepicker({
        format: 'yyyy年',
        language: 'zh-CN',
        minViewMode: 2,
        autoclose: true
    });

    var cur = new Date();
    $("#datepicker_client1").datepicker('setDate', cur);
    $("#datepicker_client2").datepicker('setDate', cur);
    $("#startTime").datepicker('setDate', cur);
    $("#endTime").datepicker('setDate', cur);


    doSearch_item();
    showFirstMonthDataChart();
    showSecondMonthDataChart();


});

function openurlCreate(type,subtype,choice){
    // startTime: $("#startTime").val(),
    //endTime: $("#endTime").val()
    var time1=$("#startTime").val()+" 00:00:00";
    var time2=$("#endTime").val()+" 23:59:59";
    window.location = getRPath()+"/manager/torderinfooperatelog/all?q_operlogType="+type+"&q_operlogSubType="+subtype+"&startTime="+time1+"&endTime="+time2+"&q_choice="+choice;
}


function openurl(type,subtype,choice){
    // startTime: $("#startTime").val(),
    //endTime: $("#endTime").val()
    var time1=$("#startTime").val()+" 00:00:00";
    var time2=$("#endTime").val()+" 23:59:59";
    window.location = getRPath()+"/manager/torderinfooperatelog/all?q_operlogType="+type+"&q_operlogSubType="+subtype+"&logstartTime="+time1+"&logendTime="+time2+"&q_choice="+choice;
}
function pay(){
    window.location = getRPath()+"/manager/torderinfo_pay/manager1?finishedState="+0;
}
function toDoing(){
    window.location = getRPath()+"/manager/torderinfo_doing/manager?q_type=10";
}
function toOrderConfirm(){
    window.location = getRPath()+"/manager/orderInfo/index";
}
function toOrderTodo(){
    window.location = getRPath()+"/manager/torderinfo_todo/manager";
}

function toOrderDoing(){
    window.location = getRPath()+"/manager/torderinfo_doing/manager";
}
function toOrderAudit(){
    window.location = getRPath()+"/manager/torderreview/manager";
}
function toOrderHuiche(){
    window.location = getRPath()+"/manager/torderreview/dellist";
}
function toOrderCompleted(){
    window.location = getRPath()+"/manager/orderCompleted/manager";
}
function toOrderBack(){
    window.location = getRPath()+"/manager/tuserbackquestion/manager?serviceState=0";
}



//月份统计1
function showFirstMonthDataChart() {
    var seld = $("#datepicker_client1").datepicker('getDate');
    var selectDate = seld.getFullYear();

    $.ajax({
        type: "post",
        url: getRPath()+'/manager/torderinfooperatelog/orderTotals',
        data: {operateTime: selectDate},
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
            text: "  发单月份统计",
            x: 'left',
            y: 'top',
            textAlign: 'left'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ["发单总数", "审核通过数"]
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
                name: "发单总数",
                type: 'bar',
                data: num1
            },
            {
                name: "审核通过数",
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
        url: getRPath()+'/manager/torderinfoaudit/orderPlus',
        data: {proposTime: selectDate},
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
            text: "  附加费工单统计",
            x: 'left',
            y: 'top',
            textAlign: 'left'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ["申请单数","审核通过数"]
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
                name: "申请单数",
                type: 'bar',
                data: num1
            },
            {
                name: "审核通过数",
                type: 'bar',
                data: num2
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}


function doSearch_item() {

    var data={
        startTime: $("#startTime").val(),
        endTime: $("#endTime").val()
    };
    $.ajax({
        type: "post",
        url: getRPath()+'/manager/admin/enterpriseCount',
        data:data,
        dataType: "json",
        success: function (data) {


            $("#numToConfirm").html(data.numToConfirm);
            $("#numToDo").html(data.numToDo);
            $("#numDoing").html(data.numDoing);
            $("#numAudit").html(data.numAudit);
            $("#numHuiche").html(data.numHuiche);
            $("#numCompleted").html(data.numCompleted);
            $("#numBack").html(data.numBack);

            $("#numAfter").html(data.numAfter);
        }
    });

}


