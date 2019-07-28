/**
 * 
 */

/**
 * 初始化日期选择器
 */
function initDataTimePicker(itemId, min, max) {

	$('#' + itemId).datetimepicker({
		format : 'yyyy-mm-dd hh:ii',
		todayBtn : true,
		language : 'zh-CN',
		autoclose : true,
		clearBtn : true,
		hourmin : min ? min : 0,
		hourmax : max ? max : 23,
	});
}

function queryformatCommon(repo) {
	if (repo.loading) {
		return repo.text;
	}

	return repo.text;
}

/**
 * 单位、片区-坐席group select2
 * 
 * @param eleid
 * @returns
 */
function initUnitAreaSelect(eleid, level) {

	if (typeof (level) == "undefined")
		level = "2";

	var tip = "选择片区";
	if (level == "3")
		tip = "选择坐席";
	/*
	 * if(dictType=='2') tip="选择案件等级"; else if(dictType=='3') tip="选择常用语";
	 */

	var input = "";
	$("#" + eleid).select2({
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
			url : getRPath() + "/manager/tareainfo/AreaTypeList",
			dataType : "json",
			data : function(params) {

				var page = params.page || 1;

				input = params.term;
				var query = {
					level : level,
					name : params.term,
					pageNum : page,
					dataState : 1,
					pageSize : 10,

				}
				// (cids == null || cids.length == 0) ? "": cids[0]
				// Query parameters will be ?search=[term]&type=public
				return query;
			},

			processResults : function(data, params) {
				var selectdatas = [];

				/*
				 * if(typeof(params.page)=="undefined" &&
				 * typeof(input)=="undefined" ) selectdatas.push({ id : "-1",
				 * text : '全选',phone:'' });
				 */

				/*
				 * selectdatas.push({ id : '0', text : "系统" });
				 */

				$.each(data, function(index, itemstr) {

					item = eval("(" + itemstr + ")");

					var clds22 = eval(item.child);
					var clditems2 = [];

					$.each(clds22, function(index22, item22) {

						var clds = eval(item22.unitListStr);

						var clditems = [];

						$.each(clds, function(index2, item2) {

							if (level == "2") {
								clditems.push({
									id : item2.id,
									text : item2.name,
								// icon: item.httppath+ item2.val1,
								});
							} else {

								var cld2s = eval(item2.seatListStr);

								var clditem2s = [];

								$.each(cld2s, function(index3, item3) {
									clditem2s.push({
										id : item3.id,
										text : item3.name,
									// icon: item.httppath+ item2.val1,
									});
								});

								clditems.push({

									text : item2.name,
									children : clditem2s,

								});
							}

						});

						clditems2.push({

							text : item22.name,
							children : clditems,

						});

					});

					selectdatas.push({

						text : item.name,
						children : clditems2,

					});
				});

				return {
					results : selectdatas,
					pagination : {
						more : false
					// (params.page * 10 >= data.total) ? false
					// : true
					}
				};
			},
		},

		templateResult : queryformatCommon,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		templateSelection : function(data, container) {
			var id = data.id;

			var html = "" + data.text;

			// if (id)
			// html = "" + data.text + "&nbsp;&nbsp;(" + id + ") ";

			// $(container).parents("tr").find(".attr_id").val(data.id);
			// return "<span phone='"+data.phone+"'>"+html+"</span>";

			return data.text;
		},

	});

}

function initUnitSelect(eleid) {

	var tip = "选择接警单位";
	/*
	 * if(dictType=='2') tip="选择案件等级"; else if(dictType=='3') tip="选择常用语";
	 */

	var input = "";
	$("#" + eleid).select2({
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
			url : getRPath() + "/manager/tunitinfo/tunitinfoList",
			dataType : "json",
			data : function(params) {

				var page = params.page || 1;

				input = params.term;
				var query = {

					name : params.term,
					pageNum : page,
					dataState : 1,
					pageSize : 10,

				}
				// (cids == null || cids.length == 0) ? "": cids[0]
				// Query parameters will be ?search=[term]&type=public
				return query;
			},

			processResults : function(data, params) {
				var selectdatas = [];

				/*
				 * if(typeof(params.page)=="undefined" &&
				 * typeof(input)=="undefined" ) selectdatas.push({ id : "-1",
				 * text : '全选',phone:'' });
				 */

				/*
				 * selectdatas.push({ id : '0', text : "系统" });
				 */

				$.each(data.rows, function(index, item) {
					selectdatas.push({
						id : item.id,
						text : item.name,

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

		templateResult : queryformatCommon,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		templateSelection : function(data, container) {
			var id = data.id;

			var html = "" + data.text;

			// if (id)
			// html = "" + data.text + "&nbsp;&nbsp;(" + id + ") ";

			// $(container).parents("tr").find(".attr_id").val(data.id);
			// return "<span phone='"+data.phone+"'>"+html+"</span>";

			return data.text;
		},

	});

}

function initAreaSelect(eleid, unitid) {

	var tip = "选择片区";
	/*
	 * if(dictType=='2') tip="选择案件等级"; else if(dictType=='3') tip="选择常用语";
	 */

	var input = "";
	$("#" + eleid).select2({
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
			url : getRPath() + "/manager/tareainfo/tareainfoList",
			dataType : "json",
			data : function(params) {

				var page = params.page || 1;

				input = params.term;
				var query = {

					name : params.term,
					pageNum : page,
					dataState : 1,
					pageSize : 10,

				}

				if (typeof (unitid) != 'undefined')
					query.unitId = unitid;

				// (cids == null || cids.length == 0) ? "": cids[0]
				// Query parameters will be ?search=[term]&type=public
				return query;
			},

			processResults : function(data, params) {
				var selectdatas = [];

				/*
				 * if(typeof(params.page)=="undefined" &&
				 * typeof(input)=="undefined" ) selectdatas.push({ id : "-1",
				 * text : '全选',phone:'' });
				 */

				/*
				 * selectdatas.push({ id : '0', text : "系统" });
				 */

				$.each(data.rows, function(index, item) {
					selectdatas.push({
						id : item.id,
						text : item.name,

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

		templateResult : queryformatCommon,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		templateSelection : function(data, container) {
			var id = data.id;

			var html = "" + data.text;

			// if (id)
			// html = "" + data.text + "&nbsp;&nbsp;(" + id + ") ";

			// $(container).parents("tr").find(".attr_id").val(data.id);
			// return "<span phone='"+data.phone+"'>"+html+"</span>";

			return data.text;
		},

	});

}

function initAlarmTypeSelect(eleid, dictType) {

	var tip = "选择案件类型";
	if (dictType == '2')
		tip = "选择案件等级";
	else if (dictType == '3')
		tip = "选择常用语";

	var input = "";
	$("#" + eleid).select2({
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
			url : getRPath() + "/manager/sysdictinfo/sysdictinfoList",
			dataType : "json",
			data : function(params) {

				var page = params.page || 1;

				input = params.term;
				var query = {
					type : dictType,
					dictName : params.term,
					pageNum : page,
					dataState : 1,
					pageSize : 10,

				}
				// (cids == null || cids.length == 0) ? "": cids[0]
				// Query parameters will be ?search=[term]&type=public
				return query;
			},

			processResults : function(data, params) {
				var selectdatas = [];

				/*
				 * if(typeof(params.page)=="undefined" &&
				 * typeof(input)=="undefined" ) selectdatas.push({ id : "-1",
				 * text : '全选',phone:'' });
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

		templateResult : queryformatCommon,
		escapeMarkup : function(markup) {
			return markup;
		}, // let our custom formatter work
		templateSelection : function(data, container) {
			var id = data.id;

			var html = "" + data.text;

			// if (id)
			// html = "" + data.text + "&nbsp;&nbsp;(" + id + ") ";

			// $(container).parents("tr").find(".attr_id").val(data.id);
			// return "<span phone='"+data.phone+"'>"+html+"</span>";

			return data.text;
		},

	});

}

function initPersonSelect(eleid) {

	var tip = "选择接警人";
	/*
	 * if(dictType=='2') tip="选择案件等级"; else if(dictType=='3') tip="选择常用语";
	 */

	var input = "";
	$("#" + eleid)
			.select2(
					{
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
							url : getRPath()
									+ "/manager/treceivepoliceinfo/treceivepoliceinfoList",
							dataType : "json",
							data : function(params) {

								var page = params.page || 1;

								input = params.term;
								var query = {

									name : params.term,
									pageNum : page,
									dataState : 1,
									pageSize : 10,

								}
								// (cids == null || cids.length == 0) ? "":
								// cids[0]
								// Query parameters will be
								// ?search=[term]&type=public
								return query;
							},

							processResults : function(data, params) {
								var selectdatas = [];

								/*
								 * if(typeof(params.page)=="undefined" &&
								 * typeof(input)=="undefined" )
								 * selectdatas.push({ id : "-1", text :
								 * '全选',phone:'' });
								 */

								/*
								 * selectdatas.push({ id : '0', text : "系统" });
								 */

								$.each(data.rows, function(index, item) {
									selectdatas.push({
										id : item.id,
										text : item.name,

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

						templateResult : queryformatCommon,
						escapeMarkup : function(markup) {
							return markup;
						}, // let our custom formatter work
						templateSelection : function(data, container) {
							var id = data.id;

							var html = "" + data.text;

							// if (id)
							// html = "" + data.text + "&nbsp;&nbsp;(" + id + ")
							// ";

							// $(container).parents("tr").find(".attr_id").val(data.id);
							// return "<span
							// phone='"+data.phone+"'>"+html+"</span>";

							return data.text;
						},

					});

}
