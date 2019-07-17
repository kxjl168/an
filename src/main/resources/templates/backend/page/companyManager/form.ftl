<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item"
      role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>
                <h4 class="modal-title" id="myModal_itemLabel">
                    <span id="myModal_item_title">添加</span> 锁匠
                </h4>
            </div>

            <form id="mform_item" name="mform_item" class="form-horizontal"
                  role="form" action="" method="post">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <input type="hidden" id="id" name="id">
                        <#if Session["user"].companyId?exists>
                            <input type="hidden" id="companyId" name="companyId"
                                   value="${(Session["user"].companyId)?c}">
                        <#else>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">所属合伙人/代理人</label>
                                <div class="col-lg-8">
                                    <select id="companyId" name="companyId" class=" form-control inputtxt" data-live-search="true" data-style=""
                                            title="未选中任何项"></select>
                                   <!--  <input type="hidden" id="companyId" name="companyId"> -->
                                    <p class="help-block"></p>
                                </div>
                            </div>
                        </#if>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">锁匠姓名</label>
                                <div class="col-lg-8">
                                    <input type="text" name="name" class="form-control" id="name" placeholder="姓名">
                                    <p class="help-block"></p>
                                </div>
                                <p class="need col-lg-1 control-label ">*</p>
                            </div>
                            
                            <div class="form-group hide">
                                <label for="name" class="col-lg-3 control-label">锁匠所在地</label>
                                <div class="col-lg-8">
                                   <!--  <div id="distpicker" data-toggle="distpicker" class="in-line selectdist "> -->
                                       
                                                 <select id="province1" name="province1" class="selectpicker form-control inputtxt" data-live-search="true" data-style="">
                    <option value="">请选择省份</option>
                </select>
                   <select id="city1" name="city1" class="selectpicker form-control inputtxt" data-live-search="true"
                        title="请选择城市" data-style="">
                </select>
                   <select id="district1" name="district1" class="selectpicker form-control inputtxt" data-live-search="true"
                        title="请选择地区" data-style="">
                </select>
                <input type="hidden" id="q_provinceCode">
        <input type="hidden" id="q_cityCode">
        <input type="hidden" id="q_districtCode"> 
                                                
                                           <!--  <select class="form-control" id="province" name="province"
                                                data-province="---- 选择省 ----">     
                                        </select> <select class="form-control" id="city" name="city"
                                                          data-city="---- 选择市 ----"></select>
                                        <select class="form-control" id="district" name="district"
                                                data-district="---- 选择区 ----"></select>  -->
                                        <input type="hidden" id="areaCode" name="areaCode">
                                   <!--  </div> -->
                                    <p class="help-block"></p>
                                </div>
                                 <p class="need col-lg-1 control-label ">*</p>
                            </div>
                            
                            
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">锁匠身份证号</label>
                                <div class="col-lg-8">
                                <!-- oninput="isIdAvailable($(this))" -->
                                    <input type="text" name="idCard"  oninput="isIdAvailable($(this))"
                                           class="form-control" id="idCard" placeholder="身份证号">
                                    <input type="hidden" name="oldIdCard"
                                           class="form-control" id="oldIdCard" placeholder="原身份证号">
                                    <p class="help-block"><span style="color: red;display: none" id="idMessage"></span>
                                    </p>
                                </div>
                                <p class="need col-lg-1 control-label ">*</p>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">手机号</label>
                                <div class="col-lg-8">
                                    <input type="text" name="phone" oninput="isPoneAvailable($(this))"
                                           class="form-control" id="phone" placeholder="手机号">
                                    <input type="hidden" name="oldPhone" class="form-control" id="oldPhone" placeholder="原手机号">
                                    <p class="help-block"><span style="color: red;display: none"
                                                                id="phoneMessage"></span></p>
                                </div>
                                <p class="need col-lg-1 control-label ">*</p>
                            </div>
                            
                            
                            

                            <div class="form-group">
                                <label for="password" class="col-lg-3 control-label">密码</label>
                                <div class="col-lg-8">
                                    <input type="password" name="password" class="form-control" id="password"
                                           placeholder="设置密码">
                                    <p id='passtip' class="txt-warning small "></p>
                                    <p class="help-block"></p>
                                </div>
                                <p class="need col-lg-1 control-label ">*</p>
                            </div>
                            
                            
                            
                            
                            <div class="form-group hide">
                                <label for="role" class="col-lg-3 control-label">作业区域</label>
                                <div class="col-lg-8">
                                    <div class=" text-right ">
											<span class="row col-xs-5 pull-right"> <span>全选</span>
												<input id="selectAll" name="app" type="checkbox" attr=""
                                                       value="" class="r_hide">
											</span>
                                        <div id="treeDemo" style="width: 90%; height: 80%" class="ztree"></div>
                                    </div>
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            
                            

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">备注标签</label>
                                <div class="col-lg-8">
                                    <input type="text" name="des" class="form-control" id="des" placeholder="备注标签">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            id="close">关闭
                    </button>
                    <button type="button" class="btn btn-primary" id="btnSubmit_item">
                        提交更改
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="${ctx}/vendor/pageAuto/tuserinfo/js/tuserinfoform.js"></script>