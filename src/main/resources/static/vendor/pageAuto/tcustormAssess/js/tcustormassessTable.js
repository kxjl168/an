$(function() {
	InitQuery_item();
    handerTd();

});

function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath()+'/manager/custormAssessQ/list', // 请求后台的URL（*）
        method: 'post', // 请求方式（*）
        contentType: 'application/x-www-form-urlencoded',
        toolbar: '#toolbar', // 工具按钮用哪个容器
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

                title : $("#title").val(),
                curUse : $("#curUse").val(),
                startTime : $("#startTime").val(),
                endTime : $("#endTime").val()
            };
            return param;
        },
        columns : [
            {
                field : 'title',
                title : '问卷调查标题',
                align : 'center',
                valign : 'middle'
            },
            {
                field : 'curUse',
                title : '开启状态',
                align : 'center',
                valign : 'middle',
                formatter: function (value) {
                    if (value == 1) {
                        return "启用"
                    }
                    if (value == 0) {
                        return "禁用"
                    }
                }
            },
            {
                field : 'createTime',
                title : '创建时间',
                align : 'center',
                valign : 'middle',
                formatter: function (value) {
                    if(value != null)
                        return value.split(".")[0]
                    else
                        return "";
                }
            },
            {
                title: '操作',
                field: '11',
                align: 'center',
                formatter: modifyAndDeleteButton_item1,
                events: PersonnelInformationEvents_item1
            }
        ],
        detailView: true,
        onExpandRow: function (index, row, $detail) {
            var subTable = $detail.html('<table id="questionTable"></table>').find('table');
            $(subTable).bootstrapTable({
                url: getRPath()+'/manager/custormAssessQ/selectQusOrOpt', // 请求后台的URL（*）
                queryParams : function queryParams(params) { // 设置查询参数
                    var param = {
                        pageSize : params.limit, // 每页要显示的数据条数
                        offset : params.offset, // 每页显示数据的开始行号
                        sortName : params.sort, // 要排序的字段
                        sortOrder : params.order, // 排序规则

                        assessId : row.id,
                        type : '1'
                    };
                    return param;
                },
                // data: row.questions,
                uniqueId: "id",
                detailView: true,
                columns : [
                    {
                        field : 'id',
                        title: 'id',
                        align: 'center',
                        valign: 'middle',
                        visible : false
                    },
                    {
                        field : 'sortstring',
                        title : '排序号',
                        align : 'center',
                        valign : 'middle'
                    },
                    {
                        field : 'title',
                        title : '题目标题',
                        align : 'center',
                        valign : 'middle'
                    },
                    {
                        field : 'resultType',
                        title : '题目类型',
                        align : 'center',
                        valign : 'middle',
                        formatter: function (value) {
                            if(value == "0"){
                                return "单选"
                            }
                            if(value == "1"){
                                return "可多选"
                            }
                            if(value == "2"){
                                return "手动输入"
                            }
                        }
                    },
                    {
                        title: '操作',
                        field: '11',
                        align: 'center',
                        formatter: modifyAndDeleteButton_item2,
                        events: PersonnelInformationEvents_item2
                    }
                ],
                onExpandRow: function (index, row, $SubDetail) {
                    var subTable = $SubDetail.html('<table id="optionsTable"></table>').find('table');
                    $(subTable).bootstrapTable({
                        url: getRPath()+'/manager/custormAssessQ/selectQusOrOpt', // 请求后台的URL（*）
                        queryParams : function queryParams(params) { // 设置查询参数
                            var param = {
                                pageSize : params.limit, // 每页要显示的数据条数
                                offset : params.offset, // 每页显示数据的开始行号
                                sortName : params.sort, // 要排序的字段
                                sortOrder : params.order, // 排序规则

                                assessId : row.assessId,
                                parentid : row.id,
                                type : '2'
                            };
                            return param;
                        },
                        uniqueId: "id",
                        columns: [
                            {
                            field : 'id',
                            title: 'id',
                            align: 'center',
                            valign: 'middle',
                            visible : false
                            },
                            {
                                field : 'sortstring',
                                title : '排序号',
                                align : 'center',
                                valign : 'middle'
                            },
                            {
                                field : 'title',
                                title : '选项描述',
                                align : 'center',
                                valign : 'middle'
                            },
                            {
                                field : 'score',
                                title : '选项分值',
                                align : 'center',
                                valign : 'middle'
                            },
                            {
                                field : 'addService',
                                title : '选择选项是否生成售后单',
                                align : 'center',
                                valign : 'middle',
                                formatter: function (value) {
                                    if (value == 0) {
                                        return "否"
                                    }
                                    if (value == 1) {
                                        return "是"
                                    }
                                }
                            },
                            {
                                title: '操作',
                                field: '11',
                                align: 'center',
                                formatter: modifyAndDeleteButton_item3,
                                events: PersonnelInformationEvents_item3
                            }],
                    });
                }
            });
            handerTd();//修复+  导致表头错位
        }
    }).on("click-row.bs.table", function (event,row, rowele,field){
        //  var subTable = $(rowele).html('<table class="nohide"></table>').find('table');


        showOrCloseDetail(rowele,row,1)
    });


}



/**
 * 表修复  +导致表头错位
 */
function handerTd(){
    $("thead").each(function(i,item){
        if($(this).find("tr:first").find("th:first").text() == "排序号"){
            return;
        }
        if($(this).find("tr:first").find("th:first").text() != " "){
            $(this).find("tr:first").prepend("<th> </th>");
        }
    });
}

/**
 * 表修复  去除 +
 */
/*function handerTd(){
    $("tbody").each(function(i,item){
        $(this).children(".detail-icon").each(function(i,item){
            debugger;
           alert($(this).html());
        });

    });
}*/

/**
 * 显示或者隐藏子表
 */
function showOrCloseDetail(tr, data, index) {

    if ($(tr.prevObject)[0].nodeName == "#document" || (  $($(tr.prevObject)[0].nodeName && ($(tr.prevObject)[0].nodeName == "TD")) )) {

        if ($(tr.prevObject)[0].nodeName == "#document") {

        }

//最后一个行有操作按钮的，不操作子行。
        else if ($(tr.prevObject).find("button").length > 0) {

            return;
        }


        $(tr).parent().find(".detail-view").trigger('collapse-row');


        $(tr).find(".detail-icon i").click();
    }
}