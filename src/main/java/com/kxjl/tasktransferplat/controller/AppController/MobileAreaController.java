package com.kxjl.tasktransferplat.controller.AppController;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.kxjl.base.aopAspect.NoNeedAuthorization;
import com.kxjl.base.util.AppResult;
import com.kxjl.base.util.AppResultUtil;
import com.kxjl.base.util.StringUtil;
import com.kxjl.tasktransferplat.dto.request.BaseRequestDto;
import com.kxjl.tasktransferplat.pojo.AreaCity;
import com.kxjl.tasktransferplat.pojo.AreaDistrict;
import com.kxjl.tasktransferplat.pojo.AreaProvince;
import com.kxjl.tasktransferplat.service.AreaService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: zhangyong
 * @Description:
 * @Data: Created in 10:02 2019/5/14
 */
@Controller
@RequestMapping("/interface/area")
public class MobileAreaController {

    private Logger logger = Logger.getLogger(MobileAreaController.class);

    @Autowired
    private AreaService areaService;

    /**
     * 获取所有省列表
     *
     * @param request
     * @param response
     * @return
     * @author zhangyong
     * @date 2019/5/14
     */
    @ResponseBody
    @RequestMapping(value = "/provinceList")
    @NoNeedAuthorization
    public AppResult provinceList(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<AreaProvince> areaProvinceList = areaService.allProvince();
            return AppResultUtil.success(areaProvinceList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AppResultUtil.fail();
        }
    }

    /**
     * 获取省下所有城市列表
     *
     * @param request
     * @param response
     * @return
     * @author zhangyong
     * @date 2019/5/14
     */
    @ResponseBody
    @RequestMapping(value = "/cityList")
    @NoNeedAuthorization
    public AppResult cityList(BaseRequestDto baseRequestDto, HttpServletRequest request, HttpServletResponse response) {
        try {
            AreaProvince areaProvince = JSONObject.parseObject(baseRequestDto.getData(),AreaProvince.class);
            List<AreaCity> areaProvinceList = areaService.provinceAllCity(areaProvince);
            return AppResultUtil.success(areaProvinceList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AppResultUtil.fail();
        }
    }

    /**
     * 获取所有省列表
     *
     * @param request
     * @param response
     * @return
     * @author zhangyong
     * @date 2019/5/14
     */
    @ResponseBody
    @RequestMapping(value = "/districtList")
    @NoNeedAuthorization
    public AppResult districtList(BaseRequestDto baseRequestDto, HttpServletRequest request, HttpServletResponse response) {
        try {
            AreaCity areaCity = JSONObject.parseObject(baseRequestDto.getData(),AreaCity.class);
            List<AreaDistrict> areaProvinceList = areaService.cityAllDistrict(areaCity);
            return AppResultUtil.success(areaProvinceList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AppResultUtil.fail();
        }
    }

}
