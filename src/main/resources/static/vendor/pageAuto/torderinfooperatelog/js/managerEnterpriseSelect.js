/**
 * 房间界面 select2相关
 */

//查询条件 小区选择select
function initManagerCompanySelect() {

    $("#mform").find("#lockEnterpriseID").select2({
        // dropdownParent : $("#myModal"),
        placeholder: "选择代理人/合伙人",
        minimumInputLength: 0,
        maximumSelectionLength: 1,
        theme: "bootstrap",
        multiple: true,
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
            url: getRPath() + "/manager/tcompany/tcompanyList",
            dataType: "json",
            data: function (params) {

                var page = params.page || 0;

                var query = {
                    companyName: params.term,
                    pageNum: page,

                    pageSize: 10,
                    dataState: 1,
                    auditFlag: 1,
                }
                //(cids == null || cids.length == 0) ? "": cids[0]
                // Query parameters will be ?search=[term]&type=public
                return query;
            },

            processResults: function (data, params) {
                var selectdatas = [];
                /*
                 * if(typeof(params.page)=="undefined") selectdatas.push({ id :
                 * -1, text : '新建属性' });
                 */

                /*selectdatas.push({
                    id : '0',
                    text : "系统"
                });*/


                $.each(data.rows, function (index, item) {
                    selectdatas.push({
                        id: item.id,
                        text: item.companyName
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
        }, // let our custom formatter work
        templateSelection: function (data, container) {
            // Add custom attributes to the <option> tag for the selected option
            // $(data.element).attr('data-custom-attribute', data.customValue);

            // $(container).parents("tr").find(".attr_id").val(data.id);
            return data.text;
        },

    });

    // Bind an event
    $('#q_room_community').on('select2:select', function (e) {
        // alert("11");
        //$("#q_room_building").show();
        //destoryRoomQ_SelectBuildingquery();
        //initRoomQ_SelectBuildingquery();
    });

    $('#q_room_community').on('select2:unselect', function (e) {
        // alert("11");
        //destoryRoomQ_SelectBuildingquery();
        //initRoomQ_SelectBuildingquery();

    });

}

/**
 * 查询锁企列表
 */
function initManagerLockCompanySelect() {
    $("#companyIdView").find("#lockEnterpriseID").select2({
        // dropdownParent : $("#myModal"),
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
                    pageSize : 10,
                    dataState:1,
                    auditFlag:1,
                }

                return query;
            },

            processResults : function(data, params) {
                var selectdatas = [];

                $.each(data.rows, function(index, item) {
                    selectdatas.push({
                        id : item.id,
                        text : item.enterpriseName
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


function destoryRoomQ_SelectBuildingquery() {
    //$("#q_room_building").select2().val(null).trigger("change");
    //$("#q_room_building").select2("destroy");
}

