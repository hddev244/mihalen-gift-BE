package shop.mihalen.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import shop.mihalen.entity.AccountEntity;
import shop.mihalen.repository.AccountRepository;
import shop.mihalen.security.AccountPrincipal;
import shop.mihalen.security.JwtUtils;

@Service
public class AuthUtil {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AccountRepository accountRepository;

    // Get account loged in from request by token
    public AccountEntity getAccountFromRequest(HttpServletRequest rq) {
        AccountPrincipal accountPrincipal = jwtUtils.getAccountPrincipalFromToken(rq);
        if (accountPrincipal != null) {
            String username = accountPrincipal.getUsername();
            return accountRepository.findByUsernameLike(username).orElse(null);
        }
        return null;
    }

}
