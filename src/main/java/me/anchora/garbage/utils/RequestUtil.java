package me.anchora.garbage.utils;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static Locale getLocale(HttpServletRequest request) {
        Locale locale;
        Cookie[] cookies = request.getCookies();
        String cookieLang = null;
        if(cookies != null && cookies.length > 0) {
            for(Cookie cookie : cookies){
                if("lang".equals(cookie.getName())) {
                    cookieLang = cookie.getValue();
                    break;
                }
            }
        }
        
        if(cookieLang != null && cookieLang.length() > 0 && cookieLang.indexOf("_") > 0) {
            locale = new Locale(cookieLang.split("_")[0], cookieLang.split("_")[1]);
        } else {
            locale = request.getLocale();
        }
        
        if(locale == null) {
            locale = Locale.SIMPLIFIED_CHINESE;
        }
        return locale;
    }
}
