package shop.mihalen.servive;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mihalen.entity.AccountEntity;
import shop.mihalen.entity.RoleEntity;
import shop.mihalen.entity.RoleOfAccount;
import shop.mihalen.model.Account;
import shop.mihalen.model.Role;
import shop.mihalen.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    private Account copyEntityToAccount(AccountEntity accountEntity) {
        Account account = new Account();
        BeanUtils.copyProperties(accountEntity, account);
        accountEntity.getRoleOfAccounts().stream()
                .map(RoleOfAccount::getRole)
                .forEach(account.getRoles()::add);
        return account;
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll().stream()
                .map(roleEntity -> Role.builder()
                        .id(roleEntity.getId())
                        .name(roleEntity.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Role findById(String id) {
        Optional<RoleEntity> roleEntity = repository.findById(id);
        if (roleEntity.isPresent()) {
            return Role.builder()
                    .id(roleEntity.get().getId())
                    .name(roleEntity.get().getName())
                    .accounts(
                            roleEntity.get().getAccounts().stream()
                                    .map(roleOfAccount -> copyEntityToAccount(roleOfAccount.getAccount()))
                                    .collect(Collectors.toList())
                                )
                    .build();
        }

        throw new IllegalStateException("Wrong roleID");
    }

    @Override
    public Role addNewRole(Role role) {
        RoleEntity roleEntity = new RoleEntity();
        BeanUtils.copyProperties(role, roleEntity);
        roleEntity = repository.save(roleEntity);
        return Role.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .build();
    }
}
