package WebServices.OrderManagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Salem Mufarreh
 * This class represents a product.
 *
 * The class stores the product's ID, name, reference number, price, vat, and is it stockable .
 * **/
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

    @JsonIgnore
    @OneToMany(mappedBy = "StockProduct")
    public Set<StockEntity> stockEntries = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<ProductOrderEntity> productOrders = new HashSet<>();

}
