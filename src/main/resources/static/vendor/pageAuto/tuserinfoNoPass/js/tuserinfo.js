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


var provinceSelectId2 = "q_province"
    var citySelectId2 = "q_city"
    var districtSelectId2 = "q_district1"
    var provinceCodeId2 = "q_provinceCode_query"
    var cityCodeId2 = "q_cityCode_query"
    var districtCodeId2 = "q_districtCode_query"	
    	
    	
$(function() {
	InitQuery_item();


	   //查询初始化
    initProvinceSelect(provinceSelectId2, citySelectId2, districtSelectId2,
            provinceCodeId2, cityCodeId2, districtCodeId2,function(){
    	  $("#q_district2" ).empty();
   	   var selectedCityId = $("#" + citySelectId2 + " option:selected").attr("id")
          initDistrictSelect("q_district2", "q_districtCode2", selectedCityId)
          
   	
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

		var url = getRPath()+"/manager/tuserinfo/saveOrUpdate";

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
		url : getRPath()+'/manager/tuserinfo/tuserinfoList', // 请求后台的URL（*）
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
			

        	var provinceId="";
        	var cityId="";
        	var DistrictId="";
        	var districtId2="";
        	
        	var provar=$("#q_province").find("option:selected");
        	if(typeof(provar)!="undefined")
        		provinceId=provar.attr('id');
        	
        	var provarcity=$("#q_city").find("option:selected");
        	if(typeof(provarcity)!="undefined")
        		cityId=provarcity.attr('id');
        	
        	var provardistrict=$("#q_district1").find("option:selected");
        	if(typeof(provardistrict)!="undefined")
        		DistrictId=provardistrict.attr('id');
        	
        	var provardistrict2=$("#q_district2").find("option:selected");
        	if(typeof(provardistrict2)!="undefined")
        		districtId2=provardistrict2.attr('id');
        	
			var param = {
					//parterquery:0//自由锁匠
	        		//userType:1, //普通锁匠
	        		auditFlag:2,//不通过
	        			
	        			
	                pageSize: params.limit, // 每页要显示的数据条数
	                offset: params.offset, // 每页显示数据的开始行号
	                sortName: params.sort, // 要排序的字段
	                sortOrder: params.order, // 排序规则
	                phone: $("#q_phone").val(),
	                name: $("#q_name").val(),
	                
	                idCard:$("#q_idcard").val(),
	                des:$("#q_des").val(),
	                
	                provinceId:provinceId,
	                cityId:cityId,
	                districtId: DistrictId,
	                districtId2:districtId2,
			};
			return param;
		},
		columns : [ {
			field : 'id',
			align: 'center',
            valign: 'middle',
			visible : false
		},
		 {
				field : 'phone',
				title : '手机号',
				align : 'center',
				valign : 'middle',


			},
		 {
				field : 'name',
				title : '姓名',
				align : 'center',
				valign : 'middle',
				   
				
			},
		 {
			    field : 'idCard',
			    title : '身份证号',
				align : 'center',
				valign : 'middle',
				   
				
			},
			
			   {
                field: 'areaName',
                title: '所在区域',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'districtnames',
                title: '作业区域',
                align: 'center',
                valign: 'middle',
            },
			
			
		 {
				field : 'updateTime',
				title : '审核时间',
				align : 'center',
				visible : true,
				valign : 'middle',
             formatter: function (value, row, index) {
                 return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
             }
				
			},
		 {
				field : 'updateName',
				title : '审核人',
				align : 'center',
				valign : 'middle',
				visible : false
				   
				
			},
            {
                field : 'auditReason',
                title : '不通过原因',
                align : 'center',
                valign : 'middle',


            },
            {
                field: 'dataState',
                title: '状态',
                visible: true,
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value == -1)
                        return "未提交审核";
                    else if (value == 0)
                        return "<div class='text-danger'>已停用</div>";
                    else if (value == 1)
                    	return "<div class='text-success'>正常</div>";
                    else if (value == 2)
                        return "删除";
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
/*
function modifyAndDeleteButton_item(value, row, index) {
	return [ '<div class="">'
			// + '<button id = "update" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改</i> </button>&nbsp;'
		  + '<button id = "delete" type = "button" class = "btn btn-warning"><i class="glyphicon glyphicon-trash">废弃</i> </button>'
		    + '<button id = "realdelete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
			+ '</div>' ].join("");
}


*/

window.PersonnelInformationEvents_item = {
	"click #update" : function(e, value, row, index) {
		$.ajax({
			type : "post",
			url :getRPath()+ '/manager/tuserinfo/load',
			data : {
				id : row.id
			},
			async : false,
			dataType : "json",
			success : function(response) {
				
			   $("#mform_item").fill(response);
			     
	
			   
			   $("#myModal_item_title").html("编辑");
			   
				$("#myModal_item").modal();
				
			
			}
		});

	},
	 "click #reset": function (e, value, row, index) {
	        var msg = "您真的确定执行恢复操作吗？";
	        var url = getRPath() + "/manager/tuserinfo/ReUse";
	        cconfirm(msg, function () {
	            $.ajax({
	                type: "post",
	                url: url,
	                data: {
	                    "id": row.id
	                },
	                success: function (response) {
	                    if (response.bol) {
	                        success("操作成功！");
	                        doSearch_item();
	                    } else {
	                        error("" + response.message);
	                    }
	                }
	            });
	        });

	    }    ,
	  "click #delete": function (e, value, row, index) {
	        var msg = "您真的确定执行废弃操作吗？";
	        var url = getRPath() + "/manager/tuserinfo/NoUsedelete";
	        cconfirm(msg, function () {
	            $.ajax({
	                type: "post",
	                url: url,
	                data: {
	                    "id": row.id
	                },
	                success: function (response) {
	                    if (response.bol) {
	                        success("操作成功！");
	                        doSearch_item();
	                    } else {
	                        error("" + response.message);
	                    }
	                }
	            });
	        });

	    }    ,
	    "click #realdelete": function (e, value, row, index) {
	        var msg = "您真的确定执行删除操作吗？";
	        var url = getRPath() + "/manager/tuserinfo/delete";
	        cconfirm(msg, function () {
	            $.ajax({
	                type: "post",
	                url: url,
	                data: {
	                    "id": row.id
	                },
	                success: function (response) {
	                    if (response.bol) {
	                        success("操作成功！");
	                        doSearch_item();
	                    } else {
	                        error("" + response.message);
	                    }
	                }
	            });
	        });

	    }    ,
};

function doSearch_item() {
	
	
	
	var opt = {
		silent : true,
        pageNumber:1
	};
	$("#table_list_item").bootstrapTable('refresh', opt);
	
	//success("test");
}

