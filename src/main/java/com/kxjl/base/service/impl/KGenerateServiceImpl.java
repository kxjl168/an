package com.kxjl.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kxjl.base.dao.SysOperLogMapper;
import com.kxjl.base.pojo.CtrollerMapperBean;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.pojo.MenuPermission;
import com.kxjl.base.pojo.Role;
import com.kxjl.base.pojo.SysOperLog;
import com.kxjl.base.service.KGenerateService;
import com.kxjl.base.service.ManagerService;
import com.kxjl.base.service.PermissionService;
import com.kxjl.base.service.RoleService;
import com.kxjl.base.service.SysLogService;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.FreeMarkUtil;
import com.kxjl.base.util.GeneratorUtils;
import com.kxjl.base.util.MGeneratorUtil;
import com.kxjl.base.util.Message;
import com.kxjl.base.util.TemplateFileUtil;
import com.kxjl.base.util.UUIDUtil;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

@Service
public class KGenerateServiceImpl implements KGenerateService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	// 模板文件目录
	@Value("${kauto.templatePath}")
	private String templatePath;

	@Autowired
	FreeMarkUtil freeMarkUtil;
	@Autowired
	MGeneratorUtil mGeneratorUtil;

	@Autowired
	PermissionService permissionService;

	@Autowired
	ManagerService managerService;

	@Autowired
	RoleService roleService;

	/**
	 * 生成默认菜单并分配给当前登陆用户
	 * 
	 * @param item
	 * @return
	 * @author zj
	 * @date 2019年1月9日
	 */
	@Transactional
	public Message generateMenuAndAsgin(HttpServletRequest request, CtrollerMapperBean data) {
		Message msg = new Message();
		msg.setBol(false);
		msg.setMessage("fail");
		try {
		
			// 创建菜单

			// 默认自动生成一级菜单菜单
			MenuPermission recordp = permissionService.selectByPrimaryKey("menu_auto");
			if (recordp == null) {
				recordp = new MenuPermission();
				recordp.setId("menu_auto");
				recordp.setName("自动生成菜单");
				recordp.setType("1");
				recordp.setUrl("/");
				recordp.setPercode("menu_auto:view");
				recordp.setParentid("0");
				recordp.setSortstring("100");
				recordp.setAvailable("1");
				recordp.setIcon("fa fa-cogs");
				permissionService.insertSelective(recordp);

			}
			// 当前菜单
			boolean isExist=false;
			String newMenuId = "menu_" + data.getCtrollerModelMapping();
			MenuPermission record = permissionService.selectByPrimaryKey(newMenuId);
			if(record==null)
				record=new MenuPermission();
			else
				isExist=true;
			
			record.setId(newMenuId);
			record.setName(data.getPagetitle() + "管理");
			record.setType("2");
			record.setUrl("/manager/" + data.getCtrollerModelMapping() + "/manager");
			record.setPercode(data.getCtrollerModelMapping() + ":view");
			record.setParentid("menu_auto");
			record.setSortstring("1");
			record.setAvailable("1");
			record.setIcon("fa fa-cog");
			
			if(isExist)
			permissionService.updateByPrimaryKeySelective(record);
			else
				permissionService.insertSelective(record);

			Map principal = (Map) request.getAttribute("principal");
			String loginUserId = String.valueOf(principal.get("userId"));
			Manager m = new Manager();
			m.setId(loginUserId);

			// permissionService.get
			List<Role> roles = roleService.selectRoleByManager(m);

			List<MenuPermission> permissions = permissionService.getRolePermissionList(roles.get(0));
			String permissionids = "";
			for (int i = 0; i < permissions.size(); i++) {
				permissionids += permissions.get(i).getId() + ",";
			}
			if(!permissionids.contains(newMenuId))
			permissionids += newMenuId+",";
			if(!permissionids.contains("menu_auto"))
				permissionids += "menu_auto,";
			
			// 默认给当前登陆账号的第一个角色增加此菜单
			roleService.updateRolePermissionList(roles.get(0).getId(), permissionids);

			msg.setBol(true);
			msg.setMessage("ok");
		} catch (Exception e) {
			e.printStackTrace();

			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("更新权限菜单出错", e);

			msg.setBol(false);
			msg.setMessage(e.getMessage());
		}
		return msg;
	}

	@Override
	public Message generateController(CtrollerMapperBean data) {

		Message msg = new Message();
		msg.setBol(false);
		msg.setMessage("fail");
		try {
			String text = freeMarkUtil.getControllerContent(data);

			TemplateFileUtil.SaveControllerFile(text, data);

			msg.setBol(true);
			msg.setMessage("ok");
		} catch (Exception e) {
			e.printStackTrace();
			msg.setBol(false);
			msg.setMessage(e.getMessage());
		}
		return msg;
	}

	@Override
	public Message generatePageAndJs(CtrollerMapperBean data) {

		Message msg = new Message();
		msg.setBol(false);
		msg.setMessage("fail");
		try {

			String textIndex = freeMarkUtil.getPageIndexContent(data);
			TemplateFileUtil.SaveIndexFile(textIndex, data, "ftl");

			String textForm = freeMarkUtil.getPageFormContent(data);
			TemplateFileUtil.SavePageFile(textForm, data, "ftl");

			String textJS = freeMarkUtil.getPageJSContent(data);
			TemplateFileUtil.SaveJSFile(textJS, data);

			msg.setBol(true);
			msg.setMessage("ok");
		} catch (Exception e) {
			e.printStackTrace();
			msg.setBol(false);
			msg.setMessage(e.getMessage());
		}
		return msg;
	}

	@Override
	public Message generateService(CtrollerMapperBean data) {

		Message msg = new Message();
		msg.setBol(false);
		msg.setMessage("fail");
		try {

			String text = freeMarkUtil.getServiceContent(data);
			TemplateFileUtil.SaveServiceFile(text, data);

			String textImpl = freeMarkUtil.getServiceImplContent(data);
			TemplateFileUtil.SaveServiceImplFile(textImpl, data);

			msg.setBol(true);
			msg.setMessage("ok");
		} catch (Exception e) {
			e.printStackTrace();
			msg.setBol(false);
			msg.setMessage(e.getMessage());
		}
		return msg;

	}

	@Override
	public Message UpdatePojoDao(CtrollerMapperBean data) {

		Message msg = new Message();
		msg.setBol(false);
		msg.setMessage("fail");
		try {

			String daoplus = freeMarkUtil.getDaoPlusContent(data);
			TemplateFileUtil.AddToDaoFile(daoplus, data);

			String mapperPlus = freeMarkUtil.getMapperPlusContent(data);
			TemplateFileUtil.AddToMapperFile(mapperPlus, data);
			
			
			TemplateFileUtil.ModifyPriKeyToLong(data);
			

			msg.setBol(true);
			msg.setMessage("ok");
		} catch (Exception e) {
			e.printStackTrace();
			msg.setBol(false);
			msg.setMessage(e.getMessage());
		}
		return msg;
	}

	@Override
	public Message generatePojoDao(CtrollerMapperBean data) {

		String cfgfileName = "mybatisgenerator-temp.xml";

		Message msg = new Message();
		msg.setBol(false);
		msg.setMessage("fail");
		try {
			mGeneratorUtil.generatoMybatisPojoMapper(cfgfileName, data);

			// 修改
			msg = UpdatePojoDao(data);

		} catch (Exception e) {
			e.printStackTrace();
			msg.setBol(false);
			msg.setMessage(e.getMessage());
		}

		return msg;
	}

}
