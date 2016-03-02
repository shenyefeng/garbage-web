package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.UserMenus;
import me.anchora.garbage.entry.UserMenusCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserMenusMapper {
    int countByExample(UserMenusCriteria example);

    int deleteByExample(UserMenusCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(UserMenus record);

    int insertSelective(UserMenus record);

    List<UserMenus> selectByExampleWithRowbounds(UserMenusCriteria example, RowBounds rowBounds);

    List<UserMenus> selectByExample(UserMenusCriteria example);

    UserMenus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserMenus record, @Param("example") UserMenusCriteria example);

    int updateByExample(@Param("record") UserMenus record, @Param("example") UserMenusCriteria example);

    int updateByPrimaryKeySelective(UserMenus record);

    int updateByPrimaryKey(UserMenus record);

    void createTable();
}