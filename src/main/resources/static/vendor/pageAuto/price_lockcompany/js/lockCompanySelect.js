/**
 * 
 */


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

		templateResult : formatRepoLockHehuo2,
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

function formatRepoLockHehuo2(repo) {
	if (repo.loading) {
		return repo.text;
	}

	var markup = " <div class=\"lock-item in-line\"> "
			+ "<div class=\"lockname col-xs-6\">" + repo.text + " </div> "
			+ "  <div class=\"lockid col-xs-6\">(" + repo.phone + ")</div>"

	"</div> ";

	return markup;
}

