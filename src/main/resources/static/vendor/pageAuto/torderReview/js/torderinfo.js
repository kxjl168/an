$(function () {
  /*  $("body [id]").each(function () {
        var ids = $(this).attr("id");
        if ($("body [id=" + ids + "]").length >= 2) {
            alert("id为" + ids + " 的重复了。");
        }
    });*/

    $('#q_type').val(GetQueryString("q_type"));

    InitQuery_item();
    InitQuery_money();

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

    /**
     * 申请信息模态框消失
     */
    $("#myModal_item").on("hide.bs.modal", function () {
        $("#auditId").val("")
        $("#passAudit").remove()
        $("#noPassAudit").remove()
        $("#applyToLockCompany").remove()
    })

    /**
     * 不通过理由模态框消失
     */
    $("#noPassReasonModal").on("hide.bs.modal", function () {
        $("#notPassReasonForm")[0].reset()
        $("#notPassReasonForm").data('bootstrapValidator').resetForm();
    })

    /**
     * 源匠向锁企申请加钱模态框消失
     */
    $("#applyToLockCompanyModal").on("hide.bs.modal", function () {
        $("#applyToLockCompanyForm")[0].reset()
        $("#applyToLockCompanyForm").data('bootstrapValidator').resetForm();
    })

    /**
     * 更新模态框消失
     */
    $("#updateOrderInfoModal").on("hide.bs.modal", function (e) {
        if ($(e.target).attr("type")) //日期选择等弹出框
            return;
        $("#updateOrderInfoForm")[0].reset()
        $("#updateOrderInfoForm").data("bootstrapValidator").resetForm()
    })

    /**
     * 提交源匠对锁匠申请加钱
     */
    $("#applyToLockCompanySubmit").on("click", function () {

        $("#applyToLockCompanyForm").data('bootstrapValidator').resetForm();
        $("#applyToLockCompanyForm").data("bootstrapValidator").validate();
        var flag = $("#applyToLockCompanyForm").data("bootstrapValidator").isValid();

        if (flag) {
            var auditId = $("#auditId").val()
            var parameter = {}
            parameter.auditId = auditId
            parameter.passState = 3
            parameter.reason = $("#proposReason").val()
            
            
            parameter.proposMoney = $("#proposMoney").val()
            var url = getRPath() + "/manager/torderreview/orderAudit";
            var callback = function (data) {
                // debugger;
                if (data.errCode = 1) {
                    $('#myModal_item').modal("hide");
                    $('#applyToLockCompanyModal').modal("hide");
                    doSearch_item();
                    success("操作成功！");
                } else {
                    error(data.errMsg);
                }
            }
            sendRequest(url, parameter, callback)
        }
    })

    /**
     * 提交不通过理由
     */
    $("#noPassReasonSubmit").on("click", function () {

        $("#notPassReasonForm").data('bootstrapValidator').resetForm();
        $("#notPassReasonForm").data("bootstrapValidator").validate();
        var flag = $("#notPassReasonForm").data("bootstrapValidator").isValid();

        if (flag) {
            var auditId = $("#auditId").val()
            var parameter = {}
            parameter.auditId = auditId
            parameter.passState = 2
            parameter.reason = $("#auditReason").val()
            var url = getRPath() + "/manager/torderreview/orderAudit";
            var callback = function (data) {
                // debugger;
                if (data.errCode = 1) {
                    $('#myModal_item').modal("hide");
                    $('#noPassReasonModal').modal("hide");
                    doSearch_item();
                    success("操作成功！");
                } else {
                    error(data.errMsg);
                }
            }
            sendRequest(url, parameter, callback)
        }
    })


    updateFormValidator()
    initValidate_item();
});


function initValidate_item() {
    $("#notPassReasonForm").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
            auditReason: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '输入的内容长度须小于200字符',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 200) {
                                return false
                            }
                            return true
                        }
                    }
                }
            }
        }
    });

    $("#applyToLockCompanyForm").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
            proposReason: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '输入的内容长度须小于200字符',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 200) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            proposMoney: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    numeric: {
                        message: '请输入数字',
                        separator: "."
                    }
                }
            },
        }
    });
}


function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/torderreview/torderinfoList', // 请求后台的URL（*）
        method: 'post', // 请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar', // 工具按钮用哪个容器
        showHeader: true,
        searchAlign: 'left',
        buttonsAlign: 'left',
        searchOnEnterKey: true,
        striped: true, // 是否显示行间隔色
        cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, // 是否显示分页（*）
        sortable: false, // 是否启用排序
        sortName: 'id', // 排序字段
        sortOrder: "desc", // 排序方式
        sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, // 初始化加载第一页，默认第一页
        pageSize: 10, // 每页的记录行数（*）
        pageList: [10, 25], // 可供选择的每页的行数（*）
        search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大

        uniqueId: "id", // 每一行的唯一标识，一般为主键列
        // queryParamsType : "limit",
        queryParams: function queryParams(params) { // 设置查询参数
            var param = {
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sortName: params.sort, // 要排序的字段
                sortOrder: params.order, // 排序规则

                type: $("#q_type").val(),
                projectName: $("#q_projectName").val(),
                orderNo: $("#q_orderNo").val(),
                serverType: $("#q_ServerType").val(),
                custPhone: $("#q_CustPhone").val(),
                customerName: $("#q_customerName").val(),
                startTime: $("#startTime").val(),
                endTime: $("#endTime").val(),
                areaCode: $("#q_provinceCode").val() + $("#q_cityCode").val() + $("#q_districtCode").val()
            };

			   param=	getQueryParam(params);
	           
		          // param.  createTime= $('#q_createTime').val()
		           
		           return param;
        },
        rowAttributes:function rowAttributes(row, index) {
        	return {
        	'title':"点击行查看详情",
        	
        	}
        	},
        rowStyle: function (row, index) {
            
        	return rowStyleFormat(row,index);
           // return "";
        }    ,
        columns: [{
            field: 'id',
            visible: false
        },
        {
            field: 'icon',
            title: '标识',
            align: 'center',
            valign: 'middle',  
            formatter: function (value, row, index) {
            	return orderFlagFormat(value,row,index);          
        }
        },
            {
                field: 'orderNo',
                title: '订单编号',
                align: 'center',
                valign: 'middle',
            },
            
            {
                field: 'orderState',
                title: '订单状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value,row,index) {
                	
                	return orderStatusTypeName(value);
                	
                	
                    if (value >= 201) {
                        return "待作业"
                    } else {
                        if (value == 1) {
                            return "<span style='color: red'>加钱待平台审核</span>"
                        } else if (value == 2) {
                            return "<span style='color: red'>退单待平台审核</span>"
                        } else if (value == 4) {
                            return "<span style='color: red'>平台申请待锁企审核</span>"
                        } else if (value == 5) {
                            return "<span style='color: red'>锁企不通过待平台处理</span>"
                        }
                    }
                }
            },
            {
                field: 'companyName',
                title: '合伙人',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'lockName',
                title: '锁匠名 ',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'lockerPhone',
                title: '锁匠电话 ',
                visible : false,
                align: 'center',
                valign: 'middle',
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
                field: 'receiveTime',
                title: '待确认时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                	if(value)
                		{
                		return datePlus(value,2).Format("yyyy-MM-dd hh:mm:ss") 	;//split(".")[0];//
                		//Format("yyyy-MM-dd hh:mm:ss") 	
                		
                		//return value.split(".")[0]	
                		}
                    
                	else
                		return "";
                
                }
            },
            {
                field: 'custName',
                title: '客户姓名',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'custPhone',
                title: '客户电话',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'areaName',
                title: '客户所在地区',
                align: 'center',
                visible : false,
                valign: 'middle',
            },
            {
                field: 'addressDetail',
                title: '安装地址',
                visible : false,
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'sellerTotalPrice',
                title: '锁企服务费',
                align: 'center',
                visible : false,
                valign: 'middle'
            }, {
                field: 'lockerTotalPrice',
                title: '锁匠服务费',
                align: 'center',
                visible : false,
                valign: 'middle'
            },
            {
                field: 'serverType',
                title: '服务类型',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                	return orderTypeNameNew(value);
                    if (value == 0) {
                        return "安装"
                    }
                    if (value == 1) {
                        return "维修"
                    }
                    if (value == 2) {
                        return "开锁"
                    }
                    if (value == 3) {
                        return "特殊门改造"
                    }
                    if (value == 4) {
                        return "测量"
                    }
                    if (value == 5) {
                        return "猫眼安装"
                    }
                }
            },
            {
                field: 'dataState',
                title: '数据状态',
                visible: false,
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value == 0)
                        return "<div class='text-danger'>已废弃</div>";
                    else if (value == 1)
                    	return "<div class='text-success'>正常</div>";
                    else if (value == 2)
                        return "删除";
                }
            },
            {
                title: '操作',
                field: 'vehicleno',
                align: 'center',
                valign: 'middle',
                formatter: modifyAndDeleteButton_item,
                events: PersonnelInformationEvents_item
            }],
        detailView: true,
        onExpandRow: function (index, row, $detail) {


            initDetailLog($detail, row.orderNo);
            //var subTable = $detail.html('<table class="nohide"></table>').find('table');
            //showDetailLog($(subTable),row.orderNo);
        }
    }).on("click-row.bs.table", function (event, row, rowele, field) {
        //  var subTable = $(rowele).html('<table class="nohide"></table>').find('table');


        showOrCloseDetail(rowele, row, 1)
    });

    //根据用户类型决定列展示
    var managerType = $("#managerType").val()
    if (managerType == 2) {
        $('#table_list_item').bootstrapTable("hideColumn", "lockerTotalPrice")
    }
    if (managerType == 1) {
        $('#table_list_item').bootstrapTable("hideColumn", "sellerTotalPrice")
    }
}

/*
function modifyAndDeleteButton_item(value, row, index) {
var managerType = $("#managerType").val()
if (row.orderState >= 201) {
return ['<div class="">'
+ '<button id = "delete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
+ '</div>'].join("");
} else {
if ((managerType == 2 && row.orderState == 4) || managerType == 0) {
return ['<div class="">'
+ '<button id = "showAudit" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">查看详情</i> </button>&nbsp;'
+ '</div>'].join("");
}
}
}*/

/**
 * 生成子表
 * @param index
 * @param row
 * @param $detail
 */
function initOrderAuditTable(orderId) {
    $("#orderInfoAuditTable").bootstrapTable("destroy")
    $("#orderInfoAuditTable").bootstrapTable({
        url: getRPath() + '/manager/torderreview/selectAuditByOrderId', // 请求后台的URL（*）
        method: 'post', // 请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar', // 工具按钮用哪个容器
        showHeader: true,
        searchAlign: 'left',
        buttonsAlign: 'left',
        searchOnEnterKey: true,
        striped: true, // 是否显示行间隔色
        cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: false, // 是否显示分页（*）
        sortable: false, // 是否启用排序
        sortName: 'id', // 排序字段
        sortOrder: "desc", // 排序方式
        sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, // 初始化加载第一页，默认第一页
        pageSize: 10, // 每页的记录行数（*）
        pageList: [10, 25], // 可供选择的每页的行数（*）
        search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大

        // showColumns: true, //是否显示所有的列
        uniqueId: "id", // 每一行的唯一标识，一般为主键列
        // queryParamsType : "limit",
        queryParams: function queryParams(params) { // 设置查询参数
            var param = {
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sortName: params.sort, // 要排序的字段
                sortOrder: params.order, // 排序规则
                id: orderId
            };
            return param;
        },
        onLoadSuccess: function (data) {
        	//id,money zj
            $("#auditId").val(data.rows[0].id+","+data.rows[0].proposMoney)
             
        },
        columns: [{
            field: 'id',
            visible: false
        },
            {
                field: 'proposType',
                title: '申请类型',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                	if(value==3)
                        return "向锁企申请加钱";
                    	else if(value==1)
                            return "锁匠原始申请";
                    	else 
                    		return "申请加钱";
                }
            },
            {
                field: 'subType',
                title: '申请加钱原因 ',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                    if (value == 1) {
                        return "空跑"
                    } else if (value == 2) {
                        return "远途"
                    } else if (value == 3) {
                        return "改装"
                    } else if (value == 4) {
                        return "特殊门"
                    } else if (value == 5) {
                        return "加急"
                    } else if (value == 6) {
                        return "假锁";
                    }
                }
            },
            {
                field: 'proposMoney',
                title: '申请加钱金额 ',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'icons',
                title: '申请加钱图片 ',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                    if (value == null || value == '') {
                        return "";
                    }
                    var result = "<div class='auditImgDiv'>"
                    var idList = value.split(",")
                    for (var i = 0; i < idList.length; i++) {
                        var url = getRootPath() + "/mongo/getAuditImg/" + idList[i]
                        result = result + "<img onclick='showDetailImgModal(\""+url+"\")' class='img-responsive auditImg' src='" + url + "'/>"
                    }
                    result+="</div>";
                    return result;
                }
            },
            {
                field: 'proposTime',
                title: '申请时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                    return value.split(".")[0]
                }
            },
            {
                field: 'auditStates',
                title: '申请状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                   if(value==0){
                	   return "待审核";
                   }
                   if(value==4){
                	   return "锁企审核不通过";
                   }
                   if(value==6){
                	   return "锁匠向合伙人申请中";
                   }
                }
            },
            {
                field: 'proposReason',
                title: '申请描述',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'auditFailReason',
                title: '申请答复',
                align: 'center',
                valign: 'middle',
            }
            
            ]
    });
};


function doSearch_item() {
    var opt = {
        silent: true,
        pageNumber: 1
    };
    $("#table_list_item").bootstrapTable('refresh', opt);
}

/**
 * 点击向锁企申请加钱
 */
function applyToLockCompanyModalShow() {
    var parameter = {}
    parameter.id = $("#auditId").val().split(',')[0];
    
    var url = getRPath() + "/manager/torderreview/selectCompanyProposMoney";
    var callback = function (data) {
        if (data != 0) {
            $("#proposMoney").val(data)
        }
    }
    sendRequest(url, parameter, callback)
    $("#applyToLockCompanyModal").modal("show")
}


function initAuitMoneyValidate(){
	$("#AuditMoneyForm").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
          
            logremarkcontent: {
            	  validators: {
        		 notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '输入的内容长度须小于50字符',
                        //trigger: 'change',
                        callback: function (value, validator) {
                        	//msg(1);
                            if (value.length > 50) {
                                return false
                            }
                            return true
                        }
                    }
            	  }
            },
            
            addMoney: {
            	  validators: {
        		 notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '金额异常',
                       // trigger: 'change',
                        callback: function (value, validator) {
                           
                        	return /^[1-9][0-9]*$/.test(value);
                        	
                        }
                    }
            },
            }
        }
    });
}
	


$(function(){
	
	$("#myModal_AuditMoney").find("#btnAuditMoney").click(function(){
		
			var auditId = $("#auditId").val()
		    var parameter = {}
			
			
			var id=$("#myModal_AuditMoney").find(".remarkdOId").val(); //审核id
			var reason=$("#myModal_AuditMoney").find(".logremarkcontent").val();
		   
			var money=$("#myModal_AuditMoney").find("#addMoney").val();
			 
			
			if($("#canModifyAuditMoney").val()=="true")
				{
				
				
				  $("#AuditMoneyForm").data('bootstrapValidator').resetForm();
			        $("#AuditMoneyForm").data("bootstrapValidator").validate();
			        var flag = $("#AuditMoneyForm").data("bootstrapValidator").isValid();

				if(!flag)
					return;
			}
			
			cconfirm("确认加钱审核通过?",function(){
			
		    parameter.auditId = id
		    parameter.reason=reason
		    parameter.proposMoney=money
		    parameter.passState = 1
		    var url = getRPath() + "/manager/torderreview/orderAudit";
		    var callback = function (data) {
		        // debugger;
		        if (data.errCode = 1) {
		            $('#myModal_item').modal("hide");
		            $("#noPassReasonModal").modal("hide")
		            
		             $("#myModal_AuditMoney").modal("hide")
		            
		            doSearch_item();
		            success("操作成功！");
		        } else {
		            error(data.errMsg);
		        }
		    }
		    sendRequest(url, parameter, callback)
		})
	});
	
	
	
})


/**
 * 点击通过
 */
function addMoneyAuditPass() {
	 var id=$("#auditId").val().split(',')[0];
	 var money=$("#auditId").val().split(',')[1];
	
	//备注标记工单
    //初始化工程下拉框
	 $("#myModal_AuditMoney").find(".remarkdOId").val( id); //审核id
	 $("#myModal_AuditMoney").find(".logremarkcontent").val("");
   
	 $("#myModal_AuditMoney").find("#addMoney").val(money);
	 
	
	
	if($("#canModifyAuditMoney").val()!="true")
	{
		
		
	//不可改价
	 $("#auditReasonDv").hide();
	 $("#myModal_AuditMoney").find("#addMoney").attr("readonly","readonly");
	}
	else{
		
		 $("#myModal_AuditMoney").find(".logremarkcontent").val("申请加价");
		 $("#myModal_AuditMoney").find(".modal-title").html("<span>审核加钱(可调整价格)</span>");
		 $("#myModal_AuditMoney").find(".modal-header").addClass("warning");
		initAuitMoneyValidate();
	}
	
	
	
    
    //initAllocateSelect(row.id)
    $("#myModal_AuditMoney").modal("show")
	
	
	
    
}

/**
 * 点击不通过
 */
function addMoneyAuditFail() {
    $("#noPassReasonModal").modal("show")
}

