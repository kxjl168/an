/**
 * @(#)OrderinfoMapper.java  2019-01-28 15:53:03
 *
 * Copyright (C),2017-2018, ZHONGTONGGUOMAI TECHNOLOGY NANJING
 * Co.,Ltd. All Rights Reserved.
 * GMWL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
//package com.ztgm.tasktransferplat.dao.plus;

import com.ztgm.tasktransferplat.pojo.Orderinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 单肙
 * @version 1.0.0 2019-01-28 15:53:03
 * @since 1.0.0
 */
public interface OrderinfoMapper  extends BaseMapper<Orderinfo> {

    /**
    * method with xml
    * @param
    * @return
    * @author KAutoGenerator
    * @date 2019-01-30 15:48:31
     */
    Orderinfo selectByPrimaryKey(Integer id);

    /**
    * method with xml
    * @param
    * @return
    * @author KAutoGenerator
    * @date 2019-01-30 15:48:31
     */
    int updateByPrimaryKeySelective(Orderinfo record);
    
    int insertSelective(Orderinfo record);

    /**
    * method with xml
    * @param
    * @return
    * @author KAutoGenerator
    * @date 2019-01-30 15:48:31
     */
    int updateByPrimaryKey(Orderinfo record);



/**
     * 列表查询
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019年1月7日
     */
    List<Orderinfo> selectList(Orderinfo item);

    /**
     * 删除
     * @param record
     * @return
     * @author KAutoGenerator
     * @date 2019-01-30 15:48:31
     */
    int delete(Orderinfo item);



    /**
     * 分页查询,
     * @param page
     * @param type 1：待接单，2：处理中，3：已完成
     * @return
     */
    IPage<Orderinfo> selectPage(@Param("page") Page page, @Param("type") Integer type);

    List<Orderinfo> selectListByComplete(Orderinfo item);

    int selectCountNum(Long id);

    List<Orderinfo> selectPointList(Long id);

    List<Orderinfo> selectMoneyList(Long id);

    
    /**
     * 工单状态统计
     * @return
     * @author zj
     * @date 2019年2月12日
     */
    List<Orderinfo> selectOrderStatusStasticList(Orderinfo query);
}
