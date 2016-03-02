/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.anchora.garbage.cache.EhcacheManager;
import me.anchora.garbage.dao.GarbageCansMapper;
import me.anchora.garbage.dao.GarbageStationsMapper;
import me.anchora.garbage.dao.GarbageTypesMapper;
import me.anchora.garbage.dao.GiftsMapper;
import me.anchora.garbage.dao.MenusMapper;
import me.anchora.garbage.dao.SystemConfigMapper;
import me.anchora.garbage.dao.UsersMapper;
import me.anchora.garbage.entry.GarbageCans;
import me.anchora.garbage.entry.GarbageCansCriteria;
import me.anchora.garbage.entry.GarbageStations;
import me.anchora.garbage.entry.GarbageStationsCriteria;
import me.anchora.garbage.entry.GarbageTypes;
import me.anchora.garbage.entry.GarbageTypesCriteria;
import me.anchora.garbage.entry.Gifts;
import me.anchora.garbage.entry.GiftsCriteria;
import me.anchora.garbage.entry.Menus;
import me.anchora.garbage.entry.SystemConfig;
import me.anchora.garbage.entry.SystemConfigCriteria;
import me.anchora.garbage.entry.Users;
import me.anchora.garbage.entry.UsersCriteria;
import me.anchora.garbage.msg.MsgEnum;
import me.anchora.garbage.service.CacheService;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "cacheService")
public class CacheServiceImpl implements CacheService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Autowired
    private GarbageCansMapper garbageCansMapper;

    @Autowired
    private GarbageTypesMapper garbageTypesMapper;

    @Autowired
    private GarbageStationsMapper garbageStationsMapper;

    @Autowired
    private GiftsMapper giftsMapper;
    
    @Autowired
    private MenusMapper menusMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public void doReloadConfigCache() {
        Map<String, SystemConfig> systemConfigMap = new HashMap<String, SystemConfig>();
        SystemConfigCriteria criteria = new SystemConfigCriteria();
        List<SystemConfig> systemConfigList = systemConfigMapper.selectByExample(criteria);
        for (SystemConfig systemConfig : systemConfigList) {
            systemConfigMap.put(systemConfig.getConfigName(), systemConfig);
        }
        EhcacheManager.getInstance().put(new Element(SystemConfig.class.getName(), systemConfigMap));
    }

    @Override
    public void doReloadGarbageCans() {
        GarbageCansCriteria criteria = new GarbageCansCriteria();
        List<GarbageCans> garbageCansList = garbageCansMapper.selectByExample(criteria);
        EhcacheManager.getInstance().put(new Element(MsgEnum.CACHE_GARBAGE_CANS.getCode(), garbageCansList));
    }

    @Override
    public void doReloadGarbageTypes() {
        GarbageTypesCriteria criteria = new GarbageTypesCriteria();
        List<GarbageTypes> list = garbageTypesMapper.selectByExample(criteria);
        EhcacheManager.getInstance().put(new Element(MsgEnum.CACHE_GARBAGE_TYPES.getCode(), list));
    }
    
    @Override
    public void doReloadGarbageStations() {
        GarbageStationsCriteria criteria = new GarbageStationsCriteria();
        List<GarbageStations> list = garbageStationsMapper.selectByExample(criteria);
        EhcacheManager.getInstance().put(new Element(MsgEnum.CACHE_GARBAGE_STATIONS.getCode(), list));
    }

    @Override
    public void doReloadGifts() {
        GiftsCriteria criteria = new GiftsCriteria();
        List<Gifts> list = giftsMapper.selectByExample(criteria);
        EhcacheManager.getInstance().put(new Element(MsgEnum.CACHE_GIFTS.getCode(), list));
    }

    @Override
    public void doReloadMenus() {
        List<Menus> list = menusMapper.selectWithMany();
        Map<String, List<Menus>> map = new HashMap<String, List<Menus>>();
        List<Menus> menusList;
        String localeAndUserName;
        for(Menus menus : list) {
            localeAndUserName = menus.getMenuLanguages().getLangName() + menus.getUsers().getUserName();
            if(map.containsKey(localeAndUserName)) {
                menusList = map.get(localeAndUserName);
                menusList.add(menus);
            } else {
                menusList = new ArrayList<Menus>();
                menusList.add(menus);
                map.put(localeAndUserName, menusList);
            }
        }
        for(String key : map.keySet()) {
            EhcacheManager.getInstance().put(new Element(MsgEnum.CACHE_MENUS.getCode() + key, map.get(key)));
        }
    }

    @Override
    public void doReloadUsers() {
        UsersCriteria criteria = new UsersCriteria();
        List<Users> list = usersMapper.selectByExample(criteria);
        EhcacheManager.getInstance().put(new Element(MsgEnum.CACHE_USERS.getCode(), list));
    }
    
    @Override
    public void doReloadAllCache() {
        doReloadConfigCache();
        doReloadGarbageCans();
        doReloadGarbageTypes();
        doReloadGarbageStations();
        doReloadGifts();
        doReloadMenus();
        doReloadUsers();
    }
}
