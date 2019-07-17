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
					<span id="myModal_item_title_add">添加</span>	订单售后表
				</h4>

			</div>

			<form id="mform_item_addB" name="mform_item_addB" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">






									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">订单编号</label>

										<div class="col-lg-9">
										<input type="text" name="orderId"

											class="form-control" id="orderId"
												placeholder="订单编号" >
											<p class="help-block"></p>
										</div>
									</div>

									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">用户密码</label>

										<div class="col-lg-9">
										<input type="text" name="password" 
											
											class="form-control" id="password"
												placeholder="用户密码" >
											<p class="help-block"></p>
										</div>
									</div>
									

									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">问题描述</label>

										<div class="col-lg-9">
										<input type="text" name="description" 
											
											class="form-control" id="description"
												placeholder="问题描述" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">联系电话</label>

										<div class="col-lg-9">
										<input type="text" name="telephone" 
											
											class="form-control" id="telephone"
												placeholder="联系电话" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">联系人称呼</label>

										<div class="col-lg-9">
										<input type="text" name="nickname" 
											
											class="form-control" id="nickname"
												placeholder="联系人称呼" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">联系人地址</label>

										<div class="col-lg-9">
										<input type="text" name="address" 
											
											class="form-control" id="address"
												placeholder="联系人地址" >
											<p class="help-block"></p>
										</div>
									</div>


						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						id="addBClose">关闭</button>
					<button type="button" class="btn btn-primary" id="btnSubmit_item">
						提交更改</button>
				</div>
			</form>


		</div>
	</div>
</div>


<div class="modal fade" data-backdrop="static" id="myModal_item_change"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>


                <h4 class="modal-title" id="myModal_itemLabel">
                    <span id="myModal_item_title">修改</span>	订单状态
                </h4>

            </div>

            <form id="mform_item_change" name="mform_item" class="form-horizontal"
                  role="form" action="" method="post">
				<input id="changeStateId" type="hidden">
                <div class="modal-body">
                    <div class="row">

                        <div class="col-lg-12">

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">修改为：</label>

                                <div class="col-lg-9">
                                    <select class="form-control" id="q_serviceState_change" name="q_serviceState_change">
                                        <option value="">请选择</option>
                                        <option value="0">已提交</option>
                                        <option value="1">客服已确认</option>
                                        <option value="2">已安排师傅处理</option>
                                        <option value="3">处理完成</option>
                                    </select>
                                    <p class="help-block"></p>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            id="editBClose">关闭</button>
                    <button type="button" class="btn btn-primary" id="btnSubmit_item_change">
                        提交更改</button>
                </div>
            </form>


        </div>
    </div>
</div>