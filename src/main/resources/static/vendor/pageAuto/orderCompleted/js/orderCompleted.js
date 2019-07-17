function test() {


    var url = getRPath() + "/manager/orderCompleted/test";

    $.ajax({
        type: "post",
        url: url,
        //data : data,
        async: false,
        dataType: "json",
        success: function (response) {
            success("操作成功！");
        }
    });
}


$(function () {
    $('#q_createTime').val(GetQueryString("month"));

    InitQuery_item();
    InitQuery_audit();
    //InitQuery_record();

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

    $("#btnAdd_item").click(function () {


        $('#mform_item')[0].reset();

        $('#mform_item').find("#id").val("");

        $("#myModal_item_title").html("添加");

        $("#distpicker2").distpicker({
            autoSelect: true
        });

        $("#myModal_item").modal();
    });


    // modal 新增基本字段事件 关闭事件事件， 清空已有的值 恢复禁用
    $('#myModal_item').on('hide.bs.modal', function (e) {

        if ($(e.target).attr("type")) //日期选择等弹出框
            return;

        $('#mform_item')[0].reset();

        $("#mform_item").data('bootstrapValidator').resetForm();

    });


    $("#btnSubmit_item").click(function () {


        $("#mform_item").data('bootstrapValidator').resetForm();


        // var bool2 = bv.isValid();
        $("#mform_item").data("bootstrapValidator").validate();
        // flag = true/false
        var flag = $("#mform_item").data("bootstrapValidator").isValid();

        var url = getRPath() + "/manager/orderCompleted/saveOrUpdate";

        if (flag) {
            var data = $("#mform_item").serialize();

            /**/

            $.ajax({
                type: "post",
                url: url,
                data: data,
                async: false,
                dataType: "json",
                success: function (response) {
                    // debugger;
                    if (response.bol) {
                        $('#myModal_item').modal("hide");
                        doSearch_item();
                        success("操作成功！");
                    } else {
                        error(response.message);
                    }
                }
            });
        }
    });

    initValidate_item();


});


function initValidate_item() {
    $("#mform_item").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {

            name: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },


        }


    });

}


function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/orderCompleted/torderinfoList', // 请求后台的URL（*）
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

        // showColumns: true, //是否显示所有的列
        uniqueId: "id", // 每一行的唯一标识，一般为主键列
        // queryParamsType : "limit",
        queryParams: function queryParams(params) { // 设置查询参数
            var param = {
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sortName: params.sort, // 要排序的字段
                sortOrder: params.order, // 排序规则


                orderNo: $("#q_orderNo").val(),
                customerPhone: $("#q_CustPhone").val(),
                serverType: $("#q_ServerType").val(),
                projectName: $("#q_projectName").val(),
                customerName: $("#q_customerName").val(),
                startTime: $("#startTime").val(),
                endTime: $("#endTime").val(),
                areaCode: $("#q_provinceCode").val() + $("#q_cityCode").val() + $("#q_districtCode").val(),

            

            };
         	
            param=	getQueryParam(params);
           
           param.  createTime= $('#q_createTime').val()
           
           return param;
           
        },
        rowAttributes:function rowAttributes(row, index) {
        	return {
        	'title':"点击行查看详情",
        	
        	}
        	},
        detailView: true,
        columns: [{
            field: 'id',
            visible: false
        },

            {
                field: 'orderNo',
                title: '订单编号',
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
                field: 'serverType',
                title: '服务类型',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                	
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
                field: 'lockName',
                title: '锁匠名 ',
                align: 'center',
                valign: 'middle',


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
                field: 'sellerTotalPrice',
                title: '锁企服务费',
                visible: false,
                align: 'center',
                valign: 'middle'
            },{
                field: 'lockerTotalPrice',
                title: '锁匠服务费',
                visible: false,
                align: 'center',
                valign: 'middle'
            },
            {
                field: 'price',
                title: '锁匠是否加价',
                align: 'center',
                visible: false,
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value != null && value != 0) {
                        return "是"
                    } else {
                        return "否"
                    }
                }
            },
            {
                field: 'price',
                title: '锁匠加价',
                align: 'center',
                visible: false,
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value != null && value != 0) {
                        return value;
                    } else {
                        return "无"
                    }
                }

            },


            {
                title: '操作',
                field: 'vehicleno',
                align: 'center',
                formatter: modifyAndDeleteButton_item,
                events: PersonnelInformationEvents_item
            }

        ],

        onExpandRow: function (index, row, $detail) {
        	initDetailLog($detail,row.orderNo);

        	// var subTable = $detail.html('<table class="nohide"></table>').find('table');
            //showDetailLog($(subTable),row.orderNo);
        }
    }).on("click-row.bs.table", function (event,row, rowele,field){
    	//  var subTable = $(rowele).html('<table class="nohide"></table>').find('table');


          showOrCloseDetail(rowele,row,1)
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




function modifyAndDeleteButton_item(value, row, index) {
	
	setTimeout(function(){
		tippy(".tippy",{
				 arrow: true,
				  arrowType: 'round', // or 'sharp' (default)
				  animation: 'perspective',
		}
				)
	},500);
	
    var managerType=$("#managerType2").val();// 模板中获取当前登陆用户类型
    
    
    if (row.price != null && managerType!=2) {
        return ['<div class="">'
        + '<button id = "record" type = "button" data-tippy-content="详情" class = "tippy btn btn-info"><i class="fa fa-sticky-note"></i> </button>&nbsp;'
     //   + '<button id = "history" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">历史</i> </button>&nbsp;'

      //  + '<button id = "history2" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">历史2</i> </button>&nbsp;'
        + '</div>'].join("");
    } else {
        return [''];
    }
}


window.PersonnelInformationEvents_item = {
    "click #record": function (e, value, row, index) {
        $.ajax({
            type: "post",
            url: getRPath() + '/manager/orderCompleted/load',
            data: {
                id: row.id
            },
            async: false,
            dataType: "json",
            success: function (rdata) {

            	
	var response=rdata.order;
            	
            	var imgs=rdata.imgs;
            	
            	try {
            		
            	
            		 
            		
            		initImgDetails(response.lockNum,imgs);
				} catch (e) {
					
				}
            	
            	
                $("#mform_item").fill(response);

                var typeName=orderTypeNameNew(response.serverType);;
 			   $("#serverTypeName").val(typeName);
                
                refreshAudit(row.id);
               // refreshRecord(row.id);
                $("#myModal_item_title").html("");

                $("#myModal_item").modal();


            }
        });
    },
    "click #history": function (e, value, row, index) {
        window.location = getRPath() + "/manager/torderinfooperatelog/manager?id=" + row.orderNo;
    },

    "click #history2": function (e, value, row, index) {


        //icon-plus
       // icon-minus
   var $this = $(buttonEle);    //a.detail-icon

  	  if($this[0].tagName=="BUTTON")
        $tr = $this.parent().parent().parent();      //current row
  	  else
  		  $tr = $this.parent().parent().parent().parent();      //current row


    	showOrCloseDetail($tr,row,index);
    },
};

function doSearch_item() {


    var opt = {
        silent: true,
        pageNumber: 1
    };
    $("#table_list_item").bootstrapTable('refresh', opt);

    //success("test");
}


function InitQuery_audit() {
	
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
        url: getRPath() + '/manager/torderinfoaudit/torderinfoauditList', // 请求后台的URL（*）
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

        // showColumns: true, //是否显示所有的列
        uniqueId: "id", // 每一行的唯一标识，一般为主键列
        // queryParamsType : "limit",
        queryParams: function queryParams(params) { // 设置查询参数
            var param = {
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sortName: params.sort, // 要排序的字段
                sortOrder: params.order, // 排序规则

                proposType: 1, //申请加钱
                // auditStates:1, //审核通过

                orderInfoId: "-1",


            };
            return param;
        },
        columns: [{
            field: 'id',
            visible: false
        },
            {
                field: 'proposMoney',
                title: '申请加钱金额',
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
                        return null
                    }
                    var result = ""
                    var idList = value.split(",")
                    for (var i = 0; i < idList.length; i++) {
                        var url = getRootPath() + "/mongo/getAuditImg/" + idList[i]
                        result = result + "<img src='" + url + "' style='width: 50px; height: 50px'/>"
                    }
                    return result;
                }
            },
            {
                field: 'proposTime',
                title: '申请时间',
                align: 'center',
                valign: 'middle',

                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }

            },
            {
                field: 'proposReason',
                title: '申请原因',
                align: 'center',
                valign: 'middle',


            },
            {
                field: 'auditStates',
                title: '审核状态',
                align: 'center',
                valign: 'middle',
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
                field: 'auditorName',
                title: '审核人',
                align: 'center',
                valign: 'middle',


            },


        ]
    });
}

function InitQuery_record() {



    // 初始化Table
    $('#table_list_record').bootstrapTable({
        url: getRPath() + '/manager/torderinfooperatelog/torderinfooperatelogList', // 请求后台的URL（*）
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

        // showColumns: true, //是否显示所有的列
        uniqueId: "id", // 每一行的唯一标识，一般为主键列
        // queryParamsType : "limit",
        queryParams: function queryParams(params) { // 设置查询参数
            var param = {
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sortName: params.sort, // 要排序的字段
                sortOrder: params.order, // 排序规则

                orderinfoId: $("#id").val()
            };
            return param;
        },
        columns: [{
            field: 'id',
            visible: false
        },
            {
                field: 'orderNo',
                title: '订单编号',
                align: 'center',
                valign: 'middle',


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
                field: 'content',
                title: '详细内容',
                align: 'center',
                valign: 'middle',


            },
            {
                field: 'operateUserName',
                title: '操作人',
                align: 'center',
                valign: 'middle',


            },
            {
                field: 'operateTime',
                title: '操作时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }

            },

        ]
    });
}

function refreshAudit(orderId) {


    var opt = {
        silent: true,

        query: {
            //请求参数
            orderInfoId: orderId,
            orderinfoId:orderId,
            proposType: 1, //申请加钱
            // auditStates:1, //审核通过
        }
    };
    $("#table_list_audit").bootstrapTable('refresh', opt);

}

function refreshRecord(orderId) {


    var opt = {
        silent: true,

        query: {
            //请求参数
            orderinfoId: orderId
        }
    };
    $("#table_list_record").bootstrapTable('refresh', opt);

}
