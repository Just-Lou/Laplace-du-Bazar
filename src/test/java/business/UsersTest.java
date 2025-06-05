package business;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {

    UUID id = UUID.randomUUID();
    UUID scoreClientId = UUID.randomUUID();
    UUID scoreSellerId = UUID.randomUUID();
    Users user = new Users(id, "Maina", "Clermont", "mc@yeah.com", "abc", "Admin", scoreClientId, scoreSellerId);

    @Test
    void getId() {
        assertEquals(id, user.getUsersId());
    }

    @Test
    void getFirstName() {
        assertEquals("Maina", user.getFirstName());
    }

    @Test
    void setFirstName() {
        user.setFirstName("Amelie");
        assertEquals("Amelie", user.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Clermont", user.getLastName());
    }

    @Test
    void setLastName() {
        user.setLastName("Luneau");
        assertEquals("Luneau", user.getLastName());
    }

    @Test
    void getEmail() {
        assertEquals("mc@yeah.com", user.getEmail());
    }

    @Test
    void setEmail() {
        user.setEmail("al@yeah.com");
        assertEquals("al@yeah.com", user.getEmail());
    }

    @Test
    void getType() { assertEquals("Admin", user.getUserType()); }

    @Test
    void getScoreClientId() {
        assertEquals(scoreClientId, user.getScoreClientId());
    }

    @Test
    void getScoreSellerId() {
        assertEquals(scoreSellerId, user.getScoreSellerId());
    }

    @Test
    void getTotalScoreClient() {
        assertEquals(0f, user.getTotalScoreClient());
    }

    @Test
    void getNumberScoreClient() {
        assertEquals(0, user.getNumberScoreClient());
    }

    @Test
    void getAverageScoreClient() {
        assertEquals(0f, user.getAverageScoreClient());
    }

    @Test
    void setScoreClient() {
        user.setScoreClient(1.5f);
        assertEquals(1.5f, user.getTotalScoreClient());
    }

    @Test
    void addScoreClient() {
        user.addScoreClient(4.5f);
        user.addScoreClient(3.5f);
        assertEquals(4f, user.getAverageScoreClient());
    }

    @Test
    void getTotalScoreSeller() {
        assertEquals(0f, user.getTotalScoreSeller());
    }

    @Test
    void getNumberScoreSeller() {
        assertEquals(0, user.getNumberScoreSeller());
    }

    @Test
    void getAverageScoreSeller() {
        assertEquals(0f, user.getAverageScoreSeller());
    }

    @Test
    void setScoreSeller() {
        user.setScoreSeller(2f);
        assertEquals(2f, user.getTotalScoreSeller());
    }

    @Test
    void addScoreSeller() {
        user.addScoreSeller(2.5f);
        user.addScoreSeller(5f);
        assertEquals(3.75f, user.getAverageScoreSeller());
    }

}