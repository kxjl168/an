package com.kxjl.tasktransferplat.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.dao.plus.LockCompanyMapper;
import com.kxjl.tasktransferplat.dao.plus.LockProductInfoMapper;
import com.kxjl.tasktransferplat.dao.plus.OrderinfoMapper;
import com.kxjl.tasktransferplat.dao.plus.PriceLockCompanyMapper;
import com.kxjl.tasktransferplat.pojo.LockCompany;
import com.kxjl.tasktransferplat.pojo.PriceLockCompany;
import com.kxjl.tasktransferplat.service.LockCompanyService;
import com.kxjl.tasktransferplat.util.DateUtil;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

@Service
public class LockCompanyServiceImpl implements LockCompanyService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LockCompanyMapper itemMapper;
    @Autowired
    private PriceLockCompanyMapper priceLockCompanyMapper;
    @Autowired
    private OrderinfoMapper orderinfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private LockProductInfoMapper lockProductInfo;
    /**
     * @param item
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject saveCompany(LockCompany item) {
        JSONObject rtn = new JSONObject();
        try {
            item.setId(snowflakeIdWorker.nextId());
            item.setDataState(1);
            itemMapper.insertSelective(item);

            if (null != item.getBuildPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(0);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getBuildPrice());
                priceLockCompany.setId(snowflakeIdWorker.nextId());
                priceLockCompanyMapper.insert(priceLockCompany);
            }
            if (null != item.getFixPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(1);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getFixPrice());
                priceLockCompany.setId(snowflakeIdWorker.nextId());
                priceLockCompanyMapper.insert(priceLockCompany);
            }

            

            if (null != item.getOpenLockPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(2);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getOpenLockPrice());
                priceLockCompany.setId(snowflakeIdWorker.nextId());
                priceLockCompanyMapper.insert(priceLockCompany);
            }



            if (null != item.getMeasutePrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(4);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getMeasutePrice());
                priceLockCompany.setId(snowflakeIdWorker.nextId());
                priceLockCompanyMapper.insert(priceLockCompany);
            }

            if (null != item.getCatBuildPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(5);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getCatBuildPrice());
                priceLockCompany.setId(snowflakeIdWorker.nextId());
                priceLockCompanyMapper.insert(priceLockCompany);
            }

            if (null != item.getChangeLockPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(6);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getChangeLockPrice());
                priceLockCompany.setId(snowflakeIdWorker.nextId());
                priceLockCompanyMapper.insert(priceLockCompany);
            }

            if (null != item.getEngineeringInstallationPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(7);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getEngineeringInstallationPrice());
                priceLockCompany.setId(snowflakeIdWorker.nextId());
                priceLockCompanyMapper.insert(priceLockCompany);
            }

            if (null != item.getEngineeringMaintenancePrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(8);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getEngineeringMaintenancePrice());
                priceLockCompany.setId(snowflakeIdWorker.nextId());
                priceLockCompanyMapper.insert(priceLockCompany);
            }

            if (null != item.getOtherFee()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(19);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getOtherFee());
                priceLockCompany.setId(snowflakeIdWorker.nextId());
                priceLockCompanyMapper.insert(priceLockCompany);
            }

            if (null != item.getHurryInVainPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(1);
                priceLockCompany.setParentType(2);
                priceLockCompany.setPrice(item.getHurryInVainPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }
            if (null != item.getLongDistancePrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(2);
                priceLockCompany.setParentType(2);
                priceLockCompany.setPrice(item.getLongDistancePrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }
            if (null != item.getRefitPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(3);
                priceLockCompany.setParentType(2);
                priceLockCompany.setPrice(item.getRefitPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }
            if (null != item.getSpecialDoorPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(4);
                priceLockCompany.setParentType(2);
                priceLockCompany.setPrice(item.getSpecialDoorPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }
            if (null != item.getUrgentPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(5);
                priceLockCompany.setParentType(2);
                priceLockCompany.setPrice(item.getUrgentPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }
            if (null != item.getFalseLockPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(6);
                priceLockCompany.setParentType(2);
                priceLockCompany.setPrice(item.getFalseLockPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }

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
    public JSONObject updateCompany(LockCompany item) {
        JSONObject rtn = new JSONObject();

        if (item.getId() == null) {
            rtn.put("bol", false);
            rtn.put("message", "id为空");
            return rtn;
        }

        try {
            if (item.getTaxPoint().equals("")){
                item.setTaxPoint("0");
            }
            itemMapper.updateByPrimaryKeySelective(item);

            if (null != item.getBuildPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(0);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getBuildPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }
            if (null != item.getFixPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(1);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getFixPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }

            if (null != item.getOpenLockPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(2);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getOpenLockPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }



            if (null != item.getMeasutePrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(4);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getMeasutePrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }

            if (null != item.getCatBuildPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(5);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getCatBuildPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }

            if (null != item.getChangeLockPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(6);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getChangeLockPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }

            if (null != item.getEngineeringInstallationPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(7);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getEngineeringInstallationPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }

            if (null != item.getEngineeringMaintenancePrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(8);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getEngineeringMaintenancePrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }

            if (null != item.getOtherFee()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(19);
                priceLockCompany.setParentType(1);
                priceLockCompany.setPrice(item.getOtherFee());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }

            if (null != item.getHurryInVainPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(1);
                priceLockCompany.setParentType(2);
                priceLockCompany.setPrice(item.getHurryInVainPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }
            if (null != item.getLongDistancePrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(2);
                priceLockCompany.setParentType(2);
                priceLockCompany.setPrice(item.getLongDistancePrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }
            if (null != item.getRefitPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(3);
                priceLockCompany.setParentType(2);
                priceLockCompany.setPrice(item.getRefitPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }
            if (null != item.getSpecialDoorPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(4);
                priceLockCompany.setParentType(2);
                priceLockCompany.setPrice(item.getSpecialDoorPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }
            if (null != item.getUrgentPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(5);
                priceLockCompany.setParentType(2);
                priceLockCompany.setPrice(item.getUrgentPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }
            if (null != item.getFalseLockPrice()) {
                PriceLockCompany priceLockCompany = new PriceLockCompany();
                priceLockCompany.setLockCompanyId(item.getId());
                priceLockCompany.setServerType(6);
                priceLockCompany.setParentType(2);
                priceLockCompany.setPrice(item.getFalseLockPrice());
                LockCompany lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(priceLockCompany.getLockCompanyId(), priceLockCompany.getServerType(), priceLockCompany.getParentType());
                if (null == lockCompany || lockCompany.getLockCompanyPriceId() == 0) {
                    priceLockCompany.setId(snowflakeIdWorker.nextId());
                    priceLockCompanyMapper.insert(priceLockCompany);
                } else {
                    priceLockCompany.setId(lockCompany.getLockCompanyPriceId());
                    priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
                }
            }

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
     * 待审核公司
     *
     * @param item
     * @return
     * @author zj
     * @date 2019年1月29日
     */
    @Override
    public List<LockCompany> selectUnAuditCompanyList(LockCompany item) {
        List<LockCompany> itemList = new ArrayList<>();
        try {
            itemList = itemMapper.selectUnAuditCompanyList(item);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList;
    }


    @Override
    public List<LockCompany> selectCompanyList(LockCompany item) {
        List<LockCompany> itemList = new ArrayList<>();
        try {

            itemList = itemMapper.selectList(item);
            for (LockCompany lockCompany:itemList) {
                if (lockCompany.getAgreenStartTime() != null) {
                    lockCompany.setAgreenStartTime(DateUtil.getDayString(lockCompany.getAgreenStartTime()));
                }
                if (lockCompany.getAgreenEndTime() != null) {
                    lockCompany.setAgreenEndTime(DateUtil.getDayString(lockCompany.getAgreenEndTime()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Message deleteCompany(LockCompany item) {
        Message message = new Message();
        try {
            int count = orderinfoMapper.selecLockCompanyUnFinishOrderCount(item.getId());
            if (count > 0) {
                message.setBol(false);
                message.setMessage("该锁企有未完成工单，请完成后再进行删除");
                return message;
            }
            int result = itemMapper.delete(item);

            message.setBol(result == 1 ? true : false);
            message.setMessage(result == 1 ? "删除成功" : "删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除出错");
            log.error(ExceptionUntil.getMessage(e));
            message.setBol(false);
            message.setMessage("删除失败");
        }
        return message;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Message dropCompany(LockCompany item) {
        Message message = new Message();
        try {
            int count = orderinfoMapper.selecLockCompanyUnFinishOrderCount(item.getId());
            if (count > 0) {
                message.setBol(false);
                message.setMessage("该锁企有未完成工单，请完成后再进行废弃");
                return message;
            }
            int result = itemMapper.drop(item);
            message.setBol(result == 1 ? true : false);
            message.setMessage(result == 1 ? "废弃成功" : "废弃失败");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("废弃出错");
            log.error(ExceptionUntil.getMessage(e));
            message.setBol(false);
            message.setMessage("废弃失败");
        }
        return message;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Message resetCompany(LockCompany item) {
        Message message = new Message();
        try {
            int result = itemMapper.reset(item);
            message.setBol(result == 1 ? true : false);
            message.setMessage(result == 1 ? "恢复成功" : "恢复失败");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("恢复出错");
            log.error(ExceptionUntil.getMessage(e));
            message.setBol(false);
            message.setMessage("恢复失败");
        }
        return message;
    }


    @Override
    public LockCompany selectCompanyById(Long id) {
        LockCompany data = null;

        LockCompany query = new LockCompany();
        query.setId(id);

        List<LockCompany> datas = selectCompanyList(query);
        if (datas != null && datas.size() > 0) {
            data = datas.get(0);

        }
        LockCompany build = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 0, 1);
        LockCompany fix = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 1, 1);
        LockCompany openLockPrice = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 2, 1);
        LockCompany measutePrice = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 4, 1);
        LockCompany catBuildPrice = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 5, 1);
        LockCompany changeLockPrice = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 6, 1);
        LockCompany engineeringInstallationPrice = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 7, 1);
        LockCompany engineeringMaintenancePrice = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 8, 1);
        LockCompany otherFee = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 19, 1);

        LockCompany specialDoorPrice = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 4, 2);
        LockCompany refitPrice = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 3, 2);
        LockCompany hurryInVainPrice = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 1, 2);
        LockCompany longDistancePrice = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 2, 2);
        LockCompany urgentPrice = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 5, 2);
        LockCompany falseLockPrice = priceLockCompanyMapper.selectByLockCompanyIdAndType(data.getId(), 6, 2);

        if (null != build) {
            data.setBuildPrice(build.getPrice());
        }
        if (null != fix) {
            data.setFixPrice(fix.getPrice());
        }
        if (null != openLockPrice) {
            data.setOpenLockPrice(openLockPrice.getPrice());
        }
        if (null != measutePrice) {
            data.setMeasutePrice(measutePrice.getPrice());
        }
        if (null != catBuildPrice) {
            data.setCatBuildPrice(catBuildPrice.getPrice());
        }
        if (null != changeLockPrice) {
            data.setChangeLockPrice(changeLockPrice.getPrice());
        }
        if (null != engineeringInstallationPrice) {
            data.setEngineeringInstallationPrice(engineeringInstallationPrice.getPrice());
        }

        if (null != engineeringMaintenancePrice) {
            data.setEngineeringMaintenancePrice(engineeringMaintenancePrice.getPrice());
        }
        if (null != otherFee) {
            data.setOtherFee(otherFee.getPrice());
        }
        if (null != specialDoorPrice) {
            data.setSpecialDoorPrice(specialDoorPrice.getPrice());
        }
        if (null != refitPrice) {
            data.setRefitPrice(refitPrice.getPrice());
        }
        if (null != hurryInVainPrice) {
            data.setHurryInVainPrice(hurryInVainPrice.getPrice());
        }
        if (null != longDistancePrice) {
            data.setLongDistancePrice(longDistancePrice.getPrice());
        }
        if (null != urgentPrice) {
            data.setUrgentPrice(urgentPrice.getPrice());
        }
        if (null != falseLockPrice) {
            data.setFalseLockPrice(falseLockPrice.getPrice());
        }

        return data;

    }

    @Override
    public LockCompany selectLockCompanyByName(String enterpriseName) {
        LockCompany lockCompany=itemMapper.selectLockCompanyByName(enterpriseName);
        if (lockCompany != null && lockCompany.getId() == null) {
            throw new RuntimeException("未查询到相关锁企，工单插入失败");
        }
        return lockCompany;
    }

    @Override
    public LockCompany selectLockCompanyByPhone(String enterprisePhone) {
        return itemMapper.selectLockCompanyByPhone(enterprisePhone);
    }

    /**
     * 查询所有审核通过的公司
     *
     * @param item
     * @return
     */
    @Override
    public List<LockCompany> allCompanyList(LockCompany item) {
        List<LockCompany> companyList = itemMapper.allCompanyList();
        return companyList;
    }

}
