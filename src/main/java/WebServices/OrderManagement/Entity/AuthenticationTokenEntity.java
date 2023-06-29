package WebServices.OrderManagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table
@Entity
public class AuthenticationTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String token;
    public Boolean expired;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public UserEntity user;

}
