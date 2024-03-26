package shop.mihalen.servive;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import shop.mihalen.entity.AccountEntity;
import shop.mihalen.model.Account;

public interface AccountService  {
    ResponseEntity<Account> saveAccount(Account account);
    Optional<AccountEntity> findById(Long id);
    Optional<Account> findByUsername(String username);
    List<Account> findAllAccounts();
    Page<Account> findPageAccounts(int size,int index);
}
