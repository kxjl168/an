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
					<span id="myModal_item_title"></span>工单结账
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">






								
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">订单编号</label>

										<div class="col-lg-9">
										<input type="text" name="orderNo" 
											
											class="form-control" id="orderNo" readonly="readonly"
												placeholder="订单编号" >
											<p class="help-block"></p>
										</div>
									</div>
									
									
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">订单备注</label>

										<div class="col-lg-9">
										<input type="text" name="createRemark" 
											
											class="form-control" id="createRemark"  readonly="readonly"
												placeholder="备注" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">服务类型</label>

										<div class="col-lg-9">
										<input type="text" name="serverTypeName" 
											
											class="form-control" id="serverTypeName"   readonly="readonly"
												placeholder="备注" >
										<!-- <select class="form-control in-line " readonly="readonly" id="serverType" name="serverType">

                                            <option value="0" >安装</option>
                                            <option value="1" >维修</option>
                                            <option value="2" >开锁</option>
                                            <option value="4">测量</option>
                                            <option value="5">猫眼安装</option>
		</select> -->
		
										
											<p class="help-block"></p>
										</div>
									</div>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">用户姓名 </label>

                                <div class="col-lg-9">
                                    <input type="text" name="custName"

                                           class="form-control" id="custName" readonly="readonly"
                                           placeholder=" ">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">用户电话 </label>

                                <div class="col-lg-9">
                                    <input type="text" name="custPhone"

                                           class="form-control" id="custPhone" readonly="readonly"
                                           placeholder=" ">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">用户地址 </label>

                                <div class="col-lg-9">
                                    <input type="text" name="areaName"

                                           class="form-control" id="areaName" readonly="readonly"
                                           placeholder="">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">用户详细地址 </label>

                                <div class="col-lg-9">
                                    <input type="text" name="addressDetail"

                                           class="form-control" id="addressDetail" readonly="readonly"
                                           placeholder="">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">完成时备注</label>

                                <div class="col-lg-9">
                                    <input type="text" name="finishRemark"

                                           class="form-control" id="finishRemark" readonly="readonly"
                                           placeholder="完成时备注">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                            
                            	
                            
										<div class="form-group">
										<label for="name" class="col-lg-3 control-label bold"><b>锁企待支付费用：</b> </label>

										<div class="col-lg-9">
											<input type="text" name="sellerTotalPrice"
											
											class="form-control" id="sellerTotalPrice"    readonly="readonly"
												>
										
											<p class="help-block"></p>
										</div>
									</div>
									
									
									
									
									<div>
									<h3>工单金额明细</h3>
									<hr>
									</div>
										<div class="form-group">
										<div class="table-responsive" style="margin: 10px;">
						<table id="table_list_audit"
							class="table table-bordered table-hover table-striped"></table>
					</div>
										</div>
									



						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						id="close">取消</button>
					<button type="button" class="btn btn-primary" id="btnSubmit_item">
						确认结账</button>
				</div>
			</form>


		</div>
	</div>
</div>