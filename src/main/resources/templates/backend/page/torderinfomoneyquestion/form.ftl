<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_itemMoney"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModal_itemLabel">
					<span id="myModal_item_title">审核</span>	订单金额申诉表
				</h4>
			</div>

			<form id="mform_itemmoney" name="mform_itemmoney" class="form-horizontal"
				role="form" action="" method="post">
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12">
							<input type="hidden" id="id" name="id">
                            <div class="form-group " id="divauditReason">
                                <label for="name" class="col-lg-3 control-label">审核原因</label>
                                <div class="col-lg-9">
                                    <textarea rows="5" cols="45" id="doneContent" name="doneContent" maxlength="200"></textarea>
                                    <p class="help-block"></p>
                                </div>
                            </div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-warning"  id="refuse">
                        拒绝
                    </button>
					<button type="button" class="btn btn-primary" id="agree">
                        通过
                    </button>
				</div>
			</form>
		</div>
	</div>
</div>
