    <script src="${ctx}/vendor/jquery/jquery.js"></script>


<script src="${ctx}/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/vendor/jquery/jquery.dcjqaccordion.2.7.js"></script>
<script src="${ctx}/vendor/jquery/jquery.scrollTo.min.js"></script>
<script src="${ctx}/vendor/jquery/jquery.nicescroll.js"></script>
<script src="${ctx}/vendor/jquery/jquery.sparkline.js"></script>
<script src="${ctx}/vendor/blueSkin/common-scripts.js"></script>
<script src="${ctx}/vendor/bootstrapValidator/js/bootstrapValidator.js"></script>
<script src="${ctx}/vendor/bootstrap-table/js/bootstrap-table.js"></script>
<script src="${ctx}/vendor/bootstrap-table/js/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/vendor/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="${ctx}/vendor/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script src="${ctx}/vendor/bootstrap-treeview/js/bootstrap-treeview.min.js"></script>
<script src="${ctx}/vendor/bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="${ctx}/vendor/bootstrap-select/locales/defaults-zh_CN.min.js"></script>

<script src="${ctx}/vendor/distpicker/js/distpicker.data.js"></script>
<script src="${ctx}/vendor/distpicker/js/distpicker.js"></script>

<script src="${ctx}/vendor/sweetalert/js/sweetalert2.min.js"></script>

    <script src="${ctx}/js/iot.js"></script>


<link rel="stylesheet"
	href="${ctx}/vendor/sweetalert/css/sweetalert2.min.css" />



	<link rel="stylesheet"
	href="http://cache.amap.com/lbs/static/main1119.css" />
<link rel="stylesheet"
	href="https://cache.amap.com/lbs/static/AMap.TransferRender1120.css" />
	<link rel="stylesheet"
	href="http://cache.amap.com/lbs/static/main1119.css" />
<link rel="stylesheet"
	href="https://cache.amap.com/lbs/static/AMap.TransferRender1120.css" />
<script type="text/javascript"
	src="http://webapi.amap.com/maps?v=1.4.4&key=bd7c5e997b2ed26801eb5f9018c36962&plugin=AMap.ToolBar,AMap.Transfer,AMap.Geocoder,AMap.MouseTool,AMap.CitySearch"ֵ></script>

<script type="text/javascript"
	src="https://cache.amap.com/lbs/static/TransferRender1230.js"></script>
<script type="text/javascript"
	src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
<div class="col-lg-12">
		<div id="container" class="kmap"></div>
</div>


<script>
var map = new AMap.Map("container", {
	resizeEnable : true,
	zoom : 13
});


var clickEventListener = map.on('click', function(e) {
	var x= e.lnglat.getLng();
	var y= e.lnglat.getLat();
parent.setxy_c(x,y);
	
});




function markLocationC(mapId, address) {
    AMap.plugin('AMap.Geocoder', function() {
        var geocoder = new AMap.Geocoder();
        geocoder.getLocation(address, function(status, result) {
            if (status === 'complete' && result.info === 'OK') {

                // 经纬度
                var lng = result.geocodes[0].location.lng;
                var lat = result.geocodes[0].location.lat;

                // 地图实例
                var map = new AMap.Map(mapId, {
                    resizeEnable: true, // 允许缩放
                    center: [lng, lat], // 设置地图的中心点
                    zoom: 15 　　　　　　 // 设置地图的缩放级别，0 - 20
                });

                // 添加标记
                var marker = new AMap.Marker({
                    map: map,
                    position: new AMap.LngLat(lng, lat),   // 经纬度
                });
            } else {
                console.log('定位失败！');
            }
        });
    });
}

function removeMarkers(){
    map.remove(markers);
}

var markers = [];

var marker;
// 清除 marker
function clearMarker() {

    if (marker) {
        marker.setMap(null);
        marker = null;
    }
}

function panToC(longitude, latitude,txt) {
   
	clearMarker();
	removeMarkers();
	
	setTimeout(function(){
		map.setZoomAndCenter(15, [longitude, latitude]);
	    


	    // 添加标记
	     marker = new AMap.Marker({
	    	//content:txt,
	        map: map,
	        position: new AMap.LngLat(longitude, latitude),   // 经纬度
	    });
	    
	     markers.push(marker);
	},500)
	
    
}
</script>