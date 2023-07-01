package WebServices.OrderManagement.Services.Implementation;

import WebServices.OrderManagement.Entity.RolesEntity;
import WebServices.OrderManagement.Entity.UserEntity;
import WebServices.OrderManagement.Repositories.RolesRepo;
import WebServices.OrderManagement.Services.RolesService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
/**
 * This class implements the RolesService interface.
 *
 * The class provides methods for creating, retrieving, updating, and deleting Roles.
 */
@Service
public class RolesImpl implements RolesService {
    private final RolesRepo _RolesRepo;

    public RolesImpl(RolesRepo rolesRepo) {
        _RolesRepo = rolesRepo;
    }

    @Override
    public RolesEntity CreateRole(RolesEntity role) {
        if (checkIfEntityParametersNullOrEmpty(role)){
            RolesEntity savedRole = _RolesRepo.save(role);
            return savedRole;
        }
        return null;
    }

    @Override
    public RolesEntity GetRoleById(Long id) {
        RolesEntity role = _RolesRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Role not found"));
        if (role !=null){
            return role;
        }
        return null;
    }

    @Override
    public List<RolesEntity> GetAllRoles() {
        List<RolesEntity> list = _RolesRepo.findAll();
        if (list != null){
            return list;
        }
        return null;
    }

    @Override
    public RolesEntity UpdateRole(Long id, RolesEntity role) {
        RolesEntity old = GetRoleById(id);
        if (old != null){
            old.setDescription(role.description);
            old.setRoleName(role.getRoleName());
            Set<UserEntity> list = old.getUsers();
            for (UserEntity user: role.getUsers()) {
                list.add(user);
            }
            old.setUsers(list);
            role = _RolesRepo.save(old);
            return role;
        }
        return null;
    }

    @Override
    public void DeleteRole(Long id) {
        RolesEntity role = GetRoleById(id);
        if (role != null){
            _RolesRepo.delete(role);
        }
    }
    public boolean checkIfEntityParametersNullOrEmpty(RolesEntity entity) {
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
