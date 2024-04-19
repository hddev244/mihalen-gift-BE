package shop.mihalen.controller.admin;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import shop.mihalen.model.Account;
import shop.mihalen.security.AccountPrincipal;
import shop.mihalen.security.ChangePasswordRequest;
import shop.mihalen.security.JwtUtils;
import shop.mihalen.servive.AccountService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/all")
    ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAllAccounts());
    }

    @GetMapping("/pages")
    ResponseEntity<Page<Account>> getPageAccounts(
            HttpServletRequest request,
            @RequestParam("index") Optional<Integer> index,
            @RequestParam("size") Optional<Integer> size) {
        AccountPrincipal accountPrincipal = jwtUtils.getAccountPrincipalFromToken(request);
        return ResponseEntity.ok(
                accountService.findPageAccounts(size.orElse(10), index.orElse(0)));
    }

    @GetMapping("/{username}")
    Account getAccountA(@PathVariable("username") String username) {
        return accountService.findByUsername(username).get();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id){
        return accountService.deleteAccount(id);
    }

    @PutMapping("/lock/{id}")
    public ResponseEntity<?> lockAccount(@PathVariable("id") Long id){
        return accountService.lockAccount(id);
    }
    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody Account account
            ) {
        return accountService.createAccount(account);
    
    }
    @PutMapping("/profile/{username}")
    public ResponseEntity<Account> updateProfile(@PathVariable("username") String username,@RequestBody Account account
            ) {
        account = accountService.updateAccount(username,account);
    return ResponseEntity.ok(account);
    }

}
