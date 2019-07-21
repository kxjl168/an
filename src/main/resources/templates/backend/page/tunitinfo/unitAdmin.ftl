<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item_admin"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				
				
				<h4 class="modal-title" id="myModal_item_adminLabel">
					<span id="myModal_item_admin_title"></span>	配置单位系统管理员
				</h4>

			</div>

			<form id="mform_item_admin" name="mform_item_admin" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">


									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">单位管理员:</label>

										<div class="col-lg-9">
										<select type="text" name="unitAdmin" 
											
											class="form-control" id="unitAdmin"
												placeholder=选择单位系统管理员" >
											<p class="help-block"></p>
										</div>
									</div>
									<div class="form-group hide">
										<label for="name" class="col-lg-3 control-label">单位管理员:</label>

										<div class="col-lg-9">
										<select type="text" name="unitAdmin2" 
											
											class="form-control" id="unitAdmin2"
												placeholder=选择单位系统管理员" >
											<p class="help-block"></p>
										</div>
									</div>
									

						</div>

					</div>
				</div>
				<div class="modal-footer">
				
					<button type="button" class="btn btn-default" data-dismiss="modal"
						id="close">关闭</button>
					<button type="button" class="btn btn-primary" id="btnUnitAdmin">
						提交更改</button>
				</div>
			</form>


		</div>
	</div>
</div>
