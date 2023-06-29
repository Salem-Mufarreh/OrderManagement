package WebServices.OrderManagement.Repositories;

import WebServices.OrderManagement.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByUsername(String username);
}
