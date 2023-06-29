package WebServices.OrderManagement.Repositories;

import WebServices.OrderManagement.Entity.AuthenticationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationTokenRepo extends JpaRepository<AuthenticationTokenEntity,Long> {
    Optional<AuthenticationTokenEntity> findByUserId(Long id);
    Optional<AuthenticationTokenEntity> findByToken(String token);
}
