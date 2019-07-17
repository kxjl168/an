function test(){
	
	
	var url = getRPath()+"/manager/companyStatistics/test";
	
	$.ajax({
		type : "post",
		url : url,
		//data : data,
		async : false,
		dataType : "json",
		success : function(response) {
			success("操作成功！");
		}
	});
}

$(function() {
    $("#q_createTime").datepicker({
        startView: 'years',  //起始选择范围
        maxViewMode:'years', //最大选择范围
        minViewMode:'months', //最小选择范围
        todayHighlight : true,// 当前时间高亮显示
        autoclose : 'true',// 选择时间后弹框自动消失
        format : 'yyyy-mm',// 时间格式
        language : 'zh-CN',// 汉化
        todayBtn:"linked",//显示今天 按钮
    });

    var cur = new Date();
    $("#q_createTime").datepicker('setDate', cur);


	InitQuery_item();



    // modal 新增基本字段事件 关闭事件事件， 清空已有的值 恢复禁用
    $('#myModal_item').on('hide.bs.modal', function(e) {

        if($(e.target).attr("type")) //日期选择等弹出框
            return;


        $("#mform_item").data('bootstrapValidator').resetForm();

    });



	initValidate_item();




});




function initValidate_item() {
	$("#mform_item").bootstrapValidator({
		feedbackIcons : {
			/* input状态样式通过，刷新，非法三种图片 */
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		// submitButtons : 'button[type="submit"]',// 提交按钮
		fields : {

				name : {
				validators : {
					notEmpty : {
						message : '不能为空'
					}
				}
			}
			
		

		}
		

	
	});

}


function InitQuery_item() {
	// 初始化Table
    $('#table_list_item').bootstrapTable({
        url : getRPath()+'/manager/LockOrderCount/selectLockOrderBadRatio', // 请求后台的URL（*）
        method : 'post', // 请求方式（*）
        contentType : 'application/x-www-form-urlencoded',
        toolbar : '#toolbar', // 工具按钮用哪个容器
        showHeader : true,
        searchAlign : 'left',
        buttonsAlign : 'left',

        searchOnEnterKey : true,
        striped : true, // 是否显示行间隔色
        cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination : true, // 是否显示分页（*）
        sortable : false, // 是否启用排序
        sortName : 'id', // 排序字段
        sortOrder : "desc", // 排序方式
        sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
        pageNumber : 1, // 初始化加载第一页，默认第一页
        pageSize : 5, // 每页的记录行数（*）
        // pageList : [ 10, 25 ], // 可供选择的每页的行数（*）
        search : false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大

        // showColumns: true, //是否显示所有的列
        uniqueId : "id", // 每一行的唯一标识，一般为主键列
        // queryParamsType : "limit",
        queryParams : function queryParams(params) { // 设置查询参数
            var param = {
                pageSize : params.limit, // 每页要显示的数据条数
                offset : params.offset, // 每页显示数据的开始行号
                sortName : params.sort, // 要排序的字段
                sortOrder : params.order, // 排序规则

                createTime : $("#q_createTime").val()

            };
            return param;
        },
        columns : [ {
            field : 'id',
            visible : false
        },
            {
                field : 'date',
                title : '月份',
                align : 'center',
                valign : 'middle',
                events: event0

            },
            {
                field : 'Name',
                title : '锁匠名',
                align : 'center',
                valign : 'middle',
                events: event0

            },
            {
                field : 'part',
                title : '不良工单数',
                align : 'center',
                valign : 'middle',
                formatter: partItem,
                events: event0

            },
            {
                field : 'count',
                title : '工单总数',
                align : 'center',
                valign : 'middle',
                formatter: countItem,
                events: event0

            },
            {
                field : 'persent',
                title : '月工单不良率',
                align : 'center',
                valign : 'middle',
                formatter: persentItem,
                events: event0

            }
        ]
    });
}


/*function item(value, row, index) {
    return ['<div class="">'
    + '<a href="javascript:void(0);" id="event">'+row.month+'</a>'
    + '</div>'].join("");
}
function item1(value, row, index) {
    return [ '<a href="javascript:void(0);" id="event1">'+(row.num1!=undefined?row.num1:0)+'</a>'].join("");
}*/
function partItem(value, row, index) {
    if(value == null){
        return 0;
    }else{
        return value;
    }
}
function countItem(value, row, index) {
    if(value == null){
        return 0;
    }else{
        return value;
    }
}
function persentItem(value, row, index) {
    if(value == null){
        return "<div style='color:red'>0%</div>";
    }else if(value > 60){
        return value + "%";
    }else{
        return "<div style='color:red'>"+ value +"%</div>";
    }
}

window.event0 = {
    "click #event": function (e, value, row, index) {

        var url=getRPath()+"/manager/companyStatistics/orderList";
        var data={
            month:row.month
        }
        $.ajax({
            type : "post",
            url : url,
            data : data,
            async : false,
            dataType : "json",
            success : function(data) {
                DataChart(data);

                $("#myModal_item").modal();
            }
        });
    },
    "click #event1": function (e, value, row, index) {
        window.location = getRPath()+"/manager/torderinfo_pay/manager?month=" + row.month;
    },
    "click #event2": function (e, value, row, index) {
        window.location = getRPath()+"/manager/orderCompleted/manager?month=" + row.month;
    },
    "click #event3": function (e, value, row, index) {
        function getCountDays() {
            var curDate = new Date(row.month);
            /* 获取当前月份 */
            var curMonth = curDate.getMonth();
            /*  生成实际的月份: 由于curMonth会比实际月份小1, 故需加1 */
            curDate.setMonth(curMonth + 1);
            /* 将日期设置为0, 这里为什么要这样设置, 我不知道原因, 这是从网上学来的 */
            curDate.setDate(0);
            /* 返回当月的天数 */
            return curDate.getDate();
        }
        var time1=row.month+"-01 00:00:00";
        var time2=row.month+"-"+getCountDays()+" 23:59:59";
        window.location = getRPath()+"/manager/torderinfooperatelog/all?&startTime="+time1+"&endTime="+time2;
    }

};

function DataChart(data) {

    var myChart = echarts.init(document.getElementById('MonthTj2'));
    var num1= new Array();
    var num2= new Array();
    var num3= new Array();
    var num4= new Array();
    $.each(data, function (key, value) {
        if (key === "allocated") {
            for (var i = 0; i < value.length; i++) {
                if (value[i] !== undefined) {
                    num1.push(value[i].Name);
                    num2.push(value[i].num)
                }
            }
        }
        if (key === "pay"){
            for (var k = 0; k < num1.length; k++) {
                num4.push("0");
                for(var j = 0;j< value.length; j++){
                    if (value[j] !== undefined && (value[j].Name===num1[k])) {
                        num4[k]=value[j].num
                    }
                }
            }
        }
        if (key === "completed"){
            for (var n = 0; n < num1.length; n++) {
                num3.push("0");
                for(var j = 0;j< value.length; j++){
                    if (value[j] !== undefined && (value[j].Name===num1[n])) {
                        num3[n]=value[j].num
                    }
                }
            }
        }
    });
    // 指定图表的配置项和数据


    var app = {};
    option = null;
    var posList = [
        'left', 'right', 'top', 'bottom',
        'inside',
        'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
        'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
    ];

    app.configParameters = {
        rotate: {
            min: -90,
            max: 90
        },
        align: {
            options: {
                left: 'left',
                center: 'center',
                right: 'right'
            }
        },
        verticalAlign: {
            options: {
                top: 'top',
                middle: 'middle',
                bottom: 'bottom'
            }
        },
        position: {
            options: echarts.util.reduce(posList, function (map, pos) {
                map[pos] = pos;
                return map;
            }, {})
        },
        distance: {
            min: 0,
            max: 100
        }
    };

    app.config = {
        rotate: -50,
        align: 'right',
        verticalAlign: 'middle',
        position: 'insideBottom',
        distance: 0,
        onChange: function () {
            var labelOption = {
                normal: {
                    rotate: app.config.rotate,
                    align: app.config.align,
                    verticalAlign: app.config.verticalAlign,
                    position: app.config.position,
                    distance: app.config.distance
                }
            };
            myChart.setOption({
                series: [{
                    label: labelOption
                }, {
                    label: labelOption
                }, {
                    label: labelOption
                }]
            });
        }
    };


    var labelOption = {
        normal: {
            show: true,
            position: app.config.position,
            distance: app.config.distance,
            align: app.config.align,
            verticalAlign: app.config.verticalAlign,
            rotate: app.config.rotate,
            formatter: '{c}  {name|{a}}',
            fontSize: 16,
            rich: {
                name: {
                    textBorderColor: '#fff'
                }
            }
        }
    };

    option = {
        color: ['#003366', '#006699', '#4cabce'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['已分配', '已完成', '已结账']
        },
        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                axisTick: {show: false},
                data: num1
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '已分配',
                type: 'bar',
                barGap: 0,
                label: labelOption,
                data: num2
            },
            {
                name: '已完成',
                type: 'bar',
                label: labelOption,
                data: num3
            },
            {
                name: '已结账',
                type: 'bar',
                label: labelOption,
                data: num4
            }
        ]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}

function doSearch_item() {
	
	
	
	var opt = {
		silent : true,
        pageNumber:1
	};
	$("#table_list_item").bootstrapTable('refresh', opt);
}


$("#btnImportOut").click(function () {
    window.open(getRPath() + '/manager/LockOrderCount/LockOrderCountExcel?createTime=' + $("#q_createTime").val(),"_blank");
});


