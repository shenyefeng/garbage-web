/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service.impl;

import java.util.Date;
import java.util.List;

import me.anchora.garbage.dao.GarbagesMapper;
import me.anchora.garbage.dao.QrCodesMapper;
import me.anchora.garbage.entry.Garbages;
import me.anchora.garbage.entry.GarbagesCriteria;
import me.anchora.garbage.entry.QrCodes;
import me.anchora.garbage.entry.QrCodesCriteria;
import me.anchora.garbage.msg.MsgEnum;
import me.anchora.garbage.service.QrCodesService;
import me.anchora.garbage.utils.SystemUtil;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "qrCodesService")
public class QrCodesServiceImpl implements QrCodesService {

    @Autowired
    private QrCodesMapper qrCodesMapper;

    @Autowired
    private GarbagesMapper garbagesMapper;

    @Override
    public QrCodes insert(QrCodes qrCodes) {
        check(qrCodes);
        qrCodes.setCreatedAt(new Date());
        qrCodes.setCreatedBy(qrCodes.getAdminId());
        qrCodesMapper.insert(qrCodes);
        return qrCodes;
    }

    private void check(QrCodes qrCodes) {
        String qrcodeStart = qrCodes.getQrCodeStart();
        String qrcodeEnd = qrCodes.getQrCodeEnd();

        if(qrcodeStart == null || qrcodeStart.length() == 0) {
            SystemUtil.throwException(MsgEnum.QRCODE_00005.getCode());
        }
        
        if(qrcodeEnd == null || qrcodeEnd.length() == 0) {
            qrcodeEnd = qrcodeStart;
        }
        
        if (qrcodeStart.compareTo(qrcodeEnd) > 0) {
            SystemUtil.throwException(MsgEnum.QRCODE_00003.getCode());
        }

        if (qrcodeStart.length() != qrcodeEnd.length()) {
            SystemUtil.throwException(MsgEnum.QRCODE_00004.getCode());
        }

        QrCodesCriteria qrCodesCriteria = new QrCodesCriteria();
        qrCodesCriteria.createCriteria().andQrCodeStartLessThanOrEqualTo(qrcodeStart).andQrCodeEndGreaterThanOrEqualTo(qrcodeStart);
        List<QrCodes> qrCodesList = qrCodesMapper.selectByExample(qrCodesCriteria);
        if (qrCodesList != null && qrCodesList.size() > 0) {
            if(qrCodesList.size() > 1 || !qrCodesList.get(0).getId().equals(qrCodes.getId())) {
                SystemUtil.throwException(MsgEnum.QRCODE_00002.getCode(), new String[] { qrcodeStart });
            }
        }

        qrCodesCriteria = new QrCodesCriteria();
        qrCodesCriteria.createCriteria().andQrCodeStartLessThanOrEqualTo(qrcodeEnd).andQrCodeEndGreaterThanOrEqualTo(qrcodeEnd);
        qrCodesList = qrCodesMapper.selectByExample(qrCodesCriteria);
        if (qrCodesList != null && qrCodesList.size() > 0) {
            if(qrCodesList.size() > 1 || !qrCodesList.get(0).getId().equals(qrCodes.getId())) {
                SystemUtil.throwException(MsgEnum.QRCODE_00002.getCode(), new String[] { qrcodeStart });
            }
        }
    }

    @Override
    public void update(QrCodes qrCodes) {
        check(qrCodes);
        GarbagesCriteria garbagesCriteria = new GarbagesCriteria();
        garbagesCriteria.createCriteria().andQrCodeIdEqualTo(qrCodes.getId());
        List<Garbages> garbagesList = garbagesMapper.selectByExample(garbagesCriteria);
        for(Garbages garbages : garbagesList) {
            if(garbages.getQrCode().compareTo(qrCodes.getQrCodeStart()) < 0
                    || garbages.getQrCode().compareTo(qrCodes.getQrCodeEnd()) > 0) {
                SystemUtil.throwException(MsgEnum.QRCODE_00007.getCode());
            }
        }
        
        qrCodes.setUpdatedAt(new Date());
        qrCodes.setUpdatedBy(qrCodes.getAdminId());
        qrCodesMapper.updateByPrimaryKeySelective(qrCodes);
    }

    @Override
    public void delete(QrCodes qrCodes) {
        GarbagesCriteria garbagesCriteria = new GarbagesCriteria();
        garbagesCriteria.createCriteria().andQrCodeIdEqualTo(qrCodes.getId());
        int garbages = garbagesMapper.countByExample(garbagesCriteria);
        if(garbages > 0) {
            SystemUtil.throwException(MsgEnum.QRCODE_00006.getCode());
        }
        qrCodesMapper.deleteByPrimaryKey(qrCodes.getId());
    }

    @Override
    public List<QrCodes> queryQrCodesByPage(QrCodesCriteria criteria, RowBounds rowBounds) {
        List<QrCodes> result = qrCodesMapper.selectByExampleWithRowbounds(criteria, rowBounds);
        return result;
    }

    @Override
    public Integer queryCount(QrCodesCriteria criteria) {
        Integer result = qrCodesMapper.countByExample(criteria);
        return result;
    }

    @Override
    public List<QrCodes> queryQrCodesByPageWithUser(QrCodes qrCodes, RowBounds rowBounds) {
        List<QrCodes> result = qrCodesMapper.selectByExampleWithUserWithRowbounds(qrCodes, rowBounds);
        return result;
    }

    @Override
    public Integer queryCountWithUser(QrCodes qrCodes) {
        Integer result = qrCodesMapper.countByExampleWithUser(qrCodes);
        return result;
    }
}
