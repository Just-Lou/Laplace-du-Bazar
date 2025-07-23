package mapper;

import business.Apartment;
import business.ApartmentDetailsViewModel;
import business.ApartmentSize;
import business.ApartmentViewModel;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Mapper
public interface ApartmentsMapper {

    public List<ApartmentViewModel> getAllApartments(@Param("userId") UUID userUUID);

    public ApartmentDetailsViewModel getApartmentById(@Param("id") UUID id, @Param("userId") UUID userId);

    public void deleteApartment(@Param("id") UUID id);

    public void addToFavorites(@Param("userId") UUID userId, @Param("adId") UUID adId );

    public void removeFromFavorites(@Param("userId") UUID userId, @Param("adId") UUID adId);

    public List<ApartmentViewModel> getFavoriteApartments(@Param("userId") UUID userId);

    List<ApartmentViewModel> getApartmentsByCriteria(
            @Param("userId") UUID userId,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            @Param("minScore") Float minScore,
            @Param("disponibilityBefore") LocalDate disponibilityBefore,
            @Param("apartmentSize") Float apartmentSize,
            @Param("sortBy") String sortBy
    );

    public void createAd(
            @Param("id") UUID id,
            @Param("title") String title,
            @Param("description") String description,
            @Param(("folderPath")) String folderPath,
            @Param("price") Float price,
            @Param("userId") UUID userId
    );

    public void createApartment(
            @Param("apartmentId") UUID apartmentId,
            @Param("disponibility") LocalDate disponibility,
            @Param("address") String address,
            @Param("apartmentSizeId") UUID apartmentSizeId,
            @Param("adId") UUID adId
    );

    public List<ApartmentSize> getSizes();

    public List<ApartmentViewModel> getAllApartmentsFromUser(@Param("userId") UUID userUUID);

    public String getSellerId (@Param("adId") UUID adId);

    public void archiveAd(@Param("adId") UUID adId);

    public String getFolderPath (@Param("adId") UUID adId);
}
