package com.kxjl.tasktransferplat.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kxjl.base.dao.ManagerMapper;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoOperateLogMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoMapper;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.OrderinfoAudit;
import com.kxjl.tasktransferplat.pojo.OrderinfoOperateLog;
import com.kxjl.tasktransferplat.service.OrderinfoOperateLogService;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class OrderinfoOperateLogServiceImpl implements OrderinfoOperateLogService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderinfoOperateLogMapper itemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private UserinfoMapper userinfoMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * @param item
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject saveOrderinfoOperateLog(OrderinfoOperateLog item) {
        JSONObject rtn = new JSONObject();


        try {

            item.setId(snowflakeIdWorker.nextId());


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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateOrderinfoOperateLog(OrderinfoOperateLog item) {
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
    public List<OrderinfoOperateLog> selectOrderinfoOperateLogList(OrderinfoOperateLog item) {
        List<OrderinfoOperateLog> itemList = new ArrayList<>();
        try {
            itemList = itemMapper.selectList(item);
            /*  for (OrderinfoOperateLog log : itemList) {
            	try {
					
				
               if (log.getType() != 2) {
                    log.setOperateUserName(managerMapper.selectByPrimaryKey(log.getOperateUserId()).getNickname());
                } else{
                    log.setOperateUserName(userinfoMapper.selectByPrimaryKey(Long.valueOf(log.getOperateUserId())).getName());
                }
            	} catch (Exception e) {
					continue;
				}
            }
            */
            
            
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteOrderinfoOperateLog(OrderinfoOperateLog item) {
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


    public OrderinfoOperateLog selectOrderinfoOperateLogById(Long id) {
        OrderinfoOperateLog data = null;

        OrderinfoOperateLog query = new OrderinfoOperateLog();
        query.setId(id);

        List<OrderinfoOperateLog> datas = selectOrderinfoOperateLogList(query);
        if (datas != null && datas.size() > 0) {
            data = datas.get(0);

        }
        return data;

    }

    /**
     * 包装操作日志
     *
     * @param orderinfo
     * @param type
     * @param subType
     * @return
     */
    @Override
    public OrderinfoOperateLog packageOperateLog(Orderinfo orderinfo, int type, int subType) {
        //插入工单日志表
        OrderinfoOperateLog orderinfoOperateLog = new OrderinfoOperateLog();
        orderinfoOperateLog.setId(snowflakeIdWorker.nextId());
        orderinfoOperateLog.setOrderinfoId(orderinfo.getId());
        orderinfoOperateLog.setType(type);
        orderinfoOperateLog.setSubType(subType);
        orderinfoOperateLog.setOperateUserId(orderinfo.getCreateUserId());
        return orderinfoOperateLog;
    }

    @Override
    public int countNumOrder(Long companyId,String start,String end) {
		int result = 0;
		int result2 = 0;
		try {
			OrderinfoOperateLog orderinfoOperateLog=new OrderinfoOperateLog();
			orderinfoOperateLog.setType(0);
			orderinfoOperateLog.setSubType(1);
			orderinfoOperateLog.setStartTime(start);
			orderinfoOperateLog.setEndTime(end);
			if (companyId!=null){
				orderinfoOperateLog.setCompanyId(companyId);
			}
			result=itemMapper.selectCountNum(orderinfoOperateLog);
            orderinfoOperateLog.setType(1);
            orderinfoOperateLog.setSubType(0);
            orderinfoOperateLog.setStartTime(start);
            orderinfoOperateLog.setEndTime(end);
            if (companyId!=null){
                orderinfoOperateLog.setCompanyId(companyId);
            }
            result2=itemMapper.selectCountNum(orderinfoOperateLog);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询下发数量出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return result+result2;
    }

	@Override
	public int countPassOrder(Long companyId,String Id,String start,String end) {
		int result = 0;
		try {
			OrderinfoOperateLog orderinfoOperateLog=new OrderinfoOperateLog();
			orderinfoOperateLog.setType(1);
			orderinfoOperateLog.setSubType(5);
			orderinfoOperateLog.setCompanyId(companyId);
            orderinfoOperateLog.setOperateUserId(Id);
            orderinfoOperateLog.setStartTime(start);
            orderinfoOperateLog.setEndTime(end);
			result=itemMapper.selectCountNum(orderinfoOperateLog);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询下发通过数量出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return result;
	}

    @Override
    public List<Map<String, Object>> countIssueOrder(OrderinfoOperateLog item) {
		List<Map<String, Object>> map = new ArrayList<>();
		List<Map<String, Object>> map1 = new ArrayList<>();
		List<Map<String, Object>> map4 = new ArrayList<>();
		try {
			item.setType(0);
			item.setSubType(1);
			map = itemMapper.selectOrderCount(item);

            item.setType(1);
            item.setSubType(0);
			map1 = itemMapper.selectOrderCount(item);
			if (map.size()==0){
			    map4=map1;
            }else if (map1.size()==0){
                map4=map;
            }
            for (Map map2:map) {
                for (Map map3:map1) {
                    if (map3.get("month").equals(map2.get("month"))){
                        int t1 = Integer.parseInt(""+map2.get("orderNum")) ;
                        int t2 = Integer.parseInt(""+map3.get("orderNum")) ;
                        t1+=t2;
                        map2.put("orderNum",t1);
                        map4.add(map2);
                    }else {
                        map4.add(map3);
                    }
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询下单数出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map4;
    }

	@Override
	public List<Map<String, Object>> countIssueOrderPass(OrderinfoOperateLog item) {
		List<Map<String, Object>> map = new ArrayList<>();
		try {
			item.setType(1);
			item.setSubType(5);
			map = itemMapper.selectOrderCount(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询下单通过数出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> countEnterpriseOrder(OrderinfoOperateLog item) {
		List<Map<String, Object>> map = new ArrayList<>();
		List<Map<String, Object>> map1 = new ArrayList<>();
        List<Map<String, Object>> map4 = new ArrayList<>();
		try {
			item.setType(0);
			item.setSubType(1);
			map = itemMapper.selectEnterpriseCount(item);
			item.setType(1);
			item.setSubType(0);
			map1 = itemMapper.selectEnterpriseCount(item);

            for (Map map2:map) {
                for (Map map3:map1) {
                    if (map3.get("EnterpriseName").equals(map2.get("EnterpriseName"))){
                        int t1 = Integer.parseInt(""+map2.get("orderNum")) ;
                        int t2 = Integer.parseInt(""+map3.get("orderNum")) ;
                        t1+=t2;
                        map2.put("orderNum",t1);
                        map4.add(map2);

                    }
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询锁企下单通过数出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map4;
	}

	@Override
	public List<Map<String, Object>> countEnterpriseOrderPass(OrderinfoOperateLog item) {
		List<Map<String, Object>> map = new ArrayList<>();
		try {
			item.setType(1);
			item.setSubType(5);
			map = itemMapper.selectEnterpriseCount(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询锁企下单通过数出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return map;
	}


}
