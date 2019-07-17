
$(function() {

    //初始化时间选择器
    initDataTimePicker("appointmentTime")
    initDataTimePicker("startTime")
    initDataTimePicker("endTime")
    initDataTimePicker("updateAppointmentTime")

	$("#btnAdd_item").click(function() {
	  $('#mform_item')[0].reset();
	  $('#mform_item').find("#id").val("");
	  $("#myModal_item_title").html("添加");
	  $("#distpicker2").distpicker({
		  autoSelect: true
	  });
	  $("#myModal_item_add").modal();
	});

});


//新增问卷
$("#btnSubmit_item").click(function(){
    //验证
    $("#myModal_item_add").bootstrapValidator('validate');
    var flag = $("#myModal_item_add").data("bootstrapValidator").isValid();
    if(!flag){
        return;
    }
	var title = $("#questionTitle").val();//试卷标题
    $.ajax({
        type : "post",
        url : getRPath()+'/manager/custormAssessQ/addAssessQuestion',
        data :{title:title},
        async : false,
        dataType : "json",
        success : function(response) {
        	if(response.result){
                $('#myModal_item_add').modal("hide");
        		success("新增成功！");
                doSearch_item();
			}else{
        		fail("新增失败！");
            }
        }
    });
})

$("#addClose").click(function(){
    $("#myModal_item_add").data('bootstrapValidator').resetForm();
});


/**
 * 按钮格式化（试卷）
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function modifyAndDeleteButton_item1(value, row, index) {
	if(row.curUse == "1"){//0：未启用，1：启用
        return ['<div class="">'
        + '<button id = "addQuestion" type = "button" class = "btn btn-info" ><i class="glyphicon glyphicon-pencil">添加题目</i> </button>&nbsp;'
        + '<button id = "openOrClose" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">禁用</i> </button>&nbsp;'
        + '<button id = "deleteAssess" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
        + '</div>'].join("");
    }else if(row.curUse == "0"){
        return ['<div class="">'
        + '<button id = "addQuestion" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">添加题目</i> </button>&nbsp;'
        + '<button id = "openOrClose" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">启用</i> </button>&nbsp;'
        + '<button id = "deleteAssess" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
        + '</div>'].join("");
	}
}

/**
 * 行按钮事件（）
 * @type {{click #delete: Window.PersonnelInformationEvents_item.click #delete, click #allocateOrder: Window.PersonnelInformationEvents_item.click #allocateOrder}}
 */
window.PersonnelInformationEvents_item1 = {
    //添加题目
    "click #addQuestion": function (e, value, row, index) {
        $("#qusAssessId").val(row.id);
        $("#myModal_item_title_1").html("添加题目");
        $("#myModal_item_edit").modal("show");
    },
    //开启或关闭
    "click #openOrClose": function (e, value, row, index) {
        $.ajax({
            type: "post",
            url: getRPath()+'/manager/custormAssessQ/openOrClose',
            data: {
                "id": row.id,
                "curUse" : row.curUse
            },
            success: function (response) {
                if (response.result) {
                    success("成功！");
                    doSearch_item();
                } else {
                    error("失败！" + response.msg );
                }
            }
        });
    },
    //删除
    "click #deleteAssess": function (e, value, row, index) {
        var msg = "您真的确定要删除吗？";
        cconfirm(msg, function () {
            $.ajax({
                type: "post",
                url: getRPath()+'/manager/custormAssessQ/delete',
                data: {
                    "id": row.id,
                    "curUse" : row.curUse
                },
                success: function (response) {
                    if (response.result) {
                        success("删除成功！");
                        doSearch_item();
                    } else {
                        error("删除失败！"+ response.msg);
                    }
                }
            });
        });
    }
};

/**
 * 按钮格式化（题目）
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function modifyAndDeleteButton_item2(value, row, index) {
    if(row.resultType == '2'){
        return ['<div class="">'
        + '<button id = "editQuestion" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改</i> </button>&nbsp;'
        + '<button id = "deleteQuestion" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
        + '</div>'].join("");
    }else{
        return ['<div class="">'
        + '<button id = "addOption" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">添加选项</i> </button>&nbsp;'
        + '<button id = "editQuestion" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改</i> </button>&nbsp;'
        + '<button id = "deleteQuestion" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
        + '</div>'].join("");
    }

}

window.PersonnelInformationEvents_item2 = {
    //添加选项
    "click #addOption": function (e, value, row, index) {
        $("#myModal_item_title_opt").html("添加选项");
        $("#optParentid").val(row.id);
        $("#myModal_item_optedit").modal("show");
    },
    //修改题目
    "click #editQuestion": function (e, value, row, index) {
        $.ajax({
            type: "post",
            url: getRPath()+'/manager/custormAssessQ/selectOption',
            data: {"id":row.id},
            traditional: true,
            success: function (response) {
                $("#myModal_item_title_1").html("修改题目");

                $("#qusId").val(response.assessQuestionDetail.id);
                // $("#optParentid").val(response.assessQuestionDetail.parentid);
                $("#qusTitle").val(response.assessQuestionDetail.title);
                $("#qusAssessId").val(response.assessQuestionDetail.assessId);
                $("#questionSortstring").val(response.assessQuestionDetail.sortstring);
                $("#questionType").attr({ disabled: "disabled"});
                $("#myModal_item_edit").modal("show");
            }
        });
    },
    //删除问题
    "click #deleteQuestion": function (e, value, row, index) {
        var msg = "您真的确定要删除吗？";
        cconfirm(msg, function () {
            $.ajax({
                type: "post",
                url: getRPath()+'/manager/custormAssessQ/deleteOptionOrQuestion',
                data: {
                    "id": row.id,
                    "assessId": row.assessId
                },
                success: function (response) {
                    if (response.result) {
                        success("删除成功！");
                        $("#questionTable").bootstrapTable('refresh');
                    } else {
                        error("删除失败！"+ response.msg);
                    }
                }
            });
        });
    }
};

/**
 * 修改/添加 问题
 */
$("#btnSubmit_item_editSave").click(function(){
    //验证
    $("#myModal_item_edit").bootstrapValidator('validate');
    var flag = $("#myModal_item_edit").data("bootstrapValidator").isValid();
    if(!flag){
        return;
    }
    $.ajax({
        type: "post",
        url: getRPath()+'/manager/custormAssessQ/updateOption',
        data: {
            "id":$("#qusId").val(),
            "sortstring":$("#questionSortstring").val(),
            "assessId":$("#qusAssessId").val(),
            "title":$("#qusTitle").val(),
            "resultType":$("#questionType").val(),
            "type":"1"
        },
        traditional: true,
        success: function (response) {
            if (response.result) {
                success("成功！");
                $("#myModal_item_edit").modal("hide");
                clearInit();
                $("#questionTable").bootstrapTable('refresh');
            } else {
                error("失败！"+ response.msg);
                $("#myModal_item_edit").modal("hide");
            }
        }
    });
});


/**
 * 按钮格式化（选项）
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function modifyAndDeleteButton_item3(value, row, index) {
        return ['<div class="">'
        + '<button id = "editOption" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改</i> </button>&nbsp;'
        + '<button id = "deleteOption" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
        + '</div>'].join("");
}

window.PersonnelInformationEvents_item3 = {
    //修改选项
    "click #editOption": function (e, value, row, index) {
        $("#myModal_item_title_opt").html("修改选项");
        $.ajax({
            type: "post",
            url: getRPath()+'/manager/custormAssessQ/selectOption',
            data: {"id":row.id},
            traditional: true,
            success: function (response) {
                $("#optId").val(response.assessQuestionDetail.id);
                // $("#optParentid").val(response.assessQuestionDetail.parentid);
                $("#optTitle").val(response.assessQuestionDetail.title);
                $("#optScore").val(response.assessQuestionDetail.score),
                $("#optAssessId").val(response.assessQuestionDetail.assessId);
                $("#optParentid").val(response.assessQuestionDetail.parentid);
                $("#optSortstring").val(response.assessQuestionDetail.sortstring);
                $("#optAddService").html("");
                if(response.assessQuestionDetail.addService == 0){
                    $("#optAddService").html("<option value=\"1\">是</option>\n" +
                        "                     <option value=\"0\" selected>否</option>");
                }else{
                    $("#optAddService").html("<option value=\"1\" selected>是</option>\n" +
                        "                     <option value=\"0\">否</option>");
                }

                $("#optSortstring").val(response.assessQuestionDetail.sortstring);
                $("#myModal_item_optedit").modal("show");
            }
        });
    },
    //删除选项
    "click #deleteOption": function (e, value, row, index) {
        var msg = "您真的确定要删除吗？";
        cconfirm(msg, function () {
            $.ajax({
                type: "post",
                url: getRPath()+'/manager/custormAssessQ/deleteOptionOrQuestion',
                data: {
                    "id": row.id,
                    "assessId": row.assessId
                },
                success: function (response) {
                    if (response.result) {
                        success("删除成功！");
                        $("#optionsTable").bootstrapTable('refresh');
                    } else {
                        error("删除失败！"+ response.msg);
                    }
                }
            });
        });
    }
};


/**
 * 修改/添加 选项
 */
$("#btnSubmit_item_editOptSave").click(function(){
    debugger
    //验证
    $("#mform_item_opt").bootstrapValidator('validate');
    var flag = $("#mform_item_opt").data("bootstrapValidator").isValid();
    if(!flag){
        return;
    }
    $.ajax({
        type: "post",
        url: getRPath()+'/manager/custormAssessQ/updateOption',
        data: {
            "id":$("#optId").val(),
            "addService":$("#optAddService").val(),
            "sortstring":$("#optSortstring").val(),
            "assessId":$("#optAssessId").val(),
            "title":$("#optTitle").val(),
            "parentid":$("#optParentid").val(),
            "score":$("#optScore").val(),
            "type":"2"
        },
        traditional: true,
        success: function (response) {
            if (response.result) {
                success("修改成功！");
                $("#myModal_item_optedit").modal("hide");
                $("#optionsTable").bootstrapTable('refresh');
                optClearInit();
            } else {
                error("修改失败！"+ response.msg);
                $("#myModal_item_optedit").modal("hide");
            }
        }
    });
});


/**
 * 处理编辑后显示参数
 */
function handerEditData(data) {
    $("#assessTitle").val(data.title);//问卷名
    $("#assessId").val(data.id);//问卷id
    $.each(data.questions,function(i,question){
        var html = "";
        html = "<div name=\"question\">\n" +
            "                                    <input type=\"hidden\" id=\"resultType\" value=\"0\">\n" +
            "                                    <p>----------------------------------------------------------------------------------------------</p>\n" +
            "                                    （单选） &nbsp;&nbsp;&nbsp;&nbsp; <button type=\"button\" class=\"btn btn-warning\"  name=\"addOption\">新增选项</button>\n" +
            "                                    <p></p>\n" +
            "                                    <label for=\"name\" class=\"col-lg-3 control-label\">题号</label>\n" +
            "                                    <div class=\"col-lg-9\">\n" +
            "                                        <input type=\"text\" name=\"sortstring\"\n" +
            "                                               class=\"form-control\" id=\"sortstring\"\n" +
            "                                               placeholder=\"题号\" maxlength=\"200\" value="+ question.sortstring +">\n" +
            "                                        <p class=\"help-block\"></p>\n" +
            "                                    </div>\n" +
            "                                    <label for=\"name\" class=\"col-lg-3 control-label\" style=\"color: lightblue;\">题目问题</label>\n" +
            "                                    <div class=\"col-lg-9\">\n" +
            "                                        <input type='hidden' name=\"questionId\"\n" +
            "                                               class=\"form-control\" id=\"questionId\"\n" +
            "                                               placeholder=\"题目id\" maxlength=\"200\" value="+ question.id +">\n" +
            "                                        <input type=\"text\" name=\"questionTitle\"\n" +
            "                                               class=\"form-control\" id=\"questionTitle\"\n" +
            "                                               placeholder=\"题目问题\" maxlength=\"200\" value="+ question.title +">\n" +
            "                                        <p class=\"help-block\"></p>\n" +
            "                                    </div>\n" +
            "                                    <p></p>\n";

        $.each(question.options,function(i,option){
            var ySelect = "";
            var nSelect = "selected";
            if(option.addService == "1"){
                ySelect = "selected";
                nSelect = "";
            }
            html = html + "<div name=\"option\" style=\"width: 400px;margin-left: 136px;\">\n" +
                "                                        -\n" +
                "                                        <p></p>\n" +
                "                                        <label for=\"name\" class=\"col-lg-3 control-label\">选项</label>\n" +
                "                                        <div>\n" +
                "                                            <div class=\"col-lg-9\">\n" +
                "                                                <input type='hidden' name=\"optId\"\n" +
                "                                                       class=\"form-control\" id=\"optId\"\n" +
                "                                                       placeholder=\"选项id\" maxlength=\"200\" value="+ option.id +">\n" +
                "                                                <input type=\"text\" name=\"optValue\"\n" +
                "                                                       class=\"form-control\" id=\"optValue\"\n" +
                "                                                       placeholder=\"选项值\" maxlength=\"200\" value="+ option.title +">\n" +
                "                                                <p class=\"help-block\"></p>\n" +
                "                                            </div>\n" +
                "                                            <label for=\"name\" class=\"col-lg-6 control-label\">选择此选项是否生成售后单</label>\n" +
                "                                            <select class=\"form-control\" style=\"width:100px \" name='addService'><option value=\"1\" "+ ySelect +">是</option><option value=\"0\" "+ nSelect +">否</option></select>\n" +
                "                                        </div>\n" +
                "                                    </div>";
        });

        html = html + "</div>";
        $("#questions").append(html);

        addClickEvent();

    });
}


function doSearch_item() {
    var opt = {
        silent : true
    };
    $("#table_list_item").bootstrapTable('refresh', opt);
    //success("test");
}

function doQuestionTableRefresh() {
    var opt = {
        silent : true
    };
    $("#table_list_item").bootstrapTable('refresh', opt);
    //success("test");
}

function doOptionTableRefresh() {
    var opt = {
        silent : true
    };
    $("#optionsTable").bootstrapTable('refresh', opt);
    //success("test");
}









//生成32位UUID
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid.replace(/\-/g,"");
}

//添加append后节点事件
function addClickEvent() {
    //添加append后节点事件
    $("#questions").on('click','button',function(){
        $(this).parent().append("<div name=\"option\" style=\"width: 400px;margin-left: 136px;\">\n" +
            "                                        -\n" +
            "                                        <p></p>\n" +
            "                                        <label for=\"name\" class=\"col-lg-3 control-label\">选项</label>\n" +
            "                                        <div>\n" +
            "                                            <div class=\"col-lg-9\">\n" +
            "                                                <input type=\"text\" name=\"optValue\"\n" +
            "                                                       class=\"form-control\" id=\"optValue\"\n" +
            "                                                       placeholder=\"选项值\" maxlength=\"200\">\n" +
            "                                                <p class=\"help-block\"></p>\n" +
            "                                            </div>\n" +
            "                                            <label for=\"name\" class=\"col-lg-6 control-label\">选择此选项是否生成售后单</label>\n" +
            "                                            <select class=\"form-control\" style=\"width:100px \"  name='addService'><option value=\"1\">是</option><option value=\"0\" selected>否</option></select>\n" +
            "                                        </div>\n" +
            "                                    </div>");
    });
}

//模态框关闭清楚
function clearInit(){
    $("#myModal_item_edit").data('bootstrapValidator').resetForm();
    $("#qusTitle").val("");
    $("#questionSortstring").val("");
    $("#questionType").removeAttr("disabled");
}



function optClearInit(){
    $("#mform_item_opt").data('bootstrapValidator').resetForm();
    $("#optTitle").val("");
    $("#optScore").val(""),
    $("#optSortstring").val("");
    // $("#optAddService").val("");
}
