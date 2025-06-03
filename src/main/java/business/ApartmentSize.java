package business;

import java.util.UUID;

public class ApartmentSize {
    private UUID apartmentSizeId;
    private double apartmentSize;

    ApartmentSize(UUID apartmentSizeId, double apartmentSize) {
        this.apartmentSizeId = apartmentSizeId;
        this.apartmentSize = apartmentSize;
    }

    public UUID getApartmentSizeId() { return apartmentSizeId; }
    public void setApartmentSize(double apartmentSize) { this.apartmentSize = apartmentSize; }
    public double getApartmentSize() { return apartmentSize; }

}
