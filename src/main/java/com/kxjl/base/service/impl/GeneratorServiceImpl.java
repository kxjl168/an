package com.kxjl.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kxjl.base.dao.GeneratorMapper;
import com.kxjl.base.pojo.AField;
import com.kxjl.base.pojo.Generator;
import com.kxjl.base.service.GeneratorService;

import java.util.List;

@Service
public class GeneratorServiceImpl implements GeneratorService{

    @Autowired
    private GeneratorMapper generatorMapper;

    @Override
    public List<Generator> selectGeneratorList(Generator generator) {
        return generatorMapper.selectGeneratorList(generator);
    }
    
    
    /**
     * 查询表字段
     * @param generator
     * @return
     * @author zj
     * @date 2019年1月8日
     */
    public List<AField> selectTableColList(Generator generator){
    	  return generatorMapper.selectTableColList(generator);
    }
}
