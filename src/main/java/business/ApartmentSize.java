package business;

import java.util.UUID;

public class ApartmentSize {
    private UUID apartmentSizeId;
    private String apartmentSize;

    ApartmentSize(UUID apartmentSizeId, String apartmentSize) {
        this.apartmentSizeId = apartmentSizeId;
        this.apartmentSize = apartmentSize;
    }

    public UUID getApartmentSizeId() { return apartmentSizeId; }
    public void setApartmentSize(String apartmentSize) { this.apartmentSize = apartmentSize; }
    public String getApartmentSize() { return apartmentSize; }

}
