package shop.mihalen.servive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mihalen.entity.AccountEntity;
import shop.mihalen.entity.RoleEntity;
import shop.mihalen.entity.RoleOfAccount;
import shop.mihalen.enums.ROLE;
import shop.mihalen.model.Account;
import shop.mihalen.model.AccountRegister;
import shop.mihalen.repository.AccountRepository;
import shop.mihalen.repository.RoleRepository;
import shop.mihalen.security.ChangePasswordRequest;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    private final PasswordEncoder pe;
    private final RoleOfAccountService roleOfAccountService;
    private final RoleRepository roleRepository;

    private Account copyEntityToAccount(AccountEntity accountEntity) {
        Account account = new Account();
        BeanUtils.copyProperties(accountEntity, account);
        accountEntity.getRoleOfAccounts().stream()
                .map(RoleOfAccount::getRole)
                .forEach(account.getRoles()::add);
        return account;
    }

    @Override
    public Account saveAccount(AccountRegister accountRegister) {
        Optional<AccountEntity> accountFound = accountRepository.findByUsernameLike(accountRegister.getUsername());
        if (accountFound.isPresent()) {
            return null;
        } else {
            AccountEntity accountEntity = new AccountEntity();
            BeanUtils.copyProperties(accountRegister, accountEntity);

            accountEntity.setPassword(pe.encode(accountEntity.getPassword()));

            // save an account
            accountEntity = accountRepository.save(accountEntity);

            // add role Customer to accout registion
            RoleEntity role = roleRepository.findById(ROLE.ROLE_CUSTOMER.toString()).get();

            RoleOfAccount roleOfAccount = roleOfAccountService.saveOneRole(accountEntity, role);
            
            accountEntity.setRoleOfAccounts(List.of(roleOfAccount));

            return copyEntityToAccount(accountEntity);
        }
    }

    @Override
    public Optional<AccountEntity> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        Optional<AccountEntity> accountEntity = accountRepository.findByUsernameLike(username);
        if (accountEntity.isPresent()) {
            Account account = copyEntityToAccount(accountEntity.get());
            return Optional.of(account);
        }
        return Optional.empty();
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepository.findAll().stream()
                .map(ae -> copyEntityToAccount(ae))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Account> findPageAccounts(int size, int index) {
        Pageable pageable = PageRequest.of(index, size);
        Page<AccountEntity> pageAccountEntity = accountRepository.findAll(pageable);

        List<Account> pageAccount = pageAccountEntity.getContent().stream()
                .map(ae -> copyEntityToAccount(ae))
                .collect(Collectors.toList());

        return new PageImpl<>(pageAccount, pageable, pageAccountEntity.getTotalElements());
    }

    @Override
    public Account updateAccount(String username, Account account) {
        AccountEntity accountEntity = accountRepository.findByUsernameLike(username).get();
        accountEntity.setFullname(account.getFullname());
        accountEntity.setPhoneNumber(account.getPhoneNumber());
        accountEntity.setEmail(account.getEmail());
        accountEntity.setAddress(account.getAddress());
        accountEntity.setPhoto(account.getPhoto());
        accountRepository.save(accountEntity);

        return copyEntityToAccount(accountEntity);
    }

    @Override
    public void changePassword(Authentication authentication, ChangePasswordRequest request) {
        var username = authentication.getName();
        AccountEntity accountEntity = accountRepository.findByUsernameLike(username).get();
        // check if the current password is correct
        if (!pe.matches(request.getCurrentPassword(), accountEntity.getPassword())) {
            throw new IllegalStateException("Wrrong password");
        }

        // check if the two password are the same
        if (!request.getNewPassword().equals(request.getComfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update password
        accountEntity.setPassword(pe.encode(request.getNewPassword()));

        // save password
        accountRepository.save(accountEntity);
    }

    @Override
    public ResponseEntity<?> deleteAccount(Long id) {
        Optional<AccountEntity> accountEntity = accountRepository.findById(id);
        if (accountEntity.isPresent()) {
            try {
                accountRepository.deleteById(id);
            } catch (Exception e) {
                accountEntity.get().setLocked(true);
                accountRepository.save(accountEntity.get());
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<?> lockAccount(Long id) {
        Optional<AccountEntity> accountEntity = accountRepository.findById(id);
        if (accountEntity.isPresent()) {
            accountEntity.get().setLocked(!accountEntity.get().isLocked());
            accountRepository.save(accountEntity.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<?> createAccount(Account account) {
        Map<String, Object> responseMap = new HashMap<>();
        String message = "Tạo tài khỏan thành công!";
        if (accountRepository.findByUsernameLike(account.getUsername()).isPresent()) {
            message = "Tên người dùng đã tồn tại!";
            responseMap.put("message", message);
            return ResponseEntity.badRequest().body(responseMap);
        }
        if (accountRepository.findByEmailLike(account.getEmail()).isPresent()) {
            message = "Email đã được đăng kí!";
            responseMap.put("message", message);
            return ResponseEntity.badRequest().body(responseMap);
        }

        account.setPassword("123"); // setDefault Password

        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(account, accountEntity); // Copy account info to entity
        accountEntity.setPassword(pe.encode(account.getPassword())); // set encode password

        accountEntity = accountRepository.save(accountEntity); // save accout
        
        // save roles
        for ( RoleEntity r : account.getRoles()) {
            RoleEntity role = roleRepository.findById(r.getId()).get();
            roleOfAccountService.saveOneRole(accountEntity, role);
        }
        
        responseMap.put("message", message);
        responseMap.put("data", account);
        return ResponseEntity.ok().body(responseMap);
    }

}
