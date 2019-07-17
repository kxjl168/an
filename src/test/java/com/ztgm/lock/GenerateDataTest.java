/**
 * @(#)GenerateDataTest.java 2019/2/15 14:35
 * <p>
 * Copyright (C),2017-2018, ZHONGTONGGUOMAI TECHNOLOGY NANJING
 * Co.,Ltd. All Rights Reserved.
 * GMWL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ztgm.lock;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kxjl.tasktransferplat.service.AreaService;

import java.io.IOException;

/**
 * @author 单肙
 * @version 1.0.0 2019/2/15 14:35
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateDataTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void generateTest () {
        String filePath = "C:\\Users\\handsome Xiao\\Desktop\\工作\\city.json";
        areaService.generateDataByJsonFile(filePath);
    }

    @Test
    public void generateSequenceTest () throws IOException {
        areaService.generateSequence();
    }
}
