package WebServices.OrderManagement.Repositories;

import WebServices.OrderManagement.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity,Long> {
}
