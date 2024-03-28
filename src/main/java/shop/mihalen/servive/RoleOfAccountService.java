package shop.mihalen.servive;

import shop.mihalen.entity.AccountEntity;
import shop.mihalen.entity.RoleEntity;
import shop.mihalen.entity.RoleOfAccount;

public interface RoleOfAccountService {
    RoleOfAccount saveOneRole(AccountEntity accountEntity, RoleEntity role);

    void addRole(String username, String roleId);

    void removeRole(Long id, String roleId);
}
