/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.base.BaseController;
import me.anchora.garbage.entry.GarbageStations;
import me.anchora.garbage.entry.GarbageStationsCriteria;
import me.anchora.garbage.service.GarbageStationsService;
import me.anchora.garbage.utils.CriteriaUtil;
import me.anchora.garbage.utils.EntryUtil;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.PageTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GarbageStationsController extends BaseController {

    @Autowired
    private GarbageStationsService garbageStationsService;

    @RequestMapping(value = "/admin/getAllGarbageStationsByPage.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getAllGarbageStationsByPage(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageStations garbageStations = (GarbageStations) EntryUtil.getObject(request, GarbageStations.class);
            GarbageStationsCriteria criteria = (GarbageStationsCriteria)CriteriaUtil.createCriteria(garbageStations, GarbageStationsCriteria.class);
            PageTool.pageSetting(garbageStations, cacheService);

            List<GarbageStations> resultList = garbageStationsService.queryGarbageStationsByPage(criteria, garbageStations.getRowBounds());
            Integer count = garbageStationsService.queryCount(criteria);

            result.addList(resultList, getTimezone(request));
            result.addTotalPage(PageTool.pageCount(count, garbageStations.getPageSize()));
            result.addCurrentPage(garbageStations.getCurrentPage());
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
    
    @RequestMapping(value = "/admin/getAllGarbageStations.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getAllGarbageStations(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageStations garbageStations = (GarbageStations) EntryUtil.getObject(request, GarbageStations.class);
            GarbageStationsCriteria criteria = (GarbageStationsCriteria)CriteriaUtil.createCriteria(garbageStations, GarbageStationsCriteria.class);

            List<GarbageStations> resultList = garbageStationsService.queryAllGarbageStations(criteria);

            result.addList(resultList, getTimezone(request));
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/createGarbageStations.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void createGarbageStations(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageStations garbageStations = (GarbageStations) EntryUtil.getObject(request, GarbageStations.class);
            garbageStationsService.insert(garbageStations);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/updateGarbageStations.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void updateGarbageStations(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageStations garbageStations = (GarbageStations) EntryUtil.getObject(request, GarbageStations.class);
            garbageStationsService.update(garbageStations);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/deleteGarbageStations.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void deleteGarbageStations(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GarbageStations garbageStations = (GarbageStations) EntryUtil.getObject(request, GarbageStations.class);
            garbageStationsService.delete(garbageStations);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
}
