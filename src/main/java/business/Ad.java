package business;

import java.time.LocalDate;
import java.util.UUID;
import business.Users;

public class Ad {
    protected final UUID adId;
    protected String title;
    protected String description;
    protected LocalDate publicationDate;
    protected float price;
    protected int flagQty;
    protected String folderPath;
    protected Users user;
    protected boolean archived;

    public Ad(UUID adId, String title, String description, LocalDate publicationDate, float price, String folderPath) {
        this.adId = adId;
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
        this.price = price;
        this.folderPath = folderPath;
    }

    public UUID getAdId() { return adId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public String getFolderPath() { return folderPath; }
    public void setFolderPath(String folderPath) { this.folderPath = folderPath; }
    public int getFlagQty() { return flagQty; }
    public void setFlagQty(int flagQty) { this.flagQty = flagQty; }
    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }
    public void archive() { archived = true; }
    public void unArchive() { archived = false; }
    public boolean isArchived() { return archived; }
}
