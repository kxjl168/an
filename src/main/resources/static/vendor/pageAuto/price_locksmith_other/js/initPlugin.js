
/**
 * 初始化锁企选择框
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
    }
    sendRequest(getRPath() + "/manager/tlockcompany/allCompany", {}, callback)

    //下拉菜单选中事件，获取工程id
    $("#" + selectId).on("change", function () {
        $("#" + inputId ).val($("#" + selectId).val())
    })
}