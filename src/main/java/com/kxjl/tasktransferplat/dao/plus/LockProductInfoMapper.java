package com.kxjl.tasktransferplat.dao.plus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.LockProductInfo;
import com.kxjl.tasktransferplat.pojo.Orderinfo;

import java.util.List;

public interface LockProductInfoMapper    extends BaseMapper<LockProductInfo>    {
    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-13 17:06:44
     */
    int deleteByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-13 17:06:44
     */
    int insert(LockProductInfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-13 17:06:44
     */
    int insertSelective(LockProductInfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-13 17:06:44
     */
    LockProductInfo selectByPrimaryKey(String id);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-13 17:06:44
     */
    int updateByPrimaryKeySelective(LockProductInfo record);

    /**
    * method with xml 
    * @param 
    * @return
    * @author KAutoGenerator
    * @date 2019-05-13 17:06:44
     */
    int updateByPrimaryKey(LockProductInfo record);



/**
     * 列表查询
     * @param item
     * @return
     * @author zj
     * @date 2019年1月7日
     */
    List<LockProductInfo> selectList(LockProductInfo item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author zj
     * @date 2019-05-13 17:06:44
     */
    int delete(LockProductInfo item);

    /**
     * 废弃
     * @param record
     * @return
     * @author zj
     * @date 2019-05-13 17:06:44
     */
    int drop(LockProductInfo item);    /**
     * 恢复
     * @param record
     * @return
     * @author zj
     * @date 2019-05-13 17:06:44
     */
    int reset(LockProductInfo item);
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<LockProductInfo> selectPage(Page page, LockProductInfo item);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author wxiang
     * @date 2019-04-15 13:39:13
     */
    int deleteByLockCompanyId(Long lockCompanyId);
    
    
    /**
     * 琐企产品关联的未完成的工单数-删除时判断使用
     * @param productId
     * @return
     * @author zj
     * @date 2019年5月23日
     */
    List<Orderinfo> selectPorductUnFinishOrder(String productId);

    /**
     * 标识验证
     * @param lockProductInfo
     * @return
     */
    List<LockProductInfo> selectList1(LockProductInfo lockProductInfo);
}
