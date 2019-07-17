/**
 * @(#)MobilePriceController.java 2019/4/22
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package com.kxjl.tasktransferplat.controller.AppController;

import com.github.pagehelper.Page;
import com.kxjl.base.aopAspect.FunLogType;
import com.kxjl.base.aopAspect.ManagerActionLog;
import com.kxjl.base.base.PageCondition;
import com.kxjl.base.util.PageUtil;
import com.kxjl.tasktransferplat.dao.plus.PriceLocksmithOtherMapper;
import com.kxjl.tasktransferplat.pojo.PriceLocksmithOther;
import com.kxjl.tasktransferplat.service.PriceLockCompanyService;
import com.kxjl.tasktransferplat.service.PriceLocksmithOtherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 *
 *@author shurui
 *@date 2019/4/22
 */
@RequestMapping("/interface/price")
@Controller
public class MobilePriceController {

    @Autowired
    private PriceLocksmithOtherService priceLocksmithOtherService;


    /**
     * 查询锁匠其他服务费用列表
     *
     * @param priceLocksmithOther
     * @return
     */
    @RequestMapping("/selectList")
    @ResponseBody
    public String selectList(PriceLocksmithOther priceLocksmithOther, HttpServletRequest request, PageCondition pageCondition) {
        Page page = PageUtil.getPage(pageCondition);
        List<PriceLocksmithOther> priceLocksmithOtherList = priceLocksmithOtherService.selectList(priceLocksmithOther);
        String result = PageUtil.packageTableData(page, priceLocksmithOtherList);
        return result;
    }

}
