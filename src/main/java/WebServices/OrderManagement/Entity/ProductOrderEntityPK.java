package WebServices.OrderManagement.Entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Salem Mufarreh
 * This class represents a primapry key of the table product_order it was used to create a new key from tboth procut id and order id.
 *
 * **/
@Data
public class ProductOrderEntityPK implements Serializable {
    private Long product;
    private Long order;
}
