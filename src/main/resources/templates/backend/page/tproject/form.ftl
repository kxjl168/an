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
					<span id="myModal_item_title">添加</span>	工程表
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">


								<#if Session["user"].companyId?exists>
									<input type="hidden" id="companyId" name="companyId" value="${(Session["user"].companyId)?c}">
								<#else>
									<div class="form-group" id="company">
										<label for="name" class="col-lg-3 control-label">请选择锁匠所属公司</label>

										<div class="col-lg-9">
											<select id="companySelect" class="selectpicker" data-live-search="true"
													title="未选中任何项">
											</select>
											<input type="hidden" id="companyId" name="companyId">
											<p class="help-block"></p>
										</div>
									</div>
								</#if>


									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">工程名称</label>

										<div class="col-lg-9">
										<input type="text" name="projectName" onchange="this.value=this.value.substring(0, 30)" onkeydown="this.value=this.value.substring(0, 30)" onkeyup="this.value=this.value.substring(0, 30)"
											
											class="form-control" id="projectName"
												placeholder="工程名称" >
											<p class="help-block"></p>
										</div>
									</div>
									
<div class="form-group ">
										<label for="name" class="col-lg-3 control-label">工程区域</label>

										<div class="col-lg-9">
										
									
										<div id="distpicker" data-toggle="distpicker" class="in-line selectdist ">

											<select class="form-control" style="width:51%;" name="province" id="province" data-province="---- 选择省 ----"></select>
											<select class="form-control" style="width:51%;" name="city" id="city" data-city="---- 选择市 ----"></select>
											<select class="form-control" style="width:51%;" name="district" id="district" data-district="---- 选择区 ----"></select>
											<input type="hidden" id="areaCode" name="areaCode">
				</div>
										
										
										
										
										
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">详细位置</label>

										<div class="col-lg-9">
										<input type="text" name="addressDetail" 
											
											class="form-control" id="addressDetail"
												placeholder="施工地点" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">工程创建时间</label>

										<div class="col-lg-9">
										<input type="text" name="createTime" 
											  readonly="readonly"  
											
											class="form-control" id="createTime"
												placeholder="工程创建时间" >
											<p class="help-block"></p>
										</div>
									</div>
									
									<script>
                            $(function() {
                                $("#createTime").datetimepicker({
                                    minView: "month",
                                    format: 'yyyy-mm-dd',
                                    language: 'zh-CN',
                                    autoclose: true,
                                });
                            });
                            </script>


                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">客户姓名</label>

                                <div class="col-lg-9">
                                    <input type="text" name="custName"

                                           class="form-control" id="custName"
                                           placeholder="客户姓名" >
                                    <p class="help-block"></p>
                                </div>
                            </div>



                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">客户号码</label>

                                <div class="col-lg-9">
                                    <input type="text" name="custPhone"

                                           class="form-control" id="custPhone"
                                           placeholder="客户号码" >
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
