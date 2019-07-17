<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item"
	tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true" style="display: none; overflow-y: auto">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>


				<h4 class="modal-title" id="myModal_itemLabel">
					<span id="myModal_item_title"></span> 回访工单
				</h4>

			</div>

			<form id="mform_item" name="mform_item" class="form-horizontal"
				role="form" action="" method="post">

				<div class="modal-body">
					<div class="row">

						<div class="col-lg-12">

							<input type="hidden" id="id" name="id">








							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">订单编号</label>

								<div class="col-lg-9">
									<input type="text" name="orderNo" class="form-control"
										id="orderNo" readonly="readonly" placeholder="订单编号">
									<p class="help-block"></p>
								</div>
							</div>



							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">订单备注</label>

								<div class="col-lg-9">
									<input type="text" name="createRemark" class="form-control"
										id="createRemark" readonly="readonly" placeholder="">
									<p class="help-block"></p>
								</div>
							</div>

							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">服务类型</label>

								<div class="col-lg-9">
									<input type="text" name="serverTypeName" class="form-control"
										id="serverTypeName" readonly="readonly" placeholder="备注">
									<!-- <select class="form-control in-line " readonly="readonly" id="serverType" name="serverType">

                                            <option value="0" >安装</option>
                                            <option value="1" >维修</option>
                                            <option value="2" >开锁</option>
                                            <option value="4">测量</option>
                                            <option value="5">猫眼安装</option>
		</select> -->


									<p class="help-block"></p>
								</div>
							</div>

							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">锁匠 </label>

								<div class="col-lg-9">
									<input type="text" name="lockName" class="form-control"
										id="lockName" readonly="readonly" placeholder="锁匠id ">
									<p class="help-block"></p>
								</div>
							</div>
							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">锁匠备注</label>

								<div class="col-lg-9">
									<input type="text" name="finishRemark" class="form-control"
										id="finishRemark" readonly="readonly" placeholder="">
									<p class="help-block"></p>
								</div>
							</div>
							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">用户姓名 </label>

								<div class="col-lg-9">
									<input type="text" name="custName" class="form-control"
										id="custName" readonly="readonly" placeholder=" ">
									<p class="help-block"></p>
								</div>
							</div>

							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">用户电话 </label>

								<div class="col-lg-9">
									<input type="text" name="custPhone" class="form-control"
										id="custPhone" readonly="readonly" placeholder=" ">
									<p class="help-block"></p>
								</div>
							</div>

							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">用户地址 </label>

								<div class="col-lg-9">
									<input type="text" name="areaName" class="form-control"
										id="areaName" readonly="readonly" placeholder="">
									<p class="help-block"></p>
								</div>
							</div>
							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">用户详细地址
								</label>

								<div class="col-lg-9">
									<input type="text" name="addressDetail" class="form-control"
										id="addressDetail" readonly="readonly" placeholder="">
									<p class="help-block"></p>
								</div>
							</div>
							
							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">锁具数量
								</label>

								<div class="col-lg-9">
									<input type="text" name="lockNum" class="form-control"
										id="lockNum" readonly="readonly" placeholder="">
									<p class="help-block"></p>
								</div>
							</div>

							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">确认完成图片</label>

								<div class="col-lg-12 nopadding">
									<input type="hidden" name="icons" id="icons"
										readonly="readonly" placeholder="">




									<div class="picscroll">
										<div class="fitem scroll_left disable"   id="LeftArr"><span class="fa fa-2x fa-chevron-circle-left"></span></div>
										<div class="fitem dvcontainer" id="scrollPic">



											<ul>
												<li>
											

	
	<p class="ltitle">第一把锁信息</p>
	
	
	
		<p class="ltitle2">安装前</p>
													<div class='auditImgDiv'>
														<img src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> <img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> 
															
															
															<img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt="">
													</div>
													
													
															<p class="ltitle2">安装中</p>
													<div class='auditImgDiv'>
														<img src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> <img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> 
															
															
															<img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt="">
													</div>
													
															<p class="ltitle2">安装后</p>
													<div class='auditImgDiv'>
														<img src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> <img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> 
															
															
															<img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt="">
													</div>



												</li>
												<li>
												
												<p class="ltitle">第一把锁信息</p>
													<div class='auditImgDiv'>
														<img src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> <img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> <img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt="">
													</div>

												</li>
												<li>
												
												<p class="ltitle">第一把锁信息</p>
													<div class='auditImgDiv'>
														<img src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> <img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt=""> <img
															src="/ttfpzj/img/blueSkin/head.png" width="100"
															height="63" alt="">
													</div>

												</li>

											</ul>


										</div>

										<div class="fitem scroll_right" id="LeftArr"><span  class="fa fa-2x fa-chevron-circle-right"></span></div>
									</div>










									<div id="pics"></div>
									<p class="help-block"></p>
								</div>
							</div>



							<div>
								<h3>回访结果</h3>
								<hr>
							</div>


							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">是否完成： </label>

								<div class="col-lg-9">
									<select class="form-control in-line " readonly="readonly"
										id="type" name="type">
										<option value="0">完成</option>
										<option value="1">未完成(重新处理)</option>
									</select>
									<p class="help-block"></p>
								</div>
							</div>

							<div class="form-group">
								<label for="name" class="col-lg-3 control-label">备注： </label>

								<div class="col-lg-9">
									<textarea rows="5" cols="45" id="content" class="form-control"
										name="content"<#--onchange="this.value=this.value.substring(0, 500)" -->
												  <#--onkeydown="this.value=this.value.substring(0, 500)" -->
												  <#--onkeyup="this.value=this.value.substring(0, 500)"-->
												maxlength="450"
										></textarea>
									<p class="help-block"></p>
								</div>
							</div>

							<div class="form-group" id="point">
								<label for="name" class="col-lg-3 control-label">完成情况(打分)：
								</label>

								<div class="col-lg-9">
									<input type="text" name="serviceScore" value="100"
										class="form-control" id="serviceScore"
										placeholder="针对完成情况打分:1-100">

									<p class="help-block"></p>
								</div>
							</div>










						</div>

					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"
						id="close">取消</button>
					<button type="button" class="btn btn-primary" id="btnSubmit_item">
						提交</button>
				</div>
			</form>





		</div>
	</div>
</div>
