package WebServices.OrderManagement.Services;

import WebServices.OrderManagement.Entity.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity CreateUser(UserEntity user);
    UserEntity GetUserById(Long id);
    List<UserEntity> GetAllUsers();
    UserEntity UpdateUser(Long id, UserEntity user);
    void DeleteUser (Long id);
}
