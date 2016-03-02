package me.anchora.garbage.dao;

import java.util.List;

import me.anchora.garbage.entry.Menus;
import me.anchora.garbage.entry.MenusCriteria;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MenusMapper {
    int countByExample(MenusCriteria example);

    int deleteByExample(MenusCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(Menus record);

    int insertSelective(Menus record);

    List<Menus> selectByExampleWithRowbounds(MenusCriteria example, RowBounds rowBounds);

    List<Menus> selectByExample(MenusCriteria example);

    Menus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Menus record, @Param("example") MenusCriteria example);

    int updateByExample(@Param("record") Menus record, @Param("example") MenusCriteria example);

    int updateByPrimaryKeySelective(Menus record);

    int updateByPrimaryKey(Menus record);

	List<Menus> selectWithMany();

    void createTable();

    void insertData();
}