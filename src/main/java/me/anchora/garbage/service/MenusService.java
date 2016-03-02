/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service;

import java.util.List;
import java.util.Map;

public interface MenusService {
    public List<Map<String, Object>> queryMenus(String userName, String locale);
}
