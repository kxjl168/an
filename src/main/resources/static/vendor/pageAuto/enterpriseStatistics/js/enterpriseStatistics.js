function test(){
	
	
	var url = getRPath()+"/manager/enterpriseStatistics/test";
	
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
        format: 'yyyy',
        language: 'zh-CN',
        minViewMode: 2,
        autoclose: true
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
        url : getRPath()+'/manager/enterpriseStatistics/enterpriseList', // 请求后台的URL（*）
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
                title : '月份',
                align : 'center',
                valign : 'middle',
                formatter: item,
                events: event0

            },
            {
                title : '待结账工单数',
                align : 'center',
                valign : 'middle',
                formatter: item1,
                events: event0

            },
            {
                title : '已结算工单数',
                align : 'center',
                valign : 'middle',
                formatter: item2,
                events: event0

            },
            {
                title : '总单数',
                align : 'center',
                valign : 'middle',
                formatter: item3,
                events: event0

            },
            {
                title : '总支出金额',
                align : 'center',
                valign : 'middle',
                formatter: item4,
                events: event0

            }
        ]
    });
}


function item(value, row, index) {
    return ['<div class="">'
    + '<a href="javascript:void(0);" id="event">'+row.month+'</a>'
    + '</div>'].join("");
}

function item1(value, row, index) {
    return ['<div class="">'
    + '<a href="javascript:void(0);" id="event1">'+(row.num1!=undefined?row.num1:0)+'</a>'
    + '</div>'].join("");
}
function item2(value, row, index) {
    return ['<div class="">'
    + '<a href="javascript:void(0);" id="event2">'+(row.num2!=undefined?row.num2:0)+'</a>'
    + '</div>'].join("");
}
function item3(value, row, index) {
    return ['<div class="">'
    + '<a href="javascript:void(0);" id="event3">'+(row.num3!=undefined?row.num3:0)+'</a>'
    + '</div>'].join("");
}
function item4(value, row, index) {
    return ['<div class="">'
    + (row.num4!=undefined?row.num4:0)
    + '</div>'].join("");
}

window.event0 = {
    "click #event": function (e, value, row, index) {

        var url=getRPath()+"/manager/torderAreaStatistics/areaStatisticsList";
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
        window.location = getRPath()+"/manager/torderinfo_pay/manager1?month=" + row.month+"&finishedState=0";
    },
    "click #event2": function (e, value, row, index) {
        window.location = getRPath()+"/manager/torderinfo_pay/manager1?month=" + row.month+"&finishedState=1";
    },
    "click #event3": function (e, value, row, index) {
        window.location = getRPath()+"/manager/torderinfo_pay/manager1?month=" + row.month;
    }

};

function DataChart(data) {

    var myChart = echarts.init(document.getElementById('MonthTj2'));
    var num1= new Array();
    var num2= new Array();
    var num3;
    var month;
    $.each(data, function (key, value) {
        if (key === "rows") {
            for (var i = 0; i < value.length; i++) {
                if (value[i] !== undefined) {
                    num1.push(value[i].custAddress);
                    num3={name:value[i].custAddress,value:value[i].num};
                    num2.push(num3);
                    month=value[i].month
                }
            }

        }
    });
    // 指定图表的配置项和数据

 var option = {
        title : {
            text:month!=undefined?month+ '工单来源地区分布':'当月地区未有完成订单',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: num1
        },
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:num2,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    ;
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
    window.open(getRPath() + '/manager/enterpriseStatistics/enterpriseExcel',"_blank");
});