package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.dao.plus.PriceLockCompanyMapper;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.pojo.LockCompany;
import com.kxjl.tasktransferplat.pojo.PriceLockCompany;
import com.kxjl.tasktransferplat.service.PriceLockCompanyService;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PriceLockCompanyServiceImpl implements PriceLockCompanyService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PriceLockCompanyMapper priceLockCompanyMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Long id) {
        int result = 0;
        try {
            result = priceLockCompanyMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByLockCompanyId(Long lockCompanyId) {
        int result = 0;
        try {
            result = priceLockCompanyMapper.deleteByLockCompanyId(lockCompanyId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message insert(PriceLockCompany priceLockCompany) {
        Message message = new Message();
        try {
            priceLockCompany.setId(snowflakeIdWorker.nextId());
            priceLockCompanyMapper.insert(priceLockCompany);
            message.setBol(true);
            return message;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("新增失败", e);
            message.setBol(false);
            message.setMessage("新增失败");
            log.error(ExceptionUntil.getMessage(e));
            return message;
        }
    }

    @Override
    public PriceLockCompany selectByPrimaryKey(Long id) {
        PriceLockCompany priceLockCompany = null;
        try {
            priceLockCompany = priceLockCompanyMapper.selectByPrimaryKey(id);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询失败", e);
        }
        return priceLockCompany;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message updateByPrimaryKey(PriceLockCompany priceLockCompany) {
        Message message = new Message();
        if (priceLockCompany.getId() == null || priceLockCompany.getId() == 0) {
            message.setBol(false);
            message.setMessage("id为空");
            return message;
        }
        try {
            priceLockCompanyMapper.updateByPrimaryKey(priceLockCompany);
            message.setBol(true);
            return message;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新出错", e);
            message.setBol(false);
            message.setMessage("更新出错");
            return message;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message updateByLockCompanyIdAndType(PriceLockCompany priceLockCompany) {
        Message message = new Message();
        if (priceLockCompany.getLockCompanyId() == null || priceLockCompany.getLockCompanyId() == 0) {
            message.setBol(false);
            message.setMessage("锁企id为空");
            return message;
        }
        try {
            priceLockCompanyMapper.updateByLockCompanyIdAndType(priceLockCompany);
            message.setBol(true);
            return message;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("更新出错", e);
            message.setBol(false);
            message.setMessage("更新出错");
            return message;
        }
    }

    @Override
    public List<PriceLockCompany> selectByLockCompanyId(Long lockCompanyId) {
        List<PriceLockCompany> lockCompanyList = new ArrayList<>();
        try {
            lockCompanyList = priceLockCompanyMapper.selectByLockCompanyId(lockCompanyId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return lockCompanyList;
    }

    @Override
    public LockCompany selectByLockCompanyIdAndType(Long lockCompanyId,Integer serverType,Integer parentType) {
        LockCompany lockCompany = null;
        try {
            lockCompany = priceLockCompanyMapper.selectByLockCompanyIdAndType(lockCompanyId,serverType,parentType);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return lockCompany;
    }

    @Override
    public List<LockCompany> selectList(LockCompany lockCompany) {
        List<LockCompany> lockCompanyList = new ArrayList<>();
        try {
            lockCompanyList = priceLockCompanyMapper.selectList(lockCompany);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return lockCompanyList;
    }

    /**
     * 根据id和服务类型查询价格信息
     * @param lockCompanyId
     * @param serverType
     * @return
     */
    @Override
    public PriceLockCompany selectByTypeAndId(Long lockCompanyId, String serverType, int parentType) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("serverType", serverType);
        queryWrapper.eq("lockCompanyId", lockCompanyId);
        queryWrapper.eq("parentType", parentType);
        return priceLockCompanyMapper.selectOne(queryWrapper);
    }

}
