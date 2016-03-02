/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service;

import java.util.List;

import me.anchora.garbage.entry.SystemConfig;
import me.anchora.garbage.entry.SystemConfigCriteria;
import me.anchora.garbage.exception.AppException;

import org.apache.ibatis.session.RowBounds;

public interface ConfigService {

    public void insert(SystemConfig systemConfig);

    public void update(SystemConfig systemConfig);

    public SystemConfig queryByName(String configName) throws AppException;

    public List<SystemConfig> queryAll();

    public void delete(SystemConfig systemConfig);

    public List<SystemConfig> querySystemConfigByPage(SystemConfigCriteria criteria, RowBounds rowBounds);

    public Integer queryCount(SystemConfigCriteria criteria);
}
