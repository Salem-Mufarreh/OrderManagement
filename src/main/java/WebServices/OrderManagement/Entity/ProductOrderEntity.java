package WebServices.OrderManagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
@IdClass(ProductOrderEntityPK.class)
public class ProductOrderEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    public ProductEntity product;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    public OrderEntity order;
    public int quantity;
    public double price;
    public double vat;

}
