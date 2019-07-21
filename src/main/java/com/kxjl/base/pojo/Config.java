package com.kxjl.base.pojo;

public class Config {
    /**
     * 配置表key,主键，非空
     */
    private String ckey;

    /**
     * 配置表value，非空
     */
    private String cvalue;

    /**
     * 备注说明本配置项的作用，非空
     */
    private String des;

    /**
     * 配置表key,主键，非空
     * @return ckey 配置表key,主键，非空
     */
    public String getCkey() {
        return ckey;
    }

    /**
     * 配置表key,主键，非空
     * @param ckey 配置表key,主键，非空
     */
    public void setCkey(String ckey) {
        this.ckey = ckey == null ? null : ckey.trim();
    }

    /**
     * 配置表value，非空
     * @return cvalue 配置表value，非空
     */
    public String getCvalue() {
        return cvalue;
    }

    /**
     * 配置表value，非空
     * @param cvalue 配置表value，非空
     */
    public void setCvalue(String cvalue) {
        this.cvalue = cvalue == null ? null : cvalue.trim();
    }

    /**
     * 备注说明本配置项的作用，非空
     * @return des 备注说明本配置项的作用，非空
     */
    public String getDes() {
        return des;
    }

    /**
     * 备注说明本配置项的作用，非空
     * @param des 备注说明本配置项的作用，非空
     */
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }
}