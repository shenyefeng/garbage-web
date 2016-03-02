/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.base.BaseController;
import me.anchora.garbage.entry.GarbageTypes;
import me.anchora.garbage.entry.GarbageTypesCriteria;
import me.anchora.garbage.service.GarbageTypesService;
import me.anchora.garbage.utils.CriteriaUtil;
import me.anchora.garbage.utils.EntryUtil;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.PageTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GarbageTypesController extends BaseController {

    @Autowired
    private GarbageTypesService garbageTypesService;

    @RequestMapping(value = "/admin/getAllGarbageTypesByPage.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getAllGarbageTypesByPage(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageTypes garbageTypes = (GarbageTypes) EntryUtil.getObject(request, GarbageTypes.class);
            GarbageTypesCriteria criteria = (GarbageTypesCriteria)CriteriaUtil.createCriteria(garbageTypes, GarbageTypesCriteria.class);
            PageTool.pageSetting(garbageTypes, cacheService);

            List<GarbageTypes> resultList = garbageTypesService.queryGarbageTypesByPage(criteria, garbageTypes.getRowBounds());
            Integer count = garbageTypesService.queryCount(criteria);

            result.addList(resultList, getTimezone(request));
            result.addTotalPage(PageTool.pageCount(count, garbageTypes.getPageSize()));
            result.addCurrentPage(garbageTypes.getCurrentPage());
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
    
    @RequestMapping(value = "/admin/getAllGarbageTypes.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getAllGarbageTypes(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageTypes garbageTypes = (GarbageTypes) EntryUtil.getObject(request, GarbageTypes.class);
            GarbageTypesCriteria criteria = (GarbageTypesCriteria)CriteriaUtil.createCriteria(garbageTypes, GarbageTypesCriteria.class);

            List<GarbageTypes> resultList = garbageTypesService.queryAllGarbageTypes(criteria);

            result.addList(resultList, getTimezone(request));
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/createGarbageTypes.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void createGarbageTypes(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageTypes garbageTypes = (GarbageTypes) EntryUtil.getObject(request, GarbageTypes.class);
            garbageTypesService.insert(garbageTypes);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/updateGarbageTypes.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void updateGarbageTypes(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageTypes garbageTypes = (GarbageTypes) EntryUtil.getObject(request, GarbageTypes.class);
            garbageTypesService.update(garbageTypes);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/deleteGarbageTypes.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void deleteGarbageTypes(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageTypes garbageTypes = (GarbageTypes) EntryUtil.getObject(request, GarbageTypes.class);
            garbageTypesService.delete(garbageTypes);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
}
