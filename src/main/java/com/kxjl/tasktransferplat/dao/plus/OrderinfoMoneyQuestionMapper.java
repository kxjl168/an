package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kxjl.tasktransferplat.pojo.OrderinfoMoneyQuestion;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderinfoMoneyQuestionMapper extends BaseMapper<OrderinfoMoneyQuestion> {
    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-06-10 15:42:23
     */
    int deleteByPrimaryKey(String id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-06-10 15:42:23
     */
    @Override
    int insert(OrderinfoMoneyQuestion record);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-06-10 15:42:23
     */
    int insertSelective(OrderinfoMoneyQuestion record);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-06-10 15:42:23
     */
    OrderinfoMoneyQuestion selectByPrimaryKey(String id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-06-10 15:42:23
     */
    int updateByPrimaryKeySelective(OrderinfoMoneyQuestion record);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-06-10 15:42:23
     */
    int updateByPrimaryKey(OrderinfoMoneyQuestion record);


    /**
     * 列表查询
     *
     * @param orderNo
     * * @param phone
     * @return
     * @author zy
     * @date 2019年1月7日
     */
    List<Map<String,Object>> selectList(@Param("orderNo") String orderNo, @Param("phone") String phone, @Param("serviceState") String serviceState);

    /**
     * 删除
     *
     * @param item
     * @return
     * @author zy
     * @date 2019-06-10 15:42:22
     */
    int delete(OrderinfoMoneyQuestion item);


}
