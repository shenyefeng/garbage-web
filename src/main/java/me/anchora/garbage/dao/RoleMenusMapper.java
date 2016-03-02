package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.RoleMenus;
import me.anchora.garbage.entry.RoleMenusCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface RoleMenusMapper {
    int countByExample(RoleMenusCriteria example);

    int deleteByExample(RoleMenusCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleMenus record);

    int insertSelective(RoleMenus record);

    List<RoleMenus> selectByExampleWithRowbounds(RoleMenusCriteria example, RowBounds rowBounds);

    List<RoleMenus> selectByExample(RoleMenusCriteria example);

    RoleMenus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleMenus record, @Param("example") RoleMenusCriteria example);

    int updateByExample(@Param("record") RoleMenus record, @Param("example") RoleMenusCriteria example);

    int updateByPrimaryKeySelective(RoleMenus record);

    int updateByPrimaryKey(RoleMenus record);

    void createTable();

    void insertData();
}