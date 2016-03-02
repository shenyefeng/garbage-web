/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service;

import java.util.List;

import me.anchora.garbage.entry.GiftGrants;
import me.anchora.garbage.entry.GiftGrantsCriteria;

import org.apache.ibatis.session.RowBounds;

public interface GiftGrantsService {

    public GiftGrants insert(GiftGrants giftGrants);

    public void update(GiftGrants giftGrants);

    public void delete(GiftGrants giftGrants);

    public List<GiftGrants> queryGiftGrantsByPage(GiftGrantsCriteria criteria, RowBounds rowBounds);

    public Integer queryCount(GiftGrantsCriteria criteria);

    public List<GiftGrants> queryGiftGrantsByPageWithUser(GiftGrants giftGrants, RowBounds rowBounds);

    public Integer queryCountWithUser(GiftGrants giftGrants);
}
