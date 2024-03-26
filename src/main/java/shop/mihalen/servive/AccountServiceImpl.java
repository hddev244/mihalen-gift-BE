package shop.mihalen.servive;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import shop.mihalen.entity.AccountEntity;
import shop.mihalen.entity.Role;
import shop.mihalen.entity.RoleOfAccount;
import shop.mihalen.model.Account;
import shop.mihalen.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ResponseEntity<Account> saveAccount(Account account) {
        BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
        Optional<AccountEntity> accountFound = accountRepository.findByUsernameLike(account.getUsername());
        if (accountFound.isPresent()) {
            return new ResponseEntity<>(account, HttpStatus.BAD_REQUEST);
        } else {
            AccountEntity accountEntity = new AccountEntity();
            BeanUtils.copyProperties(account, accountEntity);
            System.out.println(accountEntity.getPassword());
            accountEntity.setPassword(pe.encode(accountEntity.getPassword()));

            System.out.println(accountEntity.getPassword());

            accountRepository.save(accountEntity);
            return new ResponseEntity<>(account, HttpStatus.OK);
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
            Account account = new Account();
            BeanUtils.copyProperties(accountEntity.get(), account);

            accountEntity.get()
                    .getRoleOfAccounts().stream()
                    .map(RoleOfAccount::getRole)
                    .forEach(account.getRoles()::add);

            return Optional.of(account);
        }
        return Optional.empty();
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountEntity -> {
                    Account account = new Account();
                    BeanUtils.copyProperties(accountEntity, account);
                    accountEntity.getRoleOfAccounts().stream()
                            .map(RoleOfAccount::getRole)
                            .forEach(account.getRoles()::add);
                    return account;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<Account> findPageAccounts(int size, int index) {
        Pageable pageable = PageRequest.of(index, size);
        Page<AccountEntity> pageAccountEntity = accountRepository.findAll(pageable);
        
        List<Account> pageAccount = pageAccountEntity.getContent().stream()
                                                     .map(ae -> {
                                                        Account account = new Account();
                                                        BeanUtils.copyProperties(ae, account);
                                                        ae.getRoleOfAccounts().stream()
                                                            .map(RoleOfAccount::getRole)
                                                            .forEach(account.getRoles()::add);
                                                        return account;
                                                     })
                                                     .collect(Collectors.toList());

        return new PageImpl<>(pageAccount, pageable, pageAccountEntity.getTotalElements());
    }
}
