package me.anchora.garbage.utils;

import java.util.Map;

import me.anchora.garbage.cache.EhcacheManager;
import me.anchora.garbage.entry.SystemConfig;
import me.anchora.garbage.entry.base.PageEntry;
import me.anchora.garbage.service.CacheService;
import net.sf.ehcache.Element;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

public class PageTool {
    private static Logger logger = Logger.getLogger(PageTool.class);
    private static Integer PAGESIZE = 10;

    @SuppressWarnings("unchecked")
    public static void pageSetting(PageEntry pageEntry, CacheService cacheService) {
        Integer currentPage = pageEntry.getCurrentPage();
        Integer pageSize = pageEntry.getPageSize();

        if (pageSize != null && !pageSize.equals(Long.valueOf(0))) {
            pageEntry.setPageSize(pageSize);
        } else {
            Element e = EhcacheManager.getInstance().get(SystemConfig.class.getName());
            Map<String, SystemConfig> systemConfigMap;
            if (e == null) {
                cacheService.doReloadConfigCache();
                e = EhcacheManager.getInstance().get(SystemConfig.class.getName());
                systemConfigMap = (Map<String, SystemConfig>) e.getObjectValue();
            } else {
                systemConfigMap = (Map<String, SystemConfig>) e.getObjectValue();
            }
            if (!systemConfigMap.containsKey("page_size") || systemConfigMap.get("page_size").getConfigValue() == null
                    || "".equals(systemConfigMap.get("page_size").getConfigValue())) {
                logger.warn("page_size has not been setted. Default value 10 is setted.");
                pageSize = PAGESIZE;
            } else {
                pageSize = Integer.valueOf(systemConfigMap.get("page_size").getConfigValue());
            }
            pageEntry.setPageSize(pageSize);
        }

        if (currentPage != null && !currentPage.equals(Integer.valueOf(0))) {
            pageEntry.setCurrentPage(currentPage);
        } else {
            logger.warn("currentPage has not setted, default value will be setted.");
            pageEntry.setCurrentPage(1);
        }

        Integer startRow = pageEntry.getPageSize() * (pageEntry.getCurrentPage() - 1);
        pageEntry.setStartRow(startRow);

        pageEntry.setRowBounds(new RowBounds(startRow, pageSize));
    }

    public static Integer pageCount(Integer count, Integer pageSize) {
        Integer result = count / pageSize;
        if (count % pageSize > 0) {
            result++;
        }
        return result;
    }
}
