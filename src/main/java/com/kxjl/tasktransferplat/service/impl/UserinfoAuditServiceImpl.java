package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.plus.UserMessageMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoAuditMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoJobAreaMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoOperateLogMapper;
import com.kxjl.tasktransferplat.pojo.UserMessage;
import com.kxjl.tasktransferplat.pojo.Userinfo;
import com.kxjl.tasktransferplat.pojo.UserinfoAudit;
import com.kxjl.tasktransferplat.pojo.UserinfoAuditAttach;
import com.kxjl.tasktransferplat.pojo.UserinfoJobArea;
import com.kxjl.tasktransferplat.pojo.UserinfoOperateLog;
import com.kxjl.tasktransferplat.service.UserinfoAuditService;
import com.kxjl.tasktransferplat.service.UserinfoService;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;
import com.kxjl.tasktransferplat.util.TokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class UserinfoAuditServiceImpl implements UserinfoAuditService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserinfoAuditMapper itemMapper;

	@Autowired
	private UserinfoService userinfoService;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Autowired
	private UserinfoJobAreaMapper userinfoJobAreaMapper;

	@Autowired
	UserinfoOperateLogMapper userinfoOperateLogMapper;

	@Autowired
	UserMessageMapper userMessageMapper;

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveUserinfoAudit(UserinfoAudit item) {
		JSONObject rtn = new JSONObject();

		try {

			item.setId(UUIDUtil.getUUID());

			itemMapper.insertSelective(item);

			rtn.put("bol", true);

			return rtn;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("新增失败", e);
			rtn.put("bol", false);
			rtn.put("message", "新增失败");
			log.error(ExceptionUntil.getMessage(e));
			return rtn;
		}
	}

	@Transactional
	public Message userTypeChangeDone(UserinfoAudit item) {

		Message msg = new Message();
		msg.setBol(true);

		Userinfo locker = userinfoService.selectUserinfoById(item.getUserInfoId());

		UserinfoAudit userinfoAudit = null;

		UserinfoAudit uquery = new UserinfoAudit();
		uquery.setId(item.getId());
		// uquery.setAuditStates(0);
		List<UserinfoAudit> tmps = itemMapper.selectList(uquery);
		if (tmps != null && tmps.size() > 0) {
			userinfoAudit = tmps.get(0);
		}
		
		
		int checkState=userinfoService. CheckOrderAndLocker(locker);
		if(checkState<0)
		{
			msg.setBol(false);
			
			if(checkState==-1)
			{
				msg.setMessage("锁匠当前有未完成工单,暂时不能变更锁匠类型!");
			}
			else if(checkState==-2)
			{
				msg.setMessage("该合伙人下存在锁匠,暂不能变更锁匠类型!");	
			}
			
			return msg;
		}
		
		

		if (item.getAuditStates() == 1) {
			// 通过，变更锁匠信息
			if (userinfoAudit.getUserNewType() ==2) {
				locker.setUserType(userinfoAudit.getUserNewType().toString());
				// 合伙人恢复，检查区域冲突.
				// 处理锁匠-作业区域
				// 添加
				String[] dids = locker.getDistrictids().split(",");

				boolean ischeck = true;
				for (int i = 0; i < dids.length; i++) {
					if (dids[i].trim().equals(""))
						continue;

					UserinfoJobArea jquery = new UserinfoJobArea();
					jquery.setDistrictId(Integer.parseInt(dids[i]));
					jquery.setLockId(locker.getId());
					// 检查作业区域重复
					List<UserinfoJobArea> lockers = userinfoJobAreaMapper.selectParterList(jquery);
					if (lockers != null && lockers.size() > 0) {
						ischeck = false;
						msg.setBol(false);
						msg.setMessage(lockers.get(0).getDistrictName() + "区域下已存在合伙人[" + lockers.get(0).getParterName()
								+ "],此变更无法完成");
						break;
					}
				}

				// 区域检查异常
				if (!ischeck) {
					return msg;
				}
			}

		}

		// 审核
		itemMapper.updateByPrimaryKeySelective(item);

		if (item.getAuditStates() == 1) {
			// 通过，变更锁匠信息

			locker.setUserType(userinfoAudit.getUserNewType().toString());

			if (userinfoAudit.getUserNewType() == 4) {
				// 合伙人下锁匠
				locker.setCompanyId(userinfoAudit.getCompanyId());
				locker.setParterAuditFlag("1");// 合伙人通过
			} else {

			}

			// 锁匠
			userinfoService.updateByPrimaryKeySelective(locker);
			if (userinfoAudit.getUserNewType() != 4) {
				// 清空合伙人
				userinfoService.updateLockerParterNull(locker.getId());
			}
		}

		// 日志

		String curUserId = "";
		if (TokenUtil.getWebLoginUser() != null)
			curUserId = TokenUtil.getWebLoginUser().getId();
		else
			curUserId = item.getAuditor();

		// 插入锁匠类型日志表
		UserinfoOperateLog ulog = new UserinfoOperateLog();
		ulog.setId(UUIDUtil.getUUID());
		ulog.setType(UserinfoOperateLog.UserInfoType.OperateType_Kefu.toString());
		ulog.setUserInfoId(locker.getId().toString());
		ulog.setOperateUserId(curUserId);
		if (item.getAuditStates() == 1)
			ulog.setContent("审核通过：" + locker.getUserTypeName(userinfoAudit.getUserOldType().toString()) + " --> "
					+ locker.getUserTypeName(userinfoAudit.getUserNewType().toString()));
		else
			ulog.setContent("审核未通过：" + item.getAuditFailReason());

		userinfoOperateLogMapper.insert(ulog);

		// 锁匠小程序申请的，发送系统消息
		//
		Userinfo pater = userinfoService.selectUserinfoById(userinfoAudit.getCompanyId());

		String messageStr = "";
		if (userinfoAudit.getProposer().equals(userinfoAudit.getUserInfoId().toString())) // 自主申请的
		{
			if (userinfoAudit.getUserNewType() == 4) {

				if (item.getAuditStates() == 1)
					messageStr = "您加入合伙人[" + pater.getName() + "]团队的申请已通过审批,锁匠类型已变更为合伙人下锁匠";
				else
					messageStr = "您加入合伙人[" + pater.getName() + "]团队的申请未通过审批,拒绝原因:" + item.getAuditFailReason() + "请知悉！";
			}
		} else {
			if (item.getAuditStates() == 1) // 后台便跟， 变更通过,
				messageStr = "您的锁匠类型已通过后台变更为:" + locker.getUserTypeName(userinfoAudit.getUserNewType().toString());
			else //后台不通过不发送消息
				return msg;
		}

		// 插入消息
		UserMessage um = new UserMessage();
		um.setId(snowflakeIdWorker.nextId());
		um.setMessageContent(messageStr);
		um.setMessageType(1L);
		um.setReceiver(userinfoAudit.getUserInfoId());
		// um.setOrderInfoId(orderinfo.getId());
		um.setMessageTitle("锁匠类型变更");
		userMessageMapper.insert(um);
		//

		return msg;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateUserinfoAudit(UserinfoAudit item) {
		JSONObject rtn = new JSONObject();

		if (null == item || null == item.getId()) {
			rtn.put("bol", false);
			rtn.put("message", "id为空");
			return rtn;
		}

		try {
			itemMapper.updateByPrimaryKeySelective(item);

			rtn.put("bol", true);

			return rtn;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("更新出错", e);
			rtn.put("bol", false);
			rtn.put("message", "更新出错");
			return rtn;
		}
	}

	@Override
	public List<UserinfoAudit> selectUserinfoAuditList(UserinfoAudit item) {
		List<UserinfoAudit> itemList = new ArrayList<>();
		try {
			itemList = itemMapper.selectList(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteUserinfoAudit(UserinfoAudit item) {
		int result = 0;
		try {

			result = itemMapper.delete(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return result;
	}

	@Override

	public UserinfoAudit selectUserinfoAuditById(String id) {

		UserinfoAudit data = null;

		UserinfoAudit query = new UserinfoAudit();
		query.setId(id);

		List<UserinfoAudit> datas = selectUserinfoAuditList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

}
