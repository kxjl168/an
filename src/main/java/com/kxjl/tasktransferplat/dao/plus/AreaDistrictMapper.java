/**
 * @(#)AreaMapper.java 2019/2/15 14:07
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.dao.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kxjl.tasktransferplat.pojo.AreaCity;
import com.kxjl.tasktransferplat.pojo.AreaDistrict;
import com.kxjl.tasktransferplat.pojo.AreaProvince;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019/2/15 14:07
 * @since 1.0.0
 */
public interface AreaDistrictMapper extends BaseMapper<AreaDistrict> {

	
	
	/**
	 * 通过areacode 前4位模糊匹配第一个区域。兼容源匠已有数据
	 * @param areaCode
	 * @return
	 * @author zj
	 * @date 2019年5月6日
	 */
    Map selectMostLikeArea(@Param("areaCode")String areaCode);
	
    /**
     * 通过省市区的名称获取
     * @param province
     * @param city
     * @param district
     * @return
     */
    List<AreaDistrict> getAreaCodeByNameInfo(@Param("province") String province, @Param("city") String city, @Param("district") String district);

    /**
     * 生成省市区表的数据
     * @param areaProvinceList
     * @param areaCityList
     * @param areaDistrictList
     */
    void generateData(@Param("areaProvinceList") List<AreaProvince> areaProvinceList,
                      @Param("areaCityList") List<AreaCity> areaCityList,
                      @Param("areaDistrictList") List<AreaDistrict> areaDistrictList);

    /**
     * 查询直辖市下的所有地区
     * @param areaProvince
     * @return
     */
    List<AreaDistrict> municipalityAllDistrict(AreaProvince areaProvince);

    /**
     * 根据城市6位完整编码获取地区信息
     * @param cityCode
     * @return
     */
    List<AreaDistrict> getAreaDistrictCode(@Param("cityCode") String cityCode);
    
    
   /**
    * 根据锁匠id获取其已设置作业行政区域
    * @param userId
    * @return
    * @author zj
    * @date 2019年5月13日
    */
    List<AreaDistrict> getAreaDistrictByUserId(@Param("userId") String userId);
    
    
    
   /**
    * 根据所选地市code 获取地市下的行政区域
    * @param userId
    * @return
    * @author zj
    * @date 2019年5月13日
    */
    List<AreaDistrict> getAreaDistrictByCityId(@Param("cityId") String cityId);
    
    

    /**
     * 通过获取地市区
     * @param areaCode
     * @return
     */
    Map selectAll(@Param("areaCode")int areaCode);
}
