function test(){
	
	
	var url = getRPath()+"/manager/torderinfooperatelog/test";
	
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
	$('#q_orderNo').val(GetQueryString("id"));

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
		
		  $('#mform_item')[0].reset();

		$("#mform_item").data('bootstrapValidator').resetForm();

	});


	$("#btnSubmit_item").click(function() {
		
		
		
		$("#mform_item").data('bootstrapValidator').resetForm();
		
		
		 
		    // var bool2 = bv.isValid();
		$("#mform_item").data("bootstrapValidator").validate();
		// flag = true/false
		var flag = $("#mform_item").data("bootstrapValidator").isValid();

		var url = getRPath()+"/manager/torderinfooperatelog/saveOrUpdate";

		if (flag) {
			var data = $("#mform_item").serialize();

			/**/

			$.ajax({
				type : "post",
				url : url,
				data : data,
				async : false,
				dataType : "json",
				success : function(response) {
					// debugger;
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
		url : getRPath()+'/manager/torderinfooperatelog/torderinfooperatelogList', // 请求后台的URL（*）
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

				orderNo : $("#q_orderNo").val(),
				type : $("#q_type").val(),
				content : $("#q_content").val(),
				
				
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
				field : 'type',
				title : '操作类型',
				align : 'center',
				valign : 'middle',
             formatter: function (value, row, index) {
            	 if (value==0){
            		 return "锁企操作"
            	 }
            	 else if (value==1){
						return "客服操作"
					}else if (value==2) {
						return "锁匠操作"
					}
                }
             },
		 {
				field : 'subType',
				title : '服务内容',
             	align : 'center',
				valign : 'middle',
             formatter: function (value, row, index) {
            	 if (row.type==0){
            		 if (value==1){
                         return "新建下发"
                     }else if(value==2){
                         return "审核加钱"
                     
                     }
            	 }
            	 else if (row.type==1){
            		  if (value==0){
                          return "创建工单"
            		  }   
            		  else if (value==1){
                            return "派单"
                        }else if(value==2){
                            return "审核"
                        }else if(value==3){
                            return "回访评分"
                        }
                        else if(value==4){
                            return "结账"
                        }else if(value==5){
                            return "确认锁企工单"
                        }else if(value==6){
                          return "锁企打款"
                      }
					}else if(row.type==2) {
						if (value==1){
							return "接单"
						}else if(value==2){
							return "申请加钱"
						}else if(value==3) {
							return "申请退单"
						}else if(value==4) {
                            return "确认完成"
                        }
					}
			    }
			},
		 {
				field : 'content',
				title : '详细内容',
				align : 'center',
				valign : 'middle',
				   
				
			},
		 {
				field : 'operateUserName',
				title : '操作人',
				align : 'center',
				valign : 'middle',
				   
				
			},
		 {
				field : 'operateTime',
				title : '操作时间',
				align : 'center',
				valign : 'middle',
             formatter: function (value, row, index) {
                 return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
             }
				
			},

		]
	});
}


function doSearch_item() {
	
	
	
	var opt = {
		silent : true,
        pageNumber:1
	};
	$("#table_list_item").bootstrapTable('refresh', opt);
	
	//success("test");
}

