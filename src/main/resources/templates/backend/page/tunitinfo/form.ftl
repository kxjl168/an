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
					<span id="myModal_item_title">添加</span>接警单位
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">






									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">单位名称</label>

										<div class="col-lg-9">
										<input type="text" name="name" 
											
											class="form-control" id="name"
												placeholder="单位名称" >
											<p class="help-block"></p>
										</div>
									</div>
									
									
						 
                           
									
								
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">联系人</label>

										<div class="col-lg-9">
										<input type="text" name="contactPerson" 
											
											class="form-control" id="contactPerson"
												placeholder="单位联系人" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">联系电话</label>

										<div class="col-lg-9">
										<input type="text" name="contactPhone" 
											
											class="form-control" id="contactPhone"
												placeholder="单位联系电话" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">单位地址</label>

										<div class="col-lg-9">
										<input type="text" name="address" 
											
											class="form-control" id="address"
												placeholder="单位地址" >
											<p class="help-block"></p>
										</div>
									</div>
									
	<div class="form-group">
										<label for="name" class="col-lg-3 control-label">备注</label>

										<div class="col-lg-9">
										<textarea type="text" name="des" 
											
											class="form-control" id="des"
												placeholder="备注" ></textarea>
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
