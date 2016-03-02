package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.GarbageStations;
import me.anchora.garbage.entry.GarbageStationsCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GarbageStationsMapper {
    int countByExample(GarbageStationsCriteria example);

    int deleteByExample(GarbageStationsCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(GarbageStations record);

    int insertSelective(GarbageStations record);

    List<GarbageStations> selectByExampleWithRowbounds(GarbageStationsCriteria example, RowBounds rowBounds);

    List<GarbageStations> selectByExample(GarbageStationsCriteria example);

    GarbageStations selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") GarbageStations record, @Param("example") GarbageStationsCriteria example);

    int updateByExample(@Param("record") GarbageStations record, @Param("example") GarbageStationsCriteria example);

    int updateByPrimaryKeySelective(GarbageStations record);

    int updateByPrimaryKey(GarbageStations record);

    void createTable();

    void insertData();
}