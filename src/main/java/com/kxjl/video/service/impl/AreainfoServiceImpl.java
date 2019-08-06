package com.kxjl.video.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import org.apache.poi.ss.formula.ptg.AreaI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kxjl.base.pojo.MenuPermission;
import com.kxjl.base.pojo.Role;
import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;

import com.kxjl.video.dao.AreainfoMapper;
import com.kxjl.video.dao.SeatinfoMapper;
import com.kxjl.video.dao.UnitinfoMapper;
import com.kxjl.video.pojo.Areainfo;
import com.kxjl.video.pojo.Seatinfo;
import com.kxjl.video.pojo.Unitinfo;
import com.kxjl.video.service.AreainfoService;
import com.kxjl.video.service.SeatinfoService;
import com.kxjl.video.util.TokenUtil;

import java.util.*;

@Service
public class AreainfoServiceImpl implements AreainfoService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AreainfoMapper itemMapper;

	@Autowired
	private UnitinfoMapper unitinfoMapper;

	@Autowired
	private SeatinfoMapper seatinfoMapper;

	/**
	 * 构造单位-片区树
	 * 
	 * @param level
	 * @param isopen
	 * @return
	 * @author zj
	 * @date 2019年7月21日
	 */
	public List<String> buildAreaTree(String level, boolean isopen) {
		List<String> lstTree = new ArrayList<String>();

		try {

			// 一级分类
			Unitinfo uquery = new Unitinfo();
			uquery.setDataState("1");
			uquery.setUnitType("1");//一级单位
			uquery.setCurUid(TokenUtil.getWebLoginUser().getId());
			// uquery.set
			List<Unitinfo> menus = unitinfoMapper.selectLeftTreeList(uquery);

			String rootid = "0";
			for (Unitinfo menu : menus) {
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append("{");
				sBuffer.append("id:\"" + menu.getId() + "\",");
				sBuffer.append("pId:\"" + rootid + "\",");
				sBuffer.append("ttype:\"" + "unit" + "\",");
				sBuffer.append("open:" + (isopen ? "true" : "false") + ",");// 根节点打开

				sBuffer.append("name:\"" + menu.getName() + "\",");

				sBuffer.append("remark:\"" + "" + "\"");

				sBuffer.append("}");
				lstTree.add(sBuffer.toString());

			}
			
			List<Unitinfo> allLevel2Unit=new ArrayList<Unitinfo>();
			
			//二级单位
			for (int i = 0; i < menus.size(); i++) {
				// 二级分类
				Unitinfo uquery2 = new Unitinfo();
				uquery2.setDataState("1");
				uquery2.setParentUnit(menus.get(i).getId());
				uquery2.setUnitType("2");//一级单位
				uquery2.setCurUid(TokenUtil.getWebLoginUser().getId());
				// uquery.set
				List<Unitinfo> menus2 = unitinfoMapper.selectList(uquery2);
				
				allLevel2Unit.addAll(menus2);
				
				for (Unitinfo menu : menus2) {
					StringBuffer sBuffer = new StringBuffer();
					sBuffer.append("{");
					sBuffer.append("id:\"" + menu.getId() + "\",");
					sBuffer.append("pId:\"" + menus.get(i).getId() + "\",");
					sBuffer.append("ttype:\"" + "unit" + "\",");
					sBuffer.append("open:" + (isopen ? "true" : "false") + ",");// 根节点打开

					sBuffer.append("name:\"" + menu.getName() + "\",");

					sBuffer.append("remark:\"" + "" + "\"");

					sBuffer.append("}");
					lstTree.add(sBuffer.toString());

				}
			}
			

			for (int i = 0; i < allLevel2Unit.size(); i++) {

				Areainfo query2 = new Areainfo();
				query2.setUnitId(allLevel2Unit.get(i).getId());
				query2.setDataState("1");

				List<Areainfo> all_menus = itemMapper.selectList(query2);

				for (Areainfo menu : all_menus) {
					StringBuffer sBuffer = new StringBuffer();
					sBuffer.append("{");
					sBuffer.append("id:\"" + menu.getId() + "\",");
					sBuffer.append("pId:\"" + allLevel2Unit.get(i).getId() + "\",");
					sBuffer.append("open:" + (isopen ? "true" : "false") + ",");// 根节点打开
					sBuffer.append("ttype:\"" + "area" + "\",");
					sBuffer.append("name:\"" + menu.getName() + "\",");

					sBuffer.append("remark:\"" + "" + "\"");

					sBuffer.append("}");
					lstTree.add(sBuffer.toString());

					if (level.equals("3")) {
						// 显示坐席

						Seatinfo query3 = new Seatinfo();
						query3.setAreaId(menu.getId());
						query3.setDataState("1");

						List<Seatinfo> seats = seatinfoMapper.selectList(query3);
						for (int j = 0; j < seats.size(); j++) {

							Seatinfo sinfo=seats.get(j);
							StringBuffer sBuffer2 = new StringBuffer();
							sBuffer2.append("{");
							sBuffer2.append("id:\"" + sinfo.getId() + "\",");
							sBuffer2.append("pId:\"" + menu.getId() + "\",");
							sBuffer2.append("open:" + (isopen ? "true" : "false") + ",");// 根节点打开
							sBuffer2.append("ttype:\"" + "seat" + "\",");
							sBuffer2.append("name:\"" + sinfo.getName() + "\",");

							sBuffer2.append("remark:\"" + "" + "\"");

							sBuffer2.append("}");
							lstTree.add(sBuffer2.toString());
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lstTree;
	}

	/**
	 * 单位、区域二级,坐席 select group ,
	 * 
	 * @param dict_type
	 * @return
	 * @author zj
	 * @date 2019年7月21日
	 */
	public List<String> getAreaTreeSelectSecond(Areainfo item, String level) {

		List<String> rst = new ArrayList<>();

		// 一级分类
		Unitinfo uquery = new Unitinfo();
		uquery.setDataState("1");
		uquery.setName(item.getName());
		uquery.setUnitType("1");
		uquery.setCurUid(TokenUtil.getWebLoginUser().getId());
		// uquery.set
		List<Unitinfo> menus = unitinfoMapper.selectLeftTreeList(uquery);

		Gson gs = new Gson();

		for (int i = 0; i < menus.size(); i++) {
			
			org.json.JSONObject jsObj22 = new org.json.JSONObject();

			Unitinfo pInfo22 = menus.get(i);
			String pInfoStr22 = gs.toJson(pInfo22);
			jsObj22 = new org.json.JSONObject(pInfoStr22);
			
			
			
			// 二级分类
			Unitinfo uquery22 = new Unitinfo();
			uquery22.setDataState("1");
			uquery22.setName(item.getName());
			uquery22.setParentUnit(menus.get(i).getId());
			uquery22.setCurUid(TokenUtil.getWebLoginUser().getId());
			// uquery.set
			List<Unitinfo> menus22 = unitinfoMapper.selectList(uquery22);
			if(menus22==null||menus22.size()==0)
				continue;
			
			
			
			for (int k = 0; k < menus22.size(); k++) {
				org.json.JSONObject jsObj = new org.json.JSONObject();

				Unitinfo pInfo = menus22.get(k);
				String pInfoStr = gs.toJson(pInfo);
				jsObj = new org.json.JSONObject(pInfoStr);

				Areainfo query2 = new Areainfo();
				query2.setUnitId(pInfo.getId());
				query2.setDataState("1");
				query2.setName(item.getName());

				List<Areainfo> all_menus = itemMapper.selectList(query2);

				if (level.equals("3")) {
					// 显示坐席
					for (int j = 0; j < all_menus.size(); j++) {

						Areainfo ainfo = all_menus.get(j);

						String ainfoStr = gs.toJson(ainfo);
						org.json.JSONObject ainfojsObj = new org.json.JSONObject(ainfoStr);

						Seatinfo query3 = new Seatinfo();
						query3.setAreaId(ainfo.getId());
						query3.setDataState("1");
						query3.setName(item.getName());

						List<Seatinfo> areas = seatinfoMapper.selectList(query3);
						String level3list = gs.toJson(areas);
						all_menus.get(j).setSeatListStr(level3list);
					}
				}

				String level2list = gs.toJson(all_menus);
				menus22.get(k).setUnitListStr(level2list);
				
			}
			
			String level2list = gs.toJson(menus22);
			jsObj22.put("child", level2list);

			rst.add(jsObj22.toString());

		}

		return rst;

	}

	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveAreainfo(Areainfo item) {
		JSONObject rtn = new JSONObject();

		try {

			item.setId(UUIDUtil.getUUID());

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
	public JSONObject updateAreainfo(Areainfo item) {
		JSONObject rtn = new JSONObject();

		if (null == item || null == item.getId()) {
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

	@Override
	public List<Areainfo> selectAreainfoList(Areainfo item) {
		List<Areainfo> itemList = new ArrayList<>();
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
	public int deleteAreainfo(Areainfo item) {
		int result = 0;
		try {

			result = itemMapper.delete(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return result;
	}

	@Override

	public Areainfo selectAreainfoById(String id) {

		Areainfo data = null;

		Areainfo query = new Areainfo();
		query.setId(id);

		List<Areainfo> datas = selectAreainfoList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

}
