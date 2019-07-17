
$(function() {
	InitQuery_item();

    //初始化时间选择器
    initDataTimePicker("appointmentTime")
    initDataTimePicker("startTime")
    initDataTimePicker("endTime")
    initDataTimePicker("updateAppointmentTime")

	$("#btnAdd_item").click(function() {
	  $("#myModal_item_add").modal();
	});

});


//评分奖励规则列表
function InitQuery_item() {
	// 初始化Table
	$('#table_list_item').bootstrapTable({
		url : getRPath()+'/manager/gradeMoney/list', // 请求后台的URL（*）
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

                searchScore : $("#searchScore").val(),
			};
			return param;
		},
		columns : [ {
			field : 'id',
			visible : false
		},
		 {
			field : 'startScore',
			title : '起始分数',
			align : 'center',
			valign : 'middle'
		},
		{
			field : 'endScore',
			title : '结束分数',
			align : 'center',
			valign : 'middle'
		},
        {
            field : 'addMoney',
            title : '奖励金额',
            align : 'center',
            valign : 'middle'
        },
        {
			field : 'createTime',
			title : '创建时间',
			align : 'center',
			valign : 'middle',
            formatter: function (value, row, index) {
                return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
            }
		 },
		{
			title: '操作',
			field: 'vehicleno',
			align: 'center',
			formatter: modifyAndDeleteButton_item,
			events: PersonnelInformationEvents_item1
		}
		]
	});
}

/**
 * 按钮格式化
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function modifyAndDeleteButton_item(value, row, index) {
        return ['<div class="">'
        + '<button id = "editG" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改奖励价格</i> </button>&nbsp;'
        + '<button id = "deleteG" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
        + '</div>'].join("");
}

/**
 * 行按钮事件
 * @type {{click #delete: Window.PersonnelInformationEvents_item.click #delete, click #allocateOrder: Window.PersonnelInformationEvents_item.click #allocateOrder}}
 */
window.PersonnelInformationEvents_item1 = {
	//修改奖励价格
    "click #editG": function (e, value, row, index) {
        $("#changeId").val(row.id);
        $("#changMoney").val(row.addMoney);
        $("#myModal_item_edit").modal("show");
    },
	//删除
    "click #deleteG": function (e, value, row, index) {
        var msg = "您真的确定要删除吗？";
        cconfirm(msg,function() {
            $.ajax({
                type : "post",
                url : getRPath()+'/manager/gradeMoney/delete',
                data : {
                    "id" : row.id
                },
                success : function(response) {
                    if (response.result) {
                        success("删除成功！");
                        doOptionTableRefresh();
                    } else {
                        error("删除失败！");
                    }
                }
            });
        });
    }
};

/**
 * 添加奖励规则
 */
$("#btnSubmit_item_add").click(function(){
    //验证
    $("#mform_item_add").bootstrapValidator('validate');
    var flag = $("#mform_item_add").data("bootstrapValidator").isValid();
    if(!flag){
        return;
    }
    $.ajax({
        type: "post",
        url: getRPath()+'/manager/gradeMoney/addGradeMoney',
        data: {
            "startScore":$("#startScore").val(),
            "endScore":$("#endScore").val(),
            "addMoney":$("#addMoney").val()
        },
        traditional: true,
        success: function (response) {
            if (response.result) {
                $("#myModal_item_add").modal("hide");
                success("添加成功！");
                $("#startScore").val("");
                $("#endScore").val("");
                $("#addMoney").val("");
                $("#mform_item_add").data('bootstrapValidator').resetForm();
                doOptionTableRefresh();
            } else {
                error("添加失败！");
            }
        }
    });
});

/**
 * 修改奖励规则
 */
$("#btnSubmit_item_edit").click(function(){
    $.ajax({
        type: "post",
        url: getRPath()+'/manager/gradeMoney/changeGradeMoney',
        data: {
            "id":$("#changeId").val(),
            "addMoney":$("#changMoney").val()
        },
        traditional: true,
        success: function (response) {
            if (response.result) {
                $("#myModal_item_edit").modal("hide");
                success("修改成功！");
                doOptionTableRefresh();
                $("#mform_item_edit").data('bootstrapValidator').resetForm();
            } else {
                error("修改失败！");
            }
        }
    });
});

$("#gmClose").click(function(){
    $("#startScore").val("");
    $("#endScore").val("");
    $("#addMoney").val("");
    $("#mform_item_add").data('bootstrapValidator').resetForm();
})

$("#gmeClose").click(function(){
    $("#mform_item_edit").data('bootstrapValidator').resetForm();
})





function doOptionTableRefresh() {
    var opt = {
        silent : true
    };
    $("#table_list_item").bootstrapTable('refresh', opt);
    //success("test");
}





