/**
 * @(#)AreaService.java 2019/2/15 14:01
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.kxjl.tasktransferplat.pojo.AreaCity;
import com.kxjl.tasktransferplat.pojo.AreaDistrict;
import com.kxjl.tasktransferplat.pojo.AreaProvince;

/**
 * @author 单肙
 * @version 1.0.0 2019/2/15 14:01
 * @since 1.0.0
 */
public interface AreaService {

	
	
	/**
     *  构造锁匠作业区域树数据
     * @param areacode
     * @param user_id
     * @return
     * @author zj
     * @date 2019年5月13日
     */
	public List<String> getDistrctTree(String areacode,String user_id,String enableCheck) ;
	
	
	
    /**
     * 通过省市区的名称获取
     * @param province
     * @param city
     * @param district
     * @return
     */
    AreaDistrict getAreaCodeByNameInfo(String province, String city, String district);

    /**
     * 通过城市代码获得地区信息
     * @param cityCode
     * @return
     */
    AreaDistrict getAreaDistrictByCode(String cityCode);


    /**
     * 通过json文件生成数据库数据
     * @param filePath
     */
    void generateDataByJsonFile (String filePath);

    /**
     * 通过数据库生成字符串序列
     */
    void generateSequence () throws IOException;

    /**
     * 查询所有省份
     * @return
     */
    List<AreaProvince> allProvince();

    /**
     * 查询省份下的所有市
     * @return
     */
    List<AreaCity> provinceAllCity(AreaProvince areaProvince);

    /**
     * 查询市下的所有地区
     * @return
     */
    List<AreaDistrict> cityAllDistrict(AreaCity areaCity);

    /**
     * 直辖市直接查询所有地区
     * @return
     */
    List<AreaDistrict> municipalityAllDistrict (AreaProvince areaProvince);

    /**
     * 查询所有有安装业务的省份
     * @return
     */
    List<AreaProvince> lockInstallProvince();

    /**
     * 查询所有有安装业务的市区
     * @param areaProvince
     * @return
     */
    List<AreaCity> lockInstallCity(AreaProvince areaProvince);

    List<AreaCity> lockInstallCityByProvinceCode(AreaProvince areaProvince);

    Map selectAll(int areaCode);

    /**
     * 根据区id获取省市区名
     * @param districtId
     * @return
     */
    String selectAddressByDistrictId(Integer districtId);
}
