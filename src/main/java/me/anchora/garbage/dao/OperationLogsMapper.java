package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.OperationLogs;
import me.anchora.garbage.entry.OperationLogsCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OperationLogsMapper {
    int countByExample(OperationLogsCriteria example);

    int deleteByExample(OperationLogsCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(OperationLogs record);

    int insertSelective(OperationLogs record);

    List<OperationLogs> selectByExampleWithRowbounds(OperationLogsCriteria example, RowBounds rowBounds);

    List<OperationLogs> selectByExample(OperationLogsCriteria example);

    OperationLogs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OperationLogs record, @Param("example") OperationLogsCriteria example);

    int updateByExample(@Param("record") OperationLogs record, @Param("example") OperationLogsCriteria example);

    int updateByPrimaryKeySelective(OperationLogs record);

    int updateByPrimaryKey(OperationLogs record);

    void createTable();
}