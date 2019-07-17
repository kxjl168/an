/**
 * @(#)PriceLocksmithOtherService.java 2019-04-16 16:16:04
 * <p>
 * Copyright (C),2017-2018, kxjl
 * Co.,Ltd. All Rights Reserved.
 * kxjl PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.kxjl.tasktransferplat.service;
import java.util.List;

import com.kxjl.base.util.Message;
import com.kxjl.tasktransferplat.pojo.PriceLocksmithOther;

/**
 * @author 单肙
 * @version 1.0.0 2019-04-16 16:16:04
 * @since 1.0.0
 */
public interface PriceLocksmithOtherService {

  PriceLocksmithOther selectRule(String serverType, int parentType);

    Message deleteByPrimaryKey(Long id);

    Message insert(PriceLocksmithOther priceLocksmithOther);

    PriceLocksmithOther selectByPrimaryKey(Long id);

    Message updateByPrimaryKey(PriceLocksmithOther priceLocksmithOther);

    List<PriceLocksmithOther> selectList(PriceLocksmithOther priceLocksmithOther);

    /**
     * method with xml
     *
     * @param
     * @return
     * @author zhangyong
     * @date 2019-04-15 13:39:13
     */
    PriceLocksmithOther selectByType(String serverType,Integer parentType);
}
