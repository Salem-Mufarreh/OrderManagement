package WebServices.OrderManagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table
@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    public CustomerEntity customer;

    public LocalDateTime orderedAt;
    @OneToMany(mappedBy = "order")
    public List<ProductOrderEntity> productOrders;
}
