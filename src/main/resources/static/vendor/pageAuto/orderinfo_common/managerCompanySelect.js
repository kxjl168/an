//

   $("#btnrefreshOrderTimer").click(function () {

	   var url = getRPath() + "/manager/torderinfo_todo/refreshOrderTimer";
	   $.ajax({
           type: "post",
           url: url,
           data: {
              
           },
           success: function (response) {
               if (response.bol) {
                   success("已开始后台刷新工单,请稍后！");
                   doSearch_item();
               } else {
                   error("" + response.message);
               }
           }
       });
   });

 /**
     * 提交分配
     */
    $("#allocateSubmit").click(function () {
        //校验下拉框是否选择
    	
    	
    	  $("#allocateOrderForm").data('bootstrapValidator').resetForm();
        $("#allocateOrderForm").data("bootstrapValidator").validate();
        var flag = $("#allocateOrderForm").data("bootstrapValidator").isValid();

        if(!flag)
        	return;

    	
    	/*if ($("#lockerSelect").val() == "" || $("#lockerSelect").val() == null) {
            error("请选择一个锁匠");
            return;
        }*/
        
        if($("#allocateOrderForm").find("#addMoney").val()!=null&&$("#allocateOrderForm").find("#addMoney").val()!="")
        {
        
        	if($("#allocateOrderForm").find("#addMoney").val().length>5)
            {
        		 $("#allocateOrderForm").data('bootstrapValidator').updateStatus('addMoney', 'INVALID').validateField('addMoney');
        		 $("#allocateOrderForm").find("small[data-bv-for='addMoney']").html("价格过长");
        		 
            	//error("价格过长");
                return;
            }
        	
        if(!/^-?\d+$/.test($("#allocateOrderForm").find("#addMoney").val()))
        {
        	 $("#allocateOrderForm").data('bootstrapValidator').updateStatus('addMoney', 'INVALID').validateField('addMoney');
    		 $("#allocateOrderForm").find("small[data-bv-for='addMoney']").html("请输入一个合法价格，增加或者减少工单价格");
    		 
        	//error("请输入一个合法价格，增加或者减少工单价格");
            return;
        }
        else{
        	if($("#allocateOrderForm").find("#addMoneyDesc").val()==null||$("#allocateOrderForm").find("#addMoneyDesc").val()=="")
        		{
        		 $("#allocateOrderForm").data('bootstrapValidator').updateStatus('addMoneyDesc', 'INVALID').validateField('addMoneyDesc');
        		 $("#allocateOrderForm").find("small[data-bv-for='addMoneyDesc']").html("请输入价格说明原因");
        		 
        		//error("请输入价格说明原因");
                return;
        		}
        	
        }
    }
        if($("#allocateOrderForm").find("#addMoneyDesc").val().length>100)
		{
		//error("价格说明原因太长为"+$("#allocateOrderForm").find("#addMoneyDesc").val().length+",请输入少于100字说明");
		
		 $("#allocateOrderForm").data('bootstrapValidator').updateStatus('addMoneyDesc', 'INVALID').validateField('addMoneyDesc');
		 $("#allocateOrderForm").find("small[data-bv-for='addMoneyDesc']").html("价格说明原因太长,请输入少于100字说明");
		 
        return;
		}
        
        $("#lockerId").val($("#lockerSelect").val());

        if($("#allocateOrderForm").find("#addMoney").val()!=null&&$("#allocateOrderForm").find("#addMoney").val()!="")
        {
        cconfirm("确定分配工单并加价?",function(){

        	allocate();
        })
        }
        else{
        	 cconfirm("确定分配工单?",function(){
        		 allocate();
             })
        }
        
        
        function allocate(){
        	  var data = $("#allocateOrderForm").serializeArray();
              var url = getRPath() + "/manager/torderinfo_todo/allocateOrder";
              var callback = function (data) {
                  if (data.errCode == 1) {
                      $('#allocateOrderModal').modal("hide");
                      doSearch_item();
                      success(data.errMsg);
                  } else {
                      error(data.errMsg);
                  }
              }
              sendRequest(url, data, callback)
        }
        
    });
    
    


    /**
     * 工单分配初始化
     * @returns
     */
    function InitQuery_money(){
    	
    	
    
    		 $("#allocateOrderForm").bootstrapValidator({
    		        feedbackIcons: {
    		            /* input状态样式通过，刷新，非法三种图片 */
    		            valid: 'glyphicon glyphicon-ok',
    		            invalid: 'glyphicon glyphicon-remove',
    		            validating: 'glyphicon glyphicon-refresh'
    		        },
    		        // submitButtons : 'button[type="submit"]',// 提交按钮
    		        fields: {
    		          
    		            lockerSelect: {
    		            	  validators: {
    		        		 notEmpty: {
    		                        message: '不能为空'
    		                    },
    		                  
    		            	  }
    		            },
    		            addMoney:{
    		            	 validators: {
    		            		 callback: {
     		                        message: '',
     		                       // trigger: 'change',
     		                        callback: function (value, validator) {
     		                           
     		                        	return true;
     		                        	
     		                        }
     		                    }
    		            	 }
    		            },
    		            addMoneyDesc:{
   		            	 validators: {
   		            		 callback: {
    		                        message: '',
    		                       // trigger: 'change',
    		                        callback: function (value, validator) {
    		                           
    		                        	return true;
    		                        	
    		                        }
    		                    }
   		            	 }
   		            },
    		            
    		            
    		         /*   money: {
    		            	  validators: {
    		        		 notEmpty: {
    		                        message: '不能为空'
    		                    },
    		                    callback: {
    		                        message: '只能输入数字',
    		                       // trigger: 'change',
    		                        callback: function (value, validator) {
    		                           
    		                        	return /^[0-9]+.?[0-9]*$/.test(value);
    		                        	
    		                        }
    		                    }
    		            },
    		            }*/
    		        }
    		    });
    		
    	
    	
    	
    	// 初始化Table
    	$('#table_list_money').bootstrapTable({
    		url : getRPath()+'/manager/torderinfo_pay/torderinfoMoneyList', // 请求后台的URL（*）
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
    		queryParams : function queryParams(params) { // 设置查询参数
    			var param = {
    				pageSize : params.limit, // 每页要显示的数据条数
    				offset : params.offset, // 每页显示数据的开始行号
    				sortName : params.sort, // 要排序的字段
    				sortOrder : params.order, // 排序规则
    				
    				
    				
    				orderinfoId :   $("#allocateOrderModal").find("#orderInfoId").val(),
                   
    				
    			};
    			return param;
    		},
    		columns : [ {
    			field : 'id',
    			visible : false
    		},
    		 {
    			field : 'operateTime',
    			title : '时间',
    			visible : false,
    			align : 'center',
    			valign : 'middle',
    			   
    	 formatter: function (value, row, index) {
             return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
         }
    			
    		},
    		 {
    			field : 'reasonDes',
    			title : '说明',
    			align : 'center',
    			valign : 'middle',
    			   
    			
    		},
    	
    	 {
    			field : 'changeValueLocker',
    			title : '金额变动',
    			align : 'center',
    			valign : 'middle',
    			   
    			
    		},
    	 
    		

    		],

    	    });
    }
    
    

//选择锁匠
function initLockerOrParterSelect(orderid,areaname) {

	$("#allocateOrderForm").find("#lockerSelect").select2({
		// dropdownParent : $("#myModal"),
		placeholder : "选择锁匠/合伙人",
		minimumInputLength : 0,
		// maximumSelectionLength: 1,
		theme : "bootstrap",
		allowClear : true,
		multiple : false,
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
				return ""
			},
			maximumSelected : function(e) {
				var t = "";// "最多只能选择" + e.maximum + "个";
				return t
			},
			noResults : function() {
				return "未搜索到符合作业区域的锁匠"
			},
			searching : function() {
				return "搜索中…"
			}

		},

		ajax : {
			type : "post",
			url : getRPath() + "/manager/tuserinfo/selectLockerByOrderIdNew",
			dataType : "json",
			data : function(params) {

				var page = params.page || 1;

				var query = {
						lockName : params.term,
					pageNum : page,
					dataState:1,
					pageSize : 10,
					id : orderid,

				}
				// (cids == null || cids.length == 0) ? "": cids[0]
				// Query parameters will be ?search=[term]&type=public
				return query;
			},

			processResults : function(data, params) {
				var selectdatas = [];
				/*
				 * if(typeof(params.page)=="undefined") selectdatas.push({ id :
				 * -1, text : '新建属性' });
				 */

				/*
				 * selectdatas.push({ id : '0', text : "系统" });
				 */

				$.each(data.rows, function(index, item) {
					// 签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6   ',
					var type="";
					if(item.userType=='1')
						type="签约锁匠";
					else if(item.userType=='2')
						type="合伙人";
					else  if(item.userType=='3')
						type="代理人";
					else  if(item.userType=='5')
						type="自营锁匠";
					else  if(item.userType=='6')
						type="临时锁匠";
					
					selectdatas.push({
						id : item.id,
						text : item.name+"["+type+"]",
						phone:item.phone
					});
				});

				return {
					results : selectdatas,
					pagination : {
						more : (params.page * 10 >= data.total) ? false : true
					}
				};
			},
		},

		templateResult : formatRepo,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		templateSelection : function(data, container) {
			
			return data.text;
			
			var id = data.phone;

			var html = "" + data.text;
			if (id)
				html = "" + data.text + "&nbsp;&nbsp;(" + id + ") ";

			// $(container).parents("tr").find(".attr_id").val(data.id);
			return html;
		},

	});

	// Bind an event
	$('#q_room_community').on('select2:select', function(e) {
		// alert("11");
		// $("#q_room_building").show();
		// destoryRoomQ_SelectBuildingquery();
		// initRoomQ_SelectBuildingquery();
	});

	$('#q_room_community').on('select2:unselect', function(e) {
		// alert("11");
		// destoryRoomQ_SelectBuildingquery();
		// initRoomQ_SelectBuildingquery();

	});

}

function formatRepo(repo) {
	if (repo.loading) {
		return repo.text;
	}
	
	

	var markup = " <div class=\"lock-item in-line\"> "
			+ "<div class=\"lockname col-xs-6\">" + repo.text + " </div> "
			+ "  <div class=\"lockid col-xs-6\">(" + repo.phone + ")</div>"

	"</div> ";

	return markup;
}

function destoryRoomQ_SelectBuildingquery() {
	// $("#q_room_building").select2().val(null).trigger("change");
	// $("#q_room_building").select2("destroy");
}
