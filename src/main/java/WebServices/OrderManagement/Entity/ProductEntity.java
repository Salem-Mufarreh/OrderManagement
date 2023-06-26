package WebServices.OrderManagement.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
