package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.MenuActions;
import me.anchora.garbage.entry.MenuActionsCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MenuActionsMapper {
    int countByExample(MenuActionsCriteria example);

    int deleteByExample(MenuActionsCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(MenuActions record);

    int insertSelective(MenuActions record);

    List<MenuActions> selectByExampleWithRowbounds(MenuActionsCriteria example, RowBounds rowBounds);

    List<MenuActions> selectByExample(MenuActionsCriteria example);

    MenuActions selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MenuActions record, @Param("example") MenuActionsCriteria example);

    int updateByExample(@Param("record") MenuActions record, @Param("example") MenuActionsCriteria example);

    int updateByPrimaryKeySelective(MenuActions record);

    int updateByPrimaryKey(MenuActions record);

    void createTable();

    void insertData();
}