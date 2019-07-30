<!DOCTYPE html>
<!-- saved from url=(0077)https://blackrockdigital.github.io/startbootstrap-sb-admin-2/pages/login.html -->
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>警务110管理平台</title>
    <link href="${ctx}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/vendor/page/login/css/login.css" rel="stylesheet">
</head>

<body>

<div class="logo "><img src="${ctx}/img/blueSkin/logo-ga.png"></div>
<div class="logo hide"><img src="${ctx}/img/blueSkin/logo4.png"></div>
<div class="bjtu"></div>
<div class="row denglu">
    <div class="col-md-5"></div>
    <div class="col-md-5">

    <#if error??>
        <div class="col-md-3 alert alert-danger alert-dismissable"
             style="float: left;position: absolute;margin: 0 0 0 235px;width: 448px;">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
            ${error!""}
        </div>
    </#if>

        <div class="col-md-3" style="margin: 75px 0 0 180px;float: left">
            <div class="login">
                <div class="login-head">
                    <h3 class="head-title">用户登录</h3>
                </div>
                <div class="login-body content" style="margin-left: 45px;">

                    <form role="form" id="login_form" action="${ctx}/login.action" method="POST"
                          onsubmit="return checkInput();">
                        <fieldset>
                            <div class="form-group gt">
                                <img src="${ctx}/img/user.png" style="margin-left: -200px;"/>
                                <input class="form-control" placeholder="请输入手机号码" name="telephone" type="text"
                                       id="telephone"
                                       value="" autofocus>
                            </div>
                            <div class="form-group gt">
                                <img src="${ctx}/img/password.png" style="margin-left: -200px;"/>
                                <input class="form-control" placeholder="请输入密码" name="password" type="password"
                                       id="password_md5"
                                       value="">
                                <input type="hidden" id="password_state">
                            </div>

                        <#--<div class="form-group ">
                            <div class="input-group">
                                <input class="form-control" placeholder="输入右侧验证码" name="captchaCode"
                                       id="captcha_code" type="text"
                                       value="">
                                <span class="input-group-addon" id="captcha"><img
                                        src="${ctx}/validateCode.action" height="20"
                                        id="captcha_image"></span>
                            </div>
                        </div>-->

                            <input type="hidden" name="redirect_uri" value="">
                            <button type="submit"
                                    class="btn btn-primary btn-block btn-color <#if warning??>disable</#if>">
                                登&nbsp;&nbsp;录
                            </button>


                            <#--<input type="hidden" id="olongurl" value="${oauth_loginurl }">-->

                           <#-- <br>
                            <button type="type" onclick="ologin()"
                                    class="btn btn-primary btn-block btn-color ">
                                中通国脉统一认证 登&nbsp;&nbsp;录
                            </button>
                            <script>
                                function ologin(){
                                    window.location.href=$("#olongurl").val();
                                }
                            </script>-->
                        </fieldset>


                    </form>

                </div>
                <div class="row " style="margin-top:30px;margin-left: 45px;">
                    <div class="col-md-3">
                        <div id="qrcode"></div>
                    </div>

                </div>

            </div>
        </div>
    </div>
</div>

<div class="footer hide"><p>Copyright © 2018 .</p></div>

<div class="footer"><p>Copyright © 2019 . <a href="http://www.beian.miit.gov.cn" target="_blank">苏ICP备号</a></p></div>

 


<script type="text/javascript">
    //    $('#login_form input').attr('readonly', 'readonly');
//    $('#login_form').find('button').attr('disabled', 'disabled');
</script>

<script src="${ctx}/vendor/password/js/password.js" type="text/javascript"></script>
<script src="${ctx}/js/md5.js"></script>
<script src="${ctx}/vendor/page/login/js/qrcode.js"></script>
<script src="${ctx}/vendor/page/login/js/login.js"></script>
</body>
</html>