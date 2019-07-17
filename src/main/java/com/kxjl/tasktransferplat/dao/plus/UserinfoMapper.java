package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kxjl.tasktransferplat.pojo.Orderinfo;
import com.kxjl.tasktransferplat.pojo.Userinfo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserinfoMapper extends BaseMapper<Userinfo> {
    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-01-29 10:29:15
     */
    int deleteByPrimaryKey(Long id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-01-29 10:29:15
     */
    @Override
    int insert(Userinfo record);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-01-29 10:29:15
     */
    int insertSelective(Userinfo record);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-01-29 10:29:15
     */
    Userinfo selectByPrimaryKey(Long id);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-01-29 10:29:15
     */
    int updateByPrimaryKeySelective(Userinfo record);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author KAutoGenerator
     * @date 2019-01-29 10:29:15
     */
    int updateByPrimaryKey(Userinfo record);


    /**
     * 列表查询
     *
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019年1月7日
     */
    List<Userinfo> selectList(Userinfo item);

    /**
     * 删除
     *
     * @param item
     * @return
     * @author KAutoGenerator
     * @date 2019-01-29 10:29:14
     */
    int delete(Userinfo item);


    /**
     * 分页查询,
     *
     * @param page
     * @param item
     * @return
     */
    IPage<Userinfo> selectPage(Page page, Userinfo item);

    /**
     * 接口 token
     *
     * @param token
     * @return
     * @author zj
     * @date 2018年6月20日
     */
    Userinfo getUserByToken(@Param(value = "token") String token);

    /**
     * 待审核锁匠
     *
     * @param item
     * @return
     */
    List<Userinfo> selectUnAuditUserinfoList(Userinfo item);

    /**
     * 评分管理查询
     *
     * @param item
     * @return
     */
    List<Userinfo> selectListByPoint(Userinfo item);

   /**
    * 查询锁匠的费用信息，实时查询，根据工单及审核记录
    * @param item
    * @return
    * @author zj
    * @date 2019年6月11日
    */
    List<Userinfo> selectListByMoney(Userinfo item);

    /**
     * 手机号确认
     *
     * @param userinfo
     * @return
     */
    List<Userinfo> selectListByPhone(Userinfo userinfo);

    /**
     * 解绑
     *
     * @param id
     * @return
     */
    int untying(Long id);

    /**
     * 根据公司id查询锁匠
     * <p>
     * 只根据地市查询，不管合伙人id zj 190507
     * </p>
     *
     * @param companyId
     * @param areaCode
     * @return
     */
    List<Userinfo> selectLockerByCompanyId(Orderinfo orderinfo);

    /**
     * 确认身份证
     *
     * @param userinfo
     * @return
     */
    List<Userinfo> selectUserinfoByIdCard(Userinfo userinfo);

    List<Userinfo> selectListByPhoneAll(Userinfo tuserinfo);

   /**
    * 合伙人删除时，清空合伙人下的全部锁匠，变为自由锁匠
    * @param companyId
    * @return
    * @author zj
    * @date 2019年5月16日
    */
    int updateCompanyNull(Long companyId);
    
    
    /**

     * 跟新锁匠所属合伙人为空，变为自由锁匠

     * @return
     * @author zj
     * @date 2019年5月16日
     */
    int updateLockerParterNull(Long lockId);

    /**
     * 身份证
     *
     * @param tuserinfo
     * @return
     */
    List<Userinfo> selectListByIdCardAll(Userinfo tuserinfo);


    /**
     * 查询指定行政区域下是否已经存在非当前编辑合伙人的其他合伙人。
     * 一个行政区县 district只能有一个合伙人
     *
     * @param tuserinfo
     * @return
     * @author zj
     * @date 2019年5月14日
     */
    List<Userinfo> selectJobAreaByUserAndDistrictIds(Userinfo tuserinfo);


    /**
     * 根据手机号和姓名模糊搜索合伙人代理人
     *
     * @param searchValue
     * @return
     */
    List<Userinfo> selectPartnerLockerLikeNameAndPhone(String searchValue);

}
