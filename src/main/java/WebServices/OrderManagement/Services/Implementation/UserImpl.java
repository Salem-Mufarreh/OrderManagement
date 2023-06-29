package WebServices.OrderManagement.Services.Implementation;

import WebServices.OrderManagement.Entity.AuthenticationTokenEntity;
import WebServices.OrderManagement.Entity.RolesEntity;
import WebServices.OrderManagement.Entity.UserEntity;
import WebServices.OrderManagement.Repositories.UserRepo;
import WebServices.OrderManagement.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

@Service
public class UserImpl implements UserService {
    private final UserRepo _UserRepo;

    public UserImpl(UserRepo userRepo) {
        _UserRepo = userRepo;
    }

    @Override
    public UserEntity CreateUser(UserEntity user) {
        if (checkIfEntityParametersNullOrEmpty(user)){
            UserEntity SavedUser = _UserRepo.save(user);
            return SavedUser;
        }
        return null;
    }

    @Override
    public UserEntity GetUserById(Long id) {
        UserEntity user = _UserRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        if (user != null){
            return user;
        }
        return null;
    }

    @Override
    public List<UserEntity> GetAllUsers() {
        List<UserEntity> list = _UserRepo.findAll();
        if (list != null){
            return list;
        }
        return null;
    }

    @Override
    public UserEntity UpdateUser(Long id, UserEntity user) {
        UserEntity old = GetUserById(id);
        if (old != null){
            old.setEnabled(user.enabled);
            old.setPassword(user.getPassword());
            old.setUsername(user.getUsername());
            old.setFirstName(user.getFirstName());
            old.setLastName(user.getLastName());
            Set<RolesEntity> roles = old.getRoles();
            Set<AuthenticationTokenEntity> tokens = old.getAuthenticationTokens();
            roles.addAll(user.getRoles());
            old.setRoles(roles);
            tokens.addAll(user.getAuthenticationTokens());
            old.setAuthenticationTokens(tokens);
            user = _UserRepo.save(old);
            return user;
        }
        return null;
    }

    @Override
    public void DeleteUser(Long id) {
        UserEntity user = GetUserById(id);
        _UserRepo.delete(user);
    }

    @Override
    public UserEntity findUserByEmail(String username) {
        var user = _UserRepo.findByUsername(username);
        if (!user.isEmpty()){
            return  user.get();
        }
        return null;
    }

    public boolean checkIfEntityParametersNullOrEmpty(UserEntity entity) {
        if (entity == null) {
            return false;
        }

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);

                if (value == null) {
                    return false;
                }

                if (value instanceof String && ((String) value).isEmpty()) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return true;
    }


}
