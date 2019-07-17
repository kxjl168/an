var managerType = $("#managerType").val()

$(function () {

    InitQuery_item();
    InitQuery_money();
    InitQuery_orderMoney();
    //初始化时间选择器
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
     * 分派工单模态框消失
     */
    $('#allocateOrderModal').on('hide.bs.modal', function (e) {
        $('#allocateOrderForm')[0].reset();
        $("#lockerSelect").empty()
        $("#lockerId").val("")
        $("#orderId").val("")
    });

   
});

/**
 * 表格初始化
 * @constructor
 */
function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/torderinfo_todo/torderinfoList', // 请求后台的URL（*）
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
                field: 'companyName',
                title: '合伙人',
                align: 'center',
                visible: false,
                valign: 'middle',
            },
           
            {
                field: 'createTime',
                title: '工单创建时间',
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
                field: 'receiveTime',
                title: '分单时间',
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
                title: '安装地址',
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
                visible: false,
                align: 'center',
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
 * 行按钮事件
 * @type {{click #delete: Window.PersonnelInformationEvents_item.click #delete, click #allocateOrder: Window.PersonnelInformationEvents_item.click #allocateOrder}}
 */

/**
 * 查询事件
 */
function doSearch_item() {
    var opt = {
        silent: true,
        pageNumber: 1
    };
    $("#table_list_item").bootstrapTable('refresh', opt);
}

