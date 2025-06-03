package business;

import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class Users {
    private final UUID usersId;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private Score scoreClient;
    private Score scoreSeller;

    public Users(UUID id, String firstName, String lastName, String email, String password, UUID scoreClientId, UUID scoreSellerId) {
        this.usersId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.setPasswordHash(password);
        this.scoreClient = new Score(scoreClientId, 0, 0);
        this.scoreSeller = new Score(scoreSellerId, 0, 0);
    }

    public UUID getUsersId() { return usersId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] passwordHashBytes = md.digest(password.getBytes());
            this.passwordHash = HexFormat.of().formatHex(passwordHashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public UUID getScoreClientId() { return this.scoreClient.getScoreId(); }
    public UUID getScoreSellerId() { return this.scoreSeller.getScoreId(); }

    public float getTotalScoreClient() {
        return this.scoreClient.getScore();
    }

    public int getNumberScoreClient() {
        return this.scoreClient.getScoreNumber();
    }

    public float getAverageScoreClient() {
        if (this.scoreClient.getScoreNumber() == 0) {
            return 0f;
        }
        else {
            float result = this.scoreClient.getScore() / this.scoreClient.getScoreNumber();
            return result;
        }
    }

    public void setScoreClient(float score){
        if (score < 0f) {
            score = 0f;
        }
        if (score > 5f) {
            score = 5f;
        }
        this.scoreClient.setScore(score);
    }

    public void addScoreClient(float score) {
        if (score < 0f) {
            score = 0f;
        }
        if (score > 5f) {
            score = 5f;
        }
        this.scoreClient.setScore(this.scoreClient.getScore() + score);
        this.scoreClient.setScoreNumber(this.scoreClient.getScoreNumber() + 1);
    }

    public float getTotalScoreSeller() {
        return this.scoreSeller.getScore();
    }

    public int getNumberScoreSeller() {
        return this.scoreSeller.getScoreNumber();
    }

    public float getAverageScoreSeller() {
        if(this.scoreSeller.getScoreNumber() == 0){
            return 0f;
        }
        else {
            float result = this.scoreSeller.getScore()/this.scoreSeller.getScoreNumber();
            return result;
        }
    }

    public void setScoreSeller(float score) {
        if (score < 0f) {
            score = 0f;
        }
        if (score > 5f) {
            score = 5f;
        }
        this.scoreSeller.setScore(score);
    }

    public void addScoreSeller(float score) {
        if (score < 0f) {
            score = 0f;
        }
        if (score > 5f) {
            score = 5f;
        }
        this.scoreSeller.setScore(this.scoreSeller.getScore() + score);
        this.scoreSeller.setScoreNumber(this.scoreSeller.getScoreNumber() + 1);
    }
}