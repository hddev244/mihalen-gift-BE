package shop.mihalen.servive;

import java.util.List;

import shop.mihalen.entity.RoleEntity;
import shop.mihalen.model.Role;

public interface RoleService {
    Role findById(String roleId);

    List<Role> findAll();

    Role addNewRole(Role role);
}
