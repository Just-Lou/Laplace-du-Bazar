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
}
