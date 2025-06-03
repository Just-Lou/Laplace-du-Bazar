package business;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    LocalDate publishDate = LocalDate.of(2025, 6, 2);

    UUID categoryId = UUID.randomUUID();
    String categoryName = "Narratif";
    Category bookCategory = new Category(categoryId, categoryName);

    Book book = new Book("livre usagé crazy", "f jk rowling", publishDate, 25.00f, "/image/livre1", "jk rowling", "Harry Potter et la coupe de feu", bookCategory.getCategoryName(), bookCategory.getCategoryId());

    @Test
    void getBookId() {
        assertNotNull(book.getBookId());
    }

    @Test
    void getAuthor() {
        assertEquals("jk rowling", book.getAuthor());
    }

    @Test
    void setAuthor() {
        book.setAuthor("pas jk rowling");
        assertEquals("pas jk rowling", book.getAuthor());
    }

    @Test
    void getBookTitle() {
        assertEquals("Harry Potter et la coupe de feu", book.getBookTitle());
    }

    @Test
    void setBookTitle() {
        book.setBookTitle("Harry Potter à l'école des sorciers");
        assertEquals("Harry Potter à l'école des sorciers", book.getBookTitle());
    }

    @Test
    void getBookCategoryId() {
        assertEquals(categoryId, book.getBookCategoryId());
    }

    @Test
    void getBookCategoryName() {
        assertEquals("Narratif", book.getBookCategoryName());
    }

    // Champs hérités de Ad

    @Test
    void getTitle() {
        assertEquals("livre usagé crazy", book.getTitle());
    }

    @Test
    void setTitle() {
        book.setTitle("livre usagé de fou");
        assertEquals("livre usagé de fou", book.getTitle());
    }

    @Test
    void getDescription() {
        assertEquals("f jk rowling", book.getDescription());
    }

    @Test
    void setDescription() {
        book.setDescription("f jk rowling encore");
        assertEquals("f jk rowling encore", book.getDescription());
    }

    @Test
    void getPublicationDate() {
        assertEquals(publishDate, book.getPublicationDate());
    }

    @Test
    void setPublicationDate() {
        LocalDate newDate = LocalDate.of(2023, 1, 1);
        book.setPublicationDate(newDate);
        assertEquals(newDate, book.getPublicationDate());
    }

    @Test
    void getPrice() {
        assertEquals(25.00f, book.getPrice());
    }

    @Test
    void setPrice() {
        book.setPrice(20.00f);
        assertEquals(20.00f, book.getPrice());
    }

    @Test
    void getFolderPath() {
        assertEquals("/images/livre1", book.getFolderPath());
    }

    @Test
    void setFolderPath() {
        book.setFolderPath("/images/livre2");
        assertEquals("/images/livre2", book.getFolderPath());
    }



}
