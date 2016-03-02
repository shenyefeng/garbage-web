package me.anchora.garbage.utils;

import java.util.Map;

import me.anchora.garbage.cache.EhcacheManager;
import me.anchora.garbage.entry.SystemConfig;
import me.anchora.garbage.service.CacheService;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

public class ConfigUtil {

    private static Logger logger = Logger.getLogger(ConfigUtil.class);

    @SuppressWarnings("unchecked")
    public static String getConfig(CacheService cacheService, String configName) {
        Element e = EhcacheManager.getInstance().get(SystemConfig.class.getName());
        Map<String, SystemConfig> systemConfigMap;
        String result = null;
        if (e == null) {
            cacheService.doReloadConfigCache();
            e = EhcacheManager.getInstance().get(SystemConfig.class.getName());
            systemConfigMap = (Map<String, SystemConfig>) e.getObjectValue();
        } else {
            systemConfigMap = (Map<String, SystemConfig>) e.getObjectValue();
        }
        if (!systemConfigMap.containsKey(configName) || systemConfigMap.get(configName).getConfigValue() == null || "".equals(systemConfigMap.get(configName).getConfigValue())) {
            logger.info("Config " + configName + " has not been setted.");
        } else {
            result = systemConfigMap.get(configName).getConfigValue();
            if(result != null) result = result.trim();
        }
        return result;
    }
}
