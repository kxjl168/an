function test(){
	
	
	var url = getRPath()+"/manager/tuserinfo/test";
	
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
var provinceSelectId2 = "q_province"
    var citySelectId2 = "q_city"
    var districtSelectId2 = "q_district1"
    var provinceCodeId2 = "q_provinceCode_query"
    var cityCodeId2 = "q_cityCode_query"
    var districtCodeId2 = "q_districtCode_query"	
    	
    	
    	
$(function() {
	
	 var uid=GetQueryString("uid");
	   
	    if(uid!=null)
	    	{
	   
	    $("#q_id").val(uid);
	    
	    /*setTimeout(function(){
	    	doSearch_item();
	    }, 260);
	    */
	    	}
	
	
	InitQuery_item();

	//InitQuery_item1();

   
	
	
	   //查询初始化
    initProvinceSelect(provinceSelectId2, citySelectId2, districtSelectId2,
            provinceCodeId2, cityCodeId2, districtCodeId2,function(){
    	
    	
        $("#q_district2" ).empty();
    	   var selectedCityId = $("#" + citySelectId2 + " option:selected").attr("id")
           initDistrictSelect("q_district2", "q_districtCode2", selectedCityId)
           
    	
    })
	
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

}



function InitQuery_item() {
	// 初始化Table
	$('#table_list_item').bootstrapTable({
		url : getRPath()+'/manager/tuserinfoMoney/moneyList', // 请求后台的URL（*）
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


                phone : $("#q_phone").val(),
                name : $("#q_name").val(),
				
				
			};
			  param=	getUserQueryParam(params);
			  param.id=$('#q_id').val()
	          // param.  createTime= $('#q_createTime').val()
	           
	       //    return param;
			return param;
		},
		columns : [ 
			
			 {
		            field : 'name',
		            title : '锁匠',
		            align : 'center',
		            valign : 'middle',
		            formatter: function (value, row, index) {
		                return value+"("+row.userinfoId+")";
		            }
		        },
			
			{
            field : 'operateTime',
            title : '时间',
            align : 'center',
            valign : 'middle',
            formatter: function (value, row, index) {
                
            	if(value)
        		{
        		return datePlus(value,2).Format("yyyy-MM-dd hh:mm:ss") 	;//split(".")[0];//
        		//Format("yyyy-MM-dd hh:mm:ss") 	
        		
        		//return value.split(".")[0]	
        		}
            
        	else
        		return "";
            }
        },
      
            {
                field : 'changeValue',
                title : '变动额度',
                align : 'center',
                valign : 'middle',
                formatter: function (value, row, index) { 
    				if(value>0)
    					return "<span class='text-success'>+"+value+"</span>";
    				else
    					return "<span class='text-danger'>"+value+"</span>";
                }
        

            },
           
            
            {
                field : 'totalPrice',
                title : '账号总余额',
                align : 'center',
                valign : 'middle',


            },

            {
                field : 'reasonType',
                title : '类型',
                align : 'center',
                valign : 'middle',
                formatter: function (value, row, index) {
                    if (value==1){
                        return "提现"
                    }else if(value==2){
                        return "完成工单费用"
                    }
                    else if(value==3){
                        return "审核工单改价"
                    }
                }

            },
            {
                field : 'effectiveId',
                title : '工单ID/提现流水',
                align : 'center',
                valign : 'middle',
                visible : false
            },
            {
                field : 'effectiveId',
                title : '关联工单号',
                align : 'center',
                valign : 'middle',
                visible : true,
                formatter: function (value, row, index) {
                    return value;
                	
                	if (row.reasonType==1){
                        return "提现流水:"+value;
                    }else if(row.reasonType==2){
                        return "工单号:"+value;
                    }
                    else if(row.reasonType==3){
                        return "关联提现流水:"+value;
                    }
                }

            },
            
            {
                field : 'reasonDes',
                title : '备注',
                align : 'center',
                valign : 'middle',
                visible : true
            },
         
		


		]
	});
}


function InitQuery_item1() {
    $('#table_list_item1').bootstrapTable({
        url : getRPath()+'/manager/tuserinfoMoney/moneyList', // 请求后台的URL（*）
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



                id : $("#myModal_item").find('#q_id').val()

            };
            
            
          //  param=	getUserQueryParam(params);
	           
	          // param.  createTime= $('#q_createTime').val()
	           
	        //   return param;
            
            return param;
        },
        columns : [ {
            field : 'id',
            visible : false
        },
       

      
        {
            field : 'operateTime',
            title : '时间',
            align : 'center',
            valign : 'middle',
            formatter: function (value, row, index) {
                return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
            }
        },
      
            {
                field : 'changeValue',
                title : '变动额度',
                align : 'center',
                valign : 'middle',
                formatter: function (value, row, index) { 
    				if(value>0)
    					return "<span class='text-success'>+"+value+"</span>";
    				else
    					return "<span class='text-danger'>"+value+"</span>";
                }
        

            },
           
            
            {
                field : 'totalPrice',
                title : '账号总余额',
                align : 'center',
                valign : 'middle',


            },

            {
                field : 'reasonType',
                title : '类型',
                align : 'center',
                valign : 'middle',
                formatter: function (value, row, index) {
                    if (value==1){
                        return "提现"
                    }else if(value==2){
                        return "完成工单费用"
                    }
                    else if(value==3){
                        return "审核工单改价"
                    }
                }

            },
            {
                field : 'effectiveId',
                title : '工单ID/提现流水',
                align : 'center',
                valign : 'middle',
                visible : false
            },
            {
                field : 'effectiveId',
                title : '流水号',
                align : 'center',
                valign : 'middle',
                visible : true,
                formatter: function (value, row, index) {
                    if (row.reasonType==1){
                        return "提现流水:"+value;
                    }else if(row.reasonType==2){
                        return "工单号:"+value;
                    }
                    else if(row.reasonType==3){
                        return "关联提现流水:"+value;
                    }
                }

            },
            
            {
                field : 'reasonDes',
                title : '备注',
                align : 'center',
                valign : 'middle',
                visible : true
            },
         
          
        ]
    });

}


window.PersonnelInformationEvents_item2 = {
		"click #more" : function(e, value, row, index) {
			$("#myModal_item").find('#q_id').val(row.id);
	        
	        refresh_item();
	        $("#myModal_item").modal();
	    },
	    "click #more2" : function(e, value, row, index) {
	 
	    		 var uid=row.id;
	        var uname=encodeURIComponent(row.name);
	       	
	    	var url='/manager/userDeposit/index?uid='+uid+"&uname="+uname;
	    		 
	        window.open(getRPath()+url);
	    },
	    
	   

	};

function refresh_item() {

    var opt = {
        silent : true
    };
    $("#table_list_item1").bootstrapTable('refresh', opt);

}
function doSearch_item() {
	
	
	
	var opt = {
		silent : true,
        pageNumber:1
	};
	$("#table_list_item").bootstrapTable('refresh', opt);
	
	//success("test");
}

