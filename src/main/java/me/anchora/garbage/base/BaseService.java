/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.base;

import java.util.Locale;

import me.anchora.garbage.msg.MsgEnum;
import me.anchora.garbage.service.CacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class BaseService {
//    private static Logger logger = Logger.getLogger(BaseService.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    protected CacheService cacheService;

    protected String getMsg(String code, Locale locale) {
        return messageSource.getMessage(code, null, MsgEnum.getByCode(code).getDescription(), locale);
    }

    protected String getMsg(String code, Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, MsgEnum.getByCode(code).getDescription(), locale);
    }
}
