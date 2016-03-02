/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.anchora.garbage.entry.Users;
import me.anchora.garbage.msg.MsgEnum;
import me.anchora.garbage.utils.LogUtil;
import me.anchora.garbage.utils.PaasResult;
import me.anchora.garbage.utils.RequestUtil;

import org.apache.log4j.Logger;
import org.springframework.context.support.ResourceBundleMessageSource;

public class LoginFilter implements Filter {
    private static Logger logger = Logger.getLogger(LoginFilter.class);
    
    private ResourceBundleMessageSource messageSource;

    public LoginFilter() {
        if(messageSource == null) {
            messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("i18n/messages");
        }
    }
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Users users = (Users) req.getSession().getAttribute("users");
        PaasResult result = new PaasResult();
        try {
            if (users != null) {
                chain.doFilter(request, response);
            } else {
                result.setCodeAndMsg(MsgEnum.LOGIN.getCode(), messageSource.getMessage(MsgEnum.LOGIN.getCode(), null, MsgEnum.LOGIN.getDescription(), RequestUtil.getLocale(req)));
                result.asynchronousPrintResult(res, result.returnResult());
            }
        } catch (Exception e) {
            LogUtil.exception(logger, e);
            result.setCodeAndMsg(MsgEnum.SYS_ERROR.getCode(), messageSource.getMessage(MsgEnum.SYS_ERROR.getCode(), null, MsgEnum.SYS_ERROR.getDescription(), RequestUtil.getLocale(req)));
            result.asynchronousPrintResult(res, result.returnResult());
        }
    }

    public void destroy() {
    }
}
