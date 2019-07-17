/**
 * 初始化锁企选择框
 */
function initLockCompanySelect(selectId, inputId) {
	
	//return initLockCompanySelect2(selectId);
	
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#" + selectId).append(
                "<option value='" + data[i].id + "'>" + data[i].enterpriseName + "</option>"
            )
        }
        $("#" + selectId).val($("#" + inputId).val());
        $("#" + selectId).selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/tlockcompany/allCompany", {}, callback)

    //下拉菜单选中事件，获取工程id
    $("#" + selectId).on("change", function () {
        $("#" + inputId).val($("#" + selectId).val())
    })
}

/**
 * 查询锁企列表
 */
function initLockCompanySelect2(selectId) {
    $("#" + selectId).select2({
        placeholder: "请选择锁企",
        minimumInputLength: 0,
        maximumSelectionLength: 1,
        theme: "bootstrap",
        multiple: false,
        language: {
            errorLoading: function () {
                return "无法载入结果。"
            },
            inputTooLong: function (e) {
                var t = e.input.length - e.maximum, n = "请删除" + t + "个字符";
                return n
            },
            inputTooShort: function (e) {
                var t = e.minimum - e.input.length, n = "请再输入至少" + t + "个字符";
                return n
            },
            loadingMore: function () {
                return "载入更多结果…"
            },
            maximumSelected: function (e) {
                var t = "";// "最多只能选择" + e.maximum + "个";
                return t
            },
            noResults: function () {
                return "未找到结果"
            },
            searching: function () {
                return "搜索中…"
            }
        },
        ajax: {
            type: "post",
            url: getRPath() + "/manager/tlockcompany/tlockCompanyList",
            dataType: "json",
            data: function (params) {
                var page = params.page || 0;
                var query = {
                    enterpriseName: params.term,
                    pageNum: page,
                    pageSize: 10,
                    dataState: 1,
                    auditFlag: 1,
                }
                return query;
            },
            processResults: function (data, params) {
                var selectdatas = [];
                $.each(data.rows, function (index, item) {
                    selectdatas.push({
                        id: item.id,
                        text: item.enterpriseName
                    });
                });
                return {
                    results: selectdatas,
                    pagination: {
                        more: (params.page * 10 >= data.total) ? false : true
                    }
                };
            },
        },
        templateResult: formatRepo,
        escapeMarkup: function (markup) {
            return markup;
        },
        templateSelection: function (data, container) {
            return data.text;
        },
    });
    function formatRepo(repo) {
        if (repo.loading) {
            return repo.text;
        }
        var markup = repo.text;
        return markup;
    }
}