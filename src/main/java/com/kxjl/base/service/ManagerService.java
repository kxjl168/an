package com.kxjl.base.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.kxjl.base.base.PageManager;
import com.kxjl.base.pojo.Manager;

public interface ManagerService {

	/**
	 * 登陆
	 * 
	 * @param user
	 * @return
	 * @author zj
	 * @date 2018年11月9日
	 */
	Manager getLoginUser(Manager user);


	/**
	 * 用户token 登录
	 * @param token
	 * @return
	 */
	Manager getLoginUserByToken(String token);

	int deleteByPrimaryKey(String id);

	int insert(Manager record);

	int insertSelective(Manager record);

	Manager selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Manager record);

	int updateByPrimaryKeyWithBLOBs(Manager record);

	int updateByPrimaryKey(Manager record);

	/**
	 * 通过Manager bean对象查询Manager 列表
	 * 
	 * @param Manager
	 * @return
	 */
	PageInfo<Manager> selectManagerByManager(PageManager p, Manager Manager);

	/**
	 * 通过Manager bean对象查询Manager 列表
	 *
	 * @param Manager
	 * @return
	 */
	PageInfo<Manager> selectLockCompanyByManager(PageManager p, Manager Manager);

	/**
	 * 通过Manager bean对象查询Manager 列表
	 * 
	 * @param Manager
	 * @return
	 */
	Map initSuperAdmin(Manager Manager);

	/**
	 * 获取系统中现有的管理员用户列表
	 * 
	 * @return 现有管理员用户列表
	 */
	List<Manager> getAdminManagers();

	/**
	 * 新增用户
	 */
	String saveManager(Manager Manager);

	/**
	 * 新增用户
	 */
	String saveManager(Manager Manager, String roleids);

	/**
	 * 更新管理员权限信息
	 * 
	 * @param Manager
	 * @param roleids
	 * @throws Exception
	 * @author zj
	 * @date 2019年1月9日
	 */
	void updateManagerRole(Manager Manager, String roleids) throws Exception;

	/**
	 * 更新管理员基本信息及权限信息
	 * 
	 * @param Manager
	 * @param roleids
	 * @return
	 * @author zj
	 * @date 2019年1月9日
	 */
	String updateManager(Manager Manager, String roleids);

	/**
	 * 通过Manager bean对象查询Manager 列表
	 * 
	 * @param conditions
	 * @return
	 */
	List<Map> selectManagerByManager(Map conditions);

	List<Map> selectManagerList(Page p);

	int deleteManager(String uid);

	int delete(String uid);

	int drop(String uid);

	int reset(String uid);

}
