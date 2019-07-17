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
                    <span id="myModal_item_title">添加</span> 锁企费用
                </h4>
            </div>

            <form id="mform_item" name="mform_item" class="form-horizontal"
                  role="form" action="" method="post">
                <div class="modal-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <input type="hidden" id="id" name="id">
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">锁企名称</label>
                                <div class="col-lg-9">
                                    <select id="lockCompanySelect" data-style="" class="selectpicker" data-live-search="true"
                                            title="请选择锁企名称"></select>
                                    <input type="hidden" id="lockCompanyId" name="lockCompanyId">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">服务类型</label>
                                <div class="col-lg-9">
                                    <select id="serverSelect" data-style="" class="selectpicker" data-live-search="true"
                                            title="请选择服务类型">
                                        <option value="0,1" parentType="1">安装费</option>
                                        <option value="1,1" parentType="1">维修费</option>
                                        <option value="5,1" parentType="1">猫眼安装费</option>
                                        <option value="4,1" parentType="1">测量费</option>
                                        <option value="2,1" parentType="1">开锁费</option>
                                        
                                        <option value="6,1" parentType="1">换锁费</option>
                                        <option value="7,1" parentType="1">工程安装费</option>
                                        <option value="8,1" parentType="1">工程维修费</option>
                                        <option value="19,1" parentType="1">其他费</option>
                                        
                                   
                                        

                                        <option value="4,2" parentType="2">特殊门</option>
                                        <option value="3,2" parentType="2">改装费</option>
                                        <option value="1,2" parentType="2">空跑费</option>
                                        <option value="2,2" parentType="2">远途费</option>
                                        <option value="5,2" parentType="2">加急费</option>
                                        <option value="6,2" parentType="2">假锁费</option>
                                    </select>
                                    <input type="hidden" id="serverType" name="serverType">
                                    <p class="help-block"></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-lg-3 control-label">费用</label>
                                <div class="col-lg-9">
                                    <input type="text" name="price"
                                           class="form-control" id="price"
                                           placeholder="请填写费用" maxlength="6">
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
