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
					<span id="myModal_item_title">添加</span>	订单审核记录
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">






									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">工单id</label>

										<div class="col-lg-9">
										<input type="text" name="orderInfoId" 
											
											class="form-control" id="orderInfoId"
												placeholder="工单id" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">申请人id</label>

										<div class="col-lg-9">
										<input type="text" name="proposer" 
											
											class="form-control" id="proposer"
												placeholder="申请人id" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">申请类型</label>

										<div class="col-lg-9">
										<input type="text" name="proposType" 
											
											class="form-control" id="proposType"
												placeholder="申请类型" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">申请加钱的子类型</label>

										<div class="col-lg-9">
										<input type="text" name="subType" 
											
											class="form-control" id="subType"
												placeholder="申请加钱的子类型" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">申请加钱金额</label>

										<div class="col-lg-9">
										<input type="text" name="proposMoney" 
											
											class="form-control" id="proposMoney"
												placeholder="申请加钱金额" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">申请时间</label>

										<div class="col-lg-9">
										<input type="text" name="proposTime" 
											  readonly="readonly"  
											
											class="form-control" id="proposTime"
												placeholder="申请时间" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<script>
                            $(function() {
						$("#proposTime").datetimepicker({
							 format: 'yyyy-mm-dd hh:ii:ss',
							 language: 'zh-CN',
							 autoclose:true,
						        startDate:new Date()
						});
						 $("#proposTime").data('datetimepicker')
						 .setDate('2019-1-31 15:53:39');
                            });
                            </script>
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
										<label for="name" class="col-lg-3 control-label">审核状态</label>

										<div class="col-lg-9">
										<input type="text" name="auditStates" 
											
											class="form-control" id="auditStates"
												placeholder="审核状态" >
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
