
<div class="panel-left  layout-panel layout-panel-west" style="">
    <div class="panel-header">
        <div class="panel-title">
            <p class="centered">
				
					 <#if (principal.user.icon)?? && principal.user.icon != "">
					<a href="#"> <img src="${ctx}/upload/file/${principal.user.icon}"
						class="img-circle">
					<#else>
                    <a href="#"> <img src="${ctx}/img/blueSkin/head.png"
                                      class="img-circle" >
					</#if>
					</a>
				</p>


				<h5 class="centered">${principal.userName!""}</h5>
        </div>
        <div class="panel-tool">
            <a href="javascript:void(0)" class="panel-tool-collapse" style="display: none;"></a>
            <a href="javascript:void(0)" class="layout-button-left"></a>
        </div>
    </div>
    
    

  
	<div id="sidebar" class="panel-body layout-body"  style="z-index: 1000;">



		<ul class="nav-sidebar">

		
			
			<#list principal.userMenus as menu>

			
			<li class="menu1"><a href="javascript:;"><i  title='${menu.name}' class="micon ${menu.icon?default('fa fa-cube')}"></i>
					<text>${menu.name}</text><span class=" fa fa-chevron-right"></span></a>
				<ul class="nav nav-second-level " style="display: none;">
					<#assign persissions = menu.permissions> <#list persissions as
					permission>
					<li><a href="${ctx}${permission.url}">${permission.name}</a></li>
					</#list>
				</ul></li> 
				
			</#list>


		</ul>
		
	

	</div>
	
		<span class="zzbutn"  data-tippy-content="隐藏菜单" ><i class="icon fa fa-backward"></i><div class="shape"></div></span>
	
</div>


<script>
	$(function() {
		var url = window.location;
		var element = $('#sidebar li a').filter(function(index, item) {
			$(item).removeClass('active');
			var isin = false;
			if ($(item).attr("href") != null && $(item).attr("href") !== "#")
				isin = url.href.indexOf(item.href) > -1;
			else
				isin = false;
			return isin;
		}).addClass('active').parent();


		   

				
	})
	
		  //判断浏览器是否支持html5本地存储
			    function localStorageSupport() {
			        return (('localStorage' in window) && window['localStorage'] !== null)
			    }
			    	
</script>