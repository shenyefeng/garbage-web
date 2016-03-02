/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.controller;

import static me.anchora.garbage.Constants.POINT_NUM;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.base.BaseController;
import me.anchora.garbage.entry.base.CommonVo;
import me.anchora.garbage.service.GarbagesService;
import me.anchora.garbage.service.UsersService;
import me.anchora.garbage.utils.ConfigUtil;
import me.anchora.garbage.utils.EntryUtil;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.RequestUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller(value = "overviewController")
public class OverviewController extends BaseController {
    private static Logger logger = Logger.getLogger(OverviewController.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private GarbagesService garbagesService;

    @RequestMapping(value = "/admin/usersCountChart.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void usersCountChart(HttpServletRequest request, HttpServletResponse response) {
        result = new PaasResult();
        try {
            CommonVo commonVo = (CommonVo) EntryUtil.getObject(request, CommonVo.class);
            if (commonVo.getPointNum() == null || "".equals(commonVo.getPointNum())) {
                String pointNum = ConfigUtil.getConfig(cacheService, "chart_point_num");
                if (pointNum == null || "".equals(pointNum)) {
                    logger.info("chart_point_num has not bean configured! Default value " + POINT_NUM + " has been setted.");
                    pointNum = POINT_NUM;
                }
                commonVo.setPointNum(pointNum);
            }

            List<Map<String, Object>> resultList = usersService.usersChart(commonVo, RequestUtil.getLocale(request));
            result.addList(resultList);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
    
    @RequestMapping(value = "/admin/garbagesCountChart.html", method = { RequestMethod.POST, RequestMethod.GET })
    public void garbagesCountChart(HttpServletRequest request, HttpServletResponse response) {
        result = new PaasResult();
        try {
            CommonVo commonVo = (CommonVo) EntryUtil.getObject(request, CommonVo.class);
            if (commonVo.getPointNum() == null || "".equals(commonVo.getPointNum())) {
                String pointNum = ConfigUtil.getConfig(cacheService, "chart_point_num");
                if (pointNum == null || "".equals(pointNum)) {
                    logger.info("chart_point_num has not bean configured! Default value " + POINT_NUM + " has been setted.");
                    pointNum = POINT_NUM;
                }
                commonVo.setPointNum(pointNum);
            }

            List<Map<String, Object>> resultList = garbagesService.garbagesChart(commonVo, RequestUtil.getLocale(request));
            result.addList(resultList);
        } catch (Exception e) {
            super.exception(request, e);
        }

        result.asynchronousPrintResult(response, result.returnResult());
    }
}
