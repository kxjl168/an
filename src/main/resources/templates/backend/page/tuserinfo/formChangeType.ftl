<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item_changeUserType"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				
				
				<h4 class="modal-title" id="myModal_itemLabel">
					<span id="myModal_item_title"></span>	锁匠类型变更申请
				</h4>

			</div>

			<form id="mform_item_changeUserType" name="mform_item_changeUserType" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="userInfoId" name="userInfoId">

							<input type="hidden" id="userOldType" name="userOldType">
							
							
									<div class="form-group">
										<label for="name" class="col-lg-3 control-label">姓名</label>

										<div class="col-lg-8">
											 <input type="text" name="name"
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
                                    <select id="userType2" name="userNewType" class="userNewType form-control inputtxt" data-live-search="true" data-style=""
                                            title="未选中任何项">
                                            <!-- 签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6   ' -->
                                             <option value="1" parentType="1">签约锁匠</option>
                                        <option value="2" parentType="1">合伙人</option>
                                        <option value="4" parentType="1">合伙人下锁匠</option>
                                        <option value="5" parentType="1">自营锁匠</option>
                                        <option value="6" parentType="1">临时锁匠</option>
                                            </select>
                                  
                                
                                  
                                  
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        
                        
                         <div class="form-group hide companyIddiv" >
                                <label for="name" class="col-lg-3 control-label">合伙人</label>
                                <div class="col-lg-8">
                                    <select id="companyId2" name="companyId" class=" form-control inputtxt" data-live-search="true" data-style=""
                                            title="未选中任何项"></select>
                                   <!--  <input type="hidden" id="companyId" name="companyId"> -->
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            



									

                            <div class="form-group " id="divauditReason">
                                <label for="name" class="col-lg-3 control-label">变更原因</label>

                                <div class="col-lg-8">
                                    <textarea rows="5" cols="45" class="form-control" id="auditReason2" name="ProposReason"></textarea>
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








						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						>关闭</button>
					<button type="button" class="btn btn-primary" id="btnSubmit_ChangeUserType">
                        提交申请</button>
                        
                        	
				</div>
			</form>


		</div>
	</div>
</div>
