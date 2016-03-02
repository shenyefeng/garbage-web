package me.anchora.garbage.dao;

import java.util.List;

import me.anchora.garbage.entry.GiftGrants;
import me.anchora.garbage.entry.GiftGrantsCriteria;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GiftGrantsMapper {
    int countByExample(GiftGrantsCriteria example);

    int deleteByExample(GiftGrantsCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(GiftGrants record);

    int insertSelective(GiftGrants record);

    List<GiftGrants> selectByExampleWithRowbounds(GiftGrantsCriteria example, RowBounds rowBounds);

    List<GiftGrants> selectByExample(GiftGrantsCriteria example);

    GiftGrants selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GiftGrants record, @Param("example") GiftGrantsCriteria example);

    int updateByExample(@Param("record") GiftGrants record, @Param("example") GiftGrantsCriteria example);

    int updateByPrimaryKeySelective(GiftGrants record);

    int updateByPrimaryKey(GiftGrants record);

    void createTable();

    void insertData();

    List<GiftGrants> selectByExampleWithGiftWithRowbounds(GiftGrants giftGrants, RowBounds rowBounds);

    Integer countByExampleWithGift(GiftGrants giftGrants);
}