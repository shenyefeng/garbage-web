/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service;

import java.util.List;

import me.anchora.garbage.entry.GarbageCans;
import me.anchora.garbage.entry.GarbageCansCriteria;

import org.apache.ibatis.session.RowBounds;

public interface GarbageCansService {

    public void insert(GarbageCans garbageCans);

    public void update(GarbageCans garbageCans);

    public void delete(GarbageCans garbageCans);

    public List<GarbageCans> queryGarbageCansByPage(GarbageCansCriteria criteria, RowBounds rowBounds);

    public Integer queryCount(GarbageCansCriteria criteria);
}
