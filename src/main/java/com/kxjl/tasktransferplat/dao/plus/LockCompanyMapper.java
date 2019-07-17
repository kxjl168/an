package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.LockCompany;

import java.util.List;

public interface LockCompanyMapper extends BaseMapper<LockCompany> {
    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-02-01 13:39:13
     */
    int deleteByPrimaryKey(Long id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-02-01 13:39:13
     */
    int insert(LockCompany record);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-02-01 13:39:13
     */
    int insertSelective(LockCompany record);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-02-01 13:39:13
     */
    LockCompany selectByPrimaryKey(Long id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-02-01 13:39:13
     */
    LockCompany selectLockCompanyByName(String enterpriseName);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-02-01 13:39:13
     */
    LockCompany selectLockCompanyByPhone(String enterprisePhone);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-02-01 13:39:13
     */
    int updateByPrimaryKeySelective(LockCompany record);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-02-01 13:39:13
     */
    int updateByPrimaryKey(LockCompany record);


    /**
     * 列表查询
     *
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019年1月7日
     */
    List<LockCompany> selectList(LockCompany item);

    /**
     * 删除
     *
     * @param record
     * @return
     * @author KAutoGenerator
     * @date 2019-02-01 13:39:13
     */
    int delete(LockCompany item);

    /**
     * 废弃
     *
     * @param record
     * @return
     * @author KAutoGenerator
     * @date 2019-02-01 13:39:13
     */
    int drop(LockCompany item);

    /**
     * 回复
     *
     * @param record
     * @return
     * @author KAutoGenerator
     * @date 2019-02-01 13:39:13
     */
    int reset(LockCompany item);


    /**
     * 分页查询,
     *
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<LockCompany> selectPage(Page page, LockCompany item);

    /**
     * 待审核公司
     *
     * @param query
     * @return
     * @author zj
     * @date 2019年1月29日
     */
    List<LockCompany> selectUnAuditCompanyList(LockCompany query);

    /**
     * 查询公司列表
     *
     * @return
     */
    List<LockCompany> allCompanyList();
}
