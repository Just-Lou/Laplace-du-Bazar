package mapper;

import business.Book;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.UUID;

@Mapper
public interface BooksMapper {
    public List<Book> getAllBooks();

    public Book getBookById(@Param("id") UUID id);

    public void deleteBook(@Param("id") UUID id);

}
