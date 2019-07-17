package com.kxjl.tasktransferplat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.tasktransferplat.dao.plus.CompanyMapper;
import com.kxjl.tasktransferplat.dao.plus.UserinfoMapper;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.service.CompanyService;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompanyMapper itemMapper;
    @Autowired
    private UserinfoMapper userinfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;


    /**
     * @param item
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject saveCompany(Company item) {
        JSONObject rtn = new JSONObject();


        try {

            item.setId(snowflakeIdWorker.nextId());


            item.setDataState(1);
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
    public JSONObject updateCompany(Company item) {
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


    /**
     * 待审核公司
     *
     * @param item
     * @return
     * @author zj
     * @date 2019年1月29日
     */
    @Override
    public List<Company> selectUnAuditCompanyList(Company item) {
        List<Company> itemList = new ArrayList<>();
        try {
            itemList = itemMapper.selectUnAuditCompanyList(item);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询列表出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return itemList;
    }


    @Override
    public List<Company> selectCompanyList(Company item) {
        List<Company> itemList = new ArrayList<>();
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
    public int deleteCompany(Company item) {
        int result = 0;
        try {
            result = itemMapper.delete(item);
            userinfoMapper.updateCompanyNull(item.getId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除出错");
            log.error(ExceptionUntil.getMessage(e));
        }
        return result;
    }

    @Override


    public Company selectCompanyById(Long id) {
        Company data = null;

        Company query = new Company();
        query.setId(id);

        List<Company> datas = selectCompanyList(query);
        if (datas != null && datas.size() > 0) {
            data = datas.get(0);

        }
        return data;

    }

    /**
     * 查询所有审核通过的公司
     *
     * @param item
     * @return
     */
    @Override
    public List<Company> allCompanyList(Company item) {
        List<Company> companyList = itemMapper.allCompanyList();
        return companyList;
    }

    /**
     * 通过城市编码查询代理商
     * @param areaCode
     * @return
     */
    @Override
    public Company selectByAreaCode(String areaCode) {
        String provinceCityCode = areaCode.substring(0, 4);
        Company company = itemMapper.selectByAreaCode(provinceCityCode);
        return company;
    }

    /**
     * 通过省市编码查询代理商
     * @param areaCode
     * @return
     */
    @Override
    public Company selectByProvinceAndCityCode(String areaCode) {
        Company company = itemMapper.selectByProvinceAndCityCode(areaCode);
        return company;
    }

    /**
     * 通过合伙人代理人名查询
     * @param companyName
     * @return
     */
    @Override
    public Company selectByCompanyName(String companyName) {
        Company company = itemMapper.selectByCompanyName(companyName);
        return company;
    }

    /**
     * 通过合伙人代理人电话名查询
     * @param companyPhone
     * @return
     */
    @Override
    public Company selectByCompanyPhone(String companyPhone) {
        Company company = itemMapper.selectByCompanyPhone(companyPhone);
        return company;
    }

}
