package mapper;

import business.Book;
import business.BookViewModel;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.UUID;

@Mapper
public interface BooksMapper {
    List<BookViewModel> getAllBooks(@Param("userId") UUID userId);

    BookViewModel getBookById(@Param("id") UUID id, @Param("userId") UUID userId);

    void deleteBook(@Param("id") UUID id);

    void addToFavorites(@Param("userId") UUID userId, @Param("adId") UUID adId);

    void removeFromFavorites(@Param("userId") UUID userId, @Param("adId") UUID adId);

    List<BookViewModel> getFavoriteBooks(@Param("userId") UUID userId);

    List<BookViewModel> getBooksByCriteria(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minScore") Double minScore,
            @Param("categoryId") UUID categoryId,
            @Param("sortBy") String sortBy,
            @Param("userId") UUID userId
    );
}
