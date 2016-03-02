/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @Filename: IpUtils.java
 * 
 * @Description:
 * 
 * @Version: 1.0
 * 
 * @Author: yfshen
 * 
 * @Email: yfshen@anchora.me
 * 
 * 
 * @History:<br> <li>Author: yfshen</li> <li>Date: 2012-12-12</li> <li>Version:
 *               1.0</li> <li>Content: create</li>
 * 
 */
public class IpUtils {

    public static String getRemoteIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
