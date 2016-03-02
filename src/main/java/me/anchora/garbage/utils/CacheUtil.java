package me.anchora.garbage.utils;

import java.util.List;

import me.anchora.garbage.cache.EhcacheManager;
import me.anchora.garbage.service.CacheService;
import net.sf.ehcache.Element;

public class CacheUtil {
    // private static Logger logger = Logger.getLogger(CacheUtil.class);

    public static List<?> getLists(CacheService cacheService, String name) {

        Element e = EhcacheManager.getInstance().get(name);
        List<?> list;
        if (e == null) {
            cacheService.doReloadAllCache();
            e = EhcacheManager.getInstance().get(name);
            list = (List<?>) e.getObjectValue();
        } else {
            list = (List<?>) e.getObjectValue();
        }
        return list;
    }
}
