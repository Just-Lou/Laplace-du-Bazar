package business;

import java.time.LocalDate;
import java.util.UUID;

public class Equipment extends Ad{
    private UUID equipmentId;

    public Equipment(String title, String description, LocalDate publicationDate, float price, String folderPath) {
        super(title, description, publicationDate, price, folderPath);
        this.equipmentId = UUID.randomUUID();
    }

    public UUID getEquipmentId() { return equipmentId; }
}

