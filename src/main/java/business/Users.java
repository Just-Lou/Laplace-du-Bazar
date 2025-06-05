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
    private UserType userType;

    public Users(UUID id, String firstName, String lastName, String email, String password, String userTypeName, UUID scoreClientId, UUID scoreSellerId) {
        this.usersId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.setPasswordHash(password);
        this.userType = new UserType(userTypeName);
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

    public String getUserType() { return userType.getTypeName(); }

    public UUID getScoreClientId() { return this.scoreClient.getScoreId(); }
    public UUID getScoreSellerId() { return this.scoreSeller.getScoreId(); }

    public float getScoreClient() {
        return this.scoreClient.getScore();
    }

    public int getNumberScoreClient() {
        return this.scoreClient.getScoreNumber();
    }

    // Écrase scoreClient présent pour remplacer par score en paramètre
    public void setScoreClient(float score){
        if (score < 0f) {
            score = 0f;
        }
        if (score > 5f) {
            score = 5f;
        }
        this.scoreClient.setScore(score);
    }

    public void setNumberScoreClient(int number){
        if (number < 0) {
            number = 0;
        }
        this.scoreClient.setScoreNumber(number);
    }

    // Ajoute un scoreClient et recalcule son score moyen
    public void addScoreClient(float score) {
        if (score < 0f) {
            score = 0f;
        }
        if (score > 5f) {
            score = 5f;
        }
        float totalScores = this.scoreClient.getScore() + score;
        int totalNumbers = this.scoreClient.getScoreNumber() + 1;
        this.scoreClient.setScore(totalScores/totalNumbers);
        this.scoreClient.setScoreNumber(totalNumbers);
    }

    public float getScoreSeller() {
        return this.scoreSeller.getScore();
    }

    public int getNumberScoreSeller() {
        return this.scoreSeller.getScoreNumber();
    }

    // Écrase scoreSeller présent pour remplacer par score en paramètre
    public void setScoreSeller(float score) {
        if (score < 0f) {
            score = 0f;
        }
        if (score > 5f) {
            score = 5f;
        }
        this.scoreSeller.setScore(score);
    }

    public void setNumberScoreSeller(int number){
        if (number < 0) {
            number = 0;
        }
        this.scoreSeller.setScoreNumber(number);
    }

    // Ajoute un scoreSeller et recalcule son score moyen
    public void addScoreSeller(float score) {
        if (score < 0f) {
            score = 0f;
        }
        if (score > 5f) {
            score = 5f;
        }
        float totalScores = this.scoreSeller.getScore() + score;
        int totalNumbers = this.scoreSeller.getScoreNumber() + 1;
        this.scoreSeller.setScore(totalScores/totalNumbers);
        this.scoreSeller.setScoreNumber(totalNumbers);
    }
}