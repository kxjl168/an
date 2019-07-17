package com.kxjl.base.service.impl;


import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kxjl.base.base.PageManager;
import com.kxjl.base.dao.ManagerMapper;
import com.kxjl.base.dao.ManagerRoleMapper;
import com.kxjl.base.dao.RoleMapper;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.pojo.ManagerRole;
import com.kxjl.base.service.ManagerService;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.tasktransferplat.dao.plus.CompanyMapper;
import com.kxjl.tasktransferplat.dao.plus.LockCompanyMapper;
import com.kxjl.tasktransferplat.pojo.Company;
import com.kxjl.tasktransferplat.pojo.LockCompany;
import com.kxjl.tasktransferplat.service.LockCompanyService;

import java.math.BigInteger;
import java.util.*;

@Transactional
@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ManagerRoleMapper managerRoleMapper;
    @Autowired
    private LockCompanyMapper lockCompanyMapper;
    @Autowired
    private CompanyMapper companyMapper;

	

	/**
	 * 登陆
	 * @param user
	 * @return
	 * @author zj
	 * @date 2018年11月9日
	 */
	public Manager getLoginUser(Manager user) {
		return managerMapper.getLoginUser(user);
	}

    public Manager getLoginUserByToken(String token) {
        return managerMapper.getLoginUserByToken(token);
    }



    public int deleteByPrimaryKey(String id) {
        return managerMapper.deleteByPrimaryKey(id);
    }

    public int insert(Manager record) {
        return managerMapper.insert(record);
    }

    public int insertSelective(Manager record) {
        return managerMapper.insertSelective(record);
    }

    public Manager selectByPrimaryKey(String id) {
        return managerMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Manager record) {
        return managerMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(Manager record) {
        return managerMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(Manager record) {
        return managerMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<Manager> selectManagerByManager(PageManager p,Manager Manager) {
  
    	Page page = new Page();
		if (p != null)
			page = PageHelper.startPage(p.getPage(), p.getPageRows());
		List<Manager> list = managerMapper.selectManagerByManager(Manager);
		if(null != list && list.size()>0) {
            for (int i = 0;i<list.size();i++) {
                Long companyId = list.get(i).getCompanyId();
                switch (list.get(i).getType()){
                    case 0:

                        break;
                    case 1:
                        Company company = companyMapper.selectByPrimaryKey(companyId);
                        if(company!=null){
                            list.get(i).setCompanyName(company.getCompanyName());
                        }
                        break;
                    case 2:
                        LockCompany lockCompany = lockCompanyMapper.selectByPrimaryKey(companyId);
                        if(lockCompany!=null){
                            list.get(i).setCompanyName(lockCompany.getEnterpriseName());
                        }
                        break;
                }
            }
        }
		PageInfo<Manager> rst=new PageInfo<>(list);
		rst.setTotal(page.getTotal());
		rst.setPageNum(page.getPageNum());
		rst.setPageSize(page.getPageSize());
		return rst;
		
    }

    @Override
    public PageInfo<Manager> selectLockCompanyByManager(PageManager p,Manager Manager) {

        Page page = new Page();
        if (p != null)
            page = PageHelper.startPage(p.getPage(), p.getPageRows());
        List<Manager> list = managerMapper.selectLockCompanyByManager(Manager);
        if(null != list && list.size()>0) {
            for (int i = 0;i<list.size();i++) {
                Long companyId = list.get(i).getCompanyId();
                switch (list.get(i).getType()){
                    case 0:

                        break;
                    case 1:
                        Company company = companyMapper.selectByPrimaryKey(companyId);
                        if(company!=null){
                            list.get(i).setCompanyName(company.getCompanyName());
                        }
                        break;
                    case 2:
                        LockCompany lockCompany = lockCompanyMapper.selectByPrimaryKey(companyId);
                        if(lockCompany!=null){
                            list.get(i).setCompanyName(lockCompany.getEnterpriseName());
                        }
                        break;
                }
            }
        }
        PageInfo<Manager> rst=new PageInfo<>(list);
        rst.setTotal(page.getTotal());
        rst.setPageNum(page.getPageNum());
        rst.setPageSize(page.getPageSize());
        return rst;

    }

    @Override
    @Transactional
    public Map initSuperAdmin(Manager Manager) {
        Map<String, java.io.Serializable> rtn = new HashMap<String, java.io.Serializable>();
        rtn.put("result", false);

        try {
            // 插入用户信息
            Manager.setId(UUIDUtil.getUUID());

            Manager.setCreateDate(new Date());
            Manager.setCreater("0");
            managerMapper.insert(Manager);

            // 查询管理员角色信息
            Map<String, String> conditions = new HashMap<>();
            conditions.put("name", "系统管理员");
            List<Map> roles = roleMapper.selectRoleByConditions(conditions);
            if (null == roles) {
                rtn.put("msg", "管理员角色未找到");
                return rtn;
            }

            Map role = roles.get(0);

            // 插入用户角色表
            ManagerRole ManagerRole = new ManagerRole();
            ManagerRole.setId(UUIDUtil.getUUID());
            ManagerRole.setSysManagerId(Manager.getId());
            ManagerRole.setSysRoleId(String.valueOf(role.get("id")));
            managerRoleMapper.insert(ManagerRole);

            rtn.put("result", true);
        } catch (Exception e) {
            rtn.put("msg", "未知异常");
            // todo
            // Log
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return rtn;
    }

    @Override
    public List<Manager> getAdminManagers() {
        return managerMapper.getAdminManagers();
    }

    public Manager login(String Managername, String password) {
        List<Manager> ulist = managerMapper.selectManagerByNamePwd(Managername, password);
        if (ulist == null || ulist.size() == 0) {
            return null;
        }
        return ulist.get(0);
    }

    public Manager getManagerByToken(String token) {
        List<Manager> ulist = managerMapper.selectManagerByToken(token);
        if (ulist == null || ulist.size() == 0) {
            return null;
        }
        return ulist.get(0);
    }

    /**
     * 更新token
     *
     * @param token
     */
    public void updateManagerToke(String id, String token) {
        Manager u = new Manager();
        u.setId(id);
        u.setToken(token);
        managerMapper.updateByPrimaryKeySelective(u);
    }

    public Manager getManagerByPhone(String phone) {
        List<Manager> ulist = managerMapper.selectManagerByPhone(phone);
        if (ulist == null || ulist.size() == 0) {
            return null;
        }
        return ulist.get(0);
    }


    /**
     * @param Manager
     * @return
     */
    @Override
    @Transactional
    public String saveManager(Manager Manager) {
        return saveManager(Manager, "");
    }

    /**
     * @param Manager
     * @return
     */
    @Override
    @Transactional
    public String saveManager(Manager Manager, String roleids) {
        JSONObject rtn = new JSONObject();

        if (null == Manager || null == Manager.getPassword() || null == Manager.getTelephone()) {
            rtn.put("result", false);
            rtn.put("message", "手机号码或者密码为空");
            return rtn.toString();
        }

        try {
            Map<String, String> conditions = new HashMap<>();
            conditions.put("telephone", Manager.getTelephone());
            List<Map> Managers = managerMapper.selectManagerByMap(conditions);
            if (null != Managers && 0 < Managers.size()) {
                logger.info("用户输入手机号码重复");
                rtn.put("result", false);
                rtn.put("message", "用户输入手机号码重复");
                return rtn.toString();
            }
            
            int count=managerMapper. hasNickName(Manager);

            if (count>0) {
                rtn.put("result", false);
                rtn.put("message", "用户称呼已存在！");
                return rtn.toString();
            }
            
            
            

            Manager.setId(UUIDUtil.getUUID());
            Manager.stPasswordAndEncrype(Manager.getPassword());
            Manager.setCreateDate(new Date());
            Manager.setCreater("0");
            managerMapper.insertSelective(Manager);

            // 添加
            String[] menus = roleids.split(",");
            for (int i = 0; i < menus.length; i++) {
                if (menus[i].trim().equals(""))
                    continue;

                ManagerRole item = new ManagerRole();
                item.setSysManagerId(Manager.getId());
                item.setSysRoleId(menus[i]);
                item.setId(UUIDUtil.getUUID());

                managerRoleMapper.insertSelective(item);
            }

            rtn.put("result", true);

            return rtn.toString();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("新增用户失败", e);
            rtn.put("result", false);
            rtn.put("message", "新增用户失败");
            return rtn.toString();
        }
    }

    @Override
    @Transactional
    public void updateManagerRole(Manager Manager, String roleids) throws Exception {
    	 //更新用户信息
        managerMapper.updateByPrimaryKeySelective(Manager);
        // 清空
        managerRoleMapper.deleteManagerRole(Manager);

        // 添加
        String[] menus = roleids.split(",");
        for (int i = 0; i < menus.length; i++) {
            if (menus[i].trim().equals(""))
                continue;

            ManagerRole item = new ManagerRole();
            item.setSysManagerId(Manager.getId());
            item.setSysRoleId(menus[i]);
            item.setId(UUIDUtil.getUUID());

            managerRoleMapper.insertSelective(item);
        }
    }
    
    @Override
    @Transactional
    public String updateManager(Manager Manager, String roleids) {
        JSONObject rtn = new JSONObject();

        if (null == Manager || null == Manager.getId()) {
            rtn.put("result", false);
            rtn.put("message", "用户id为空");
            return rtn.toString();
        }
        
        
        int count=managerMapper. hasNickName(Manager);

        if (count>0) {
            rtn.put("result", false);
            rtn.put("message", "用户称呼已存在！");
            return rtn.toString();
        }
        

        try {
            if (null != Manager.getPassword() && !"".equals(Manager.getPassword())) {
                Manager.stPasswordAndEncrype(Manager.getPassword());
            }
           
            updateManagerRole(Manager,roleids);
            // Map<String, String> ManagerRole = new HashMap<>();
            // ManagerRole.put("id", Manager.getManagerRoleId());
            // ManagerRole.put("sysRoleId", Manager.getRole());
            // ManagerRoleMapper.update(ManagerRole);

            rtn.put("result", true);

            return rtn.toString();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("更新用户出错", e);
            rtn.put("result", false);
            rtn.put("message", "更新用户出错");
            return rtn.toString();
        }
    }

    @Override
    public List<Map> selectManagerByManager(Map conditions) {
    	
        List<Map> mapList = managerMapper.selectManagerByMap(conditions);
        if(null != mapList && mapList.size()>0){
            BigInteger companyId = (BigInteger) mapList.get(0).get("companyId");
            int type = (Integer)mapList.get(0).get("type");
            if(type == 1&&companyId!=null){
                Company company = companyMapper.selectByPrimaryKey(companyId.longValue());
                mapList.get(0).put("companyName",company.getCompanyName());
            }else if(type == 2&&companyId!=null){
                LockCompany lockCompany = lockCompanyMapper.selectByPrimaryKey(companyId.longValue());
                mapList.get(0).put("companyName",lockCompany.getEnterpriseName());
            }
        }
        return mapList;
    }

    public void directUpdateManager(Manager u) {
        managerMapper.updateByPrimaryKey(u);
    }

    @Override
    public List<Map> selectManagerList(Page p) {
        List<Map> ManagerList = new ArrayList<>();
        try {
        	if(p!=null)
        	PageHelper.startPage(p);
            ManagerList = managerMapper.selectManagerList();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询用户列表出错");
        }
        return ManagerList;
    }

    @Override
    @Transactional
    public int deleteManager(String uid) {
        int result = 0;
        try {

            Manager q = new Manager();
            q.setId(uid);
            managerRoleMapper.deleteManagerRole(q);

            result = managerMapper.deleteByPrimaryKey(uid);


        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.error("删除用户出错");
        }
        return result;
    }

    @Override
    @Transactional
    public int delete(String uid) {
        int result = 0;
        try {

            result = managerMapper.deleteLockCompanyByPrimaryKey(uid);


        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.error("删除用户出错");
        }
        return result;
    }

    @Override
    @Transactional
    public int drop(String uid) {
        int result = 0;
        try {

            result = managerMapper.dropLockCompanyByPrimaryKey(uid);


        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.error("废弃用户出错");
        }
        return result;
    }

    @Override
    @Transactional
    public int reset(String uid) {
        int result = 0;
        try {

            result = managerMapper.resetLockCompanyByPrimaryKey(uid);


        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.error("恢复用户出错");
        }
        return result;
    }
}
