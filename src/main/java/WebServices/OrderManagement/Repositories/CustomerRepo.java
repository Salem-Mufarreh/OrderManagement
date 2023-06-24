package WebServices.OrderManagement.Repositories;

import WebServices.OrderManagement.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<CustomerEntity,Long> {
}
