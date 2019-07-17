package com.kxjl.tasktransferplat.service.impl;


import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.tasktransferplat.dao.plus.UserBackQuestionMapper;
import com.kxjl.tasktransferplat.dao.plus.UserMessageMapper;
import com.kxjl.tasktransferplat.pojo.UserBackQuestion;
import com.kxjl.tasktransferplat.pojo.UserMessage;
import com.kxjl.tasktransferplat.service.UserBackQuestionService;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBackQuestionServiceImpl implements UserBackQuestionService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserBackQuestionMapper itemMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * @param item
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject saveUserBackQuestion(UserBackQuestion item) {
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
    public JSONObject updateUserBackQuestion(UserBackQuestion item) {
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
    public List<UserBackQuestion> selectUserBackQuestionList(UserBackQuestion item) {
        List<UserBackQuestion> itemList = new ArrayList<>();
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
    public int deleteUserBackQuestion(UserBackQuestion item) {
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


    public UserBackQuestion selectUserBackQuestionById(Long id) {
        UserBackQuestion data = null;

        UserBackQuestion query = new UserBackQuestion();
        query.setId(id);

        List<UserBackQuestion> datas = selectUserBackQuestionList(query);
        if (datas != null && datas.size() > 0) {
            data = datas.get(0);

        }
        return data;

    }

}
