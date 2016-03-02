/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.base.BaseController;
import me.anchora.garbage.service.MenusService;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.RequestUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MenusController extends BaseController {

    @Autowired
    private MenusService menusService;

    @RequestMapping(value = "/admin/getMenus.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void getMenus(HttpServletRequest request, HttpServletResponse response) {

        result = new PaasResult();
        try {
            String userName = (String)request.getSession().getAttribute("userName");
            String locale = RequestUtil.getLocale(request).toString();
            List<Map<String, Object>> resultList = menusService.queryMenus(userName, locale);

            result.addList(resultList);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
}
