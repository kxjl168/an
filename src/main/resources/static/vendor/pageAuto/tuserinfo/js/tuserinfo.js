function test() {


    var url = getRPath() + "/manager/tuserinfo/test";

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

        var provinceSelectId2 = "q_province"
            var citySelectId2 = "q_city"
            var districtSelectId2 = "q_district1"
            var provinceCodeId2 = "q_provinceCode_query"
            var cityCodeId2 = "q_cityCode_query"
            var districtCodeId2 = "q_districtCode_query"	
            	
            	
            	


          	
$(function () {
    InitQuery_item();
    
    //合伙人锁匠，select2赋值
    initLockerSelect("companyId");
    
    initLockerSelect("companyId2");
    
    
   /* $("#distpicker").distpicker({
        autoSelect: false
    });
    $("#province").change(function () {
        $(this).attr("title", $(this).val());
    });
    $("#city").change(function () {
        $(this).attr("title", $(this).val());
    });
    $("#district").change(function () {
        $(this).attr("title", $(this).val());
        $("#areaCode").val($("#district option:selected").attr("data-code"));
    });*/

    //初始化地市区查询下拉框
    
   //查询初始化
    initProvinceSelect(provinceSelectId2, citySelectId2, districtSelectId2,
            provinceCodeId2, cityCodeId2, districtCodeId2,function(){
    	
    	
        $("#q_district2" ).empty();
    	   var selectedCityId = $("#" + citySelectId2 + " option:selected").attr("id")
           initDistrictSelect("q_district2", "q_districtCode2", selectedCityId)
           
    	
    })
    
    
    
    initProvinceSelect(provinceSelectId, citySelectId, districtSelectId,
          provinceCodeId, cityCodeId, districtCodeId,changeJobArea)
     // initCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId,changeJobArea);
     // initDistrictSelect(districtSelectId, districtCodeId)
      
      
    
   // initProvinceSelect(provinceSelectId2, citySelectId2, districtSelectId2,
  ////        provinceCodeId2, cityCodeId2, districtCodeId2)
   //   initCitySelect(citySelectId2, districtSelectId2, cityCodeId2, districtCodeId2,changeJobArea2)
   //   initDistrictSelect(districtSelectId2, districtCodeId2)


    $("#btnModify").click(function(){
    	
    	   $('#myModal_item2').modal("hide");
    	showModifyModel($("#id2").val());
    })
    
  
    $("#btnAdd_item").click(function () {
        $('#idMessage').html("");
        $('#phoneMessage').html("");
    	$("#btnReAudit").hide();
       	$("#userType").removeAttr("disabled");//,"disabled")

       	//合伙人也不能改
       	$("#companyId").removeAttr("disabled");//,"disabled")
       	
        $("#companyId").select2().val(null).trigger("change");
   		$("#companyId").select2("destroy");
   		$("#companyId").html("");
   	    initLockerSelect("companyId");
      
       	
   	 $("#userType").trigger("change");
   	    
    
        $(".jobareadiv").removeClass('hide');
       	
       	
       	
       	

        $('#mform_item')[0].reset();

        $('#mform_item').find("#id").val("");

        $("#myModal_item_title").html("添加");

        $("#passtip").text("若不填密码则设置初始密码123456")

       /* $("#distpicker2").distpicker({
            autoSelect: true
        });*/
        // 强密码验证密码
						$('#mform_item')
								.bootstrapValidator(
										"addField",
										'password',
										{
											validators : {
												/*notEmpty : {
													message : '密码不能为空'
												},*/
												callback : {
													message : '密码需要长度在6-18位',
													callback : testpasswd

												}

											}
										});
        
        
        cleanSelectArea(provinceSelectId,changeJobArea);
        
        
        
        $("#myModal_item").modal("show");
    });


    /**
     * 创建工单界面初始化
     */
    $("#myModal_item").on("show.bs.modal", function () {
        //初始化公司选择
        if ($("#companySelect").length > 0) {
            initCompanySelect("companySelect", "companyId")
        }
    });


    // modal 新增基本字段事件 关闭事件事件， 清空已有的值 恢复禁用
    $('#myModal_item').on('hide.bs.modal', function (e) {

        if ($(e.target).attr("type")) //日期选择等弹出框
            return;

        $('#mform_item')[0].reset();

        $("#mform_item").data('bootstrapValidator').resetForm();

        $("#companySelect").empty()
        $('#mform_item').find("#id").val("");
        $("#companyId").val("")
        $("#areaCode").val("")
    });

    $("#divauditReason").hide();

  

    $("#mform_item2 #auditFlag2").change(function () {
        if ($(this).val() == "2") {
            $("#divauditReason").show();

            updateNormalValidate($("#mform_item2"), $("#auditReason2"), "审核原因不能为空", false);
        }
        else {
            $("#divauditReason").hide();
            updateNormalValidate($("#mform_item2"), $("#auditReason2"), "审核原因不能为空", true);
        }

    });
    
    $("#userType").change(function () {
    	  var bv=$("#mform_item").data('bootstrapValidator')
    	  if(bv)
    		  bv.resetForm();
        if ($(this).val() == "4") {
            $(".companyIddiv").removeClass("hide");
            $(".jobareadiv").addClass("hide");
            
            if(bv)
            updateNormalValidate($("#mform_item"), $("#companyId"), "合伙人不能为空", false);
        }
        else {
        	  $(".companyIddiv").addClass("hide");
        	  $(".jobareadiv").removeClass("hide");
        	 if(bv)
            updateNormalValidate($("#mform_item"), $("#companyId"), "", true);
        }

    });
    
    $("#mform_item_changeUserType").find(".userNewType").change(function () {
  	 
      if ($(this).val() == "4") {
          $("#mform_item_changeUserType").find(".companyIddiv").removeClass("hide");
        
          updateNormalValidate($("#mform_item_changeUserType"), $("#companyId2"), "合伙人不能为空", false);
      }
      else {
    	  $("#mform_item_changeUserType").find(".companyIddiv").addClass("hide");
          updateNormalValidate($("#mform_item_changeUserType"), $("#companyId2"), "合伙人不能为空", true);
      }

  });
    
    
    
    

    $("#btnSubmit_item_audit").click(function () {


        $("#mform_item2").data('bootstrapValidator').resetForm();


        // var bool2 = bv.isValid();
        $("#mform_item2").data("bootstrapValidator").validate();
        // flag = true/false
        var flag = $("#mform_item2").data("bootstrapValidator").isValid();

        var url = getRPath() + "/manager/tuserinfo/updateAudit";

       
		
		var checkenum=$("#treeDemo2").find(".checkbox_true_disable").length;
		
		var userType=$("#userType").val();
		if(userType!=4)  //非合伙人下锁匠，都需要作业区域
		{
		if(checkenum=="")
			{
			error("请先编辑锁匠作业区域后再审批!");
			return false;
			}
		}
		
	
	
   
        
        
        if (flag) {


            cconfirm("确认审核此锁匠吗?", function () {

            
            	
                var data = $("#mform_item2").serialize();
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
    	SubmitOrReAudit(true);
    });
    
    $("#btnReAudit").click(function () {
    	SubmitOrReAudit(false);
    });
    
    $("#btnSubmit_ChangeUserType").click(function () {
    	//btnSubmit_ChangeUserType(false);
    	 $("#mform_item_changeUserType").data('bootstrapValidator').resetForm();
         var imgs = $kfile.get("proImgs").getAllFileIds();
         var annexs = $kfile.get("annexs").getAllFileIds();

       
         if($("#userType2").val()==$("#userOldType").val())
        	 {
        	 error("锁匠类型未做修改!需要选择其他锁匠类型")
        	 return;
        	 }
         
         // var bool2 = bv.isValid();
         $("#mform_item_changeUserType").data("bootstrapValidator").validate();
         // flag = true/false
         var flag = $("#mform_item_changeUserType").data("bootstrapValidator").isValid();

         var url = getRPath() + "/manager/tuserinfo/userTypeChange";

         if (flag) {
        	 
        	 
        	 
             var data = $("#mform_item_changeUserType").serialize();

             data += "&icons=" + imgs;
             data += "&files=" + annexs;
            // console.log(data)

             $.ajax({
                 type: "post",
                 url: url,
                 data: data,
                 async: false,
                 dataType: "json",
                 success: function (response) {
                     // debugger;
                     if (response.bol) {
                         $('#myModal_item_changeUserType').modal("hide");
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
    initValidate_userTypeChange();
    initHeiValidate();
    
    
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
        container: $("#mform_item_changeUserType").find('#proImgs'), // 图片容器
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
        container: $("#mform_item_changeUserType").find('#annexs'), // 附件容器
    });
});



function initValidate_userTypeChange() {
    $("#mform_item_changeUserType").bootstrapValidator({
        feedbackIcons: {
            /* input状态样式通过，刷新，非法三种图片 */
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
        	ProposReason: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
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
        				
        			},
                    
                    
                }
            },
            
           
        
    });
}

function SubmitOrReAudit(isSubmit){
	 $("#mform_item").data('bootstrapValidator').resetForm();
     // var bool2 = bv.isValid();
     $("#mform_item").data("bootstrapValidator").validate();
     // flag = true/false
     var flag = $("#mform_item").data("bootstrapValidator").isValid();
     
     
     var url = getRPath() + "/manager/tuserinfo/saveOrUpdate";
     if(!isSubmit)
    	 url = getRPath() + "/manager/tuserinfo/reDoAudit";
    	 
     $("#areaCode").val($("#"+provinceCodeId).val()+$("#"+cityCodeId).val()+$("#"+districtCodeId).val());
 	
     
     
     var idMessage = $("#idMessage").html();
     var phoneMessage = $("#phoneMessage").html();
     //验证是否存在身份证或手机号
     if(idMessage == '该身份证号已存在!' || phoneMessage == '该电话号码已存在!'){
         flag = false;
     }
     
     
	var checkenum=$("#treeDemo").find(".checkbox_true_full").length;
		
	var userType=$("#userType").val();
	
	if(userType!=4)  //非合伙人下锁匠，都需要作业区域
		{
		if(checkenum=="")
			{
			error("请选择锁匠作业区域!");
			return false;
			}
		}

     
     
     if (flag) {
     	
     	

         
         var zTree = $.fn.zTree.getZTreeObj("treeDemo");
 		var nodes = zTree.getCheckedNodes(true);
 		var districtids="";
 		for (var i = 0, l = nodes.length; i < l; i++) {
 			districtids += nodes[i].id + ",";
 		}
 		
 		var userType=$("#userType").val();
 		
 		if(userType!=4)  //非合伙人下锁匠，都需要作业区域
 			{
 		if(districtids=="")
 			{
 			error("请选择可作业区域!");
 			return;
 			}
 			}
 		
 		
 		$("#userType").removeAttr("disabled");

     	//合伙人也不能改
     	$("#companyId").removeAttr("disabled");
 		
     	var data = $("#mform_item").serialize();
         
         
 	
 		data += "&districtids=" + districtids;
 		
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
}

function InitQuery_item() {
    // 初始化Table
    $('#table_list_item').bootstrapTable({
        //url: getRPath() + '/manager/tuserinfo/tuserAuditinfoList', // 请求后台的URL（*）
        url: getRPath() + '/manager/tuserinfo/tuserinfoList', // 请求后台的URL（*）
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
        showToggle: false,
        // showColumns: true, //是否显示所有的列
        uniqueId: "id", // 每一行的唯一标识，一般为主键列
        // queryParamsType : "limit",
        queryParams: function queryParams(params) { // 设置查询参数
            
        	
        	var provinceId="";
        	var cityId="";
        	var DistrictId="";
        	var districtId2="";
        	
        	var provar=$("#q_province").find("option:selected");
        	if(typeof(provar)!="undefined")
        		provinceId=provar.attr('id');
        	
        	var provarcity=$("#q_city").find("option:selected");
        	if(typeof(provarcity)!="undefined")
        		cityId=provarcity.attr('id');
        	
        	var provardistrict=$("#q_district1").find("option:selected");
        	if(typeof(provardistrict)!="undefined")
        		DistrictId=provardistrict.attr('id');
        	
        	var provardistrict2=$("#q_district2").find("option:selected");
        	if(typeof(provardistrict2)!="undefined")
        		districtId2=provardistrict2.attr('id');
        	
        	
        	
        	
        	var param = {
        		//	parterquery:0,//自由锁匠
        		//userType:1, //普通锁匠
        			dataState:1,//
        		//auditFlag:0,//待审核
        			
        			
                pageSize: params.limit, // 每页要显示的数据条数
                offset: params.offset, // 每页显示数据的开始行号
                sortName: params.sort, // 要排序的字段
                sortOrder: params.order, // 排序规则
                phone: $("#q_phone").val(),
                name: $("#q_name").val(),
                
                idCard:$("#q_idcard").val(),
                des:$("#q_des").val(),
                
                provinceId:provinceId,
                cityId:cityId,
                districtId: DistrictId,
                districtId2:districtId2,
                
                
                
                
            };
        	
        	  param=	getUserQueryParam(params);
	           
	          // param.  createTime= $('#q_createTime').val()
	           
	           return param;
        },
        rowStyle: function (row, index) {
            
        	return userrowStyleFormat(row,index);
            
        }    ,
        rowAttributes:function rowAttributes(row, index) {
        	return {
        	'title':"点击行查看详情",
        	
        	}
        	},
        columns: [{
            field: 'id',
            title: '锁匠ID',
            align: 'center',
            valign: 'middle',
            visible: true,
           /* formatter: function (value, row, index) {
  		      var dtxt = "<span class='usertippy tipurl' data-tippy-content='" + '单击行查看详情' + "' >" + value + "</span>";


          setTimeout(function () {
              tippy(".usertippy", {
                      arrow: true,
                      arrowType: 'round', // or 'sharp' (default)
                      animation: 'perspective',
                  }
              )
          }, 500);

          return dtxt;
  			  }*/
        },
        {
            field: 'name',
            title: '姓名',
            align: 'center',
            valign: 'middle',
        },
       
            {
                field: 'phone',
                title: '手机号',
                align: 'center',
                valign: 'middle',
            },
        
            {
                field: 'idCard',
                title: '身份证号',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'usertype',
                title: '锁匠类型',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                	
                    var utypeName= getUserTypeName(row);
                    
                    
                  var   dtxt="<span class='usertippy' data-tippy-content='"+'单击行查看锁匠详情'+"' >"+utypeName+"</span>";
            		
                	
                	
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
                field: 'companyName',
                title: '合伙人',
                visible: true,
                align: 'center',
                valign: 'middle',
  formatter: function (value, row, index) {
                	
                    return getDisPlayPaterName(row);
                }
            },
            
            {
                field: 'areaName',
                title: '所在地',
                align: 'center',
                visible: false,
                valign: 'middle',
                formatter: function (value, row, index) {
                	
                	
                		
                    return value;
                }
            },
            {
                field: 'districtnames',
                title: '作业区域',
                align: 'center',
                valign: 'middle',
 formatter: function (value, row, index) {
                	
		return getDisPlayJobArea(row);
            
 }		
                
            },
           
            {
                field: 'des',
                title: '备注',
                visible: false,
                align: 'center',
                valign: 'middle',
            },
            
            
            {
                field: 'updateTime',
                title: '上次更新时间',
                align: 'center',
                visible: false,
                valign: 'middle',
                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {
                field: 'updateName',
                title: '上次更新人',
                visible: false,
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'wxOpenId',
                title: '微信状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                	return getWxType(row);
                }
            },
            {
                field: 'dataState',
                title: '状态',
                visible: true,
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                   return getUserDataState(row);
                }
            },
            {
                field: 'auditFlag',
                title: '审核状态',
                visible: false,
                align: 'center',
                valign: 'middle',
                formatter: function (value, row, index) {
                  return getAuditFlag(row);
                }
            },
            {
                title: '操作',
                field: 'vehicleno',
                align: 'center',
                formatter: modifyAndDeleteButton_item,
                events: PersonnelInformationEvents_item
            }
        ],
        detailView: true,
        onExpandRow: function (index, row, $detail) {


        	initDetailLog($detail, row.id);
            //var subTable = $detail.html('<table class="nohide"></table>').find('table');
            //showDetailLog($(subTable),row.orderNo);
        }
    }).on("click-row.bs.table", function (event, row, rowele, field) {
        //  var subTable = $(rowele).html('<table class="nohide"></table>').find('table');


    	showOrCloseDetail(rowele, row, 1)
    });
    
}
/*
function modifyAndDeleteButton_item(value, row, index) {
    return ['<div class="">'
    + '<button id = "update" type = "button" class = "btn btn-info"><i class="glyphicon glyphicon-pencil">修改</i> </button>&nbsp;'
    + '<button id = "audit" type = "button" class = "btn btn-warning"><i class="glyphicon glyphicon-check">审核</i> </button>&nbsp;'
    + '<button id = "delete" type = "button" class = "btn btn-warning"><i class="glyphicon glyphicon-trash">废弃</i> </button>'
    + '<button id = "realdelete" type = "button" class = "btn btn-danger"><i class="glyphicon glyphicon-trash">删除</i> </button>'
    + '</div>'].join("");
}*/



/*
//btnmodify
function showModifyModel(userid){
	 $('#idMessage').html("");
     $('#phoneMessage').html("");
     $.ajax({
         type: "post",
         url: getRPath() + '/manager/tuserinfo/load',
         data: {
             id: userid
         },
         async: false,
         dataType: "json",
         success: function (response) {
             // $("#mform_item").fill(response);
             //setArea($("#mform_item"), response);
          	setSelectArea(provinceSelectId,citySelectId,districtSelectId, response,changeJobArea);
             
             
             $("#mform_item").find("#district").trigger("change");
             //$("#password").val(response.password)
             $("#password").val("")
             $("#id").val(response.id);
             $('#name').val(response.name);
             $('#phone').val(response.phone);
             $('#oldPhone').val(response.phone);
             $('#idCard').val(response.idCard);
             $('#oldIdCard').val(response.idCard);
             $('#companyId').val(response.companyId);
             $('#des').val(response.des);
             setTimeout(function() {
                 $("#companySelect").val(response.companyId);
                 $("#companySelect").selectpicker('refresh');
                 $("#companySelect").trigger("change");
             },100);
             $("#myModal_item_title").html("编辑");
             
             
             
             $('#mform_item').bootstrapValidator('removeField',
				'password');

             $("#passtip").text("若不填则保持原密码不变")
             
		// 验证密码
		$('#mform_item')
				.bootstrapValidator(
						"addField",
						'password',
						{
							validators : {
								
								 * regexp:{ regexp:
								 * /^(\d+)[A-Z]+[a-z]+[~!@#$%^&*_+()?]+$/,
								 * message:'密码需要包含数字、大小写字母、
								 * 以下特殊符号~!@#$%^&*()_?,并且
								 * 长度在6-18位' },
								 
								callback : {
									message : '密码需要包含数字、大小写字母、 以下特殊符号~!@#$%^&*()_?,并且 长度在6-18位',
									callback : testpasswd
								}
							}
						});
             
             
             
             // $("#passtip").text("密码留空则保持原密码不变,填入密码则设置新密码")
             $("#myModal_item").modal();
         }
     });
}

window.PersonnelInformationEvents_item = {
    "click #update": function (e, value, row, index) {
       
    	showModifyModel(row.id);

    },

    "click #audit": function (e, value, row, index) {
        $.ajax({
            type: "post",
            url: getRPath() + '/manager/tuserinfo/load',
            data: {
                id: row.id
            },
            async: false,
            dataType: "json",
            success: function (response) {
                $("#mform_item2").fill(response);
                //setArea($("#mform_item2"), response);
             	//setSelectArea(provinceSelectId2,citySelectId2,districtSelectId2, response);
                
                loadArea(response);
                
                $("#auditFlag2").val(1);
                $("#mform_item2 #myModal_item_title").html("审核");
                $("#myModal_item2").modal();
            }
        });

    },
    "click #reset": function (e, value, row, index) {
        var msg = "您真的确定执行恢复操作吗？";
        var url = getRPath() + "/manager/tuserinfo/ReUse";
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

    }    ,
    "click #delete": function (e, value, row, index) {
        var msg = "您真的确定执行废弃操作吗？";
        var url = getRPath() + "/manager/tuserinfo/NoUsedelete";
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

    }    ,
    "click #realdelete": function (e, value, row, index) {
        var msg = "您真的确定执行删除操作吗？";
        var url = getRPath() + "/manager/tuserinfo/delete";
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

    }    ,
};*/

function doSearch_item() {
    var opt = {
        silent: true,
        pageNumber: 1
    };
    $("#table_list_item").bootstrapTable('refresh', opt);
    //success("test");
}

