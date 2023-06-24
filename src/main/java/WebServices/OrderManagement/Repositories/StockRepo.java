package WebServices.OrderManagement.Repositories;

import WebServices.OrderManagement.Entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepo extends JpaRepository<StockEntity,Long> {
}
