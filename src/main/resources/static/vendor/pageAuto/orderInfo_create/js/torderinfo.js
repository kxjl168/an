$(function () {
    /*$("body [id]").each(function () {
        var ids = $(this).attr("id");
        if ($("body [id=" + ids + "]").length >= 2) {
            alert("id为" + ids + " 的重复了。");
        }
    });*/
    InitQuery_item();
    
    InitQuery_orderMoney();
    //初始化时间选择器
    initDataTimePicker("appointmentTime",9,16)
    initDataTimePicker("startTime")
    initDataTimePicker("endTime")
    initDataTimePicker("updateAppointmentTime")

    //初始化地市区查询下拉框
    var provinceSelectId = "q_province"
    var citySelectId = "q_city"
    var districtSelectId = "q_district"
    var provinceCodeId = "q_provinceCode"
    var cityCodeId = "q_cityCode"
    var districtCodeId = "q_districtCode"

    initProvinceSelect(provinceSelectId, citySelectId, districtSelectId,
        provinceCodeId, cityCodeId, districtCodeId)
   // initCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId)
   // initDistrictSelect(districtSelectId, districtCodeId)

    //初始化创建校验器
    initCreateFormValidate();
    //初始化导入校验器
    initImportFormValidate();
    //初始化更新校验器
    initUpdateFormValidate();
    
    
    if($("#companyId").val()==null) //管理员才初始化锁企选择
    initLockCompanySelect("sellerId");
    else{
    	$("#sellerId").val($("#companyId").val());
    			
    	//直接初始化产品选择
   	 initProductSelect2("productId",$("#sellerId").val());
    }

    
    var provinceSelectId = "provinceSelect"
        var citySelectId = "citySelect"
        var districtSelectId = "districtSelect"
        var provinceCodeId = "provinceCode"
        var cityCodeId = "cityCode"
        var districtCodeId = "districtCode"

        	initProvinceSelect(provinceSelectId, citySelectId, districtSelectId,
            provinceCodeId, cityCodeId, districtCodeId)
    
    
 // Bind an event
	$('#sellerId').on('select2:select', function(e) {
		// alert("11");
		//$("#q_building").show();
		$("#productId").select2().val(null).trigger("change");
		$("#productId").select2("destroy");
		 initProductSelect2("productId",$("#sellerId").val());
	});
    
   

    /**
     * 点击添加
     */
    $("#btnAdd_item").click(function () {
    	
    	  var provinceSelectId = "provinceSelect"
              var citySelectId = "citySelect"
              var districtSelectId = "districtSelect"
              var provinceCodeId = "provinceCode"
              var cityCodeId = "cityCode"
              var districtCodeId = "districtCode"
          
              	   cleanSelectArea(provinceSelectId,null);
    	  
    	  
    	  if($("#companyId").val()==null) //管理员才初始化锁企选择
    		  {
    		   $("#sellerId").select2().val(null).trigger("change");
    	 		$("#sellerId").select2("destroy");
    	 		$("#sellerId").html("");
    	 		initLockCompanySelect("sellerId");
    	 		
    	 		$("#productId").select2().val(null).trigger("change");
    			$("#productId").select2("destroy");
    			$("#productId").html("");
    	 		
    	 		$("#dvSellerId").removeClass('hide')
    	 		
    	          
    	 		  $("#dvEnterpriseName").addClass('hide');
    	 		//chushihua
                  initProductSelect2("productId",$("#sellerId").val());

              }
    		    
    		    else{
    		    	$("#sellerId").val($("#companyId").val());
    		    	  $("#productId").select2().val(null).trigger("change");
    	    	 		$("#productId").select2("destroy");
    	    	 		
    	    			$("#productId").html("");
    		    	//直接初始化产品选择
    		   	 initProductSelect2("productId",$("#sellerId").val());
    		    }
    	  
    	
        $('#mform_item')[0].reset();
        $("#myModal_item_title").html("工单录入");
        $("#myModal_item").modal("show");
    });

    /**
     * 点击导入工单按钮
     */
    $("#btnImport_item").click(function () {
        $('#importModalForm')[0].reset();
        // if ($("#importCompanySelect").length > 0) {
        //     $("#importCompanyId").val("")
        // }
        $("#importProjectId").val("")
        initFileInput()
        //初始化工程下拉框
        // initProjectSelect("importProjectSelect", "importProjectId", null, "importCompanySelect", "importCompanyId")
        //初始化公司选择
        /*if ($("#importCompanySelect").length > 0) {
            initLockCompanySelect("importCompanySelect", "importCompanyId")
        }*/
        initLockerCompanySelect2("importCompanySelect", "importCompanyId");

        $("#importFileModal").modal("show");
    })


    /**
     * 创建工单界面初始化
     */
    $("#myModal_item").on("show.bs.modal", function () {
        var modalHasShown = $("#modalHasShown").val()
        if (modalHasShown == "1") {
            return;
        }
        //初始化工程下拉框
        // initProjectSelect("projectSelect", "projectId", null, "companySelect", "companyId")
        //初始化公司选择
       /* if ($("#companySelect").length > 0) {
           
        }*/
        //初始化省选择下拉框
        
      
      //  initCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId)
      //  initDistrictSelect(districtSelectId, districtCodeId)*/

        //该值防止日期选择器触发模态框显示事件
        $("#modalHasShown").val(1)
    })


    /**
     * 创建模态框消失
     */
    $('#myModal_item').on('hide.bs.modal', function (e) {
        if ($(e.target).attr("type")) //日期选择等弹出框
            return;
        $('#mform_item')[0].reset();
        $("#mform_item").data('bootstrapValidator').resetForm();

      //  $("#citySelect").empty()
      //  $("#districtSelect").empty()
      //  $("#provinceSelect").empty()
        // $("#projectSelect").empty()
     //   $("#companySelect").empty()
      //  $("#companySelect").prop("disabled", false)
        $('#mform_item').find("#id").val("");
        $("#projectId").val("")
        $("#provinceCode").val("")
      //  $("#cityCode").val("")
      //  $("#districtCode").val("")

        /*// 如果页面中没有公司下拉框，证明该用户为公司用户，则companyId不应该改变，
        // 若页面中有公司下拉框，证明登录用户为admin，那么应该每次重置公司id
        if ($("#companySelect").length > 0) {
            $("#sellerId").val("")
        }*/
        //该值防止日期选择器触发模态框显示事件
        $("#modalHasShown").val(0)
    });

    /**
     * 导入模态框消失
     */
    $('#importFileModal').on('hide.bs.modal', function (e) {
        $('#importModalForm')[0].reset();
        $("#importModalForm").data('bootstrapValidator').resetForm();
        $("#importProjectSelect").empty()
        $("#importCompanySelect").empty()
        $("#importCompanySelect").prop("disabled", false)
    });

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
     * 单选钮变更事件
     */
    $("input[name='serverType']").on("change", function () {

        var serverType = $(this).val();
        var provinceSelectId = "provinceSelect"
        var citySelectId = "citySelect"
        var districtSelectId = "districtSelect"
        var provinceCodeId = "provinceCode"
        var cityCodeId = "cityCode"
        var districtCodeId = "districtCode"


        	//不做操作
        	return ;
        	
        //如果是安装，那么只显示平台当前有业务的省，切换时清空省市县选项
        if (serverType == 0) {
            //清空省市区选择器的值
            $("#citySelect").empty()
            $("#districtSelect").empty()
            $("#provinceSelect").empty()
            $("#provinceCode").val("")
            $("#cityCode").val("")
            $("#districtCode").val("")

            //初始化省选择下拉框
            //initCreateProvinceSelect(provinceSelectId, citySelectId, districtSelectId,
            //    provinceCodeId, cityCodeId, districtCodeId)
        }
        //如果是其他，不作限制
        else {
            //初始化省选择下拉框
           // initProvinceSelect(provinceSelectId, citySelectId, districtSelectId,
          //      provinceCodeId, cityCodeId, districtCodeId)
        }
       // initCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId)
       // initDistrictSelect(districtSelectId, districtCodeId)
    })


    /**
     * 提交创建或更新工单
     */
    $("#btnSubmit_item").click(function () {
        console.log($("#sellerId").val())
        //校验下拉框是否选择
        if (($("#provinceCode").val() == "" || $("#provinceCode").val() == null)
            || ($("#cityCode").val() == "" || $("#cityCode").val() == null)
            || ($("#districtCode").val() == "" || $("#districtCode").val() == null)) {
            error("请选择正确的省市区");
            return;
        }
        
        
        //校验工单类型
        if ( $('input[name=serverType]:checked').val() == "" ||$('input[name=serverType]:checked').val()  == null) {
            error("请选择服务类型")
            return
        }
        

        //校验锁企是否选择
        if ($("#sellerId").val() == "" || $("#sellerId").val() == null) {
            error("请选择锁企")
            return
        }
        
        if ( (($("#mailAddressDetail").val() == "") || ($("#mailAddressDetail").val() == null)) && ( ($("#addressDetail").val() == "")|| ($("#addressDetail").val() == null) ) ) {
            error("请填写至少一个地址信息")
            return
        }
        
        

        //校验输入
        $("#mform_item").data('bootstrapValidator').resetForm();
        $("#mform_item").data("bootstrapValidator").validate();
        var flag = $("#mform_item").data("bootstrapValidator").isValid();


        if (flag) {
            var data = $("#mform_item").serializeArray();
            
            var areaCodeParam = {}
            areaCodeParam.name = "areaCode"
            areaCodeParam.value = $("#provinceCode").val() + $("#cityCode").val() + $("#districtCode").val()
            data.push(areaCodeParam)
            console.log(data)
            var url = getRPath() + "/manager/orderInfo/saveOrUpdate";
            var callback = function (data) {
                if (data.errCode == 1) {
                    $('#myModal_item').modal("hide");
                    doSearch_item();
                    success("操作成功！");
                } else {
                    error(data.errMsg);
                }
            }
            sendRequest(url, data, callback)
        }
    });

    /**
     * 提交导入Excel文件
     */
    $("#submitFileImport").click(function () {

        var fileCheck = $(".text-danger").length > 0
        if (fileCheck) {
            error("请选择正确的文件")
            return
        }

        //校验锁企是否选择
        if ($("#importCompanyId").val() == "" || $("#importCompanyId").val() == null) {
            error("请选择锁企")
            return
        }

        //校验输入
        $("#importModalForm").data('bootstrapValidator').resetForm();
        $("#importModalForm").data("bootstrapValidator").validate();
        var flag = $("#importModalForm").data("bootstrapValidator").isValid();

        if (flag) {
            var data = new FormData($("#importModalForm")[0])
            console.log(data)
            var url = getRPath() + "/manager/orderInfo/importDataByExcel";
            var callback = function (data) {
                if (data.errCode == 1) {
                    $('#importFileModal').modal("hide");
                    doSearch_item();
                    success("操作成功！");
                } else {
                    error(data.errMsg,5000);
                }
            }
            sendMultiRequest(url, data, callback)
        }
    });

    /**
     *
     */
    $("#submitConfirm").click(function () {
        $("#updateOrderInfoForm").data("bootstrapValidator").validate();
        var flag = $("#updateOrderInfoForm").data("bootstrapValidator").isValid();

        if (flag) {
            var parameter = $("#updateOrderInfoForm").serializeArray()
            var url = getRPath() + "/manager/orderInfo/updateCreateOrderInfo";
            var callback = function (data) {
                if (data.errCode = 1) {
                    $('#updateOrderInfoModal').modal("hide");
                    doSearch_item();
                    success("操作成功！");
                } else {
                    error(data.errMsg);
                }
            }
            sendRequest(url, parameter, callback)
        }
    });
});

/**
 * 初始化表格
 * @constructor
 */
function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/orderInfo/torderinfoList', // 请求后台的URL（*）
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
            
            param.xx="1";
            
            return param;
        },
        rowAttributes:function rowAttributes(row, index) {
        	return {
        	'title':"点击行查看详情",
        	
        	}
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
                field: 'orderState',
                title: '订单状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                    return "<span style='color: red'>待平台确认</span>"
                }
            },
            {
                field: 'createRemark',
                title: '备注',
                align: 'center',
                valign: 'middle',
                formatter: function (value,row) {
                    return RemarkData2(row);
                }
            },
            {
                field: 'appointmentTime',
                title: '预约时间',
                visible: false,
                align: 'center',
                valign: 'middle',
                formatter: function (value) {
                	if(value)
                    return value.split(".")[0]
                	else
                		return "";
                }
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
                valign: 'middle',
            },
            {
                field: 'addressDetail',
                title: '详细地址',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'sellerTotalPrice',
                title: '锁企服务费',
                align: 'center',
                visible: false,
                valign: 'middle'
            },{
                field: 'lockerTotalPrice',
                title: '锁匠服务费',
                align: 'center',
                visible: false,
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
                title: '操作',
                field: 'xxx',
                align: 'center',
                valign: 'middle',
                formatter: modifyAndDeleteButton_item,
                events: PersonnelInformationEvents_item
            }
        ],
        detailView: true,
        onExpandRow: function (index, row, $detail) {
        	
        	
        	initDetailLog($detail,row.orderNo);
            //var subTable = $detail.html('<table class="nohide"></table>').find('table');
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

/**
 * 按钮格式化
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
/*function modifyAndDeleteButton_item(value, row, index) {
    var editButtonWords
    var managerType = $("#managerType").val()
    if (managerType == 0) {
        editButtonWords = "确认工单";
    } else if (managerType == 2){
        editButtonWords = "更新";
    }
    return ['<div class="">'
    + '<button type = "button" class = "btn btn-info" onclick="showOrderInfo(\'' + row.id + '\')"><i class="glyphicon glyphicon-edit" >' + editButtonWords + '</i> </button>'
    
    + '<button type = "button" id="showEdit" class = "btn btn-info" onclick="showOrderInfo(\'' + row.id + '\')"><i class="glyphicon glyphicon-edit" >' + editButtonWords + '</i> </button>'
    
    
    + '<button type = "button" class = "btn btn-danger" onclick="deleteOrderInfo(\'' + row.id + '\')"><i class="glyphicon glyphicon-trash">删除</i> </button>'
    + '</div>'].join("");
}*/

/**
 * 删除事件
 * @param orderInfoId
 */
function deleteOrderInfo(orderInfoId) {
    var msg = "您真的确定要删除吗？";
    var url = getRPath() + "/manager/orderInfo/delete";
    cconfirm(msg, function () {
        $.ajax({
            type: "post",
            url: url,
            data: {
                "id": orderInfoId
            },
            success: function (response) {
                if (response.bol) {
                    success("删除成功！");
                    doSearch_item();
                } else {
                    error("" + response.message);
                }
            }
        });
    });
}

/*window.buttonEvent = {
    "click .btn-danger": function (e, value, row, index) {

    }
};
*/
function doSearch_item() {
    var opt = {
        silent: true,
        pageNumber: 1
    };
    $("#table_list_item").bootstrapTable('refresh', opt);
}

