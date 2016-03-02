package me.anchora.garbage.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.log4j.Logger;

public class EhcacheManager {
    private static Logger logger = Logger.getLogger(EhcacheManager.class);
    private static Cache ehCache = null;

    private EhcacheManager() {

    }

    public static synchronized Cache getInstance() {
        if (ehCache == null) {
            logger.info("The first time to create cache!");
            CacheManager manager = CacheManager.create();
            ehCache = manager.getCache("ehcache");
        }
        return ehCache;
    }
}
