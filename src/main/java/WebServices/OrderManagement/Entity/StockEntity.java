package WebServices.OrderManagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * @author Salem Mufarreh
 * This class represents a stock.
 *
 * The class stores the stock's ID, product, quantity, and update time.
 * **/
@Data
@Table
@Entity
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    public ProductEntity StockProduct;
    public int quantity;
    public LocalDateTime updatedAt;
}
