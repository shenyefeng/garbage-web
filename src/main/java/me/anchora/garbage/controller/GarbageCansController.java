/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.base.BaseController;
import me.anchora.garbage.entry.GarbageCans;
import me.anchora.garbage.entry.GarbageCansCriteria;
import me.anchora.garbage.service.GarbageCansService;
import me.anchora.garbage.utils.CriteriaUtil;
import me.anchora.garbage.utils.EntryUtil;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.PageTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GarbageCansController extends BaseController {

    @Autowired
    private GarbageCansService garbageCansService;

    @RequestMapping(value = "/admin/getAllGarbageCansByPage.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getAllGarbageCansByPage(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageCans garbageCans = (GarbageCans) EntryUtil.getObject(request, GarbageCans.class);
            GarbageCansCriteria criteria = (GarbageCansCriteria)CriteriaUtil.createCriteria(garbageCans, GarbageCansCriteria.class);
            PageTool.pageSetting(garbageCans, cacheService);

            List<GarbageCans> resultList = garbageCansService.queryGarbageCansByPage(criteria, garbageCans.getRowBounds());
            Integer count = garbageCansService.queryCount(criteria);

            result.addList(resultList, getTimezone(request));
            result.addTotalPage(PageTool.pageCount(count, garbageCans.getPageSize()));
            result.addCurrentPage(garbageCans.getCurrentPage());
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/createGarbageCans.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void createGarbageCans(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageCans garbageCans = (GarbageCans) EntryUtil.getObject(request, GarbageCans.class);
            garbageCansService.insert(garbageCans);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/updateGarbageCans.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void updateGarbageCans(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageCans garbageCans = (GarbageCans) EntryUtil.getObject(request, GarbageCans.class);
            garbageCansService.update(garbageCans);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/deleteGarbageCans.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void deleteGarbageCans(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageCans garbageCans = (GarbageCans) EntryUtil.getObject(request, GarbageCans.class);
            garbageCansService.delete(garbageCans);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
}
