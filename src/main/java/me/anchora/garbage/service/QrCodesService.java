/**
 * www.anchora.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package me.anchora.garbage.service;

import java.util.List;

import me.anchora.garbage.entry.QrCodes;
import me.anchora.garbage.entry.QrCodesCriteria;

import org.apache.ibatis.session.RowBounds;

public interface QrCodesService {

    public QrCodes insert(QrCodes qrCodes);

    public void update(QrCodes qrCodes);

    public void delete(QrCodes qrCodes);

    public List<QrCodes> queryQrCodesByPage(QrCodesCriteria criteria, RowBounds rowBounds);

    public Integer queryCount(QrCodesCriteria criteria);

    public List<QrCodes> queryQrCodesByPageWithUser(QrCodes qrCodes, RowBounds rowBounds);

    public Integer queryCountWithUser(QrCodes qrCodes);
}
