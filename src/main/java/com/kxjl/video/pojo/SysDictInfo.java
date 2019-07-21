package com.kxjl.video.pojo;

public class SysDictInfo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 类型   1案件类型  2案件等级
     */
    private Integer type;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 主键
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 类型   1案件类型  2案件等级
     * @return type 类型   1案件类型  2案件等级
     */
    public Integer getType() {
        return type;
    }

    /**
     * 类型   1案件类型  2案件等级
     * @param type 类型   1案件类型  2案件等级
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 字典名称
     * @return dict_name 字典名称
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * 字典名称
     * @param dictName 字典名称
     */
    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }

    /**
     * 字典值
     * @return dict_value 字典值
     */
    public String getDictValue() {
        return dictValue;
    }

    /**
     * 字典值
     * @param dictValue 字典值
     */
    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
    }
}
