/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.base.BaseController;
import me.anchora.garbage.entry.Garbages;
import me.anchora.garbage.service.GarbagesService;
import me.anchora.garbage.utils.EntryUtil;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.PageTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GarbagesController extends BaseController {

    @Autowired
    private GarbagesService garbagesService;

    @RequestMapping(value = "/admin/getAllGarbagesByPage.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getAllGarbagesByPage(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            Garbages garbages = (Garbages) EntryUtil.getObject(request, Garbages.class);
            PageTool.pageSetting(garbages, cacheService);

            List<Garbages> resultList = garbagesService.queryGarbagesByPageWithOther(garbages, garbages.getRowBounds());
            Integer count = garbagesService.queryCountWithOther(garbages);

            result.addList(resultList, getTimezone(request));
            result.addTotalPage(PageTool.pageCount(count, garbages.getPageSize()));
            result.addCurrentPage(garbages.getCurrentPage());
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/createGarbages.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void createGarbages(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            Garbages garbages = (Garbages) EntryUtil.getObject(request, Garbages.class);

            garbagesService.insert(garbages);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/updateGarbages.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void updateGarbages(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            Garbages garbages = (Garbages) EntryUtil.getObject(request, Garbages.class);

            garbagesService.update(garbages);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/deleteGarbages.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void deleteGarbages(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            Garbages garbages = (Garbages) EntryUtil.getObject(request, Garbages.class);

            garbagesService.delete(garbages);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
}
