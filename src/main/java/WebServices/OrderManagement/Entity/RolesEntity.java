package WebServices.OrderManagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

/**
 * @author Salem Mufarreh
 * This class represents a role.
 *
 * The class stores the customer's ID, role name, description, and users assigned to this role.
 * **/
@Data
@Table
@Entity
public class RolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String roleName;
    public String description;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    public Set<UserEntity> users;
}
