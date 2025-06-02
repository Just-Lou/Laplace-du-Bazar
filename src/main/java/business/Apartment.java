package business;

import java.util.UUID;
import java.time.LocalDate;

public class Apartment extends Ad{
    private UUID apartmentId;
    private LocalDate disponibility;
    private String adress;
    private ApartmentSize apartmentSize;

    public Apartment(String title, String description, LocalDate publicationDate, float price, String folderPath, LocalDate disponibility, String adress, ApartmentSize apartmentSize) {
        super(title, description, publicationDate, price, folderPath);
        this.apartmentId = UUID.randomUUID();
        this.disponibility = disponibility;
        this.adress = adress;
        this.apartmentSize = apartmentSize;
    }

    public UUID getApartmentId() { return apartmentId; }
    public LocalDate getDisponibility() { return disponibility; }
    public void setDisponibility(LocalDate disponibility) { this.disponibility = disponibility; }
    public String getAdress() { return adress; }
    public void setAdress(String adress) { this.adress = adress; }
    public ApartmentSize getApartmentSize() { return apartmentSize; }
    public void setApartmentSize(ApartmentSize apartmentSize) { this.apartmentSize = apartmentSize; }
}
