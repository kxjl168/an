package com.kxjl.tasktransferplat.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_userinfo_audit_attach")
public class UserinfoAuditAttach {
    /**
     * 
     */
    private String id;

    /**
     * 产品id
     */
    private String audit_id;

    /**
     * 文件md5, 对应mongo id字段, 对应 sys_file md5字段
     */
    private String file_id;

    /**
     * 
     */
    private String create_time;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 
     * @return id 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 产品id
     * @return audit_id 产品id
     */
    public String getAudit_id() {
        return audit_id;
    }

    /**
     * 产品id
     * @param audit_id 产品id
     */
    public void setAudit_id(String audit_id) {
        this.audit_id = audit_id == null ? null : audit_id.trim();
    }

    /**
     * 文件md5, 对应mongo id字段, 对应 sys_file md5字段
     * @return file_id 文件md5, 对应mongo id字段, 对应 sys_file md5字段
     */
    public String getFile_id() {
        return file_id;
    }

    /**
     * 文件md5, 对应mongo id字段, 对应 sys_file md5字段
     * @param file_id 文件md5, 对应mongo id字段, 对应 sys_file md5字段
     */
    public void setFile_id(String file_id) {
        this.file_id = file_id == null ? null : file_id.trim();
    }

    /**
     * 
     * @return create_time 
     */
    public String getCreate_time() {
        return create_time;
    }

    /**
     * 
     * @param create_time 
     */
    public void setCreate_time(String create_time) {
        this.create_time = create_time == null ? null : create_time.trim();
    }

    /**
     * 排序
     * @return sort 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 排序
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}