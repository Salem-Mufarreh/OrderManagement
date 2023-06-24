package WebServices.OrderManagement.Services;

import WebServices.OrderManagement.Entity.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderEntity CreateOrder(OrderEntity order);
    OrderEntity GetOrderById(Long id);
    List<OrderEntity> GetAllOrders();
    OrderEntity UpdateOrder(Long id, OrderEntity order);
    void DeleteOrder(Long id);
}
