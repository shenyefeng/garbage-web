package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.MenuDetails;
import me.anchora.garbage.entry.MenuDetailsCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MenuDetailsMapper {
    int countByExample(MenuDetailsCriteria example);

    int deleteByExample(MenuDetailsCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(MenuDetails record);

    int insertSelective(MenuDetails record);

    List<MenuDetails> selectByExampleWithRowbounds(MenuDetailsCriteria example, RowBounds rowBounds);

    List<MenuDetails> selectByExample(MenuDetailsCriteria example);

    MenuDetails selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MenuDetails record, @Param("example") MenuDetailsCriteria example);

    int updateByExample(@Param("record") MenuDetails record, @Param("example") MenuDetailsCriteria example);

    int updateByPrimaryKeySelective(MenuDetails record);

    int updateByPrimaryKey(MenuDetails record);

    void createTable();

    void insertData();
}