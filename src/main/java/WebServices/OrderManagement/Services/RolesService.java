package WebServices.OrderManagement.Services;

import WebServices.OrderManagement.Entity.RolesEntity;

import java.util.List;

public interface RolesService {
    RolesEntity CreateRole(RolesEntity role);
    RolesEntity GetRoleById(Long id);
    List<RolesEntity> GetAllRoles();
    RolesEntity UpdateRole(Long id, RolesEntity role);
    void DeleteRole (Long id);
}
