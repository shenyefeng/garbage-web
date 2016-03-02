/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service;

import java.util.List;

import me.anchora.garbage.entry.Gifts;
import me.anchora.garbage.entry.GiftsCriteria;

import org.apache.ibatis.session.RowBounds;

public interface GiftsService {

    public void insert(Gifts gifts);

    public void update(Gifts gifts);

    public void delete(Gifts gifts);

    public List<Gifts> queryGiftsByPage(GiftsCriteria criteria, RowBounds rowBounds);

    public Integer queryCount(GiftsCriteria criteria);

    public List<Gifts> queryAllGifts(GiftsCriteria criteria);
}
