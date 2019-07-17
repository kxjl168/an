/**
 * @(#)Orderinfo.java  2019-02-18 16:38:57
 *
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.FieldStrategy;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kxjl.tasktransferplat.pojo.mongodb.MongoOrderImg;
import com.baomidou.mybatisplus.annotation.TableField;



import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author 单肙
 * @version 1.0.0 2019-02-18 16:38:57
 * @since 1.0.0
 */
@Data
@TableName("t_orderinfo")
public class Orderinfo implements Delayed {

    /**
     * 订单主键，根据redis返回递增id
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private String id;

    /**
     * 工单对应的工程id
     */
    private Long projectId;

    /**
     * 订单编号，用于查询和前端展示(根据时间到秒+3位随机数)
     */
    private String orderNo;
    
    @TableField(exist=false)
    private String depositStatus;

    /**
     * (待审核阶段【1~99】
     * 1：锁匠申请加钱待源匠审核，
     * 2：审核退单，
     * 3：待源匠确认,
     * 4：源匠加钱申请待锁企审核，
     * 5：锁企不通过待源匠处理（如果锁企通过源匠无需再次处理）
     *  6: 合伙人下锁匠申请加钱待合伙人审核
     * 待接单阶段【101~199】, 待作业阶段【201~299】,
     *
     * 待回访阶段【301~399】, 待结账阶段【401~499】,终止状态阶段【501~599】)
     */
    private Integer orderState;

    /**
     * 订单创建时间 触发器
     */
    private String createTime;

    /**
     * 订单创建人（外键manager），不可能为0
     */
    private String createUserId;

    /**
     * 订单创建时备注
     */
    private String createRemark;

    /**
     * (锁企0, 客户1, 默认是锁企0)
     */
    private Integer paymentType;

    /**
     * (安装 = 0,维修 = 1,开锁 = 2,特殊们改造 = 3,测量 = 4, 猫眼安装 = 5)
     */
    //zj 190516 修改为String ，兼容多类型，使用逗号分隔 ，eg：  0,1,2,3标识0-3的类型都有
    @TableField("ServerType")
    private String serverType;

    /**
     * (状态中转)
     */
    @TableField(exist=false)
    private String serverTypeGet;
    /**
     * 锁具数量
     */
    @TableField("LockNum")
    private Integer lockNum;

    /**
     * 预约下次联系时间
     */
    @TableField("AppointmentTime")
    private String appointmentTime;

    /**
     * 锁匠id 外键，不能为0
     */
    private Long lockerId;

    /**
     * 接单时间
     */
    @TableField("ReceiveTime")
    private String receiveTime;

    /**
     * 完工时间（锁匠点击完成）
     */
    @TableField("DoneTime")
    private String doneTime;

    /**
     * 对锁匠结账时间
     */
    @TableField("AccountTime")
    private String accountTime;
    
    
    //锁匠上门时间
    @TableField("ArravieTime")
    private String arravieTime;
    
    

    /**
     * 锁匠服务费，单位 元
     */
    @TableField("LockerBasePrice")
    private java.math.BigDecimal lockerBasePrice;

    /**
     * 商家ID，不使用
     */
    @TableField("SellerId")
    private Long sellerId;

    /**
     * 收商家的费用，单位 元，都是整数，不使用
     */
    @TableField("SellerBasePrice")
    private BigDecimal sellerBasePrice;

    /**
     * 商家内部id，不使用
     */
    @TableField("SellerInnerId")
    private String sellerInnerId;

    /**
     * 商家自定义数据，不使用
     */
    @TableField("SellerInnerData")
    private String sellerInnerData;

    /**
     * 锁匠总费用，单位 元
     */
    @TableField("LockerTotalPrice")
    private java.math.BigDecimal lockerTotalPrice;

    /**
     * 商家总费用，单位 元，不使用
     */
    @TableField("SellerTotalPrice")
    private BigDecimal sellerTotalPrice;

    /**
     * 数据状态，1：可用，0：禁用，2：删除
     */
    @TableField("DataState")
    @TableLogic
    private Integer dataState;

    /**
     * 服务分数
     */
    @TableField("ServiceScore")
    private Integer serviceScore;


    @TableField("CompanyId")
    private Long companyId;

    /**
     * 接单类型 主动接单：1，后台分配：2
     */
    @TableField("OrderType")
    private String orderType;

    /**
     * 锁匠完成时的备注
     */
    @TableField("FinishRemark")
    private String finishRemark;

    /**
     * 客户姓名
     */
    @TableField("CustomerName")
    private String customerName;

    /**
     * 客户电话
     */
    @TableField("CustomerPhone")
    private String customerPhone;
    
    /**
     * 客户电话2
     */
    @TableField("CustomerPhone2")
    private String customerPhone2;
 

    /**
     * 客户详细地址
     */
    @TableField("AddressDetail")
    private String addressDetail;

    //邮寄地址
    @TableField("MailAddressDetail")
    private String mailAddressDetail;
    
    
    /**
     * 地址代码
     */
    @TableField("AreaCode")
    private String areaCode;

    /**
     * 地区id
     */
    @TableField("DistrictId")
    private Integer districtId;

    /**
     * 锁企是否结账
     */
    @TableField("FinishedState")
    private Integer finishedState;
    /**
     * 是否需要上传房号
     */
    @TableField("isRoomNessary")
    private Integer isRoomNessary;
    /**
     * 是否已上传IMEI号
     */
    @TableField("isUploadImei")
    private Integer isUploadImei;
    /**
     * IMEI号
     */
    @TableField("imeiNum")
    private String imeiNum;

    /**
     * 地区名称
     */
    @TableField(exist = false)
    private String areaName;

    //query
    @TableField(exist=false)
    private Integer type;

    //工单回访备注
    @TableField(exist=false)
    private String content;

    //工单回访备注
    @TableField(exist=false)
    private String backReason;
    /**
     * 类型解释
     */
    @TableField(exist = false)
    private String serverDes;

    @TableField(exist = false)
    private Project project;

    
    
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
    
    
    /**
     * 工程名
     */
    @TableField(exist=false)
    private String projectName;
    
    
    //锁企
    @TableField(exist=false)
    private String EnterprisePhone;

  //锁企
    @TableField(exist=false)
    private String EnterpriseName;

    
    /**
     * 锁匠名
     */
    @TableField(exist=false)
    private String lockName;

    /**
     * 图片地址集合
     */
    @TableField(exist=false)
    private List<MongoOrderImg> orderImages;

    /**
     * 图片md5集合
     */
    @TableField("icons")
    private String icons;
    
    //'打卡签到图片，逗号分隔的mongodbid',
    @TableField("startimgs")
    private String startimgs;
    
    //'安装前产品图片，逗号分隔的mongodbid',
    @TableField("lockimgs")
    private String lockimgs;
    
   //'安装后完工图片，逗号分隔的mongodbid',
    @TableField("doneimgs")
    private String doneimgs;

    //'锁匠预约上门时间',
    @TableField("LockerAppointmentTime")
    private String lockerAppointmentTime;


    @TableField(exist=false) //统计数量
    private Integer num;

    @TableField(exist=false) //统计月份
    private String month;


    /**
     * 锁匠名
     */
    @TableField(exist=false)
    private String choice;


    /**
     * 加价
     */
    @TableField(exist=false)
    private int price;

    @TableField(exist = false)
    private String lockerPhone;

    @TableField(exist = false)
    private Long milliseconds;

    /**
     * Returns the remaining delay associated with this object, in the
     * given time unit.
     *
     * @param unit the time unit
     * @return the remaining delay; zero or negative values indicate
     * that the delay has already elapsed
     */
    /**
     * 延时值，在该值小于等于0时可以被延时队列返回，进行相关操作
     * 该方法返回该值会被取出的时间的毫秒数与当前时间戳的差值
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        long delay = milliseconds - System.currentTimeMillis();
        return delay;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    /**
     * compare方法，延时队列（存储方式是二叉堆）的实际是一个优先级队列，优先级队列的存储结构是二叉堆，
     * 该方法是在将元素插入队列中，比较要插入的位置的父节点和该元素之间的大小，如果
     * 该元素比较小，则将该元素放在父节点的位置；若该元素比较大，则跳出循环。
     * 如此循环，直到找到根节点。
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        long delayDifference = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        if (delayDifference == 0) {
            return 0;
        } else if (delayDifference < 0) {
            return -1;
        } else {
            return 1;
        }
    }
 



    /**
     * 客户姓名
     */
    @TableField(exist = false)
    private String custName;

    /**
     * 客户电话
     */
    @TableField(exist = false)
    private String custPhone;

    /**
     * 客户地址 外键 address，不能为 0
     */
    @TableField(exist = false)
    private String custAddress;

    /**
     *
     */
    @TableField(exist = false)
    private Address address;

    /**
     * 客户地址详情 文本
     */
    @TableField(exist = false)
    private String custAddressDetail;


    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

    //是否加急 加急 0:普通， 1：加急',
    @TableField("urgent")
    private String urgent;
    
    //是否超时 '是否超时未处理  0:普通， 1：已超时',
    @TableField("timeout")
    private String timeout;
    
    
    //产品id
    @TableField("productId")
    private String productId;
    
    @TableField(exist = false)
    private String productName;
    
    @TableField(exist = false)
    private String proType; // '产品型号',
    
    @TableField(exist = false)
    private String lockType;//'锁类型，0：NB-锁，1：机械锁，2：其他锁',
    

    @TableField(exist = false)
    private String lockCompanyName;

    
    @TableField(exist = false)
    private String companyName;
    
    
    
    //操作日志查询
    @TableField(exist = false)
    private String operlogType;
    
    @TableField(exist = false)
    private String operlogSubType;
    
    @TableField(exist = false) //最后操作时间
    private String lastOperateTime;
    
    @TableField(exist = false)  //操作日志时间，
    private String logstartTime;

    @TableField(exist = false)
    private String logendTime;
    
    @TableField(exist = false)
    private String managerType;//用户类型
    
    @TableField(exist = false)
    private String addMoney;//主动加钱
    
    @TableField(exist = false)
    private String addMoneyDesc;//主动加钱说明
    
    @TableField(exist = false)
    private String operateUserId;//查询操作人
    
    
    private String orderFeeType;//工单类型
    private String deleteUseType;//` varchar(2) NOT NULL DEFAULT '1' COMMENT '废弃操作方 1:源匠， 2:琐企',
    private String deleteReason;//` varchar(200) NOT NULL DEFAULT '0' COMMENT '废弃原因说明'

    private String installRemark;//安装注意事项
    private String doorInfo;//客户房门信息
    
    private String orderCashType;//工单的结算类型，对应工单完成时锁匠的结算类型，用于锁匠结算  提现类型， 0：现结，1：周结，2：月结（需要结合userType合伙人判断）
    
    @TableField("orderComplateCode")
    private String orderComplateCode;//核销码 短信sms code
    
    @TableField("orderComplateCodeSend")
    private String orderComplateCodeSend;//核销码 是否已发送 0:未发送，1：已发送
    
    
}

