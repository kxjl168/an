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
					<span id="myModal_item_title">添加</span>	接警人员信息
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">



<div class="form-group">
										<label for="name" class="col-lg-3 control-label">工号</label>

										<div class="col-lg-9">
										<input type="text" name="idNo" 
											
											class="form-control" id="idNo"
												placeholder="工号" >
											<p class="help-block"></p>
										</div>
									</div>


									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">接警人员手机号</label>

										<div class="col-lg-9">
										<input type="text" name="phone" 
											
											class="form-control" id="phone"
												placeholder="接警人员手机号" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">密码</label>

										<div class="col-lg-9">
										<input type="password" name="password" 
											
											class="form-control" id="password"
												placeholder="接警人员密码" >
												  <p id='passtip' class="txt-warning small ">密码留空则保持原密码不变,填入密码则设置新密码</p>
											<p class="help-block"></p>
										</div>
									</div>
									
									
									
									
									
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">姓名</label>

										<div class="col-lg-9">
										<input type="text" name="name" 
											
											class="form-control" id="name"
												placeholder="姓名" >
											<p class="help-block"></p>
										</div>
									</div>
									
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">性别</label>

										<div class="col-lg-9">
										<select type="text" name="sex" 
											
											class="form-control" id="sex"
												placeholder="" >
												<option value="男">男</option>
												<option value="女">女</option>
												</select>
											<p class="help-block"></p>
										</div>
									</div>
									
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">坐席</label>

										<div class="col-lg-9">
										<select type="text" name="seatId" 
											
											class="form-control" id=seatId
												placeholder="选择坐席" >
												</select>
											<p class="help-block"></p>
										</div>
									</div>
									
									
									
									
	<div class="form-group">
										<label for="name" class="col-lg-3 control-label">备注</label>

										<div class="col-lg-9">
										<textarea type="text" name="des" 
											
											class="form-control" id="des"
												placeholder="备注" ></textarea>
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
