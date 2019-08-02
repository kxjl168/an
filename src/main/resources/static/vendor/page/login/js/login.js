$('#captcha_code').keyup(function(e) {
	if (e.keyCode === 13 && 4 === $(e.target).val().length) {
		loginAction();
		// $('#login_form').submit();
	}

	/*
	 * if (e.keyCode == 13 ) { $('#login_form').submit(); }
	 */
});

$('#captcha').click(
		function() {
			document.getElementById('captcha_image').src = getRootPath()
					+ '/validateCode.action?time=' + new Date();
		});

$(function() {
	$("#btnlogin").click(function() {
		loginAction();
	});

})

function loginAction() {

	$("#login_form").data('bootstrapValidator').resetForm();

	// var bool2 = bv.isValid();
	$("#login_form").data("bootstrapValidator").validate();
	// flag = true/false
	var flag = $("#login_form").data("bootstrapValidator").isValid();
	if (flag)
		{
		document.login_form.submit()
		
		}
		//$('#login_form').submit();
}
/*
Code();
function Code() {
	showCheck(createCode(""));
}
function showCheck(a) {
	CodeVal = a;
	var c = document.getElementById("myCanvas");
	var ctx = c.getContext("2d");
	ctx.clearRect(0, 0, 1000, 1000);
	ctx.font = "80px 'Hiragino Sans GB'";
	ctx.fillStyle = "#E8DFE8";
	ctx.fillText(a, 0, 100);
}

// 生成验证码
var code = "";
function createCode(e) {
	code = "";
	var codeLength = 4;
	var selectChar = new Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z');
	for (var i = 0; i < codeLength; i++) {
		var charIndex = Math.floor(Math.random() * 60);
		code += selectChar[charIndex];
	}
	if (code.length != codeLength) {
		createCode(e);
	}
	if (canGetCookie == 1) {
		setCookie(e, code, 60 * 60 * 60, '/');
	} else {
		return code;
	}
}
*/
// 验证
$(function() {
	$("#login_form").bootstrapValidator({
		feedbackIcons : {
			/* input状态样式通过，刷新，非法三种图片 */
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		//submitButtons : 'button[type="submit"]',// 提交按钮
		fields : {
			telephone : {// 验证账户
				validators : {
					notEmpty : {// 非空
						message : '用户名不能为空'
					}
				}
			},
			password : {
				validators : {
					notEmpty : {
						message : '密码不能为空'
					}
				}
			},
			validateCode : {
				validators : {
					notEmpty : {
						message : '验证码不能为空'
					}
				}
			}
		}
	});

	// 显示密码
	$('#password').password().on('show.bs.password', function(e) {
	}).on('hide.bs.password', function(e) {
	});

	// 生成下载二维码
	// 生成前景色为红色背景色为白色的二维码
	$.ajax({
		type : "post",
		url : getRootPath() + '/interface/downloadappUrl',
		data : {},
		success : function(data) {
			var qrcode = new QRCode("qrcode", {
				text : data,
				width : 100,
				height : 100,
				colorDark : "#000000",
				colorLight : "#ffffff",
				correctLevel : QRCode.CorrectLevel.H
			});
		}
	});

});

function checkInput() {
	var password_state = $("#password_state").val();
	if (password_state.length <= 0) {
		var telephone = $("#telephone").val();
		var password_md5 = $("#password_md5").val();

		/*
		 * var hash = md5(password_md5);
		 * 
		 * var hash1 = md5(hash + telephone);
		 * 
		 * $("#password_md5").val(hash1); $("#password_state").val(hash1);
		 */

		var hash = md5(password_md5, telephone);

		var hash1 = password_md5;// hash;// md5(hash + telephone);

		$("#password_md5").val(hash1);
		$("#password_state").val(hash1);

	}
	return true;
}