package WebServices.OrderManagement.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;


/**
 * @author Salem Mufarreh
 * This class represents a user.
 *
 * The class stores the user's ID, username, password, first name, last name, created time, is active, tokens, and roles.
 * **/
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
