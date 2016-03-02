package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.Roles;
import me.anchora.garbage.entry.RolesCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface RolesMapper {
    int countByExample(RolesCriteria example);

    int deleteByExample(RolesCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(Roles record);

    int insertSelective(Roles record);

    List<Roles> selectByExampleWithRowbounds(RolesCriteria example, RowBounds rowBounds);

    List<Roles> selectByExample(RolesCriteria example);

    Roles selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Roles record, @Param("example") RolesCriteria example);

    int updateByExample(@Param("record") Roles record, @Param("example") RolesCriteria example);

    int updateByPrimaryKeySelective(Roles record);

    int updateByPrimaryKey(Roles record);

    void createTable();

    void insertData();
}