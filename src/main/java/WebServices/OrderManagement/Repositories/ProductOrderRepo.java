package WebServices.OrderManagement.Repositories;

import WebServices.OrderManagement.Entity.ProductOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepo extends JpaRepository<ProductOrderEntity,Long> {
}
