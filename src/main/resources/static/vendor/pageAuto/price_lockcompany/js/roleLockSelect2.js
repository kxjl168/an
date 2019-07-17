/**
 * 楼栋界面 select2相关
 */

//查询条件 小区选择select
function initLockSelect() {

	$("#st_lock").select2(
			{
				// dropdownParent : $("#myModal"),
				placeholder : "选择门锁",
				minimumInputLength : 0,
				//maximumSelectionLength : 1,
				theme : "bootstrap",
				multiple : true,
				language : {
					errorLoading : function() {
						return "无法载入结果。"
					},
					inputTooLong : function(e) {
						var t = e.input.length - e.maximum, n = "请删除"
								+ t + "个字符";
						return n
					},
					inputTooShort : function(e) {
						var t = e.minimum - e.input.length, n = "请再输入至少"
								+ t + "个字符";
						return n
					},
					loadingMore : function() {
						return "";// "载入更多结果…"
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
					url : getRPath()+"/manager/lock/lockKeyList",
					dataType : "json",
					data : function(params) {

						var page = params.page || 0;

						//var cids = $("#q_room_building").val();

						var query = {
							querykey : params.term,
							pagenum : page,

							pageSize : 10,
						
						}

						// Query parameters will be
						// ?search=[term]&type=public
						return query;
					},

					processResults : function(data, params) {
						var selectdatas = [];
						/*
						 * if(typeof(params.page)=="undefined")
						 * selectdatas.push({ id : -1, text : '新建属性' });
						 */

						
						if(typeof(params.page)=='undefined')
							params.page=1;
						
						if(params.page==1)
						selectdatas.push({ id :
							  -1, text : '全部' });
						
						$.each(data.rows, function(index, item) {
							selectdatas.push({
								id : item.id,
								text : item.name,
								imei:item.imei,
							});
						});

						return {
							results : selectdatas,
							pagination : {
								more : (params.page * 10 >= data.total) ? false
										: true
							}
						};
					},
				},

				templateResult : formatRepoLock,
				escapeMarkup : function(markup) {
					return markup;
				}, // let our custom formatter work
				templateSelection : function(data, container) {
					
					var id=data.imei;
					if(typeof(data.imei)=='undefined')
						id=$(data.element).attr("imei");
					
					var html = ""
						+ data.text ;
					if(id)
						html = ""
							+ data.text+"&nbsp;&nbsp;("+ id+") ";
						

					// $(container).parents("tr").find(".attr_id").val(data.id);
					return html;
				},

			});


}



function formatRepoLock(repo) {
	if (repo.loading) {
		return repo.text;
	}

	var markup = 	 " <div class=\"lock-item in-line\"> "
	+ "<div class=\"lockname col-xs-6\">"+repo.text + " </div> "
	+ "  <div class=\"lockid col-xs-6\">(IMEI:" + repo.imei+")</div>"

			"</div> ";
	


	if (repo.id == -1) {
	//	markup = '<button type="button "  style=" width: 100%;" class=" btn btn-danger btn-warning " data-dismiss="modal ">'
		//		+ repo.text + '</button>';
		 markup = 	 " <div class=\"lock-item in-line\"> "
				+ "<div class=\"lockname col-xs-6\">"+repo.text + " </div> "
				+ "  <div class=\"lockid col-xs-6\"></div>"

						"</div> ";
	}

	return markup;
}



function formatRepo(repo) {
	if (repo.loading) {
		return repo.text;
	}

	var markup = repo.text;

	if (repo.id == -1) {
		//markup = '<button type="button "  style=" width: 100%;" class=" btn btn-danger btn-warning " data-dismiss="modal ">'
		//		+ repo.text + '</button>';
		 markup = repo.text;
	}

	return markup;
}


