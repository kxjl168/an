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
					<span id="myModal_item_title">添加</span>	锁匠变更日志
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">






									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">锁匠id</label>

										<div class="col-lg-9">
										<input type="text" name="userInfoId" 
											
											class="form-control" id="userInfoId"
												placeholder="锁匠id" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">详细内容，可空</label>

										<div class="col-lg-9">
										<input type="text" name="content" 
											
											class="form-control" id="content"
												placeholder="详细内容，可空" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">操作人id,外键到User表或者manager表</label>

										<div class="col-lg-9">
										<input type="text" name="operateUserId" 
											
											class="form-control" id="operateUserId"
												placeholder="操作人id,外键到User表或者manager表" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">操作时间（触发器）</label>

										<div class="col-lg-9">
										<input type="text" name="operateTime" 
											  readonly="readonly"  
											
											class="form-control" id="operateTime"
												placeholder="操作时间（触发器）" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<script>
                            $(function() {
						$("#operateTime").datetimepicker({
							 format: 'yyyy-mm-dd hh:ii:ss',
							 language: 'zh-CN',
							 autoclose:true,
						        startDate:new Date()
						});
						 $("#operateTime").data('datetimepicker')
						 .setDate('2019-6-5 13:27:01');
                            });
                            </script>
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">审核id，对应t_userinfo_auditid</label>

										<div class="col-lg-9">
										<input type="text" name="auditId" 
											
											class="form-control" id="auditId"
												placeholder="审核id，对应t_userinfo_auditid" >
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
