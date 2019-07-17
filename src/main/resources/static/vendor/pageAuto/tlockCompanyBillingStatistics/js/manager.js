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

    $.ajax({
        type: "post",
        url: getRPath() + '/manager/lockCompanyStatistics/orderCountList',
        data: {
            creatTime: $("#q_month").val(),
            id: $("#lockEnterpriseID").val()[0]
        },
        dataType: "json",
        success: function (data) {
            DataChart(data);
        }
    });
}

function DataChart(data) {

    var myChart = echarts.init(document.getElementById('MonthTj1'));

    var statisticsData = dealData(data);
    // 指定图表的配置项和数据
    var day = new Array();
    var value = new Array();

    for (var index in statisticsData){

        day.push((parseInt(index)+1)+'号');

        value.push(statisticsData[parseInt(index)][parseInt(index)+1]);
    }

    option = {
        title: {
            text: '月发单量统计',
            subtext: '订单数量'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['订单数量']
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
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
                boundaryGap: false,
                data: day
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ],
        series: [
            {
                name: '订单数量',
                type: 'line',
                data: value,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                }
            }
        ]
    };

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}

function doSearch_item() {

    InitQuery_item();
}


function dealData(data) {

    var resultData = getMouthDayArr();

    for (var item in data) {

        var currentDate = data[item].creatTime.split('-');
        var temp = currentDate[2].split(' ');
        var day = temp[0]

        var dic = resultData[parseInt(day)-1];
        dic[parseInt(day)] = parseInt(dic[parseInt(day)]) + parseInt(data[item].orderCount);
    }

    return resultData;
}

function getMouthDayArr(data) {

    var currentDate = $("#q_month").val().split('-');
    var yaer = currentDate[0];
    var mouth = currentDate[1];
    var dayCount = 0;

    switch (parseInt(mouth)) {

        case 1:
            dayCount = 31;
            break;
        case 2: {

            if (isLeapYear(parseInt(yaer))) {

                dayCount = 29;
            } else {

                dayCount = 28;
            }
        }
            break;
        case 3:
            dayCount = 31;
            break;
        case 4:
            dayCount = 30;
            break;
        case 5:
            dayCount = 31;
            break;
        case 6:
            dayCount = 30;
            break;
        case 7:
            dayCount = 31;
            break;
        case 8:
            dayCount = 31;
            break;
        case 9:
            dayCount = 30;
            break;
        case 10:
            dayCount = 31;
            break;
        case 11:
            dayCount = 30;
            break;
        case 12:
            dayCount = 31;
            break;
    }

    var resultData = new Array();

    for (var i = 1, count = dayCount; i <= count; i++) {

        var dic = {};
        dic[i.toString()] = 0;
        resultData.push(dic);
    }

    return resultData;
}

function isLeapYear(year) {
    var cond1 = year % 4 == 0;  //条件1：年份必须要能被4整除
    var cond2 = year % 100 != 0;  //条件2：年份不能是整百数
    var cond3 = year % 400 == 0;  //条件3：年份是400的倍数
    //当条件1和条件2同时成立时，就肯定是闰年，所以条件1和条件2之间为“与”的关系。
    //如果条件1和条件2不能同时成立，但如果条件3能成立，则仍然是闰年。所以条件3与前2项为“或”的关系。
    //所以得出判断闰年的表达式：
    var cond = cond1 && cond2 || cond3;
    if (cond) {
        alert(year + "是闰年");
        return true;
    } else {
        alert(year + "不是闰年");
        return false;
    }
}