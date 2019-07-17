package com.kxjl.tasktransferplat.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("t_userinfo")
public class Userinfo {

	/**
	 * 用户id(主键)，不能为0
	 */
	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;

	/**
	 * 用户手机号，也是登陆用户名（唯一约束）
	 */
	private String phone;

	/**
	 * 用户密码，暂时存储明文
	 */
	private String password;

	/**
	 * 会话id
	 */
	@TableField("SessionKey")
	private String sessionKey;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 创建时间（insert 触发器 确定）
	 */
	@TableField("CreateTime")
	private String createTime;

	/**
	 * 上次更新时间（update 触发器 确定）
	 */
	@TableField("UpdateTime")
	private String updateTime;

	/**
	 * 创建人（外键manager）
	 */
	@TableField("CreateUser")
	private String createUser;

	/**
	 * 上次更新人（外键manager）
	 */
	@TableField("UpdateUser")
	private String updateUser;

	/**
	 * 用户类型 锁匠 = 1, 合伙人锁匠 = 2,代理人锁匠 = 3 ，此表内的所有用户均为锁匠
	 */
	@TableField("UserType")
	private String userType;

	/**
	 * 数据状态，1：可用，0：禁用，2：删除
	 */
	@TableField("DataState")
	@TableLogic
	private Integer dataState;

	/**
	 * 父id,外键自己，可空，目前不使用
	 */
	@TableField("FatherId")
	private Long fatherId;

	/**
	 * 父Level +1,所处的层次，目前不使用
	 */
	private Long level;

	/**
	 * 为了导入数据，目前不使用
	 */
	@TableField("tempOldUserId")
	private String tempOldUserId;

	/**
	 * 性别（男，女），默认 男
	 */
	private String sex;

	/**
	 * 身份证号
	 */
	@TableField("IdCard")
	private String idCard;

	/**
	 * 客服对锁匠的备注
	 */
	@TableField("des")
	private String des;

	/**
	 * 标签:黑名单，常合作伙伴
	 */
	private String tag;

	/**
	 * 锁匠所属公司id，若属于本平台为null
	 */
	@TableField("CompanyId") // 合伙人id-本表关联 zj190514
	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long companyId;

	/**
	 * 用户分数
	 */
	@TableField("UserScore")
	private Long userScore;

	/**
	 * 已评分工单数
	 */
	@TableField("GradedOrderCount")
	private Long gradedOrderCount;

	/**
	 * 微信openid
	 */
	@TableField("WxOpenId")
	private String wxOpenId;

	/**
	 * 源匠审核状态（-1未提交，0已提交，1审核通过,2不通过）
	 */
	@TableField("AuditFlag")
	private String auditFlag;

	/**
	 * 合伙人审核状态（-1未提交，0已提交，1审核通过,2不通过） ，是否同意加入其团队审核
	 */
	@TableField("parterAuditFlag")
	private String parterAuditFlag;
	
	@TableField(exist=false)
	private String parterAuditFlag2;

	/**
	 * 审核不通过原因
	 */
	private String auditReason;

	/**
	 * 用户账户余额
	 */
	@TableField("AccountMoney")
	private java.math.BigDecimal accountMoney;

	/**
	 * 用户账户冻结金额，目前仅仅为提现时待审核的金额
	 */
	@TableField("FreezeMoney")
	private java.math.BigDecimal freezeMoney;

	@TableField(exist = false)
	private Double freezeOrderMoney;

	/**
	 * 所在省份
	 */
	@TableField(exist = false)
	private String province;

	/**
	 * 所在地市
	 */
	@TableField(exist = false)
	private String city;

	/**
	 * 所在区域
	 */
	@TableField(exist = false)
	private String district;

	/**
	 * 所在省份id
	 */
	@TableField(exist = false)
	private String provinceId;

	/**
	 * 所在地市id
	 */
	@TableField(exist = false)
	private String cityId;

	@TableField("districtId")
	private String districtId;

	@TableField(exist = false)
	private String districtId2;

	/**
	 * 更新人名
	 */
	@TableField(exist = false)
	private String updateName;

	/**
	 * 头像
	 */
	@TableField("AvatarUrl")
	private String avatarUrl;

	/**
	 * 手持身份证
	 */
	@TableField("ImgIdCardUrl")
	private String imgIdCardUrl;

	@TableField(exist = false)
	private String bankId;

	@TableField(exist = false)
	private String bankCardId;

	@TableField(exist = false)
	private String bankUserName;

	@TableField(exist = false)
	private String bankUserIDCard;

	@TableField("DistrictId")
	private String areaCode;

	@TableField(exist = false)
	private String areaName;

	@TableField(exist = false)
	private String companyName;

	@TableField(exist = false)
	private String companyPhone;
	@TableField(exist = false)
	private String companyidCard;

	/**
	 * 开始时间
	 */
	@TableField(exist = false)
	private String startTime;

	/**
	 * 结束时间
	 */
	@TableField(exist = false)
	private String endTime;

	@TableField(exist = false)
	private String parterquery;// 查询合伙人下的锁匠

	@TableField(exist = false)
	private String districtids;// 作业区域id,逗号分隔

	@TableField(exist = false)
	private String districtnames;// 作业区域名称,逗号分隔

	@TableField(exist = false)
	private String jobdistrictid;// 作业区域id

	@TableField(exist = false)
	private String partnerName;// 锁匠合伙人名

	@TableField(exist = false)
	private String checkCode;// 短信验证码

	private String depositType;// 提现状态

	
	//zj 用户账户金额计算以下 3项为准，根据工单、提现记录实时计算.
	@TableField(exist = false)
	private java.math.BigDecimal 	total;// 根据工单计算的全部账户金额(包括已提现)
	
	@TableField(exist = false)
	private java.math.BigDecimal doneMoney;// 已提现金额

	@TableField(exist = false)
	private java.math.BigDecimal todoMoney;// 提现待审核金额(冻结金额)

	
	@TableField(exist = false)
	private String querykey;// 名称/id/phone模糊查询

	
	/**
	 * 根据用户类型获取其提现类型
	 * @return
	 * @author zj
	 * @date 2019年6月11日
	 */
	public String getDepositType() {
		// 签约锁匠=1, 合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6
		
	    //`usertype` varchar(2) DEFAULT '1' COMMENT '用户类型   签约锁匠=1,  合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6   ',
        //`depositType` varchar(2) NOT NULL DEFAULT '0' COMMENT '提现类型， 0：现结，1：周结，2：月结
		
		String depositType = "";

		if(userType!=null)
		{
		switch (userType) {
		case "1":
			//depositType = "签约锁匠";
			depositType = "1";
			break;
		case "2":
			//depositType = "合伙人";
			depositType = "2";
			break;
		case "3":
			//depositType = "代理人";
			depositType = "2";
			break;
		case "4":
			//depositType = "合伙人下锁匠";
			depositType = "2"; //需要结合用户类型先判断
			break;
		case "5":
			//depositType = "自营锁匠";
			depositType = "2";
			break;
		case "6":
			//depositType = "临时锁匠";
			depositType = "0";
			break;

		default:
			break;
		}
		}

		return depositType;
	}

	/**
	 * 获取锁匠类型名称
	 * 
	 * @param userType
	 * @return
	 * @author zj
	 * @date 2019年6月6日
	 */
	public String getUserTypeName(String userType) {
		// 签约锁匠=1, 合伙人锁匠 = 2,代理人锁匠 = 3, 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6
		String name = "";

		if(userType!=null)
		switch (userType) {
		case "1":
			name = "签约锁匠";
			break;
		case "2":
			name = "合伙人";
			break;
		case "3":
			name = "代理人";
			break;
		case "4":
			name = "合伙人下锁匠";
			break;
		case "5":
			name = "自营锁匠";
			break;
		case "6":
			name = "临时锁匠";
			break;

		default:
			break;
		}

		return name;
	}
}
