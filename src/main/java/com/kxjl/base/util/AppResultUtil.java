package com.kxjl.base.util;

public class AppResultUtil {

    public static final String no_login_code = "3";
    public static final String no_login_message = "no_login";
    public static final String no_user_code = "3";
    public static final String no_user_message = "no this user";

    public static final String success_code = "1";
    public static final String success_message = "success";
    public static final String fail_code = "0";
    public static final String fail_message = "fail";



    public static AppResult success() {
        AppResult rs = new AppResult();
        rs.setErrCode(success_code);
        rs.setErrMsg(success_message);
        rs.setData(null);
        return rs;
    }

    public static AppResult success(Object obj) {
        AppResult rs = new AppResult();
        rs.setRtime(DateUtil.getNowStr(""));
        rs.setErrCode(success_code);
        if(obj.getClass().equals(String.class))
        {
        	rs.setErrMsg(String.valueOf(obj));
        }
        else
        {
        rs.setErrMsg(success_message);
        rs.setData(obj);
        }
        return rs;
    }

    public static AppResult fail() {
        AppResult rs = new AppResult();
        rs.setErrCode(fail_code);
        rs.setErrMsg(fail_message);
        rs.setData(null);
        return rs;
    }

    public static AppResult fail(String msg) {
        AppResult rs = new AppResult();
        rs.setErrCode(fail_code);
        rs.setErrMsg(msg);
        rs.setData(null);
        return rs;

    }
}
