package mapper;

import business.Users;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import org.apache.ibatis.annotations.*;
import org.jose4j.http.Response;

import java.util.List;
import java.util.UUID;

@Mapper
public interface UsersMapper {
    public List<Users> getAllUsers();

    public Users getUserById(@Param("id") UUID id);

    public Response createUser(@Param("id") UUID id,
                               @Param("firstName") String firstName,
                               @Param("lastName") String lastName,
                               @Param("email") String email,
                               @Param("role") String[] role);

    public Response updateUser(@Param("id") UUID id,
                               @Param("firstName") String firstName,
                               @Param("lastName") String lastName,
                               @Param("email") String email);

    public Response deleteUser(@Param("id") UUID id);

    public String getUserIdByEmail(@Param("email") String email);
}
