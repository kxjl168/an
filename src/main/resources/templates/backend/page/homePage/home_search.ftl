<div class="queryclass" style="margin-top: 0;min-height: 40px;">


    <form class="form-inline">

        <div class="form-group">
            <label for="name" class="lb_text col-xs-5 control-label">时间:</label>

            <div class="col-xs-6">
                <input type="text" name="startTime"
                       class="form-control inputtxt" id="startTime"
                       placeholder="开始时间" readonly>
            </div>
        </div>

        <div class="form-group">
            <label for="name" class="lb_text col-xs-3 control-label">~</label>

            <div class="col-xs-6">
                <input type="text" name="endTime"
                       class="form-control inputtxt" id="endTime"
                       placeholder="结束时间" readonly>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-2">
                <button type="button" class="btn btn-default" onclick="doSearch_item()">查看</button>
            </div>
        </div>
    </form>



</div>