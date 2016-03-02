/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.base.BaseController;
import me.anchora.garbage.entry.GiftGrants;
import me.anchora.garbage.service.GiftGrantsService;
import me.anchora.garbage.utils.EntryUtil;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.PageTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GiftGrantsController extends BaseController {

    @Autowired
    private GiftGrantsService giftGrantsService;

    @RequestMapping(value = "/admin/getAllGiftGrantsByPage.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getAllGiftGrantsByPage(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GiftGrants giftGrants = (GiftGrants) EntryUtil.getObject(request, GiftGrants.class);
            PageTool.pageSetting(giftGrants, cacheService);

            List<GiftGrants> resultList = giftGrantsService.queryGiftGrantsByPageWithUser(giftGrants, giftGrants.getRowBounds());
            Integer count = giftGrantsService.queryCountWithUser(giftGrants);

            result.addList(resultList, getTimezone(request));
            result.addTotalPage(PageTool.pageCount(count, giftGrants.getPageSize()));
            result.addCurrentPage(giftGrants.getCurrentPage());
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/createGiftGrants.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void createGiftGrants(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GiftGrants giftGrants = (GiftGrants) EntryUtil.getObject(request, GiftGrants.class);

            giftGrantsService.insert(giftGrants);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/updateGiftGrants.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void updateGiftGrants(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GiftGrants giftGrants = (GiftGrants) EntryUtil.getObject(request, GiftGrants.class);

            giftGrantsService.update(giftGrants);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/deleteGiftGrants.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void deleteGiftGrants(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            GiftGrants giftGrants = (GiftGrants) EntryUtil.getObject(request, GiftGrants.class);

            giftGrantsService.delete(giftGrants);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
}
