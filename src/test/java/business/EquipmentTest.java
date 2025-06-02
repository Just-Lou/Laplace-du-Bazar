package business;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class EquipmentTest {

    UUID categoryId = UUID.randomUUID();
    LocalDate publishDate = LocalDate.of(2025, 6, 2);
    String categoryName = "Outil";
    Category equipmentCategory = new Category(categoryId, categoryName);

    Equipment equipment = new Equipment("marteau à vendre","robuste, peu utilisé", publishDate,15.0f,"/images/outil1","Marteau", equipmentCategory.getCategoryId(), equipmentCategory.getCategoryName());


    @Test
    void getEquipmentId() {
        assertNotNull(equipment.getEquipmentId());
    }

    @Test
    void getEquipmentName() {
        assertEquals("Marteau", equipment.getEquipmentName());
    }

    @Test
    void setEquipmentName() {
        equipment.setEquipmentName("Tournevis");
        assertEquals("Tournevis", equipment.getEquipmentName());
    }

    @Test
    void getEquipmentCategoryId() {
        assertEquals(categoryId, equipment.getEquipmentCategoryId());
    }

    @Test
    void getEquipmentCategoryName() {
        assertEquals("Outils", equipment.getEquipmentCategoryName());
    }

    // Champs hérités de Ad

    @Test
    void getTitle() {
        assertEquals("marteau à vendre", equipment.getTitle());
    }

    @Test
    void setTitle() {
        equipment.setTitle("tournevis à ventre");
        assertEquals("tournevis à ventre", equipment.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals("robuste, peu utilisé", equipment.getDescription());
    }

    @Test
    void setDescription() {
        equipment.setDescription("tout cassé frl");
        assertEquals("tout cassé frl", equipment.getDescription());
    }

    @Test
    void getPublicationDate() {
        assertEquals(publishDate, equipment.getPublicationDate());
    }

    @Test
    void setPublicationDate() {
        LocalDate newDate = LocalDate.of(2025, 4, 1);
        equipment.setPublicationDate(newDate);
        assertEquals(newDate, equipment.getPublicationDate());
    }

    @Test
    void getPrice() {
        assertEquals(15.0f, equipment.getPrice());
    }

    @Test
    void setPrice() {
        equipment.setPrice(5.00f);
        assertEquals(5.00f, equipment.getPrice());
    }

    @Test
    void getFolderPath() {
        assertEquals("/images/outil1", equipment.getFolderPath());
    }

    @Test
    void setFolderPath() {
        equipment.setFolderPath("/images/outil2");
        assertEquals("/images/outil2", equipment.getFolderPath());
    }
}
