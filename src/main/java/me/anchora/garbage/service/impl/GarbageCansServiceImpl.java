/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service.impl;

import java.util.Date;
import java.util.List;

import me.anchora.garbage.base.BaseService;
import me.anchora.garbage.dao.GarbageCansMapper;
import me.anchora.garbage.entry.GarbageCans;
import me.anchora.garbage.entry.GarbageCansCriteria;
import me.anchora.garbage.service.GarbageCansService;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "garbageCansService")
public class GarbageCansServiceImpl extends BaseService implements GarbageCansService {

    @Autowired
    private GarbageCansMapper garbageCansMapper;

    @Override
    public void insert(GarbageCans garbageCans) {
        garbageCans.setCreatedAt(new Date());
        garbageCans.setCreatedBy(garbageCans.getAdminId());
        garbageCansMapper.insert(garbageCans);
        cacheService.doReloadGarbageCans();
    }

    @Override
    public void update(GarbageCans garbageCans) {
        garbageCans.setUpdatedAt(new Date());
        garbageCans.setUpdatedBy(garbageCans.getAdminId());
        garbageCansMapper.updateByPrimaryKeySelective(garbageCans);
        cacheService.doReloadGarbageCans();
    }

    @Override
    public void delete(GarbageCans garbageCans) {
        garbageCansMapper.deleteByPrimaryKey(garbageCans.getId());
        cacheService.doReloadGarbageCans();
    }

    @Override
    public List<GarbageCans> queryGarbageCansByPage(GarbageCansCriteria criteria, RowBounds rowBounds) {
        List<GarbageCans> result = garbageCansMapper.selectByExampleWithRowbounds(criteria, rowBounds);
        return result;
    }

    @Override
    public Integer queryCount(GarbageCansCriteria criteria) {
        Integer result = garbageCansMapper.countByExample(criteria);
        return result;
    }
}
