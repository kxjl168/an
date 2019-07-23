<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_itemad"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog ">
		<div class="modal-content mad">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				
				
				<h4 class="modal-title" id="myModal_itemadLabel">
					<span id="myModal_itemad_title"></span>	音频播放
				</h4>

			</div>

			<form id="mform_itemad" name="mform_itemad" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12" >
<!-- <video id="myplayer" class="video-js vjs-default-skin" style="width: 100%"  controls preload="none">
</video> -->
						
						<audio id="myvideoplayer" preload="auto" style="width: 100%" controls>

</audio>	


						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						id="close">关闭</button>
					
				</div>
			</form>


		</div>
	</div>
</div>
