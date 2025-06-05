package business;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;


public class ApartmentTest {

    UUID apartmentId = UUID.randomUUID();
    UUID sizeId = UUID.randomUUID();
    ApartmentSize size = new ApartmentSize(sizeId, 4.5);
    LocalDate publishDate = LocalDate.of(2025, 6, 2);
    LocalDate dispoDate = LocalDate.of(2025, 7, 1);

    Apartment apt = new Apartment(apartmentId,"Superbe 4 1/2 à louer", "Près de l'uni, trop cool", publishDate, 1600.0f, "/images/appart1", dispoDate, "123 rue X, Sherbrooke", sizeId, 4.5);

    @Test
    void getApartmentId() {
        assertEquals(apartmentId, apt.getApartmentId());
    }

    @Test
    void getDisponibility() {
        assertEquals(dispoDate, apt.getDisponibility());
    }

    @Test
    void setDisponibility() {
        LocalDate newDate = LocalDate.of(2025, 8, 1);
        apt.setDisponibility(newDate);
        assertEquals(newDate, apt.getDisponibility());
    }

    @Test
    void getAdress() {
        assertEquals("123 rue X, Sherbrooke", apt.getAdress());
    }

    @Test
    void setAdress() {
        apt.setAdress("456 rue Y, Sherbrooke");
        assertEquals("456 rue Y, Sherbrooke", apt.getAdress());
    }

    @Test
    void getApartmentSize() {
        assertEquals(4.5, apt.getApartmentSize());
    }

    @Test
    void setApartmentSize() {
        apt.setApartmentSize(5.5);
        assertEquals(5.5, apt.getApartmentSize());
    }


    @Test
    void getTitle() {
        assertEquals("Superbe 4 1/2 à louer", apt.getTitle());
    }

    @Test
    void setTitle() {
        apt.setTitle("incroyable 4 1/2 à louer");
        assertEquals("incroyable 4 1/2 à louer", apt.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals("Près de l'uni, trop cool", apt.getDescription());
    }

    @Test
    void setDescription() {
        apt.setDescription("Stationnement et wifi inclut, trop slay");
        assertEquals("Stationnement et wifi inclut, trop slay", apt.getDescription());
    }

    @Test
    void getPublicationDate() {
        assertEquals(publishDate, apt.getPublicationDate());
    }

    @Test
    void setPublicationDate() {
        LocalDate newPubDate = LocalDate.of(2025, 5, 15);
        apt.setPublicationDate(newPubDate);
        assertEquals(newPubDate, apt.getPublicationDate());
    }

    @Test
    void getPrice() {
        assertEquals(1600.0f, apt.getPrice());
    }

    @Test
    void setPrice() {
        apt.setPrice(1350.0f);
        assertEquals(1350.0f, apt.getPrice());
    }

    @Test
    void getFolderPath() {
        assertEquals("/images/appart1", apt.getFolderPath());
    }

    @Test
    void setFolderPath() {
        apt.setFolderPath("/images/appart2");
        assertEquals("/images/appart2", apt.getFolderPath());
    }

}