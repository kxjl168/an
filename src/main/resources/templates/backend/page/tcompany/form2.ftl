<!-- 模态框（Modal） -->
<div class="modal fade" data-backdrop="static" id="myModal_item2"
     tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>
                <h4 class="modal-title" id="myModal_itemLabel">
                    <span id="myModal_item_title">审核</span> 合伙人
                </h4>
            </div>

            <form id="mform_item2" name="mform_item2" class="form-horizontal"
                  role="form" action="" method="post">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <input type="hidden" id="id" name="id">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">名称</label>
                                <div class="col-lg-9">
                                    <input type="text" name="companyName" class="form-control"
                                           id="companyName" readonly="readonly" placeholder="名称">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">电话</label>
                                <div class="col-lg-9">
                                    <input type="text" name="companyPhone" class="form-control"
                                           id="companyPhone" readonly="readonly" placeholder="公司电话">
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group ">
                                <label for="name" class="col-lg-3 control-label">所在地</label>
                                <div class="col-lg-9">
                                   <!--  <div id="distpicker" data-toggle="distpicker" class="in-line selectdist "> -->
                                       
                                                 <select id="province2"  name="province2" class="selectpicker form-control inputtxt" data-live-search="true" data-style="">
                    <option value="">请选择省份</option>
                </select>
                   <select id="city2" name="city2" class="selectpicker form-control inputtxt" data-live-search="true"
                        title="请选择城市" data-style="">
                </select>
                   <select id="district2" name="district2" class="selectpicker form-control inputtxt" data-live-search="true"
                        title="请选择地区" data-style="">
                </select>
                <input type="hidden" id="q_provinceCode2">
        <input type="hidden" id="q_cityCode2">
        <input type="hidden" id="q_districtCode2"> 
                                                
                                           <!--  <select class="form-control" id="province" name="province"
                                                data-province="---- 选择省 ----">     
                                        </select> <select class="form-control" id="city" name="city"
                                                          data-city="---- 选择市 ----"></select>
                                        <select class="form-control" id="district" name="district"
                                                data-district="---- 选择区 ----"></select>  -->
                                        <input type="hidden" id="areaCode2" name="areaCode2">
                                   <!--  </div> -->
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">详细地址</label>
                                <div class="col-lg-9">
                                    <input type="text" name="companyAddressDetail"
                                           class="form-control" id="companyAddressDetail"
                                           readonly="readonly" placeholder="合伙人详细地址">
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
                                    <select class="form-control in-line " id="auditFlag"
                                            name="auditFlag">
                                        <option value="1" selected="selected">通过</option>
                                        <option value="2">不通过</option>
                                    </select>
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="form-group " id="divauditReason">
                                <label for="name" class="col-lg-3 control-label">不通过原因</label>
                                <div class="col-lg-9">
                                    <textarea rows="5" cols="45" id="auditReason" name="auditReason" maxlength="200"></textarea>
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
                    <button type="button" class="btn btn-primary" id="btnSubmit_item_audit">
                        确认审核
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
