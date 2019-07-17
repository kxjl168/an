
				
				
				    <input type="hidden" id="startTime">
        <input type="hidden" id="endTime"> 
				
				
				
				  <div class='querytitle ' data-tippy-content="展开查询条件" >
                                <h5>查询条件 <i    class="querytitlebtn fa fa-chevron-down"></i></h5>
                               
                                <hr>
                            </div>
				
				
				<form class="form-inline">
				
				<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">锁匠ID:</label>

						<div class="col-xs-7">
							<input id="q_id" type="text" name="q_id"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>

					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">锁匠手机:</label>

						<div class="col-xs-7">
							<input id="q_phone" type="text" name="q_phone"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>


					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">锁匠姓名:</label>

						<div class="col-xs-7">
							<input id="q_name" type="text" name="q_name"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>
					
					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">锁匠身份证:</label>

						<div class="col-xs-7">
							<input id="q_idcard" type="text" name="q_idcard"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>
					

				</form>
				
				
				<form class="form-inline hide">

	<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">合伙人手机:</label>

						<div class="col-xs-7">
							<input id="q_phone2" type="text" name="q_phone2"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>


					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">合伙人姓名:</label>

						<div class="col-xs-7">
							<input id="q_name2" type="text" name="q_name2"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>
					
					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">合伙人身份证:</label>

						<div class="col-xs-7">
							<input id="q_idcard2" type="text" name="q_idcard2"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>

					
						<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">备注:</label>

						<div class="col-xs-7">
							<input id="q_des" type="text" name="q_des"
								class="form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
						</div>
					</div>
					
				
				</form>
				
				
				<form class="form-inline hide">

					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">省:</label>

						<div class="col-xs-7">
							<select id="q_province" type="text" name="q_province"
								class=" form-control inputtxt"  data-live-search="true"  placeholder=""  data-style=""
								aria-controls="dataTables-example">
								 <option value="">请选择省份</option>
								</select>
						</div>
					</div>


					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">市:</label>

						<div class="col-xs-7">
							<select id="q_city" type="text" name="q_city"  data-live-search="true" 
								class=" form-control inputtxt" placeholder=""  data-style=""
								aria-controls="dataTables-example">
								 <option value="">请选择省份</option>
								</select>
						</div>
					</div>
					
					
					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">所在区域:</label>

						<div class="col-xs-7">
							<select id="q_district1"  data-live-search="true"  type="text" name="q_district1"  data-style=""
								class=" form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
								 <option value="">请选择省份</option>
								</select>
						</div>
					</div>
				

					<div class="form-group">
						<label for="name" class="lb_text col-xs-5 control-label">作业区域:</label>

						<div class="col-xs-7">
								<select id="q_district2"  data-live-search="true"  type="text" name="q_district2"  data-style=""
								class=" form-control inputtxt" placeholder=""
								aria-controls="dataTables-example">
								 <option value="">请选择省份</option>
								</select>
						</div>
					</div>
				
				</form>
				
				  <input type="hidden" id="q_provinceCode_query">
        <input type="hidden" id="q_cityCode_query">
        <input type="hidden" id="q_districtCode_query"> 
				
				<form class="form-inline">
				
					



				</form>


			<script>
$(function(){
	tippy(".querytitle",{
		 arrow: true,
		 content: $(".querytitle").attr('data-tippy-content'),
			placement:'top-start',
		  arrowType: 'round', // or 'sharp' (default)
		  animation: 'perspective',
});

   $(".querytitle").click(function(){

	  if( $(".queryclass").hasClass("collaps"))
		  {
		  $(".queryclass").removeClass("collaps");
		  $(".querytitle").attr("data-tippy-content","收起查询条件");
		  }
	  else{
		  $(".queryclass").addClass("collaps");
		  $(".querytitle").attr("data-tippy-content","展开查询条件");
		  }

	  $(".querytitle")[0]._tippy.setContent($(".querytitle").attr("data-tippy-content"));

		
	   
	   })
	   

	
})
    </script>	
