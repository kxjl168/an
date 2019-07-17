function test(){
	
	
	var url = getRPath()+"/manager/userTypeaudit/test";
	
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
	InitQuery_item();
	
	// 多图片上传-
    $kfile.init({
        isimg: true,
        filesufix: 'png,jpg,gif,jpeg,',
        maxFileSize: 2 * 1024 * 1024,// 2M
        mform: '#mform_item_changeUserType',
        notnull: true,
        notnullmsg: '至少需要一张图片',
        minimgupload: 1,
        maximgupload: 2,// 最多可上传图片数量
        defaultadd: getRPath() + '/img/add.png',// 默认添加图片
        defaultimg: getRPath() + '/img/default.jpg',// 默认空占位图片
        showurl: getRPath() + '/upload/file',// 获取图片的 url
        uploadurl: getRPath() + '/upload/UploadFileXhr',// 上传图片action url
        container: $("#mform_item_ConfirmUserType").find('#proImgs'), // 图片容器
        readonly:true,
    });

    // 多附件上传-
    $kfile.init({
        isimg: false,
        filesufix: 'docx,xls,doc,pdf,xlsx,txt,log',
        maxFileSize: 5 * 1024 * 1024,// 2M
        mform: '#mform_item_changeUserType',
        notnull: false,
        notnullmsg: '至少需要一份附件',
        minimgupload: 1,
        maximgupload: 2,// 最多可上传附件数量
        defaultadd: getRPath() + '/img/add.png',// 默认添加附件
        defaultimg: getRPath() + '/img/default.jpg',// 默认空占位附件
        showurl: getRPath() + '/upload/file',// 获取附件的 url
        uploadurl: getRPath() + '/upload/UploadFileXhr',// 上传附件action url
        container: $("#mform_item_ConfirmUserType").find('#annexs'), // 附件容器
        readonly:true,
    });


    $("#mform_item_ConfirmUserType").find("#auditFlag2").change(function () {
        if ($(this).val() == "2") {
            $("#divauditReason").removeClass("hide");

            updateNormalValidate($("#mform_item_ConfirmUserType"), $("#auditReason2"), "审核原因不能为空", false);
        }
        else {
            $("#divauditReason").addClass("hide");
            updateNormalValidate($("#mform_item_ConfirmUserType"), $("#auditReason2"), "审核原因不能为空", true);
        }

    });
    
    $("#btnSubmit_ConfirmUserType").click(function () {


        $("#mform_item_ConfirmUserType").data('bootstrapValidator').resetForm();


        // var bool2 = bv.isValid();
        $("#mform_item_ConfirmUserType").data("bootstrapValidator").validate();
        // flag = true/false
        var flag = $("#mform_item_ConfirmUserType").data("bootstrapValidator").isValid();

        var url = getRPath() + "/manager/userTypeaudit/userTypeChangeDone";

       
		
		
        
        if (flag) {
        	
        	var msg="确认审核此锁匠吗?";
        	if(!$('.lockdv').hasClass('hide'))
        		{
        		//
        		error( "此合伙人名下存在锁匠,类型暂时无法变更!");
        		return;
        		}
        	
        	if(!$('.orderdv').hasClass('hide'))
    		{
    		//
        		error( "此锁匠名下存在未完成工单,暂无法审批!");
        		return;
    		}


            cconfirm(msg, function () {

            
            	
                var data = $("#mform_item_ConfirmUserType").serialize();
              //  data+="&id="+$("#id2").val();
               
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
                            $('#myModal_item_ConfirmUserType').modal("hide");
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

	$("#btnAdd_item").click(function() {

	
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


	$("#btnSubmit_item").click(function() {
		
		
		
		$("#mform_item").data('bootstrapValidator').resetForm();
		
		
		 
		    // var bool2 = bv.isValid();
		$("#mform_item").data("bootstrapValidator").validate();
		// flag = true/false
		var flag = $("#mform_item").data("bootstrapValidator").isValid();

		var url = getRPath()+"/manager/userTypeaudit/saveOrUpdate";

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

	initValidate_item();
	

	initValidate_confirm();


});


function initValidate_confirm() {
	$("#mform_item_ConfirmUserType").bootstrapValidator({
		feedbackIcons : {
			/* input状态样式通过，刷新，非法三种图片 */
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		// submitButtons : 'button[type="submit"]',// 提交按钮
		fields : {

				/*name : {
				validators : {
					notEmpty : {
						message : '不能为空'
					}
				}
			},
			*/
            auditFailReason: {
                validators: {
                    callback: {
                        message: '输入的内容长度须小于200字符',
                        callback: function (value, validator) {
                            if (value.length > 200) {
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
			proposReason : {
				validators : {
					  callback: {
	                        message: '输入的内容长度须小于300字符',
	                        //trigger: 'change',
	                        callback: function (value, validator) {
	                        	//msg(1);
	                            if (value.length >300) {
	                                return false
	                            }
	                            return true
	                        }
	                    }
				}
			},
		

		}
		

	
	});

}



function InitQuery_item() {
	// 初始化Table
	$('#table_list_item').bootstrapTable({
		url : getRPath()+'/manager/userTypeaudit/tuserinfoauditList', // 请求后台的URL（*）
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
				
				
				
				userInfoName : $("#q_name").val(),
				userPhone : $("#q_phone").val(),
				auditStates:$("#q_auditStates").val(),
				
			};
			return param;
		},
		columns : [ {
			field : 'id',
			visible : false
		},
		 {
				field : 'userInfoName',
				title : '锁匠',
				align : 'center',
				valign : 'middle',
				 formatter: function (value, row, index) {
	                	
	                    return value+"("+row.userPhone+")";
	                }     
				
			},
			 {
				field : 'userOldType',
				title : '原类型',
				align : 'center',
				valign : 'middle',
				  formatter: function (value, row, index) {
	                	
	                    var typename= getUserTypeNameByval(value);
	                    if(row.userOldType==4)
	                    	typename+="("+row.companyName+")";
	                    
	                    return typename;
	                }  
				
			},
			 {
				field : 'userNewType',
				title : '变更类型',
				align : 'center',
				valign : 'middle',
				  formatter: function (value, row, index) {
	                	
	                    var typename= getUserTypeNameByval(value);
	                    if(row.userNewType==4)
	                    	typename+="("+row.companyName+")";
	                    
	                    return typename;
	                }
				
			},
		 {
				field : 'proposerName',
				title : '变更申请人',
				align : 'center',
				valign : 'middle',
				
				
			},
		 {
				field : 'proposTime',
				title : '申请时间',
				align : 'center',
				valign : 'middle',
				   
				 formatter: function (value) {

	                    return value.split(".")[0]
	                }
				
			},
			
		
		 {
				field : 'proposReason',
				title : '申请原因',
				align : 'center',
				valign : 'middle',
				visible : true ,
				   
				 formatter: function (value, row, index) {
		            var dtxt="";
					 if(value && value.length<15)
							dtxt=value;
						else
							dtxt=value.substr(0,15)+"...";
						
					dtxt="<span class='usertippy' data-tippy-content='"+value+"'  title='"+value+"'>"+dtxt+"</span>";
							
						
						
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
                field: 'icons',
                title: '图片说明',
                align: 'center',
                valign: 'middle',

                formatter: function (value, row, index) {

                    var htmlStr = '';
                    if (value) {
                        var imgIdArr = value.split(",");
                        var url=getRPath() + '/upload/file';
                        for (var imgId in imgIdArr) {

                            if (imgIdArr[imgId] == "") {

                                return htmlStr;
                            }


                            htmlStr = htmlStr + "<img  data-tippy-content='点击查看大图' class='rowimg img-responsive'   src='" + url + "/" + imgIdArr[imgId] + "' />";
                        }
                    }
                    

                	setTimeout(function(){
        				tippy(".rowimg",{
        						 arrow: true,
        						  arrowType: 'round', // or 'sharp' (default)
        						  animation: 'perspective',
        				}
                				)
        			},500);

                    return htmlStr;
                }
            },
            {
                field: 'filenames',
                title: '附件',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                    var htmlStr = '';
                    if (value) {

                        var filenames = row.filenames.split(",");
                        var filemd5 = row.filemd5.split(",");

                        var url=getRPath() + '/upload/file';
                        $.each(filemd5, function (index, item) {
                            htmlStr += "<a  href='" + url+ "/" + item + "' >" + filenames[index] + "</a>,";
                        });

                        
                        htmlStr=htmlStr.substr(0,htmlStr.length-1);
                    }

                    return htmlStr;
                }


            },
			
       	 {
				field : 'auditStates',
				title : '审核状态',
				align : 'center',
				valign : 'middle',
				  formatter: function (value, row, index) {
					  if(value=="0")
						  
						  return "<span class='text-warning'>待审核</span>";
					  else if(value=="1")
						  return "<span class='text-success'>审核通过</span>";
					  else if(value=="2")
						  return "<span class='text-danger'>审核不通过</span>";
				  }
				
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


window.PersonnelInformationEvents_item = {
	"click #update" : function(e, value, row, index) {
		$.ajax({
			type : "post",
			url :getRPath()+ '/manager/userTypeaudit/load',
			data : {
				id : row.id
			},
			async : false,
			dataType : "json",
			success : function(response) {

			   $("#mform_item").fill(response);
			     
	
			   
			   $("#myModal_item_title").html("编辑");
			   
				$("#myModal_item").modal();
				
			
			}
		});

	},

	"click #delete" : function(e, value, row, index) {
		var msg = "您真的确定要删除吗？";
		var url = getRPath()+"/manager/userTypeaudit/delete";
		cconfirm(msg,function() {
			$.ajax({
				type : "post",
				url : url,
				data : {
					"id" : row.id
				},
				success : function(response) {
					if (response.bol) {
						success("删除成功！");
						doSearch_item();
					} else {
						error(""+response.message);
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

