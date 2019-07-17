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
					<span id="myModal_item_title">添加</span>	消息表
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">






									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">消息接收人</label>

										<div class="col-lg-9">
										<input type="text" name="receiver" 
											
											class="form-control" id="receiver"
												placeholder="消息接收人" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">消息标题</label>

										<div class="col-lg-9">
										<input type="text" name="messageTitle" 
											
											class="form-control" id="messageTitle"
												placeholder="消息标题" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">消息体</label>

										<div class="col-lg-9">
										<input type="text" name="messageContent" 
											
											class="form-control" id="messageContent"
												placeholder="消息体" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">消息类型</label>

										<div class="col-lg-9">
											<select class="form-control in-line " id="messageType" name="messageType">
				
				<option value="1" selected="selected">系统消息</option>
				<option value="2">工单消息</option>
		</select>
		
								
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
