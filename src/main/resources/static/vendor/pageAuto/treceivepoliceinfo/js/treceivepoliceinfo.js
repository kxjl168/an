function test(){
	
	
	var url = getRPath()+"/manager/treceivepoliceinfo/test";
	
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


var selectUnitId = null;
var selectAreaId = null;
var selectSeatId = null;
function treeClick() {

	var menuids = "";
	var zTree = $.fn.zTree.getZTreeObj("Areatree");
	if (zTree != null) {
		var nodes = zTree.getSelectedNodes();
		if (nodes != null) {
			var selectNd = nodes[0];
			if (selectNd.level == 0) {
				selectUnitId = selectNd.id;
				selectAreaId = null;
				selectSeatId=null;

			}

			else if (selectNd.level == 1) {
				selectUnitId = null;
				selectAreaId = selectNd.id;
				selectSeatId=null;
			}
			else if (selectNd.level == 2) {
				selectUnitId = null;
				selectAreaId = null;
				selectSeatId = selectNd.id;
			}
			

			doSearch_item();
			
		}
	}

}

$(function() {
	InitQuery_item();

	loadAreaTree(treeClick,3);
	initUnitAreaSelect("seatId",3);


	$("#btnAdd_item").click(function() {

		$("#seatId").select2().val(null).trigger("change");
		$("#seatId").select2("destroy");
		$("#seatId").html("");
		initUnitAreaSelect("seatId",3);
		
		$('#mform_item')
		.bootstrapValidator(
				"addField",
				'password',
				{
					validators : {
						notEmpty : {
							message : '密码不能为空'
						},
						 callback: {
		                        message: '密码长度不能小于6位',
		                        trigger: 'change',
		                        callback: function (value, validator) {
		                        	if(value)
		                        		{
		                            if (value.length < 6) {
		                                return false
		                            }
		                        		}
		                            return true
		                        }
		                    }

					}
				});
		
		 $("#passtip").html("");
		  
		  
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

		var url = getRPath()+"/manager/treceivepoliceinfo/saveOrUpdate";

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
					  notEmpty: {
	                        message: '不能为空'
	                    },
	                    callback: {
	                        message: '字符长度不能大于30',
	                        trigger: 'change',
	                        callback: function (value, validator) {
	                            if (value.length > 30) {
	                                return false
	                            }
	                            return true
	                        }
	                    }
				}
			},
			
		
			des: {
                validators: {
                   
                    callback: {
                        message: '字符长度不能大于300',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 300) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            
/*
			password: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '密码长度不能小于6位',
                        trigger: 'change',
                        callback: function (value, validator) {
                        	if(value)
                        		{
                            if (value.length < 6) {
                                return false
                            }
                        		}
                            return true
                        }
                    }
                }
            },*/
            phone: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    regexp: {
                        regexp: /^1[0-9]{10}$|0\d{2,3}-\d{7,8}$/i,
                        message: '电话为手机号码或者座机电话'
                    }
                }
            },
            
            

            seatId: {
				validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    
                }
            },
            idNo: {
				validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    
                }
            },
		

		}
		

	
	});

}



function InitQuery_item() {
	// 初始化Table
	$('#table_list_item').bootstrapTable({
		url : getRPath()+'/manager/treceivepoliceinfo/treceivepoliceinfoList', // 请求后台的URL（*）
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
				idNo : $("#q_idNo").val(),
				
				unitId: selectUnitId ,
				areaId: selectAreaId ,
				seatId:selectSeatId,
				
			};
			return param;
		},
		columns : [ {
			field : 'id',
			visible : false
		},
		{
			field : 'idNo',
			title : '工号',
			align : 'center',
			valign : 'middle',
			   
			
		},
		 {
				field : 'phone',
				title : '接警人员手机号',
				align : 'center',
				valign : 'middle',
				   
				
			},
		 
		
		 {
				field : 'name',
				title : '名称',
				align : 'center',
				valign : 'middle',
				   
				
			},
			 {
				field : 'unitName',
				title : '单位',
				align : 'center',
				valign : 'middle',
				   
				
			},
			 {
				field : 'areaName',
				title : '片区',
				align : 'center',
				valign : 'middle',
				   
				
			},
			 {
				field : 'seatName',
				title : '坐席',
				align : 'center',
				valign : 'middle',
				   
				
			},
			 {
				field : 'des',
				title : '备注',
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
			+ '<button id = "update" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改</i> </button>&nbsp;'
			+ '<button id = "delete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
			+ '</div>' ].join("");
}




window.PersonnelInformationEvents_item = {
	"click #update" : function(e, value, row, index) {
		$.ajax({
			type : "post",
			url :getRPath()+ '/manager/treceivepoliceinfo/load',
			data : {
				id : row.id
			},
			async : false,
			dataType : "json",
			success : function(response) {
				
			   $("#mform_item").fill(response);
			     
	
			  $("#password").val("");
			  
			  
				$("#seatId").select2().val(null).trigger("change");
				$("#seatId").select2("destroy");
				$("#seatId").html("");
				
				// unitAdmin
				if (response) {
					var data = response;
					var pname = data.seatName;
					var option = new Option(pname, data.seatId, true, true);
					$("#seatId").append(option).trigger('change');
					$("#seatId").trigger({
						type : 'select2:select',
						params : {
							data : {
								text : pname,
								id : data.seatId
							}
						}
					});
				}
				
				initUnitAreaSelect("seatId",3);
				
				  $('#mform_item').bootstrapValidator('removeField',
					'password');

	           $("#passtip").text("若不填则保持原密码不变")
			   
			   
			   $("#myModal_item_title").html("编辑");
			   
				$("#myModal_item").modal();
				
			
			}
		});

	},

	"click #delete" : function(e, value, row, index) {
		var msg = "您真的确定要删除吗？";
		var url = getRPath()+"/manager/treceivepoliceinfo/delete";
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
		silent : true
	};
	$("#table_list_item").bootstrapTable('refresh', opt);
	
	//success("test");
}

