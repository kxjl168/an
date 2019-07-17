function test() {


    var url = getRPath() + "/manager/tlockproductinfo/test";

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
	
	
	if($("#managerType2").val()=='2')
		$("#ImeiNeedView").addClass('hide');
	else
		$("#ImeiNeedView").removeClass('hide');

    // 多图片上传-
    $kfile.init({
        isimg: true,
        filesufix: 'png,jpg,gif,jpeg,',
        maxFileSize: 2 * 1024 * 1024,// 2M
        mform: '#mform',
        notnull: true,
        notnullmsg: '至少需要一张图片',
        minimgupload: 1,
        maximgupload: 4,// 最多可上传图片数量
        defaultadd: getRPath() + '/img/add.png',// 默认添加图片
        defaultimg: getRPath() + '/img/default.jpg',// 默认空占位图片
        showurl: getRPath() + '/upload/file',// 获取图片的 url
        uploadurl: getRPath() + '/upload/UploadFileXhr',// 上传图片action url
        container: $("body").find('#proImgs'), // 图片容器
    });

    // 多附件上传-
    $kfile.init({
        isimg: false,
        filesufix: 'docx,xls,doc,pdf,xlsx,txt,log',
        maxFileSize: 5 * 1024 * 1024,// 2M
        mform: '#mform',
        notnull: false,
        notnullmsg: '至少需要一份附件',
        minimgupload: 1,
        maximgupload: 4,// 最多可上传附件数量
        defaultadd: getRPath() + '/img/add.png',// 默认添加附件
        defaultimg: getRPath() + '/img/default.jpg',// 默认空占位附件
        showurl: getRPath() + '/upload/file',// 获取附件的 url
        uploadurl: getRPath() + '/upload/UploadFileXhr',// 上传附件action url
        container: $("body").find('#annexs'), // 附件容器
    });

    InitQuery_item();

    $("#btnAdd_item").click(function () {

        $("#proTypeMessage").html("");
        $("#proTypeMessage").hide();

        $kfile.get("proImgs").initFile();
        $kfile.get("annexs").initFile();

        $('#mform')[0].reset();

        $('#mform').find("#id").val("");

        $("#myModal_item_title").html("添加");

        $("#distpicker2").distpicker({
            autoSelect: true
        });

        var managerType = $("#managerType").val();

        if (managerType == 2) {

            $("#companyView").css("display", "none")
        } else {

            $("#lockEnterpriseID").select2().val(null).trigger("change");
            $("#lockEnterpriseID").select2("destroy");
            initManagerLockCompanySelect();
        }

        $("#myModal_item").modal();
    });


    // modal 新增基本字段事件 关闭事件事件， 清空已有的值 恢复禁用
    $('#myModal_item').on('hide.bs.modal', function (e) {

        $("#id").val('');
        $("#name").val('');
        $("#proType").val('');
        $("#proSize").val('');
        $("#proDesc").val('');
        $("#selectAll").val('');
        $("#companyView").val('');
        $("input[name= lockType][value=" + 0 + "]").prop("checked", true);
        if ($(e.target).attr("type")) //日期选择等弹出框
            return;
        $kfile.get("proImgs").resetValidate();
        $kfile.get("annexs").resetValidate();
        $('#mform')[0].reset();

        $("#mform").data('bootstrapValidator').resetForm();
    });


    $("#btnSubmit_item").click(function () {


        $("#mform").data('bootstrapValidator').resetForm();
        var imgs = eval($kfile.get("proImgs").getAllFileIds());
        var annexs = eval($kfile.get("annexs").getAllFileIds());

        var imgIdArr = [];
        for (var i = 0, count = imgs.length; i < count; i++) {

            imgIdArr.push(imgs[i].id);
        }

        var annexsMd5 = [];
        for (var i = 0, count = annexs.length; i < count; i++) {

            if (annexs[i].id.length != 0) {
                annexsMd5.push(annexs[i].id);
            }
        }
        // var bool2 = bv.isValid();
        $("#mform").data("bootstrapValidator").validate();
        // flag = true/false
        var flag = $("#mform").data("bootstrapValidator").isValid();


        var proTypeMessage = $("#proTypeMessage").html();
        if(proTypeMessage == '该产品标识已存在!'){
            flag = false;
        }

        var url = getRPath() + "/manager/tlockproductinfo/saveOrUpdate";

        if (flag) {
            var data = $("#mform").serialize();

            data += "&proImgs=" + imgIdArr.join(",");
            data += "&annexs=" + annexsMd5.join(",");
            console.log(data)

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
    $("#mform").bootstrapValidator({
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
                        message: '名称不能为空'
                    },
                    callback: {
                        message: '名称长度不能大于20',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 20) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            proType: {
                validators: {
                    notEmpty: {
                        message: '产品型号不能为空'
                    },
                    callback: {
                        message: '产品型号长度不能大于20',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 20) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            lockEnterpriseID: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
            proSize: {
                validators: {
                    notEmpty: {
                        message: '产品尺寸不能为空'
                    },
                    callback: {
                        message: '产品尺寸长度不能大于20',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 20) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            proDesc: {
                validators: {
                    notEmpty: {
                        message: '产品说明不能为空'
                    },
                    callback: {
                        message: '产品说明长度不能大于50',
                        trigger: 'change',
                        callback: function (value, validator) {
                            if (value.length > 50) {
                                return false
                            }
                            return true
                        }
                    }
                }
            },
            proImgs: {
                validators: {
                    notEmpty: {
                        message: '产品图片不能为空'
                    }
                }
            },
            proInstallUrl1: {
                validators: {
                    regexp: {
                        /* 只需加此键值对，包含正则表达式，和提示 */
                        regexp: /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/,
                        message: '请输入合法的下载地址'
                    }
                }
            },
            proInstallUrl2: {
                validators: {
                    regexp: {
                        /* 只需加此键值对，包含正则表达式，和提示 */
                        regexp: /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/,
                        message: '请输入合法的下载地址'
                    }
                }
            },
            techPersonName: {
                validators: {

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
            },
            techPersonPhone: {
                validators: {

                    regexp: {
                        regexp: /^1[0-9]{10}$|0\d{2,3}-\d{7,8}$/i,
                        message: '电话为手机号码或者座机电话'
                    }
                }
            }
        }
    });

}


function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        url: getRPath() + '/manager/tlockproductinfo/tlockproductinfoList', // 请求后台的URL（*）
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


                name: $("#q_name").val(),
                proType: $("#q_proType").val(),
                companyName: $("#q_company").val(),

            };
            return param;
        },
        columns: [{
            field: 'id',
            visible: false
        },
            {
                field: 'name',
                title: '产品名称',
                align: 'center',
                valign: 'middle',


            },
            {
                field: 'proType',
                title: '产品型号-标识',
                align: 'center',
                valign: 'middle',


            },
            {
                field: 'lockType',
                title: '锁类型',
                align: 'center',
                valign: 'middle',

                formatter: function (value, row, index) {//0：NB-锁，1：机械锁，2：其他锁

                    if (value == 0) {
                        return "NB-锁";
                    } else if (value == 1) {
                        return "机械锁";
                    } else if (value == 2) {
                        return "其他锁";
                    }else if (value == 3) {
                        return "国脉智联锁";
                    }
                }
            },
            {
                field: 'companyName',
                title: '所属企业',
                align: 'center',
                valign: 'middle',


            },
            {
                field: 'proSize',
                title: '尺寸说明',
                align: 'center',
                valign: 'middle',


            },
            {
                field: 'proDesc',
                title: '产品说明',
                align: 'center',
                valign: 'middle',


            },
            {
                field: 'proImgs',
                title: '产品图片',
                align: 'center',
                valign: 'middle',

                formatter: function (value, row, index) {

                    var htmlStr = '';
                    if (value) {
                        var imgIdArr = value.split(",");

                        for (var imgId in imgIdArr) {

                            if (imgIdArr[imgId] == "") {

                                return htmlStr;
                            }


                            htmlStr = htmlStr + "<img class='rowimg img-responsive'  style=\"float: left;width: 50px;height: 50px\" src='" + $kfile.get("proImgs").options.showurl + "/" + imgIdArr[imgId] + "' />";
                        }
                    }

                    return htmlStr;
                }
            },
            {
                field: 'filenames',
                title: '产品附件',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    var htmlStr = '';
                    if (value) {

                        var filenames = value.split(",");
                        var filemd5 = row.filemd5.split(",");

                        for (var i = 0; i < filemd5.length; i++) {
                        	   htmlStr += "<a  href='" + $kfile.get("proImgs").options.showurl + "/" + filemd5[i] + "' >" + filenames[i] + "</a>,";	
						}
                        
                        $.each(filemd5, function (index, item) {
                         
                        });


                        htmlStr = htmlStr.substr(0, htmlStr.length - 1);
                    }

                    return htmlStr;
                }


            },
            {
                field: 'createTime',
                title: '建立时间',
                align: 'center',
                visible: false,
                valign: 'middle',

                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }


            },
            {
                field: 'dataState',
                title: '状态',
                visible: true,
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    if (value == 0)
                        return "<div class='text-danger'>已停用</div>";
                    else if (value == 1)
                        return "<div class='text-success'>正常</div>";
                }
            },

            {
                title: '操作',
                field: 'xxx',
                align: 'center',
                formatter: modifyAndDeleteButton_item,
                events: PersonnelInformationEvents_item
            }

        ],
        detailView: true,
        onExpandRow: function (index, row, $detail) {

            initDetailLog($detail, row);
        }
    }).on("click-row.bs.table", function (event, row, rowele, field) {

        showOrCloseDetail(rowele, row, 1)
    });
}

function modifyAndDeleteButton_item(value, row, index) {

    var html = '<div class="">'

        + '<button id = "update" type = "button"   data-tippy-content="编辑" class = "tippy btn btn-info"><i class="fa fa-edit"></i> </button>&nbsp;'
        + '<button id = "delete" type = "button"   data-tippy-content="删除" class = "tippy btn btn-danger"><i class="fa fa-trash"></i> </button>&nbsp;';

    if (row.dataState == 1)
        html += '<button id = "drop" type = "button"   data-tippy-content="废弃" class = "tippy btn btn-warning"><i class="fa fa-remove"></i> </button>&nbsp;';

    if (row.dataState == 0)
        html += '<button id = "reset" type = "button"   data-tippy-content="恢复" class = "tippy btn btn-success"><i class="fa fa-refresh"></i> </button>&nbsp;';

    html += '</div>';

    setTimeout(function(){
        tippy(".tippy",{
                arrow: true,
                arrowType: 'round', // or 'sharp' (default)
                animation: 'perspective'
            }
        )
    },500);

    return [html].join("");
}

window.PersonnelInformationEvents_item = {
    "click #update": function (e, value, row, index) {
        $.ajax({
            type: "post",
            url: getRPath() + '/manager/tlockproductinfo/load',
            data: {
                id: row.id
            },
            async: false,
            dataType: "json",
            success: function (response) {

                $("#proTypeMessage").html("");
                $("#proTypeMessage").hide();

                $("#mform").fill(response);

                $("#oldProType").val(response.proType);
                $("#myModal_item_title").html("编辑");

                $("#myModal_item").modal();

                if (response.proImgs && response.proImgs != "") {

                    $kfile.get("proImgs").initFile(
                        function () {

                            var imgIdArr = response.proImgs.split(",");

                            for (var imgId in imgIdArr) {

                                $kfile.get("proImgs").addFile(
                                    imgIdArr[imgId],
                                    $kfile.get("proImgs").options.showurl
                                    + "/"
                                    + imgIdArr[imgId]);
                            }
                        })


                } else {
                    $kfile.get("proImgs").initFile();
                }

                if (response.annexs && response.filenames && response.annexs != "") {
                    $kfile
                        .get("annexs")
                        .initFile(
                            function () {

                                var annexsArr = response.filenames.split(",");

                                var annexsIdArr = response.filemd5.split(",");


                              
                                
                                for (var i = 0; i < annexsIdArr.length; i++) {
                             	 //  htmlStr += "<a  href='" + $kfile.get("proImgs").options.showurl + "/" + filemd5[i] + "' >" + filenames[i] + "</a>,";
                             	   
                             	   $kfile.get("annexs").addFile(
                                           annexsIdArr[i],
                                           $kfile
                                               .get("annexs").options.showurl
                                           + "/"
                                           + annexsIdArr[i], annexsArr[i]);
                             	   
     						   }
                             /*   
                                for (var annexsId in annexsArr) {

                                    $kfile.get("annexs").addFile(
                                        annexsIdArr[annexsId],
                                        $kfile
                                            .get("annexs").options.showurl
                                        + "/"
                                        + annexsIdArr[annexsId], annexsArr[annexsId]);
                                    //第三个参数为文件名称
                                }
                                */
                                
                                
                                
                                
                                
                                
                                
                            })
                } else {
                    $kfile.get("annexs").initFile();
                }

                // $("#lockEnterpriseID").select2().val(null).trigger("change");
                // $("#lockEnterpriseID").select2("destroy");
                // initManagerLockCompanySelect();
                var managerType = $("#managerType").val();
                if (managerType == 2) {

                    $("#companyView").css("display", "none")
                } else {

                    $("#companyView").css("display", "block");
                    $("#companyLabel").html("所属锁企");
                    $("#lockEnterpriseID").select2().val(null).trigger("change");
                    $("#lockEnterpriseID").select2("destroy");
                    $("#lockEnterpriseID").html("");
                    initManagerLockCompanySelect();

                    var option = new Option(response.companyName, response.lockEnterpriseID, true, true);
                    $("#lockEnterpriseID").append(option).trigger('change');
                    $("#lockEnterpriseID").trigger({
                        type: 'select2:select',
                        params: {
                            data: {text: response.companyName, id: response.lockEnterpriseID}
                        }
                    });
                }

                $("#myModal").modal();
            }
        });

    },

    "click #delete": function (e, value, row, index) {
        var msg = "您真的确定要删除吗？";
        var url = getRPath() + "/manager/tlockproductinfo/delete";
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

    },
    "click #drop": function (e, value, row, index) {
        var msg = "您真的确定要废弃吗？";
        var url = getRPath() + "/manager/tlockproductinfo/drop";
        cconfirm(msg, function () {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "id": row.id
                },
                success: function (response) {
                    if (response.bol) {
                        success("废弃成功！");
                        doSearch_item();
                    } else {
                        error("" + response.message);
                    }
                }
            });
        });

    },
    "click #reset": function (e, value, row, index) {
        var msg = "您真的确定要恢复吗？";
        var url = getRPath() + "/manager/tlockproductinfo/reset";
        cconfirm(msg, function () {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "id": row.id
                },
                success: function (response) {
                    if (response.bol) {
                        success("恢复成功！");
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
        url: getRPath() + "/manager/tlockproductinfo/tlockproductinfoList",
    };
    $("#table_list_item").bootstrapTable('refresh', opt);

    //success("test");
}

