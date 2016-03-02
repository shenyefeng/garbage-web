/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service.impl;

import java.util.Date;
import java.util.List;

import me.anchora.garbage.base.BaseService;
import me.anchora.garbage.dao.SystemConfigMapper;
import me.anchora.garbage.entry.SystemConfig;
import me.anchora.garbage.entry.SystemConfigCriteria;
import me.anchora.garbage.service.ConfigService;
import me.anchora.garbage.utils.SecurityUtil;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "configService")
public class ConfigServiceImpl extends BaseService implements ConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    public void insert(SystemConfig systemConfig) {
        if(systemConfig.getIsEncrypt() != null && systemConfig.getIsEncrypt()) {
            systemConfig.setConfigValue(SecurityUtil.encrypt(systemConfig.getConfigValue()));
        }
        systemConfig.setCreatedAt(new Date());
        systemConfig.setCreatedBy(systemConfig.getAdminId());
        systemConfigMapper.insert(systemConfig);
        cacheService.doReloadConfigCache();
    }

    @Override
    public void update(SystemConfig systemConfig) {
        systemConfig.setUpdatedAt(new Date());
        systemConfig.setUpdatedBy(systemConfig.getAdminId());
       systemConfigMapper.updateByPrimaryKeySelective(systemConfig);
       cacheService.doReloadConfigCache();
    }

    @Override
    public SystemConfig queryByName(String configName) {
        SystemConfigCriteria criteria = new SystemConfigCriteria();
        criteria.createCriteria().andConfigNameEqualTo(configName);
        List<SystemConfig> systemConfigList;
        SystemConfig result = null;
        systemConfigList = systemConfigMapper.selectByExample(criteria);
        if (systemConfigList != null && systemConfigList.size() != 0) {
            result = systemConfigList.get(0);
        }
        return result;
    }

    @Override
    public List<SystemConfig> queryAll() {
        return systemConfigMapper.selectByExample(new SystemConfigCriteria());
    }

    @Override
    public void delete(SystemConfig systemConfig) {
        systemConfigMapper.deleteByPrimaryKey(systemConfig.getId());
        cacheService.doReloadConfigCache();
    }

    @Override
    public List<SystemConfig> querySystemConfigByPage(SystemConfigCriteria criteria, RowBounds rowBounds) {
        List<SystemConfig> result = systemConfigMapper.selectByExampleWithRowbounds(criteria, rowBounds);
        return result;
    }

    @Override
    public Integer queryCount(SystemConfigCriteria criteria) {
        Integer result = systemConfigMapper.countByExample(criteria);
        return result;
    }
}
