package mapper;

import business.Apartment;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.UUID;

@Mapper
public interface ApartmentsMapper {
    public List<Apartment> getAllApartments();

    public Apartment getApartmentById(@Param("id") UUID id);
}
