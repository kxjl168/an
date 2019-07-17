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
					<span id="myModal_item_title">添加</span>	锁匠类型变更审核
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
										<label for="name" class="col-lg-3 control-label">变更申请人id</label>

										<div class="col-lg-9">
										<input type="text" name="proposer" 
											
											class="form-control" id="proposer"
												placeholder="变更申请人id" >
											<p class="help-block"></p>
										</div>
									</div>
									
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">申请原因</label>

										<div class="col-lg-9">
										<input type="text" name="proposReason" 
											
											class="form-control" id="proposReason"
												placeholder="申请原因" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">申请原因图片说明, mongodb ids,对应sys_file md5</label>

										<div class="col-lg-9">
										<input type="text" name="icons" 
											
											class="form-control" id="icons"
												placeholder="申请原因图片说明, mongodb ids,对应sys_file md5" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">审核不通过原因</label>

										<div class="col-lg-9">
										<input type="text" name="auditFailReason" 
											
											class="form-control" id="auditFailReason"
												placeholder="审核不通过原因" >
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
