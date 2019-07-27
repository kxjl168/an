function test(){
	
	
	var url = getRPath()+"/manager/tunitinfo/test";
	
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

function refreshParentDv(){
	var utype= $("#unitType").val();
	if(utype=="1")
		{
		$(".parentUnitDv").addClass("hide")
		try {
			$('#mform_item').bootstrapValidator('removeField',
			'parentUnit');
		} catch (e) {
			// TODO: handle exception
		}
		}
	else{
		$(".parentUnitDv").removeClass("hide");
		
		try {
			$('#mform_item').bootstrapValidator('removeField',
			'parentUnit');
		} catch (e) {
			// TODO: handle exception
		}
		try {

			// 验证密码
			$('#mform_item')
					.bootstrapValidator(
							"addField",
							'parentUnit',
							{
								validators : {
									  notEmpty: {
					                        message: '不能为空'
					                    },
								}
							});
		} catch (e) {
			// TODO: handle exception
		}
	

		
		
	}
}

$(function() {
	InitQuery_item();

	
	initAdminSelect("unitAdmin");
	initUnitSelect("parentUnit");
	
	$("#unitType").change(function(){
		refreshParentDv();
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


	$("#btnSubmit_item").click(function() {
		
		
		
		$("#mform_item").data('bootstrapValidator').resetForm();
		
		
		 
		    // var bool2 = bv.isValid();
		$("#mform_item").data("bootstrapValidator").validate();
		// flag = true/false
		var flag = $("#mform_item").data("bootstrapValidator").isValid();

		var url = getRPath()+"/manager/tunitinfo/saveOrUpdate";

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
	
	
$("#btnUnitAdmin").click(function() {
		
		
		
		var url = getRPath()+"/manager/tunitinfo/updateManager";

		
			var data = $("#mform_item_admin").serialize();
			
			data+="&adminlist="+$("#unitAdmin").val().toString();
	
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
						$('#myModal_item_admin').modal("hide");
						doSearch_item();
						success("操作成功！");
					} else {
						error( response.message);
					}
				}
			});
		
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
            

			contactPerson: {
                validators: {
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
            contactPhone: {
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
            
            

            address: {
				validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '字符长度不能大于50',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 50) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
			
		

		}
		

	
	});

}



function InitQuery_item() {
	// 初始化Table
	$('#table_list_item').bootstrapTable({
		url : getRPath()+'/manager/tunitinfo/tunitinfoPageList', // 请求后台的URL（*）
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
				
				
				
				name : $("#q_name").val(),
				createTime : $("#q_createTime").val(),
				
				
			};
			return param;
		},
		columns : [ {
			field : 'id',
			visible : false
		},
		 {
				field : 'name',
				title : '单位名称',
				align : 'center',
				valign : 'middle',
			},
			{
				field : 'unitType',
				title : '单位类型',
				align : 'center',
				valign : 'middle',
				 formatter: function (value, row, index) {
					 if(value=="1")
						 return "市级单位";
					 else if(value=="2")
						 return "区县单位";
					 else return "";
		         }
			},
			
			 {
				field : 'contactPerson',
				title : '联系人',
				align : 'center',
				valign : 'middle',
				   
				
			},
			
			 {
				field : 'contactPhone',
				title : '联系电话',
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
				field : 'address',
				title : '单位地址',
				align : 'center',
				valign : 'middle',
				   
				
			},
		
			 {
				field : 'uptimestamp',
				title : '最近编辑时间',
				align : 'center',
				valign : 'middle',
				   
		 formatter: function (value, row, index) {
			  return  dateFormat(value,"yyyy-MM-dd hh:mm:ss");
         }
				
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
			+ '<button id = "editPri" type = "button" class = "btn btn-warning"><i class="fa fa-lock">配置单位管理员</i> </button>&nbsp;'
			
			+ '<button id = "update" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改</i> </button>&nbsp;'
			
			+ '<button id = "delete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
			+ '</div>' ].join("");
}




window.PersonnelInformationEvents_item = {
		"click #editPri" : function(e, value, row, index) {
			$.ajax({
				type : "post",
				url :getRPath()+ '/manager/tunitinfomanager/tunitinfomanagerList',
				data : {
					unitId : row.id
				},
				async : false,
				dataType : "json",
				success : function(response) {
					
					$("#unitAdmin").select2().val(null).trigger("change");
					$("#unitAdmin").select2("destroy");
					$("#unitAdmin").html("");
					
					
					//unitAdmin
					if(response&&response.rows)
						{
						for (var i = 0; i < response.rows.length; i++) {
							var data=response.rows[i];
							 var pname=data.name;
					         var option = new Option(pname, data.managerId, true, true);
					         $("#unitAdmin").append(option).trigger('change');
					         $("#unitAdmin").trigger({
					             type: 'select2:select',
					             params: {
					                 data: {text:pname,id:data.managerId}
					             }
					         });
						}
						}
					initAdminSelect("unitAdmin");
				

					

					$("#mform_item_admin").fill(row);
					
					$("#myModal_item_admin").modal();
					
				
				}
			});

		},
		
	"click #update" : function(e, value, row, index) {
		$.ajax({
			type : "post",
			url :getRPath()+ '/manager/tunitinfo/load',
			data : {
				id : row.id
			},
			async : false,
			dataType : "json",
			success : function(response) {
				
			   $("#mform_item").fill(response);
			     
			   

				$("#parentUnit").select2().val(null).trigger("change");
				$("#parentUnit").select2("destroy");
				$("#parentUnit").html("");
				
				
				//unitAdmin
				var data=response;
				 var pname=response.parentUnitName;
		         var option = new Option(pname, data.parentUnit, true, true);
		         $("#parentUnit").append(option).trigger('change');
		         $("#parentUnit").trigger({
		             type: 'select2:select',
		             params: {
		                 data: {text:pname,id:data.parentUnit}
		             }
		         });
				
				
		     	initUnitSelect("parentUnit");
				
				refreshParentDv();
			   

			   $("#myModal_item_title").html("编辑");
			   
				$("#myModal_item").modal();
				
			
			}
		});

	},

	"click #delete" : function(e, value, row, index) {
		var msg = "您真的确定要删除吗？";
		var url = getRPath()+"/manager/tunitinfo/delete";
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

