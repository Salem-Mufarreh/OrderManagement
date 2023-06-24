package WebServices.OrderManagement.Repositories;

import WebServices.OrderManagement.Entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<RolesEntity,Long> {
}
