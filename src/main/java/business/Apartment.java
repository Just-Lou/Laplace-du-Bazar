package business;

import java.util.UUID;
import java.time.LocalDate;

public class Apartment extends Ad{
    private UUID apartmentId;
    private LocalDate disponibility;
    private String adress;
    private ApartmentSize apartmentSize;

    public Apartment(UUID apartmentId, String title, String description, LocalDate publicationDate, float price, String folderPath, LocalDate disponibility, String adress, UUID apartmentSizeId, String apartmentSize) {
        super(apartmentId, title, description, publicationDate, price, folderPath);
        this.apartmentId = apartmentId;
        this.disponibility = disponibility;
        this.adress = adress;
        this.apartmentSize = new ApartmentSize(apartmentSizeId, apartmentSize);
    }

    public UUID getApartmentId() { return apartmentId; }
    public LocalDate getDisponibility() { return disponibility; }
    public void setDisponibility(LocalDate disponibility) { this.disponibility = disponibility; }
    public String getAdress() { return adress; }
    public void setAdress(String adress) { this.adress = adress; }
    public String getApartmentSize() { return apartmentSize.getApartmentSize(); }
    public void setApartmentSize(String apartmentSize) { this.apartmentSize.setApartmentSize(apartmentSize); }
}
