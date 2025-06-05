package business;

import java.time.LocalDate;
import java.util.UUID;

public class Equipment extends Ad{
    private UUID equipmentId;
    private String equipmentName;

    private Category equipmentCategory;

    public Equipment(UUID equipmentId, String title, String description, LocalDate publicationDate, float price, String folderPath, String equipmentName, UUID equipmentCategoryId, String equipmentCategoryName) {
        super(equipmentId, title, description, publicationDate, price, folderPath);
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.equipmentCategory = new Category(equipmentCategoryId, equipmentCategoryName);
    }

    public UUID getEquipmentId() { return equipmentId; }
    public String getEquipmentName() { return equipmentName; }
    public void setEquipmentName(String equipmentName) { this.equipmentName = equipmentName; }
    public UUID getEquipmentCategoryId() { return equipmentCategory.getCategoryId(); }
    public void setEquipmentCategoryId(UUID equipmentCategoryId) { this.equipmentCategory.setCategoryId(equipmentCategoryId) ; }
    public String getEquipmentCategoryName() { return equipmentCategory.getCategoryName(); }
    public void setEquipmentCategoryName(String equipmentCategoryName) { this.equipmentCategory.setCategoryName(equipmentCategoryName); }

}

