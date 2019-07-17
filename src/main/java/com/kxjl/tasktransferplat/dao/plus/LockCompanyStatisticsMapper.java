package com.kxjl.tasktransferplat.dao.plus;

import java.util.List;

import com.kxjl.tasktransferplat.pojo.LockCMStistics;
import com.kxjl.tasktransferplat.pojo.LockCPStatistics;
import com.kxjl.tasktransferplat.pojo.MouthOrderStatistics;

public interface LockCompanyStatisticsMapper {

    /**
     * 列表查询
     * @param item
     * @return
     * @author wx
     * @date
     */
    List<LockCMStistics> selectMouthList(LockCMStistics item);

    List<LockCMStistics> selectOrderList(LockCMStistics item);

    List<MouthOrderStatistics> selectYearList(LockCMStistics item);

    List<LockCPStatistics> selectProvinceList(LockCMStistics item);

    List<LockCPStatistics> selectTurnoverList(LockCMStistics item);
}
