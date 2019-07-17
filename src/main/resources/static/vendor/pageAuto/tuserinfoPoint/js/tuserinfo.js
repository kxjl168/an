function test(){
	
	
	var url = getRPath()+"/manager/tuserinfo/test";
	
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
	InitQuery_item();



	$("#btnAdd_item").click(function() {

	
		  $('#mform_item')[0].reset();
		  
		  $('#mform_item').find("#id").val("");
		  
		$("#myModal_item_title").html("添加");
		
		$("#distpicker2").distpicker({
			  autoSelect: true
			});
		
		$("#myModal_item").modal();
	});

	
	// modal 新增基本字段事件 关闭事件事件， 清空已有的值 恢复禁用
	$('#myModal_item').on('hide.bs.modal', function(e) {
		
		if($(e.target).attr("type")) //日期选择等弹出框
			return;
		

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
			},
			
		

		}
		

	
	});

}



function InitQuery_item() {
	// 初始化Table
	$('#table_list_item').bootstrapTable({
		url : getRPath()+'/manager/tuserinfoPoint/list', // 请求后台的URL（*）
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
		pageSize : 10, // 每页的记录行数（*）
		pageList : [ 10, 25 ], // 可供选择的每页的行数（*）
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


                phone : $("#q_phone").val(),
                name : $("#q_name").val(),
				
				
			};
			return param;
		},
		columns : [ {
			field : 'id',
			visible : false
		},
		 {
			field : 'name',
			title : '锁匠姓名',
			align : 'center',
			valign : 'middle',
			   
			
		}, 
		{
				field : 'phone',
				title : '手机号',
				align : 'center',
				valign : 'middle',
	
			},
		

		 {
				field : 'updateTime',
				title : '上次更新时间',
				align : 'center',
				valign : 'middle',
				visible : false
				
			},
		 {
				field : 'updateName',
				title : '上次更新人',
				align : 'center',
				valign : 'middle',
				visible : false

			},
            {
                field : 'userScore',
                title : '工单平均评分',
                align : 'center',
                valign : 'middle',


            },
            {
                title : '操作',
                field : 'vehicleno',
                align : 'center',
                formatter : modifyAndDeleteButton_item,
                events : PersonnelInformationEvents_item
            }
		


		]
	});
}

function modifyAndDeleteButton_item(value, row, index) {
	return [ '<div class="">'
			+ '<button id = "more" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">工单评分详细</i> </button>&nbsp;'
			+ '</div>' ].join("");
}

/**
 * 工单类型
 * @param value
 * @returns
 */
function orderTypeNameNew(value) {
	var type="";
    if (value .indexOf('19')>-1) {
        value=value.replace(19,9);
        type+= "其他,";//  "其他"
    }
    if (value .indexOf('0')>-1) {
    	type+= "安装,";
    }
    if (value .indexOf('1')>-1) {
    	type+= "维修,"; 
    }
    if (value .indexOf('2')>-1) {
    	type+= "开锁,";  //"开锁"
    }
    if (value .indexOf('3')>-1) {
    	type+= "特殊门改造,"; // "特殊门改造"
    }
    if (value .indexOf('4')>-1) {
    	type+= "测量,"; // "测量"
    }
    if (value .indexOf('5')>-1) {
    	type+= "猫眼安装,";//  "猫眼安装"
    }
    if (value .indexOf('6')>-1) {
        type+= "换锁,";//  "换锁"
    }
    if (value .indexOf('7')>-1) {
        type+= "工程安装,";//  "工程安装"
    }
    if (value .indexOf('8')>-1) {
        type+= "工程维修,";//  "工程维修"
    }

    type=type.substr(0,type.length-1);
    
    return type;
}


function InitQuery_item1() {
    $('#table_list_item1').bootstrapTable({
        url : getRPath()+'/manager/tuserinfoPoint/pointList', // 请求后台的URL（*）
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
        pageSize : 10, // 每页的记录行数（*）
        pageList : [ 10, 25 ], // 可供选择的每页的行数（*）
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



                id : $("#q_id").val()

            };
            return param;
        },
        columns : [ {
            field : 'id',
            visible : false
        },

            {
                field : 'orderNo',
                title : '订单编号',
                align : 'center',
                valign : 'middle',


            },
            {
                field : 'serverType',
                title : '服务类型',
                align : 'center',
                valign : 'middle',
                formatter: function (value, row, index) {
                	
                  	return orderTypeNameNew(value);
                	
                    if (value==0){
                        return "安装"
                    }else if(value==1){
                        return "维修"
                    }else if(value==2){
                        return "开锁"
                    }else if(value==3){
                        return "特殊门改造"
                    }else if(value==4){
                        return "测量"
                    }else if(value==5){
                        return "猫眼安装"
                    }else if(value==6){
                        return "换锁"
                    }else if(value==7){
                        return "工程安装"
                    }else if(value==8){
                        return "工程维修"
                    }else if(value==9){
                        return "其他"
                    }
                }

            },
            {
                field : 'orderState',
                title : '工单状态',
                align : 'center',
                valign : 'middle',
                formatter: function (value, row, index) {
                    return orderStatusTypeName(value);
                }
            },
            {
                field : 'doneTime',
                title : '订单完成时间',
                align : 'center',
                valign : 'middle',
                formatter: function (value, row, index) {
                	if(value)
                        return value.split(".")[0]
                    	else
                    		return "";
                	
//                    return value;// new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }

            },

          
            {
                field : 'serviceScore',
                title : '服务分数',
                align : 'center',
                valign : 'middle'
            }

        ]
    });

}



window.PersonnelInformationEvents_item = {
	"click #more" : function(e, value, row, index) {
		$('#q_id').val(row.id);
        InitQuery_item1();
        refresh_item();
        $("#myModal_item").modal();
    }
};
function refresh_item() {

    var opt = {
        silent : true
    };
    $("#table_list_item1").bootstrapTable('refresh', opt);

}
function doSearch_item() {
	
	
	
	var opt = {
		silent : true,
        pageNumber:1
	};
	$("#table_list_item").bootstrapTable('refresh', opt);
	
	//success("test");
}

function orderStatusTypeName(value) {
    if (value == 3) {
        return "<span class='auditstyle' >待平台确认</span>"
    }
    else if (value == 1) {
        return "<span class='auditstyle'>加钱待平台审核</span>"
    } else if (value == 2) {
        return "<span class='auditstyle' >退单待平台审核</span>"
    } else if (value == 4) {
        return "<span class='auditstyle'>平台申请待锁企审核</span>"
    } else if (value == 5) {
        return "<span class='auditstyle'>锁企不通过待平台处理</span>"
    }
    else if (value == 6) {
        return "<span class='auditstyle'>加钱待合伙人审核</span>"
    }
    else if (value >= 101 && value <= 199) {
        return "<span class='normalstyle' >待接单</span>"
    }
    else if (value >= 201 && value <= 299) {

        if (value ==210) {
            return "<span  class='comfirmstyle' >待作业(已确认)</span>"
        }
        else  if (value ==213) {
            return "<span  class='comfirmstyle' >已预约</span>"
        }
        else if (value ==214) {
            return "<span  class='backstyle' >预约失败</span>"
        }
        else  if (value ==255) {
            return "<span  class='backstyle' >待作业(回访问题单)</span>"
        }
        else
            return "<span  class='normalstyle' >待作业</span>"
    }
    else if (value >= 301 && value <= 399) {
        return "<span  class='normalstyle' >待回访</span>"
    }
    else if (value >= 401 && value <= 499) {
        return "<span  class='normalstyle' >待结账</span>"
    }
    else if (value >= 501 && value <= 599) {
        return "<span  class='donestyle' >已完结</span>"
    }
}