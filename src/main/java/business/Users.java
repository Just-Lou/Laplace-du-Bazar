package business;

import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import business.Score;

public class Users {
    private final UUID Id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private Score scoreClient;
    private Score scoreSeller;

    public Users(UUID id, String firstName, String lastName, String email, String password, UUID scoreClientId, UUID scoreSellerId) {
        this.Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.setPasswordHash(password);
        this.scoreClient = new Score(scoreClientId, 0, 0);
        this.scoreSeller = new Score(scoreSellerId, 0, 0);
    }

    public UUID getId() { return Id; }
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
    public float getScoreClient() { return this.scoreClient.getScore(); }

    public void setScoreClient(float score) {
        if (score < 0) {
            score = 0;
        }
        if (score > 5) {
            score = 5;
        }
        float totalScores = this.scoreClient.getScore() + score;
        int totalNumbers = this.scoreClient.getScoreNumber() + 1;
        this.scoreClient.setScore(totalScores/totalNumbers);
        this.scoreClient.setScoreNumber(totalNumbers);
    }

    public float getScoreSeller() { return this.scoreSeller.getScore(); }

    public void setScoreSeller(float score) {
        if (score < 0) {
            score = 0;
        }
        if (score > 5) {
            score = 5;
        }
        float totalScores = this.scoreSeller.getScore() + score;
        int totalNumbers = this.scoreSeller.getScoreNumber() + 1;
        this.scoreSeller.setScore(totalScores/totalNumbers);
        this.scoreSeller.setScoreNumber(totalNumbers);
    }
}