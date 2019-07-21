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
										<label for="name" class="col-lg-3 control-label">接警人员手机号，也是登陆接警人员名（唯一约束）</label>

										<div class="col-lg-9">
										<input type="text" name="phone" 
											
											class="form-control" id="phone"
												placeholder="接警人员手机号，也是登陆接警人员名（唯一约束）" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">接警人员密码</label>

										<div class="col-lg-9">
										<input type="text" name="password" 
											
											class="form-control" id="password"
												placeholder="接警人员密码" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">会话id</label>

										<div class="col-lg-9">
										<input type="text" name="sessionKey" 
											
											class="form-control" id="sessionKey"
												placeholder="会话id" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">名称</label>

										<div class="col-lg-9">
										<input type="text" name="name" 
											
											class="form-control" id="name"
												placeholder="名称" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">创建时间（insert 触发器 确定）</label>

										<div class="col-lg-9">
										<input type="text" name="createTime" 
											  readonly="readonly"  
											
											class="form-control" id="createTime"
												placeholder="创建时间（insert 触发器 确定）" >
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
						 .setDate('2019-7-19 22:22:03');
                            });
                            </script>
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">上次更新时间（update 触发器 确定）</label>

										<div class="col-lg-9">
										<input type="text" name="uptimestamp" 
											  readonly="readonly"  
											
											class="form-control" id="uptimestamp"
												placeholder="上次更新时间（update 触发器 确定）" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<script>
                            $(function() {
						$("#uptimestamp").datetimepicker({
							 format: 'yyyy-mm-dd hh:ii:ss',
							 language: 'zh-CN',
							 autoclose:true,
						        startDate:new Date()
						});
						 $("#uptimestamp").data('datetimepicker')
						 .setDate('2019-7-19 22:22:03');
                            });
                            </script>
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">创建人（外键manager）</label>

										<div class="col-lg-9">
										<input type="text" name="createUser" 
											
											class="form-control" id="createUser"
												placeholder="创建人（外键manager）" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">上次更新人（外键manager）</label>

										<div class="col-lg-9">
										<input type="text" name="updateUser" 
											
											class="form-control" id="updateUser"
												placeholder="上次更新人（外键manager）" >
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
