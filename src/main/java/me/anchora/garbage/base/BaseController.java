/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.base;

import static me.anchora.garbage.Constants.DEFAULT_TIMEONE;

//import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
//import me.anchora.inpaasmgr.utils.PropertyUtil;

import me.anchora.garbage.exception.AppException;
import me.anchora.garbage.msg.MsgEnum;
import me.anchora.garbage.service.CacheService;
import me.anchora.garbage.utils.ConfigUtil;
import me.anchora.garbage.utils.LogUtil;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.RequestUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class BaseController {

    public PaasResult result;
    private static Logger logger = Logger.getLogger(BaseController.class);

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected CacheService cacheService;

    public String exception(HttpServletRequest request, Throwable e) {
        LogUtil.exception(logger, e);

        String returnMsg;
        if (e instanceof AppException) {
            returnMsg = ((AppException) e).getMessage(messageSource, RequestUtil.getLocale(request));
            result.setCodeAndMsg(((AppException) e).getMessageKey(), returnMsg);
        } else {
            returnMsg = messageSource.getMessage(MsgEnum.SYS_ERROR.getCode(), null, MsgEnum.SYS_ERROR.getDescription(), RequestUtil.getLocale(request));
            result.setCodeAndMsg(MsgEnum.SYS_ERROR.getCode(), returnMsg);
        }

        return returnMsg;
    }

    public String getTimezone(HttpServletRequest request) {
        String timezone = (String)request.getSession().getAttribute("timezone");
        if (timezone == null || timezone.length() == 0) {
            timezone = ConfigUtil.getConfig(cacheService, "timezone");
        }
        if (timezone == null || timezone.length() == 0) {
            logger.info("timezone has not bean configured! Default value " + DEFAULT_TIMEONE + " has been setted.");
            timezone = DEFAULT_TIMEONE;
        }
        return timezone;
    }
    
    public BaseController() {
        //TimeZone.setDefault(TimeZone.getTimeZone(PropertyUtil.getProperty("cf.timezone")));
    }
}
