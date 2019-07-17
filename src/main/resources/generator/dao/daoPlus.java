

/**
     * 列表查询
     * @param item
     * @return
     * @author ${author}
     * @date 2019年1月7日
     */
    List<${modelClass}> selectList(${modelClass} item);
    
    /**
     * 删除
     * @param record
     * @return
     * @author ${author}
     * @date ${generatedate}
     */
    int delete(${modelClass} item);
    
    
    
    /**
     * 分页查询,
     * @param page
     * @param orderinfoAdd
     * @return
     */
    IPage<${modelClass}> selectPage(Page page, ${modelClass} item);