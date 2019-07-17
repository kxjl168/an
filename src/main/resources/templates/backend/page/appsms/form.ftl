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
					<span id="myModal_item_title">添加</span>	短信验证表
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">






									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">手机号码</label>

										<div class="col-lg-9">
										<input type="text" name="mobile" 
											
											class="form-control" id="mobile"
												placeholder="手机号码" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">短信内容</label>

										<div class="col-lg-9">
										<input type="text" name="content" 
											
											class="form-control" id="content"
												placeholder="短信内容" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">验证码</label>

										<div class="col-lg-9">
										<input type="text" name="checkCode" 
											
											class="form-control" id="checkCode"
												placeholder="验证码" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">发送时间</label>

										<div class="col-lg-9">
										<input type="text" name="sendTime" 
											
											class="form-control" id="sendTime"
												placeholder="发送时间" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">短息类型1-注册2-找回密码.....</label>

										<div class="col-lg-9">
										<input type="text" name="sendType" 
											
											class="form-control" id="sendType"
												placeholder="短息类型1-注册2-找回密码....." >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">请求ip</label>

										<div class="col-lg-9">
										<input type="text" name="ip" 
											
											class="form-control" id="ip"
												placeholder="请求ip" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">0：有效，1:无效</label>

										<div class="col-lg-9">
										<input type="text" name="isValidate" 
											
											class="form-control" id="isValidate"
												placeholder="0：有效，1:无效" >
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
