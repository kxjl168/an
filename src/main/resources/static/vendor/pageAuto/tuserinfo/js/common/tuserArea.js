/**
 * 
 */
function loadAreaTree(treeid,cityCode,user_id,edit) {
	
	
	if( (typeof(cityCode)=='undefined'||cityCode==''))
		{
		//清空区域
		$("#"+treeid).html("");
		return;
		}
	
	
/*	if( (typeof(cityCode)=='undefined'||cityCode=='') && (typeof(user_id)=="undefined")||(user_id==""))
		return;*/
	
	var setting = {
		isSimpleData : true, // 数据是否采用简单 Array 格式，默认false
		treeNodeKey : "id", // 在isSimpleData格式下，当前节点id属性
		treeNodeParentKey : "pId", // 在isSimpleData格式下，当前节点的父节点id属性
		check : {
			autoCheckTrigger : false,
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			},
			chkStyle : "checkbox",
			enable : true,
			nocheckInherit : false,
			radioType : "level"
		},
		view : {
			showLine : true, // 是否显示节点间的连线
			// addHoverDom : addHoverDom, // 增加节点 点击新增
			// removeHoverDom : removeHoverDom,
			selectedMulti : false
		},
		edit : {
			enable : false,
			editNameSelectAll : true,
		// renameTitle : renameTitle, // 编辑按钮说明文字
		// removeTitle : removeTitle, // 删除按钮说明文字
		// showRemoveBtn : showRemoveBtn, // 是否显示移除按钮
		// showRenameBtn : showRenameBtn
		// 是否显示编辑按钮
		},
		callback : {
		// beforeDrag : beforeDrag,
		// beforeEditName : beforeEditName, // 编辑节点
		// beforeRemove : beforeRemove, // 删除节点
		// beforeRename : beforeRename,
		// onRemove : onRemove,
		// onRename : onRename,
		// beforeDrop : beforeDrop,
		// beforeClick : beforeClick,
		// onCheck : onCheck
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPID : 0
			}
		}
	};
	// var role_id=$("#role_id").val();
	// 防止页面乱码现象
	$.ajax({
		async : false,
		cache : false,
		type : 'post',
		data : {
			user_id : user_id,
			cityId:cityCode,
			enableCheck:edit,
		},
		dataType : "json",
		url : getRPath() + "/manager/area/getDistrictTree",// 请求的action路径
		error : function() {// 请求失败处理函数
			error('请求失败');
		},
		success : function(data) { // 请求成功后处理函数
			var data1 = eval('[' + data + ']');
			// if (typeof (data1.name) == undefined) {
			// data1.name = '';
			// }
			zNodes = data1; // 把后台封装好的简单Json格式赋给treeNodes
			$.fn.zTree.init($("#"+treeid), setting, zNodes);
		}
	});
	$("#selectAll").bind("click", selectAll);
	$("#"+treeid+" a").each(function() {

	});

}




/**
 * 切换可作业行政区域district
 * @param cityid
 * @returns
 */
function changeJobArea(){
	
	
	//$("#"+provinceSelectId).find("option:selected").text()
	var cityId=$("#"+citySelectId).find("option:selected").attr('id');
	var userid=$("#id").val();
	
	loadAreaTree("treeDemo",cityId,userid,'false');
}

/**
 * 切换可作业行政区域district
 * @param cityid
 * @returns
 */
function loadArea(response){
	
	loadAreaTree("treeDemo2",response.cityId,response.id,'true');
}


/**
 * 动态添加或者删除 作业区域验证
 * @param formid
 * @param callback
 * @param addaction
 * @returns
 */
function AreaValidator(formid,nullcallback, callback,addaction){
	
	if(addaction)
		{
	$('#'+formid)
	.bootstrapValidator(
			"addField",
			'treeDemo',
			{
				validators : {
					callback : {
						message : '作业区域不能为空',
						nullcallback
					},
					callback : {
						message : '请选择作业区域,{0}中已存在合伙人',
						callback : callback

					}

				}
			});
		}
	else{
		$('#'+formid).bootstrapValidator('removeField',
		'treeDemo');
	}
	
}


/**
 * 验证所选作业区域是否已经有其他合伙人
 * @param value
 * @param validator
 * @returns
 */
function testAreaNoRepeat(value, validator) {
	 var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = zTree.getCheckedNodes(true);
		var districtids="";
		for (var i = 0, l = nodes.length; i < l; i++) {
			districtids += nodes[i].id + ",";
		}
		
		 $.ajax({
	            type: "post",
	            url: getRPath() + '/manager/tuserinfo/checkLockAndDistrict',
	            data: {
	            	districtids: districtids,
	            	name:$("#id").val()
	            },
	            async: false,
	            dataType: "json",
	            success: function (response) {
	            	
	            	if(response.bol)
	            		return true;
	            	else
	            		{
	            		error(response.message);
	            		return false;
	            		}
	            	
	            },
	            error:function(){
	            	error("作业区域验证失败!");
	            	return false;
	            }
	            
		 });
		
		
	return true;
}



function testAreaSelect1(value, validator) {
	 var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = zTree.getCheckedNodes(true);
		var districtids="";
		for (var i = 0, l = nodes.length; i < l; i++) {
			districtids += nodes[i].id + ",";
		}
		if(districtids=="")
			{
			//error("请选择可作业区域!");
			return false;
			}
		
	return true;
}

function testAreaSelect2(value, validator) {
	 var zTree = $.fn.zTree.getZTreeObj("treeDemo2");
		var nodes = zTree.getCheckedNodes(true);
		var districtids="";
		for (var i = 0, l = nodes.length; i < l; i++) {
			districtids += nodes[i].id + ",";
		}
		if(districtids=="")
			{
			//error("请选择可作业区域!");
			return false;
			}
		
	return true;
}






  


function selectAll() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var ck = $("#selectAll").get(0).checked;
	// alert(ck);
	zTree.checkAllNodes(ck);
}







