package WebServices.OrderManagement.Repositories;

import WebServices.OrderManagement.Entity.AuthenticationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationTokenRepo extends JpaRepository<AuthenticationTokenEntity,Long> {
}
