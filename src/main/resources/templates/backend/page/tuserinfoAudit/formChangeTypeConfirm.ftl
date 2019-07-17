<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item_ConfirmUserType"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				
				
				<h4 class="modal-title" id="myModal_itemLabel">
					<span id="myModal_item_title2"></span>	锁匠类型变更审核
				</h4>

			</div>

			<form id="mform_item_ConfirmUserType" name="mform_item_changeUserType" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">
						
						
							<input type="hidden"  name="id">
						

							<input type="hidden" id="userInfoId" name="userInfoId">

							<input type="hidden" id="userOldType" name="userOldType">
							
							
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">锁匠姓名</label>

										<div class="col-lg-8">
											 <input type="text" name="userInfoName"
                                                   readonly="readonly"
												   class="form-control" id="name2"
												   placeholder="姓名" > 
												   
												   
												   
											<p class="help-block"></p>
										</div>
									</div>
							
							
								<div class="form-group">
										<label for="name" class="col-lg-3 control-label">当前类型</label>

										<div class="col-lg-8">
											 <input type="text" name="userOldTypeName"
                                                   readonly="readonly"
												   class="form-control" id="userOldTypeName"
												   placeholder="类型" > 
	 
											<p class="help-block"></p>
										</div>
									</div>


										<div class="form-group">
										<label for="name" class="col-lg-3 control-label">变更类型</label>

										<div class="col-lg-8">
											 <input type="text" name="userNewTypeName"
                                                   readonly="readonly"
												   class="form-control" id="userNewTypeName"
												   placeholder="类型" > 
	 
											<p class="help-block"></p>
										</div>
									</div>

                        
                        
                         <div class="form-group hide companyIddiv" >
                                <label for="name" class="col-lg-3 control-label">所属合伙人</label>
                                <div class="col-lg-8">
                                    <select id="companyId2" name="companyId" class=" form-control inputtxt" data-live-search="true" data-style=""
                                            title="未选中任何项"></select>
                                   <!--  <input type="hidden" id="companyId" name="companyId"> -->
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            



									

                            <div class="form-group " >
                                <label for="name" class="col-lg-3 control-label">变更原因</label>

                                <div class="col-lg-8">
                                    <textarea rows="5" cols="45" class="form-control" readonly id="proposReason" name="proposReason"></textarea>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                            
                               <div class="form-group">
                                <label for="password" class="col-lg-3 control-label">图片</label>
                                <div class="col-lg-8">
                                    <div id="proImgs" class="padding-left-0"></div>
                                    <p class="help-block"></p>
                                </div>
                                <p class="need col-lg-1 control-label ">*</p>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">附件</label>
                                <div class="col-lg-8">
                                    <div id="annexs" class="padding-left-0"></div>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                           
                           
                           
                            <div class=" hide form-group orderdv">
                            
                             <div>
                                <h4>锁匠未完成工单列表</h4>
                                <hr>
                            </div>
                            
                            
                              <label for="name" class="hide col-lg-3 control-label">锁匠未完成工单列表:</label>

                                <div class="col-lg-12">
                                    
                                  <div class="table-responsive" style="margin-bottom: 10px;">
                                    <table id="table_list_order"
                                           class="table table-bordered table-hover table-striped"></table>
                                </div>
                                </div>
                                
                                
                            </div>
                            
                            
                         
                            
                                <div class=" hide  form-group lockdv">
                            
                               <div>
                                <h4>名下锁匠列表</h4>
                                <hr>
                            </div>
                            
                            
                              <label for="name" class="col-lg-3 control-label">名下锁匠列表:</label>

                                <div class="col-lg-9">
                                    
                                  <div class="table-responsive" style="margin-bottom: 10px;">
                                    <table id="table_list_locker"
                                           class="table table-bordered table-hover table-striped"></table>
                                </div>
                                </div>
                                
                                
                            </div>
                           
                            
                            
                         

                            <div class="form-group">
                               <div>
                                <h4>审核</h4>
                                <hr>
                            </div>
                                <label for="name" class="col-lg-3 control-label">审核状态</label>

                                <div class="col-lg-8">
                                    <select class="form-control in-line " id="auditFlag2"
                                            name="auditStates">

                                        <option value="1" >通过</option>
                                        <option value="2">不通过</option>
                                    </select>
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group hide" id="divauditReason">
                                <label for="name" class="col-lg-3 control-label">不通过原因</label>

                                <div class="col-lg-8">
                                    <textarea rows="5" cols="45" class="form-control" id="auditReason2" name="auditFailReason"></textarea>
                                    <p class="help-block"></p>
                                </div>
                            </div>




                            








						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						>关闭</button>
					<button type="button" class="btn btn-warning" id="btnSubmit_ConfirmUserType">
                       确认审批</button>
                        
                        	
				</div>
			</form>


		</div>
	</div>
</div>
