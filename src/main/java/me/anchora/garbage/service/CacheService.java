/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service;

public interface CacheService {

    public void doReloadAllCache();

    public void doReloadConfigCache();

    public void doReloadGarbageCans();
    
    public void doReloadGarbageStations();

    public void doReloadGarbageTypes();
    
    public void doReloadGifts();
    
    public void doReloadMenus();
    
    public void doReloadUsers();
}
