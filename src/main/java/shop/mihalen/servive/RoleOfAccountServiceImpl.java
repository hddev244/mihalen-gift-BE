package shop.mihalen.servive;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mihalen.entity.AccountEntity;
import shop.mihalen.entity.RoleEntity;
import shop.mihalen.entity.RoleOfAccount;
import shop.mihalen.repository.AccountRepository;
import shop.mihalen.repository.RoleOfAccountRepository;
import shop.mihalen.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleOfAccountServiceImpl implements RoleOfAccountService {
    private final RoleOfAccountRepository repository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    @Override
    public RoleOfAccount saveOneRole(AccountEntity accountEntity, RoleEntity role) {
        RoleOfAccount roleOfAccount = new RoleOfAccount();
        roleOfAccount.setAccount(accountEntity);
        roleOfAccount.setRole(role);
        return repository.save(roleOfAccount);
    }

    @Override
    public ResponseEntity<?> changeRole(String username, String roleId) {
        Map<String, Object> responseMap = new HashMap<>();
        RoleOfAccount roleOfAccount = repository.findByUsernameAndRoleId(username, roleId);
        if (roleOfAccount != null) {
            responseMap.put("message", "Cập nhật thành công!");
            repository.delete(roleOfAccount);
            return ResponseEntity.ok().body(responseMap);
        }

        Optional<RoleEntity> role = roleRepository.findById(roleId);
        Optional<AccountEntity> accountEntity = accountRepository.findByUsernameLike(username);
        if(role.isPresent() && accountEntity.isPresent()){
            saveOneRole(accountEntity.get(), role.get());
            responseMap.put("message", "Thêm thành công!");
        }

        return ResponseEntity.ok().body(responseMap);
    }

    @Override
    public ResponseEntity<?> removeRole(String username, String roleId) {
        if (repository.findByUsernameAndRoleId(username, roleId) == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy role!");
        } else {
            repository.removeRole(username, roleId);
            return ResponseEntity.ok().body("Xóa thành công!");
        }
    }
}
