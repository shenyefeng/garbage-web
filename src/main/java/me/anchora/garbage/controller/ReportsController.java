/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.base.BaseController;
import me.anchora.garbage.entry.base.ReportVo;
import me.anchora.garbage.service.GarbagesService;
import me.anchora.garbage.utils.EntryUtil;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.PageTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReportsController extends BaseController {

    @Autowired
    private GarbagesService garbagesService;

    /**
     * 垃圾报表
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/admin/garbageTypeReport.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void garbageReport(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            List<ReportVo> resultList = garbagesService.garbageTypeReport();
            result.addList(resultList, getTimezone(request));
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
    
    @RequestMapping(value = "/admin/userGarbageTypeReport.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void userGarbageTypeReport(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            ReportVo vo = (ReportVo) EntryUtil.getObject(request, ReportVo.class);
            PageTool.pageSetting(vo, cacheService);

            List<ReportVo> resultList = garbagesService.userGarbageTypeReport(vo, vo.getRowBounds());
            Integer count = garbagesService.userGarbageTypeReportCount(vo);

            result.addList(resultList, getTimezone(request));
            result.addTotalPage(PageTool.pageCount(count, vo.getPageSize()));
            result.addCurrentPage(vo.getCurrentPage());
            
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
}
