package WebServices.OrderManagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
/**
 * @author Salem Mufarreh
 * This class represents a order.
 *
 * The class stores the order's ID, customer, and order date.
 * **/
@Data
@Table
@Entity
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id",nullable = true)
    public CustomerEntity customer;

    public LocalDateTime orderedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private Set<ProductOrderEntity> productOrders = new HashSet<>();

}
