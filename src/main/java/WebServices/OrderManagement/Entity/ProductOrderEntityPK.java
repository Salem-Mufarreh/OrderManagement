package WebServices.OrderManagement.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductOrderEntityPK implements Serializable {
    private Long product;
    private Long order;
}
