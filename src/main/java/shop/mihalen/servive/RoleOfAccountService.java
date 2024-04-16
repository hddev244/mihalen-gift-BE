package shop.mihalen.servive;

import org.springframework.http.ResponseEntity;

import shop.mihalen.entity.AccountEntity;
import shop.mihalen.entity.RoleEntity;
import shop.mihalen.entity.RoleOfAccount;

public interface RoleOfAccountService {
    
    RoleOfAccount saveOneRole(AccountEntity accountEntity, RoleEntity role);

    ResponseEntity<?> changeRole(String username, String roleId);

    ResponseEntity<?> removeRole(String username, String roleId);
}
