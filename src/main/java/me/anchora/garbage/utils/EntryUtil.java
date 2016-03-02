package me.anchora.garbage.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class EntryUtil {
    private static final Logger logger = Logger.getLogger(EntryUtil.class);

    public static Object getObject(HttpServletRequest request, Class<?> clazz) {
        String jsonStr = getJsonStr(request);
        return JsonUtil.toObject(jsonStr, clazz);
    }

    public static Object getObject(HttpServletRequest request, Class<?> clazz, Map<String, Class<?>> classMap) {
        String jsonStr = getJsonStr(request);
        return JsonUtil.toObject(jsonStr, clazz, classMap);
    }

    private static String getJsonStr(HttpServletRequest request) {
        StringBuffer jsonStr = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jsonStr.append(line);
            }
        } catch (IOException e) {
            LogUtil.exception(logger, e);
        }

        String result = jsonStr.toString();
        if (result == null || "".equals(result)) {
            result = "{\"adminId\":" + request.getSession().getAttribute("userId") + "}";
        } else {
            result = result.substring(0, result.length()-1) + ",\"adminId\":" + request.getSession().getAttribute("userId") + "}";
        }
        

        result = result.replaceAll("\"\"", "null");
        result = result.replaceAll("\"\\[", "\"##\\[##");
        logger.info("Json string from request.length():" + result.length());
        if (result.length() > 256) {
            logger.info("Json string from request:" + result.substring(0, 256) + "...");
        } else {
            logger.info("Json string from request:" + result);
        }
        return result;
    }
}
