package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.RoleUsers;
import me.anchora.garbage.entry.RoleUsersCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface RoleUsersMapper {
    int countByExample(RoleUsersCriteria example);

    int deleteByExample(RoleUsersCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(RoleUsers record);

    int insertSelective(RoleUsers record);

    List<RoleUsers> selectByExampleWithRowbounds(RoleUsersCriteria example, RowBounds rowBounds);

    List<RoleUsers> selectByExample(RoleUsersCriteria example);

    RoleUsers selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoleUsers record, @Param("example") RoleUsersCriteria example);

    int updateByExample(@Param("record") RoleUsers record, @Param("example") RoleUsersCriteria example);

    int updateByPrimaryKeySelective(RoleUsers record);

    int updateByPrimaryKey(RoleUsers record);

    void createTable();

    void insertData();
}