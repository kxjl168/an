/**
 * @(#)AssessQServiceImpl.java 2019/5/17
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.plus.AssessQuestionDetailMapper;
import com.kxjl.tasktransferplat.dao.plus.AssessQuestionMapper;
import com.kxjl.tasktransferplat.pojo.AssessQuestion;
import com.kxjl.tasktransferplat.pojo.AssessQuestionDQ;
import com.kxjl.tasktransferplat.pojo.AssessQuestionDetail;
import com.kxjl.tasktransferplat.service.AssessQService;
import com.sun.org.apache.bcel.internal.generic.NEW;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 *@author shurui
 *@date 2019/5/17
 */
@Service
@Slf4j
public class AssessQServiceImpl implements AssessQService {


    @Autowired
    AssessQuestionMapper assessQuestionMapper;

    @Autowired
    AssessQuestionDetailMapper assessQuestionDetailMapper;


    @Override
    public List<AssessQuestion> selectAssessQuestion(AssessQuestion assessQuestion) {
        List<AssessQuestion> itemList = new ArrayList<>();
        List<AssessQuestion> itemList2 = new ArrayList<>();
        try {
            itemList = assessQuestionMapper.selectList(assessQuestion);
            for(AssessQuestion a:itemList){
                AssessQuestion assessQuestion1 = selectDetailWithAllByAssessId(a.getId());
                itemList2.add(assessQuestion1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询评价问卷列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList2;
    }

    @Override
    @Transactional
    public Map addAssessQuestion(AssessQuestion assessQuestion) {
        assessQuestion.setId(UUIDUtil.getUUID());//添加id
        assessQuestion.setCurUse("0");//默认当前不使用状态
        Map result = new HashMap();
        try {
            assessQuestionMapper.insert(assessQuestion);
            result.put("result",true);
        }catch (Exception e){
            result.put("result",false);
            log.error("新增评价问卷出错");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional
    public Map openOrClose(AssessQuestion assessQuestion) {//curUse  0：未启用，1：启用
        Map result = new HashMap();
        try {
            if("0".equals(assessQuestion.getCurUse())){//开启操作
                //先判断是否存在开启的问卷
                QueryWrapper<AssessQuestion> wrapper = new QueryWrapper<AssessQuestion>();
                wrapper.eq("curUse", "1");
                AssessQuestion assessQuestion1 = assessQuestionMapper.selectOne(wrapper);
                if(assessQuestion1 != null){//存在开启的问卷
                    result.put("result",false);
                    result.put("msg","当前存在开启问卷，只能同时开启一份问卷，请先关闭所有开启问卷！");
                    return result;
                }else {
                    assessQuestion.setCurUse("1");
                }
            }else {
                assessQuestion.setCurUse("0");
            }
            assessQuestionMapper.updateById(assessQuestion);
            result.put("result",true);
        }catch (Exception e){
            result.put("result",false);
            log.error("开启/关闭评价问卷出错");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional
    public Map delete(AssessQuestion assessQuestion) {
        Map result = new HashMap();
        //先判断删除问卷是否使用
        if("1".equals(assessQuestion.getCurUse())){
            result.put("result",false);
            result.put("msg","此问卷正在使用，请先禁用再删除！");
            return result;
        }
        //正常删除
        assessQuestion.setDataState(3);
        try {
            assessQuestionMapper.updateById(assessQuestion);
            result.put("result",true);
        }catch (Exception e){
            result.put("result",false);
            log.error("删除评价问卷出错");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional
    public Map addQuestionDetails(String questionDetails,String assessTitle,String assessId) {
        Map result = new HashMap();
        List<AssessQuestionDetail> assessQuestionDetails = JSON.parseArray(questionDetails, AssessQuestionDetail.class);
        AssessQuestion assessQuestion = new AssessQuestion();
        assessQuestion.setId(assessId);
        assessQuestion.setTitle(assessTitle);
        try {
            QueryWrapper<AssessQuestionDetail> wrapper = new QueryWrapper<AssessQuestionDetail>();
            wrapper.eq("assessId", assessId);
            List<AssessQuestionDetail> assessQuestionDetailsOld = assessQuestionDetailMapper.selectList(wrapper);//原题目和选项
            assessQuestionMapper.updateById(assessQuestion);//修改问卷名
            if(assessQuestionDetailsOld.size() != 0){
                //先删除原来所有选项
                assessQuestionMapper.batchDeletetAQDetails(assessQuestionDetailsOld);
            }
            if(assessQuestionDetails.size() != 0){
                //再添加或修改
                assessQuestionMapper.batchInsertAQDetails(assessQuestionDetails);
            }
            result.put("result",true);
        }catch (Exception e){
            result.put("result",false);
            log.error("编辑评价问卷表出错");
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public Map selectAssessWithDetails(String id){
        Map result = new HashMap();
        try {
            AssessQuestion assessQuestion = selectDetailWithAllByAssessId(id);
            result.put("assessQuestion",assessQuestion);
        }catch (Exception e){
            log.error("查询评价问卷表出错");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map selectOption(String id){
        Map result = new HashMap();
        try {
            AssessQuestionDetail assessQuestionDetail = assessQuestionDetailMapper.selectById(id);
            result.put("assessQuestionDetail",assessQuestionDetail);
        }catch (Exception e){
            log.error("查询选项出错");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional
    public Map deleteDetail(String id,String assessId){
        Map result = new HashMap();
        try {
            //先判断改问卷是否在使用
            AssessQuestion assessQuestion = assessQuestionMapper.selectById(assessId);
            if("0".equals(assessQuestion.getCurUse())){
                //不使用，直接删
                assessQuestionDetailMapper.deleteById(id);
                result.put("result",true);
            }else if("1".equals(assessQuestion.getCurUse())){
                //使用，不给删
                result.put("result",false);
                result.put("msg","此问卷正在使用，请先禁用！");
                return result;
            }
        }catch (Exception e){
            log.error("删除选项出错");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional
    public Map updateOption(AssessQuestionDetail assessQuestionDetail){
        Map result = new HashMap();
        try {
            //先判断改问卷是否在使用
            AssessQuestion assessQuestion = assessQuestionMapper.selectById(assessQuestionDetail.getAssessId());
            if("0".equals(assessQuestion.getCurUse())){
                //不使用，直接改
                assessQuestionDetailMapper.updateById(assessQuestionDetail);
                result.put("result",true);
            }else if("1".equals(assessQuestion.getCurUse())){
                //使用，不给改
                result.put("result",false);
                result.put("msg","此问卷正在使用，请先禁用再修改！");
                return result;
            }
        }catch (Exception e){
            log.error("修改选项/题目出错");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @Transactional
    public Map saveOption(AssessQuestionDetail assessQuestionDetail){
        Map result = new HashMap();
        try {
            String id = UUIDUtil.getUUID();
            assessQuestionDetail.setId(id);
            if(null != assessQuestionDetail.getParentid()){
                //获取试卷id
                AssessQuestionDetail assessQuestionDetail1 = assessQuestionDetailMapper.selectById(assessQuestionDetail.getParentid());
                AssessQuestion assessQuestion = assessQuestionMapper.selectById(assessQuestionDetail1.getAssessId());
                assessQuestionDetail.setAssessId(assessQuestion.getId());
            }
            assessQuestionDetailMapper.insert(assessQuestionDetail);
            result.put("result",true);
        }catch (Exception e){
            result.put("result",false);
            log.error("新增选项/题目出错");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<AssessQuestionDetail> selectQusOrOpt(String assessId,String type,String parentid){
        List<AssessQuestionDetail> result = null;
        try {
            QueryWrapper<AssessQuestionDetail> wrapper = new QueryWrapper<AssessQuestionDetail>();
            wrapper.eq("assessId", assessId);
            wrapper.eq("type", type);
            if(null != parentid){
                wrapper.eq("parentid", parentid);
            }
            result = assessQuestionDetailMapper.selectList(wrapper);
        }catch (Exception e){
            log.error("查询所有题目或选项出错");
            e.printStackTrace();
        }
        return result;
    }





    /**
     * 问卷细节转换为问题视图类
     */
    private AssessQuestionDQ detailchangeToQuestion(AssessQuestionDetail assessQuestionDetail){
        AssessQuestionDQ assessQuestionDQ = new AssessQuestionDQ();
        assessQuestionDQ.setId(assessQuestionDetail.getId());//id
        assessQuestionDQ.setTitle(assessQuestionDetail.getTitle());//名
        assessQuestionDQ.setSortstring(assessQuestionDetail.getSortstring());//排序
        assessQuestionDQ.setResultType(assessQuestionDetail.getResultType());//题目类别
        assessQuestionDQ.setCreateTime(assessQuestionDetail.getCreateTime());//创建时间
        assessQuestionDQ.setAssessId(assessQuestionDetail.getAssessId());//AssessId
        return assessQuestionDQ;
    }


    /**
     * 通过问卷id  查找并封装问卷全部信息
     * @param id
     * @return
     */
    public AssessQuestion selectDetailWithAllByAssessId(String id){
        QueryWrapper<AssessQuestionDetail> wrapper = new QueryWrapper<AssessQuestionDetail>();
        wrapper.eq("assessId", id);
        AssessQuestion assessQuestion = assessQuestionMapper.selectById(id);//问卷
        List<AssessQuestionDetail> assessQuestionDetails = assessQuestionDetailMapper.selectList(wrapper);//题目和选项
        //处理封装题目和选项
        ArrayList<AssessQuestionDQ> questions = new ArrayList<AssessQuestionDQ>();//题目
        for(AssessQuestionDetail q : assessQuestionDetails){ //题目
            AssessQuestionDQ assessQuestionDQ = new AssessQuestionDQ();
            if(null == q.getParentid()){
                assessQuestionDQ = detailchangeToQuestion(q);
                ArrayList<AssessQuestionDetail> options = new ArrayList<>();
                for(AssessQuestionDetail o : assessQuestionDetails){
                    if(q.getId().equals(o.getParentid())){
                        options.add(o);//匹配添加选项到对应问题
                    }
                }
                assessQuestionDQ.setOptions(options);
                questions.add(assessQuestionDQ);
            }
        }
        assessQuestion.setQuestions(questions);
        return assessQuestion;
    }

}
