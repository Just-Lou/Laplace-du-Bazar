package business;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void getScoreId() {
        UUID id = UUID.randomUUID();
        Score score = new Score(id, 4.5f, 1);
        assertEquals(id, score.getScoreId());
    }

    @Test
    void getScore() {
        UUID id = UUID.randomUUID();
        Score score = new Score(id, 4.5f, 1);
        assertEquals(4.5f, score.getScore());
    }

    @Test
    void setScore() {
        UUID id = UUID.randomUUID();
        Score score = new Score(id, 0, 0);
        score.setScore(3f);
        assertEquals(3f, score.getScore());
    }

    @Test
    void getScoreNumber() {
        UUID id = UUID.randomUUID();
        Score score = new Score(id, 4.5f, 1);
        assertEquals(1, score.getScoreNumber());
    }

    @Test
    void setScoreNumber() {
        UUID id = UUID.randomUUID();
        Score score = new Score(id, 0, 0);
        score.setScoreNumber(3);
        assertEquals(3, score.getScoreNumber());
    }
}