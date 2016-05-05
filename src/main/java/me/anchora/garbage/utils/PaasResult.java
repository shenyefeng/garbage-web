/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.msg.MsgEnum;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class PaasResult {

    private static Logger logger = Logger.getLogger(PaasResult.class);
    private JSONObject result = new JSONObject();

    public PaasResult() {
        result.put("returnCode", MsgEnum.SUCCESS.getCode());
        result.put("returnMessage", MsgEnum.SUCCESS.getDescription());
    }

    public void setCodeAndMsg(String code, String msg) {
        result.put("returnCode", code);
        result.put("returnMessage", msg);
    }

    public void add(String key, Object value) {
        result.put(key, value);
    }

    public void addList(List<?> list) {
        result.put("list", JsonUtil.toJson(list));
    }

    public synchronized void addList(List<?> list, String tz) {
        TimeZone defaultTz = TimeZone.getDefault();
        JsonUtil.setTimeZone(TimeZone.getTimeZone(tz));
        result.put("list", JsonUtil.toJson(list));
//        JsonUtil.setTimeZone(TimeZone.getTimeZone(PropertyUtil.getProperty("cf.timezone")));
        JsonUtil.setTimeZone(defaultTz);
    }

    public void addCount(Integer count) {
        result.put("count", count);
    }

    public void addResult(Object obj) {
        result.put("result", JsonUtil.toJson(obj));
    }

    public void addCurrentPage(Integer currentPage) {
        result.put("currentPage", currentPage);
    }

    public void addTotalPage(Integer totalpage) {
        result.put("totalPage", totalpage);
    }

    public JSONObject returnResult() {
        return result;
    }

    public JSONObject createJson(String fieldMap) {
        JSONObject cJson = new JSONObject();
        if (fieldMap == null) {
            return cJson;
        }
        String[] fields = fieldMap.split(";");
        int lenght = fields.length;
        for (int i = 0; i < lenght; i++) {
            if (lenght <= 0) {
                continue;
            }
            String[] field = fields[i].split("=");
            String fromName = field[0];
            String toName = field.length == 1 ? field[0] : field[1];
            cJson.put(fromName, toName);
        }
        return cJson;
    }

    public void asynchronousPrintResult(HttpServletResponse response, Object value) {
    	asynchronousPrintResult(null, response, value);
    }

    public void asynchronousPrintResult(HttpServletRequest request, HttpServletResponse response, Object value) {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            String result;
            if(request != null && request.getParameter("callback") != null) {
            	result = request.getParameter("callback") + "(" + value.toString() + ")";
            } else {
            	result = value.toString();
            }
            logger.debug("result: " + result);
            writer.print(result);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("asynchronousPrintResult error!" + e);
        }
    }
}
