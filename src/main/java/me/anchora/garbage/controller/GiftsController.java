/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.base.BaseController;
import me.anchora.garbage.entry.Gifts;
import me.anchora.garbage.entry.GiftsCriteria;
import me.anchora.garbage.service.GiftsService;
import me.anchora.garbage.utils.CriteriaUtil;
import me.anchora.garbage.utils.EntryUtil;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.PageTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GiftsController extends BaseController {

    @Autowired
    private GiftsService giftsService;

    @RequestMapping(value = "/admin/getAllGiftsByPage.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getAllGiftsByPage(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            Gifts gifts = (Gifts) EntryUtil.getObject(request, Gifts.class);
            GiftsCriteria criteria = (GiftsCriteria)CriteriaUtil.createCriteria(gifts, GiftsCriteria.class);
            PageTool.pageSetting(gifts, cacheService);

            List<Gifts> resultList = giftsService.queryGiftsByPage(criteria, gifts.getRowBounds());
            Integer count = giftsService.queryCount(criteria);

            result.addList(resultList, getTimezone(request));
            result.addTotalPage(PageTool.pageCount(count, gifts.getPageSize()));
            result.addCurrentPage(gifts.getCurrentPage());
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
    
    @RequestMapping(value = "/admin/getAllGifts.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getAllGifts(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            Gifts gifts = (Gifts) EntryUtil.getObject(request, Gifts.class);
            GiftsCriteria criteria = (GiftsCriteria)CriteriaUtil.createCriteria(gifts, GiftsCriteria.class);

            List<Gifts> resultList = giftsService.queryAllGifts(criteria);

            result.addList(resultList, getTimezone(request));
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/createGifts.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void createGifts(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            Gifts gifts = (Gifts) EntryUtil.getObject(request, Gifts.class);
            giftsService.insert(gifts);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/updateGifts.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void updateGifts(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            Gifts gifts = (Gifts) EntryUtil.getObject(request, Gifts.class);
            giftsService.update(gifts);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/deleteGifts.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void deleteGifts(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            Gifts gifts = (Gifts) EntryUtil.getObject(request, Gifts.class);
            giftsService.delete(gifts);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
}
