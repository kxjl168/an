<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item"
role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				
				
				<h4 class="modal-title" id="myModal_itemLabel">
					<span id="myModal_item_title"></span>	案件受理
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">






									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">案件类型:</label>

										<div class="col-lg-9">
										<select  name="case_type" 
											
											class="form-control" id="case_type"
												 ></select>
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">案件等级:</label>

										<div class="col-lg-9">
										<select  name="case_level" 
											
											class="form-control" id="case_level"
												 ></select>
											<p class="help-block"></p>
										</div>
									</div>
									
									

						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						id="close">关闭</button>
					<button type="button" class="btn btn-primary" id="btnAccept">
						确定</button>
				</div>
			</form>


		</div>
	</div>
</div>
