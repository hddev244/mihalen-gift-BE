package shop.mihalen.servive;

import java.util.Optional;

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
    public void addRole(String username, String roleId) {
        Optional<RoleEntity> role = roleRepository.findById(roleId);
        Optional<AccountEntity> accountEntity = accountRepository.findByUsernameLike(username);
        if(role.isPresent() && accountEntity.isPresent()){
            saveOneRole(accountEntity.get(), role.get());
        }
    }

    @Override
    public void removeRole(Long id, String roleId) {
        repository.removeRole(id, roleId);
    }
}
