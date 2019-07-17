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
					<span id="myModal_item_title">添加</span>	琐企投诉表
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
                                    <select id="q_orderNo"  class="form-control"  data-live-search="true" title="未选中任何项"></select>
                                    <input type="hidden" id="orderNo" name="orderNo">
                                    <p class="help-block"></p>
                                </div>
                            </div>



                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">投诉锁企名</label>
                                <div class="col-xs-9">
                                    <input type="text" name="enterpriseName" readonly class="form-control" id="enterpriseName" placeholder="锁企名" >
                                    <input type="hidden" name="enterpriseId" id="enterpriseId" >
                                    <p class="help-block"></p>
								</div>
                            </div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">投诉内容</label>

										<div class="col-lg-9">
                                        <textarea type="text" name="messageContent"
                                                  rows="5"
                                                  class="form-control" id="messageContent"
                                                  placeholder="投诉内容" maxlength="200"></textarea>
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
