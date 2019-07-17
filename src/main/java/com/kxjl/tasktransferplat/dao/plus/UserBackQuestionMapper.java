package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.UserBackQuestion;

import java.util.List;

public interface UserBackQuestionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserBackQuestion record);

    int insertSelective(UserBackQuestion record);

    UserBackQuestion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserBackQuestion record);

    int updateByPrimaryKey(UserBackQuestion record);
/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<UserBackQuestion> selectList(UserBackQuestion item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-06-04 11:36:22
     */
    int delete(UserBackQuestion item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<UserBackQuestion> selectPage(Page page, UserBackQuestion item);
}
