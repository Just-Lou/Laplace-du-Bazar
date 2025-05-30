package business;

import java.time.LocalDate;
import java.util.UUID;

public class Book extends Ad{
    private UUID bookId;
    private String author;
    private String bookTitle;

    public Book(String title, String description, LocalDate publicationDate, float price, String folderPath, String author, String bookTitle) {
        super(title, description, publicationDate, price, folderPath);
        this.bookId = UUID.randomUUID();
        this.author = author;
        this.bookTitle = bookTitle;
    }

    public UUID getBookId() { return bookId; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
}
