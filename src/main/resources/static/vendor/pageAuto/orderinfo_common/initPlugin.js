/**
 * 初始化工程选择框
 */
var projectList = []


/**
 * 显示详细图片信息
 * @returns
 */
function showDetailImgModal(url) {
    $("#myModal_itemDetailPic").find(".detailImg").attr("src",url);
    
    $("#myModal_itemDetailPic").modal("show")
}

function getQueryParam(params){
	
	  var param = {
              pageSize: params.limit, // 每页要显示的数据条数
              offset: params.offset, // 每页显示数据的开始行号
              sortName: params.sort, // 要排序的字段
              sortOrder: params.order, // 排序规则
              projectName: $("#q_projectName").val(),
              orderNo: $("#q_orderNo").val(),
              serverType: $("#q_ServerType").val(),
              custPhone: $("#q_CustPhone").val(),
              customerName: $("#q_customerName").val(),
              startTime: $("#startTime").val(),
              endTime: $("#endTime").val(),
              areaCode: $("#q_provinceCode").val() + $("#q_cityCode").val() + $("#q_districtCode").val(),
              
              lockerPhone: $("#q_lockPhone").val(),
              lockName: $("#q_lockName").val(),
              EnterprisePhone: $("#q_EnterprisePhone").val(),
              EnterpriseName: $("#q_EnterpriseName").val(),
              orderFeeType:$("#q_orderFeeType").val(),
          };
          
	  
	  return param;
}

/**
 * 工单列表行样式处理
 * @param row
 * @param index
 * @returns
 */
function rowStyleFormat(row,index){
	var cls="";
	var style={};
	if(row.dataState==1)
	   {
	if(row.urgent==1)
		{
		cls="urgent ";
		}
	if(row.timeout==1)
	{
		if(row.orderState==201)
		cls +=" timeout ";
	}
	

	
	
	//cls +=" timeout ";
	
	   }
	else 
	{
		cls +=" nouse ";
	}
	
	style={classes:cls};
                    
    return style;
}

/**
 * 表格第一列工单图标标识
 * @param value
 * @param row
 * @param index
 * @returns
 */
function orderFlagFormat(value,row,index){
	

var html="";
if(row.dataState!=1)
 {
	 html+=  '<div title="数据已废弃" class="ordernouse img-responsive">'
 		+'<span class="fa-stack fa-lg">'
 		+'  <i class="fa fa-check-square-o fa-stack-1x"></i>'
 		 +' <i class="fa fa-ban fa-stack-2x "></i>'
 		+'</span>'
 		+'</div>';
	
	return html;
 }
if(row.urgent==1)
{
// return "<img src='"+getRPath()+"/img/t.png' class='orderflag img-responsive'>" +
	//"<img src='"+getRPath()+"/img/t.png' class='orderflag img-responsive'>"
	
 html+= '<div title="工单加急" class="orderflag img-responsive">'
+'<span class="fa-stack fa-lg">'
+'  <i class="fa fa-circle fa-stack-2x"></i>'
 +' <i class="fa fa-flash fa-stack-1x fa-inverse"></i>'
+'</span>'
+'</div>';
}
if(row.timeout==1)
	{
	//if(row.orderState==201)
html+=  '<div title="工单超时" class="ordertimeout img-responsive">'
+'<span class="fa-stack fa-lg">'
+'  <i class="fa fa-circle fa-stack-2x"></i>'
 +' <i class="fa fa-hourglass-1 fa-stack-1x fa-inverse"></i>'
+'</span>'
+'</div>';
}

 if (row.dataState == 1)
	 {

	 
/*	 html+=  '<div title="数据正常" class="orderuse img-responsive">'
 		+'<span class="fa-stack fa-lg">'
 		+'  <i class="fa fa-circle fa-stack-2x"></i>'
 		 +' <i class="fa fa-check-square-o fa-stack-1x fa-inverse"></i>'
 		+'</span>'
 		+'</div>';
	 */
	 }
 
 if(row.orderState<=6&&row.orderState>=1 )
	 {
	 
	/* html+=  '<div title="待审核" class="orderaudit img-responsive">'
	 		+'<span class="fa-stack fa-lg">'
	 		+'  <i class="fa fa-circle fa-stack-2x"></i>'
	 		 +' <i class="fa fa-jpy  fa-stack-1x fa-inverse"></i>'
	 		+'</span>'
	 		+'</div>';*/
	 
	 }
    




return html;
}


/**
 * 工单类型
 * @param value
 * @returns
 */
function orderTypeNameNew(value) {
	var type="";
    if (value .indexOf('19')>-1) {
        value=value.replace(19,9);
        type+= "其他,";//  "其他"
    }

    if (value .indexOf('0')>-1) {
    	type+= "安装,";
    }
    if (value .indexOf('1')>-1) {
    	type+= "维修,"; 
    }
    if (value .indexOf('2')>-1) {
    	type+= "开锁,";  //"开锁"
    }
    if (value .indexOf('3')>-1) {
    	type+= "特殊门改造,"; // "特殊门改造"
    }
    if (value .indexOf('4')>-1) {
    	type+= "测量,"; // "测量"
    }
    if (value .indexOf('5')>-1) {
    	type+= "猫眼安装,";//  "猫眼安装"
    }
    if (value .indexOf('6')>-1) {
        type+= "换锁,";//  "换锁"
    }
    if (value .indexOf('7')>-1) {
        type+= "工程安装,";//  "工程安装"
    }
    if (value .indexOf('8')>-1) {
        type+= "工程维修,";//  "工程维修"
    }

    type=type.substr(0,type.length-1);
    
    return type;
}



/**
 * 逻辑：先选择工程，则公司确定，公司下拉框禁用；先选择公司，则工程下拉框为该公司的工程
 * @param selectId
 * @param inputId
 * @param companyId
 */
function initProjectSelect(selectId, inputId, companyId, companySelectId, companyInputId) {
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            projectList = data
            $("#" + selectId).append(
                "<option value='" + data[i].id + "'>" + data[i].projectName + "</option>"
            )
        }
        $("#" + selectId).selectpicker('refresh')
    }
    var parameter = {}
    if (companyId != null) {
        parameter.companyId = companyId
    }
    sendRequest(getRPath() + "/manager/tproject/allProject", parameter, callback)

    var events = $._data($("#" + selectId)[0], "events")
    if (events.change.length <= 1) {
        //下拉菜单选中事件，获取工程id
        $("#" + selectId).on("change", function () {
            var id = $("#" + selectId).val()
            $("#" + inputId).val(id)
            for (var i = 0; i < projectList.length; i++) {
                if (id == projectList[i].id) {
                    $("#customerPhone").val(projectList[i].custPhone)

                    $("#customerName").val(projectList[i].custName)

                    $("#addressDetail").val(projectList[i].addressDetail)

                    $("#" + companyInputId).val(projectList[i].companyId)
                    if ($("#" + companySelectId).length > 0) {
                        if (projectList[i].companyId) {
                            $("#" + companySelectId).selectpicker("val", projectList[i].companyId)
                        } else {
                            $("#" + companySelectId).selectpicker("val", "-1")
                        }
                    }
                    $("#" + companySelectId).prop("disabled", true)

                    $("#provinceSelect").selectpicker("val", projectList[i].areaCode.substr(0, 2))
                    $("#provinceSelect").trigger("change")

                    $("#citySelect").selectpicker("val", projectList[i].areaCode.substr(2, 2))
                    $("#citySelect").trigger("change")

                    $("#districtSelect").selectpicker("val", projectList[i].areaCode.substr(4, 2))
                    $("#districtSelect").trigger("change")

                    break
                }
            }
        })
    }
}

/**
 * 初始化合伙人选择框
 */
function initCompanySelect(selectId, inputId) {

	
	
	initLockerSelect(selectId);
	
	return;
	
	
	
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#" + selectId).append(
                "<option value='" + data[i].id + "'>" + data[i].companyName + "</option>"
            )
        }
        $("#" + selectId).selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/tcompany/allCompany", {}, callback)

    var events = $._data($("#" + selectId)[0], "events")
    if (events.change.length <= 1) {
        //下拉菜单选中事件，获取工程id
        $("#" + selectId).on("change", function () {
            var companyId = $("#" + selectId).val()
            $("#" + inputId).val($("#" + selectId).val())
            //渲染城市下拉框
           // $("#projectSelect").empty()
           // initProjectSelect("projectSelect", "projectId", companyId)
        })
    }
}





//选择合伙人
function initLockerSelect(eleid,orderid) {

	$("#"+eleid).select2({
		// dropdownParent : $("#myModal"),
		placeholder : "选择合伙人",
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
				return "未找到结果"
			},
			searching : function() {
				return "搜索中…"
			}

		},

		ajax : {
			type : "post",
			url : getRPath() + "/manager/tuserinfo/tuserinfoList",
			dataType : "json",
			data : function(params) {

				var page = params.page || 1;

				var query = {
						name : params.term,
					pageNum : page,
					dataState:1,
					pageSize : 10,
					
					
					//parterquery:1//合伙人
	        		userType:2, //合伙人
	        		auditFlag:1,//通过

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
					selectdatas.push({
						id : item.id,
						text : item.name,
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

		templateResult : formatRepoLockHehuo,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		templateSelection : function(data, container) {
			/*var id = data.phone;

			var html = "" + data.text;
			if (id)
				html = "" + data.text + "&nbsp;&nbsp;(" + id + ") ";

			// $(container).parents("tr").find(".attr_id").val(data.id);
			return html;*/
			
			return data.text;
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

function formatRepoLockHehuo(repo) {
	if (repo.loading) {
		return repo.text;
	}

	var markup = " <div class=\"lock-item in-line\"> "
			+ "<div class=\"lockname col-xs-6\">" + repo.text + " </div> "
			//+ "  <div class=\"lockid col-xs-6\">(" + repo.phone + ")</div>"

	"</div> ";

	return markup;
}












/**
 * 初始化锁企选择框
 */
function initLockCompanySelect(selectId, inputId) {

	initLockerCompanySelect2(selectId);
	return;
	
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#" + selectId).append(
                "<option value='" + data[i].id + "'>" + data[i].enterpriseName + "</option>"
            )
        }
        $("#" + selectId).selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/tlockcompany/allCompany", {}, callback)

    var events = $._data($("#" + selectId)[0], "events")
    if (events.change.length <= 1) {
        //下拉菜单选中事件，获取工程id
        $("#" + selectId).on("change", function () {
            $("#" + inputId).val($("#" + selectId).val())
        })
    }
}




//选择合伙人
function initLockerCompanySelect2(eleid,orderid) {

	$("#"+eleid).select2({
		// dropdownParent : $("#myModal"),
		placeholder : "选择锁企",
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
				return "未找到结果"
			},
			searching : function() {
				return "搜索中…"
			}

		},

		ajax : {
			type : "post",
			url : getRPath() + "/manager/tlockcompany/tlockCompanyList",
			dataType : "json",
			data : function(params) {

				var page = params.page || 1;

				var query = {
						enterpriseName : params.term,
					pageNum : page,
					dataState:1,
					pageSize : 10,
					
					
					//parterquery:1//合伙人
	        		//userType:2, //合伙人
	        		//auditFlag:1,//通过

				}
				// (cids == null || cids.length == 0) ? "": cids[0]
				// Query parameters will be ?search=[term]&type=public
				return query;
			},

			processResults : function(data, params) {
                $("#"+orderid).val("");
                var selectdatas = [];
				/*
				 * if(typeof(params.page)=="undefined") selectdatas.push({ id :
				 * -1, text : '新建属性' });
				 */

				/*
				 * selectdatas.push({ id : '0', text : "系统" });
				 */

				$.each(data.rows, function(index, item) {
					selectdatas.push({
						id : item.id,
						text : item.enterpriseName,
						phone:item.enterprisePhone
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

		templateResult : formatRepoLockHehuo,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		templateSelection : function(data, container) {
			/*var id = data.phone;

			var html = "" + data.text;
			if (id)
				html = "" + data.text + "&nbsp;&nbsp;(" + id + ") ";

			// $(container).parents("tr").find(".attr_id").val(data.id);
			return html;*/
			$("#"+orderid).val(data.id);
			return data.text;
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




//选择产品
function initProductSelect2(eleid,lockcompanyId) {

	$("#"+eleid).select2({
		// dropdownParent : $("#myModal"),
		placeholder : "选择产品",
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
				return "未找到结果"
			},
			searching : function() {
				return "搜索中…"
			}

		},

		ajax : {
			type : "post",
			url : getRPath() + "/manager/tlockproductinfo/tlockproductinfoList",
			dataType : "json",
			data : function(params) {

				var page = params.page || 1;

				var query = {
						name : params.term,
					pageNum : page,

					pageSize : 10,
					dataState:1,
					lockEnterpriseID:lockcompanyId,
					
					//parterquery:1//合伙人
	        		//userType:2, //合伙人
	        		//auditFlag:1,//通过

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
					
					var typeName="";
					var lockType=item.lockType;
					if(lockType==0)
						typeName="NB-锁";
					else if(lockType==1)
						typeName="机械锁";
					else if(lockType==2)
						typeName="其他锁";
					
					selectdatas.push({
						id : item.id,
						text : item.name+'[型号:'+item.proType+'('+typeName+')]',
						//phone:item.enterprisePhone
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

		templateResult : formatproduct,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		templateSelection : function(data, container) {
			/*var id = data.phone;

			var html = "" + data.text;
			if (id)
				html = "" + data.text + "&nbsp;&nbsp;(" + id + ") ";

			// $(container).parents("tr").find(".attr_id").val(data.id);
			return html;*/
			
			return data.text;
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

function formatproduct(repo) {
	if (repo.loading) {
		return repo.text;
	}
	var markup =repo.text;
	return markup;
	
	
	var markup = " <div class=\"lock-item in-line\"> "
			+ "<div class=\"lockname col-xs-6\">" + repo.text + " </div> "
			+ "  <div class=\"lockid col-xs-6\">(" + repo.phone + ")</div>"

	"</div> ";

	return markup;
}





/**
 * 初始化锁匠选择框
 */
function initAllocateSelect(orderId) {

    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#lockerSelect").append(
                "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
            )
        }
        $("#lockerSelect").selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/tuserinfo/selectLockerByOrderId", {"id": orderId}, callback)

    var events = $._data($("#lockerSelect")[0], "events")
    if (events.change.length <= 1) {
        //下拉菜单选中事件，获取工程id
        $("#lockerSelect").on("change", function () {
            $("#lockerId").val($("#lockerSelect").val())
        })
    }
}

/**
 * 初始化日期选择器
 */
function initDataTimePicker(itemId,min,max) {
   
	
	$('#' + itemId).datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        todayBtn: true,
        language: 'zh-CN',
        autoclose: true,
        clearBtn: true,
        hourmin:min?min:0,
        hourmax:max?max:23,    
    });
}


function cleanSelectArea(provinceSelectId,otherChangeFun)
{
	otherOnChangeFun=otherChangeFun;
	
	//模糊匹配
	var pval=$("#"+provinceSelectId).val("");
	
	$("#"+provinceSelectId).trigger("change");
	
	
}

function setSelectArea(provinceSelectId, citySelectId, districtSelectId,response)
{
	
	//模糊匹配
	var pval=$("#"+provinceSelectId).find("option:contains('"+response.province+"')").val();
	$("#"+provinceSelectId).val(pval);
	$("#"+provinceSelectId).trigger("change");
	$("#"+provinceSelectId).selectpicker('val', pval);
	setTimeout(function(){

		var pcval=$("#"+citySelectId).find("option:contains('"+response.city+"')").val();
		
		$("#"+citySelectId).selectpicker('val', pcval);
		
		//$("#"+citySelectId).val(response.city);
		$("#"+citySelectId).trigger("change");
		setTimeout(function(){
			
			var pdval=$("#"+districtSelectId).find("option:contains('"+response.district+"')").val();
			
			//$("#"+districtSelectId).val(response.district);
			
			$("#"+districtSelectId).selectpicker('val', pdval);
			$("#"+districtSelectId).trigger("change");
		}, 100);
		
	}, 100);
	
}



/**
 * 初始化省选择框
 */
function initProvinceSelect(provinceSelectId, citySelectId, districtSelectId,
                            provinceCodeId, cityCodeId, districtCodeId,otherOnChange) {

	
	
	
    var provinceSelect = $("#" + provinceSelectId)
    
    
    
    
    
    
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            provinceSelect.append(
                "<option value='" + data[i].provinceCode + "' id='" + data[i].id + "'>" + data[i].provinceName + "</option>"
            )
        }
        provinceSelect.selectpicker('refresh')
        
    }
    sendRequest(getRPath() + "/manager/area/allProvince", {}, callback)

    

    var events = $._data(provinceSelect[0], 'events');
   if (events.change.length > 1) {
       events.change.pop()
   }
  
   //下拉菜单选中事件，获取工程id
   provinceSelect.on("change", function () {
       //为地区代码赋值
       $("#" + provinceCodeId).val(provinceSelect.val())
     

       //渲染城市下拉框
       $("#" + citySelectId).empty()
       	
       
       
       $("#" + districtSelectId).empty()
       var selectedProvinceId = $("#" + provinceSelectId + " option:selected").attr("id")
       initCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId, selectedProvinceId,otherOnChange)
       initDistrictSelect(districtSelectId, districtCodeId)
       $("#" + cityCodeId).val("")
       $("#" + districtCodeId).val("")
       
       setTimeout(function(){
       	  $("#"+provinceCodeId).trigger("change");
		}, 50);
   })
   
   setTimeout(function(){
	   $("#"+provinceSelectId).trigger("change");
		}, 50);
   
}


/**
 * 初始化创建时的省选择框
 */
function initCreateProvinceSelect(provinceSelectId, citySelectId, districtSelectId,
                            provinceCodeId, cityCodeId, districtCodeId,otherOnChange) {

    var provinceSelect = $("#" + provinceSelectId)
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            provinceSelect.append(
                "<option value='" + data[i].provinceCode + "' id='" + data[i].id + "'>" + data[i].provinceName + "</option>"
            )
        }
        provinceSelect.selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/area/lockInstallProvince", {}, callback)

    var events = $._data(provinceSelect[0], 'events');
    if (events.change.length > 1) {
        events.change.pop()
    }
    //下拉菜单选中事件，获取工程id
    provinceSelect.on("change", function () {
       
        
        
        $("#" + districtSelectId).empty()
        var selectedProvinceId = $("#" + provinceSelectId + " option:selected").attr("id")
        initCreateCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId, selectedProvinceId,otherOnChange)
        initDistrictSelect(districtSelectId, districtCodeId)
        $("#" + cityCodeId).val("")
        $("#" + districtCodeId).val("")
        
        
        //为地区代码赋值
        $("#" + provinceCodeId).val(provinceSelect.val())

        //渲染城市下拉框
        $("#" + citySelectId).empty()
        $("#"+citySelectId).trigger("change");
    })
}

/**
 * 初始化市选择器
 */
function initCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId, provinceId,otherOnChange) {

    var citySelect = $("#" + citySelectId)
    var callback = function (data) {
    	 citySelect.append(
                 "<option value='' id=''>请选择</option>"
             );
            
    	 
    	
        for (var i = 0; i < data.length; i++) {
            if (data[i].cityName == "重庆市") {
                if (data[i].cityCode == "01") {
                    data[i].cityName = data[i].cityName + "（区）"
                } else {
                    data[i].cityName = data[i].cityName + "（县）"
                }
            }
            citySelect.append(
                "<option value='" + data[i].cityCode + "' id='" + data[i].id + "'>" + data[i].cityName + "</option>"
            )
        }
        citySelect.selectpicker('refresh')
        
     
    }
    sendRequest(getRPath() + "/manager/area/provinceAllCity", {"id": provinceId}, callback)

    var events = $._data(citySelect[0], 'events');
    if (events.change.length > 1) {
        events.change.pop()
    }
    //下拉菜单选中事件，获取城市id
    citySelect.on("change", function () {
        //为地区代码赋值
        $("#" + cityCodeId).val(citySelect.val())

        //初始化区县选择器
        $("#" + districtSelectId).empty()
        var selectedCityId = $("#" + citySelectId + " option:selected").attr("id")
        initDistrictSelect(districtSelectId, districtCodeId, selectedCityId)
       $("#" + districtCodeId).val("")
        
        //其他外部事件
        if(typeof(otherOnChange)=='function')
        	otherOnChange();
        
     /*   
        //其他外部事件
        if(typeof(otherOnChangeFun)=='function')
        	otherOnChangeFun();*/
    })
    
    setTimeout(function(){
    	   $("#"+citySelectId).trigger("change");
 		}, 50);
}

//var otherOnChangeFun;

/**
 * 初始化市创建安装下拉框
 */
function initCreateCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId, provinceId,otherOnChange) {

    var citySelect = $("#" + citySelectId)
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            if (data[i].cityName == "重庆市") {
                if (data[i].cityCode == "01") {
                    data[i].cityName = data[i].cityName + "（区）"
                } else {
                    data[i].cityName = data[i].cityName + "（县）"
                }
            }
            citySelect.append(
                "<option value='" + data[i].cityCode + "' id='" + data[i].id + "'>" + data[i].cityName + "</option>"
            )
        }
        citySelect.selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/area/lockInstallCity", {"id": provinceId}, callback)

    var events = $._data(citySelect[0], 'events');
    if (events.change.length > 1) {
        events.change.pop()
    }
    //下拉菜单选中事件，获取城市id
    citySelect.on("change", function () {
        //为地区代码赋值
        $("#" + cityCodeId).val(citySelect.val())

        //初始化区县选择器
        $("#" + districtSelectId).empty()
        var selectedCityId = $("#" + citySelectId + " option:selected").attr("id")
        initDistrictSelect(districtSelectId, districtCodeId, selectedCityId)
        $("#" + districtCodeId).val("")
        
        //其他外部事件
        if(typeof(otherOnChange)=='function')
        	otherOnChange();
    })
}

/**
 * 初始化区县选择器
 */
function initDistrictSelect(districtSelectId, districtCodeId, cityId) {

    var districtSelect = $("#" + districtSelectId)
    var callback = function (data) {
    	
    	districtSelect.append(
                "<option value='' id=''>请选择</option>"
            );
    	
        for (var i = 0; i < data.length; i++) {
            districtSelect.append(
                "<option value='" + data[i].districtCode + "' id='" + data[i].id + "'>" + data[i].districtName + "</option>"
            )
        }
        districtSelect.selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/area/cityAllDistrict", {"id": cityId}, callback)

   /* var events = $._data(districtSelect[0], 'events');
    if (events.change.length > 1) {
        events.change.pop()
    }*/
    //下拉菜单选中事件，获取城市id
    districtSelect.on("change", function () {
        //为地区代码赋值
        $("#" + districtCodeId).val(districtSelect.val())
    })
}

function initFileInput() {
    $("#excelFile").fileinput({
        language: "zh",//设置语言
        showCaption: true,//是否显示标题
        showUpload: false, //是否显示上传按钮
        allowedFileExtensions: ["xls", "xlsx"], //接收的文件后缀
        maxFileCount: 1,//最大上传文件数限制
        maxFileSize: 1000,//最大上传文件大小
        previewFileIcon: '<i class="glyphicon glyphicon-file"></i>',
        allowedPreviewTypes: null,
        previewFileIconSettings: {
            'doc': '<i class="fa fa-file-word-o text-primary"></i>',
            'xls': '<i class="fa fa-file-excel-o text-success"></i>',
            'ppt': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
            'jpg': '<i class="fa fa-file-photo-o text-warning"></i>',
            'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
            'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
            'htm': '<i class="fa fa-file-code-o text-info"></i>',
            'txt': '<i class="fa fa-file-text-o text-info"></i>',
            'mov': '<i class="fa fa-file-movie-o text-warning"></i>',
            'mp3': '<i class="fa fa-file-audio-o text-warning"></i>',
        },
        previewFileExtSettings: {
            'doc': function (ext) {
                return ext.match(/(doc|docx)$/i);
            },
            'xls': function (ext) {
                return ext.match(/(xls|xlsx)$/i);
            },
            'ppt': function (ext) {
                return ext.match(/(ppt|pptx)$/i);
            },
            'zip': function (ext) {
                return ext.match(/(zip|rar|tar|gzip|gz|7z)$/i);
            },
            'htm': function (ext) {
                return ext.match(/(php|js|css|htm|html)$/i);
            },
            'txt': function (ext) {
                return ext.match(/(txt|ini|md)$/i);
            },
            'mov': function (ext) {
                return ext.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i);
            },
            'mp3': function (ext) {
                return ext.match(/(mp3|wav)$/i);
            },
        }
    });
}



/**
 * 工单涉及到的通用操作
 */
window.PersonnelInformationEvents_item = {
   
		 "click #delete": function (e, value, row, index) {
			 
			 
			 initOrderFeiqiValidate();
				//备注标记工单
			    //初始化工程下拉框
				 $("#myModal_delRemark").find(".remarkdOId").val(row.id);
				 $("#myModal_delRemark").find(".logremarkcontent").val("");
			   
			    
			    //initAllocateSelect(row.id)
			    $("#myModal_delRemark").modal("show")
			    
			

		  }    ,
		  "click #reset": function (e, value, row, index) {
		      var msg = "您真的确定执行恢复操作吗？";
		      var url = getRPath() + "/manager/torderinfo_todo/ReUse";
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
		        var msg = "您真的确定要删除吗？";
		        var url = getRPath() + "/manager/torderinfo_todo/realdelete";
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
		
		    "click #showAudit": function (e, value, row, index) {
		    	//显示审核信息
		        var orderId = row.id
		        initOrderAuditTable(orderId)
		        
		        var managerType = $("#managerType").val()
		        if ((row.orderState == 4 && managerType == 2) || (row.orderState == 5 && managerType == 0)) {
		            $("#auditModalFooter").append(
		                '<button type="button" class="btn btn-default" id="passAudit" onclick="addMoneyAuditPass()">\n' +
		                '    通过\n' +
		                '</button>\n' +
		                '<button type="button" class="btn btn-danger" id="noPassAudit" onclick="addMoneyAuditFail()">\n' +
		                '    不通过\n' +
		                '</button>'
		            )
		        } else if (row.orderState == 1 && managerType == 0) {
		            $("#auditModalFooter").append(
		                '<button type="button" class="btn btn-default" id="passAudit" onclick="addMoneyAuditPass()">\n' +
		                '    通过\n' +
		                '</button>\n' +
		                '<button type="button" class="pull-left btn btn-warning" id="applyToLockCompany" onclick="applyToLockCompanyModalShow()">\n' +
		                '    向锁企申请加钱\n' +
		                '</button>\n' +
		                '<button type="button" class="btn btn-danger" id="noPassAudit" onclick="addMoneyAuditFail()">\n' +
		                '    不通过\n' +
		                '</button>'
		            )
		        }
		        $("#myModal_item").modal("show")
		    },
		    "click #addRemark": function (e, value, row, index) {
		    	
		    	initOrderBeizhuValidate();
		    	//备注标记工单
		        //初始化工程下拉框
		    	 $("#myModal_itemRemark").find(".remarkdOId").val(row.id);
		    	 $("#myModal_itemRemark").find(".logremarkcontent").val("");
		       
		        
		        //initAllocateSelect(row.id)
		        $("#myModal_itemRemark").modal("show")
		    },

    "click #allocateOrder": function (e, value, row, index) {
    	//分配工单
        //初始化工程下拉框
        $("#orderId").val(row.id)
        
        try {
        	$("#allocateOrderForm").find("#lockerSelect").select2().val(null).trigger("change");
       		$("#allocateOrderForm").find("#lockerSelect").select2("destroy");
		} catch (e) {
			// TODO: handle exception
		}
      
   		
        initLockerOrParterSelect(row.id);
        
        
        $("#allocateOrderModal").find("#orderInfoId").val(row.id);
        $("#allocateOrderModal").find("#tip").val(row.areaName);
        
        $("#allocateOrderModal").find("#addMoney").val("");
        $("#allocateOrderModal").find("#addMoneyDesc").val("");
        
        
        

    	var opt = {
    		silent : true,
            pageNumber:1
    	};
    	$("#table_list_money").bootstrapTable('refresh', opt);
        
        //initAllocateSelect(row.id)
        $("#allocateOrderModal").modal("show")
    },
    "click #updatemoneyquestion": function (e, value, row, index) {
    	//订单金额申述
        $("#myModal_itemMoney").find("#id").val(row.id);
        $("#myModal_itemMoney").modal();
    },
    
    "click #moneyModify": function (e, value, row, index) {
		 
		 

		//工单界面-工单改价
		 $("#myModal_MoneyModify").find(".remarkdOId").val(row.id);
		 $("#myModal_MoneyModify").find(".logremarkcontent").val("");
	   
		 var opt = {
		    		silent : true,
		            pageNumber:1
		    	};
		    	$("#myModal_MoneyModify").find(".table_order_money").bootstrapTable('refresh', opt);
		        
	    //initAllocateSelect(row.id)
	    $("#myModal_MoneyModify").modal("show")
	    
	

  }    ,
  "click #moneyModify2": function (e, value, row, index) {
		 
		 
//提现审核界面 -工单改价
		 $("#myModal_MoneyModify").find(".remarkdOId").val(row.orderInfoId);
		 $("#myModal_MoneyModify").find(".logremarkcontent").val("");
	   
		 var opt = {
		    		silent : true,
		            pageNumber:1
		    	};
		    	$("#myModal_MoneyModify").find(".table_order_money").bootstrapTable('refresh', opt);
		        
	    //initAllocateSelect(row.id)
	    $("#myModal_MoneyModify").modal("show")
	    
	

}    ,

"click #moneypass": function (e, value, row, index) {
	 
	 
	//提现审核界面 -审核通过
		pass(row.id,row.depositStatus);

	}    ,
	"click #moneypass2": function (e, value, row, index) {
		 
		 
		//提现审核界面 -审核通过
			pass(row.id,row.depositStatus);

		}    ,
    
    "click #cancelOrder": function (e, value, row, index) {
    	//源匠取消工单，变为待作业工单，清除合伙人锁匠信息
        
        //$("#orderId").val(row.id)
    	var msg = "确认取消工单，将工单改为待接单？";
        // var url = getRPath() + "/manager/torderinfo_todo/realdelete";
         var url = getRPath() + "/manager/orderInfo/cancelOrder";
         cconfirm(msg, function () {
             $.ajax({
                 type: "post",
                 url: url,
                 data: {
                     "id": row.id
                 },
                 success: function (data) {
                 	 if (data.errCode = 1) {
                          doSearch_item();
                          success("操作成功！");
                      } else {
                          error(data.errMsg);
                      }
                 }
             });
         });
    },
    
    "click #showEdit": function (e, value, row, index) {
    	//锁企修改工单
        
        //$("#orderId").val(row.id)
        showOrderModify(row);
    },
    "click #showConfirmOrder": function (e, value, row, index) {
    	//锁企修改工单
        
        //$("#orderId").val(row.id)
    	//showOrderInfo(row.id);
    	
    	var msg = "确认工单信息？";
       // var url = getRPath() + "/manager/torderinfo_todo/realdelete";
        var url = getRPath() + "/manager/orderInfo/updateCreateOrderInfo";
        cconfirm(msg, function () {
            $.ajax({
                type: "post",
                url: url,
                data: {
                    "id": row.id
                },
                success: function (data) {
                	 if (data.errCode = 1) {
                         doSearch_item();
                         success("操作成功！");
                     } else {
                         error(data.errMsg);
                     }
                }
            });
        });
    },

   

    
};


/**
 * 工单改价初始化
 * @returns
 */
function InitQuery_orderMoney(){
	
	function initMoneyValidater(){
	 $("#MoneyModifyForm").bootstrapValidator({
	        feedbackIcons: {
	            /* input状态样式通过，刷新，非法三种图片 */
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        // submitButtons : 'button[type="submit"]',// 提交按钮
	        fields: {
	          
	            logremarkcontent: {
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
	}
	 
	/* $("#MoneyModifyForm").bootstrapValidator({
	        feedbackIcons: {
	             input状态样式通过，刷新，非法三种图片 
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	logremarkcontent: {
	        		 notEmpty: {
	                        message: '不能为空'
	                    },
	                    callback: {
	                        message: '输入的内容长度须小于50字符',
	                        //trigger: 'change',
	                        callback: function (value, validator) {
	                            if (value.length > 50) {
	                                return false
	                            }
	                            return true
	                        }
	                    }
	            },
	            
	            money: {
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
	            
	        
	        }
	 });*/
	
    	// 初始化Table
	$("#myModal_MoneyModify").find(".table_order_money").bootstrapTable({
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
    				
    				
    				
    				orderinfoId :   $("#myModal_MoneyModify").find(".remarkdOId").val(),
                   
    				
    			};
    			return param;
    		},
    		onPostBody:function(){
    			setTimeout(function(){
    				initMoneyValidater();
    			},
				 200);
    			
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
    			title : '金额',
    			align : 'center',
    			valign : 'middle',
    			   formatter: function (value, row, index) {
    				   if(row.reasonDes!="总计")
    					   {
    					   return "<input name='money' onkeyup='dynamicValidate(this)' data-bv-field='"+row.id+"' class='moneydetail form-control' value='"+value+"' id='"+row.id+"' >"
       	            	+'<small class="help-block" id="validate'+row.id+'" data-bv-validator="callback" data-bv-for="'+row.id+'" data-bv-result="" style="display: none;">金额异常</small>';   
    					   }
    	            	
    				   else
    					   return value;
    	        }
    			
    		},
    	
    		],

    	    });
    }


/**
 * 利用bootstrapvalidate css手动验证 手动加价验证
 * @param item
 * @returns
 */
function dynamicValidate(item){
	 
	   var isMoneyOk=true;
	//if( !/^[0-9]+.?[0-9]*$/.test($(item).val())){
	   if( !/^[1-9]+[0-9]*$/.test($(item).val()) || $(item).val()>20000  ){
	   $("#validate"+$(item).attr("id")).attr("data-bv-result","INVALID");
	   $("#validate"+$(item).attr("id")).attr("style","display:block");
	   
	   $(item).parent().addClass(" has-feedback has-error");
	  
	   isMoneyOk=false;
	  
	  }
	else{
		 $("#validate"+$(item).attr("id")).attr("data-bv-result","VALID");
		   $("#validate"+$(item).attr("id")).attr("style","display:none");
		   
		   $(item).parent().removeClass("has-error ").addClass("has-success");
		   
	}
	
	
	
	 return isMoneyOk
}


/**
 *  利用bootstrapvalidate css手动验证 工单审核加价验证
 * @param item   myModal_AuditMoney
 * @returns
 */
function dynamicAuditReasonValidate(item){
	 
	   var isMoneyOk=true;
	//if( !/^[0-9]+.?[0-9]*$/.test($(item).val())){
	   
	   var money=$(item).find(".addMoney").val();
	   var moneyItem=$(item).find(".addMoney");
	   
	   if( !/^[1-9]+[0-9]*$/.test(money) || money>20000  ){
	   $("#validate"+$(item).attr("id")).attr("data-bv-result","INVALID");
	   $("#validate"+$(item).attr("id")).attr("style","display:block");
	   
	   $(item).parent().addClass(" has-feedback has-error");
	  
	   isMoneyOk=false;
	  
	  }
	else{
		 $("#validate"+$(item).attr("id")).attr("data-bv-result","VALID");
		   $("#validate"+$(item).attr("id")).attr("style","display:none");
		   
		   $(item).parent().removeClass("has-error ").addClass("has-success");
		   
	}
	
	
	
	 return isMoneyOk
}



$(function(){
	
	
	$("#btnMoneyModify").click(function () {


		 
	      var msg = "您确定要执行改价操作吗？";
	      
	      var id= $("#myModal_MoneyModify").find(".remarkdOId").val();
			var content= $("#myModal_MoneyModify").find(".logremarkcontent").val();
			
			
			
			  $("#MoneyModifyForm").data('bootstrapValidator').resetForm();
		        $("#MoneyModifyForm").data("bootstrapValidator").validate();
		        var flag = $("#MoneyModifyForm").data("bootstrapValidator").isValid();


		        
		       var moneys= $("#MoneyModifyForm").find(".moneydetail");
		       var isMoneyOk=true;
		       var moneydatas=[];
		       $.each(moneys,function(index,item){
		    	   
		    	   isMoneyOk=dynamicValidate(item);
		    	   if(!isMoneyOk)
		    		   return;
		    	   
		    	   
		    	   var moneydata={};
		    	   moneydata.id=$(item).attr("id");
		    	   moneydata.val=$(item).val();
		    	   
		    	   moneydatas.push(moneydata);
		    	   
		    	   if( !/^[0-9]+.?[0-9]*$/.test($(item).val())){
		    		   
		    		
		    		   $("#validate"+$(item).attr("id")).attr("data-bv-result","INVALID");
		    		   $("#validate"+$(item).attr("id")).attr("style","display:block");
		    		   
		    		   $(item).parent().addClass(" has-feedback has-error");
		    		  
		    		   isMoneyOk=false;
		    		   
		    		   
		    		   
		    		   return;
		    		   //$(item).append('<small class="help-block" data-bv-validator="callback" data-bv-for="logremarkcontent" data-bv-result="VALID" style="display: none;">输入的内容长度须小于50字符</small>');
		    	   }
		       })
		       
		       if(!isMoneyOk||!flag)
		    	   return;
		        
		        

	      
	      var url = getRPath() + "/manager/torderinfo_pay/ModifyMoneyList";
	      cconfirm(msg, function () {
	          $.ajax({
	              type: "post",
	              url: url,
	              data: {
	                  "data": JSON.stringify( moneydatas),
	                  "id":id,
	                  "reason":content
	              },
	              success: function (response) {
	                  if (response.bol) {
	                      success("操作成功！");
	                      
	                      $("#myModal_MoneyModify").modal("hide");
	                      
	                      doSearch_item();
	                  } else {
	                      error("" + response.message);
	                  }
	              }
	          });
	      });
		
		
		
	});
	
	
	$("#btnLogRemark").click(function () {

		var id= $("#myModal_itemRemark").find(".remarkdOId").val();
		var content= $("#myModal_itemRemark").find(".logremarkcontent").val();
		
		
		$("#RemarkReasonForm").data('bootstrapValidator').resetForm();
        $("#RemarkReasonForm").data("bootstrapValidator").validate();
        var flag = $("#RemarkReasonForm").data("bootstrapValidator").isValid();
        
        if(flag)
		{
		   var url = getRPath() + "/manager/torderinfooperatelog/saveOrUpdate";
		   $.ajax({
	        type: "post",
	        url: url,
	        data: {
	        	orderinfoId:id,
	           content:content
	        },
	        success: function (response) {
	            if (response.bol) {
	                success("操作完成！");
	                //doSearch_item();
	                refreshDetailLog();
	                $("#myModal_itemRemark").modal("hide");
	            } else {
	                error("" + response.message);
	            }
	        }
	    });
		}
	});
	
	
	$("#btnDelRemark").click(function () {

		
		 
		 
	      var msg = "您真的确定执行废弃操作吗？";
	      
	      var id= $("#myModal_delRemark").find(".remarkdOId").val();
			var content= $("#myModal_delRemark").find(".logremarkcontent").val();
	      

			   $("#myModal_delRemark").find("#deleteReasonForm").data("bootstrapValidator").validate();
		        // flag = true/false
		        var flag =$("#myModal_delRemark").find("#deleteReasonForm").data("bootstrapValidator").isValid();
		        
		        
		        if(flag)
			{
			
	      var url = getRPath() + "/manager/torderinfo_todo/NoUsedelete";
	      cconfirm(msg, function () {
	          $.ajax({
	              type: "post",
	              url: url,
	              data: {
	                  "id": id,
	                  "deleteReason":content
	              },
	              success: function (response) {
	                  if (response.bol) {
	                      success("操作成功！");
	                      
	                      $("#myModal_delRemark").modal("hide");
	                      
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



function initOrderBeizhuValidate(){
 $("#myModal_itemRemark").find("#RemarkReasonForm").bootstrapValidator({
	        feedbackIcons: {
	            /* input状态样式通过，刷新，非法三种图片 */
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        // submitButtons : 'button[type="submit"]',// 提交按钮
	        fields: {
	          
	        	content: {
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


function initOrderFeiqiValidate(){
 $("#myModal_delRemark").find("#deleteReasonForm").bootstrapValidator({
	        feedbackIcons: {
	            /* input状态样式通过，刷新，非法三种图片 */
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        // submitButtons : 'button[type="submit"]',// 提交按钮
	        fields: {
	          
	        	deleteReason: {
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




/**
 * 显示更新模态框
 * @param orderInfoId
 */
function showOrderInfo(orderInfoId) {
    var parameter = {}
    parameter.id = orderInfoId
    var url = getRPath() + '/manager/torderinfo_doing/load'
    var callback = function (response) {
        console.log(response)
        $("#updateOrderInfoForm").fill(response);
        $("#updateOrderInfoModal").modal("show")
    }
    sendRequest(url, parameter, callback)
}



/**
 * 工单回访/工单完结，查看工单详情， 包括锁匠完工图片
 * @param lockNum
 * @param imgs
 * @returns
 */
function initImgDetails(lockNum,imgs){
	
	$(".dvcontainer").html("");
	
	 SCROLL_picNum=lockNum; //滑动模块数
	 SCROLL_divWidth=500;
	 
	 if(SCROLL_picNum==1)
		 {
		 $(".scroll_left").addClass("hide");
		 $(".scroll_right").addClass("hide");
		 }
	 else
		 {
		 $(".scroll_left").removeClass("hide");
		 $(".scroll_right").removeClass("hide");
		 }
	 
		
			 $(".scroll_left").addClass("disable");
			
	 
	//
	
	/*<ul>
	<li>



<p class="ltitle">第一把锁信息</p>
	<p class="ltitle2">安装前</p>
													<div class='auditImgDiv'>
														<img src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> <img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> 
															
															
															<img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt="">
													</div>
													
														<p class="ltitle2">安装前</p>
													<div class='auditImgDiv'>
														<img src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> <img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> 
															
															
															<img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt="">
													</div>



	</li>*/
	
	
	
	
	var html="<ul>";
	
	if(typeof(imgs.length)!='undefined')
		{
			for (var i = 0; i < imgs.length; i++) {
		
				html+="<li>";
					
				html+='<p class="ltitle">锁'+(i+1)+'信息</p>';
				
				
				var startimgs="";
				var lockimgs="";
				var doneimgs="";
				
				if(typeof(imgs[i].startimgs)!='undefined')
				 startimgs=imgs[i].startimgs.split(",");
				if(typeof(imgs[i].lockimgs)!='undefined')
				 lockimgs=imgs[i].lockimgs.split(",");
				if(typeof(imgs[i].doneimgs)!='undefined')
				 doneimgs=imgs[i].doneimgs.split(",");
				
				html+='<p class="ltitle2">安装前照片</p>';
				html+="<div class='auditImgDiv'>";
				for (var j = 0; j < startimgs.length; j++) {
					if(startimgs[j]!='')
						{
				    var url = getRootPath() + "/mongo/getAuditImg/" + startimgs[j];
				    html += "<img onclick='showDetailImgModal(\"" + url + "\")' class='img-responsive auditImg' src='" + url + "'/>"
						}
				}
				html+="</div>";
				
				html+='<p class="ltitle2">安装中照片</p>';
				html+="<div class='auditImgDiv'>";
				for (var j = 0; j < lockimgs.length; j++) {
					if(lockimgs[j]!='')
					{
			    var url = getRootPath() + "/mongo/getAuditImg/" + lockimgs[j];
			    html += "<img onclick='showDetailImgModal(\"" + url + "\")' class='img-responsive auditImg' src='" + url + "'/>"
					}
				}
				html+="</div>";

				html+='<p class="ltitle2">安装完成照片</p>';
				html+="<div class='auditImgDiv'>";
				for (var j = 0; j < doneimgs.length; j++) {
					if(doneimgs[j]!='')
					{
			    var url = getRootPath() + "/mongo/getAuditImg/" + doneimgs[j];
			    html += "<img onclick='showDetailImgModal(\"" + url + "\")' class='img-responsive auditImg' src='" + url + "'/>"
					}
				}
				html+="</div>";
				
				html+="</li>";
		
			}
		}
	
	html+="</ul>";
	
	$(".dvcontainer").html(html);
	
}




//btnmodify
function showOrderModify(row){
	 $('#idMessage').html("");
 $('#phoneMessage').html("");
 
 $.ajax({
     type: "post",
     url: getRPath() + '/manager/torderinfo_doing/load',
     data: {
         id: row.id
     },
     async: false,
     dataType: "json",
     success: function (response) {
    	 
    	 // console.log(response)
          //$("#updateOrderInfoForm").fill(response);
         // $("#updateOrderInfoModal").modal("show")
    	 $("#mform_item").find("[name='serverType']:checkbox").attr("checked",false);
    	   
          $("#mform_item").fill(response);
          
          var provinceSelectId = "provinceSelect"
              var citySelectId = "citySelect"
              var districtSelectId = "districtSelect"
              var provinceCodeId = "provinceCode"
              var cityCodeId = "cityCode"
              var districtCodeId = "districtCode"
       
      	setSelectArea(provinceSelectId,citySelectId,districtSelectId, response,null);
         

         $("#myModal_item_title").html("修改工单");
         
         
       
         var typeName="";
         //合伙人锁匠，select2赋值
        // initLockerSelect("companyId");
         if(response.lockType==0)
				typeName="NB-锁";
			else if(response.lockType==1)
				typeName="机械锁";
			else if(response.lockType==2)
				typeName="其他锁";
         
         
         $("#sellerId").select2().val(null).trigger("change");
 		$("#sellerId").select2("destroy");
 		
 		
 		
 		
 		$("#dvSellerId").addClass('hide')
 		
 		  $("#sellerId").val(response.sellerId);
 		
 	   var managerType = $("#managerType").val();
 	   if(managerType==0)
 		   {
 		
 		  var pname=response.enterpriseName;
          var option = new Option(pname, response.sellerId, true, true);
          $("#sellerId").append(option).trigger('change');
          $("#sellerId").trigger({
              type: 'select2:select',
              params: {
                  data: {text:pname,id:response.sellerId}
              }
          });
          
 		  $("#dvEnterpriseName").removeClass('hide');
 		   }
 		
 	
          
 		/*
 		 
          var pname=response.productName+"["+response.proType+"("+typeName+")]";
          var option = new Option(pname, response.productId, true, true);
          $("#productId").append(option).trigger('change');
          $("#productId").trigger({
              type: 'select2:select',
              params: {
                  data: {text:response.pname,id:response.productId}
              }
          });
         
        */ 
         
     	$("#productId").select2().val(null).trigger("change");
		$("#productId").select2("destroy");
		 initProductSelect2("productId",$("#sellerId").val());
         
         var pname=response.productName+"["+response.proType+"("+typeName+")]";
         var option = new Option(pname, response.productId, true, true);
         $("#productId").append(option).trigger('change');
         $("#productId").trigger({
             type: 'select2:select',
             params: {
                 data: {text:pname,id:response.productId}
             }
         });
         
         
         
         
         // $("#passtip").text("密码留空则保持原密码不变,填入密码则设置新密码")
         $("#myModal_item").modal();
     }
 });
}



