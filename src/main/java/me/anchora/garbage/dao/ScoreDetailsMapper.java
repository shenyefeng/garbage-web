package me.anchora.garbage.dao;

import java.util.List;
import me.anchora.garbage.entry.ScoreDetails;
import me.anchora.garbage.entry.ScoreDetailsCriteria;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScoreDetailsMapper {
    int countByExample(ScoreDetailsCriteria example);

    int deleteByExample(ScoreDetailsCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(ScoreDetails record);

    int insertSelective(ScoreDetails record);

    List<ScoreDetails> selectByExampleWithRowbounds(ScoreDetailsCriteria example, RowBounds rowBounds);

    List<ScoreDetails> selectByExample(ScoreDetailsCriteria example);

    ScoreDetails selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ScoreDetails record, @Param("example") ScoreDetailsCriteria example);

    int updateByExample(@Param("record") ScoreDetails record, @Param("example") ScoreDetailsCriteria example);

    int updateByPrimaryKeySelective(ScoreDetails record);

    int updateByPrimaryKey(ScoreDetails record);

    void createTable();
}