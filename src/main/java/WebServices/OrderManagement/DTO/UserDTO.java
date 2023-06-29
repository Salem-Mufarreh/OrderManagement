package WebServices.OrderManagement.DTO;

import WebServices.OrderManagement.Entity.AuthenticationTokenEntity;
import WebServices.OrderManagement.Entity.RolesEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
@Data
public class UserDTO {
    public Long id;
    public String username; //it's the email
    public String password;
    public String firstName;
    public String lastName;
    public LocalDateTime createdAt;
    public boolean enabled;
    public Set<RolesEntity> roles;
    public Set<AuthenticationTokenEntity> authenticationTokens;
}
