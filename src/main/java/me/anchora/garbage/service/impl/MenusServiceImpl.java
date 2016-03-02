/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.anchora.garbage.base.BaseService;
import me.anchora.garbage.dao.MenusMapper;
import me.anchora.garbage.entry.Menus;
import me.anchora.garbage.msg.MsgEnum;
import me.anchora.garbage.service.MenusService;
import me.anchora.garbage.utils.CacheUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "menusService")
public class MenusServiceImpl extends BaseService implements MenusService {

    private static Logger logger = Logger.getLogger(MenusServiceImpl.class);
    
    @Autowired
    private MenusMapper menusMapper;

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> queryMenus(String userName, String locale) {
//        List<Menus> menusList = menusMapper.selectWithMany(locale);
        List<Menus> menusList = (List<Menus>)CacheUtil.getLists(cacheService, MsgEnum.CACHE_MENUS.getCode() + locale + userName);
        
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Map<String, Object>> tmpmap = new HashMap<String, Map<String,Object>>();
        Map<String, Object> firstLevelMenu;
        List<Map<String, Object>> subMenus;
        Map<String, Object> secondryLevelMenu;
        for(Menus menu : menusList) {
            if(menu.getParentMenuLanguageUser() == null) {
                firstLevelMenu = new HashMap<String, Object>();
                
                subMenus = new ArrayList<Map<String,Object>>();
                firstLevelMenu.put("title", menu.getMenuDetails().getMenuValue());
                firstLevelMenu.put("styleClass", menu.getStyleClass());
                firstLevelMenu.put("menuItemId", menu.getMenuItemId());
                firstLevelMenu.put("subMenus", subMenus);
                tmpmap.put(menu.getMenuLanguageUser(), firstLevelMenu);
                result.add(firstLevelMenu);
            } else {
                if(tmpmap.containsKey(menu.getParentMenuLanguageUser())) {
                    firstLevelMenu = tmpmap.get(menu.getParentMenuLanguageUser());
                    subMenus = (List<Map<String, Object>>)firstLevelMenu.get("subMenus");
                    secondryLevelMenu = new HashMap<String, Object>();
                    secondryLevelMenu.put("menuItemId", menu.getMenuItemId());
                    secondryLevelMenu.put("title", menu.getMenuDetails().getMenuValue());
                    subMenus.add(secondryLevelMenu);
                } else {
                    logger.warn("Menu configuration error. Menu id:" + menu.getId());
                }
            }
        }
        
        return result;
    }
}
