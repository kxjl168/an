/**
 * 锁匠/合伙人相关公用js
 */


function getDisPlayJobAreaAll(row){
	var areaname=row.districtnames;
	if(typeof(areaname)=="undefined")
		areaname="";
	
	var p=(row.province == undefined) ? "" : row.province;
	var c=(row.city == undefined) ? "" : row.city;
	
	
	var districtnames=p+" "+c+" "+ areaname;
	var dtxt="";
	return  districtnames;
}

function getDisPlayJobArea(row){
	var areaname=row.districtnames;
	if(typeof(areaname)=="undefined")
		areaname="";
	
	var p=(row.province == undefined) ? "" : row.province;
	var c=(row.city == undefined) ? "" : row.city;
	
	
	var districtnames=p+" "+c+" "+ areaname;
	
	
	var dtxt="";
	

    if(districtnames.length<10)
		dtxt=districtnames;
	else
		dtxt=districtnames.substr(0,10)+"...";
	
	
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


function getUserQueryParam(params){
	
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
        userType:$("#q_userType").val(),
        des:$("#q_des").val(),
        
        
        companyPhone: $("#q_phone2").val(),
        companyName: $("#q_name2").val(),
        
        companyidCard:$("#q_idcard2").val(),
        
        provinceId:provinceId,
        cityId:cityId,
        districtId: DistrictId,
        districtId2:districtId2,
        
        
        
        
    };
	  
	  return param;
}


function getDisPlayPaterName(row){
	var pname=row.companyName;
	var pphone=row.companyPhone;
	
	var	dtxt="";
	if(pname)
		dtxt="<span class='usertippy' data-tippy-content='"+pphone+"'  title='"+pphone+"'>"+pname+"</span>";
		
	
	
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


function getUserDepositTypeName(value){
	//签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6  
	//var value=row.userType;
	if (value == 0)
         return "现结";
     else if (value == 1)
    	  return "周结";
     else if (value == 2)
    	  return "月结";
    
}

function getUserTypeNameByval(value){
	//签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6  
	//var value=row.userType;
	if (value == 1)
         return "签约锁匠";
     else if (value == 2)
    	  return "合伙人";
     else if (value == 3)
    	  return "代理人";
     else if (value == 4)
    	  return "合伙人下锁匠";
     else if (value == 5)
    	  return "自营锁匠";
     else if (value == 6)
    	 return "临时锁匠";
}

function getUserTypeName(row){
	//签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6  
	var value=row.userType;
	if (value == 1)
         return "<span class='text-success'>签约锁匠</span>";
     else if (value == 2)
    	  return "<span class='text-success'>合伙人</span>";
     else if (value == 3)
    	  return "<span class='text-danger'>代理人</span>";
     else if (value == 4)
    	  return "<span class='text-info'>合伙人下锁匠</span>";
     else if (value == 5)
    	  return "<span class='text-success'>自营锁匠</span>";
     else if (value == 6)
    	 return "<span class='text-danger'>临时锁匠</span>";
}


function getWxType(row){
	//签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6  
	var value=row.wxOpenId;
	if(value)
		 return "<div class='text-success'>已绑定</div>";
	else
		return "未绑定";
    	
}



function getUserDataState(row){
	
	var value=row.dataState;
	if (value == -1)
         return "";
     else if (value == 0)
         return "<div class='text-warning'>已停用</div>";
     else if (value == 1)
    	 {
    	 return getAuditFlag(row);
    	 //return "<div class='text-success'>正常</div>"; 
    	 }
     	
     else if (value == 2)
         return "删除";
     else if (value == 3)
     	return "<div class='text-danger'>已拉黑</div>";
}


/**
 * 锁匠列表行样式处理
 * @param row
 * @param index
 * @returns
 */
function userrowStyleFormat(row,index){
	var cls="";
	var style={};
	if(row.dataState==1)
	   {

	
	
	//cls +=" timeout ";
	
	   }
	else 
	{
		cls +=" nouse ";
	}
	
	style={classes:cls};
                    
    return style;
}



function getAuditFlag(row){
	
	var value=row.auditFlag;
	var parterAuditFlag=row.parterAuditFlag;
	
	var usertype=row.userType;
	
	if(usertype!=4)
		{
		//非合伙人下锁匠
	  if (value == -1)
   	   //return "<span class='text-warning'> 未提交审核</span>";
   	   return "<span class='text-warning'> 待审核</span>";
   else if (value == 0)
   	   return "<span class='text-warning'> 待审核</span>";
   else if (value == 1)
       return "<span class='text-success'> 审核通过</span>";
   else if (value == 2)
       return "<span class='text-danger'>审核未通过</span>";
		}
	else{
		
		//合伙人下锁匠
		
		 if (parterAuditFlag == -1)
		   	   //return "<span class='text-warning'> 未提交审核</span>";
		   	   return "<span class='text-warning'> 待合伙人审核</span>";
		   else if (parterAuditFlag == 0)
		   	   return "<span class='text-warning'> 待合伙人审核</span>";
		   else if (parterAuditFlag == 1)
		       return "<span class='text-success'> 审核通过</span>";
		   else if (parterAuditFlag == 2)
		       return "<span class='text-danger'> 合伙人审核未通过</span>";
	}
}



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
            name: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    stringLength : {
                        max : 10,
                    message : '不超过10个字符'
                    }
                }
            },
            idCard: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    regexp: {
                        message: '请输入正确的身份证号',
                        regexp : "^[0-9]{17}[0-9X]$"
                    },
                    /*callback:{
                    	message: '已存在该身份证！',
                    	callback:checkIdCardrepeat
                    }*/
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    callback: {
                        message: '已存在该手机号！'
                    },
                    regexp: {
                        message: '请输入正确的手机号',
                        regexp : "^1(3|4|5|6|7|8|9)\\d{9}$"
                    },
                   /* callback:{
                    	message: '已存在该手机号！',
                    	callback:checkPhonerepeat
                    }*/
                }
            },
            //动态添加验证
         /*   password: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
            callback : {
				message : '密码长度在6-18位',
				callback : testpasswd

			}
                }
            },*/
            district1: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
            tag: {
                validators: {
                    stringLength : {
                        max : 10,
                    message : '不超过10个字符'
                    }
                }
            },
            des: {
                validators: {
                    stringLength : {
                        max : 60,
                    message : '不超过60个字符'
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
        // submitButtons : 'button[type="submit"]',// 提交按钮
        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
            idCard: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
            district2: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    }
                }
            },
            auditReason: {
                validators: {
                    callback: {
                        message: '输入的内容长度须小于255字符',
                        callback: function (value, validator) {
                            if (value.length > 255) {
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


function checkPhonerepeat(value, validator) {
	
	  //message: '请输入正确的手机号',
      
	 validator.updateMessage('phone','callback','');
	
	  if (!/^1(3|4|5|6|7|8|9)\d{9}$/.test(value)) 
{
		  validator.updateMessage('phone','callback','请输入正确的手机号');
          
		  return false;
}

	
	 $.ajax({
         type: "post",
         url: getRPath() + '/manager/tuserinfo/checkPhone',
         data: {
         	id: $("#id").val(),
         	phone:value
         },
         async: false,
         dataType: "json",
         success: function (response) {
         	
         	if(response.bol)
         		{
         		  validator.updateMessage('phone','callback','已存在该手机号码');
         		return false;
         		}
         	else
         		{
         		 validator.updateMessage('phone','callback','');
         		//error(response.message);
         		return true;
         		}
         	
         },
         error:function(){
         	//error("作业区域验证失败!");
         	return false;
         }
         
	 });
}


function checkIdCardrepeat(value, validator) {
	
	 validator.updateMessage('idCard','callback','');
	 
	 
	  if (!/^[0-9]{18}$/.test(value)) 
{
		  validator.updateMessage('idCard','callback','请输入正确的身份证号');
        
		  return false;
}
 
	 $.ajax({
         type: "post",
         url: getRPath() + '/manager/tuserinfo/checkCard',
         data: {
         	id: $("#id").val(),
         	idcard:value
         },
         async: false,
         dataType: "json",
         success: function (response) {
         	
        		if(response.bol)
         		{
         		  validator.updateMessage('idCard','callback','已存在该身份证号码');
         		return false;
         		}
         	else
         		{
         		 validator.updateMessage('idCard','callback','');
         		//error(response.message);
         		return true;
         		}
         	
         },
         error:function(){
         	//error("作业区域验证失败!");
         	return false;
         }
         
	 });
	
	 
//	return true;
}



function testpasswd(value, validator) {
	if (value == null || value == "")
		return true;
	
	if(value!=null &&(value.length<6 || value.length>18))
			return false;
	/*
	 * if (!/^.*\d+.*$/.test(value)) return false; if
	 * (!/^.*[A-Z]+.*$/.test(value)) return false; if
	 * (!/^.*[a-z]+.*$/.test(value)) return false; if
	 * (!/^.*[~!@#$%^&*_+()?]+.*$/.test(value)) return false; if
	 * (!/^[\da-zA-Z~!@#$%^&*_()?]{6,18}$/.test(value)) return false;
	 */
	return true;
}

function updateNormalValidate(ele, fieldnameOrEle, messagetext, isremove) {


	try {
	
    if (isremove)
        $(ele).bootstrapValidator("removeField", fieldnameOrEle);
    else {
        $(ele).bootstrapValidator("addField", fieldnameOrEle, {
            validators: {
                notEmpty: {
                    message: messagetext
                }

            }
        });


    }
	
	} catch (e) {
		// TODO: handle exception
	}
}

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
            $("#mform_item").fill(response);
           //setArea($("#mform_item"), response);
        	setSelectArea(provinceSelectId,citySelectId,districtSelectId, response,changeJobArea);
           
          
        	
          // $("#mform_item").find("#district").trigger("change");
           //$("#password").val(response.password)
           $("#password").val("")
           /*$("#id").val(response.id);
           $('#name').val(response.name);
           $('#phone').val(response.phone);
          
           $('#companyId').val(response.companyId);
           $('#des').val(response.des);*/
           
           $('#oldPhone').val(response.phone);
           $('#idCard').val(response.idCard);
           $('#oldIdCard').val(response.idCard);
           
           
          /* setTimeout(function() {
               $("#companySelect").val(response.companyId);
               $("#companySelect").selectpicker('refresh');
               $("#companySelect").trigger("change");
           },100);*/
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
								/*
								 * regexp:{ regexp:
								 * /^(\d+)[A-Z]+[a-z]+[~!@#$%^&*_+()?]+$/,
								 * message:'密码需要包含数字、大小写字母、
								 * 以下特殊符号~!@#$%^&*()_?,并且
								 * 长度在6-18位' },
								 */
								callback : {
									message : '密码需要包含数字、大小写字母、 以下特殊符号~!@#$%^&*()_?,并且 长度在6-18位',
									callback : testpasswd
								}
							}
						});
           
        
         	//锁匠类型不能修改
          	//$("#userType").trigger("change");
          	
           if ($("#userType").val() == "4") 
        	   {
        	   $(".companyIddiv").removeClass("hide");
        	   $(".jobareadiv").addClass('hide');
        	   }
               
               else
            	   {
            	   $(".companyIddiv").addClass("hide");
            	   $(".jobareadiv").removeClass('hide');
            	   }
            	   
           
           
           $("#companyId").select2().val(null).trigger("change");
   		$("#companyId").select2("destroy");
           //合伙人锁匠，select2赋值
           initLockerSelect("companyId");
           var option = new Option(response.companyName, response.companyId, true, true);
           $("#companyId").append(option).trigger('change');
           $("#companyId").trigger({
               type: 'select2:select',
               params: {
                   data: {text:response.companyName,id:response.companyId}
               }
           });
           
           
     
       	//$("#userType").attr("readonly","readonly");
       	$("#userType").attr("disabled","disabled")

       	//合伙人也不能改
       	$("#companyId").attr("disabled","disabled")
           
               $("#mform_item").data('bootstrapValidator').resetForm();
           
       	if(response.auditFlag==2||response.parterAuditFlag==2){
       		$("#btnReAudit").show();
       	}
       	else
       		$("#btnReAudit").hide();
       	
           
           // $("#passtip").text("密码留空则保持原密码不变,填入密码则设置新密码")
           $("#myModal_item").modal();
       }
   });
}



/**
 * 显示详细图片信息
 * @returns
 */
function showDetailUserImgModal(url) {
    $("#myModal_itemDetailPic2").find(".detailImg").attr("src",url);
    
    $("#myModal_itemDetailPic2").modal("show")
}



$(function(){
	$("body").on("click",".rowimg",function(){
		var url=$(this).attr("src");
		showDetailUserImgModal(url);
	});
			
		
})


function orderStatusTypeName(value) {
    if (value == 3) {
        return "<span class='auditstyle' >待平台确认</span>"
    }
    else if (value == 1) {
        return "<span class='auditstyle'>加钱待平台审核</span>"
    } else if (value == 2) {
        return "<span class='auditstyle' >退单待平台审核</span>"
    } else if (value == 4) {
        return "<span class='auditstyle'>平台申请待锁企审核</span>"
    } else if (value == 5) {
        return "<span class='auditstyle'>锁企不通过待平台处理</span>"
    }
    else if (value == 6) {
    	return "<span class='auditstyle'>加钱待合伙人审核</span>"
    }
    else if (value >= 101 && value <= 199) {
        return "<span class='normalstyle' >待接单</span>"
    }
    else if (value >= 201 && value <= 299) {
    	
    	  if (value ==210) {
    	        return "<span  class='comfirmstyle' >待作业(已确认)</span>"
    	    }
    	  else  if (value ==213) {
  	        return "<span  class='comfirmstyle' >已预约</span>"
  	    }
    	  else if (value ==214) {
  	        return "<span  class='backstyle' >预约失败</span>"
  	    }
    	  else  if (value ==255) {
  	        return "<span  class='backstyle' >待作业(回访问题单)</span>"
  	    }
    	  else
        return "<span  class='normalstyle' >待作业</span>"
    }
    else if (value >= 301 && value <= 399) {
        return "<span  class='normalstyle' >待回访</span>"
    }
    else if (value >= 401 && value <= 499) {
        return "<span  class='normalstyle' >待结账</span>"
    }
    else if (value >= 501 && value <= 599) {
        return "<span  class='donestyle' >已完结</span>"
    }
}

function loadOrderlist(data){
	$('#table_list_order').bootstrapTable({
		//url : getRPath()+'/manager/tuserinfoaudit2/tuserinfoauditList', // 请求后台的URL（*）
		method : 'post', // 请求方式（*）
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toolbar', // 工具按钮用哪个容器
		showHeader : true,
		searchAlign : 'left',
		buttonsAlign : 'left',

		searchOnEnterKey : true,
		striped : true, // 是否显示行间隔色
		cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination : false, // 是否显示分页（*）
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
		data:data,
		columns : [ {
			field : 'id',
			visible : false
		},

            {
                field: 'orderNo',
                title: '订单编号',
                align: 'center',
                valign: 'middle',
            },
            {
                field: 'orderState',
                title: '订单状态',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {

                    return orderStatusTypeName(value);


                }
            },
            {
                field: 'createTime',
                title: '工单创建时间',
                align: 'center',
                valign: 'middle',
                formatter: function (value) {

                    return value.split(".")[0]
                }
            },

		]
	});
}


$(function(){

	$("#btnHeiSubmit").click(function(){
		
		
		var id=$("#heiForm").find(".id").val();
		var type=$("#heiForm").find(".type").val();
		var reason=$("#heiForm").find(".auditReason").val();
		
		var msg = "您确定要拉黑锁匠吗？";
		   var url = getRPath() + "/manager/tuserinfo/NoUsedelete";
		if(type=="3")
		{
			 msg = "您确定要拉黑锁匠吗？";
			  url = getRPath() + "/manager/tuserinfo/NoUsedelete";
		}	
		else if(type=="0")
		{
			 msg = "您确定要废弃吗？";
		     url = getRPath() + "/manager/tuserinfo/NoUsedelete";
		}
		
	
		   $("#heiForm").data("bootstrapValidator").validate();
	        // flag = true/false
	        var flag = $("#heiForm").data("bootstrapValidator").isValid();
	        
	        
	        if(flag)
	        	{
	      cconfirm(msg, function () {
	          $.ajax({
	              type: "post",
	              url: url,
	              data: {
	                  "id": id,
	                  "dataState": type,
	                  "auditReason":reason,
	              },
	              success: function (response) {
	                  if (response.bol) {
	                      success("操作成功！");
	                     $("#heiModal").modal('hide');
	                      doSearch_item();
	                  } else {
	                      error("" + response.message);
	                  }
	              }
	          });
	      });
	        	}
	});
	
	
	

	
	
})

function initHeiValidate(){
 $("#heiForm").bootstrapValidator({
	        feedbackIcons: {
	            /* input状态样式通过，刷新，非法三种图片 */
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        // submitButtons : 'button[type="submit"]',// 提交按钮
	        fields: {
	          
	        	auditReason: {
	            	  validators: {
	            		  notEmpty: {
	                        message: '不能为空'
	            		  	},
	            		  callback: {
	                        message: '输入的内容长度须小于300字符',
	                        //trigger: 'change',
	                        callback: function (value, validator) {
	                        	//msg(1);
	                            if (value.length > 300) {
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
              
              if(response.userType=="4") 
            	  $(".cdiv").show();
              else
            	  $(".cdiv").hide();


              $("#divauditReason").hide();
              $("#auditReason2").val("");
              $("#auditFlag2").val(1);
              $("#mform_item2 #myModal_item_title").html("审核");
              $("#myModal_item2").modal();
          }
      });

  },
  "click #auditType": function (e, value, row, index) {
      $.ajax({
          type: "post",
          url: getRPath() + '/manager/userTypeaudit/load',
          data: {
              id: row.id
          },
          async: false,
          dataType: "json",
          success: function (response) {
              $("#divauditReason").addClass("hide");
              $("#auditReason2").val("");
              if(response.error)
        		  {
        		  error(response.error);
        		  return;
        		  }
        	  
        	  var adata=eval( response.info);
        	  var aorder=eval( response.order);
        	  var alocker=eval( response.locker);
              $("#mform_item_ConfirmUserType").fill(adata);
              //setArea($("#mform_item2"), response);
           	//setSelectArea(provinceSelectId2,citySelectId2,districtSelectId2, response);
              
              
              
             // loadArea(response);
              
              if(adata.userType=="4") 
            	  $(".cdiv").show();
              else
            	  $(".cdiv").hide();
              
              
              
              if (adata.icons && adata.icons != "") {

                  $kfile.get("proImgs").initFile(
                      function () {

                          var imgIdArr = adata.icons.split(",");

                          for (var imgId in imgIdArr) {

                        	  if(imgIdArr[imgId]!='')
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

              if (adata.filenames && adata.filemd5 && adata.filenames != "") {
                  $kfile
                      .get("annexs")
                      .initFile(
                          function () {

                              var annexsArr = adata.filenames.split(",");

                              var annexsIdArr = adata.filemd5.split(",");

                              for (var annexsId in annexsArr) {

                                  $kfile.get("annexs").addFile(
                                      annexsIdArr[annexsId],
                                      $kfile
                                          .get("annexs").options.showurl
                                      + "/"
                                      + annexsIdArr[annexsId], annexsArr[annexsId]);
                                  //第三个参数为文件名称
                              }
                          })
              } else {
                  $kfile.get("annexs").initFile();
              }
              
              
              $("#myModal_item_ConfirmUserType").find("#userNewTypeName").val(getUserTypeNameByval(adata.userNewType));
              $("#myModal_item_ConfirmUserType").find("#userOldTypeName").val(getUserTypeNameByval(adata.userOldType));
              
              
              $("#auditFlag2").val(1);
              
              
              
              if(aorder && aorder.length>0)
            	  {
              //工单
              loadOrderlist(aorder);
              
              $(".orderdv").removeClass("hide")
              
            	  }
              else
            	  $(".orderdv").addClass("hide")
              
              
              
              //锁匠
            	     if(alocker  && alocker.length>0)
            	    	 {
            	    	 $(".lockdv").removeClass('hide');
            	    	 }
            	     else
            	    	 {
            	    	 $(".lockdv").addClass('hide');
            	    	 }
              
              
              
              
              
              
              
              $("#myModal_item_ConfirmUserType #myModal_item_title").html("审核");
              $("#myModal_item_ConfirmUserType").modal();
          }
      });

  },

  "click #updown": function (e, value, row, index) {
	  //变更锁匠类型
      $.ajax({
          type: "post",
          url: getRPath() + '/manager/tuserinfo/load',
          data: {
              id: row.id
          },
          async: false,
          dataType: "json",
          success: function (response) {
              $(".companyIddiv").addClass("hide");

              $("#myModal_item_changeUserType").fill(response);
              //setArea($("#mform_item2"), response);
           	//setSelectArea(provinceSelectId2,citySelectId2,districtSelectId2, response);
              
              $kfile.get("proImgs").initFile();
              $kfile.get("annexs").initFile();
              
              loadArea(response);
              $("#auditReason2").val("");
              
              $("#myModal_item_changeUserType").find("#userOldType").val(response.userType);
              $("#myModal_item_changeUserType").find("#userInfoId").val(response.id);
              $("#myModal_item_changeUserType").find("#userOldTypeName").val(getUserTypeNameByval(response.userType));
              
             // $("#auditFlag2").val(1);
             // $("#mform_item2 #myModal_item_title").html("审核");
              $("#myModal_item_changeUserType").modal();
          }
      });

  },
  
  
  "click #black": function (e, value, row, index) {
	  
	  
	  $("#heiForm").find(".uinfoid").val(row.id);
		$("#heiForm").find(".uinfotype").val(3);
		$("#heiForm").find(".auditReason").val("");
		$("#heiModal").find(".modal-title").html("锁匠拉黑");
		  $("#heiModal").modal('show');
		  return;
	  
	  
      var msg = "您确定要拉黑锁匠吗？";
      var url = getRPath() + "/manager/tuserinfo/NoUsedelete";
      cconfirm(msg, function () {
          $.ajax({
              type: "post",
              url: url,
              data: {
                  "id": row.id,
                  "dataState": 3,
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
	  
	  
	  $("#heiForm").find(".id").val(row.id);
		$("#heiForm").find(".type").val(0);
		
		$("#heiForm").find(".auditReason").val("");
		
		
		 $("#heiModal").find(".modal-title").html("锁匠废弃");
		  $("#heiModal").modal('show');
		 
		  return;
	  
      var msg = "您确定要废弃吗？";
      var url = getRPath() + "/manager/tuserinfo/NoUsedelete";
      cconfirm(msg, function () {
          $.ajax({
              type: "post",
              url: url,
              data: {
                  "id": row.id,
                  "dataState": 0,
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
  
 /* "click #clocker": function (e, value, row, index) {
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

  }    ,*/
  
  "click #ownlocker": function (e, value, row, index) {
      var msg = "您确定要将此锁匠变为自由锁匠吗？";
      var url = getRPath() + "/manager/tuserinfo/changeTofree";
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
      var msg = "您确定执行删除操作吗？";
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
  "click #reset": function (e, value, row, index) {
      var msg = "您确定执行恢复操作吗？";
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
  "click #untying": function (e, value, row, index) {
      var msg = "您确定要解绑锁匠微信吗？";
      var url = getRPath() + "/manager/tuserinfo/untying";
      cconfirm(msg, function () {
          $.ajax({
              type: "post",
              url: url,
              data: {
                  "id": row.id
              },
              success: function (response) {
                  if (response.bol) {
                      success("解绑成功！");
                      doSearch_item();
                  } else {
                      error("" + response.message);
                  }
              }
          });
      });

  }
  };