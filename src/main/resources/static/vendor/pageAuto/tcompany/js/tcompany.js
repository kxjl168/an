function test() {

    var url = getRPath() + "/manager/tcompany/test";

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


var provinceSelectId = "province1"
    var citySelectId = "city1"
    var districtSelectId = "district1"
    var provinceCodeId = "q_provinceCode"
    var cityCodeId = "q_cityCode"
    var districtCodeId = "q_districtCode"

        var provinceSelectId2 = "province2"
            var citySelectId2 = "city2"
            var districtSelectId2 = "district2"
            var provinceCodeId2 = "q_provinceCode2"
            var cityCodeId2 = "q_cityCode2"
            var districtCodeId2 = "q_districtCode2"	

$(function () {
    InitQuery_item();
//    $("#distpicker").distpicker({
//        autoSelect: false
//    });
//    $("#province").change(function () {
//        $(this).attr("title", $(this).val());
//    });
//    $("#city").change(function () {
//        $(this).attr("title", $(this).val());
//    });
//    $("#district").change(function () {
//        $(this).attr("title", $(this).val());
//        $("#areaCode").val($("#district option:selected").attr("data-code"));
//    });

    
    
    initProvinceSelect(provinceSelectId, citySelectId, districtSelectId,
          provinceCodeId, cityCodeId, districtCodeId)
      initCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId)
      initDistrictSelect(districtSelectId, districtCodeId)
      
      
    
    initProvinceSelect(provinceSelectId2, citySelectId2, districtSelectId2,
          provinceCodeId2, cityCodeId2, districtCodeId2)
      initCitySelect(citySelectId2, districtSelectId2, cityCodeId2, districtCodeId2)
      initDistrictSelect(districtSelectId2, districtCodeId2)


    
    $("#btnAdd_item").click(function () {
        $('#mform_item')[0].reset();
        $('#mform_item').find("#id").val("");
        $("#myModal_item_title").html("添加");
      /*  $("#distpicker2").distpicker({
            autoSelect: true
        });*/
        
        initProvinceSelect(provinceSelectId, citySelectId, districtSelectId,
                provinceCodeId, cityCodeId, districtCodeId)
            initCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId)
            initDistrictSelect(districtSelectId, districtCodeId)
            
        
        $("#myModal_item").modal();
    });


    // modal 新增基本字段事件 关闭事件事件， 清空已有的值 恢复禁用
    $('#myModal_item').on('hide.bs.modal', function (e) {
        if ($(e.target).attr("type")) //日期选择等弹出框
            return;
        $('#mform_item')[0].reset();

        $("#mform_item").data('bootstrapValidator').resetForm();
    });

    $('#myModal_item2').on('hide.bs.modal', function (e) {
        $("#divauditReason").hide();
        $("#divauditReason").val('');
        $("#mform_item2").data('bootstrapValidator').resetForm();
    });

    $("#divauditReason").hide();

    function updateNormalValidate(ele, fieldnameOrEle, messagetext, isremove) {
        if (isremove)
            $(ele).bootstrapValidator("removeField", fieldnameOrEle);
        else {
            $(ele).bootstrapValidator("addField", fieldnameOrEle, {
                validators: {
                    notEmpty: {
                        message: messagetext
                    },
                }
            });
        }
    };

    $("#mform_item2 #auditFlag").change(function () {
        if ($(this).val() == "2") {
            $("#divauditReason").show();
             updateNormalValidate($("#mform_item2"), $("#auditReason"), "审核原因不能为空", false);
            $("#mform_item2").data('bootstrapValidator').resetForm();
            $("#mform_item2").data("bootstrapValidator").validate();
        }
        else {
            $("#divauditReason").hide();
             updateNormalValidate($("#mform_item2"), $("#auditReason"), "审核原因不能为空", true);
        }
    });

    $("#btnSubmit_item_audit").click(function () {
        if ($(this).val() == "2") {
            $("#mform_item2").data('bootstrapValidator').resetForm();
            // var bool2 = bv.isValid();
            $("#mform_item2").data("bootstrapValidator").validate();
        }
        
        $("#areaCode2").val($("#"+provinceCodeId2).val()+$("#"+cityCodeId2).val()+$("#"+districtCodeId2).val());
        
        if($("#areaCode2").val().length!=6)
        	{
        	error("地市区不能为空");
        	return;
        	}
        
        // flag = true/false
        var flag = $("#mform_item2").data("bootstrapValidator").isValid();
        var url = getRPath() + "/manager/tcompany/saveOrUpdate";
        if (flag) {
            cconfirm("确认审核吗?", function () {
                
           	
            	
                var data = $("#mform_item2").serialize();
                
                data+="&areaCode="+$("#areaCode2").val()
                +"&province="+$("#"+provinceSelectId2).find("option:selected").text()
                +"&city="+$("#"+citySelectId2).find("option:selected").text()
               + "&district="+$("#"+districtSelectId2).find("option:selected").text();
                
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
                            $('#myModal_item2').modal("hide");
                            doSearch_item();
                            success("操作成功！");
                        } else {
                            error(response.message);
                        }
                    }
                });
            });
        }
    });


    $("#btnSubmit_item").click(function () {
        $("#mform_item").data('bootstrapValidator').resetForm();
        // var bool2 = bv.isValid();
        $("#mform_item").data("bootstrapValidator").validate();
        // flag = true/false
        var flag = $("#mform_item").data("bootstrapValidator").isValid();
        var url = getRPath() + "/manager/tcompany/saveOrUpdate";
        if (flag) {
        	
        	   $("#areaCode").val($("#"+provinceCodeId).val()+$("#"+cityCodeId).val()+$("#"+districtCodeId).val());
           	
        	  // $("#"+provinceSelectId).val($("#"+provinceSelectId).find("option:selected").text());
        	 //  $("#"+citySelectId).val($("#"+citySelectId).find("option:selected").text());
        	 //  $("#"+districtSelectId).val($("#"+districtSelectId).find("option:selected").text());
            var data = $("#mform_item").serialize();
            data+="&province="+$("#"+provinceSelectId).find("option:selected").text()
            +"&city="+$("#"+citySelectId).find("option:selected").text()
           + "&district="+$("#"+districtSelectId).find("option:selected").text();
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
    initValidate_item2();
});


function initValidate_item() {
    $("#mform_item").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        excluded: [":disabled"], //隐藏的也要验证
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
            companyName: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
                                return false
                            }
                            return true
                        }
                    }
                },
            },
            companyPhone: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    regexp: {
                        regexp: /^1[0-9]{10}$|0\d{2,3}-\d{7,8}$/i,
                        message: '电话为手机号码或者座机电话'
                    }
                }
            },
            district1: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '不能为空',
                        callback: function (value, validator) {
                            if (value == "" || $("#mform_item").find("#city").val() == "" || $("#mform_item").find("#province").val() == "") {
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                }
            },
            companyAddressDetail:{
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '字符长度不能大于30',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 30) {
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

function initValidate_item2() {
    $("#mform_item2").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        //excluded: [":disabled"], //隐藏的也要验证
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
        	 district2: {
                 validators: {
                     notEmpty: {
                         message: '不能为空'
                     },
                     callback: {
                         message: '不能为空',
                         callback: function (value, validator) {
                             if (value == "" || $("#mform_item2").find("#city2").val() == "" || $("#mform_item2").find("#province2").val() == "") {
                                 return false;
                             } else {
                                 return true;
                             }
                         }
                     }
                 }
             },
           /* auditReason: {
                validators: {
                    notEmpty: {
                        message: '审核原因不能为空'
                    },
                    callback: {
                        message: '字符长度不能大于200',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 200) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },*/
        }
    });

}


function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/tcompany/tcompanyUnAuditList', // 请求后台的URL（*）
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
                companyName: $("#q_companyName").val(),
                companyPhone: $("#q_companyPhone").val(),


            };
            return param;
        },
        columns: [{
            field: 'id',
            visible: false
        },
            {
                field: 'companyName',
                title: '名称',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'companyPhone',
                title: '电话',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'companyType',
                title: '类型',
                align: 'center',
                valign: 'middle',
                formatter : function (value) {
                    return value == 0?"合伙人":"代理人"
                }
            },
            {
                field: 'address',
                title: '地区',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    return (row.province ? row.province : '') + " " + (row.city ? row.city : '') + " " + (row.district ? row.district : '');
                }
            },
            {
                field: 'companyAddressDetail',
                title: '详细地址',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'createTime',
                title: '入驻平台时间',
                align: 'center',
                valign: 'middle',
                visible: false,
                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {
                field: 'auditFlag',
                title: '审核状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value == -1)
                        return "未提交审核";
                    else if (value == 0)
                        return "已提交审核";
                    else if (value == 1)
                        return "<span class='pass'> 审核通过</span>";
                    else if (value == 2)
                        return "<span class='nopass'>审核不通过</span>";
                }
            },
            {
                title: '操作',
                field: 'vehicleno',
                align: 'center',
                formatter: modifyAndDeleteButton_item,
                events: PersonnelInformationEvents_item
            }
        ]
    });
}

function modifyAndDeleteButton_item(value, row, index) {
    return ['<div class="">'
    + '<button id = "update" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改</i> </button>&nbsp;'
    + '<button id = "audit" type = "button" class = "btn btn-warning"><i class="glyphicon glyphicon-check">审核</i> </button>&nbsp;'
    + '<button id = "delete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
    + '</div>'].join("");
}

window.PersonnelInformationEvents_item = {
    "click #update": function (e, value, row, index) {
        $.ajax({
            type: "post",
            url: getRPath() + '/manager/tcompany/load',
            data: {
                id: row.id
            },
            async: false,
            dataType: "json",
            success: function (response) {
                $("#mform_item").fill(response);
                //setArea($("#mform_item"), response);
                
                setSelectArea(provinceSelectId,citySelectId,districtSelectId, response);
                
                
                $("#areaCode").val(response.areaCode);
                $("#myModal_item_title").html("编辑");
                $("#myModal_item").modal();
            }
        });

    },
    "click #audit": function (e, value, row, index) {
        $.ajax({
            type: "post",
            url: getRPath() + '/manager/tcompany/load',
            data: {
                id: row.id
            },
            async: false,
            dataType: "json",
            success: function (response) {
                $("#mform_item2").fill(response);
               // setArea($("#mform_item2"), response);
                
                setSelectArea(provinceSelectId2,citySelectId2,districtSelectId2, response);
                
                
                $("#auditFlag").val(1);
                $("#mform_item2 #areaCode").val(response.areaCode);
                $("#mform_item2 #myModal_item_title").html("编辑");
                $("#myModal_item2").modal();
            }
        });

    },

    "click #delete": function (e, value, row, index) {
        var msg = "您真的确定要删除吗？";
        var url = getRPath() + "/manager/tcompany/delete";
        cconfirm(msg, function () {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "id": row.id
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
};

function doSearch_item() {
    var opt = {
        silent: true
    };
    $("#table_list_item").bootstrapTable('refresh', opt);

    //success("test");
}

