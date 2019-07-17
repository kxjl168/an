



$(function () {
    initManagerLockCompanySelect();

    /*if ($("#enterpriseSelect").length > 0) {
        initLockCompanySelect("enterpriseSelect", "enterpriseId")
    }*/
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

    $("#datepicker_client2").datepicker({
        format: 'yyyy年',
        language: 'zh-CN',
        minViewMode: 2,
        autoclose: true
    });

    var cur = new Date();
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
function userinfo(){
	  var time1=$("#startTime").val()+" 00:00:00";
	    var time2=$("#endTime").val()+" 23:59:59";
    window.location = getRPath()+"/manager/tuserinfo/managerPass?startTime="+time1+"&endTime="+time2;
}
function pay(){
    window.location = getRPath()+"/manager/torderinfo_pay/manager1?finishedState="+0;
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
function toOrderHuifang(){
    window.location = getRPath()+"/manager/torderinfo_huif/manager";
}
function toOrderBack(){
    window.location = getRPath()+"/manager/tuserbackquestion/manager?serviceState=0";
}



//月份统计1
function showFirstMonthDataChart() {
    $("#enterpriseId").val($("#enterpriseSelect").select2("val"));

    var enterpriseId=$("#enterpriseId").val();

    $.ajax({
        type: "post",
        // url: getRPath()+'/manager/torderinfooperatelog/orderEnterpriseYear',
        data: {SellerId: enterpriseId},
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
            text: "  发单年度统计",
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
        // url: getRPath()+'/manager/torderinfooperatelog/orderEnterprise',
        data: {operateTime: selectDate},
        dataType: "json",
        success: function (data) {
            twoMonthDataChart(data);
        }
    });
}


function twoMonthDataChart(data) {

    var myChart = echarts.init(document.getElementById('MonthTj2'));
    var num1= new Array();
    var num2= new Array();
    var num3= new Array();

    $.each(data, function (key, value) {
        if (key === "orders") {
            for (var i = 0; i < value.length; i++) {
                num1.push(value[i].orderNum);
                num3.push(value[i].EnterpriseName);
            }
        } else if (key === "ordersPass") {
            for (var i = 0; i < value.length; i++) {
                num2.push(value[i].orderNum);
            }
        }
    });
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: "  锁企发单总数统计",
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
        url: getRPath()+'/manager/admin/normalCount',
        dataType: "json",
        data:data,
        success: function (data) {



            $("#numToConfirm").html(data.numToConfirm);
            $("#numToDo").html(data.numToDo);
            $("#numDoing").html(data.numDoing);
            $("#numAudit").html(data.numAudit);
            $("#numHuifang").html(data.numHuifang);
            $("#numBack").html(data.numBack);

            $("#numAfter").html(data.numAfter);
        }
    });
}




