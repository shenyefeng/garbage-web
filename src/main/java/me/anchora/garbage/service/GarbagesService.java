/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.anchora.garbage.entry.Garbages;
import me.anchora.garbage.entry.GarbagesCriteria;
import me.anchora.garbage.entry.base.CommonVo;
import me.anchora.garbage.entry.base.ReportVo;

import org.apache.ibatis.session.RowBounds;

public interface GarbagesService {
    public List<Garbages> queryGarbagesByPage(GarbagesCriteria criteria, RowBounds rowBounds);

    public Integer queryCount(GarbagesCriteria criteria);

    public void insert(Garbages garbages);

    public void update(Garbages garbages);

    public void delete(Garbages garbages);

    public List<Map<String, Object>> garbagesChart(CommonVo commonVo, Locale locale);

    public List<Garbages> queryGarbagesByPageWithOther(Garbages garbages, RowBounds rowBounds);

    public Integer queryCountWithOther(Garbages garbages);

    public List<Garbages> queryAllGarbages(GarbagesCriteria criteria);
    
    public List<ReportVo> garbageTypeReport();

    public List<ReportVo> userGarbageTypeReport(ReportVo reportVo, RowBounds rowBounds);

    public Integer userGarbageTypeReportCount(ReportVo vo);
}
