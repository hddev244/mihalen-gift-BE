package shop.mihalen.servive;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mihalen.entity.AccountEntity;
import shop.mihalen.entity.RoleOfAccount;
import shop.mihalen.model.Account;
import shop.mihalen.model.AccountRegister;
import shop.mihalen.repository.AccountRepository;
import shop.mihalen.security.ChangePasswordRequest;
import shop.mihalen.security.JwtUtils;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository; 
    private final PasswordEncoder pe;
    private final JwtUtils jwtUtils;


    @Override
    public Account saveAccount(AccountRegister accountRegister) {
        Optional<AccountEntity> accountFound = accountRepository.findByUsernameLike(accountRegister.getUsername());
        if (accountFound.isPresent()) {
            return null;
        } else {
            AccountEntity accountEntity = new AccountEntity();
            BeanUtils.copyProperties(accountRegister, accountEntity);
            System.out.println(accountEntity.getPassword());
            accountEntity.setPassword(pe.encode(accountEntity.getPassword()));
            accountRepository.save(accountEntity);
            Account account = new Account();
            BeanUtils.copyProperties(accountEntity, account);
            return account;
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
        accountEntity.setAddress(account.getAddress());
        accountEntity.setPhoto(account.getPhoto());
        accountRepository.save(accountEntity);

        return copyEntityToAccount(accountEntity);
    }

    private Account copyEntityToAccount(AccountEntity accountEntity) {
        Account account = new Account();
        BeanUtils.copyProperties(accountEntity, account);
        accountEntity.getRoleOfAccounts().stream()
                .map(RoleOfAccount::getRole)
                .forEach(account.getRoles()::add);
        return account;
    }

    @Override
    public void changePassword(Authentication authentication, ChangePasswordRequest request) {
        var username = authentication.getName();
        AccountEntity accountEntity = accountRepository.findByUsernameLike(username).get();
        // check if the current password is correct
        if (!pe.matches(request.getCurrentPassword(), accountEntity.getPassword())) {
            throw new IllegalStateException("Wrrong password");
        } 

        //check if the two password are the same
        if (!request.getNewPassword().equals(request.getComfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        //update password
        accountEntity.setPassword(pe.encode(request.getNewPassword()));

        //save password
        accountRepository.save(accountEntity);
    }

    public void invalidateToken(String token) {
        jwtUtils.invalidateToken(token);
    }
}
