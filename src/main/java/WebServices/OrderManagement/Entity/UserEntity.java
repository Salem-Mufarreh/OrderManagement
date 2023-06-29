package WebServices.OrderManagement.Entity;

import WebServices.OrderManagement.DTO.UserDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Table
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String username; //it's the email
    public String password;
    public String firstName;
    public String lastName;
    public LocalDateTime createdAt;
    public boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    public Set<RolesEntity> roles;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    public Set<AuthenticationTokenEntity> authenticationTokens;



}
