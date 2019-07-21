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
					<span id="myModal_item_title">添加</span>	报警事件
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">






									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">类型  1 图文报警  2在线坐席上传</label>

										<div class="col-lg-9">
										<input type="text" name="type" 
											
											class="form-control" id="type"
												placeholder="类型  1 图文报警  2在线坐席上传" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">接警人员ID</label>

										<div class="col-lg-9">
										<input type="text" name="onlineseatsId" 
											
											class="form-control" id="onlineseatsId"
												placeholder="接警人员ID" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">报警者姓名</label>

										<div class="col-lg-9">
										<input type="text" name="userName" 
											
											class="form-control" id="userName"
												placeholder="报警者姓名" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">身份证号</label>

										<div class="col-lg-9">
										<input type="text" name="idNumber" 
											
											class="form-control" id="idNumber"
												placeholder="身份证号" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">区域</label>

										<div class="col-lg-9">
										<input type="text" name="area" 
											
											class="form-control" id="area"
												placeholder="区域" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">纬度</label>

										<div class="col-lg-9">
										<input type="text" name="latitude" 
											
											class="form-control" id="latitude"
												placeholder="纬度" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">经度</label>

										<div class="col-lg-9">
										<input type="text" name="longitude" 
											
											class="form-control" id="longitude"
												placeholder="经度" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">地理位置</label>

										<div class="col-lg-9">
										<input type="text" name="address" 
											
											class="form-control" id="address"
												placeholder="地理位置" >
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
