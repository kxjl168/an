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
					<span id="myModal_item_title">添加</span>	企业
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">



									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">公司名称</label>

										<div class="col-lg-9">
										<input type="text" name="companyName" 
											
											class="form-control" id="companyName"
												placeholder="公司名称" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">公司电话</label>

										<div class="col-lg-9">
										<input type="text" name="companyPhone" 
											
											class="form-control" id="companyPhone"
												placeholder="公司电话" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group ">
										<label for="name" class="col-lg-3 control-label">公司所在地</label>

										<div class="col-lg-9">
										
									
										<div id="distpicker" data-toggle="distpicker" class="in-line selectdist ">
					<select class="form-control" id="province" name="province"
						data-province="---- 选择省 ----">
					</select> <select class="form-control" id="city" name="city" data-city="---- 选择市 ----"></select>
					<select class="form-control" id="district" name="district"
						data-district="---- 选择区 ----"></select>
					<input type="hidden" id="areaCode" name="areaCode">
				</div>
										
										
										
										
										
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">公司详细地址</label>

										<div class="col-lg-9">
										<input type="text" name="companyAddressDetail" 
											
											class="form-control" id="companyAddressDetail"
												placeholder="公司详细地址" >
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
