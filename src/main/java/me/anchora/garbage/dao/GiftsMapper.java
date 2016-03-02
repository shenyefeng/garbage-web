package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.Gifts;
import me.anchora.garbage.entry.GiftsCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GiftsMapper {
    int countByExample(GiftsCriteria example);

    int deleteByExample(GiftsCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(Gifts record);

    int insertSelective(Gifts record);

    List<Gifts> selectByExampleWithRowbounds(GiftsCriteria example, RowBounds rowBounds);

    List<Gifts> selectByExample(GiftsCriteria example);

    Gifts selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Gifts record, @Param("example") GiftsCriteria example);

    int updateByExample(@Param("record") Gifts record, @Param("example") GiftsCriteria example);

    int updateByPrimaryKeySelective(Gifts record);

    int updateByPrimaryKey(Gifts record);

    void createTable();
}