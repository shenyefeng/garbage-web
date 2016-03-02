/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service;

import java.util.List;

import me.anchora.garbage.entry.GarbageTypes;
import me.anchora.garbage.entry.GarbageTypesCriteria;

import org.apache.ibatis.session.RowBounds;

public interface GarbageTypesService {

    public void insert(GarbageTypes garbageTypes);

    public void update(GarbageTypes garbageTypes);

    public void delete(GarbageTypes garbageTypes);

    public List<GarbageTypes> queryGarbageTypesByPage(GarbageTypesCriteria criteria, RowBounds rowBounds);

    public Integer queryCount(GarbageTypesCriteria criteria);

    public List<GarbageTypes> queryAllGarbageTypes(GarbageTypesCriteria criteria);
}
