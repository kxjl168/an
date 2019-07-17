/**
 * @(#)AreaServiceImpl.java 2019/2/15 14:06
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.kxjl.base.pojo.Manager;
import com.kxjl.base.pojo.Role;
import com.kxjl.tasktransferplat.dao.plus.AreaCityMapper;
import com.kxjl.tasktransferplat.dao.plus.AreaDistrictMapper;
import com.kxjl.tasktransferplat.dao.plus.AreaProvinceMapper;
import com.kxjl.tasktransferplat.pojo.AreaCity;
import com.kxjl.tasktransferplat.pojo.AreaDistrict;
import com.kxjl.tasktransferplat.pojo.AreaProvince;
import com.kxjl.tasktransferplat.pojo.TempGaode;
import com.kxjl.tasktransferplat.service.AreaService;
import com.kxjl.tasktransferplat.util.SnowflakeIdWorker;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 单肙
 * @version 1.0.0 2019/2/15 14:06
 * @since 1.0.0
 */
@Service
public class AreaServiceImpl implements AreaService {


    @Autowired
    private AreaDistrictMapper areaDistrictMapper;

    @Autowired
    private AreaProvinceMapper areaProvinceMapper;

    @Autowired
    private AreaCityMapper areaCityMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    
    
    /**
     *  构造锁匠作业区域树数据
     * @param areacode
     * @param user_id
     * @return
     * @author zj
     * @date 2019年5月13日
     */
	public List<String> getDistrctTree(String areacode,String user_id,String enableCheck) {
		List<String> lstTree = new ArrayList<String>();

		try {

			List<AreaDistrict> Managerroles = new ArrayList<>();
			if(user_id!=null&&!user_id.equals(""))
			Managerroles = areaDistrictMapper.getAreaDistrictByUserId(user_id);

			
			// 一级菜单
			Role q = new Role();
			q.setAvailable("1");
			
			
			
			List<AreaDistrict> allroles = areaDistrictMapper.getAreaDistrictByCityId(areacode);

			String rootid = "0";
			for (AreaDistrict role : allroles) {
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append("{");
				sBuffer.append("id:\"" + role.getId() + "\",");
				sBuffer.append("pId:\"" + rootid + "\",");
				
				
				sBuffer.append("chkDisabled:"+enableCheck+",");// 是否禁用
				
				sBuffer.append("open:true,");// 根节点打开

				if (Managerroles != null)
					for (int i = 0; i < Managerroles.size(); i++) {
						if (Managerroles.get(i).getId().equals(role.getId())) {
							sBuffer.append("checked:true,");// 选中
							break;
						}
					}

				sBuffer.append("name:\"" + role.getDistrictName() + "\",");

				sBuffer.append("remark:\"" + "" + "\"");
				// sBuffer.append("iconSkin:\"" + "diygroup" + "\"");
				// sBuffer.append("icon:\"" + "images/group2.png" + "\",");

				sBuffer.append("}");
				lstTree.add(sBuffer.toString());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lstTree;
	}
    
    
    /**
     * 通过省市区的名称获取
     *
     * @param province
     * @param city
     * @param district
     * @return
     */
    @Override
    public AreaDistrict getAreaCodeByNameInfo(String province, String city, String district) {

        List<AreaDistrict> areaDistrictList = areaDistrictMapper.getAreaCodeByNameInfo(province, city, district);
        if (areaDistrictList == null || areaDistrictList.isEmpty()) {
           return null;
        }
        AreaDistrict areaDistrict = areaDistrictList.get(0);
        return areaDistrict;
    }

    /**
     * 通过城市代码获得地区信息
     * @param cityCode
     * @return
     */
    @Override
    public AreaDistrict getAreaDistrictByCode(String cityCode) {
        List<AreaDistrict> areaDistrictList = areaDistrictMapper.getAreaDistrictCode(cityCode);
        if (areaDistrictList == null || areaDistrictList.isEmpty()) {
            throw new RuntimeException("未查询到相关地区，创建工单失败");
        }
        AreaDistrict areaDistrict = areaDistrictList.get(0);
        return areaDistrict;
    }

    /**
     * 通过json文件生成数据库数据
     * @param filePath
     */
    @Override
    public void generateDataByJsonFile(String filePath) {
        List<AreaProvince> areaProvinceList = new ArrayList<>();
        List<AreaCity> areaCityList = new ArrayList<>();
        List<AreaDistrict> areaDistrictList = new ArrayList<>();

        int provinceId = 1;
        int cityId = 1;
        int districtId = 1;
        try {
            File file = new File(filePath);
            InputStream inputStream = new FileInputStream(file);
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            Gson gson = new GsonBuilder().create();

            // Read file in stream mode
            reader.beginArray();
            while (reader.hasNext()) {
                // Read data into object model
                TempGaode province = gson.fromJson(reader, TempGaode.class);
                String provinceName = province.getName();

                AreaProvince areaProvince = new AreaProvince();
                areaProvince.setId(provinceId);
                areaProvince.setProvinceName(provinceName);
                areaProvince.setProvinceCode(province.getAdCode().substring(0, 2));
                areaProvinceList.add(areaProvince);

                if (provinceName.endsWith("市")) {
                    AreaCity areaCity = new AreaCity();
                    areaCity.setId(cityId);
                    areaCity.setCityName(provinceName);
                    areaCity.setCityCode("01");
                    areaCity.setProvinceId(provinceId);
                    areaCityList.add(areaCity);
                    cityId ++;
                }

                if (provinceName.equals("重庆市")) {
                    AreaCity areaCity = new AreaCity();
                    areaCity.setId(cityId);
                    areaCity.setCityName(provinceName);
                    areaCity.setCityCode("02");
                    areaCity.setProvinceId(provinceId);
                    areaCityList.add(areaCity);
                    cityId ++;
                }

                List<TempGaode> gaodeCityList = province.getBeanList();
                for (TempGaode city : gaodeCityList) {
                    if (provinceName.endsWith("市")) {
                        String middleCode = city.getAdCode().substring(2, 4);
                        AreaDistrict areaDistrict = new AreaDistrict();
                        areaDistrict.setId(districtId ++);
                        areaDistrict.setDistrictName(city.getName());
                        areaDistrict.setDistrictCode(city.getAdCode().substring(4));
                        if (provinceName.equals("重庆市")) {
                            if ("01".equals(middleCode)) {
                                areaDistrict.setCityId(cityId - 2);
                            } else {
                                areaDistrict.setCityId(cityId - 1);
                            }
                        } else {
                            areaDistrict.setCityId(cityId - 1);
                        }
                        areaDistrictList.add(areaDistrict);
                        continue;
                    }
                    AreaCity areaCity = new AreaCity();
                    areaCity.setId(cityId);
                    areaCity.setCityName(city.getName());
                    areaCity.setCityCode(city.getAdCode().substring(2, 4));
                    areaCity.setProvinceId(areaProvince.getId());
                    areaCityList.add(areaCity);

                    List<TempGaode> gaodeDistrictList = city.getBeanList();
                    for (TempGaode districtGaode : gaodeDistrictList) {
                        AreaDistrict areaDistrict = new AreaDistrict();
                        areaDistrict.setId(districtId ++);
                        areaDistrict.setDistrictName(districtGaode.getName());
                        areaDistrict.setDistrictCode(districtGaode.getAdCode().substring(4));
                        areaDistrict.setCityId(cityId);
                        areaDistrictList.add(areaDistrict);
                    }
                    cityId ++;
                }
                provinceId ++;
            }
            reader.close();
            areaDistrictMapper.generateData(areaProvinceList, areaCityList, areaDistrictList);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void generateSequence() throws IOException {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            List<AreaProvince> areaProvinces = areaProvinceMapper.selectList(queryWrapper);
            List<AreaCity> areaCities = areaCityMapper.selectList(queryWrapper);

            //创建省市表
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet0 = workbook.createSheet();
            int provinceRowNo = 0;
            int provinceColumnNo = 0;
            for (AreaProvince areaProvince : areaProvinces) {
                Row row = sheet0.createRow(provinceRowNo ++);
                Cell provinceNameCell = row.createCell(provinceColumnNo ++);
                provinceNameCell.setCellValue(areaProvince.getProvinceName());
                QueryWrapper cityQueryWrapper = new QueryWrapper();
                cityQueryWrapper.eq("ProvinceId", areaProvince.getId());
                List<AreaCity> areaCityList = areaCityMapper.selectList(cityQueryWrapper);
                for (AreaCity areaCity : areaCityList) {
                    Cell cityNameCell = row.createCell(provinceColumnNo ++);
                    cityNameCell.setCellValue(areaCity.getCityName());
                }
                provinceColumnNo = 0;
            }

            //创建市区表
            Sheet sheet1 = workbook.createSheet();
            int cityRowNo = 0;
            int cityColumnNo = 0;
            for (AreaCity areaCity : areaCities) {
                Row row = sheet1.createRow(cityRowNo ++);
                Cell cityNameCell = row.createCell(cityColumnNo ++);
                cityNameCell.setCellValue(areaCity.getCityName());
                QueryWrapper districtQueryWrapper = new QueryWrapper();
                districtQueryWrapper.eq("CityId", areaCity.getId());
                List<AreaDistrict> districtList = areaDistrictMapper.selectList(districtQueryWrapper);
                for (AreaDistrict areaDistrict : districtList) {
                    Cell districtNameCell = row.createCell(cityColumnNo ++);
                    districtNameCell.setCellValue(areaDistrict.getDistrictName());
                }
                cityColumnNo = 0;
            }

            String fileName = "C:\\Users\\handsome Xiao\\Desktop\\工作\\省市区.xlsx";
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName));
            workbook.write(bufferedOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
        }
    }

    /**
     * 查询所有省份
     * @return
     */
    @Override
    public List<AreaProvince> allProvince() {
        QueryWrapper queryWrapper = new QueryWrapper();
        return areaProvinceMapper.selectList(queryWrapper);
    }

    /**
     * 查询省份下的所有市
     * @return
     */
    @Override
    public List<AreaCity> provinceAllCity(AreaProvince areaProvince) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("provinceId", areaProvince.getId());
        return areaCityMapper.selectList(queryWrapper);
    }

    /**
     * 查询所有地区
     * @return
     */
    @Override
    public List<AreaDistrict> cityAllDistrict(AreaCity areaCity) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("cityId", areaCity.getId());
        return areaDistrictMapper.selectList(queryWrapper);
    }

    /**
     * 直辖市直接查询所有
     * @return
     */
    @Override
    public List<AreaDistrict> municipalityAllDistrict (AreaProvince areaProvince) {
        return areaDistrictMapper.municipalityAllDistrict(areaProvince);
    }

    /**
     * 查询所有有安装业务的省份
     * @return
     */
    @Override
    public List<AreaProvince> lockInstallProvince() {
        return areaProvinceMapper.lockInstallProvince();
    }

    /**
     * 查询所有有安装业务的市区
     * @param areaProvince
     * @return
     */
    @Override
    public List<AreaCity> lockInstallCity(AreaProvince areaProvince) {
        areaProvince = areaProvinceMapper.selectById(areaProvince.getId());
        return areaCityMapper.lockInstallCity(areaProvince);
    }

    /**
     * 查询所有有安装业务的市区
     * @param areaProvince
     * @return
     */
    @Override
    public List<AreaCity> lockInstallCityByProvinceCode(AreaProvince areaProvince) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("provinceCode", areaProvince.getProvinceCode());
        areaProvince = areaProvinceMapper.selectOne(queryWrapper);
        return areaCityMapper.lockInstallCity(areaProvince);
    }

    @Override
    public Map selectAll(int areaCode) {
        return areaDistrictMapper.selectAll(areaCode);
    }

    /**
     * 根据区id查询省市区名
     * @param districtId
     * @return
     */
    @Override
    public String selectAddressByDistrictId(Integer districtId) {
        AreaDistrict areaDistrict = areaDistrictMapper.selectById(districtId);
        AreaCity areaCity = areaCityMapper.selectById(areaDistrict.getCityId());
        AreaProvince areaProvince = areaProvinceMapper.selectById(areaCity.getProvinceId());
        return areaProvince.getProvinceName()+areaCity.getCityName()+areaDistrict.getDistrictName();
    }
}
