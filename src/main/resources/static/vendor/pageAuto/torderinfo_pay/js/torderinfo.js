function test(){
	
	
	var url = getRPath()+"/manager/torderinfo_pay/test";
	
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
    $('#q_createTime').val(GetQueryString("month"));
	InitQuery_item();
	InitQuery_audit();

    //初始化查询相关
    initDataTimePicker("startTime")
    initDataTimePicker("endTime")

    //初始化地市区查询下拉框
    var provinceSelectId = "q_province"
    var citySelectId = "q_city"
    var districtSelectId = "q_district"
    var provinceCodeId = "q_provinceCode"
    var cityCodeId = "q_cityCode"
    var districtCodeId = "q_districtCode"

    initProvinceSelect(provinceSelectId, citySelectId, districtSelectId,
        provinceCodeId, cityCodeId, districtCodeId)
    initCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId)
    initDistrictSelect(districtSelectId, districtCodeId)

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

		var url = getRPath()+"/manager/torderinfo_pay/saveOrUpdate";

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


function refreshAudit(orderId) {
	
	
	var opt = {
		silent : true,

		query:{
		    //请求参数
			orderInfoId:orderId,
			orderinfoId:orderId,
		}
	};
	$("#table_list_audit").bootstrapTable('refresh', opt);
	
	//success("test");
}


function InitQuery_audit(){
	
	// 初始化Table
	$('#table_list_audit').bootstrapTable({
		url : getRPath()+'/manager/torderinfo_pay/torderinfoMoneyList', // 请求后台的URL（*）
		method : 'post', // 请求方式（*）
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toolbar', // 工具按钮用哪个容器
		showHeader : true,
		searchAlign : 'left',
		buttonsAlign : 'left',

		searchOnEnterKey : true,
		striped : true, // 是否显示行间隔色
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination : false, // 是否显示分页（*）
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
				
				
				
				orderinfoId :  -1,// $("#allocateOrderModal").find("#orderInfoId").val(),
               
				
			};
			return param;
		},
		columns : [ {
			field : 'id',
			visible : false
		},
		 {
			field : 'operateTime',
			title : '时间',
			visible : false,
			align : 'center',
			valign : 'middle',
			   
	 formatter: function (value, row, index) {
         return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
     }
			
		},
		 {
			field : 'reasonDes',
			title : '说明',
			align : 'center',
			valign : 'middle',
			   
			
		},
	
	 {
			field : 'changeValueLocker',
			title : '金额变动',
			align : 'center',
			valign : 'middle',
			   
			
		},
	 
		

		],

	    });
	
	return;
	
	// 初始化Table
	$('#table_list_audit').bootstrapTable({
		url : getRPath()+'/manager/torderinfoaudit/torderinfoauditList', // 请求后台的URL（*）
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
				
				proposType:1, //申请加钱
				auditStates:1, //审核通过
				
				orderInfoId : "-1",
                customerName: $("#q_customerName").val(),
                startTime: $("#startTime").val(),
                endTime: $("#endTime").val(),
                areaCode: $("#q_provinceCode").val() + $("#q_cityCode").val() + $("#q_districtCode").val()

				
				
			};
			return param;
		},
		columns : [ {
			field : 'id',
			visible : false
		},
		 {
			field : 'proposMoney',
			title : '申请加钱金额',
			align : 'center',
			valign : 'middle',
			   
			
		},
	 {
			field : 'proposTime',
			title : '申请时间',
			align : 'center',
			valign : 'middle',
			   
	 formatter: function (value, row, index) {
         return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
     }
			
		},
	 {
			field : 'proposReason',
			title : '申请原因',
			align : 'center',
			valign : 'middle',
			   
			
		},
	 {
			field : 'auditStates',
			title : '审核状态',
			align : 'center',
			valign : 'middle',
			 formatter: function (value, row, index) {
                 if (value == 0)
                     return "未审核";
                 else if (value == 1)
                     return "源匠审核通过";
                 else if (value == 2)
                     return "源匠审核不通过";
                 else if (value == 3)
                     return "锁企审核通过";
                 else if (value == 4)
                     return "锁企审核不通过";
                 else if (value == 5)
                     return "源匠正在向锁企申请";
		     }
			
		},
		
		 {
				field : 'auditorName',
				title : '审核人',
				align : 'center',
				valign : 'middle',
				   
				
			},
		
		

		],

	    });
}



function InitQuery_item() {
	// 初始化Table
	$('#table_list_item').bootstrapTable({
		url : getRPath()+'/manager/torderinfo_pay/torderinfoList', // 请求后台的URL（*）
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


                serverType : $("#q_ServerType").val(),
                projectName : $("#q_projectName").val(),
				orderNo : $("#q_orderNo").val(),
                custPhone : $("#q_CustPhone").val(),
                customerName: $("#q_customerName").val(),
                startTime: $("#startTime").val(),
                endTime: $("#endTime").val(),
                areaCode: $("#q_provinceCode").val() + $("#q_cityCode").val() + $("#q_districtCode").val(),

				createTime: $('#q_createTime').val()
			};
			return param;
		},
		 rowAttributes:function rowAttributes(row, index) {
	        	return {
	        	'title':"点击行查看详情",
	        	
	        	}
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
                field: 'createTime',
                title: '工单创建时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                    return value.split(".")[0]
                }
            },
            {
                field: 'customerName',
                title: '客户姓名',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'customerPhone',
                title: '客户电话',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'areaName',
                title: '客户所在地区',
                align: 'center',
                valign: 'middle',
            },
		 {
				field : 'serverType',
				title : '服务类型',
				align : 'center',
				valign : 'middle',
             formatter: function (value, row, index) {
            	 
            	 return orderTypeNameNew(value);
            	 
                 if(value==0)
                     return "安装";
                 else     if(value==1)
                     return "维修";
                 else     if(value==2)
                     return "开锁";
                 else     if(value==3)
                     return "特殊门改造";
                 else     if(value==4)
                     return "测量";
                 else     if(value==5)
                     return "猫眼安装";
                 else     if(value==6)
                     return "其他";
             }


         },
		 {
				field : 'lockName',
				title : '锁匠名 ',
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

		],detailView: true,
        onExpandRow: function (index, row, $detail) {


        	initDetailLog($detail,row.orderNo);
            //var subTable = $detail.html('<table class="nohide"></table>').find('table');
            //showDetailLog($(subTable),row.orderNo);
        }
    }).on("click-row.bs.table", function (event,row, rowele,field){
    	//  var subTable = $(rowele).html('<table class="nohide"></table>').find('table');


          showOrCloseDetail(rowele,row,1)

	});
}

function modifyAndDeleteButton_item(value, row, index) {
	return [ '<div class="">'
			+ '<button id = "update" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">结账</i> </button>&nbsp;'
			+ '<button id = "delete" type = "button" class = "hide btn btn-danger"><i class=" glyphicon glyphicon-trash">删除</i> </button>'
			+ '</div>' ].join("");
}




window.PersonnelInformationEvents_item = {
	"click #update" : function(e, value, row, index) {
		$.ajax({
			type : "post",
			url :getRPath()+ '/manager/torderinfo_pay/load',
			data : {
				id : row.id
			},
			async : false,
			dataType : "json",
			success : function(response) {
				
			   $("#mform_item").fill(response);
			     
			   var typeName=orderTypeNameNew(response.serverType);;
			   $("#serverTypeName").val(typeName);
			   
			   refreshAudit(row.id);
			   
			   $("#myModal_item_title").html("");
			   
				$("#myModal_item").modal();
				
			
			}
		});

	},

	"click #delete" : function(e, value, row, index) {
		var msg = "您真的确定要删除吗？";
		var url = getRPath()+"/manager/torderinfo_pay/delete";
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
