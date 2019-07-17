
/**
 * 初始化公司选择框
 */
function initLockCompanySelect(selectId, inputId) {

    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#" + selectId).append(
                "<option value='" + data[i].id + "'>" + data[i].enterpriseName + "</option>"
            )
        }
        $("#" + selectId).val($("#" + inputId ).val());
        $("#" + selectId).selectpicker('refresh')
    };
    sendRequest(getRPath() + "/manager/tlockcompany/allCompany", {}, callback);

    //下拉菜单选中事件，获取工程id
    $("#" + selectId).on("change", function () {
        $("#" + inputId ).val($("#" + selectId).val())
    })
}

/**
 * 初始化锁匠选择框
 */
function initAllocateSelect() {

    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#lockerSelect").append(
                "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
            )
        }
        $("#lockerSelect").selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/tuserinfo/selecttuserinfo", {}, callback)

    //下拉菜单选中事件，获取工程id
    $("#lockerSelect").on("change", function () {
        $("#lockerId").val($("#lockerSelect").val())
    })
}

/**
 * 查询锁匠列表
 */
function initManagerLockSmithSelect() {
    $("#mform1").find("#lockerSelect").select2({
        placeholder : "选择锁匠",
        minimumInputLength : 0,
        maximumSelectionLength : 1,
        theme : "bootstrap",
        multiple : true,
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
                return "载入更多结果…"
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
            url : getRPath()+"/manager/tuserinfo/tuserinfoList",
            dataType : "json",
            data : function(params) {
                var page = params.page || 0;
                var query = {
                    name : params.term,
                    pageNum : page,
                    pageSize : 6,
                    dataState:1,
                    auditFlag:1
                }
                //(cids == null || cids.length == 0) ? "": cids[0]
                // Query parameters will be ?search=[term]&type=public
                return query;
            },

            processResults : function(data, params) {
                var selectdatas = [];
                /*
                 * if(typeof(params.page)=="undefined") selectdatas.push({ id :
                 * -1, text : '新建属性' });
                 */

                /*  selectdatas.push({
                      id : '0',
                      text : "系统"
                  });
  */

                $.each(data.rows, function(index, item) {
                    selectdatas.push({
                        id : item.id,
                        text : item.name
                    });
                });

                return {
                    results : selectdatas,
                    pagination : {
                        more : (params.page * 6 >= data.total) ? false : true
                    }
                };
            },
        },

        templateResult : formatRepo,
        escapeMarkup : function(markup) {
            return markup;
        }, // let our custom formatter work
        templateSelection : function(data, container) {
            // Add custom attributes to the <option> tag for the selected option
            // $(data.element).attr('data-custom-attribute', data.customValue);

            // $(container).parents("tr").find(".attr_id").val(data.id);
            return data.text;
        },

    });
}

/**
 * 查询锁企列表
 */
function initManagerLockCompanySelect() {
    $("#mform1").find("#enterpriseSelect").select2({
        placeholder : "选择锁企",
        minimumInputLength : 0,
        maximumSelectionLength : 1,
        theme : "bootstrap",
        multiple : true,
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
                return "载入更多结果…"
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
            url : getRPath()+"/manager/tlockcompany/tlockCompanyList",
            dataType : "json",
            data : function(params) {
                var page = params.page || 0;
                var query = {
                    enterpriseName : params.term,
                    pageNum : page,
                    pageSize : 6,
                    dataState:1,
                    auditFlag:1
                }
                //(cids == null || cids.length == 0) ? "": cids[0]
                // Query parameters will be ?search=[term]&type=public
                return query;
            },

            processResults : function(data, params) {
                var selectdatas = [];
                /*
                 * if(typeof(params.page)=="undefined") selectdatas.push({ id :
                 * -1, text : '新建属性' });
                 */

                /*  selectdatas.push({
                      id : '0',
                      text : "系统"
                  });
  */

                $.each(data.rows, function(index, item) {
                    selectdatas.push({
                        id : item.id,
                        text : item.enterpriseName
                    });
                });

                return {
                    results : selectdatas,
                    pagination : {
                        more : (params.page * 6 >= data.total) ? false : true
                    }
                };
            },
        },

        templateResult : formatRepo,
        escapeMarkup : function(markup) {
            return markup;
        }, // let our custom formatter work
        templateSelection : function(data, container) {
            // Add custom attributes to the <option> tag for the selected option
            // $(data.element).attr('data-custom-attribute', data.customValue);

            // $(container).parents("tr").find(".attr_id").val(data.id);
            return data.text;
        },

    });
}

function formatRepo(repo) {
    if (repo.loading) {
        return repo.text;
    }

    var markup = repo.text;

    /*if (repo.id == -1) {
        markup = '<button type="button "  style=" width: 100%;" class=" btn btn-danger btn-warning " data-dismiss="modal ">'
                + repo.text + '</button>';
    }*/

    return markup;
}
