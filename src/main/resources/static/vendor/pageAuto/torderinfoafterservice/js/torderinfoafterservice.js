function test(){


	
	
	var url = getRPath()+"/manager/torderinfoafterservice/test";
	
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

    initDataTimePicker("startTime")
    initDataTimePicker("endTime")
	InitQuery_item();

	$("#btnSubmit_item").click(function() {
		//点击验证
        $("#mform_item_addB").bootstrapValidator('validate');
        var flag = $("#mform_item_addB").data("bootstrapValidator").isValid();
        if(!flag){
            return;
        }


		var url = getRPath()+"/manager/torderinfoafterservice/saveOrUpdate";

		if (flag) {
			var data = $("#mform_item_addB").serialize();

			/**/

			$.ajax({
				type : "post",
				url : url,
				data : data,
				async : false,
				dataType : "json",
				success : function(response) {
					if (response.bol) {
						$('#myModal_item').modal("hide");
						doSearch_item();
						success("操作成功！");
					} else {
						error( response.message);
					}
				}
			});
		}
	});

	initValidate_item();
	




});

$("#btnAdd_item").click(function(){
	$("#myModal_item_title_add").text("添加")
    $("#myModal_item").modal();
});

$("#addBClose").click(function(){
    //关闭清楚
	$("#orderId").val("");
	$("#password").val("");
	$("#description").val("");
	$("#telephone").val("");
	$("#nickname").val("");
	$("#address").val("");
	$("#id").val("");
    $("#mform_item_addB").data('bootstrapValidator').resetForm();
});

$("#editBClose").click(function(){
    //关闭清楚
    $("#mform_item_change").data('bootstrapValidator').resetForm();
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
		url : getRPath()+'/manager/torderinfoafterservice/torderinfoafterserviceList', // 请求后台的URL（*）
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
				
				
				
				orderId : $("#q_orderId").val(),
				password : $("#q_password").val(),
				createTime : $("#q_createTime").val(),
                startTime : $("#startTime").val(),
				endTime : $("#endTime").val(),
				telephone : $("#q_telephone").val(),
				nickname : $("#q_nickname").val(),
				serviceState : $("#q_serviceState").val(),
				

			};
			return param;
		},
		columns : [ {
			field : 'id',
			visible : false
		},
		 {
				field : 'orderId',
				title : '订单编号',
				align : 'center',
				valign : 'middle',
				   
				
			},
		 {
				field : 'password',
				title : '用户密码',
				align : 'center',
				valign : 'middle',
				   
				
			},
		 {
				field : 'description',
				title : '问题描述',
				align : 'center',
				valign : 'middle',
				   
				
			},
		 {
				field : 'telephone',
				title : '联系电话',
				align : 'center',
				valign : 'middle',
				   
				
			},
		 {
				field : 'nickname',
				title : '联系人称呼',
				align : 'center',
				valign : 'middle',
				   
				
			},
		 {
				field : 'address',
				title : '联系人地址',
				align : 'center',
				valign : 'middle',
				   
				
			},
		 {
				field : 'serviceState',
				title : '售后状态',
				align : 'center',
				valign : 'middle',
				 formatter: function (value, row, index) {
					if(value == 0){
						return "已提交";
					}else if(value == 1){
                        return "客服已确认";
					}else if(value == 2){
                        return "已安排师傅处理";
                    }else if(value == 3){
                        return "处理完成";
                    }
				 }
				
			},
            {
                field : 'createTime',
                title : '创建时间',
                align : 'center',
                valign : 'middle',

                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }

            },
		
		
		
		{
			title : '操作',
			field : 'vehicleno',
			align : 'center',
			formatter : modifyAndDeleteButton_item,
			events : PersonnelInformationEvents_item1
		}

		]
	});
}

function modifyAndDeleteButton_item(value, row, index) {
	return [ '<div class="">'
			+ '<button id = "update" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改信息</i> </button>&nbsp;'
			+ '<button id = "updateState" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改状态</i> </button>&nbsp;'
			+ '<button id = "delete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
			+ '</div>' ].join("");
}




window.PersonnelInformationEvents_item1 = {
	"click #update" : function(e, value, row, index) {
		$.ajax({
			type : "post",
			url :getRPath()+ '/manager/torderinfoafterservice/load',
			data : {
				id : row.id
			},
			async : false,
			dataType : "json",
			success : function(response) {
			   $("#mform_item_addB").fill(response);
			     
	
			   
			   $("#myModal_item_title_add").html("编辑");
			   
				$("#myModal_item").modal();
				
			
			}
		});

	},

	"click #delete" : function(e, value, row, index) {
		var msg = "您真的确定要删除吗？";
		var url = getRPath()+"/manager/torderinfoafterservice/delete";
		cconfirm(msg,function() {
			$.ajax({
				type : "post",
				url : url,
				data : {
					"id" : row.id
				},
				success : function(response) {
					if (response.bol) {
						success("删除成功！");
						doSearch_item();
					} else {
						error(""+response.message);
					}
				}
			});
		});
	},

    "click #updateState" : function(e, value, row, index) {
		$("#changeStateId").val(row.id);
		$("#q_serviceState_change").html("");
		var state1="";
		var state2="";
		var state3="";
		var state4="";
        if(row.serviceState == 0){
            state1 = "selected";
        }else if(row.serviceState == 1){
            state2 = "selected";
        }else if(row.serviceState == 2){
            state3 = "selected";
        }else if(row.serviceState == 3){
            state4 = "selected";
        }

        $("#q_serviceState_change").html("<option value=\"0\" "+state1+">已提交</option>\n" +
            "                                        <option value=\"1\" "+state2+">客服已确认</option>\n" +
            "                                        <option value=\"2\" "+state3+">已安排师傅处理</option>\n" +
            "                                        <option value=\"3\" "+state4+">处理完成</option>");
		$("#myModal_item_change").modal();
    }
};

function doSearch_item() {
	
	
	
	var opt = {
		silent : true
	};
	$("#table_list_item").bootstrapTable('refresh', opt);
	
	//success("test");
}


$("#btnSubmit_item_change").click(function(){
    $.ajax({
        type : "post",
        url : getRPath()+"/manager/torderinfoafterservice/saveOrUpdate",
        data : {"id":$("#changeStateId").val(),"serviceState":$("#q_serviceState_change").val()},
        async : false,
        dataType : "json",
        success : function(response) {
            if (response.bol) {
                $('#myModal_item_change').modal("hide");
                doSearch_item();
                success("操作成功！");
            } else {
                error( response.message);
            }
        }
    });
});

