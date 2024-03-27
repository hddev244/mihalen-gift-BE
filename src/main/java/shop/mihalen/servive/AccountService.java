package shop.mihalen.servive;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import shop.mihalen.entity.AccountEntity;
import shop.mihalen.model.Account;
import shop.mihalen.model.AccountRegister;
import shop.mihalen.security.ChangePasswordRequest;

public interface AccountService  {
    Account saveAccount(AccountRegister accountRegister);
    Optional<AccountEntity> findById(Long id);
    Optional<Account> findByUsername(String username);
    List<Account> findAllAccounts();
    Page<Account> findPageAccounts(int size,int index);
    Account updateAccount(String username, Account account);
    void changePassword(Authentication authentication, ChangePasswordRequest request);
}
