/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service.impl;

import java.util.Date;
import java.util.List;

import me.anchora.garbage.base.BaseService;
import me.anchora.garbage.dao.GarbageTypesMapper;
import me.anchora.garbage.entry.GarbageTypes;
import me.anchora.garbage.entry.GarbageTypesCriteria;
import me.anchora.garbage.service.GarbageTypesService;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "garbageTypesService")
public class GarbageTypesServiceImpl extends BaseService implements GarbageTypesService {

    @Autowired
    private GarbageTypesMapper garbageTypesMapper;

    @Override
    public void insert(GarbageTypes garbageTypes) {
        garbageTypes.setCreatedAt(new Date());
        garbageTypes.setCreatedBy(garbageTypes.getAdminId());
        garbageTypesMapper.insert(garbageTypes);
        cacheService.doReloadGarbageTypes();
    }

    @Override
    public void update(GarbageTypes garbageTypes) {
        garbageTypes.setUpdatedAt(new Date());
        garbageTypes.setUpdatedBy(garbageTypes.getAdminId());
        garbageTypesMapper.updateByPrimaryKeySelective(garbageTypes);
        cacheService.doReloadGarbageTypes();
    }

    @Override
    public void delete(GarbageTypes garbageTypes) {
        garbageTypesMapper.deleteByPrimaryKey(garbageTypes.getId());
        cacheService.doReloadGarbageTypes();
   }

    @Override
    public List<GarbageTypes> queryGarbageTypesByPage(GarbageTypesCriteria criteria, RowBounds rowBounds) {
        List<GarbageTypes> result = garbageTypesMapper.selectByExampleWithRowbounds(criteria, rowBounds);
        return result;
    }

    @Override
    public Integer queryCount(GarbageTypesCriteria criteria) {
        Integer result = garbageTypesMapper.countByExample(criteria);
        return result;
    }

    @Override
    public List<GarbageTypes> queryAllGarbageTypes(GarbageTypesCriteria criteria) {
        List<GarbageTypes> result = garbageTypesMapper.selectByExample(criteria);
        return result;
    }
}
