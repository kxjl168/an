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
					<span id="myModal_item_title">客服确认</span>
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="q_id" name="id">
							<input type="hidden" id="serviceState" name="serviceState">

                            <div class="form-group" id="back">
                                <label for="name" class="col-lg-3 control-label">确认备注</label>

                                <div class="col-lg-9">
                                        <textarea type="text" name="backContent"
                                                  rows="5"
                                                  class="form-control" id="backContent"
                                                  placeholder="确认备注" maxlength="200"></textarea>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            <#--<div class="form-group" id="done">
                                <label for="name" class="col-lg-3 control-label">完成备注</label>

                                <div class="col-lg-9">
                                        <textarea type="text" name="doneContent"
                                                  rows="5"
                                                  class="form-control" id="doneContent"
                                                  placeholder="完成备注" maxlength="200"></textarea>
                                    <p class="help-block"></p>
                                </div>
                            </div>-->


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
