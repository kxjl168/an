/**
 * @(#)AreaController.java 2019/2/18 14:07
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.controller.WebController;


import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.pojo.AreaCity;
import com.kxjl.tasktransferplat.pojo.AreaDistrict;
import com.kxjl.tasktransferplat.pojo.AreaProvince;
import com.kxjl.tasktransferplat.service.AreaService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 单肙
 * @version 1.0.0 2019/2/18 14:07
 * @since 1.0.0
 */
@Controller
@RequestMapping("/manager/area")
public class AreaController {

    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

   /**
    * 获取锁匠作业区域 zTree
    * @param user_id
    * @param areaCode
    * @return
    * @author zj
    * @date 2019年5月13日
    */
	@RequestMapping(value = { "/getDistrictTree" }, method = RequestMethod.POST)
	public @ResponseBody List<String> getRoleTree(@RequestParam("user_id") String user_id,@RequestParam("cityId") String cityId,
			@RequestParam("enableCheck") String enableCheck) {
		// 获取锁匠作业区域
		return areaService.getDistrctTree(cityId, user_id,enableCheck);
	}
    
	


    
    /**
     * 查询所有省
     * @return
     */
    @RequestMapping("/allProvince")
    @ResponseBody
    public List<AreaProvince> allProvince () {
        List<AreaProvince> provinces;

        try {
            provinces = areaService.allProvince ();
        } catch (Exception e) {
            logger.error("查询省份失败", e);
            provinces = null;
        }
        return provinces;
    }

    /**
     * 查询所有有安装业务的省分
     * @return
     */
    @RequestMapping("/lockInstallProvince")
    @ResponseBody
    public List<AreaProvince> lockInstallProvince () {
        List<AreaProvince> provinces;

        try {
            provinces = areaService.lockInstallProvince ();
        } catch (Exception e) {
            logger.error("查询省份失败", e);
            provinces = null;
        }
        return provinces;
    }

    /**
     * 查询所有有安装业务的省分
     * @return
     */
    @RequestMapping("/lockInstallCity")
    @ResponseBody
    public List<AreaCity> lockInstallCity (AreaProvince areaProvince) {
        List<AreaCity> cities;

        try {
            cities = areaService.lockInstallCity (areaProvince);
        } catch (Exception e) {
            logger.error("查询城市失败", e);
            cities = null;
        }
        return cities;
    }

    /**
     * 查询省份下的所有市
     * @return
     */
    @RequestMapping("/provinceAllCity")
    @ResponseBody
    public List<AreaCity> provinceAllCity (AreaProvince areaProvince) {
        List<AreaCity> cities;

        try {
            cities = areaService.provinceAllCity (areaProvince);
        } catch (Exception e) {
            logger.error("查询城市失败", e);
            cities = null;
        }
        return cities;
    }

    /**
     * 查询城市下所有地区
     * @return
     */
    @RequestMapping("/cityAllDistrict")
    @ResponseBody
    public List<AreaDistrict> cityAllDistrict (AreaCity areaCity) {
        List<AreaDistrict> districts;

        try {
            districts = areaService.cityAllDistrict (areaCity);
        } catch (Exception e) {
            logger.error("查询省份失败", e);
            districts = null;
        }
        return districts;
    }

    /**
     * 根据provinceCode查询省下所有市区
     * @return
     */
    @RequestMapping("/selectCityListByProvinceCode")
    @ResponseBody
    public List<AreaCity> selectCityListByProvinceCode (AreaProvince areaProvince) {
        List<AreaCity> cityList;
        try {
            cityList = areaService.lockInstallCityByProvinceCode (areaProvince);
        } catch (Exception e) {
            logger.error("查询省份失败", e);
            cityList = null;
        }
        return cityList;
    }
}
