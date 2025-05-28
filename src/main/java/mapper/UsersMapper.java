package mapper;

import business.Users;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.UUID;

@Mapper
public interface UsersMapper {
    public List<Users> getAllUsers();

    public Users getUserById(@Param("id") UUID id);
}
