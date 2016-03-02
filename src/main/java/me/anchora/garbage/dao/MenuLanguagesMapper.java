package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.MenuLanguages;
import me.anchora.garbage.entry.MenuLanguagesCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MenuLanguagesMapper {
    int countByExample(MenuLanguagesCriteria example);

    int deleteByExample(MenuLanguagesCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(MenuLanguages record);

    int insertSelective(MenuLanguages record);

    List<MenuLanguages> selectByExampleWithRowbounds(MenuLanguagesCriteria example, RowBounds rowBounds);

    List<MenuLanguages> selectByExample(MenuLanguagesCriteria example);

    MenuLanguages selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MenuLanguages record, @Param("example") MenuLanguagesCriteria example);

    int updateByExample(@Param("record") MenuLanguages record, @Param("example") MenuLanguagesCriteria example);

    int updateByPrimaryKeySelective(MenuLanguages record);

    int updateByPrimaryKey(MenuLanguages record);

    void createTable();

    void insertData();
}