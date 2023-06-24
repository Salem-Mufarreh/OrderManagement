package WebServices.OrderManagement.Repositories;

import WebServices.OrderManagement.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<OrderEntity,Long> {
}
