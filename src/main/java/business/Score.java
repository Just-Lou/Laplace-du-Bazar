package business;

import java.util.UUID;

public class Score {
    private final UUID scoreId;
    private float score;
    private int number;

    public Score(UUID id, float score, int number) {
        this.scoreId = id;
        this.score = score;
        this.number = number;
    }

    public UUID getScoreId() {return this.scoreId;}
    public float getScore() {return this.score;}
    public void setScore(float score) {this.score = score;}
    public int getScoreNumber() {return this.number;}
    public void setScoreNumber(int number) {this.number = number;}
}
