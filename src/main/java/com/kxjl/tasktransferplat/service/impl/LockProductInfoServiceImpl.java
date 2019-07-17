package com.kxjl.tasktransferplat.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.OrderInfoMapper;
import com.kxjl.tasktransferplat.dao.plus.LockProductAttachMapper;
import com.kxjl.tasktransferplat.dao.plus.LockProductInfoMapper;
import com.kxjl.tasktransferplat.pojo.LockProductAttach;
import com.kxjl.tasktransferplat.pojo.LockProductInfo;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.service.LockProductInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class LockProductInfoServiceImpl implements LockProductInfoService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LockProductInfoMapper itemMapper;

    @Autowired
    private LockProductAttachMapper attachMapper;

    @Autowired
    private OrderInfoMapper orderMapper;


    /**
     * @param item
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject saveLockProductInfo(LockProductInfo item) {
        JSONObject rtn = new JSONObject();


        try {

            item.setId(UUIDUtil.getUUID());
            item.setDataState(1);
            itemMapper.insertSelective(item);


            if (!item.getAnnexs().equals("")) {

                String[] arr = item.getAnnexs().split(",");

                for (String annexId : arr) {

                    LockProductAttach attach = new LockProductAttach();
                    attach.setId(UUIDUtil.getUUID());
                    attach.setProduct_id(item.getId());
                    attach.setFile_id(annexId);
                    attachMapper.insertSelective(attach);
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
    public JSONObject updateLockProductInfo(LockProductInfo item) {
        JSONObject rtn = new JSONObject();

        if (null == item || null == item.getId()) {
            rtn.put("bol", false);
            rtn.put("message", "id为空");
            return rtn;
        }

        try {
            //通过id删除附件id
            LockProductAttach attachDel = new LockProductAttach();
            attachDel.setProduct_id(item.getId());
            attachMapper.deleteProId(attachDel);

            itemMapper.updateByPrimaryKeySelective(item);

            if (!item.getAnnexs().equals("")) {

                String[] arr = item.getAnnexs().split(",");

                for (String annexId : arr) {

                    LockProductAttach attach = new LockProductAttach();
                    attach.setId(UUIDUtil.getUUID());
                    attach.setProduct_id(item.getId());
                    attach.setFile_id(annexId);
                    attachMapper.insertSelective(attach);
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

    @Override
    public List<LockProductInfo> selectLockProductInfoList(LockProductInfo item) {
        List<LockProductInfo> itemList = new ArrayList<>();
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
    public int deleteLockProductInfo(LockProductInfo item) {
        int result = 0;
        try {
            // 查询产品未完成工单
          /*  QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("productId", item.getId());
            wrapper.le("orderState", 499);
            List<Orderinfo> orderinfoList = orderMapper.selectList(wrapper);
            if (!orderinfoList.isEmpty()) {
                // 存在未完成工单，不能删除
                return -1;
            }*/
            
            List<Orderinfo> orderinfoList = itemMapper.selectPorductUnFinishOrder(item.getId());
            if (!orderinfoList.isEmpty()) {
                // 存在未完成工单，不能删除
                return -1;
            }

            result = itemMapper.delete(item);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return result;
    }

    @Override

    public LockProductInfo selectLockProductInfoById(String id) {

        LockProductInfo data = null;

        LockProductInfo query = new LockProductInfo();
        query.setId(id);

        List<LockProductInfo> datas = selectLockProductInfoList(query);
        if (datas != null && datas.size() > 0) {
            data = datas.get(0);

        }
        return data;

    }

    @Override
    public LockProductInfo selectLockProductByprotype(Orderinfo orderinfo) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("protype",orderinfo.getProType());
        queryWrapper.eq("lockEnterpriseID",orderinfo.getSellerId());
        LockProductInfo lockProductInfo=itemMapper.selectOne(queryWrapper);
        if (lockProductInfo == null) {
           
        }
        return lockProductInfo;
    }

    @Override
    public List<LockProductInfo> selectLockProductByProType(LockProductInfo lockProductInfo) {
        return itemMapper.selectList1(lockProductInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int dropLockProductInfo(LockProductInfo item) {
        int result = 0;
        try {

        	  List<Orderinfo> orderinfoList = itemMapper.selectPorductUnFinishOrder(item.getId());
              if (!orderinfoList.isEmpty()) {
                  // 存在未完成工单，不能删除
                  return -1;
              }

        	
            result = itemMapper.drop(item);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("废弃出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int resetLockProductInfo(LockProductInfo item) {
        int result = 0;
        try {

            result = itemMapper.reset(item);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("恢复出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return result;
    }

}
