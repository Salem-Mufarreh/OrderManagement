package WebServices.OrderManagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

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
    public Set<UserEntity> users;
}
