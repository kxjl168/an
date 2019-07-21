<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				
				
				<h4 class="modal-title" id="myModal_itemLabel">
					<span id="myModal_item_title">添加</span>	报警事件聊天记录
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">






									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">报警id</label>

										<div class="col-lg-9">
										<input type="text" name="alarmId" 
											
											class="form-control" id="alarmId"
												placeholder="报警id" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">聊天状态 1:报警人-》接警人， 2:接警人-》报警人</label>

										<div class="col-lg-9">
										<input type="text" name="talkType" 
											
											class="form-control" id="talkType"
												placeholder="聊天状态 1:报警人-》接警人， 2:接警人-》报警人" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">时间</label>

										<div class="col-lg-9">
										<input type="text" name="ctime" 
											  readonly="readonly"  
											
											class="form-control" id="ctime"
												placeholder="时间" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<script>
                            $(function() {
						$("#ctime").datetimepicker({
							 format: 'yyyy-mm-dd hh:ii:ss',
							 language: 'zh-CN',
							 autoclose:true,
						        startDate:new Date()
						});
						 $("#ctime").data('datetimepicker')
						 .setDate('2019-7-19 22:23:11');
                            });
                            </script>
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">消息类型 1:文本</label>

										<div class="col-lg-9">
										<input type="text" name="msgType" 
											
											class="form-control" id="msgType"
												placeholder="消息类型 1:文本" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">消息内容</label>

										<div class="col-lg-9">
										<input type="text" name="msgContent" 
											
											class="form-control" id="msgContent"
												placeholder="消息内容" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">非文本文件路径</label>

										<div class="col-lg-9">
										<input type="text" name="fileUrl" 
											
											class="form-control" id="fileUrl"
												placeholder="非文本文件路径" >
											<p class="help-block"></p>
										</div>
									</div>
									










						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						id="close">关闭</button>
					<button type="button" class="btn btn-primary" id="btnSubmit_item">
						提交更改</button>
				</div>
			</form>


		</div>
	</div>
</div>
