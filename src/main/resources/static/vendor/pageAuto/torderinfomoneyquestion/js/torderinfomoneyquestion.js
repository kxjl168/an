function test() {


    var url = getRPath() + "/manager/torderinfomoneyquestion/test";

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
    InitQuery_item();
    initValidate_item();
    
    //改价查询
    InitQuery_orderMoney();

    $('#myModal_itemMoney').on('hide.bs.modal', function (e) {
        $('#mform_itemmoney')[0].reset();
        $("#mform_itemmoney").data('bootstrapValidator').resetForm();
    });

    $("#agree").click(function () {
        $("#mform_itemmoney").data('bootstrapValidator').resetForm();
        $("#mform_itemmoney").data("bootstrapValidator").validate();
        var flag = $("#mform_itemmoney").data("bootstrapValidator").isValid();
        if (flag) {
            var data = {};
            data["id"] = $("#id").val();
            data["doneContent"] = $("#doneContent").val();
            data["serviceState"] = 1;
            var url = getRPath() + "/manager/torderinfomoneyquestion/saveOrUpdate";
            $.ajax({
                type: "post",
                url: url,
                data: data,
                async: false,
                dataType: "json",
                success: function (response) {
                    if (response.bol) {
                        $('#mform_itemmoney')[0].reset();
                        $('#myModal_itemMoney').modal("hide");
                        doSearch_item();
                        success("操作成功！");
                    } else {
                        error(response.message);
                    }
                }
            });
        }
    })

    $("#refuse").click(function () {
        $("#mform_itemmoney").data('bootstrapValidator').resetForm();
        $("#mform_itemmoney").data("bootstrapValidator").validate();
        var flag = $("#mform_itemmoney").data("bootstrapValidator").isValid();
        if (flag) {
            var data = {};
            data["id"] = $("#id").val();
            data["doneContent"] = $("#doneContent").val();
            data["serviceState"] = 2;
            var url = getRPath() + "/manager/torderinfomoneyquestion/saveOrUpdate";
            $.ajax({
                type: "post",
                url: url,
                data: data,
                async: false,
                dataType: "json",
                success: function (response) {
                    if (response.bol) {
                        $('#mform_itemmoney')[0].reset();
                        $('#myModal_itemMoney').modal("hide");
                        doSearch_item();
                        success("操作成功！");
                    } else {
                        error(response.message);
                    }
                }
            });
        }
    })
});

function initValidate_item() {
    $("#mform_itemmoney").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
            doneContent: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '输入的内容长度须小于200字符',
                        //trigger: 'change',
                        callback: function (value, validator) {
                        	//msg(1);
                            if (value.length >200) {
                                return false
                            }
                            return true
                        }
                    }
                }
            }
        }
    });
}

function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/torderinfomoneyquestion/torderinfomoneyquestionList', // 请求后台的URL（*）
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
                phone: $("#phone").val(),
                orderNo: $("#orderNo").val(),
                serviceState: $("#serviceState").val(),
            };
            return param;
        },
        rowAttributes: function rowAttributes(row, index) {
            return {
                'title': "点击行查看详情",
            }
        },
        columns: [{
            field: 'id',
            visible: false
        },
            {
                field: 'orderNo',
                title: '工单编号',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'name',
                title: '锁匠名',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'phone',
                title: '锁匠手机号',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'messageContent',
                title: '消息备注',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'doneContent',
                title: '完成备注',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                
                	var districtnames=value;
                	if(typeof(districtnames)=='undefined')
                		return "";
                	
                	var dtxt="";
                if(districtnames.length<20)
            		dtxt=districtnames;
            	else
            		dtxt=districtnames.substr(0,20)+"...";
            	
            	
            		dtxt="<span class='usertippy' data-tippy-content='"+districtnames+"'  title='"+districtnames+"'>"+dtxt+"</span>";
            		
            	
            	
            		setTimeout(function(){
            			tippy(".usertippy",{
            					 arrow: true,
            					  arrowType: 'round', // or 'sharp' (default)
            					  animation: 'perspective',
            			}
                				)
            		},500);
            		
            		return dtxt;
                
                }
                
            },
            {
                field: 'createTime',
                title: '时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return new Date(parseFloat(value)).Format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {
                field: 'serviceState',
                title: '状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0:
                            return "<b class='text-warning'>待处理</b>"
                        case 1:
                            return "<b class='text-success'>已通过</b>"
                        case 2:
                            return "<b class='text-danger'>已拒绝</b>"
                    }
                }
            },
            {
                title: '操作',
                field: 'vehicleno',
                align: 'center',
                formatter: modifyAndDeleteButton_item,
                events: window.PersonnelInformationEvents_item
            }
        ],
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
}

function modifyAndDeleteButton_item(value, row, index) {
    var html = '<div class="">';
        
    //if(row.serviceState==0)
    html+='<button id = "moneyModify2" type = "button" data-tippy-content="工单改价"    class = "tippy btn btn-warning"><i class="fa fa-cny"></i> </button>&nbsp;';
    if(row.serviceState ==0){
            html+='<button id = "updatemoneyquestion" type = "button" data-tippy-content="申诉审核"    class = "tippy btn btn-info"><i class="fa fa-check"></i> </button>'
        }
        
        
    html+= '</div>'
    	
    	setTimeout(function(){
			tippy(".tippy",{
					 arrow: true,
					  arrowType: 'round', // or 'sharp' (default)
					  animation: 'perspective',
			}
    				)
		},500);
    	
    return [html].join("");
}


/*window.PersonnelInformationEvents_item = {
  
};
*/
function doSearch_item() {
    var opt = {
        silent: true
    };
    $("#table_list_item").bootstrapTable('refresh', opt);

    //success("test");
}

