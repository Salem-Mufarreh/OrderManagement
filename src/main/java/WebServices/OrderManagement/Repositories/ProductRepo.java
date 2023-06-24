package WebServices.OrderManagement.Repositories;

import WebServices.OrderManagement.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
}
