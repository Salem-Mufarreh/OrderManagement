package WebServices.OrderManagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table
@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String name;
    public String reference;
    public Double price;
    public Double vat;
    public Boolean stockAble;
    @OneToMany(mappedBy = "StockProduct")
    public List<StockEntity> stockEntries;
    @OneToMany(mappedBy = "product")
    public List<ProductOrderEntity> productOrder;

}