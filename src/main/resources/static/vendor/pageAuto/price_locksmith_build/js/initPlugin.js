
/**
 * 初始化省选择框
 */
function initProvinceSelect(provinceSelectId,provinceCodeId,citySelectId,cityCodeId) {
    var provinceSelect = $("#"+provinceSelectId)
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
        $("#"+provinceCodeId).val(provinceSelect.val())
        //渲染城市下拉框
        $("#"+citySelectId).empty()
        var selectedProvinceId = $("#" + provinceSelectId + " option:selected").attr("id")
        initCitySelect(selectedProvinceId,citySelectId,cityCodeId)
    })
    
    setTimeout(function(){
 	   $("#"+provinceSelectId).trigger("change");
 		}, 50);
}

/**
 * 初始化市选择器
 */
function initCitySelect(provinceId,citySelectId,cityCodeId) {
    var citySelect = $("#"+citySelectId)
    
    
    var callback = function (data) {
    	
    	
    	  citySelect.append(
                  "<option value='00' id=''>默认地市</option>"
              )
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
    //下拉菜单选中事件，获取城市id
    citySelect.on("change", function () {
        //为地区代码赋值
        $("#"+cityCodeId).val(citySelect.val())
        $("#" + citySelectId + " option:selected").attr("id")
    })
}

/**
 * 初始化市选择器
 */
function initUpdateCitySelect(provinceId,citySelectId,cityCodeId) {
    var citySelect = $("#"+citySelectId)
    var callback = function (data) {
    	
  	  citySelect.append(
              "<option value='' id=''>默认地市</option>"
          )
    	
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
    sendRequest(getRPath() + "/manager/area/selectCityListByProvinceCode", {"provinceCode" : provinceId}, callback)
    //下拉菜单选中事件，获取城市id
    citySelect.on("change", function () {
        //为地区代码赋值
        $("#"+cityCodeId).val(citySelect.val())
        $("#" + citySelectId + " option:selected").attr("id")
    })
}