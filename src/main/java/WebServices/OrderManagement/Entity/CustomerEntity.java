package WebServices.OrderManagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Table
@Entity
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String firstName;
    public String lastName;
    public LocalDate bornAt;
    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders;

}
