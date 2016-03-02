/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service.impl;

import java.util.Date;
import java.util.List;

import me.anchora.garbage.base.BaseService;
import me.anchora.garbage.dao.GarbageStationsMapper;
import me.anchora.garbage.entry.GarbageStations;
import me.anchora.garbage.entry.GarbageStationsCriteria;
import me.anchora.garbage.service.GarbageStationsService;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "garbageStationsService")
public class GarbageStationsServiceImpl extends BaseService implements GarbageStationsService {

    @Autowired
    private GarbageStationsMapper garbageStationsMapper;

    @Override
    public void insert(GarbageStations garbageStations) {
        garbageStations.setCreatedAt(new Date());
        garbageStations.setCreatedBy(garbageStations.getAdminId());
        garbageStationsMapper.insert(garbageStations);
        cacheService.doReloadGarbageStations();
    }

    @Override
    public void update(GarbageStations garbageStations) {
        garbageStations.setUpdatedAt(new Date());
        garbageStations.setUpdatedBy(garbageStations.getAdminId());
        garbageStationsMapper.updateByPrimaryKeySelective(garbageStations);
        cacheService.doReloadGarbageStations();
    }

    @Override
    public void delete(GarbageStations garbageStations) {
        garbageStationsMapper.deleteByPrimaryKey(garbageStations.getId());
        cacheService.doReloadGarbageStations();
   }

    @Override
    public List<GarbageStations> queryGarbageStationsByPage(GarbageStationsCriteria criteria, RowBounds rowBounds) {
        List<GarbageStations> result = garbageStationsMapper.selectByExampleWithRowbounds(criteria, rowBounds);
        return result;
    }

    @Override
    public Integer queryCount(GarbageStationsCriteria criteria) {
        Integer result = garbageStationsMapper.countByExample(criteria);
        return result;
    }

    @Override
    public List<GarbageStations> queryAllGarbageStations(GarbageStationsCriteria criteria) {
        List<GarbageStations> result = garbageStationsMapper.selectByExample(criteria);
        return result;
    }
}
