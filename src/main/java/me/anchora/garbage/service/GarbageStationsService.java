/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service;

import java.util.List;

import me.anchora.garbage.entry.GarbageStations;
import me.anchora.garbage.entry.GarbageStationsCriteria;

import org.apache.ibatis.session.RowBounds;

public interface GarbageStationsService {

    public void insert(GarbageStations garbageStations);

    public void update(GarbageStations garbageStations);

    public void delete(GarbageStations garbageStations);

    public List<GarbageStations> queryGarbageStationsByPage(GarbageStationsCriteria criteria, RowBounds rowBounds);

    public Integer queryCount(GarbageStationsCriteria criteria);

    public List<GarbageStations> queryAllGarbageStations(GarbageStationsCriteria criteria);
}
