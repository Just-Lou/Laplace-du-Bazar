package mapper;

import business.Equipment;
import business.EquipmentViewModel;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface EquipmentsMapper {
    public List<EquipmentViewModel> getAllEquipments(@Param("userId") UUID userUUID);

    public Equipment getEquipmentById(@Param("id") UUID id, @Param("userId") UUID userId);

    public void deleteEquipment(@Param("id") UUID id);

    public void addToFavorites(@Param("userId") UUID userId, @Param("adId") UUID adId );

    public void removeFromFavorites(@Param("userId") UUID userId, @Param("adId") UUID adId);

    public List<EquipmentViewModel> getFavoriteEquipments(@Param("userId") UUID userId);

    List<EquipmentViewModel> getEquipmentsByCriteria(
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            @Param("minScore") Float minScore,
            @Param("equipmentName") String equipmentName,
            @Param("sortBy") String sortBy
    );
}
