package WebServices.OrderManagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Salem Mufarreh
 * This class represents a product_Order linkage table many-to-many relation.
 *
 * The class stores the product_Order's ID, quantity, price,vat, product id, and order id.
 * **/
@Data
@Table
@Entity
public class ProductOrderEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    public int quantity;
    public double price;
    public double vat;

    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = true)
    private OrderEntity order;


}
