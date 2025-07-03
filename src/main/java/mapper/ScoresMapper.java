package mapper;

import business.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jose4j.http.Response;

import java.util.List;
import java.util.UUID;

@Mapper
public interface ScoresMapper {
    public Score getScoreById(@Param("id") UUID id);

    public List<Score> getAllScores();

    public boolean updateScore(@Param("id") UUID id, @Param("score") float score, @Param("number") int number);

    public void createScore(@Param("id") UUID id, @Param("score") float score);
}
