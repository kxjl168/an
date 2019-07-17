package com.kxjl.base.service;

import java.util.List;

import com.kxjl.base.pojo.AField;
import com.kxjl.base.pojo.Generator;

public interface GeneratorService {

    List<Generator> selectGeneratorList(Generator generator);
    
    /**
     * 查询表字段
     * @param generator
     * @return
     * @author zj
     * @date 2019年1月8日
     */
    List<AField> selectTableColList(Generator generator);
}
