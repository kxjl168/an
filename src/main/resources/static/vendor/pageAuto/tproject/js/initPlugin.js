/**
 * 初始化工程选择框
 */
function initProjectSelect(selectId, inputId) {
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            $("#" + selectId).append(
                "<option value='" + data[i].id + "'>" + data[i].projectName + "</option>"
            )
        }
        $("#" + selectId).selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/tproject/allProject", {}, callback)

    //下拉菜单选中事件，获取工程id
    $("#" + selectId).on("change", function () {
        $("#" + inputId).val($("#" + selectId).val())
    })
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
        $("#" + selectId).val($("#" + inputId ).val());
        $("#" + selectId).selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/tcompany/allCompany", {}, callback)

    //下拉菜单选中事件，获取工程id
    $("#" + selectId).on("change", function () {
        $("#" + inputId ).val($("#" + selectId).val())
    })
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
    sendRequest(getRPath() + "/manager/tuserinfo/selectLockerByOrderId", {"id" : orderId}, callback)

    //下拉菜单选中事件，获取工程id
    $("#lockerSelect").on("change", function () {
        $("#lockerId").val($("#lockerSelect").val())
    })
}

/**
 * 初始化日期选择器
 */
function initDataTimePicker() {
    $('#appointmentTime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        todayBtn: true,
        language: 'zh-CN',
        autoclose: true
    });
}

/**
 * 初始化省选择框
 */
function initProvinceSelect() {
    var provinceSelect = $("#provinceSelect")
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            provinceSelect.append(
                "<option value='" + data[i].provinceCode + "' id='" + data[i].id + "'>" + data[i].provinceName + "</option>"
            )
        }
        provinceSelect.selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/area/allProvince", {}, callback)

    //下拉菜单选中事件，获取工程id
    provinceSelect.on("change", function () {
        //为地区代码赋值
        $("#provinceCode").val(provinceSelect.val())

        //渲染城市下拉框
        $("#citySelect").empty()
        $("#districtSelect").empty()
        var selectedProvinceId = $("#provinceSelect option:selected").attr("id")
        initCitySelect(selectedProvinceId)
        initDistrictSelect()
    })
}

/**
 * 初始化市选择器
 */
function initCitySelect(provinceId) {
    var citySelect = $("#citySelect")
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
    sendRequest(getRPath() + "/manager/area/provinceAllCity", {"id" : provinceId}, callback)
    $("#cityCode").val("")
    //下拉菜单选中事件，获取城市id
    citySelect.on("change", function () {
        //为地区代码赋值
        $("#cityCode").val(citySelect.val())

        //初始化区县选择器
        $("#districtSelect").empty()
        var selectedCityId = $("#citySelect option:selected").attr("id")
        initDistrictSelect(selectedCityId)
    })
}

/**
 * 初始化区县选择器
 */
function initDistrictSelect(cityId) {

    var districtSelect = $("#districtSelect")
    var callback = function (data) {
        for (var i = 0; i < data.length; i++) {
            districtSelect.append(
                "<option value='" + data[i].districtCode + "' id='" + data[i].id + "'>" + data[i].districtName + "</option>"
            )
        }
        districtSelect.selectpicker('refresh')
    }
    sendRequest(getRPath() + "/manager/area/cityAllDistrict", {"id" : cityId}, callback)
    $("#districtCode").val("")
    //下拉菜单选中事件，获取城市id
    districtSelect.on("change", function () {
        //为地区代码赋值
        $("#districtCode").val(districtSelect.val())
    })
}

function initFileInput() {
    $("#excelFile").fileinput({
        language : "zh",//设置语言
        showCaption: true,//是否显示标题
        showUpload: false, //是否显示上传按钮
        allowedFileExtensions: ["xls","xlsx"], //接收的文件后缀
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
            'doc': function(ext) {
                return ext.match(/(doc|docx)$/i);
            },
            'xls': function(ext) {
                return ext.match(/(xls|xlsx)$/i);
            },
            'ppt': function(ext) {
                return ext.match(/(ppt|pptx)$/i);
            },
            'zip': function(ext) {
                return ext.match(/(zip|rar|tar|gzip|gz|7z)$/i);
            },
            'htm': function(ext) {
                return ext.match(/(php|js|css|htm|html)$/i);
            },
            'txt': function(ext) {
                return ext.match(/(txt|ini|md)$/i);
            },
            'mov': function(ext) {
                return ext.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i);
            },
            'mp3': function(ext) {
                return ext.match(/(mp3|wav)$/i);
            },
        }
    });
}