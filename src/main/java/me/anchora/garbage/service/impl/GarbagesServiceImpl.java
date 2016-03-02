/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service.impl;

import static me.anchora.garbage.Constants.CHART_DAYS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.anchora.garbage.base.BaseService;
import me.anchora.garbage.dao.GarbagesMapper;
import me.anchora.garbage.dao.QrCodesMapper;
import me.anchora.garbage.dao.ScoreDetailsMapper;
import me.anchora.garbage.dao.UsersMapper;
import me.anchora.garbage.entry.GarbageTypes;
import me.anchora.garbage.entry.Garbages;
import me.anchora.garbage.entry.GarbagesCriteria;
import me.anchora.garbage.entry.QrCodes;
import me.anchora.garbage.entry.QrCodesCriteria;
import me.anchora.garbage.entry.ScoreDetails;
import me.anchora.garbage.entry.ScoreDetailsCriteria;
import me.anchora.garbage.entry.Users;
import me.anchora.garbage.entry.base.CommonVo;
import me.anchora.garbage.entry.base.ReportVo;
import me.anchora.garbage.msg.MsgEnum;
import me.anchora.garbage.service.GarbagesService;
import me.anchora.garbage.utils.CacheUtil;
import me.anchora.garbage.utils.DateUtil;
import me.anchora.garbage.utils.ListUtil;
import me.anchora.garbage.utils.SystemUtil;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "garbagesService")
public class GarbagesServiceImpl extends BaseService implements GarbagesService {
    private static Logger logger = Logger.getLogger(GarbagesServiceImpl.class);

    @Autowired
    private GarbagesMapper garbagesMapper;

    @Autowired
    private ScoreDetailsMapper scoreDetailsMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private QrCodesMapper qrCodesMapper;

    @Override
    public List<Garbages> queryGarbagesByPage(GarbagesCriteria criteria, RowBounds rowBounds) {
        List<Garbages> result = garbagesMapper.selectByExampleWithRowbounds(criteria, rowBounds);
        return result;
    }

    @Override
    public Integer queryCount(GarbagesCriteria criteria) {
        Integer result = garbagesMapper.countByExample(criteria);
        return result;
    }

    @Override
    public void insert(Garbages garbages) {
        garbages.setCreatedAt(new Date());
        garbages.setCreatedBy(garbages.getAdminId());
        
        String qrcode = garbages.getQrCode();
        QrCodesCriteria qrCodesCriteria = new QrCodesCriteria();
        qrCodesCriteria.createCriteria().andQrCodeStartLessThanOrEqualTo(qrcode).andQrCodeEndGreaterThanOrEqualTo(qrcode);
        List<QrCodes> qrCodesList = qrCodesMapper.selectByExample(qrCodesCriteria);
        Long userId = null;
        if (qrCodesList != null && qrCodesList.size() > 0) {
            if(qrCodesList.size() == 1) {
                garbages.setQrCodeId(qrCodesList.get(0).getId());
                userId = qrCodesList.get(0).getUserId();
            } else {
                for(QrCodes qrCodesTmp : qrCodesList) {
                    if(qrCodesTmp.getQrCodeStart().length() == qrcode.length()) {
                        garbages.setQrCodeId(qrCodesTmp.getId());
                        userId = qrCodesTmp.getUserId();
                        break;
                    }
                }
            }
            
        }
        
        if(garbages.getQrCodeId() == null) {
            SystemUtil.throwException(MsgEnum.QRCODE_00001.getCode(), new String[]{qrcode});
        }
        
        garbages.setUserId(garbages.getAdminId());
        
        @SuppressWarnings("unchecked")
        List<GarbageTypes> garbageTypesList = (List<GarbageTypes>)CacheUtil.getLists(cacheService, MsgEnum.CACHE_GARBAGE_TYPES.getCode());
        for(GarbageTypes garbageTypes : garbageTypesList) {
            if(garbages.getGarbageTypeId().equals(garbageTypes.getId())) {
                garbages.setScore(garbageTypes.getScoreWeight() * garbages.getWeight());
                break;
            }
        }
        
        garbagesMapper.insert(garbages);
        
        if(userId != null) {
            ScoreDetails scoreDetails = new ScoreDetails();
            scoreDetails.setScore(garbages.getScore());
            
            GarbagesCriteria criteria = new GarbagesCriteria();
            criteria.createCriteria().andQrCodeEqualTo(garbages.getQrCode());
            List<Garbages> garbagesList = garbagesMapper.selectByExample(criteria);
            if(garbagesList != null && garbagesList.size() > 0) {
                scoreDetails.setGarbageId(garbagesList.get(0).getId());
            }
            
            scoreDetails.setUserId(userId);
            scoreDetails.setCreatedAt(new Date());
            scoreDetails.setCreatedBy(garbages.getAdminId());
            scoreDetailsMapper.insert(scoreDetails);
            
            Users users = usersMapper.selectByPrimaryKey(userId);
            users.setScore((users.getScore() == null ? 0 : users.getScore()) + garbages.getScore());
            users.setUpdatedAt(new Date());
            users.setUpdatedBy(garbages.getAdminId());
            usersMapper.updateByPrimaryKeySelective(users);
        }
    }

    @Override
    public void update(Garbages garbages) {
        garbages.setUpdatedAt(new Date());
        garbages.setUpdatedBy(garbages.getAdminId());
        Garbages garbagesOld = garbagesMapper.selectByPrimaryKey(garbages.getId());
        if(!garbagesOld.getWeight().equals(garbages.getWeight()) || !garbagesOld.getGarbageTypeId().equals(garbages.getGarbageTypeId())) {
            @SuppressWarnings("unchecked")
            List<GarbageTypes> garbageTypesList = (List<GarbageTypes>)CacheUtil.getLists(cacheService, MsgEnum.CACHE_GARBAGE_TYPES.getCode());
            for(GarbageTypes garbageTypes : garbageTypesList) {
                if(garbages.getGarbageTypeId().equals(garbageTypes.getId())) {
                    garbages.setScore(garbageTypes.getScoreWeight() * garbages.getWeight());
                    break;
                }
            }

            String qrcode = garbages.getQrCode();
            QrCodesCriteria qrCodesCriteria = new QrCodesCriteria();
            qrCodesCriteria.createCriteria().andQrCodeStartLessThanOrEqualTo(qrcode).andQrCodeEndGreaterThanOrEqualTo(qrcode);
            List<QrCodes> qrCodesList = qrCodesMapper.selectByExample(qrCodesCriteria);
            Long userId = null;
            if (qrCodesList != null && qrCodesList.size() > 0) {
                if(qrCodesList.size() == 1) {
                    garbages.setQrCodeId(qrCodesList.get(0).getId());
                    userId = qrCodesList.get(0).getUserId();
                } else {
                    for(QrCodes qrCodesTmp : qrCodesList) {
                        if(qrCodesTmp.getQrCodeStart().length() == qrcode.length()) {
                            garbages.setQrCodeId(qrCodesTmp.getId());
                            userId = qrCodesTmp.getUserId();
                            break;
                        }
                    }
                }
            } else {
                SystemUtil.throwException(MsgEnum.QRCODE_00001.getCode(), new String[]{qrcode});
            }
            
            if(userId != null) {
                Users users = usersMapper.selectByPrimaryKey(userId);
                users.setScore(users.getScore() + garbages.getScore() - garbagesOld.getScore());
                users.setUpdatedAt(new Date());
                users.setUpdatedBy(garbages.getAdminId());
                usersMapper.updateByPrimaryKeySelective(users);
                
                ScoreDetailsCriteria scoreDetailsCriteria = new ScoreDetailsCriteria();
                scoreDetailsCriteria.createCriteria().andGarbageIdEqualTo(garbages.getId());
                List<ScoreDetails> scoreDetailsList = scoreDetailsMapper.selectByExample(scoreDetailsCriteria);
                ScoreDetails scoreDetails;
                if(scoreDetailsList != null && scoreDetailsList.size() > 0) {
                    scoreDetails = scoreDetailsList.get(0);
                    scoreDetails.setUserId(userId);
                    scoreDetails.setScore(scoreDetails.getScore() + garbages.getScore() - garbagesOld.getScore());
                    scoreDetails.setUpdatedAt(new Date());
                    scoreDetails.setUpdatedBy(garbages.getAdminId());
                    scoreDetailsMapper.updateByPrimaryKeySelective(scoreDetails);
                } else {
                    SystemUtil.throwException(MsgEnum.SYS_ERROR.getCode());
                }
            }

        }
        garbagesMapper.updateByPrimaryKeySelective(garbages);
    }

    @Override
    public void delete(Garbages garbages) {
        Garbages garbagesOld = garbagesMapper.selectByPrimaryKey(garbages.getId());
        String qrcode = garbagesOld.getQrCode();
        QrCodesCriteria qrCodesCriteria = new QrCodesCriteria();
        qrCodesCriteria.createCriteria().andQrCodeStartLessThanOrEqualTo(qrcode).andQrCodeEndGreaterThanOrEqualTo(qrcode);
        List<QrCodes> qrCodesList = qrCodesMapper.selectByExample(qrCodesCriteria);
        Long userId = null;
        if (qrCodesList != null && qrCodesList.size() > 0) {
            if(qrCodesList.size() == 1) {
                garbagesOld.setQrCodeId(qrCodesList.get(0).getId());
                userId = qrCodesList.get(0).getUserId();
            } else {
                for(QrCodes qrCodesTmp : qrCodesList) {
                    if(qrCodesTmp.getQrCodeStart().length() == qrcode.length()) {
                        garbagesOld.setQrCodeId(qrCodesTmp.getId());
                        userId = qrCodesTmp.getUserId();
                        break;
                    }
                }
            }
        } else {
            SystemUtil.throwException(MsgEnum.QRCODE_00001.getCode(), new String[]{qrcode});
        }
        
        if(userId != null) {
            Users users = usersMapper.selectByPrimaryKey(userId);
            users.setScore(users.getScore() - garbagesOld.getScore());
            users.setUpdatedAt(new Date());
            users.setUpdatedBy(garbages.getAdminId());
            usersMapper.updateByPrimaryKeySelective(users);
            
            ScoreDetailsCriteria scoreDetailsCriteria = new ScoreDetailsCriteria();
            scoreDetailsCriteria.createCriteria().andGarbageIdEqualTo(garbages.getId());
            scoreDetailsMapper.deleteByExample(scoreDetailsCriteria);
            
        }
        garbagesMapper.deleteByPrimaryKey(garbages.getId());
    }

    @Override
    public List<Map<String, Object>> garbagesChart(CommonVo commonVo, Locale locale) {
        String days = commonVo.getDays();
        if (days == null || "".equals(days)) {
            logger.info("Parameter 'days' has not been setted. Default value(" + CHART_DAYS + ") has been setted.");
            days = CHART_DAYS;
        }

        List<Date> dayList = new ArrayList<Date>();
        Date date = DateUtil.endOfDay(new Date());
        dayList.add(date);
        for (int i = 1; i < Integer.parseInt(days); i++) {
            date = DateUtil.beforeHours(date, 24L);
            dayList.add(date);
        }

        @SuppressWarnings("unchecked")
        List<Date> severalDate = (List<Date>) ListUtil.getSeveralData(dayList, Long.valueOf(commonVo.getPointNum()));
        Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
        Map<String, Object> lastMap;
        List<Map<String, Object>> list;
        Integer count;
        Double weight;
        String type;
        GarbagesCriteria criteria;

        for (Date dateTmp : severalDate) {
            criteria = new GarbagesCriteria();
            criteria.createCriteria().andCreatedAtLessThanOrEqualTo(dateTmp);
            type = super.getMsg(MsgEnum.GARBAGE_ALL.getCode(), locale);

            if (map.containsKey(type)) {
                list = map.get(type);
            } else {
                list = new ArrayList<Map<String, Object>>();
                map.put(type, list);
            }
            lastMap = new HashMap<String, Object>();
            count = garbagesMapper.countByExample(criteria);
            weight = garbagesMapper.sumWeightByExample(criteria);
            lastMap.put("time", dateTmp);
            lastMap.put("count", count);
            lastMap.put("weight", weight);
            list.add(lastMap);
        }

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapTmp;
        for (String userFlag : map.keySet()) {
            mapTmp = new HashMap<String, Object>();
            mapTmp.put("type", userFlag);
            mapTmp.put("datas", map.get(userFlag));
            result.add(mapTmp);
        }
        return result;
    }

    @Override
    public List<Garbages> queryGarbagesByPageWithOther(Garbages garbages, RowBounds rowBounds) {
        List<Garbages> result = garbagesMapper.selectByExampleWithOtherWithRowbounds(garbages, rowBounds);
        return result;
    }

    @Override
    public Integer queryCountWithOther(Garbages garbages) {
        Integer result = garbagesMapper.countByExampleWithOther(garbages);
        return result;
    }

    @Override
    public List<Garbages> queryAllGarbages(GarbagesCriteria criteria) {
        List<Garbages> result = garbagesMapper.selectByExample(criteria);
        return result;
    }

    @Override
    public List<ReportVo> garbageTypeReport() {
        List<ReportVo> result = garbagesMapper.garbageTypeReport();
        return result;
    }

    @Override
    public List<ReportVo> userGarbageTypeReport(ReportVo reportVo, RowBounds rowBounds) {
        List<ReportVo> result = garbagesMapper.garbageTypeReportWithRowbounds(reportVo, rowBounds);
        return result;
    }

    @Override
    public Integer userGarbageTypeReportCount(ReportVo vo) {
        Integer result = garbagesMapper.userGarbageTypeReportCount(vo);
        return result;
    }
}
