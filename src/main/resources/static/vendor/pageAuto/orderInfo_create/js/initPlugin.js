/**
 * 初始化工程选择框
 */
var projectList = []


/**
 * 逻辑：先选择工程，则公司确定，公司下拉框禁用；先选择公司，则工程下拉框为该公司的工程
 * @param selectId
 * @param inputId
 * @param companyId
 */
function initProjectSelect(selectId, inputId, companyId, companySelectId, companyInputId) {
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            projectList = data
            $("#" + selectId).append(
                "<option value='" + data[i].id + "'>" + data[i].projectName + "</option>"
            )
        }
        $("#" + selectId).selectpicker('refresh')
    }
    var parameter = {}
    if (companyId != null) {
        parameter.companyId = companyId
    }
    sendRequest(getRPath() + "/manager/tproject/allProject", parameter, callback)

    var events = $._data($("#" + selectId)[0], "events")
    if (events.change.length <= 1) {
        //下拉菜单选中事件，获取工程id
        $("#" + selectId).on("change", function () {
            var id = $("#" + selectId).val()
            $("#" + inputId).val(id)
            for (var i = 0; i < projectList.length; i++) {
                if (id == projectList[i].id) {
                    $("#customerPhone").val(projectList[i].custPhone)

                    $("#customerName").val(projectList[i].custName)

                    $("#addressDetail").val(projectList[i].addressDetail)

                    $("#" + companyInputId).val(projectList[i].companyId)
                    if ($("#" + companySelectId).length > 0) {
                        if (projectList[i].companyId) {
                            $("#" + companySelectId).selectpicker("val", projectList[i].companyId)
                        } else {
                            $("#" + companySelectId).selectpicker("val", "-1")
                        }
                    }
                    $("#" + companySelectId).prop("disabled", true)

                    $("#provinceSelect").selectpicker("val", projectList[i].areaCode.substr(0, 2))
                    $("#provinceSelect").trigger("change")

                    $("#citySelect").selectpicker("val", projectList[i].areaCode.substr(2, 2))
                    $("#citySelect").trigger("change")

                    $("#districtSelect").selectpicker("val", projectList[i].areaCode.substr(4, 2))
                    $("#districtSelect").trigger("change")

                    break
                }
            }
        })
    }
}

/**
 * 初始化公司选择框
 */
function initCompanySelect(selectId, inputId) {

    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#" + selectId).append(
                "<option value='" + data[i].id + "'>" + data[i].companyName + "</option>"
            )
        }
        $("#" + selectId).selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/tcompany/allCompany", {}, callback)

    var events = $._data($("#" + selectId)[0], "events")
    if (events.change.length <= 1) {
        //下拉菜单选中事件，获取工程id
        $("#" + selectId).on("change", function () {
            var companyId = $("#" + selectId).val()
            $("#" + inputId).val($("#" + selectId).val())
            //渲染城市下拉框
            $("#projectSelect").empty()
            initProjectSelect("projectSelect", "projectId", companyId)
        })
    }
}

/**
 * 初始化锁匠选择框
 */
function initAllocateSelect(orderId) {

    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#lockerSelect").append(
                "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
            )
        }
        $("#lockerSelect").selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/tuserinfo/selectLockerByOrderId", {"id": orderId}, callback)

    var events = $._data($("#lockerSelect")[0], "events")
    if (events.change.length <= 1) {
        //下拉菜单选中事件，获取工程id
        $("#lockerSelect").on("change", function () {
            $("#lockerId").val($("#lockerSelect").val())
        })
    }
}

/**
 * 初始化日期选择器
 */
function initDataTimePicker(itemId) {
    $('#' + itemId).datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        todayBtn: true,
        language: 'zh-CN',
        autoclose: true
    });
}

/**
 * 初始化省选择框
 */
function initProvinceSelect(provinceSelectId, citySelectId, districtSelectId,
                            provinceCodeId, cityCodeId, districtCodeId) {

    var provinceSelect = $("#" + provinceSelectId)
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            provinceSelect.append(
                "<option value='" + data[i].provinceCode + "' id='" + data[i].id + "'>" + data[i].provinceName + "</option>"
            )
        }
        provinceSelect.selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/area/allProvince", {}, callback)

    var events = $._data(provinceSelect[0], 'events');
    if (events.change.length <= 1) {
        //下拉菜单选中事件，获取工程id
        provinceSelect.on("change", function () {
            //为地区代码赋值
            $("#" + provinceCodeId).val(provinceSelect.val())

            //渲染城市下拉框
            $("#" + citySelectId).empty()
            $("#" + districtSelectId).empty()
            var selectedProvinceId = $("#" + provinceSelectId + " option:selected").attr("id")
            initCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId, selectedProvinceId)
            initDistrictSelect(districtSelectId, districtCodeId)
            $("#" + cityCodeId).val("")
            $("#" + districtCodeId).val("")
        })
    }
}

/**
 * 初始化市选择器
 */
function initCitySelect(citySelectId, districtSelectId, cityCodeId, districtCodeId, provinceId) {

    var citySelect = $("#" + citySelectId)
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            if (data[i].cityName == "重庆市") {
                if (data[i].cityCode == "01") {
                    data[i].cityName = data[i].cityName + "（区）"
                } else {
                    data[i].cityName = data[i].cityName + "（县）"
                }
            }
            citySelect.append(
                "<option value='" + data[i].cityCode + "' id='" + data[i].id + "'>" + data[i].cityName + "</option>"
            )
        }
        citySelect.selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/area/provinceAllCity", {"id": provinceId}, callback)

    var events = $._data(citySelect[0], 'events');
    if (events.change.length <= 1) {
        //下拉菜单选中事件，获取城市id
        citySelect.on("change", function () {
            //为地区代码赋值
            $("#" + cityCodeId).val(citySelect.val())

            //初始化区县选择器
            $("#" + districtSelectId).empty()
            var selectedCityId = $("#" + citySelectId + " option:selected").attr("id")
            initDistrictSelect(districtSelectId, districtCodeId, selectedCityId)
            $("#" + districtCodeId).val("")
        })
    }
}

/**
 * 初始化区县选择器
 */
function initDistrictSelect(districtSelectId, districtCodeId, cityId) {

    var districtSelect = $("#" + districtSelectId)
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            districtSelect.append(
                "<option value='" + data[i].districtCode + "' id='" + data[i].id + "'>" + data[i].districtName + "</option>"
            )
        }
        districtSelect.selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/area/cityAllDistrict", {"id": cityId}, callback)

    var events = $._data(districtSelect[0], 'events');
    if (events.change.length <= 1) {
        //下拉菜单选中事件，获取城市id
        districtSelect.on("change", function () {
            //为地区代码赋值
            $("#" + districtCodeId).val(districtSelect.val())
        })
    }
}

function initFileInput() {
    $("#excelFile").fileinput({
        language: "zh",//设置语言
        showCaption: true,//是否显示标题
        showUpload: false, //是否显示上传按钮
        allowedFileExtensions: ["xls", "xlsx"], //接收的文件后缀
        maxFileCount: 1,//最大上传文件数限制
        maxFileSize: 1000,//最大上传文件大小
        previewFileIcon: '<i class="glyphicon glyphicon-file"></i>',
        allowedPreviewTypes: null,
        previewFileIconSettings: {
            'doc': '<i class="fa fa-file-word-o text-primary"></i>',
            'xls': '<i class="fa fa-file-excel-o text-success"></i>',
            'ppt': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
            'jpg': '<i class="fa fa-file-photo-o text-warning"></i>',
            'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
            'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
            'htm': '<i class="fa fa-file-code-o text-info"></i>',
            'txt': '<i class="fa fa-file-text-o text-info"></i>',
            'mov': '<i class="fa fa-file-movie-o text-warning"></i>',
            'mp3': '<i class="fa fa-file-audio-o text-warning"></i>',
        },
        previewFileExtSettings: {
            'doc': function (ext) {
                return ext.match(/(doc|docx)$/i);
            },
            'xls': function (ext) {
                return ext.match(/(xls|xlsx)$/i);
            },
            'ppt': function (ext) {
                return ext.match(/(ppt|pptx)$/i);
            },
            'zip': function (ext) {
                return ext.match(/(zip|rar|tar|gzip|gz|7z)$/i);
            },
            'htm': function (ext) {
                return ext.match(/(php|js|css|htm|html)$/i);
            },
            'txt': function (ext) {
                return ext.match(/(txt|ini|md)$/i);
            },
            'mov': function (ext) {
                return ext.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i);
            },
            'mp3': function (ext) {
                return ext.match(/(mp3|wav)$/i);
            },
        }
    });
}