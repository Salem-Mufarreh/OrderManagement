package WebServices.OrderManagement.Entity;

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
    public LocalDateTime expiryDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserEntity user;

}
