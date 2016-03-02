package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.SystemConfig;
import me.anchora.garbage.entry.SystemConfigCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SystemConfigMapper {
    int countByExample(SystemConfigCriteria example);

    int deleteByExample(SystemConfigCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(SystemConfig record);

    int insertSelective(SystemConfig record);

    List<SystemConfig> selectByExampleWithRowbounds(SystemConfigCriteria example, RowBounds rowBounds);

    List<SystemConfig> selectByExample(SystemConfigCriteria example);

    SystemConfig selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SystemConfig record, @Param("example") SystemConfigCriteria example);

    int updateByExample(@Param("record") SystemConfig record, @Param("example") SystemConfigCriteria example);

    int updateByPrimaryKeySelective(SystemConfig record);

    int updateByPrimaryKey(SystemConfig record);

	void createTable();

	void insertData();
}