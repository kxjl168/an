function isPoneAvailable(obj) {
    var phone=$('#phone').val();
    var oldPhone=$('#oldPhone').val();
    if (oldPhone == phone){
        return ;
    }
     if (obj.val().length==11){
       /* $.ajax({
            type: "post",
            url: getRootPath() + "/manager/tuserinfo/check",
            data: {phone:phone},
            success: function (msg) {
                if (true== msg) {
                    $("#phoneMessage").html("该电话号码已存在!");
                    $('#phoneMessage').show();
                } else {
                    $("#phoneMessage").html("");
                }
            },
            error: function () {
                alert("调用异常");
            }
        });*/
        
        
        $.ajax({
            type: "post",
            url: getRPath() + '/manager/tuserinfo/checkPhone',
            data: {
            	id: $("#id").val(),
            	phone:phone
            },
            async: false,
            dataType: "json",
            success: function (response) {
            	
            	if(response.bol)
            		{
            		//$("#mform_item").data('bootstrapValidator').updateMessage('phone','callback','已存在该手机号码');
            		//$("#mform_item").data('bootstrapValidator').updateStatus('phone','NOT_VALIDATED') .validateField('phone');;
            		
            		
            		//$("#mform_item")
            	  //  .bootstrapValidator('updateStatus', 'phone', 'NOT_VALIDATED') 
            	   // .bootstrapValidator('validateField', 'phone');
            		
            		
            		  $("#phoneMessage").html("该电话号码已存在!");
                      $('#phoneMessage').show();
            		return false;
            		}
            	else
            		{
            		  $("#phoneMessage").html("");
            		return true;
            		}
            	
            },
            error:function(){
            	//error("作业区域验证失败!");
            	return false;
            }
            
   	 });
        
        
        
        
    }
    else {
        $('#phoneMessage').hide();
    }
}
function isIdAvailable(obj) {
    var idCard=$('#idCard').val();
    var oldIdCard=$('#oldIdCard').val();
    if (oldIdCard == idCard){
        return ;
    }
    if(obj.val().length==18){
        $.ajax({
            type: "post",
            url: getRootPath() + "/manager/tuserinfo/checkId",
            data: {idCard:idCard},
            success: function (msg) {
                if (true== msg) {
                    $("#idMessage").html("该身份证号已存在!");
                    $('#idMessage').show();
                } else {
                    $("#idMessage").html("");
                }
            },
            error: function () {
                alert("调用异常");
            }
        });
    }
    else {
        $('#idMessage').hide();
    }
}
