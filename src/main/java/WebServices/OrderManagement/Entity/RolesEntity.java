package WebServices.OrderManagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    public Set<UserEntity> users;
}
