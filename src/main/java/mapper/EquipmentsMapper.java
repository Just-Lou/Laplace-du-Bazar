package mapper;

import business.Equipment;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.UUID;

@Mapper
public interface EquipmentsMapper {
    public List<Equipment> getAllEquipments();

    public Equipment getEquipmentById(@Param("id") UUID id);

    public void deleteEquipment(@Param("id") UUID id);
}
