/**
 * 
 */


//月份统计2
function showPieChart() {
	var startDate=$("#startTime").val();
	var endDate=$("#endTime").val();
    $.ajax({
        type: "post",
        url: getRPath()+'/manager/videoalarmerrorinfo/allTotal',
        data: {startDate: startDate,
        	endDate:endDate,
        },
        dataType: "json",
        success: function (data) {
        	if(data.errCode=="1")
        		allTotalChart(data.data);
        }
    });
}


function allTotalChart(data) {

    var myChart = echarts.init(document.getElementById('piechart'));
    var num1 = [];
 
    $.each(data, function (key, item) {
       
    	num1.push({name:item.dictName,value:item.dictValue});
    });
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: " 异常接警总数统计",
            x: 'left',
            y: 'top',
            textAlign: 'left'
        },
        tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
       
        legend: {
            data: ["分类统计"]
        },
        toolbox: {
            show: true,
            feature: {
                dataView: {show: false, readOnly: false},
                magicType: {show: false, type: ['line', 'bar']},
                restore: {show: false},
                saveAsImage: {show: false}
            }
        },
        calculable: true,
       /* xAxis: [
            {
                type: 'category',
                data: num3
            }
        ],*/
       /* yAxis: [
            {
                type: 'value'
            }
        ],*/

        series: [
            {
                name: "异常接警",
                type: 'pie',
                data: num1
            },
           
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}


//月份统计2
function showLineChart() {
	var startDate=$("#startTime").val();
	var endDate=$("#endTime").val();
    $.ajax({
        type: "post",
        url: getRPath()+'/manager/videoalarmerrorinfo/dayTotal',
        data: {startDate: startDate,
        	endDate:endDate,
        },
        dataType: "json",
        success: function (data) {
        	if(data.errCode=="1")
        		lineChart(data.data);
        }
    });
}


//计算两个日期的天数差
function dateDiff(firstDate,secondDate){
var firstDate = new Date(firstDate);
var secondDate = new Date(secondDate);
var diff = Math.abs(firstDate.getTime() - secondDate.getTime())
var result = parseInt(diff / (1000 * 60 * 60 * 24));
return result
}

//方法 增添dayNumber天（整形），date：如果没传就使用今天（日期型）
       function addDay(dayNumber, date) {
         date = date ? date : new Date();
         var ms = dayNumber * (1000 * 60 * 60 * 24)
         var newDate = new Date(date.getTime() + ms);
         return newDate;
       }

function getNewDateStr(strdateStart,addnum)
{
	 var datetarget=addDay(addnum,new Date(strdateStart));
	 return datetarget.Format("yyyy-MM-dd");
}


function getvalue(datalist,dateStr,type)
{
	for (var i = 0; i < datalist.length; i++) {
		if(datalist[i]['date']==dateStr)
			return datalist[i][type];
		
	}
	

		return 0;
	}

function lineChart(data) {


		
	var startDate=new Date( $("#startTime").val()).Format("yyyy-MM-dd");
	
	var endDate=new Date( $("#endTime").val()).Format("yyyy-MM-dd");
	
	var num=dateDiff(startDate,endDate)+1;
	
	 var num1 = [];
	    var num2 = [];
	    var num3 = [];//['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];

	for (var i = 0; i < num; i++) {
		var cdate=getNewDateStr(startDate,i );
		num3.push( cdate);
		
		var value1=getvalue(data,cdate,1);
		var value2=getvalue(data,cdate,2);
		num1.push(value1);
		num2.push(value2);
	}
	
    var myChart = echarts.init(document.getElementById('linechart'));
   
   
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: " 异常接警天统计",
            x: 'left',
            y: 'top',
            textAlign: 'left'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ["坐席繁忙","超时未接"]
        },
        toolbox: {
            show: true,
            feature: {
                dataView: {show: false, readOnly: false},
                magicType: {show: false, type: ['line', 'bar']},
                restore: {show: false},
                saveAsImage: {show: false}
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
                name: "坐席繁忙未接",
                type: 'bar',
                data: num1
            },
            {
                name: "超时未接",
                type: 'bar',
                data: num2
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}
