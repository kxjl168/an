/**
 * 
 */


//选择案件类型，等级
function initAlarmTypeSelect(eleid,dictType) {

	var tip="选择案件类型";
	if(dictType=='2')
		 tip="选择案件等级";
	else if(dictType=='3')
		 tip="选择常用语";
	
	var input="";
	$("#"+eleid).select2({
		// dropdownParent : $("#myModal"),
		placeholder : tip,
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
			url : getRPath() + "/talk/sysdictinfoList",
			dataType : "json",
			data : function(params) {

				var page = params.page || 1;

				input=params.term;
				var query = {
						type:dictType,
						dictName : params.term,
					pageNum : page,
					dataState:1,
					pageSize : 10,
					
				

				}
				// (cids == null || cids.length == 0) ? "": cids[0]
				// Query parameters will be ?search=[term]&type=public
				return query;
			},

			processResults : function(data, params) {
				var selectdatas = [];
				
				 /*if(typeof(params.page)=="undefined" && typeof(input)=="undefined" ) 
					 selectdatas.push({ id :
				 "-1", text : '全选',phone:'' });
				 */

				/*
				 * selectdatas.push({ id : '0', text : "系统" });
				 */

				$.each(data.rows, function(index, item) {
					selectdatas.push({
						id : item.dictValue,
						text : item.dictName,
						
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

		templateResult : queryformatType,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		templateSelection : function(data, container) {
			var id = data.id;

			var html = "" + data.text;
			
		//	if (id)
			//	html = "" + data.text + "&nbsp;&nbsp;(" + id + ") ";

			// $(container).parents("tr").find(".attr_id").val(data.id);
			//return "<span phone='"+data.phone+"'>"+html+"</span>";
			
			return data.text;
		},

	});


}


function queryformatType(repo) {
	if (repo.loading) {
		return repo.text;
	}
	
	return repo.text;
}


