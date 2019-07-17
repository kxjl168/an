package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.base.util.AliSMS.AliSendSMS;
import com.kxjl.tasktransferplat.dao.plus.*;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.*;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog.OperateSubType;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog.OperateType;
import com.kxjl.tasktransferplat.pojo.UserinfoMoneyDetail.MoneyChangeType;
import com.kxjl.tasktransferplat.service.*;
import com.kxjl.tasktransferplat.util.DateUtil;
import com.kxjl.tasktransferplat.util.ParamUtil;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;
import com.kxjl.tasktransferplat.util.TokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class OrderinfoServiceImpl implements OrderinfoService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderinfoMapper itemMapper;

    @Autowired
    private OrderinfoMapper orderinfoMapper;

    @Autowired
    OrderInfoOperateProxy orderInfoOperateProxy;

    @Autowired
    private OrderinfoAfterServiceMapper afterServiceMapper;

    @Autowired
    private UserBackQuestionService tuserbackquestionService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private OrderinfoOperateLogMapper orderinfoOperateLogMapper;

    @Autowired
    private UserinfoMoneyDetailMapper userinfoMoneyDetailMapper;

    @Autowired
    private UserinfoMapper userInfoMapper;

    @Autowired
    private UserMessageMapper userMessageMapper;
    @Autowired
    private RedisLockService redisLockService;

    @Autowired
    UserinfoService userinfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderinfoAttachMoneyDetailMapper orderinfoAttachMoneyDetailMapper;

    @Autowired
    private LockCompanyMapper lockCompanyMapper;

    @Autowired
    private UserinfoJobAreaMapper userinfoJobAreaMapper;
    

    /**
     * 工单查询接口-移动端
     *
     * @param orderListDto
     * @return
     * @author zj
     * @date 2019年5月17日
     */
    @Override
    public IPage orderList(BaseRequestDto orderListDto) {
        // 处理json参数
        String orderRequest = orderListDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);

        // 检查入参是否为空
        ParamUtil.checkArgsNull(paramMap);

        Page page = new Page();

        page.setSize(Long.parseLong(String.valueOf(paramMap.get("pageSize"))));
        page.setCurrent(Long.parseLong(String.valueOf(paramMap.get("pageNum"))));

        // type 1:未接单工单， 2：处理中工单， 3：已完成工单
        Integer type = Integer.valueOf(String.valueOf(paramMap.get("type")));
        Long lockerId = TokenUtil.getCurrentUser().getId();

        Long companyId = null;
        // 查询
        if (TokenUtil.getCurrentUser().getCompanyId() != null) {
            companyId = TokenUtil.getCurrentUser().getCompanyId();
        }
        // // 获取用户的所在省市的编码
        // Userinfo userinfo = userInfoMapper.selectById(lockerId);
        // String userProvinceCityCode = userinfo.getAreaCode().substring(0, 4);
        UserinfoJobArea userinfoJobArea = new UserinfoJobArea();
        userinfoJobArea.setLockId(lockerId);
        List<UserinfoJobArea> userinfoJobAreaList = userinfoJobAreaMapper.selectList(userinfoJobArea);

        // 查询工单
        IPage<Orderinfo> orderPage = orderinfoMapper.selectPage(page, type, lockerId, companyId, userinfoJobAreaList);
        List<Orderinfo> list = orderPage.getRecords();
        for (Orderinfo orderinfo : list) {

            if (orderinfo.getCustAddress() != null && orderinfo.getCustAddress().length() > 15) {
                orderinfo.setCustAddress(orderinfo.getCustAddress().substring(0, 15) + "...");
            }

            orderinfo.setAccountTime(DateUtil.getMinString(orderinfo.getAccountTime()));
            orderinfo.setAppointmentTime(DateUtil.getMinString(orderinfo.getAppointmentTime()));
            orderinfo.setCreateTime(DateUtil.getMinString(orderinfo.getCreateTime()));
            orderinfo.setReceiveTime(DateUtil.getMinString(orderinfo.getReceiveTime()));
            orderinfo.setDoneTime(DateUtil.getMinString(orderinfo.getDoneTime()));

            String serverDes = "";
            if (null != orderinfo.getServerType()) {
                String[] typeList = orderinfo.getServerType().split(",");
                Arrays.sort(typeList); //首先对数组排序
                if (Arrays.binarySearch(typeList, "0") > -1) {
                    serverDes += "安装,";
                }
                if (Arrays.binarySearch(typeList, "1") > -1) {
                    serverDes += "维修,";
                }
                if (Arrays.binarySearch(typeList, "2") > -1) {
                    serverDes += "开锁,";
                }
                if (Arrays.binarySearch(typeList, "3") > -1) {
                    serverDes += "特殊门改造,";
                }
                if (Arrays.binarySearch(typeList, "4") > -1) {
                    serverDes += "测量,";
                }
                if (Arrays.binarySearch(typeList, "5") > -1) {
                    serverDes += "猫眼安装,";
                }
                if (Arrays.binarySearch(typeList, "6") > -1) {
                    serverDes += "换锁,";
                }
                if (Arrays.binarySearch(typeList, "7") > -1) {
                    serverDes += "工程安装,";
                }
                if (Arrays.binarySearch(typeList, "8") > -1) {
                    serverDes += "工程维修,";
                }
                if (Arrays.binarySearch(typeList, "19") > -1) {
                    serverDes += "其他,";
                }
            }

            if (serverDes.length() > 0) {
                serverDes = serverDes.substring(0, serverDes.length() - 1);
            }
            orderinfo.setServerDes(serverDes);
        }
        orderPage.setRecords(list);
        return orderPage;
    }

    /**
     * 工单查询接口-移动端
     *
     * @param orderListDto
     * @return
     * @author zj
     * @date 2019年5月17日
     */
    @Override
    public IPage orderDistributeList(BaseRequestDto orderListDto) {
        // 处理json参数
        String orderRequest = orderListDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);

        // 检查入参是否为空
        ParamUtil.checkArgsNull(paramMap);

        Page page = new Page();

        page.setSize(Long.parseLong(String.valueOf(paramMap.get("pageSize"))));
        page.setCurrent(Long.parseLong(String.valueOf(paramMap.get("pageNum"))));

        Long userId = TokenUtil.getCurrentUser().getId();

        // 查询工单
        IPage<Orderinfo> orderPage = orderinfoMapper.selectDistributePage(page, userId);
        List<Orderinfo> list = orderPage.getRecords();
        for (Orderinfo orderinfo : list) {

            if (orderinfo.getCustAddress() != null && orderinfo.getCustAddress().length() > 15) {
                orderinfo.setCustAddress(orderinfo.getCustAddress().substring(0, 15) + "...");
            }

            orderinfo.setAccountTime(DateUtil.getMinString(orderinfo.getAccountTime()));
            orderinfo.setAppointmentTime(DateUtil.getMinString(orderinfo.getAppointmentTime()));
            orderinfo.setCreateTime(DateUtil.getMinString(orderinfo.getCreateTime()));
            orderinfo.setReceiveTime(DateUtil.getMinString(orderinfo.getReceiveTime()));
            orderinfo.setDoneTime(DateUtil.getMinString(orderinfo.getDoneTime()));

            String serverDes = "";
            if (null != orderinfo.getServerType()) {
                String[] typeList = orderinfo.getServerType().split(",");
                Arrays.sort(typeList); //首先对数组排序
                if (Arrays.binarySearch(typeList, "0") > -1) {
                    serverDes += "安装,";
                }
                if (Arrays.binarySearch(typeList, "1") > -1) {
                    serverDes += "维修,";
                }
                if (Arrays.binarySearch(typeList, "2") > -1) {
                    serverDes += "开锁,";
                }
                if (Arrays.binarySearch(typeList, "3") > -1) {
                    serverDes += "特殊门改造,";
                }
                if (Arrays.binarySearch(typeList, "4") > -1) {
                    serverDes += "测量,";
                }
                if (Arrays.binarySearch(typeList, "5") > -1) {
                    serverDes += "猫眼安装,";
                }
                if (Arrays.binarySearch(typeList, "6") > -1) {
                    serverDes += "换锁,";
                }
                if (Arrays.binarySearch(typeList, "7") > -1) {
                    serverDes += "工程安装,";
                }
                if (Arrays.binarySearch(typeList, "8") > -1) {
                    serverDes += "工程维修,";
                }
                if (Arrays.binarySearch(typeList, "19") > -1) {
                    serverDes += "其他,";
                }
            }
            if (serverDes.length() > 0) {
                serverDes = serverDes.substring(0, serverDes.length() - 1);
            }
            orderinfo.setServerDes(serverDes);
        }
        orderPage.setRecords(list);
        return orderPage;
    }

    /**
     * 综合查询全部工单
     *
     * @param orderinfo
     * @return
     */
    @Override
    public List<Orderinfo> selectAllOrderInfoList(Orderinfo orderinfo) {

        List<Orderinfo> itemList = orderinfoMapper.selectALLList(orderinfo);
        /*
		 * for (Orderinfo orderinfo1 : itemList) { if
		 * (!isBlank(orderinfo1.getAppointmentTime())) {
		 * orderinfo1.setAppointmentTime(orderinfo1.getAppointmentTime().substring(0,
		 * orderinfo1.getAppointmentTime().lastIndexOf(":"))); } }
		 */
        return itemList;
    }

    /**
     * 查询工单详情
     *
     * @param requestDto
     * @return
     */
    @Override
    public Orderinfo orderDetail(BaseRequestDto requestDto) {

        // 处理json参数
        String orderRequest = requestDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);

        // 检查入参是否为空
        ParamUtil.checkArgsNull(paramMap);

        String orderId = String.valueOf(paramMap.get("orderId"));
        Orderinfo orderinfo = null;

        try {
            orderinfo = orderinfoMapper.selectorderinfoById(orderId);
            
            
            

            
            // 判断是否被打回
            OrderinfoOperateLog orderinfoOperateLog = orderinfoOperateLogMapper.selectRecentReason(orderId);
            if (orderinfoOperateLog != null) {
                orderinfo.setBackReason(orderinfoOperateLog.getContent());
            }

            orderinfo.setAccountTime(DateUtil.getMinString(orderinfo.getAccountTime()));
            orderinfo.setAppointmentTime(DateUtil.getMinString(orderinfo.getAppointmentTime()));
            orderinfo.setCreateTime(DateUtil.getMinString(orderinfo.getCreateTime()));
            orderinfo.setReceiveTime(DateUtil.getMinString(orderinfo.getReceiveTime()));
            orderinfo.setDoneTime(DateUtil.getMinString(orderinfo.getDoneTime()));

            String serverDes = "";
            if (null != orderinfo.getServerType()) {
                String[] typeList = orderinfo.getServerType().split(",");
                Arrays.sort(typeList); //首先对数组排序
                if (Arrays.binarySearch(typeList, "0") > -1) {
                    serverDes += "安装,";
                }
                if (Arrays.binarySearch(typeList, "1") > -1) {
                    serverDes += "维修,";
                }
                if (Arrays.binarySearch(typeList, "2") > -1) {
                    serverDes += "开锁,";
                }
                if (Arrays.binarySearch(typeList, "3") > -1) {
                    serverDes += "特殊门改造,";
                }
                if (Arrays.binarySearch(typeList, "4") > -1) {
                    serverDes += "测量,";
                }
                if (Arrays.binarySearch(typeList, "5") > -1) {
                    serverDes += "猫眼安装,";
                }
                if (Arrays.binarySearch(typeList, "6") > -1) {
                    serverDes += "换锁,";
                }
                if (Arrays.binarySearch(typeList, "7") > -1) {
                    serverDes += "工程安装,";
                }
                if (Arrays.binarySearch(typeList, "8") > -1) {
                    serverDes += "工程维修,";
                }
                if (Arrays.binarySearch(typeList, "19") > -1) {
                    serverDes += "其他,";
                }
            }
            if (serverDes.length() > 0) {
                serverDes = serverDes.substring(0, serverDes.length() - 1);
            }
            orderinfo.setServerDes(serverDes);
        } catch (Exception e) {
            throw new RuntimeException("异常工单id" + orderId, e);
        }
        return orderinfo;
    }

    /**
     * 查询工单详情
     *
     * @param id
     * @return
     */
    @Override
    public Orderinfo loadOrderinfoById(String id) {
        Orderinfo orderinfo = orderinfoMapper.selectById(id);

        // 设置锁企信息
        LockCompany company = lockCompanyMapper.selectByPrimaryKey(orderinfo.getSellerId());

        if (company != null) {
            orderinfo.setLockCompanyName(company.getEnterpriseName());
        }

		/*
		 * // 设置是否加急信息 QueryWrapper wrapper = new QueryWrapper();
		 * wrapper.eq("OrderinfoId", id); wrapper.eq("reasonType", 5);
		 * OrderinfoAttachMoneyDetail detail =
		 * orderinfoAttachMoneyDetailMapper.selectOne(wrapper); if (detail == null) {
		 * orderinfo.setUrgent(0); } else { orderinfo.setUrgent(1); }
		 */
        String appointmentTime = orderinfo.getAppointmentTime();
        if (!isBlank(appointmentTime)) {
            orderinfo.setAppointmentTime(appointmentTime.split("[.]")[0]);
        }
        return orderinfo;
    }

    /**
     * 查询带创建工单
     *
     * @param orderinfo
     * @return
     */
    @Override
    public List<Orderinfo> selectCreateOrderInfoList(Orderinfo orderinfo) {
        orderinfo.setType(0);
        orderinfo.setDataState(1);// 正常状态
        if (orderinfo.getServerType() != null) {
            orderinfo.setServerTypeGet(String.valueOf(orderinfo.getServerType()));
        }
        List<Orderinfo> itemList = orderinfoMapper.selectOrderInfoList(orderinfo);
        for (Orderinfo orderinfo1 : itemList) {
            if (!isBlank(orderinfo1.getAppointmentTime())) {
                orderinfo1.setAppointmentTime(
                        orderinfo1.getAppointmentTime().substring(0, orderinfo1.getAppointmentTime().lastIndexOf(":")));
            }
        }
        return itemList;
    }

    @Override
    public Map selectOrderNumInfo(Orderinfo orderinfo) {

        Map map = new HashMap<>();
        orderinfo.setDataState(0);// 废弃的工单

        List<Orderinfo> huicheList = orderinfoMapper.selectOrderInfoList(orderinfo);

        orderinfo.setDataState(1);

        orderinfo.setType(0);
        List<Orderinfo> toComfirmList = orderinfoMapper.selectOrderInfoList(orderinfo);

        orderinfo.setType(1);
        List<Orderinfo> todoList = orderinfoMapper.selectOrderInfoList(orderinfo);

        orderinfo.setType(2);
        List<Orderinfo> doingList = orderinfoMapper.selectOrderInfoList(orderinfo);

        orderinfo.setType(6);
        List<Orderinfo> toAuditList = orderinfoMapper.selectOrderInfoList(orderinfo);

        orderinfo.setType(5);
        List<Orderinfo> completeList = orderinfoMapper.selectOrderInfoList(orderinfo);

        orderinfo.setType(3);
        List<Orderinfo> huifangList = orderinfoMapper.selectOrderInfoList(orderinfo);

        OrderinfoAfterService afterquery = new OrderinfoAfterService();
        afterquery.setServiceState(0);
        List<OrderinfoAfterService> afterSerivceList = afterServiceMapper.selectList(afterquery);

        List<UserBackQuestion> tuserbackquestions = new ArrayList<>();
        UserBackQuestion item = new UserBackQuestion();
        item.setServiceState(0); // 新提交问题
        tuserbackquestions = tuserbackquestionService.selectUserBackQuestionList(item);

        map.put("numToConfirm", toComfirmList.size());
        map.put("numToDo", todoList.size());
        map.put("numDoing", doingList.size());
        map.put("numAudit", toAuditList.size());
        map.put("numHuifang", huifangList.size());
        map.put("numHuiche", huicheList.size());
        map.put("numCompleted", completeList.size());

        map.put("numBack", tuserbackquestions.size());
        map.put("numAfter", afterSerivceList.size());

        return map;
    }

    @Override
    public Orderinfo selectOrderByOrerNo(String orderNo) {
        return orderinfoMapper.selectByOrderNo(orderNo);
    }

    /**
     * 查询待接单工单
     *
     * @param orderinfo
     * @return
     */
    @Override
    public List<Orderinfo> selectTodoOrderInfoList(Orderinfo orderinfo) {
        orderinfo.setType(1);
        orderinfo.setDataState(1);// 正常状态
        if (orderinfo.getServerType() != null) {
            orderinfo.setServerTypeGet(String.valueOf(orderinfo.getServerType()));
        }
        List<Orderinfo> itemList = orderinfoMapper.selectOrderInfoList(orderinfo);
        for (Orderinfo orderinfo1 : itemList) {
            if (!isBlank(orderinfo1.getAppointmentTime())) {
                orderinfo1.setAppointmentTime(
                        orderinfo1.getAppointmentTime().substring(0, orderinfo1.getAppointmentTime().lastIndexOf(":")));
            }
        }
        return itemList;
    }

    /**
     * 查询待作业工单
     *
     * @param item
     * @return
     */
    @Override
    public List<Orderinfo> selectDoingOrderInfoList(Orderinfo item) {
        if (item.getType() == null) {
            item.setType(2);
        }
        item.setDataState(1);// 正常状态
        if (item.getServerType() != null) {
            item.setServerTypeGet(String.valueOf(item.getServerType()));
        }
        List<Orderinfo> itemList = orderinfoMapper.selectOrderInfoList(item);
        for (Orderinfo orderinfo1 : itemList) {
            if (!isBlank(orderinfo1.getAppointmentTime())) {
                orderinfo1.setAppointmentTime(
                        orderinfo1.getAppointmentTime().substring(0, orderinfo1.getAppointmentTime().lastIndexOf(":")));
            }
        }
        return itemList;
    }

    /**
     * 查询待审核工单
     *
     * @param item
     * @return
     */
    @Override
    public List<Orderinfo> selectReviewOrderInfoList(Orderinfo item) {
        item.setType(6);
        item.setDataState(1);// 正常状态
        if (item.getServerType() != null) {
            item.setServerTypeGet(String.valueOf(item.getServerType()));
        }
        List<Orderinfo> itemList = orderinfoMapper.selectOrderInfoList(item);
        for (Orderinfo orderinfo1 : itemList) {
            if (!isBlank(orderinfo1.getAppointmentTime())) {
                orderinfo1.setAppointmentTime(
                        orderinfo1.getAppointmentTime().substring(0, orderinfo1.getAppointmentTime().lastIndexOf(":")));
            }
        }
        return itemList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject NoUseOrderinfo(Manager operater, Orderinfo item) {
        JSONObject rtn = new JSONObject();

        if (item.getId() == null) {
            rtn.put("bol", false);
            rtn.put("message", "id为空");
            return rtn;
        }

        try {
            item.setDataState(0);
            item.setDeleteUseType(operater.getCompanyId() == null ? "1" : "2");// 废弃方


            Orderinfo oinfo = orderinfoMapper.selectById(item.getId());

            // 如果有处理锁匠，发送消息
            if (oinfo.getLockerId() != null) {
                String messageStr = "您好，您的工单号为" + oinfo.getOrderNo() + "的工单，已在后台被废弃,原因说明:" + item.getDeleteReason()
                        + ",请及时查看！";
                // 插入消息
                UserMessage um = new UserMessage();
                um.setId(snowflakeIdWorker.nextId());
                um.setMessageContent(messageStr);
                um.setMessageType(2L);
                um.setReceiver(oinfo.getLockerId());
                um.setOrderInfoId(oinfo.getId());
                um.setMessageTitle("工单废弃");
                userMessageMapper.insert(um);
            }


            itemMapper.updateByPrimaryKeySelective(item);

            // 插入工单日志表
            OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
            orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
            orderinfoOperateLog.setOrderinfoId(item.getId());
            orderinfoOperateLog.setType(operater.getCompanyId() == null ? 1 : 0);// 1源匠，0锁企
            orderinfoOperateLog.setSubType(-1);
            orderinfoOperateLog.setOperateUserId(operater.getId());
            orderinfoOperateLog.setContent("废弃说明：" + item.getDeleteReason());

            orderinfoOperateLogMapper.insert(orderinfoOperateLog);

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
    @Transactional(rollbackFor = Exception.class)
    public JSONObject ReUseOrderinfo(Manager operater, Orderinfo item) {
        JSONObject rtn = new JSONObject();

        if (item.getId() == null) {
            rtn.put("bol", false);
            rtn.put("message", "id为空");
            return rtn;
        }

        try {
            String rType = operater.getCompanyId() == null ? "1" : "2";

            Orderinfo oinfo = itemMapper.selectByPrimaryKey(item.getId());
            if (!oinfo.getDeleteUseType().equals(rType)) {
                rtn.put("bol", false);
                rtn.put("message", "只能恢复己方废弃的工单");
                return rtn;
            }

            item.setDataState(1);
            itemMapper.updateByPrimaryKeySelective(item);

            // 插入工单日志表
            OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
            orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
            orderinfoOperateLog.setOrderinfoId(item.getId());
            orderinfoOperateLog.setType(operater.getCompanyId() == null ? 1 : 0);// 1源匠，0锁企
            orderinfoOperateLog.setSubType(-2); //
            orderinfoOperateLog.setOperateUserId(operater.getId());
            orderinfoOperateLog.setContent("恢复");

            orderinfoOperateLogMapper.insert(orderinfoOperateLog);

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

    /**
     * 更新工单信息
     *
     * @param orderinfo
     */
    @Override
    public void updateOrder(Orderinfo orderinfo) {
        orderinfoMapper.updateById(orderinfo);
    }

    /**
     * 导入工单
     *
     * @param packageList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importOrderInfo(List<List> packageList) {

        // 插入工单
        List<Orderinfo> orderinfoList = packageList.get(0);
        if (orderinfoList == null || orderinfoList.isEmpty()) {
            throw new RuntimeException("不能插入空");
        }
        // lzq 625
		/*
		 * for (int i = 0; i < orderinfoList.size(); i++) {
		 * orderinfoList.get(i).setIsRoomNessary(1); }
		 */

        // zj 190617
        Manager manger = TokenUtil.getWebLoginUser();
        for (int i = 0; i < orderinfoList.size(); i++) {
            orderInfoOperateProxy.createOrder(orderinfoList.get(i), manger);
        }
        // orderinfo.setId(UUIDUtil.getUUID());

    }

    /**
     * 根据锁匠id查询未完成工单列表
     *
     * @param lockerId
     * @return
     */
    @Override
    public List<Orderinfo> selectOrderinfoByLockerId(Long lockerId) {

        return itemMapper.selectLockerUnFinishOrder(lockerId);

		/*
		 * QueryWrapper wrapper = new QueryWrapper(); wrapper.eq("lockerId", lockerId);
		 * wrapper.le("orderState", 499); return orderInfoMapper.selectList(wrapper);
		 */
    }

	/*
	 * =========================================wrote by
	 * 单肙===============================================
	 *
	 */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateOrderinfo(Orderinfo item) {
        JSONObject rtn = new JSONObject();

        if (item.getId() == null) {
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
    public List<Orderinfo> selectOrderinfoList(Orderinfo item) {
        List<Orderinfo> itemList = new ArrayList<>();
        try {
            if (item.getServerType() != null) {
                item.setServerTypeGet(String.valueOf(item.getServerType()));
            }
            itemList = itemMapper.selectOrderInfoList(item);
            for (Orderinfo orderinfo : itemList) {
                if (!isBlank(orderinfo.getAppointmentTime())) {
                    orderinfo.setAppointmentTime(orderinfo.getAppointmentTime().substring(0,
                            orderinfo.getAppointmentTime().lastIndexOf(":")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList;
    }

    /**
     * @return
     */
    @Override
    public Map selectOrderinfoDetail(String orderId) {
        Map result = new HashMap();
        try {
            Orderinfo orderinfo = itemMapper.selectById(orderId);// 查询订单详情
            List<OrderinfoOperateLog> orderinfoOperateLogs = orderinfoOperateLogMapper.selectListByOrderId(orderId);// 查询订单流转记录
            // 封装结果集
            result.put("orderinfoLog", orderinfoOperateLogs);
            result.put("orderinfoDetail", orderinfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("锁企查询订单列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return result;
    }

    @Override
    public void orderEdite(String operateUserId, BaseRequestDto requestDto) {
        String orderRequest = requestDto.getData();
        Map<String, String> paramMap = (Map<String, String>) JSON.parse(orderRequest);
        String orderId = String.valueOf(paramMap.get("orderId"));
        String appointmentTime = String.valueOf(paramMap.get("appointmentTime"));
        Orderinfo orderinfo = orderinfoMapper.selectById(orderId);
        orderinfo.setAppointmentTime(appointmentTime);
        orderinfoMapper.updateById(orderinfo);
    }

    @Override
    public Orderinfo selectOrderinfoByIdAndLockId(String orderId) {
        return orderinfoMapper.selectOrderinfoByIdAndLockId(orderId);
    }

    @Override
    public int selectCount(String id) {
        return orderinfoMapper.selectCountNum(id);
    }

    // @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrderByCompany(Map<String, String> paramMap) {
		/*
		 * //处理入参参数json String orderRequest = requestDto.getData(); Map<String, String>
		 * paramMap = (Map<String, String>) JSON.parse(orderRequest);
		 */
        try {
            paramMap.put("id", String.valueOf(snowflakeIdWorker.nextId()));// 插入订单ID
            paramMap.put("orderNo", DateUtil.timestampOrderNo());// 插入订单编号
            paramMap.put("createUserId", TokenUtil.getWebLoginUser().getId());// 插入创建人
            // 新增订单
            orderinfoMapper.insertByParamMap(paramMap);
            // 查询新增的订单
            Orderinfo orderinfolog = orderinfoMapper.selectByOrderNo(paramMap.get("orderNo"));
            // 封装订单记录参数
            OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
            orderinfoOperateLog.setId(snowflakeIdWorker.nextId());// 日志id
            orderinfoOperateLog.setOrderinfoId(orderinfolog.getId());
            orderinfoOperateLog.setType(OperateType.OperateType_LockCompany.type);// 锁企操作
            orderinfoOperateLog.setSubType(OperateSubType.OperateSubType_LCompany_CreateOrder.type);// 锁企创建订单
            orderinfoOperateLog.setOperateUserId(TokenUtil.getWebLoginUser().getId());// 创建人
            orderinfoOperateLog.setContent(null == (orderinfolog.getContent()) ? orderinfolog.getContent() : null);// 详细内容，可空
            // 插入订单历史
            orderinfoOperateLogMapper.insert(orderinfoOperateLog);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("锁企新增订单出错");
            log.error(ExceptionUntil.getMessage(e));
        }
    }

    /**
     * 查询订单统计
     *
     * @param month
     * @param orderinfo
     * @return
     */
    @Override
    public List selectOrderCount(String month, Orderinfo orderinfo) {

        return null;
    }

    @Override
    public int countCompanyPayOrder(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("FinishedState", 0);
        queryWrapper.eq("DataState", 1);
        if (id != null) {
            queryWrapper.eq("SellerId", id);
        }
        return orderinfoMapper.selectCount(queryWrapper);
    }

    @Override
    public int countHuifOrder(Orderinfo item) {
        item.setType(3);
        return itemMapper.selectStateCount(item);
    }

    @Override
    public int countPayOrder(Orderinfo item) {
        item.setType(4);
        return itemMapper.selectStateCount(item);
    }

    @Override
    public int countCompletedOrder(Orderinfo item) {
        item.setType(5);
        return itemMapper.selectStateCount(item);
    }

    @Override
    public List<Map<String, Object>> countHuiFlockOrder(Orderinfo item) {
        List<Map<String, Object>> map = new ArrayList<>();
        try {
            map = itemMapper.selectHuiFlockCount(item);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询已作业总数出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> countHuiFlockOrderPass(Orderinfo item) {
        List<Map<String, Object>> map = new ArrayList<>();
        try {

            item.setType(4);
            map = itemMapper.selectHuiFlockPassCount(item);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询已作业总数出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return map;
    }

    @Override
    public int countNumOrderCompany(Orderinfo item) {
        int result = 0;
        try {
            item.setType(1);
            result = itemMapper.selectCountCompany(item);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询锁企下发数量出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> countFinishLockOrder(Orderinfo item) {
        List<Map<String, Object>> map = new ArrayList<>();
        try {
            OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
            orderinfoOperateLog.setCompanyId(item.getCompanyId());
            orderinfoOperateLog.setType(1);
            orderinfoOperateLog.setSubType(5);
            orderinfoOperateLog.setOperateTime(item.getCreateTime());
            map = orderinfoOperateLogMapper.selectOrderCount(orderinfoOperateLog);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询工单总数出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteOrderinfo(Orderinfo item) {
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

    public Orderinfo selectOrderinfoById(String id) {
        Orderinfo data = null;

        Orderinfo query = new Orderinfo();
        query.setId(id);

        List<Orderinfo> datas = selectOrderinfoList(query);
        if (datas != null && datas.size() > 0) {
            data = datas.get(0);

        }
        return data;

    }

    @Override
    public Orderinfo selectOrderinfoByOrerNo(String id) {
        Orderinfo data = null;

        Orderinfo query = new Orderinfo();
        query.setOrderNo(id);

        List<Orderinfo> datas = selectOrderinfoList(query);
        if (datas != null && datas.size() > 0) {
            data = datas.get(0);

        }
        return data;

    }

    @Override
    public List<Orderinfo> selectOrderinfoListByComplete(Orderinfo item) {
        List<Orderinfo> itemList = new ArrayList<>();
        item.setType(5);
        item.setDataState(1);// 正常状态
        try {
            if (item.getServerType() != null) {
                item.setServerTypeGet(String.valueOf(item.getServerType()));
            }
            itemList = orderinfoMapper.selectOrderInfoList(item);
            List<Orderinfo> price = orderinfoMapper.selectAddPrice(item);
            for (Orderinfo orderinfo : itemList) {
                if (!isBlank(orderinfo.getAppointmentTime())) {
                    orderinfo.setAppointmentTime(orderinfo.getAppointmentTime().substring(0,
                            orderinfo.getAppointmentTime().lastIndexOf(":")));
                }
                for (Orderinfo orderinfo1 : price) {
                    if (orderinfo.getId().equals(orderinfo1.getId())) {
                        orderinfo.setPrice(orderinfo1.getPrice());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList;
    }

    /**
     * @param item
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject saveOrderinfo(Orderinfo item) {
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

    /**
     * 完成结账操作，并记录日志
     *
     * @param item
     * @return
     * @author zj
     * @date 2019年1月31日
     */
    @Transactional
    @Override
    public JSONObject ComplatePayOrderinfo(Orderinfo item) {
        JSONObject rtn = new JSONObject();
        String version = String.valueOf(redisLockService.getVersion());
        String orderId = item.getId();
        try {

            // 加锁
            boolean lockResult = redisLockService.blockLock("acceptOrderLock_" + orderId, version);
            if (lockResult) {

                item.setOrderState(501);// 工单完结
                item.setAccountTime(com.kxjl.base.util.DateUtil.getNowStr(""));

                Orderinfo oitem = itemMapper.selectById(item.getId());

                // zj 190611
                // 根据当前工单的锁匠类型，确定工单的结账类型，金额打入锁匠或者合伙人账号
                // `usertype` varchar(2) DEFAULT '1' COMMENT '用户类型 签约锁匠=1, 合伙人锁匠 = 2,代理人锁匠 = 3,
                // 合伙人下的锁匠 = 4, 自营锁匠=5,临时锁匠=6 ',
                // `depositType` varchar(2) NOT NULL DEFAULT '0' COMMENT '提现类型， 0：现结，1：周结，2：月结
                Userinfo user = userInfoMapper.selectByPrimaryKey(oitem.getLockerId());

                // 收钱用户
                Userinfo getMoneyUser = user;
				/*
				 * if (user.getUserType().equals("4")) { // 合伙人下锁匠 ，钱打给合伙人 getMoneyUser =
				 * userInfoMapper.selectByPrimaryKey(oitem.getCompanyId()); } else { // 其他都打给自己
				 * getMoneyUser = user; }
				 */
                // 按工单当时的情况处理，是否有合伙人
                if (oitem.getCompanyId() != null) {
                    // 单子有合伙人，则打给呵护荣恩
                    getMoneyUser = userInfoMapper.selectByPrimaryKey(oitem.getCompanyId());
                } else {
                    // 其他都打给自己
                    getMoneyUser = user;
                }

                // 更新工单信息，及工单结账类型，按当时锁匠的结账类型计算
                item.setOrderCashType(user.getDepositType());
                itemMapper.updateByPrimaryKeySelective(item);

                Userinfo UMoney = userinfoService.getUserMoneyInfo(getMoneyUser);
                // 当前重新计算的总价；
                BigDecimal total = UMoney.getTotal().subtract(UMoney.getDoneMoney());
                // 插入用户账户变更记录
                // BigDecimal totalNow =
                // getMoneyUser.getAccountMoney().add(oitem.getLockerTotalPrice());

                UserinfoMoneyDetail moneyDetail = new UserinfoMoneyDetail();
                moneyDetail.setId(snowflakeIdWorker.nextId());
                moneyDetail.setMoneyNo(DateUtil.timestampOrderNo());
                moneyDetail.setUserinfoId(getMoneyUser.getId());
                moneyDetail.setOperateTime(com.kxjl.base.util.DateUtil.getNowStr(""));
                moneyDetail.setReasonType(MoneyChangeType.OperateType_Income.type);
                moneyDetail.setOperateUserId(String.valueOf(TokenUtil.getWebLoginUser().getId()));
                moneyDetail.setReasonDes("工单完成,完成锁匠:" + user.getName() + "ID:" + user.getId().toString());
                moneyDetail.setChangeValue(oitem.getLockerTotalPrice());
                moneyDetail.setEffectiveId(oitem.getOrderNo()); // 工单NO
                moneyDetail.setTotalPrice(total);
                userinfoMoneyDetailMapper.insert(moneyDetail);

                // 更新用户钱包
                userinfoService.refreshUserMoneyInfo(getMoneyUser);

                // 完成结账，插入记录
                // 插入工单日志表
                OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
                orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
                orderinfoOperateLog.setOrderinfoId(item.getId());
                orderinfoOperateLog.setType(OperateType.OperateType_Kefu.type);// 客服操作
                orderinfoOperateLog.setSubType(OperateSubType.OperateSubType_pay.type);// 结账
                orderinfoOperateLog.setOperateUserId(String.valueOf(TokenUtil.getWebLoginUser().getId()));

                // orderinfoOperateLog.setContent(item.getContent());
                orderinfoOperateLog
                        .setContent("回访完成自动入账,锁匠:" + getMoneyUser.getName() + " ID:" + getMoneyUser.getId().toString());
                orderinfoOperateLogMapper.insert(orderinfoOperateLog);

                String messageStr = "工单号[" + item.getOrderNo() + "],完成锁匠" + user.getName() + ",用户回访已完成,余额增加"
                        + oitem.getLockerTotalPrice() + ",请知悉.";
                // 插入消息
                UserMessage um = new UserMessage();
                um.setId(snowflakeIdWorker.nextId());
                um.setMessageContent(messageStr);
                um.setMessageType(2L);
                um.setReceiver(getMoneyUser.getId());
                um.setOrderInfoId(item.getId());
                um.setMessageTitle("工单结账");
                userMessageMapper.insert(um);

                rtn.put("bol", true);
                return rtn;
            } else {
                rtn.put("bol", false);
                return rtn;
            }

        } catch (Exception e) {
            // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            throw new RuntimeException("结账失败!");

        } finally {
            redisLockService.unlock("acceptOrderLock_" + orderId);
        }
    }

    @Override
    public List<Orderinfo> selectPointList(Long id) {
        List<Orderinfo> itemList = new ArrayList<>();
        try {
            itemList = itemMapper.selectPointList(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject ComplateHuifOrderinfo(Orderinfo item) {
        JSONObject rtn = new JSONObject();

        try {

            if (item.getType() == 0) {// 完成
                item.setOrderState(401);// 待结账
            } else if (item.getType() == 1) {// 未完成
                item.setOrderState(255);// 待作业-打回

                // 发送消息

                Orderinfo oinfo = orderinfoMapper.selectByPrimaryKey(item.getId());

                Userinfo Locker = userinfoService.selectUserinfoById(oinfo.getLockerId());
                String msg = "工单回访未完成";
                // todo 3、推送
                AliSendSMS.sendMessageNoraml(Locker.getPhone(), msg);

                String messageStr = "您上报的已完成作业工单，工单号[" + item.getOrderNo() + "],用户回访过程中被评定为未完成,原因:" + item.getContent()
                        + ",请尽快重新处理.";
                // 插入消息
                UserMessage um = new UserMessage();
                um.setId(snowflakeIdWorker.nextId());
                um.setMessageContent(messageStr);
                um.setMessageType(2L);
                um.setReceiver(Locker.getId());
                um.setOrderInfoId(item.getId());
                um.setMessageTitle("工单回访未完成");
                userMessageMapper.insert(um);
            }

            itemMapper.updateByPrimaryKeySelective(item);

            // 完成回访，插入记录
            // 插入工单日志表
            OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
            orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
            orderinfoOperateLog.setOrderinfoId(item.getId());
            orderinfoOperateLog.setType(OperateType.OperateType_Kefu.type);// 客服操作
            orderinfoOperateLog.setSubType(OperateSubType.OperateSubType_huifang.type);// 回访
            orderinfoOperateLog.setOperateUserId(String.valueOf(TokenUtil.getWebLoginUser().getId()));

            if (item.getType() == 0) {// 完成
                orderinfoOperateLog.setContent("回访完成:" + item.getContent());
            } else if (item.getType() == 1) {// 未完成
                orderinfoOperateLog.setContent("回访未完成:" + item.getContent());
            }

            orderinfoOperateLogMapper.insert(orderinfoOperateLog);

            if (item.getType() == 0) {
                // zj 190604
                // 回访完成直接自动结账
                rtn = ComplatePayOrderinfo(item);
            }

            rtn.put("bol", true);

            return rtn;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新出错", e);
            rtn.put("bol", false);
            rtn.put("message", "操作出错");
            return rtn;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject checkoutOrderinfo(Orderinfo item) {
        JSONObject rtn = new JSONObject();

        if (item.getId() == null) {
            rtn.put("bol", false);
            rtn.put("message", "id为空");
            return rtn;
        }

        try {
            itemMapper.updateByPrimaryKeySelective(item);

            rtn.put("bol", true);

            // 插入操作日志
            OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
            orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
            orderinfoOperateLog.setOrderinfoId(item.getId());
            orderinfoOperateLog.setType(OperateType.OperateType_Kefu.type);// 客服操作
            orderinfoOperateLog.setSubType(OperateSubType.OperateSubType_playMoney.type);// 锁企打款
            orderinfoOperateLog.setOperateUserId(String.valueOf(TokenUtil.getWebLoginUser().getId()));
            orderinfoOperateLogMapper.insert(orderinfoOperateLog);

            return rtn;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新出错", e);
            rtn.put("bol", false);
            rtn.put("message", "操作出错");
            return rtn;
        }
    }

    @Override
    public List<Orderinfo> selectOrderinfoLists(Orderinfo item) {
        List<Orderinfo> itemList = new ArrayList<>();
        try {
            if (item.getServerType() != null) {
                item.setServerTypeGet(String.valueOf(item.getServerType()));
            }
            itemList = itemMapper.selectOrderInfoLists(item);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList;
    }

    @Override
    public int selectPartnerUnFinishOrderCount(Long companyId) {
        int count = 0;
        try {
            count = orderinfoMapper.selectPartnerUnFinishOrderCount(companyId);
        } catch (Exception e) {
            e.printStackTrace();
            return count;
        }
        return count;
    }

    @Override
    public int selecLockCompanyUnFinishOrderCount(Long sellerId) {
        int count = 0;
        try {
            count = orderinfoMapper.selecLockCompanyUnFinishOrderCount(sellerId);
        } catch (Exception e) {
            e.printStackTrace();
            return count;
        }
        return count;
    }

    @Override
    public JSONObject selectListByWithdraw(BaseRequestDto orderListDto) {
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(orderListDto.getData());
            String name = jsonObject.getString("name");
            String phone = jsonObject.getString("phone");
            String orderNo = jsonObject.getString("orderNo");
            String doneTime = jsonObject.getString("doneTime");
            Long userId = TokenUtil.getCurrentUser().getId();

            int pageSize = jsonObject.getInteger("pageSize");
            int pageNum = jsonObject.getInteger("pageNum");

            Page page = new Page(pageNum, pageSize);

            IPage<Map<String, Object>> orderinfoList = orderinfoMapper.selectMobileListByWithdraw(page, userId, orderNo,
                    name, phone, doneTime);
            for (int i = 0; i < orderinfoList.getRecords().size(); i++) {
                String serverDes = "";
                if (null != orderinfoList.getRecords().get(i).get("serverType").toString()) {
                    String[] typeList = orderinfoList.getRecords().get(i).get("serverType").toString().split(",");
                    Arrays.sort(typeList); //首先对数组排序
                    if (Arrays.binarySearch(typeList, "0") > -1) {
                        serverDes += "安装,";
                    }
                    if (Arrays.binarySearch(typeList, "1") > -1) {
                        serverDes += "维修,";
                    }
                    if (Arrays.binarySearch(typeList, "2") > -1) {
                        serverDes += "开锁,";
                    }
                    if (Arrays.binarySearch(typeList, "3") > -1) {
                        serverDes += "特殊门改造,";
                    }
                    if (Arrays.binarySearch(typeList, "4") > -1) {
                        serverDes += "测量,";
                    }
                    if (Arrays.binarySearch(typeList, "5") > -1) {
                        serverDes += "猫眼安装,";
                    }
                    if (Arrays.binarySearch(typeList, "6") > -1) {
                        serverDes += "换锁,";
                    }
                    if (Arrays.binarySearch(typeList, "7") > -1) {
                        serverDes += "工程安装,";
                    }
                    if (Arrays.binarySearch(typeList, "8") > -1) {
                        serverDes += "工程维修,";
                    }
                    if (Arrays.binarySearch(typeList, "19") > -1) {
                        serverDes += "其他,";
                    }
                }
                if (serverDes.length() > 0) {
                    serverDes = serverDes.substring(0, serverDes.length() - 1);
                }
                orderinfoList.getRecords().get(i).put("serverDes", serverDes);
            }

            Object objMoney = orderinfoMapper.selectTotalMoneyByWithdraw(userId, orderNo, name, phone, doneTime, "flag");
            jsonObject = new JSONObject();
            jsonObject.put("totalMoney", objMoney == null ? 0.00 : Double.parseDouble(objMoney.toString()));
            jsonObject.put("records", orderinfoList.getRecords());
            jsonObject.put("totalPage", orderinfoList.getPages());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询出错");
        }
        return jsonObject;
    }

    /**
     * 锁企工单报表
     *
     * @param item
     * @return
     */
    @Override
    public List<Map<String, Object>> selectLockerSmithEnterpriseFinancialList(Orderinfo item) {
        List<Map<String, Object>> itemList = new ArrayList<>();
        try {
            if (item.getServerType() != null) {
                item.setServerTypeGet(String.valueOf(item.getServerType()));
            }
            itemList = itemMapper.selectLockerSmithEnterpriseFinancialList(item);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList;
    }

}
