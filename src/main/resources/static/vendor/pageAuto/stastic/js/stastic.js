

$(function() {
	InitQuery_item();

$("#distpicker").distpicker({
	  autoSelect: false
	});

$("#province").change(function(){
	$(this).attr("title",$(this).val());
});
$("#city").change(function(){
	$(this).attr("title",$(this).val());
});
$("#district").change(function(){
	$(this).attr("title",$(this).val());
	$("#areaCode").val($("#district option:selected").attr("data-code"));
});





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

	$("#divauditReason").hide();
	
	function updateNormalValidate(ele, fieldnameOrEle, messagetext, isremove){
		
		
		if (isremove)
			$(ele).bootstrapValidator("removeField", fieldnameOrEle);
		else {
			$(ele).bootstrapValidator("addField", fieldnameOrEle, {
				validators : {
					notEmpty : {
						message : messagetext
					},
				}
			});
		}
	};
	
	$("#mform_item2 #auditFlag").change(function() {
		if($(this).val()=="2")
			{
			$("#divauditReason").show();
			
			updateNormalValidate($("#mform_item2"),$("#auditReason"),"审核原因不能为空",false);
			}
		else{
			$("#divauditReason").hide();
			updateNormalValidate($("#mform_item2"),$("#auditReason"),"审核原因不能为空",true);
		}
		
	});
	
$("#btnSubmit_item_audit").click(function() {
		
		
		
		$("#mform_item2").data('bootstrapValidator').resetForm();
		
		
		 
		    // var bool2 = bv.isValid();
		$("#mform_item2").data("bootstrapValidator").validate();
		// flag = true/false
		var flag = $("#mform_item2").data("bootstrapValidator").isValid();

		var url = getRPath()+"/manager/tcompany/saveOrUpdate";

		if (flag) {
			
			
			cconfirm("确认审核此公司吗?",function(){
				
				var data = $("#mform_item2").serialize();

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
							$('#myModal_item2').modal("hide");
							doSearch_item();
							success("操作成功！");
						} else {
							error( response.message);
						}
					}
				});
			});
			
			
		}
	});
	
	
	$("#btnSubmit_item").click(function() {
		
		
		
		$("#mform_item").data('bootstrapValidator').resetForm();
		
		
		 
		    // var bool2 = bv.isValid();
		$("#mform_item").data("bootstrapValidator").validate();
		// flag = true/false
		var flag = $("#mform_item").data("bootstrapValidator").isValid();

		var url = getRPath()+"/manager/tcompany/saveOrUpdate";

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
	initValidate_item2();




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
				},
			},
			companyPhone:{
				validators : {
					notEmpty : {
						message : '不能为空'
					},
				regexp: {
			          regexp: /^1[1-9]{10}|0\d{2,3}-\d{7,8}$/i,
			          message: '电话为手机号码或者座机电话'
			           }
				}
			},
		district : {
				validators : {
					notEmpty : {
						message : '不能为空'
					},
					 callback: {
						                         message: '不能为空',
						                          callback: function(value, validator) {
						 
						                             if (value == ""||$("#mform_item").find("#city").val()==""||$("#mform_item").find("#province").val()=="") {
						                                return false;
						                             } else {
						                                 return true;
						                             }
						 
						                        }
						                     }
				}
			},
			
		

		}
		

	
	});

}


function initValidate_item2() {
	$("#mform_item2").bootstrapValidator({
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
		url : getRPath()+'/manager/stastic/statusStastic', // 请求后台的URL（*）
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
				
				
				month: $("#q_month").val(),
								
				
			};
			return param;
		},
		columns : [ {
			field : 'id',
			visible : false
		},
		 {
				field : 'month',
				title : '日期',
				align : 'center',
				valign : 'middle',
				   
				
			},
			 {
				field : 'type',
				title : '状态',
				align : 'center',
				valign : 'middle',
				 formatter: function (value, row, index) {
					 //待接单阶段【101~199】, 待作业阶段【201~299】, 待回访阶段【301~399】, 待结账阶段【401~499】,终止状态阶段【501~599】)',
		            if(value=="1")
		            	return "待接单";
		            else if(value=="2")
		            	return "待作业";
		            else  if(value=="3")
		            	return "待回访";
		            else if(value=="4")
		            	return "待结账";
		            else  if(value=="5")
		            	return "已完成";
		         }   
				
			},
		 {
				field : 'num',
				title : '工单数量',
				align : 'center',
				valign : 'middle',
				   
				
			},
		
		 
	

		]
	});
}

function modifyAndDeleteButton_item(value, row, index) {
	return [ '<div class="">'
			+ '<button id = "update" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改</i> </button>&nbsp;'
			+ '<button id = "audit" type = "button" class = "btn btn-warning"><i class="glyphicon glyphicon-check">审核</i> </button>&nbsp;'
			+ '<button id = "delete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
			+ '</div>' ].join("");
}




window.PersonnelInformationEvents_item = {
	"click #update" : function(e, value, row, index) {
		$.ajax({
			type : "post",
			url :getRPath()+ '/manager/tcompany/load',
			data : {
				id : row.id
			},
			async : false,
			dataType : "json",
			success : function(response) {
				
			   $("#mform_item").fill(response);
			   setArea($("#mform_item"),response);
	
			   
			   $("#myModal_item_title").html("编辑");
			   
				$("#myModal_item").modal();
				
			
			}
		});

	},
	"click #audit" : function(e, value, row, index) {
		$.ajax({
			type : "post",
			url :getRPath()+ '/manager/tcompany/load',
			data : {
				id : row.id
			},
			async : false,
			dataType : "json",
			success : function(response) {
				
			   $("#mform_item2").fill(response);
			   setArea($("#mform_item2"),response);
			   $("#auditFlag").val(1);
	
			   
			   $("#mform_item2 #myModal_item_title").html("编辑");
			   
				$("#myModal_item2").modal();
				
			
			}
		});

	},

	"click #delete" : function(e, value, row, index) {
		var msg = "您真的确定要删除吗？";
		var url = getRPath()+"/manager/tcompany/delete";
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
		
	}
};

function doSearch_item() {
	
	
	
	var opt = {
		silent : true,
		pageNumber:1
	};
	$("#table_list_item").bootstrapTable('refresh', opt);
	
	//success("test");
}

