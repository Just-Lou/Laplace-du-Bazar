package mapper;

import business.Apartment;
import business.ApartmentDetailsViewModel;
import business.ApartmentViewModel;
import org.apache.ibatis.annotations.*;

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
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            @Param("minScore") Float minScore,
            @Param("disponibilityBefore") String disponibilityBefore,
            @Param("apartmentSize") String apartmentSize,
            @Param("sortBy") String sortBy
    );
}
