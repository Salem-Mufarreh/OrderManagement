package WebServices.OrderManagement.Entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductOrderEntityPK implements Serializable {
    private Long product;
    private Long order;
}
