/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.base.BaseController;
import me.anchora.garbage.entry.QrCodes;
import me.anchora.garbage.service.QrCodesService;
import me.anchora.garbage.utils.EntryUtil;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.PageTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class QrCodesController extends BaseController {

    @Autowired
    private QrCodesService qrCodesService;

    @RequestMapping(value = "/admin/getAllQrCodesByPage.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getAllQrCodesByPage(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            QrCodes qrCodes = (QrCodes) EntryUtil.getObject(request, QrCodes.class);
            PageTool.pageSetting(qrCodes, cacheService);

            List<QrCodes> resultList = qrCodesService.queryQrCodesByPageWithUser(qrCodes, qrCodes.getRowBounds());
            Integer count = qrCodesService.queryCountWithUser(qrCodes);

            result.addList(resultList, getTimezone(request));
            result.addTotalPage(PageTool.pageCount(count, qrCodes.getPageSize()));
            result.addCurrentPage(qrCodes.getCurrentPage());
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/createQrCodes.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void createQrCodes(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            QrCodes qrCodes = (QrCodes) EntryUtil.getObject(request, QrCodes.class);

            qrCodesService.insert(qrCodes);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/updateQrCodes.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void updateQrCodes(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            QrCodes qrCodes = (QrCodes) EntryUtil.getObject(request, QrCodes.class);

            qrCodesService.update(qrCodes);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }

    @RequestMapping(value = "/admin/deleteQrCodes.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void deleteQrCodes(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            QrCodes qrCodes = (QrCodes) EntryUtil.getObject(request, QrCodes.class);

            qrCodesService.delete(qrCodes);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
}
