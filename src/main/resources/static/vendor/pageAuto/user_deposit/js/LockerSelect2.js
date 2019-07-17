/**
 * 
 */


//选择锁匠，电话/id/名称
function initLockerQuerySelect(eleid,orderid) {

	var input="";
	$("#"+eleid).select2({
		// dropdownParent : $("#myModal"),
		placeholder : "选择锁匠(ID/姓名/手机号模糊匹配)",
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

				input=params.term;
				var query = {
						querykey : params.term,
					pageNum : page,
					dataState:1,
					pageSize : 10,
					
					
					dataState:1,
					//parterquery:1//合伙人
	        		//userType:2, //合伙人
	        		auditFlag:1,//通过

				}
				// (cids == null || cids.length == 0) ? "": cids[0]
				// Query parameters will be ?search=[term]&type=public
				return query;
			},

			processResults : function(data, params) {
				var selectdatas = [];
				
				 if(typeof(params.page)=="undefined" && typeof(input)=="undefined" ) 
					 selectdatas.push({ id :
				 "-1", text : '全选',phone:'' });
				 

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

		templateResult : queryformatRepoLockHehuo2,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		templateSelection : function(data, container) {
			var id = data.id;

			var html = "" + data.text;
			
		//	if (id)
			//	html = "" + data.text + "&nbsp;&nbsp;(" + id + ") ";

			// $(container).parents("tr").find(".attr_id").val(data.id);
			return "<span phone='"+data.phone+"'>"+html+"</span>";
			
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

function queryformatRepoLockHehuo2(repo) {
	if (repo.loading) {
		return repo.text;
	}
	
	if(repo.id==-1)
		 return " <div class=\"lock-item in-line\"> "
			+ "<div class=\"lockname col-xs-12\">" + repo.text + " </div> "

	
	
	var markup = " <div class=\"lock-item in-line\"> "
			+ "<div class=\"lockname col-xs-12\">" + repo.text + "("+repo.id+") </div> "
			//+ "  <div class=\"lockid col-xs-6\">(" + repo.phone + ")</div>"

	"</div> ";

	return markup;
}


