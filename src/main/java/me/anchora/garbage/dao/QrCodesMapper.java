package me.anchora.garbage.dao;

import java.util.List;

import me.anchora.garbage.entry.QrCodes;
import me.anchora.garbage.entry.QrCodesCriteria;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QrCodesMapper {
    int countByExample(QrCodesCriteria example);

    int deleteByExample(QrCodesCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(QrCodes record);

    int insertSelective(QrCodes record);

    List<QrCodes> selectByExampleWithRowbounds(QrCodesCriteria example, RowBounds rowBounds);

    List<QrCodes> selectByExample(QrCodesCriteria example);

    QrCodes selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") QrCodes record, @Param("example") QrCodesCriteria example);

    int updateByExample(@Param("record") QrCodes record, @Param("example") QrCodesCriteria example);

    int updateByPrimaryKeySelective(QrCodes record);

    int updateByPrimaryKey(QrCodes record);

    List<QrCodes> selectByExampleWithUserWithRowbounds(QrCodes record, RowBounds rowBounds);

    Integer countByExampleWithUser(QrCodes qrCodes);

    void createTable();

    void insertData();
}