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
					<span id="myModal_item_title">添加</span>	工单上多把锁的安装图片信息
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">






									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">锁序列号，第几把</label>

										<div class="col-lg-9">
										<input type="text" name="lockIndex" 
											
											class="form-control" id="lockIndex"
												placeholder="锁序列号，第几把" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">订单编号，用于查询和前端展示(根据时间到秒+3位随机数)</label>

										<div class="col-lg-9">
										<input type="text" name="orderNo" 
											
											class="form-control" id="orderNo"
												placeholder="订单编号，用于查询和前端展示(根据时间到秒+3位随机数)" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label"></label>

										<div class="col-lg-9">
										<input type="text" name="imeiNum" 
											
											class="form-control" id="imeiNum"
												placeholder="" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">锁安装前图片，逗号分隔的mongodbid </label>

										<div class="col-lg-9">
										<input type="text" name="startimgs" 
											
											class="form-control" id="startimgs"
												placeholder="锁安装前图片，逗号分隔的mongodbid " >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">锁安装中产品图片，逗号分隔的mongodbid</label>

										<div class="col-lg-9">
										<input type="text" name="lockimgs" 
											
											class="form-control" id="lockimgs"
												placeholder="锁安装中产品图片，逗号分隔的mongodbid" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">锁安装后完工图片，逗号分隔的mongodbid</label>

										<div class="col-lg-9">
										<input type="text" name="doneimgs" 
											
											class="form-control" id="doneimgs"
												placeholder="锁安装后完工图片，逗号分隔的mongodbid" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">创建时间</label>

										<div class="col-lg-9">
										<input type="text" name="createTime" 
											  readonly="readonly"  
											
											class="form-control" id="createTime"
												placeholder="创建时间" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<script>
                            $(function() {
						$("#createTime").datetimepicker({
							 format: 'yyyy-mm-dd hh:ii:ss',
							 language: 'zh-CN',
							 autoclose:true,
						        startDate:new Date()
						});
						 $("#createTime").data('datetimepicker')
						 .setDate('2019-7-12 10:35:54');
                            });
                            </script>










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
