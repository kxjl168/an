/**
 * 
 */

/**
 * 初始化日期选择器
 */
function initDataTimePicker(itemId,min,max) {
   
	
	$('#' + itemId).datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        todayBtn: true,
        language: 'zh-CN',
        autoclose: true,
        clearBtn: true,
        hourmin:min?min:0,
        hourmax:max?max:23,    
    });
}