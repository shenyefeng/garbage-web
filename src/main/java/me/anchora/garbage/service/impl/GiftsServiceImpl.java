/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service.impl;

import java.util.Date;
import java.util.List;

import me.anchora.garbage.base.BaseService;
import me.anchora.garbage.dao.GiftGrantsMapper;
import me.anchora.garbage.dao.GiftsMapper;
import me.anchora.garbage.entry.GiftGrantsCriteria;
import me.anchora.garbage.entry.Gifts;
import me.anchora.garbage.entry.GiftsCriteria;
import me.anchora.garbage.msg.MsgEnum;
import me.anchora.garbage.service.GiftsService;
import me.anchora.garbage.utils.SystemUtil;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "giftsService")
public class GiftsServiceImpl extends BaseService implements GiftsService {

    @Autowired
    private GiftsMapper giftsMapper;

    @Autowired
    private GiftGrantsMapper giftGrantsMapper;

    @Override
    public void insert(Gifts gifts) {
        gifts.setCreatedAt(new Date());
        gifts.setCreatedBy(gifts.getAdminId());
        
        if(gifts.getGiftGrantNum() == null) {
            gifts.setGiftGrantNum(0F);
        }
        giftsMapper.insert(gifts);
        cacheService.doReloadGifts();
    }

    @Override
    public void update(Gifts gifts) {
        gifts.setUpdatedAt(new Date());
        gifts.setUpdatedBy(gifts.getAdminId());
        
        if(gifts.getGiftGrantNum() == null) {
            gifts.setGiftGrantNum(0F);
        }
        
        if(gifts.getGiftGrantNum() > gifts.getGiftNum()) {
            SystemUtil.throwException(MsgEnum.GIFT_00001.getCode());
        }
        
        giftsMapper.updateByPrimaryKeySelective(gifts);
        cacheService.doReloadGifts();
    }

    @Override
    public void delete(Gifts gifts) {
        GiftGrantsCriteria giftGrantsCriteria = new GiftGrantsCriteria();
        giftGrantsCriteria.createCriteria().andGiftIdEqualTo(gifts.getId());
        int giftGrants = giftGrantsMapper.countByExample(giftGrantsCriteria);
        if(giftGrants > 0) {
            SystemUtil.throwException(MsgEnum.GIFT_00002.getCode());
        }
        
        giftsMapper.deleteByPrimaryKey(gifts.getId());
        cacheService.doReloadGifts();
   }

    @Override
    public List<Gifts> queryGiftsByPage(GiftsCriteria criteria, RowBounds rowBounds) {
        List<Gifts> result = giftsMapper.selectByExampleWithRowbounds(criteria, rowBounds);
        return result;
    }

    @Override
    public Integer queryCount(GiftsCriteria criteria) {
        Integer result = giftsMapper.countByExample(criteria);
        return result;
    }

    @Override
    public List<Gifts> queryAllGifts(GiftsCriteria criteria) {
        List<Gifts> result = giftsMapper.selectByExample(criteria);
        return result;
    }
}
