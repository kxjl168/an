function test(){
	
	
	var url = getRPath()+"/manager/tlocksmithenterprisecomplain/test";
	
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
    initManagerOrderNoSelect();
    InitQuery_item();



	$("#btnAdd_item").click(function() {

        initManagerOrderNoSelect();

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
    $('#myModal_item1').on('hide.bs.modal', function(e) {

        if($(e.target).attr("type")) //日期选择等弹出框
            return;

        $('#mform_item1')[0].reset();

        $("#mform_item1").data('bootstrapValidator').resetForm();

    });

	$("#btnSubmit_item").click(function() {
		
		
		
		$("#mform_item").data('bootstrapValidator').resetForm();
		
		
		 
		    // var bool2 = bv.isValid();
		$("#mform_item").data("bootstrapValidator").validate();
		// flag = true/false
		var flag = $("#mform_item").data("bootstrapValidator").isValid();

		var url = getRPath()+"/manager/tlocksmithenterprisecomplain/saveOrUpdate";

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

    $("#btnSubmit_item1").click(function() {
        $("#mform_item1").data('bootstrapValidator').resetForm();
        $("#mform_item1").data("bootstrapValidator").validate();
        var flag = $("#mform_item1").data("bootstrapValidator").isValid();
        var url = getRPath()+"/manager/tlocksmithenterprisecomplain/saveOrUpdate";
        if (flag) {
            var data = $("#mform_item1").serialize();
            $.ajax({
                type : "post",
                url : url,
                data : data,
                async : false,
                dataType : "json",
                success : function(response) {
                    if (response.bol) {
                        $('#myModal_item1').modal("hide");
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
    $("#mform_item1").bootstrapValidator({
        feedbackIcons : {
            /* input状态样式通过，刷新，非法三种图片 */
            valid : 'glyphicon glyphicon-ok',
            invalid : 'glyphicon glyphicon-remove',
            validating : 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields : {

            backContent : {
                validators : {
                    notEmpty : {
                        message : '不能为空'
                    }
                }
            },
            doneContent : {
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
		url : getRPath()+'/manager/tlocksmithenterprisecomplain/tlocksmithenterprisecomplainList', // 请求后台的URL（*）
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
				
				
				
				enterpriseName : $("#q_enterpriseId").val(),
				messageContent : $("#q_messageContent").val(),
				
				
			};
			return param;
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
				field : 'enterpriseName',
				title : '投诉锁企名',
				align : 'center',
				valign : 'middle',
				   
				
			},
		 {
				field : 'messageContent',
				title : '投诉内容',
				align : 'center',
				valign : 'middle',
				   
				
			},

            {
                field : 'createTime',
                title : '投诉时间',
                align : 'center',
                valign : 'middle',

                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }

            },
            {
                field : 'serviceState',
                title : '状态',
                align : 'center',
                valign : 'middle',
                formatter: function (value, row, index) {
                    if (value == 0)
                        return "已提交";
                    else if (value == 1)
                        return "客服已确认";
                    else if (value == 2)
                        return "处理完成";
                }
            },
		 {
				field : 'backContent',
				title : '确认备注',
				align : 'center',
				valign : 'middle',
				   
				
			},
		 {
				field : 'doneContent',
				title : '完成备注',
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
	var html='<div class="">';
    if (row.serviceState==0)
    	html+= '<button id = "update1" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">客服确认</i> </button>&nbsp;'
	else if(row.serviceState==1)
        html+= '<button id = "update2" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">确认完成</i> </button>&nbsp;'
    html+= '<button id = "delete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>';
    html+= '</div>';
    return [html].join("");
}




window.PersonnelInformationEvents_item = {
	"click #update1" : function(e, value, row, index) {
		$('#q_id').val(row.id);
		$('#serviceState').val(1);
        $('#back').show();
        $('#done').hide();
        $("#myModal_item_title1").html("客服确认");
        $("#myModal_item1").modal();
	},

    "click #update2" : function(e, value, row, index) {
        $('#q_id').val(row.id);
        $('#serviceState').val(2);
        $('#done').show();
        $('#back').hide();
        $("#myModal_item_title1").html("确认完成");
        $("#myModal_item1").modal();
    },
    "click #delete": function (e, value, row, index) {
        var msg = "您真的确定执行删除操作吗？";
        var url = getRPath() + "/manager/tlocksmithenterprisecomplain/delete";
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
    }
};

function doSearch_item() {
	
	
	
	var opt = {
		silent : true
	};
	$("#table_list_item").bootstrapTable('refresh', opt);
	
	//success("test");
}

function searchEnterprise() {
	var obj=$('#orderNo');
        $.ajax({
            type : "post",
            url : getRPath()+"/manager/tlocksmithenterprisecomplain/company",
            data : {
                "orderNo" : obj.val()
            },
            success : function(response) {
                $('#enterpriseName').val(response.enterpriseName)
                $('#enterpriseId').val(response.enterpriseId)
            }
        })
	}


/**
 * 查询订单列表
 */
function initManagerOrderNoSelect() {
    $("#mform_item").find("#q_orderNo").select2({
        placeholder : "选择订单",
        minimumInputLength : 0,
        // maximumSelectionLength : 1,
        theme : "bootstrap",
        multiple : true,
        language : {
            errorLoading : function() {
                return "无法载入结果。"
            },
            inputTooLong : function(e) {
                var t = e.input.length - e.maximum, n = "请删除" + t + "个字符";
                return n
            },
            inputTooShort : function(e) {
                var t = e.minimum - e.input.length, n = "请再输入至少" + t + "个字符";
                return n
            },
            loadingMore : function() {
                return "载入更多结果…"
            },
            maximumSelected : function(e) {
                var t = "";// "最多只能选择" + e.maximum + "个";
                return t
            },
            noResults : function() {
                return "未找到结果"
            },
            searching : function() {
                return "搜索中…"
            }
        },
        ajax : {
            type : "post",
            url : getRPath()+"/manager/torderinfooperatelog/orderAllList",
            dataType : "json",
            data : function(params) {
                var page = params.page || 0;
                var query = {
                    orderNo : params.term,
                    pageNum : page,
                    pageSize : 10,
                    dataState:1
                };

                return query;
            },

            processResults : function(data, params) {
                $('#orderNo').val("");
                var selectdatas = [];
                $.each(data.rows, function(index, item) {
                    selectdatas.push({
                        id : item.id,
                        text : item.orderNo
                    });
                });

                return {
                    results : selectdatas,
                    pagination : {
                        more : (params.page * 10 >= data.total) ? false : true
                    }
                };
            }
        },

        templateResult : formatRepo,
        escapeMarkup : function(markup) {
            return markup;
        }, // let our custom formatter work
        templateSelection : function(data, container) {
            $('#orderNo').val(data.text);
            searchEnterprise();
            return data.text;
        }
    });
}

function formatRepo(repo) {
    if (repo.loading) {
        return repo.text;
    }

    var markup = repo.text;

    return markup;
}