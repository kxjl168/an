<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item2"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				
				
				<h4 class="modal-title" id="myModal_itemLabel">
					<span id="myModal_item_title">审核</span>	锁匠
				</h4>

			</div>

			<form id="mform_item2" name="mform_item2" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id2" name="id">


									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">锁匠姓名</label>

										<div class="col-lg-9">
											 <input type="text" name="name"
                                                   readonly="readonly"
												   class="form-control" id="name2"
												   placeholder="姓名" > 
												   
												   
												   
											<p class="help-block"></p>
										</div>
									</div>
									
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">锁匠所属合伙人</label>

										<div class="col-lg-9">
											 <input type="text" name="companyName"
                                                   readonly="readonly"
												   class="form-control" id="companyName"
												   placeholder="姓名" > 
												   
												   
												   
											<p class="help-block"></p>
										</div>
									</div>



									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">锁匠手机号</label>

										<div class="col-lg-9">
									 	<input type="text" name="phone"
                                               readonly="readonly"
											   class="form-control" id="phone2"
											   placeholder="手机号" > 
											   
											
											   
											<p class="help-block"></p>
										</div>
									</div>


									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">锁匠身份证号</label>

										<div class="col-lg-9">
											 <input type="text" name="idCard"
                                                   readonly="readonly"
												   class="form-control" id="idCard2"
												   placeholder="身份证号" > 
												   
											
												   
											<p class="help-block"></p>
										</div>
									</div>
                            <div class="form-group ">
                                <label for="name" class="col-lg-3 control-label">所在地</label>
                                <div class="col-lg-9">
                                   <!--  <div id="distpicker" data-toggle="distpicker" class="in-line selectdist "> -->
                                       
                                       <input type="text" name="areaName"
                                                   readonly="readonly"
												   class="form-control" id="areaName"
												   placeholder="身份证号" >
                                       
                                                 
                                   <!--  </div> -->
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="role" class="col-lg-3 control-label">作业区域</label>
                                <div class="col-lg-8">
                                    <div class=" text-right ">
											
                                        <div id="treeDemo2"  style="width: 90%; height: 80%" class="ztree"></div>
                                    </div>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            

                            <div>
                                <h3>审核</h3>
                                <hr>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">审核状态</label>

                                <div class="col-lg-9">
                                    <select class="form-control in-line " id="auditFlag2"
                                            name="auditFlag">

                                        <option value="1" >通过</option>
                                        <option value="2">不通过</option>
                                    </select>
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group " id="divauditReason">
                                <label for="name" class="col-lg-3 control-label">不通过原因</label>

                                <div class="col-lg-9">
                                    <textarea rows="5" cols="45" id="auditReason2" name="auditReason"></textarea>
                                    <p class="help-block"></p>
                                </div>
                            </div>








						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						id="close2">关闭</button>
					<button type="button" class="btn btn-primary" id="btnSubmit_item_audit">
                        确认审核</button>
                        
                        	<button type="button" class="btn btn-warning" 
						id="btnModify">手动修改</button>
				</div>
			</form>


		</div>
	</div>
</div>
