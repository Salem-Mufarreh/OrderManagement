package WebServices.OrderManagement.Services;


import WebServices.OrderManagement.Entity.ProductOrderEntity;

import java.util.List;

public interface ProductOrderService {
    ProductOrderEntity CreateProductOrder(ProductOrderEntity productOrder);
    ProductOrderEntity GetOrderById(Long id);
    List<ProductOrderEntity> GetAllProductOrders();
    ProductOrderEntity UpdateOrder(Long id, ProductOrderEntity productOrder);
    void DeleteOrder(Long id);
}
