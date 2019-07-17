package com.kxjl.tasktransferplat.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_lock_product_info")
public class LockProductInfo {
    /**
     * id
     */
    private String id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品型号-标识
     */
    private String proType;

    /**
     * 锁类型，0：NB-锁，1：机械锁，2：其他锁
     */
    private String lockType;

    /**
     * 对应企业ID
     */
    private String lockEnterpriseID;

    /**
     * 尺寸说明
     */
    private String proSize;

    /**
     * 产品说明
     */
    private String proDesc;

    /**
     * 产品图片文件id,可多个，逗号分隔，对应mongodbid
     */
    private String proImgs;

    /**
     * 产品附件文件id,可多个，逗号分隔，对应mongodbid
     */
    private String annexs;

    /**
     * 产品附件文件名称,可多个，逗号分隔，对应mongodbid
     */
    @TableField(exist = false)
    private String filenames;

    @TableField(exist = false)
    private String filemd5;
    /**
     * 建立时间
     */
    private String createTime;

    /**
     * 上次更新时间（update 触发器 确定）
     */
    private String updateTime;

    /**
     * 录入人id，对应manager 表id
     */
    private String userId;

    /**
     * 锁企名称，对应manager 表companyName
     */
    @TableField(exist = false)
    private String companyName;

    /**
     * 产品状态
     */
    private int dataState;

    /**
     * 产品安装说明
     */
    private String proInstallDesc;

    /**
     * 技术人员名称
     */
    private String techPersonName;

    /**
     * 技术人员联系方式

     */
    private String techPersonPhone;

    /**
     * 产品安装视频url地址1
     */
    private String proInstallUrl1;

    /**
     * 产品安装视频url地址2
     */
    private String proInstallUrl2;

    /**
     * 完成不需要imei
     */
    private String imeiNeed;

    /**
     * id
     * @return Id id
     */
    public String getId() {
        return id;
    }

    /**
     * id
     * @param id id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 产品名称
     * @return Name 产品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 产品名称
     * @param name 产品名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 产品型号-标识
     * @return ProType 产品型号-标识
     */
    public String getProType() {
        return proType;
    }

    /**
     * 产品型号-标识
     * @param proType 产品型号-标识
     */
    public void setProType(String proType) {
        this.proType = proType == null ? null : proType.trim();
    }

    /**
     * 锁类型，0：NB-锁，1：机械锁，2：其他锁
     * @return LockType 锁类型，0：NB-锁，1：机械锁，2：其他锁
     */
    public String getLockType() {
        return lockType;
    }

    /**
     * 锁类型，0：NB-锁，1：机械锁，2：其他锁
     * @param lockType 锁类型，0：NB-锁，1：机械锁，2：其他锁
     */
    public void setLockType(String lockType) {
        this.lockType = lockType == null ? null : lockType.trim();
    }

    /**
     * 对应企业ID
     * @return lockEnterpriseID 对应企业ID
     */
    public String getLockEnterpriseID() {
        return lockEnterpriseID;
    }

    /**
     * 对应企业ID
     * @param lockEnterpriseID 对应企业ID
     */
    public void setLockEnterpriseID(String lockEnterpriseID) {
        this.lockEnterpriseID = lockEnterpriseID == null ? null : lockEnterpriseID.trim();
    }

    /**
     * 尺寸说明
     * @return ProSize 尺寸说明
     */
    public String getProSize() {
        return proSize;
    }

    /**
     * 尺寸说明
     * @param proSize 尺寸说明
     */
    public void setProSize(String proSize) {
        this.proSize = proSize == null ? null : proSize.trim();
    }

    /**
     * 产品说明
     * @return ProDesc 产品说明
     */
    public String getProDesc() {
        return proDesc;
    }

    /**
     * 产品说明
     * @param proDesc 产品说明
     */
    public void setProDesc(String proDesc) {
        this.proDesc = proDesc == null ? null : proDesc.trim();
    }

    /**
     * 产品图片文件id,可多个，逗号分隔，对应mongodbid
     * @return ProImgs 产品图片文件id,可多个，逗号分隔，对应mongodbid
     */
    public String getProImgs() {
        return proImgs;
    }

    /**
     * 产品图片文件id,可多个，逗号分隔，对应mongodbid
     * @param proImgs 产品图片文件id,可多个，逗号分隔，对应mongodbid
     */
    public void setProImgs(String proImgs) {
        this.proImgs = proImgs == null ? null : proImgs.trim();
    }

    /**
     * 产品附件文件id,可多个，逗号分隔，对应mongodbid
     * @return annexs 产品附件文件id,可多个，逗号分隔，对应mongodbid
     */
    public String getAnnexs() {
        return annexs;
    }

    /**
     * 产品附件文件id,可多个，逗号分隔，对应mongodbid
     * @param annexs 产品附件文件id,可多个，逗号分隔，对应mongodbid
     */
    public void setAnnexs(String annexs) {
        this.annexs = annexs == null ? null : annexs.trim();
    }

    /**
     * 建立时间
     * @return CreateTime 建立时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 建立时间
     * @param createTime 建立时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 上次更新时间（update 触发器 确定）
     * @return UpdateTime 上次更新时间（update 触发器 确定）
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 上次更新时间（update 触发器 确定）
     * @param updateTime 上次更新时间（update 触发器 确定）
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    /**
     * 录入人id，对应manager 表id
     * @return UserId 录入人id，对应manager 表id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 录入人id，对应manager 表id
     * @param userId 录入人id，对应manager 表id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 录锁企名称，对应manager 表companyName
     * @return companyName 锁企名称，对应manager 表companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 锁企名称，对应manager 表companyName
     * @param companyName 锁企名称，对应manager 表companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * 产品附件文件名称,可多个，逗号分隔，对应mongodbid
     */
    public String getFilenames() {
        return filenames;
    }

    /**
     * 产品附件文件名称,可多个，逗号分隔，对应mongodbid
     */

    public void setFilenames(String filenames) {
        this.filenames = filenames == null ? null : filenames.trim();
    }

	public String getFilemd5() {
		return filemd5;
	}

	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}


    public int getDataState() {
        return dataState;
    }

    public void setDataState(int dataState) {
        this.dataState = dataState;
    }

    public String getProInstallDesc() {
        return proInstallDesc;
    }

    public void setProInstallDesc(String proInstallDesc) {
        this.proInstallDesc = proInstallDesc;
    }

    public String getTechPersonName() {
        return techPersonName;
    }

    public void setTechPersonName(String techPersonName) {
        this.techPersonName = techPersonName;
    }

    public String getTechPersonPhone() {
        return techPersonPhone;
    }

    public void setTechPersonPhone(String techPersonPhone) {
        this.techPersonPhone = techPersonPhone;
    }

    public String getProInstallUrl1() {
        return proInstallUrl1;
    }

    public void setProInstallUrl1(String proInstallUrl1) {
        this.proInstallUrl1 = proInstallUrl1;
    }

    public String getProInstallUrl2() {
        return proInstallUrl2;
    }

    public void setProInstallUrl2(String proInstallUrl2) {
        this.proInstallUrl2 = proInstallUrl2;
    }

    public String getImeiNeed() {
        return imeiNeed;
    }

    public void setImeiNeed(String imeiNeed) {
        this.imeiNeed = imeiNeed;
    }
}