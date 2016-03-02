/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service.impl;

import java.util.Date;
import java.util.List;

import me.anchora.garbage.dao.GiftGrantsMapper;
import me.anchora.garbage.dao.GiftsMapper;
import me.anchora.garbage.dao.ScoreDetailsMapper;
import me.anchora.garbage.dao.UsersMapper;
import me.anchora.garbage.entry.GiftGrants;
import me.anchora.garbage.entry.GiftGrantsCriteria;
import me.anchora.garbage.entry.Gifts;
import me.anchora.garbage.entry.ScoreDetails;
import me.anchora.garbage.entry.ScoreDetailsCriteria;
import me.anchora.garbage.entry.Users;
import me.anchora.garbage.msg.MsgEnum;
import me.anchora.garbage.service.GiftGrantsService;
import me.anchora.garbage.utils.SystemUtil;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "giftGrantsService")
public class GiftGrantsServiceImpl implements GiftGrantsService {

    @Autowired
    private GiftGrantsMapper giftGrantsMapper;

    @Autowired
    private GiftsMapper giftsMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private ScoreDetailsMapper scoreDetailsMapper;

    @Override
    public GiftGrants insert(GiftGrants giftGrants) {
        giftGrants.setCreatedAt(new Date());
        giftGrants.setCreatedBy(giftGrants.getAdminId());

        Gifts gifts = giftsMapper.selectByPrimaryKey(giftGrants.getGiftId());
        gifts.setGiftGrantNum(gifts.getGiftGrantNum() + giftGrants.getGiftNum());
        gifts.setUpdatedAt(new Date());
        gifts.setUpdatedBy(giftGrants.getAdminId());
        if(gifts.getGiftGrantNum() > gifts.getGiftNum()) {
            SystemUtil.throwException(MsgEnum.GIFT_GRANT_00001.getCode());
        }
        
        Users users = usersMapper.selectByPrimaryKey(giftGrants.getUserId());
        if(users.getScore() < gifts.getScore() * giftGrants.getGiftNum()) {
            SystemUtil.throwException(MsgEnum.GIFT_GRANT_00002.getCode());
        }
        
        ScoreDetails scoreDetails = new ScoreDetails();
        scoreDetails.setUserId(users.getId());
        scoreDetails.setGiftId(gifts.getId());
        scoreDetails.setScore(-(gifts.getScore() * giftGrants.getGiftNum()));
        scoreDetails.setCreatedAt(new Date());
        scoreDetails.setCreatedBy(giftGrants.getAdminId());
        scoreDetailsMapper.insert(scoreDetails);
        
        users.setScore(users.getScore() - (gifts.getScore() * giftGrants.getGiftNum()));
        users.setCreatedAt(new Date());
        users.setCreatedBy(giftGrants.getAdminId());
        usersMapper.updateByPrimaryKeySelective(users);
        
        giftsMapper.updateByPrimaryKeySelective(gifts);
        
        giftGrantsMapper.insert(giftGrants);
        return giftGrants;
    }

    @Override
    public void update(GiftGrants giftGrants) {
        giftGrants.setUpdatedAt(new Date());
        giftGrants.setUpdatedBy(giftGrants.getAdminId());
        
        GiftGrants giftGrantsOld = giftGrantsMapper.selectByPrimaryKey(giftGrants.getId());
        
        if(!giftGrantsOld.getGiftId().equals(giftGrants.getGiftId())
                || !giftGrantsOld.getUserId().equals(giftGrants.getUserId())
                || !giftGrantsOld.getGiftNum().equals(giftGrants.getGiftNum())) {
            Gifts giftsOld = giftsMapper.selectByPrimaryKey(giftGrantsOld.getGiftId());
            
            ScoreDetailsCriteria scoreDetailsCriteria = new ScoreDetailsCriteria();
            scoreDetailsCriteria.createCriteria().andGiftIdEqualTo(giftGrantsOld.getGiftId()).andUserIdEqualTo(giftGrantsOld.getUserId()).andScoreEqualTo(-giftsOld.getScore() * giftGrantsOld.getGiftNum());
            scoreDetailsCriteria.setOrderByClause("created_at desc");
            List<ScoreDetails> scoreDetailsList = scoreDetailsMapper.selectByExample(scoreDetailsCriteria);
            if(scoreDetailsList != null && scoreDetailsList.size() > 0) {
                scoreDetailsMapper.deleteByPrimaryKey(scoreDetailsList.get(0).getId());
            } else {
                SystemUtil.throwException(MsgEnum.SYS_ERROR.getCode());
            }
            
            Users users = usersMapper.selectByPrimaryKey(giftGrantsOld.getUserId());
            users.setScore(users.getScore() + (giftsOld.getScore() * giftGrantsOld.getGiftNum()));
            users.setCreatedAt(new Date());
            users.setCreatedBy(giftGrants.getAdminId());
            usersMapper.updateByPrimaryKeySelective(users);

            giftsOld.setGiftGrantNum(giftsOld.getGiftGrantNum() - giftGrantsOld.getGiftNum());
            giftsOld.setUpdatedAt(new Date());
            giftsOld.setUpdatedBy(giftGrants.getAdminId());
            giftsMapper.updateByPrimaryKeySelective(giftsOld);
            
            Gifts gifts = giftsMapper.selectByPrimaryKey(giftGrants.getGiftId());
            gifts.setGiftGrantNum(gifts.getGiftGrantNum() + giftGrants.getGiftNum());
            gifts.setUpdatedAt(new Date());
            gifts.setUpdatedBy(giftGrants.getAdminId());
            if(gifts.getGiftGrantNum() > gifts.getGiftNum()) {
                SystemUtil.throwException(MsgEnum.GIFT_GRANT_00001.getCode());
            }
            
            users = usersMapper.selectByPrimaryKey(giftGrants.getUserId());
            if(users.getScore() < gifts.getScore() * giftGrants.getGiftNum()) {
                SystemUtil.throwException(MsgEnum.GIFT_GRANT_00002.getCode());
            }
            
            ScoreDetails scoreDetails = new ScoreDetails();
            scoreDetails.setUserId(users.getId());
            scoreDetails.setGiftId(gifts.getId());
            scoreDetails.setScore(-(gifts.getScore() * giftGrants.getGiftNum()));
            scoreDetails.setCreatedAt(new Date());
            scoreDetails.setCreatedBy(giftGrants.getAdminId());
            scoreDetailsMapper.insert(scoreDetails);
            
            users.setScore(users.getScore() - (gifts.getScore() * giftGrants.getGiftNum()));
            users.setCreatedAt(new Date());
            users.setCreatedBy(giftGrants.getAdminId());
            usersMapper.updateByPrimaryKeySelective(users);
            
            giftsMapper.updateByPrimaryKeySelective(gifts);
        }
        
        giftGrantsMapper.updateByPrimaryKeySelective(giftGrants);
    }

    @Override
    public void delete(GiftGrants giftGrants) {
        GiftGrants giftGrantsOld = giftGrantsMapper.selectByPrimaryKey(giftGrants.getId());
        Gifts giftsOld = giftsMapper.selectByPrimaryKey(giftGrantsOld.getGiftId());
        
        ScoreDetailsCriteria scoreDetailsCriteria = new ScoreDetailsCriteria();
        scoreDetailsCriteria.createCriteria().andGiftIdEqualTo(giftGrantsOld.getGiftId()).andUserIdEqualTo(giftGrantsOld.getUserId()).andScoreEqualTo(-giftsOld.getScore() * giftGrantsOld.getGiftNum());
        scoreDetailsCriteria.setOrderByClause("created_at desc");
        List<ScoreDetails> scoreDetailsList = scoreDetailsMapper.selectByExample(scoreDetailsCriteria);
        if(scoreDetailsList != null && scoreDetailsList.size() > 0) {
            scoreDetailsMapper.deleteByPrimaryKey(scoreDetailsList.get(0).getId());
        } else {
            SystemUtil.throwException(MsgEnum.SYS_ERROR.getCode());
        }
        
        Users users = usersMapper.selectByPrimaryKey(giftGrantsOld.getUserId());
        users.setScore(users.getScore() + (giftsOld.getScore() * giftGrantsOld.getGiftNum()));
        users.setCreatedAt(new Date());
        users.setCreatedBy(giftGrants.getAdminId());
        usersMapper.updateByPrimaryKeySelective(users);

        giftsOld.setGiftGrantNum(giftsOld.getGiftGrantNum() - giftGrantsOld.getGiftNum());
        giftsOld.setUpdatedAt(new Date());
        giftsOld.setUpdatedBy(giftGrants.getAdminId());
        giftsMapper.updateByPrimaryKeySelective(giftsOld);

        giftGrantsMapper.deleteByPrimaryKey(giftGrants.getId());
    }

    @Override
    public List<GiftGrants> queryGiftGrantsByPage(GiftGrantsCriteria criteria, RowBounds rowBounds) {
        List<GiftGrants> result = giftGrantsMapper.selectByExampleWithRowbounds(criteria, rowBounds);
        return result;
    }

    @Override
    public Integer queryCount(GiftGrantsCriteria criteria) {
        Integer result = giftGrantsMapper.countByExample(criteria);
        return result;
    }

    @Override
    public List<GiftGrants> queryGiftGrantsByPageWithUser(GiftGrants giftGrants, RowBounds rowBounds) {
        List<GiftGrants> result = giftGrantsMapper.selectByExampleWithGiftWithRowbounds(giftGrants, rowBounds);
        return result;
    }

    @Override
    public Integer queryCountWithUser(GiftGrants giftGrants) {
        Integer result = giftGrantsMapper.countByExampleWithGift(giftGrants);
        return result;
    }
}
