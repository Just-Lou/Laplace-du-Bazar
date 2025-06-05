package business;

import java.time.LocalDate;
import java.util.UUID;

public class Book extends Ad{
    private UUID bookId;
    private String author;
    private String bookTitle;
    private Category bookCategory;

    public Book(UUID bookId, String title, String description, LocalDate publicationDate, float price, String folderPath, String author, String bookTitle, String bookCategoryName, UUID bookCategoryId) {
        super(bookId, title, description, publicationDate, price, folderPath);
        this.bookId = bookId;
        this.author = author;
        this.bookTitle = bookTitle;
        this.bookCategory = new Category(bookCategoryId, bookCategoryName);
    }

    public UUID getBookId() { return bookId; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public UUID getBookCategoryId() { return bookCategory.getCategoryId(); }
    public void setBookCategoryId(UUID bookCategoryId) { this.bookCategory.setCategoryId(bookCategoryId); }
    public String getBookCategoryName() { return bookCategory.getCategoryName(); }
    public void setBookCategoryName(String bookCategoryName) { this.bookCategory.setCategoryName(bookCategoryName); }

}
